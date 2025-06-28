import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Service service = new Service();

        // Using Thread.sleep(1) to ensure each transaction has a distinct timestamp
        // so that the printed statement shows the transactions in correct chronological order.
        // This is only for demonstration purposes.

        service.setRoom(1, RoomType.STANDARD_SUITE, 1000);
        Thread.sleep(1);
        service.setRoom(2, RoomType.JUNIOR_SUITE, 2000);
        Thread.sleep(1);
        service.setRoom(3, RoomType.MASTER_SUITE, 3000);

        service.setUser(1, 5000);
        Thread.sleep(1);
        service.setUser(2, 10000);

        // parseDate() Converts a date string like "07/07/2026" into a Date object for demonstration purposes

        service.bookRoom(1, 2, parseDate("30/06/2026"), parseDate("07/07/2026"));
        service.bookRoom(1, 2, parseDate("07/07/2026"), parseDate("30/06/2026"));
        service.bookRoom(1, 1, parseDate("07/07/2026"), parseDate("08/07/2026"));
        service.bookRoom(2, 1, parseDate("07/07/2026"), parseDate("09/07/2026"));
        service.bookRoom(2, 3, parseDate("07/07/2026"), parseDate("08/07/2026"));
        service.setRoom(1, RoomType.MASTER_SUITE, 10000);

        service.printAllUsers();
        service.printAll();
    }

    public static Date parseDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false); // Strict parsing, avoid weird dates
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + dateString);
            return null; // Or throw a RuntimeException if preferred
        }
    }
}