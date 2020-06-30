package adapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * You have a class BadmintonBooking, a class ZumbaBooking, both can extend the end time, 
 * but in different ways (`extendTo`, `extendBy`, respectively)
 * You have a complicated function `updateBookingInfo` laying around somewhere,
 * which ONLY accepts BadmintonBooking
 * You know that this function will use the method `extendTo` in BadmintonBooking
 * You cannot change any class, nor the function `updateBookingInfo`
 * 
 * You need to find some way to use this `updateBookingInfo` function with a ZumbaBooking
 * Solution: Create an adapter that makes ZumbaBooking behave more or less like a BadmintonBooking
 * ===> Make the adapter a subclass of BadmintonBooking
 * 
 * The adapter overrides `extendTo`, in which it will call the `ZumbaBooking.extendBy`
 * - CONVERT the input of `extendTo` into a suitable format for `extendBy`
 * - REUSE `extendBy` on the converted data
 * 
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BadmintonBooking b1 = new BadmintonBooking();
        b1.id = "ABC123";
        b1.end = LocalDateTime.of(2020, 7, 1, 10, 0, 0);
        updateBookingInfo(b1, 2020, 7, 1, 12, 0, 0); // alles gut

        ZumbaBooking b2 = new ZumbaBooking();
        b2.id = "DEF456";
        b2.end = LocalDateTime.of(2020, 7, 1, 10, 0, 0);
        // updateBookingInfo(b2); // compile error

        // Use Adapter to make a Zumba Booking fit in the slot of a Badminton Booking:
        updateBookingInfo(new ZumbaBookingAdapter(b2), 2020, 7, 1, 12, 0, 0);
        assert b2.end.getHour() == 12;

        System.out.println("All assertions passed!");
    }

    public static void updateBookingInfo(BadmintonBooking b, int jahr, int monat, int tag, int uhr, int minute, int sekund) {
        System.out.println("Booking " + b.id + " is being updated...");
        b.extendTo(LocalDateTime.of(jahr, monat, tag, uhr, minute, sekund));
        System.out.println("Updated successfully. New time: " + b.end);
    }
}

abstract class Booking {
    public String id;
    public LocalDateTime start, end;
}

class ZumbaBooking extends Booking {
    public void extendBy(int min) {
        end = end.plus(min, ChronoUnit.MINUTES);
    }
}

class BadmintonBooking extends Booking {
    public void extendTo(LocalDateTime newEnd) {
        end = newEnd;
    }
}

class ZumbaBookingAdapter extends BadmintonBooking {
    ZumbaBookingAdapter(ZumbaBooking zumbaBooking) {
        this.end = zumbaBooking.end;
        this.start = zumbaBooking.start;
        this.id = zumbaBooking.id;
        this.zumbaBooking = zumbaBooking;
    }
    @Override
    public void extendTo(LocalDateTime newEnd) {
        int minutes = (int)zumbaBooking.end.until(newEnd, ChronoUnit.MINUTES);
        zumbaBooking.extendBy(minutes);
        this.end = this.zumbaBooking.end;
    }
    ZumbaBooking zumbaBooking;
}
