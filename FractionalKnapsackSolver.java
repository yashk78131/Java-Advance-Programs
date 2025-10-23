import java.util.*;

class FractionalKnapsackSolver {

    static class ItemComparator implements Comparator<int[]> {
        public int compare(int[] a, int[] b) {
            double a1 = (1.0 * a[0]) / a[1];
            double b1 = (1.0 * b[0]) / b[1];
            return Double.compare(b1, a1);
        }
    }

    static double fractionalKnapsack(int[] val, int[] wt, int capacity) {
        int n = val.length;
        int[][] items = new int[n][2];
        for (int i = 0; i < n; i++) {
            items[i][0] = val[i];
            items[i][1] = wt[i];
        }

        Arrays.sort(items, new ItemComparator());

        double res = 0.0;
        int currentCapacity = capacity;

        for (int i = 0; i < n; i++) {
            if (items[i][1] <= currentCapacity) {
                res += items[i][0];
                currentCapacity -= items[i][1];
            } else {
                res += (1.0 * items[i][0] / items[i][1]) * currentCapacity;
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of items: ");
        int n = sc.nextInt();

        int[] val = new int[n];
        int[] wt = new int[n];

        System.out.println("Enter the values of the items:");
        for (int i = 0; i < n; i++) {
            val[i] = sc.nextInt();
        }

        System.out.println("Enter the weights of the items:");
        for (int i = 0; i < n; i++) {
            wt[i] = sc.nextInt();
        }

        System.out.print("Enter the capacity of the knapsack: ");
        int capacity = sc.nextInt();

        double result = fractionalKnapsack(val, wt, capacity);
        System.out.println("Maximum value in knapsack = " + result);

        sc.close();
    }
}
