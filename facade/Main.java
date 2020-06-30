package facade;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Facade is basically a class that contains a bunch of functions which you don't know where else to put
 * These functions are helpful, take care of little things that you may forget 
 * if you implement the functionalities on your own
 * 
 * For example, in order to extend a Booking end time by one week, it is required to refund the old booking
 * and pay for the new booking (because... let's say we have to change the price)
 * 
 * Compared to Adapter, Facade provides a new functionality, while Adapter still deals with the same functionality
 * but it a different input format (Adapter's main focus is to convert data in suitable format)
 * 
 * They are similar in the sense that both reuse existing methods
 */

public class Main {
    public static void main(String[] args) {
        Booking b = new Booking();
        Card c = new Card();
        b.card = c;
        b.price = 90;
        b.start = LocalDateTime.of(2020, 7, 1, 8, 0, 0);
        b.end = LocalDateTime.of(2020, 7, 1, 9, 0, 0);
        c.balance = 1000; 

        ExtendOneWeek.extend(b);
    }
}

class Booking {
    public LocalDateTime start, end;
    public Card card;
    public int price;
    void pay() {
        card.add(-price);
    }
    void refund() {
        card.add(price);
    }
}

class Card {
    public int balance;
    public void add(int amount) {        
        balance += amount;
        System.out.println(amount + " has been added to card.");
    }
}

class ExtendOneWeek {
    public static void extend(Booking b) {
        b.refund();
        b.end = b.end.plus(1, ChronoUnit.WEEKS);
        b.price += 70;
        b.pay();
    }
}
