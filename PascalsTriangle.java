// Object-Oriented version of Pascal’s Triangle

import java.util.Scanner;

class Triangle {
    private int rows;

    // Constructor
    public Triangle(int rows) {
        this.rows = rows;
    }

    // Method to generate and display Pascal's Triangle
    public void displayTriangle() {
        System.out.println("\n✨ Pascal's Triangle (" + rows + " rows) ✨\n");

        for (int i = 0; i < rows; i++) {
            // For alignment
            for (int space = 0; space < rows - i; space++) {
                System.out.print(" ");
            }

            int num = 1; // Start with 1 in every row
            for (int j = 0; j <= i; j++) {
                System.out.print(num + " ");
                num = num * (i - j) / (j + 1); // Generate next number
            }

            System.out.println();
        }
    }
}

public class PascalsTriangle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows for Pascal's Triangle: ");
        int n = sc.nextInt();

        Triangle triangle = new Triangle(n);
        triangle.displayTriangle();

        sc.close();
    }
}
