import java.util.Date;

public class Booking {
    private final int roomNumber;
    private final RoomType roomType;
    private final int pricePerNight;
    private final int userId;
    private final int userBalanceAtBooking;
    private final Date checkIn;
    private final Date checkOut;
    private final Date createdAt;

    public Booking(int roomNumber, RoomType roomType, int pricePerNight,
                   int userId, int userBalanceAtBooking, Date checkIn, Date checkOut) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.userId = userId;
        this.userBalanceAtBooking = userBalanceAtBooking;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = new Date();
    }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public int getPricePerNight() { return pricePerNight; }
    public int getUserId() { return userId; }
    public int getUserBalanceAtBooking() { return userBalanceAtBooking; }
    public Date getCheckIn() { return checkIn; }
    public Date getCheckOut() { return checkOut; }
    public Date getCreatedAt() { return createdAt; }
}
