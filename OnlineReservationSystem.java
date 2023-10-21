import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Reservation
{
    private Long id;
    private String reservationAt;
    private Date reservationDate;
    private String reservationFor;

    public Reservation()
    {

    }
    public Reservation(Long id, String reservationAt, Date reservationOn, String reservationFor) {
        this.id = id;
        this.reservationAt = reservationAt;
        this.reservationDate = reservationOn;
        this.reservationFor = reservationFor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationAt() {
        return reservationAt;
    }

    public void setReservationAt(String reservationAt) {
        this.reservationAt = reservationAt;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationFor() {
        return reservationFor;
    }

    public void setReservationFor(String reservationFor) {
        this.reservationFor = reservationFor;
    }

}


class ReservationSystem {
    private HashMap<String, User> users;
    private HashMap<Long,Reservation> reservations;
    private Long id;
    private Scanner scanner;

    public ReservationSystem() {
        users = new HashMap<>();
        // Initialize some users (you can add more)
        users.put("user1", new User("user1", "password1"));
        users.put("user2", new User("user2", "password2"));

        scanner=new Scanner(System.in);

        reservations=new HashMap<>();

        id= 0L;
    }



    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public void makeReservation(String username) {
        // Implement reservation logic here
        String reservationAt;
        Date reservationDate = null;
        System.out.println("Hey, "+username+"... Please fill below details.");
        System.out.print("Reservation At : ");
        reservationAt= scanner.nextLine();
        System.out.print("Reservation On ( YYYY-MM-DD ) : ");
        try
        {
            reservationDate=new SimpleDateFormat("yyyy-MM-dd").parse(scanner.nextLine());
            Reservation reservation=new Reservation(++id,reservationAt,reservationDate,username);
            reservations.put(reservation.getId(),reservation);

            System.out.println("Reservation Successful");
            printReservation(reservation);
        }
        catch (ParseException parseException)
        {
            System.out.println("Invalid Date format, date should be YYYY-MM-DD (2023-10-23)");
        }


    }

    public void cancelReservation(String username) {
        // Implement cancellation logic here
        Long id;
        System.out.print("Enter the reservation ID to cancel : ");
        id=scanner.nextLong();
        if(reservations.containsKey(id) && reservations.get(id).getReservationFor().equals(username))
        {
            Reservation reservation=reservations.get(id);
            reservations.remove(id);
            System.out.println("Reservation Cancelled Successfully.");
            printReservation(reservation);
        }
        else
        {
            System.out.println("Couldn't find your reservation for given Reservation ID : "+id);
        }

    }

    public void printAllActiveReservation(String username)
    {
        if(reservations.values().stream().anyMatch(reservation -> reservation.getReservationFor().equals(username)))
        {
            reservations.values().stream().filter(reservation -> reservation.getReservationFor().equals(username)).forEach(this::printReservation);
        }
        else
        {
            System.out.println("No reservation found for you.");
        }
    }

    private void printReservation(Reservation reservation)
    {
        System.out.println("\n\n========================== Reservation Details ===============================");
        System.out.println("Reservation ID : "+reservation.getId());
        System.out.println("Reservation At : "+reservation.getReservationAt());
        System.out.println("Reservation Date : "+reservation.getReservationDate());
        System.out.println("Reservation by : "+reservation.getReservationFor());
        System.out.println("==============================================================================");
    }
}
public class OnlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationSystem reservationSystem = new ReservationSystem();

        System.out.println("Welcome to the Online Reservation System!");

        // Login Page
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (reservationSystem.login(username, password)) {
            System.out.println("Login successful!");
            boolean exit = false;
            while (!exit) {
                // Reservation Menu
                System.out.println("\nSelect an option:");
                System.out.println("1. Make a reservation");
                System.out.println("2. Cancel a reservation");
                System.out.println("3. Print my reservations");
                System.out.println("4. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        // Redirect TO Reservation method
                        reservationSystem.makeReservation(username);
                        break;
                    case 2:
                        // Redirect TO Cancellation method
                        reservationSystem.cancelReservation(username);
                        break;
                    case 3:
                        // print all reservation method
                        reservationSystem.printAllActiveReservation(username);
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Invalid username or password. Login failed.");
        }

        System.out.println("Thank you for using the Online Reservation System!");
    }
}

