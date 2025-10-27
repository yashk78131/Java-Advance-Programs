import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    public static void main(String[] args) {
        // Create a new Random object for generating numbers
        Random random = new Random();
        // Generate a random number between 1 and 100
        int secretNumber = random.nextInt(100) + 1;
        
        // Create a new Scanner object for reading user input
        Scanner scanner = new Scanner(System.in);
        
        int guess;
        int attempts = 0;
        boolean hasGuessed = false;
        
        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I have picked a number between 1 and 100. Try to guess it!");
        
        while (!hasGuessed) {
            System.out.print("Enter your guess: ");
            
            // Check if the input is an integer to prevent errors
            if (scanner.hasNextInt()) {
                guess = scanner.nextInt();
                attempts++;
                
                if (guess < secretNumber) {
                    System.out.println("Too low! Try again.");
                } else if (guess > secretNumber) {
                    System.out.println("Too high! Try again.");
                } else {
                    System.out.printf("Congratulations! You guessed the number %d in %d attempts!%n", secretNumber, attempts);
                    hasGuessed = true; // Set to true to exit the loop
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input to avoid an infinite loop
            }
        }
        
        scanner.close();
    }
}
