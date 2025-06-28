import java.util.Date;

public class Room {
    private final int roomNumber;
    private RoomType type;
    private int pricePerNight;
    private final Date createdAt;

    public Room(int roomNumber, RoomType type, int pricePerNight) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.createdAt = new Date();
    }

    public void setType(RoomType type) { this.type = type; }
    public void setPricePerNight(int pricePerNight) { this.pricePerNight = pricePerNight; }
    public int getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public int getPricePerNight() { return pricePerNight; }
    public Date getCreatedAt() { return createdAt; }
}