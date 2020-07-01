package decorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * In addition to bookings, we now offer services in our center
 * - You can use the Shower for 15 VND
 * - You can use the Locker for 10 VND
 * Also, a badminton booking costs 90 VND
 *
 * The price will add up the more services you use.
 * The idea of Decorator pattern is to wrap an object with another object
 * to provide more meaningful properties
 *
 * At first I have a BadmintonBooking, then I wrap it with a Locker,
 * then I wrap the BadmintonBooking + Locker with a Shower,
 * finally I have a BadmintonBooking + Locker + Shower
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Program started");
        BadmintonBooking b = new BadmintonBooking();
        assertEquals(90, b.fullPrice());
        Service s = new Locker(b);
        assertEquals(100, s.fullPrice());
        s = new Shower(s);
        assertEquals(115, s.fullPrice());

        assertTrue(s.wrappedBooking instanceof Locker);
        assertTrue(((Locker)s.wrappedBooking).wrappedBooking instanceof BadmintonBooking);

        s = new Shower(b);
        assertEquals(105, s.fullPrice());
        System.out.println("All assertions passed!");        
    }
}

abstract class Booking {
    public abstract int basePrice();
    public abstract int fullPrice();
    public int price;
}

class BadmintonBooking extends Booking {
    @Override public int basePrice() { return 90; }
    @Override public int fullPrice() { return basePrice(); }
}

abstract class Service extends Booking {
    Service(Booking b) {
        wrappedBooking = b;
        this.price += b.price;
    }
    @Override 
    public int fullPrice() { 
        return basePrice() + wrappedBooking.fullPrice(); 
    }
    Booking wrappedBooking;
}

class Shower extends Service {
    Shower(Booking b) {
        super(b);
    }
    @Override public int basePrice() { return 15; }
}

class Locker extends Service {
    Locker(Booking b) {
        super(b);
    }
    @Override
    public int basePrice() { return 10; }
}
