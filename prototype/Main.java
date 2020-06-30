package prototype;
import java.time.LocalDate;

/**
 * Basic idea: You want an object, but instead of creating it, you clone an
 * existing object (called a prototype), and make modification to your object
 * later
 */
public class Main {
    // Client code
    public static void main(String[] args) {
        System.out.println("Program started.");
        Booking b1 = BookingFactory.build("Badminton", "ABC123", 5);
        Booking b2 = BookingFactory.build("ZumbaClass", "ABC456", 8);
        Booking b3 = BookingFactory.build("Badminton", "DEF123", 10);
        Booking b4 = BookingFactory.build("ZumbaClass", "DEF456", 12);

        // We have 4 bookings
        // But there are only 2 bookings actually created: the prototypes in the BookingFactory
    }    
}

abstract class Booking {
    Booking() { }
    Booking(String bookingID) {
        this.bookingID = bookingID;
        // suppose that it takes a lot of time to get current date
        // you want to use LocalDate.now() as few as possible!
        this.dateCreated = LocalDate.now();
        System.out.println("A new Booking is created on " + this.dateCreated);
    }
    abstract public Booking clone();
    public String bookingID;
    public LocalDate dateCreated;
}

class BadmintonBooking extends Booking {
    BadmintonBooking() { }
    BadmintonBooking(String bookingID) {
        super(bookingID);
    }
    public BadmintonBooking clone() {
        BadmintonBooking b = new BadmintonBooking();
        b.bookingID = this.bookingID;
        b.dateCreated = this.dateCreated;
        b.courtRoom = this.courtRoom;
        return b;
    }
    public int courtRoom;
}

class ZumbaBooking extends Booking {
    ZumbaBooking() { }
    ZumbaBooking(String bookingID) {
        super(bookingID);
    }
    public ZumbaBooking clone() {
        ZumbaBooking b = new ZumbaBooking();
        b.bookingID = this.bookingID;
        b.dateCreated = this.dateCreated;
        b.classRoom = this.classRoom;
        return b;
    }
    public int classRoom;
}

class BookingFactory {
    private static ZumbaBooking ZumbaBookingPrototype = new ZumbaBooking("");
    private static BadmintonBooking badmintonBookingPrototype = new BadmintonBooking("");
    public static Booking build(String type, String bookingID, int room) {
        switch (type) {
            case "Badminton": {
                BadmintonBooking result = badmintonBookingPrototype.clone();
                result.bookingID = bookingID;
                result.courtRoom = room;
                return result;
            }
            case "ZumbaClass": {
                ZumbaBooking result = ZumbaBookingPrototype.clone();
                result.bookingID = bookingID;
                result.classRoom = room;
                return result;
            }
            default:
                return null;
        }
    }
}

