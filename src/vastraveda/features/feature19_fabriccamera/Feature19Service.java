package vastraveda.features.feature19_fabriccamera;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Mock fabric classifier — no real ML. Uses filename hints or random demo output.
 */
public class Feature19Service {

    private final Random random = new Random();

    public String classifyMock(String fileName, BufferedImage image) {
        if (fileName != null) {
            String n = fileName.toLowerCase(Locale.ROOT);
            if (n.contains("silk") || n.contains("saree")) {
                return "Silk (mock: filename hint)";
            }
            if (n.contains("cotton") || n.contains("khadi")) {
                return "Cotton (mock: filename hint)";
            }
            if (n.contains("wool") || n.contains("shawl")) {
                return "Wool (mock: filename hint)";
            }
        }
        if (image != null) {
            int w = image.getWidth();
            int h = image.getHeight();
            long sum = 0;
            int step = Math.max(1, Math.min(w, h) / 32);
            for (int y = 0; y < h; y += step) {
                for (int x = 0; x < w; x += step) {
                    sum += image.getRGB(x, y) & 0xFFFFFF;
                }
            }
            String[] fabrics = {"Cotton", "Silk", "Linen blend (mock)", "Wool-like texture (mock)"};
            return fabrics[(int) (Math.abs(sum) % fabrics.length)] + " — heuristic from image pixels (demo only)";
        }
        return pickRandom();
    }

    public String classifyPreset(String presetKey) {
        Map<String, String> map = new HashMap<>();
        map.put("cotton", "Cotton — high confidence (mock preset)");
        map.put("silk", "Silk — mock preset for demos");
        map.put("wool", "Wool / Pashmina family — mock preset");
        return map.getOrDefault(presetKey.toLowerCase(Locale.ROOT), pickRandom());
    }

    private String pickRandom() {
        String[] fabrics = {"Cotton", "Silk", "Wool", "Linen", "Brocade / zari blend (mock)"};
        return fabrics[random.nextInt(fabrics.length)] + " (random mock)";
    }
}
