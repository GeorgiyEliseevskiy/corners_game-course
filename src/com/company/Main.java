package com.company;

import java.util.Scanner;

import static com.company.CornersGame.*;

public class Main {
    private static final int BOARD_SIZE = 8;
    private static int[][] path = new int[120][2];
    private static int length;
    public static void main(String[] args) {
        char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard(board);

        System.out.println("\n================== Welcome to the Corners Game! =================\n");

        System.out.println("Select the game mode\n\n");
        System.out.println("1. Play with the AI\n2. Play with a Friend\n3. Exit the game\n");

        Scanner scanner = new Scanner(System.in);
        char choice = scanner.next().charAt(0);

        if (choice == '1') {
            char player1 = 'W';
            char player2 = 'B';
            playerVsAI(board);
        } else if (choice == '2') {
            char player1 = 'W';
            char player2 = 'B';
            playerVsPlayer(board);
        } else {
            return;
        }
    }
}
