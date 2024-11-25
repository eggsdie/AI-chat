package data_access;

import java.util.Random;
import java.util.Scanner;

public class DemoMessagingApiCalls {

    private static DemoRestfulApi api = new DemoRestfulApi();
    private static String currentUserId;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the Social Media App!");
            System.out.println("1. Create a New Account");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    logIn();
                    break;
                case 3:
                    System.out.println("Exiting the application...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createNewAccount() {
        System.out.println("\nCreate a New Account");
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your Username: ");
        String userName = scanner.nextLine();
        System.out.print("Enter your Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter your Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your Last Name: ");
        String lastName = scanner.nextLine();

        String response = api.createNewUser(userId, userName, password, email, firstName, lastName);
        System.out.println("Account created successfully!\n" + response);
    }

    private static void logIn() {
        System.out.println("\nLog In");
        System.out.print("Enter your User ID: ");
        String userId = scanner.nextLine();
        currentUserId = userId; // Store the current user ID for future actions
        System.out.println("Logged in as: " + currentUserId);

        userMenu();
    }

    private static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. Send a Message");
            System.out.println("3. Log Out");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    sendMessage();
                    break;
                case 3:
                    System.out.println("Logging out...");
                    currentUserId = null;
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void viewAllUsers() {
        String users = api.getAllUsers();
        System.out.println("\nUsers List:\n" + users);
        System.out.println("Select a user to send a message.");
    }

    private static void sendMessage() {
        System.out.print("Enter the recipient's User ID: ");
        String receiverId = scanner.nextLine();
        System.out.print("Enter your message: ");
        String messageContent = scanner.nextLine();

        String messageId = generateMessageId();
        String response = api.createNewMessage(messageId, messageContent, currentUserId, receiverId);
        System.out.println("Message sent!\n" + response);
    }

    private static String generateMessageId() {
        Random random = new Random();
        int randomNumber = random.nextInt(1000) + 1;
        // Generate a simple message ID (for example, based on current time)
        return randomNumber + "";
    }
}
