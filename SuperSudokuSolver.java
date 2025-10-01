// SudokuSolver
// Description-Java program to solve a Sudoku puzzle using backtracking with user input

import java.util.Scanner;

public class SudokuSolver {

    private static final int SIZE = 9; // Sudoku grid size

    // Print Sudoku gridd
    public static void printGrid(int[][] grid) {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }
    }

    // Chec if placing num is safe
    public static boolean isSafe(int[][] grid, int row, int col, int num) {
        // Row and column check
        for (int x = 0; x < SIZE; x++) {
            if (grid[row][x] == num || grid[x][col] == num) {
                return false;
            }
        }

        // 3x3 box chec
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[r + startRow][c + startCol] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    // Backtrackingsolver
    public static boolean solveSudoku(int[][] grid, int row, int col) {
        if (row == SIZE - 1 && col == SIZE) {
            return true; // Solved
        }

        if (col == SIZE) {
            row++;
            col = 0;
        }

        if (grid[row][col] != 0) {
            return solveSudoku(grid, row, col + 1);
        }

        for (int num = 1; num <= SIZE; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;
                if (solveSudoku(grid, row, col + 1)) {
                    return true;
                }
                grid[row][col] = 0; // Backtrack
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] puzzle = new int[SIZE][SIZE];

        System.out.println("Enter Sudoku puzzle row by row (9 numbers each, use 0 for empty):");

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                puzzle[i][j] = sc.nextInt();
            }
        }

        System.out.println("\nOriginal Sudoku Puzzle:");
        printGrid(puzzle);

        if (solveSudoku(puzzle, 0, 0)) {
            System.out.println("\nSolved Sudoku Puzzle:");
            printGrid(puzzle);
        } else {
            System.out.println("No solution exists.");
        }

        sc.close();
    }
}
