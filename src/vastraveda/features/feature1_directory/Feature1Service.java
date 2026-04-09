package vastraveda.features.feature1_directory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Feature 1 — Service / Business Logic
 * Structured catalog with filtering and pagination.
 */
public class Feature1Service {

    public static final class CatalogItem {
        private final int id;
        private final String name;
        private final String region;
        private final String community;
        private final String fabric;
        private final String description;
        private final String imageUrl;

        public CatalogItem(int id, String name, String region, String community,
                           String fabric, String description, String imageUrl) {
            this.id = id;
            this.name = name;
            this.region = region;
            this.community = community;
            this.fabric = fabric;
            this.description = description;
            this.imageUrl = imageUrl;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getRegion() {
            return region;
        }

        public String getCommunity() {
            return community;
        }

        public String getFabric() {
            return fabric;
        }

        public String getDescription() {
            return description;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

    public static final class PagedResult<T> {
        private final List<T> data;
        private final int total;
        private final int page;
        private final int totalPages;

        public PagedResult(List<T> data, int total, int page, int totalPages) {
            this.data = data;
            this.total = total;
            this.page = page;
            this.totalPages = totalPages;
        }

        public List<T> getData() {
            return data;
        }

        public int getTotal() {
            return total;
        }

        public int getPage() {
            return page;
        }

        public int getTotalPages() {
            return totalPages;
        }
    }

    private final List<CatalogItem> dataset = Arrays.asList(
        new CatalogItem(1, "Nauvari Saree", "Maharashtra", "Brahmin", "Cotton",
            "Nine-yard saree draped in kashta style for festive and ritual occasions.", ""),
        new CatalogItem(2, "Paithani Saree", "Maharashtra", "Maratha", "Silk",
            "Rich handwoven saree with zari peacock motifs, often used for weddings.", ""),
        new CatalogItem(3, "Phulkari Dupatta", "Punjab", "Sikh", "Cotton",
            "Embroidered dupatta with floral threadwork associated with Punjabi households.", ""),
        new CatalogItem(4, "Punjabi Salwar Kameez", "Punjab", "Punjabi", "Cotton",
            "Comfortable kurta and salwar set worn across everyday and festive contexts.", ""),
        new CatalogItem(5, "Mundu Neriyathu", "South India", "Nair", "Cotton",
            "Two-piece Kerala drape with kasavu border, commonly worn during festivals.", ""),
        new CatalogItem(6, "Kanchipuram Saree", "South India", "Tamil Brahmin", "Silk",
            "Heavy silk saree with temple motifs and contrasting borders.", ""),
        new CatalogItem(7, "Mekhela Chador", "North East", "Assamese", "Silk",
            "Traditional Assamese two-piece garment in muga or pat silk.", ""),
        new CatalogItem(8, "Puan", "North East", "Mizo Tribal", "Cotton",
            "Handwoven wrap skirt with geometric motifs used in social ceremonies.", ""),
        new CatalogItem(9, "Ghagra Choli", "Rajasthan", "Rajput", "Cotton",
            "Flared skirt and choli ensemble decorated with mirror work.", ""),
        new CatalogItem(10, "Bandhani Saree", "Rajasthan", "Jain", "Silk",
            "Tie-dye saree with dotted resist patterns in bright colors.", ""),
        new CatalogItem(11, "Pashmina Shawl", "Kashmir", "Kashmiri", "Wool",
            "Fine shawl made from pashmina fiber, valued for warmth and softness.", ""),
        new CatalogItem(12, "Pheran", "Kashmir", "Kashmiri", "Wool",
            "Loose winter garment layered with woolens for cold climates.", ""),
        new CatalogItem(13, "Dhoti Kurta", "Uttar Pradesh", "Brahmin", "Cotton",
            "Traditional men’s attire used in religious functions and ceremonies.", ""),
        new CatalogItem(14, "Banarasi Saree", "Uttar Pradesh", "Kayastha", "Silk",
            "Brocade saree with zari weaving from Varanasi.", ""),
        new CatalogItem(15, "Sambalpuri Saree", "Odisha", "Weaver Community", "Cotton",
            "Ikat-style saree with geometric and conch motifs.", ""),
        new CatalogItem(16, "Bomkai Saree", "Odisha", "Tribal", "Silk",
            "Textured saree combining ikat body and extra-weft borders.", ""),
        new CatalogItem(17, "Gamocha Attire", "Assam", "Assamese", "Cotton",
            "Traditional cloth used with ceremonial attire and cultural events.", ""),
        new CatalogItem(18, "Toda Embroidered Shawl", "Tamil Nadu", "Toda Tribal", "Wool",
            "Distinct red-black embroidery on white shawls from the Nilgiri hills.", ""),
        new CatalogItem(19, "Angarkha", "Gujarat", "Rabari Tribal", "Cotton",
            "Wrap-style upper garment paired with kediyu variants in folk wear.", ""),
        new CatalogItem(20, "Patola Saree", "Gujarat", "Jain", "Silk",
            "Double-ikat woven saree known for precise geometric patterns.", "")
    );

    public PagedResult<CatalogItem> queryCatalog(String region, String community, String fabric,
                                                 String search, String sort,
                                                 int page, int limit) {
        List<CatalogItem> filtered = new ArrayList<>(dataset);

        if (!isAll(region)) {
            String q = region.toLowerCase();
            filtered.removeIf(i -> !i.getRegion().toLowerCase().contains(q));
        }
        if (!isAll(community)) {
            String q = community.toLowerCase();
            filtered.removeIf(i -> !i.getCommunity().toLowerCase().contains(q));
        }
        if (!isAll(fabric)) {
            String q = fabric.toLowerCase();
            filtered.removeIf(i -> !i.getFabric().toLowerCase().contains(q));
        }
        if (search != null && !search.trim().isEmpty()) {
            String q = search.trim().toLowerCase();
            filtered.removeIf(i ->
                !i.getName().toLowerCase().contains(q) &&
                !i.getDescription().toLowerCase().contains(q));
        }

        if ("fabric".equalsIgnoreCase(sort)) {
            filtered.sort(Comparator.comparing(CatalogItem::getFabric).thenComparing(CatalogItem::getName));
        } else {
            filtered.sort(Comparator.comparing(CatalogItem::getName));
        }

        int safeLimit = Math.max(1, limit);
        int total = filtered.size();
        int totalPages = Math.max(1, (int) Math.ceil(total / (double) safeLimit));
        int safePage = Math.min(Math.max(1, page), totalPages);
        int fromIndex = (safePage - 1) * safeLimit;
        int toIndex = Math.min(fromIndex + safeLimit, total);

        List<CatalogItem> data = fromIndex >= total
            ? Collections.emptyList()
            : filtered.subList(fromIndex, toIndex);
        return new PagedResult<>(data, total, safePage, totalPages);
    }

    public List<String> getAllRegions() {
        List<String> values = new ArrayList<>();
        for (CatalogItem item : dataset) {
            values.add(item.getRegion());
        }
        return toDistinctSortedValues(values);
    }

    public List<String> getAllCommunities() {
        List<String> values = new ArrayList<>();
        for (CatalogItem item : dataset) {
            values.add(item.getCommunity());
        }
        return toDistinctSortedValues(values);
    }

    public List<String> getAllFabrics() {
        List<String> values = new ArrayList<>();
        for (CatalogItem item : dataset) {
            values.add(item.getFabric());
        }
        return toDistinctSortedValues(values);
    }

    public String formatDetail(CatalogItem item) {
        return String.format(
            "ID: %d\n" +
            "Name: %s\n" +
            "Region: %s\n" +
            "Community: %s\n" +
            "Fabric: %s\n\n" +
            "Description:\n%s\n\n" +
            "Image URL: %s",
            item.getId(),
            item.getName(),
            item.getRegion(),
            item.getCommunity(),
            item.getFabric(),
            item.getDescription(),
            item.getImageUrl() == null || item.getImageUrl().isBlank() ? "N/A" : item.getImageUrl()
        );
    }

    private boolean isAll(String value) {
        return value == null || value.trim().isEmpty() || value.toLowerCase().startsWith("all ");
    }

    private List<String> toDistinctSortedValues(List<String> values) {
        Set<String> unique = new LinkedHashSet<>(values);
        List<String> sorted = new ArrayList<>(unique);
        Collections.sort(sorted);
        return sorted;
    }
}
