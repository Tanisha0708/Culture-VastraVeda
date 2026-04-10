package vastraveda.features.feature11_care;

import vastraveda.core.data.DataStore;
import vastraveda.core.models.ClothingItem;

import java.util.List;

/**
 * Open dataset export — JSON and CSV from DataStore.
 */
public class Feature11Service {

    public String getMetadataJson() {
        return "{"
            + "\"name\":\"VastraVeda Clothing Dataset\","
            + "\"version\":\"1.0\","
            + "\"license\":\"MIT\","
            + "\"source\":\"DataStore\","
            + "\"recordCount\":" + DataStore.getAllItems().size()
            + "}";
    }

    public String buildJsonDataset() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"metadata\": ").append(getMetadataJson()).append(",\n  \"items\": [\n");
        List<ClothingItem> items = DataStore.getAllItems();
        for (int i = 0; i < items.size(); i++) {
            ClothingItem it = items.get(i);
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append("    {");
            sb.append("\"name\":\"").append(escapeJson(it.getName())).append("\",");
            sb.append("\"region\":\"").append(escapeJson(it.getRegion())).append("\",");
            sb.append("\"fabric\":\"").append(escapeJson(it.getFabricType())).append("\",");
            sb.append("\"occasion\":\"").append(escapeJson(it.getOccasion())).append("\",");
            sb.append("\"gender\":\"").append(escapeJson(it.getGender())).append("\"");
            sb.append("}");
        }
        sb.append("\n  ]\n}");
        return sb.toString();
    }

    public String buildCsvDataset() {
        StringBuilder sb = new StringBuilder();
        sb.append("name,region,fabric,occasion,gender,era,care\n");
        for (ClothingItem it : DataStore.getAllItems()) {
            sb.append(csvCell(it.getName())).append(',');
            sb.append(csvCell(it.getRegion())).append(',');
            sb.append(csvCell(it.getFabricType())).append(',');
            sb.append(csvCell(it.getOccasion())).append(',');
            sb.append(csvCell(it.getGender())).append(',');
            sb.append(csvCell(it.getEra())).append(',');
            sb.append(csvCell(it.getCareInstructions())).append('\n');
        }
        return sb.toString();
    }

    private String escapeJson(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
    }

    private String csvCell(String s) {
        if (s == null) {
            return "";
        }
        String t = s.replace("\"", "\"\"");
        if (t.contains(",") || t.contains("\"") || t.contains("\n")) {
            return "\"" + t + "\"";
        }
        return t;
    }
}
