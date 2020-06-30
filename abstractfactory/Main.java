package abstractfactory;

/**
 * 
 * There are 2 types of bookings: Zumba and Badminton,
 * each can be categorized as Premium or Standard booking
 * 
 * a Zumba Booking has a classRoom field
 * a Badminton Booking has a courtRoom field
 * a Premium Booking has a phone field
 * a Standard Booking has an email field
 * 
 * (thus, e.g., a zumba standard booking has a classRoom field and an email field)
 * 
 * Thus there are 4 types in total
 * We have 4 classes for that: 
 *      BadmintonPremiumBooking
 *      BadmintonStandardBooking
 *      ZumbaPremiumBooking
 *      ZumbaStandardBooking
 * Suppose that we already have two factories:
 *      PremiumFactory: produce either a BadmintonPremiumBooking or a ZumbaPremiumBooking
 *      StandardFactory: produce either a BadmintonStandardBooking or a ZumbaStandardBooking
 * 
 * Basic idea: We want to construct an AbstractFactory to produce either a PremiumFactory or a StandardFactory
 * 
 */

public class Main {
    public static void main(String[] args) {
        Booking b = AbstractFactory.getFactory("Premium").build("Zumba", "ABC123", 10, "0386315741");
        assert (b instanceof ZumbaPremiumBooking);
        System.out.println("All assertions passed!");
    }
}

abstract class AbstractFactory {
    static AbstractFactory getFactory(String type) {
        switch (type) {
            case "Premium": return PremiumFactory.getFactory();
            case "Standard": return StandardFactory.getFactory();
            default: return null;
        }
    }
    abstract Booking build(String type, String id, int room, String info);
}

class PremiumFactory extends AbstractFactory {
    private static PremiumFactory self;
    private PremiumFactory() {}
    static PremiumFactory getFactory() {
        if (self == null) {
            self = new PremiumFactory();
        }
        return self;
    }
    Booking build(String type, String id, int room, String info) {
        switch (type) {
            case "Badminton": {
                BadmintonPremiumBooking b = new BadmintonPremiumBooking();
                b.id = id;
                b.courtRoom = room;
                b.phone = info;
                return b;
            }
            case "Zumba": {
                ZumbaPremiumBooking b = new ZumbaPremiumBooking();
                b.id = id;
                b.classRoom = room;
                b.phone = info;
                return b;
            }
            default: return null;
        }
    }
}

class StandardFactory extends AbstractFactory {
    private static StandardFactory self;
    private StandardFactory() {}
    static StandardFactory getFactory() {
        if (self == null) {
            self = new StandardFactory();
        }
        return self;
    }
    Booking build(String type, String id, int room, String info) {
        switch (type) {
            case "Badminton": {
                BadmintonStandardBooking b = new BadmintonStandardBooking();
                b.id = id;
                b.courtRoom = room;
                b.email = info;
                return b;
            }
            case "Zumba": {
                ZumbaStandardBooking b = new ZumbaStandardBooking();
                b.id = id;
                b.classRoom = room;
                b.email = info;
                return b;
            }
            default: return null;
        }
    }
}

abstract class Booking {
    public String id;
}

class BadmintonBooking extends Booking {
    public int courtRoom;
}

class ZumbaBooking extends Booking {
    public int classRoom;
}

class BadmintonPremiumBooking extends BadmintonBooking {
    public String phone;
}

class BadmintonStandardBooking extends BadmintonBooking {
    public String email;
}

class ZumbaPremiumBooking extends ZumbaBooking {
    public String phone;
}

class ZumbaStandardBooking extends ZumbaBooking {
    public String email;
}
