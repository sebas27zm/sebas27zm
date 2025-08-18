package utils;

/**
 * Enum for predefined nationalities
 */
public enum Nationality {
    ARGENTINA("Argentina"),
    BOLIVIA("Bolivia"),
    BRAZIL("Brazil"),
    CANADA("Canada"),
    CHILE("Chile"),
    COLOMBIA("Colombia"),
    COSTA_RICA("Costa Rica"),
    CUBA("Cuba"),
    DOMINICAN_REPUBLIC("Dominican Republic"),
    ECUADOR("Ecuador"),
    EL_SALVADOR("El Salvador"),
    GUATEMALA("Guatemala"),
    HAITI("Haiti"),
    HONDURAS("Honduras"),
    JAMAICA("Jamaica"),
    MEXICO("Mexico"),
    NICARAGUA("Nicaragua"),
    PANAMA("Panama"),
    PARAGUAY("Paraguay"),
    PERU("Peru"),
    PUERTO_RICO("Puerto Rico"),
    UNITED_STATES("United States"),
    URUGUAY("Uruguay"),
    VENEZUELA("Venezuela"),
    SPAIN("Spain"),
    FRANCE("France"),
    GERMANY("Germany"),
    ITALY("Italy"),
    UNITED_KINGDOM("United Kingdom"),
    PORTUGAL("Portugal"),
    NETHERLANDS("Netherlands"),
    BELGIUM("Belgium"),
    SWITZERLAND("Switzerland"),
    AUSTRIA("Austria"),
    SWEDEN("Sweden"),
    NORWAY("Norway"),
    DENMARK("Denmark"),
    FINLAND("Finland"),
    POLAND("Poland"),
    RUSSIA("Russia"),
    CHINA("China"),
    JAPAN("Japan"),
    SOUTH_KOREA("South Korea"),
    INDIA("India"),
    AUSTRALIA("Australia"),
    NEW_ZEALAND("New Zealand"),
    SOUTH_AFRICA("South Africa"),
    EGYPT("Egypt"),
    MOROCCO("Morocco"),
    NIGERIA("Nigeria"),
    KENYA("Kenya"),
    OTHER("Other");
    
    private final String displayName;
    
    Nationality(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * Get nationality by display name
     */
    public static Nationality fromDisplayName(String displayName) {
        for (Nationality nationality : values()) {
            if (nationality.displayName.equalsIgnoreCase(displayName)) {
                return nationality;
            }
        }
        return OTHER;
    }
    
    /**
     * Get all nationality display names
     */
    public static String[] getAllDisplayNames() {
        Nationality[] nationalities = values();
        String[] displayNames = new String[nationalities.length];
        for (int i = 0; i < nationalities.length; i++) {
            displayNames[i] = nationalities[i].displayName;
        }
        return displayNames;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}