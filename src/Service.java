import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Service {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Booking> bookings = new ArrayList<>();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    //Setting Users/Rooms
    public void setRoom(int roomNumber, RoomType type, int price) {
        if (!validateRoomParameters(roomNumber, type, price)) {
            return;
        }

        Room existingRoom = findRoom(roomNumber);

        // If room already exist
        if (existingRoom != null) {
            existingRoom.setType(type);
            existingRoom.setPricePerNight(price);
            return;
        }

        rooms.add(new Room(roomNumber, type, price));
    }

    public void setUser(int userId, int balance) {
        if (!validateUserParameters(userId, balance)) {
            return;
        }

        User existingUser = findUser(userId);

        // If user already exist
        if (existingUser != null) {
            existingUser.setBalance(balance);
            return;
        }

        users.add(new User(userId, balance));
    }

    private boolean validateRoomParameters(int roomNumber, RoomType type, int price) {
        if (type == null) {
            System.out.println("Room type cannot be null.");
            return false;
        }
        if (roomNumber <= 0) {
            System.out.println("Room number must be positive.");
            return false;
        }
        if (price <= 0) {
            System.out.println("Price must be positive.");
            return false;
        }
        return true;
    }

    private boolean validateUserParameters(int userId, int balance) {
        if (userId <= 0) {
            System.out.println("User ID must be positive.");
            return false;
        }
        if (balance < 0) {
            System.out.println("Balance cannot be negative.");
            return false;
        }
        return true;
    }

    private Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private User findUser(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    //Booking
    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {

        // Check for null and invalid inputs.
        if (!validateBookingInput(userId, roomNumber, checkIn, checkOut)) {
            return;
        }

        // Normalize dates to midnight and verify that check-in is strictly before check-out
        Date normalizedCheckIn = truncateTime(checkIn);
        Date normalizedCheckOut = truncateTime(checkOut);

        if (!isCheckInBeforeCheckOut(normalizedCheckIn, normalizedCheckOut)) {
            return;
        }

        // Find user and room, check if they exist
        User user = findUser(userId);
        Room room = findRoom(roomNumber);

        if (user == null) {
            System.out.println("User does not exist");
            return;
        }

        if (room == null) {
            System.out.println("Room does not exist");
            return;
        }

        // Check room availability
        if (!isRoomAvailable(roomNumber, normalizedCheckIn, normalizedCheckOut)) {
            System.out.println("Room " + roomNumber + " is not available");
            return;
        }

        // Calculate booking cost and verify the user has sufficient balance
        int nights = calculateNights(normalizedCheckIn, normalizedCheckOut);
        int totalCost = nights * room.getPricePerNight();

        if (user.getBalance() < totalCost) {
            System.out.println("User " + userId + " has insufficient balance. " +
                    "Required: " + totalCost + ", Available: " + user.getBalance());
            return;
        }

        // Process booking
        user.setBalance(user.getBalance() - totalCost);
        bookings.add(new Booking(
                roomNumber,
                room.getType(),
                room.getPricePerNight(),
                userId,
                user.getBalance(),
                normalizedCheckIn,
                normalizedCheckOut
        ));

        System.out.println("Booking successful: User " + userId + " booked Room " +
                roomNumber + " for " + nights + " nights");
    }

    private boolean validateBookingInput(int userId, int roomNumber, Date checkIn, Date checkOut) {
        if (userId <= 0) {
            System.out.println("User ID must be positive.");
            return false;
        }
        if (roomNumber <= 0) {
            System.out.println("Room number must be positive.");
            return false;
        }
        if (checkIn == null) {
            System.out.println("Check-in date cannot be null.");
            return false;
        }
        if (checkOut == null) {
            System.out.println("Check-out date cannot be null.");
            return false;
        }
        return true;
    }

    private Date truncateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private boolean isCheckInBeforeCheckOut(Date checkIn, Date checkOut) {
        if (!checkIn.before(checkOut)) {
            System.out.println("Check-in date must be strictly before check-out date.");
            return false;
        }
        return true;
    }

    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                if (checkIn.before(booking.getCheckOut()) &&
                        checkOut.after(booking.getCheckIn())) {
                    return false;
                }
            }
        }
        return true;
    }

    private int calculateNights(Date checkIn, Date checkOut) {
        long diff = checkOut.getTime() - checkIn.getTime();
        return (int) (diff / (1000 * 60 * 60 * 24));
    }

    // Reporting
    public void printAllUsers() {
        System.out.println("=== ALL USERS ===");
        users.stream()
                .sorted(Comparator.comparing(User::getCreatedAt).reversed())
                .forEach(this::printUser);
    }

    public void printAll() {
        printAllRooms();
        printAllBookings();
    }

    private void printAllBookings() {
        System.out.println("\n=== ALL BOOKINGS ===");
        bookings.stream()
                .sorted(Comparator.comparing(Booking::getCreatedAt).reversed())
                .forEach(this::printBooking);
    }

    private void printAllRooms() {
        System.out.println("=== ALL ROOMS ===");
        rooms.stream()
                .sorted(Comparator.comparing(Room::getCreatedAt).reversed())
                .forEach(this::printRoom);
    }

    private void printUser(User user) {
        String createdAt = DATE_FORMAT.format(user.getCreatedAt());
        System.out.printf(
                "User ID: %d | Balance: %d | Created on: %s%n",
                user.getUserId(),
                user.getBalance(),
                createdAt
        );
    }

    private void printRoom(Room room) {
        String createdAt = DATE_FORMAT.format(room.getCreatedAt());

        System.out.printf(
                "Room number: %d | Type: %s | Price per night: %d | Created on: %s%n",
                room.getRoomNumber(),
                room.getType().getDisplayName(),
                room.getPricePerNight(),
                createdAt
        );
    }

    private void printBooking(Booking booking) {
        String checkInStr = DATE_FORMAT.format(booking.getCheckIn());
        String checkOutStr = DATE_FORMAT.format(booking.getCheckOut());

        System.out.printf(
                "User ID: %d | Balance: %d | Room number: %d | Type: %s | Price per night: %d | CheckIn date: %s | CheckOut date: %s%n",
                booking.getUserId(),
                booking.getUserBalanceAtBooking(),
                booking.getRoomNumber(),
                booking.getRoomType().getDisplayName(),
                booking.getPricePerNight(),
                checkInStr,
                checkOutStr
        );
    }
}
