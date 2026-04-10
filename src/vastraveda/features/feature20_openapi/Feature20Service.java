package vastraveda.features.feature20_openapi;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * Embedded mock REST API for developers (JDK HttpServer).
 */
public class Feature20Service {

    public static final String DEFAULT_API_KEY = "vv-dev-2026";

    private HttpServer server;
    private int boundPort = -1;

    public int getBoundPort() {
        return boundPort;
    }

    public boolean isRunning() {
        return server != null;
    }

    public void start(int port) throws IOException {
        stop();
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
        boundPort = server.getAddress().getPort();
        server.createContext("/api/v1/health", new HealthHandler());
        server.createContext("/api/v1/clothing", new ClothingListHandler());
        server.createContext("/api/v1/docs", new DocsHandler());
        server.setExecutor(null);
        server.start();
    }

    public void stop() {
        if (server != null) {
            server.stop(1);
            server = null;
            boundPort = -1;
        }
    }

    private boolean authorize(HttpExchange ex) {
        String key = ex.getRequestHeaders().getFirst("X-API-Key");
        if (key == null) {
            key = "";
        }
        return DEFAULT_API_KEY.equals(key.trim());
    }

    private void send(HttpExchange ex, int code, String contentType, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", contentType);
        ex.sendResponseHeaders(code, bytes.length);
        try (OutputStream os = ex.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void sendJson(HttpExchange ex, int code, String json) throws IOException {
        send(ex, code, "application/json; charset=utf-8", json);
    }

    private void sendUnauthorized(HttpExchange ex) throws IOException {
        sendJson(ex, 401, "{\"error\":\"invalid or missing X-API-Key\"}");
    }

    private String jsonEscape(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "");
    }

    private String buildClothingJson(List<ClothingItem> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"ok\":true,\"count\":").append(items.size()).append(",\"data\":[");
        for (int i = 0; i < items.size(); i++) {
            ClothingItem it = items.get(i);
            if (i > 0) {
                sb.append(',');
            }
            sb.append('{');
            sb.append("\"name\":\"").append(jsonEscape(it.getName())).append("\",");
            sb.append("\"region\":\"").append(jsonEscape(it.getRegion())).append("\",");
            sb.append("\"fabric\":\"").append(jsonEscape(it.getFabricType())).append("\",");
            sb.append("\"occasion\":\"").append(jsonEscape(it.getOccasion())).append("\",");
            sb.append("\"gender\":\"").append(jsonEscape(it.getGender())).append("\"");
            sb.append('}');
        }
        sb.append("]}");
        return sb.toString();
    }

    private class HealthHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
                send(ex, 405, "text/plain", "Method Not Allowed");
                return;
            }
            if (!authorize(ex)) {
                sendUnauthorized(ex);
                return;
            }
            sendJson(ex, 200, "{\"ok\":true,\"service\":\"VastraVeda Open API\"}");
        }
    }

    private class ClothingListHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
                send(ex, 405, "text/plain", "Method Not Allowed");
                return;
            }
            if (!authorize(ex)) {
                sendUnauthorized(ex);
                return;
            }
            String query = ex.getRequestURI().getQuery();
            String fabricFilter = null;
            if (query != null) {
                for (String part : query.split("&")) {
                    String[] kv = part.split("=", 2);
                    if (kv.length == 2 && "fabric".equalsIgnoreCase(kv[0])) {
                        fabricFilter = java.net.URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name());
                    }
                }
            }
            List<ClothingItem> all = DataStore.getAllItems();
            if (fabricFilter != null && !fabricFilter.isEmpty()) {
                String f = fabricFilter.toLowerCase(Locale.ROOT);
                all = new java.util.ArrayList<>();
                for (ClothingItem it : DataStore.getAllItems()) {
                    if (it.getFabricType().toLowerCase(Locale.ROOT).contains(f)) {
                        all.add(it);
                    }
                }
            }
            sendJson(ex, 200, buildClothingJson(all));
        }
    }

    private class DocsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) throws IOException {
            if (!"GET".equalsIgnoreCase(ex.getRequestMethod())) {
                send(ex, 405, "text/plain", "Method Not Allowed");
                return;
            }
            String html = "<!DOCTYPE html><html><head><meta charset='utf-8'><title>VastraVeda API</title></head><body>"
                + "<h1>VastraVeda Open Clothing API</h1>"
                + "<p>Send header <code>X-API-Key: " + DEFAULT_API_KEY + "</code></p>"
                + "<ul>"
                + "<li><code>GET /api/v1/health</code> — health check</li>"
                + "<li><code>GET /api/v1/clothing</code> — all garments (JSON)</li>"
                + "<li><code>GET /api/v1/clothing?fabric=Silk</code> — filter by fabric substring</li>"
                + "</ul>"
                + "<p>Example (curl):</p><pre>curl -H \"X-API-Key: " + DEFAULT_API_KEY
                + "\" http://127.0.0.1:PORT/api/v1/clothing</pre>"
                + "</body></html>";
            send(ex, 200, "text/html; charset=utf-8", html);
        }
    }
}
