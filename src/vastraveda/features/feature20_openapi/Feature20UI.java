package vastraveda.features.feature20_openapi;

import vastraveda.core.utils.BaseUI;
import vastraveda.core.utils.Feature;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class Feature20UI extends BaseUI implements Feature {

    private final Feature20Service service = new Feature20Service();
    private JTextField portField;
    private JLabel statusLabel;
    private JTextArea docsArea;
    private JButton startBtn;
    private JButton stopBtn;
    private JButton openDocsBtn;

    public Feature20UI() {
        super("Open Clothing API");
        buildUI();
    }

    @Override
    public void render() {
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout());
        add(createHeader("🌐 Open Clothing API", "Mock REST server + API key + in-app docs"), BorderLayout.NORTH);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setBackground(new Color(245, 235, 215));
        top.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        top.add(new JLabel("Port:"));
        portField = new JTextField("8787", 6);
        top.add(portField);
        startBtn = createStyledButton("Start server", COLOR_PRIMARY, COLOR_TEXT_LIGHT);
        startBtn.addActionListener(e -> startServer());
        top.add(startBtn);
        stopBtn = createStyledButton("Stop", COLOR_ACCENT, COLOR_TEXT_LIGHT);
        stopBtn.addActionListener(e -> stopServer());
        top.add(stopBtn);
        openDocsBtn = createStyledButton("Open docs in browser", COLOR_BORDER, COLOR_TEXT);
        openDocsBtn.setEnabled(false);
        openDocsBtn.addActionListener(e -> openBrowserDocs());
        top.add(openDocsBtn);
        statusLabel = new JLabel("Stopped");
        statusLabel.setFont(FONT_SMALL);
        top.add(statusLabel);

        docsArea = new JTextArea(buildDocsText());
        docsArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        docsArea.setEditable(false);
        docsArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JScrollPane scroll = new JScrollPane(docsArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Documentation"));

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                service.stop();
            }
        });
    }

    private String buildDocsText() {
        return "VastraVeda Open Clothing API (embedded)\n"
            + "========================================\n\n"
            + "API key (header): X-API-Key: " + Feature20Service.DEFAULT_API_KEY + "\n\n"
            + "Endpoints (after Start server):\n"
            + "  GET  /api/v1/health\n"
            + "  GET  /api/v1/clothing\n"
            + "  GET  /api/v1/clothing?fabric=Silk\n"
            + "  GET  /api/v1/docs          (HTML in browser)\n\n"
            + "Base URL: http://127.0.0.1:<port>\n\n"
            + "Example:\n"
            + "  curl -H \"X-API-Key: " + Feature20Service.DEFAULT_API_KEY + "\" "
            + "http://127.0.0.1:8787/api/v1/clothing\n";
    }

    private void startServer() {
        try {
            int port = Integer.parseInt(portField.getText().trim());
            service.start(port);
            int p = service.getBoundPort();
            statusLabel.setText("Running on http://127.0.0.1:" + p);
            openDocsBtn.setEnabled(true);
            showInfo("API", "Server started on port " + p);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Start failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stopServer() {
        service.stop();
        statusLabel.setText("Stopped");
        openDocsBtn.setEnabled(false);
    }

    private void openBrowserDocs() {
        try {
            int p = service.getBoundPort();
            if (p <= 0) {
                return;
            }
            Desktop.getDesktop().browse(new URI("http://127.0.0.1:" + p + "/api/v1/docs"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not open browser: " + ex.getMessage(),
                "Browser", JOptionPane.WARNING_MESSAGE);
        }
    }
}
