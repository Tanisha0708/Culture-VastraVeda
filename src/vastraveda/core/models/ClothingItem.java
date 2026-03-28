package vastraveda.core.models;

/**
 * 🔒 CORE MODEL — DO NOT MODIFY
 * Represents a traditional Indian clothing item.
 * Contributors: Use this class as-is in your features.
 */
public class ClothingItem {

    private String name;
    private String region;
    private String fabricType;
    private String occasion;
    private String gender;
    private String description;
    private String imageIcon;   // emoji or icon reference
    private String era;         // historical period
    private String careInstructions;

    public ClothingItem(String name, String region, String fabricType,
                        String occasion, String gender, String description) {
        this.name = name;
        this.region = region;
        this.fabricType = fabricType;
        this.occasion = occasion;
        this.gender = gender;
        this.description = description;
        this.imageIcon = "🥻";
        this.era = "Traditional";
        this.careInstructions = "Dry clean recommended";
    }

    public ClothingItem(String name, String region, String fabricType,
                        String occasion, String gender, String description,
                        String imageIcon, String era, String careInstructions) {
        this.name = name;
        this.region = region;
        this.fabricType = fabricType;
        this.occasion = occasion;
        this.gender = gender;
        this.description = description;
        this.imageIcon = imageIcon;
        this.era = era;
        this.careInstructions = careInstructions;
    }

    // --- Getters ---
    public String getName()             { return name; }
    public String getRegion()           { return region; }
    public String getFabricType()       { return fabricType; }
    public String getOccasion()         { return occasion; }
    public String getGender()           { return gender; }
    public String getDescription()      { return description; }
    public String getImageIcon()        { return imageIcon; }
    public String getEra()              { return era; }
    public String getCareInstructions() { return careInstructions; }

    // --- Setters ---
    public void setName(String name)                       { this.name = name; }
    public void setRegion(String region)                   { this.region = region; }
    public void setFabricType(String fabricType)           { this.fabricType = fabricType; }
    public void setOccasion(String occasion)               { this.occasion = occasion; }
    public void setGender(String gender)                   { this.gender = gender; }
    public void setDescription(String description)         { this.description = description; }
    public void setImageIcon(String imageIcon)             { this.imageIcon = imageIcon; }
    public void setEra(String era)                         { this.era = era; }
    public void setCareInstructions(String care)           { this.careInstructions = care; }

    @Override
    public String toString() {
        return name + " (" + region + ")";
    }
}
