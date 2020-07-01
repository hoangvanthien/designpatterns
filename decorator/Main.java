package decorator;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program started");
        BadmintonBooking b = new BadmintonBooking();
        assertEquals(90, b.fullPrice());
        Service s = new Locker(b);
        assertEquals(100, s.fullPrice());
        s = new Shower(s);
        assertEquals(115, s.fullPrice());
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
