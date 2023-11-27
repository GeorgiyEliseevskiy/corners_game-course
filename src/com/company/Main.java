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

        char choice = inputIsCorrect();

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

    public static char inputIsCorrect() {
        Scanner scanner = new Scanner(System.in);
        String answerForPanel = scanner.nextLine();
        try {
            if (Character.isDigit(answerForPanel.charAt(0))) {
                return answerForPanel.charAt(0);
            } else {
                while (!Character.isDigit(answerForPanel.charAt(0))) {
                    System.out.println("Please, input the digit");
                    answerForPanel = scanner.nextLine();
                }
                return answerForPanel.charAt(0);
            }
        } catch (StringIndexOutOfBoundsException c) {
            System.out.println("bad data");
        }
        return '9';
    }

    public static int inputIntWithValidation() {
        Scanner scanner = new Scanner(System.in);
        int value = 0;
        boolean inputValid = false;

        while (!inputValid) {
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                inputValid = true;
            } else {
                System.out.println("Please, input the digit:");
                scanner.nextLine();
            }
        }

        return value;
    }
}
