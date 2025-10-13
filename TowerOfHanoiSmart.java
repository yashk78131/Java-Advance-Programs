import java.util.Scanner;

public class TowerOfHanoiSmart {

    public static int solveHanoi(int n, char source, char auxiliary, char destination) {
        if (n == 0) return 0;

        int moves = solveHanoi(n - 1, source, destination, auxiliary);
        System.out.println("Move disk " + n + " from " + source + " to " + destination);
        moves++;
        moves += solveHanoi(n - 1, auxiliary, source, destination);

        return moves;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of disks: ");
        int n = sc.nextInt();

        System.out.println("\nSteps to solve Tower of Hanoi:");
        int totalMoves = solveHanoi(n, 'A', 'B', 'C');
        System.out.println("\nTotal moves required: " + totalMoves);

        sc.close();
    }
}
