import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {

    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS = 10;
    
    // Main method where the game execution starts
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain;

        System.out.println("🎉 Welcome to Guess The Number! 🎉");
        System.out.println("I will pick a number between " + MIN_RANGE + " and " + MAX_RANGE + ".");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it.");
        System.out.println("----------------------------------------");

        // Main game loop (do-while ensures the game runs at least once)
        do {
            int targetNumber = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
            int attemptsUsed = 0;
            boolean hasGuessedCorrectly = false;

            System.out.println("\nI've picked a new number. Start guessing!");

            // Attempt loop
            while (attemptsUsed < MAX_ATTEMPTS) {
                System.out.print("Attempt " + (attemptsUsed + 1) + "/" + MAX_ATTEMPTS + ". Enter your guess: ");
                
                // Input validation
                if (!scanner.hasNextInt()) {
                    System.out.println("🚨 Invalid input. Please enter a number.");
                    scanner.next(); // Consume the invalid token
                    continue; 
                }
                
                int guess = scanner.nextInt();
                attemptsUsed++;

                if (guess < MIN_RANGE || guess > MAX_RANGE) {
                    System.out.println("❌ Your guess is out of range. Stick between " + MIN_RANGE + " and " + MAX_RANGE + ".");
                    // Don't count this as a meaningful attempt
                    attemptsUsed--; 
                    continue;
                }

                if (guess == targetNumber) {
                    hasGuessedCorrectly = true;
                    break;
                } else if (guess < targetNumber) {
                    System.out.println("⬇️ Too low! Guess higher.");
                } else {
                    System.out.println("⬆️ Too high! Guess lower.");
                }
            }

            // Game end message
            if (hasGuessedCorrectly) {
                System.out.println("\n👑 CONGRATULATIONS! You guessed the number " + targetNumber + " in " + attemptsUsed + " attempts!");
            } else {
                System.out.println("\n😔 GAME OVER. You ran out of attempts.");
                System.out.println("The number I was thinking of was " + targetNumber + ".");
            }

            // Ask to play again
            System.out.print("\nDo you want to play again? (yes/no): ");
            String playChoice = scanner.next().toLowerCase();
            playAgain = playChoice.startsWith("y");

        } while (playAgain);

        System.out.println("\nThanks for playing! Goodbye. 👋");
        scanner.close();
    }
}
