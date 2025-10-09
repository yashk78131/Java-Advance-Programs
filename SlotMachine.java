import java.util.*;

public class SlotMachine {
	
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        String[] symbols = {"🍒", "🍋", "🍉", "💎", "⭐", "O", "X"};
        int coins = 10;

        System.out.println("🎰 Welcome to Lucky Slot Machine!");
        System.out.println("You start with 10 coins. Each spin costs 2 coins.\n");

        while (coins >= 2) {
            System.out.print("Press Enter to spin (or type 'exit'): ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            coins -= 2;
            String a = symbols[random.nextInt(symbols.length)];
            String b = symbols[random.nextInt(symbols.length)];
            String c = symbols[random.nextInt(symbols.length)];

            System.out.println("🌀 Spin: " + a + " " + b + " " + c);

            if (a.equals(b) && b.equals(c)) {
                System.out.println("🎉 JACKPOT! You win 10 coins!");
                coins += 10;
            } else if (a.equals(b) || b.equals(c) || a.equals(c)) {
                System.out.println("😄 Two match! You win 4 coins!");
                coins += 4;
            } else {
                System.out.println("😢 No match. Try again!");
            }

            System.out.println("💰 Coins left: " + coins + "\n");
        }

        System.out.println("Game Over! Final Coins: " + coins);
        sc.close();
    }
}
