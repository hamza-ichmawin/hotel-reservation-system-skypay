public enum RoomType {
    STANDARD_SUITE("Standard"),
    JUNIOR_SUITE("Junior"),
    MASTER_SUITE("Master");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
