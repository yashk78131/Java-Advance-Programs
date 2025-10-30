import java.util.Scanner;
public class MatrixChainMultiplicationUserInput {

    // Recursive function with memoization
    static int matrixChainOrder(int[] p, int i, int j, int[][] dp) {
        // Base case: single matrix
        if (i == j)
            return 0;

        if (dp[i][j] != -1)
            return dp[i][j];
        dp[i][j] = Integer.MAX_VALUE;

        // Try placing parenthesis at every possible position and find the one with minimum cost
        for (int k = i; k < j; k++) {
            int cost = matrixChainOrder(p, i, k, dp)
                     + matrixChainOrder(p, k + 1, j, dp)
                     + p[i - 1] * p[k] * p[j];

            dp[i][j] = Math.min(dp[i][j], cost);
        }

        return dp[i][j];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Matrix Chain Multiplication Problem");
        System.out.print("Enter number of matrices: ");
        int n = sc.nextInt();

        int[] p = new int[n + 1];

        System.out.println("\nEnter the dimensions:");
        for (int i = 0; i <= n; i++) {
            System.out.print("p[" + i + "] = ");
            p[i] = sc.nextInt();
        }

        int[][] dp = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++)
            for (int j = 0; j <= n; j++)
                dp[i][j] = -1;

        // Compute minimum multiplication cost
        int minCost = matrixChainOrder(p, 1, n, dp);
        System.out.println("\nMinimum number of multiplications: " + minCost);

        sc.close();
    }
}
