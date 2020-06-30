/**
 * Basic idea: You want to create an object of an abstract class, 
 * but you are lazy and you don't want to read documentation about the concrete classes,
 * so you give a factory some information so that it can create the object for you
 */
package factory;
public class Main {
    // Client code
    public static void main(String[] args) {
        Booking b = BookingFactory.build("Badminton", "ABC123", 5);
        assert (b instanceof BadmintonBooking);
        b = BookingFactory.build("ZumbaClass", "ABC456", 8);
        assert (b instanceof ZumbaBooking);
        System.out.println("All assertions passed!");
    }
}

abstract class Booking {
    Booking(String bookingID) {
        this.bookingID = bookingID;
    }
    public String bookingID;
}

class BadmintonBooking extends Booking {
    BadmintonBooking(String bookingID) {
        super(bookingID);
    }
    public int courtRoom;
}

class ZumbaBooking extends Booking {
    ZumbaBooking(String bookingID) {
        super(bookingID);
    }
    public int classRoom;
}

class BookingFactory {
    public static Booking build(String type, String bookingID, int room) {
        switch (type) {
            case "Badminton": {
                BadmintonBooking result = new BadmintonBooking(bookingID);
                result.courtRoom = room;
                return result;
            }
            case "ZumbaClass": {
                ZumbaBooking result = new ZumbaBooking(bookingID);
                result.classRoom = room;
                return result;
            }
            default:
                return null;
        }
    }
}
