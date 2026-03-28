package vastraveda.core.utils;

import vastraveda.core.models.ClothingItem;
import vastraveda.core.data.DataStore;
import java.util.ArrayList;
import java.util.List;

/**
 * 🔒 CORE UTILITIES — DO NOT MODIFY
 * Provides static filter methods for querying clothing items.
 * Contributors: Use these methods instead of writing your own filters.
 */
public class FilterUtils {

    private FilterUtils() {
        // Utility class — no instantiation
    }

    /**
     * Filter items by region (case-insensitive, partial match).
     */
    public static List<ClothingItem> filterByRegion(String region) {
        List<ClothingItem> result = new ArrayList<>();
        if (region == null || region.trim().isEmpty()) return DataStore.getAllItems();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getRegion().toLowerCase().contains(region.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Filter items by fabric type (case-insensitive, partial match).
     */
    public static List<ClothingItem> filterByFabric(String fabric) {
        List<ClothingItem> result = new ArrayList<>();
        if (fabric == null || fabric.trim().isEmpty()) return DataStore.getAllItems();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getFabricType().toLowerCase().contains(fabric.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Filter items by gender (case-insensitive, exact match).
     */
    public static List<ClothingItem> filterByGender(String gender) {
        List<ClothingItem> result = new ArrayList<>();
        if (gender == null || gender.trim().isEmpty() || gender.equals("All")) {
            return DataStore.getAllItems();
        }
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getGender().equalsIgnoreCase(gender) ||
                item.getGender().equalsIgnoreCase("Unisex")) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Filter items by occasion (case-insensitive, partial match).
     */
    public static List<ClothingItem> filterByOccasion(String occasion) {
        List<ClothingItem> result = new ArrayList<>();
        if (occasion == null || occasion.trim().isEmpty()) return DataStore.getAllItems();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getOccasion().toLowerCase().contains(occasion.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Multi-filter: pass null or empty string to skip any filter.
     */
    public static List<ClothingItem> filterMulti(String region, String fabric,
                                                  String gender, String occasion) {
        List<ClothingItem> result = new ArrayList<>(DataStore.getAllItems());

        if (region != null && !region.isEmpty() && !region.equals("All Regions")) {
            result.retainAll(filterByRegion(region));
        }
        if (fabric != null && !fabric.isEmpty() && !fabric.equals("All Fabrics")) {
            result.retainAll(filterByFabric(fabric));
        }
        if (gender != null && !gender.isEmpty() && !gender.equals("All Genders")) {
            List<ClothingItem> byGender = new ArrayList<>();
            for (ClothingItem item : result) {
                if (item.getGender().equalsIgnoreCase(gender) ||
                    item.getGender().equalsIgnoreCase("Unisex")) {
                    byGender.add(item);
                }
            }
            result = byGender;
        }
        if (occasion != null && !occasion.isEmpty() && !occasion.equals("All Occasions")) {
            result.retainAll(filterByOccasion(occasion));
        }
        return result;
    }

    /**
     * Search items by name (case-insensitive, partial match).
     */
    public static List<ClothingItem> searchByName(String query) {
        List<ClothingItem> result = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return DataStore.getAllItems();
        for (ClothingItem item : DataStore.getAllItems()) {
            if (item.getName().toLowerCase().contains(query.toLowerCase()) ||
                item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                result.add(item);
            }
        }
        return result;
    }
}
