/**
 * TicTacToe Game in Java
 * Hacktoberfest 2025 Contribution 
 * 
 * Features:
 * - Two-player console game (X and O)
 * - Validates moves
 * - Detects win or tie
 * - Uses OOP concepts
 * 
 */

import java.util.Scanner;

public class TicTacToe {

    private char[][] board;      // 3x3 board for Tic-Tac-Toe
    private char currentPlayer;  // Current player symbol: 'X' or 'O'

    // Constructor to initialize the board and set starting player
    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    // Initialize the board with '-' to indicate empty positions
    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    // Print the current state of the board
    private void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Check if the board is full (tie condition)
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // Check if the current player has won
    private boolean checkWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer &&
                board[i][1] == currentPlayer &&
                board[i][2] == currentPlayer) {
                return true;
            }
        }
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == currentPlayer &&
                board[1][j] == currentPlayer &&
                board[2][j] == currentPlayer) {
                return true;
            }
        }
        // Check diagonals
        if (board[0][0] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][2] == currentPlayer) {
            return true;
        }
        if (board[0][2] == currentPlayer &&
            board[1][1] == currentPlayer &&
            board[2][0] == currentPlayer) {
            return true;
        }
        return false;
    }

    // Switch player from X to O or O to X
    private void changePlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    // Place a mark on the board; returns true if move is valid
    private boolean placeMark(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    // Main game loop
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        boolean gameEnded = false;

        System.out.println("Welcome to Tic-Tac-Toe!");
        printBoard();

        while (!gameEnded) {
            System.out.println("Player " + currentPlayer + ", enter your move (row and column: 0,1,2): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // Check if move is valid
            if (!placeMark(row, col)) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            printBoard();

            // Check for win or tie
            if (checkWin()) {
                System.out.println("Congratulations! Player " + currentPlayer + " wins!");
                gameEnded = true;
            } else if (isBoardFull()) {
                System.out.println("The game is a tie!");
                gameEnded = true;
            } else {
                changePlayer(); // Switch turns
            }
        }
        scanner.close();
    }

    // Entry point
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.playGame();
    }
}
