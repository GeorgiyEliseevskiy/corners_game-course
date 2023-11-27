package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.Colors.*;

public class CornersGame {
    private static final int BOARD_SIZE = 8;
    private static int[][] path = new int[120][2];
    private static int length;

    /** Инициализация доски.
     *
     * @param board - игровое поле
     */
    public static void initializeBoard(char[][] board) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row == 0 || row == 1 || row == 2) && (col == 5 || col == 6 || col == 7)) {
                    board[row][col] = 'B';
                } else if ((row == 5 || row == 6 || row == 7) && (col == 0 || col == 1 || col == 2)) {
                    board[row][col] = 'W';
                } else {
                    board[row][col] = ' ';
                }
            }
        }
    }

    /** Проверка, является ли игрок победителем.
     *
     * @param board - игровое поле
     * @param player - игрок
     * @return boolean - результат
     */
    public static boolean isWinner(char[][] board, char player) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == player) {
                    if (isValidMove(board, i, j, i + 1, j, player)
                            // Проверяем возможные ходы вокруг фишки игрока
                            || isValidMove(board, i, j, i, j + 1, player)
                            || isValidMove(board, i, j, i - 1, j, player)
                            || isValidMove(board, i, j, i, j - 1, player)
                            || isValidMove(board, i, j, i + 2, j, player)
                            || isValidMove(board, i, j, i, j + 2, player)
                            || isValidMove(board, i, j, i - 2, j, player)
                            || isValidMove(board, i, j, i, j - 2, player)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /** Метод осуществляющий игру человека с человеком.
     *
     * @param board - игровое поле
     */
    public static void playerVsPlayer(char[][] board) {
        // Инициализируем переменные
        String playerWin;
        int fromRow, fromCol, toRow, toCol;
        char player = 'W'; // Игрок, чей сейчас ход

        // Бесконечный цикл для ходов
        while (true) {
            // Выводим доску
            System.out.println("Now evaluareBoard: " + evaluateBoard(board));
            displayBoard(board);

            // Получаем координаты исходной фишки и ячейки назначения
            System.out.println("Player " + player + " turn: ");
            System.out.print("Enter row and column of piece to move: ");
            Scanner scanner = new Scanner(System.in);
            fromRow = scanner.nextInt();
            fromCol = scanner.nextInt();
            System.out.print("Enter row and column of destination: ");
            toRow = scanner.nextInt();
            toCol = scanner.nextInt();

            // Проверяем, возможен ли такой ход
            if (isValidMove(board, fromRow, fromCol, toRow, toCol, player)) {
                // Если ход возможен, перемещаем фишку
                movePiece(board, fromRow, fromCol, toRow, toCol);

                // Проверяем, есть ли победитель
                if (isWinner(board, player)) {
                    // Если есть, выводим сообщение о победе и выходим из цикла
                    clearScreen();
                    if (evaluateBoard(board) > 0)
                        playerWin = "WHITE";
                    else
                        playerWin = "BLACK";
                    System.out.println("\n\n\t\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\n\n" +
                            "\t        CONGRATULATIONS\n" +
                            "\t   PLAYER " + playerWin + " WINS THIS GAME\n" +
                            "\n\t\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\u256C\n\n");

                    break;
                }

                // Переключаем игрока
                player = (player == 'W') ? 'B' : 'W';
                clearScreen();

            } else {
                // Если ход невозможен, выводим сообщение об ошибке
                clearScreen();
                System.out.println("Invalid move, try again!");
            }
        }
    }

    /** Метод осуществляющий игру человека с компьютером.
     *
     * @param board - игровое поле
     */
    public static void playerVsAI(char[][] board) {
        char player = 'W'; // Игрок ходит белыми фишками
        boolean gameOver = false; // Флаг окончания игры
        int depth = 4; // Глубина поиска в альфа-бета минимаксе
        int maxEval = 1000; // Максимальная оценка
        int alpha = -maxEval; // Альфа (лучший ход для максимизации)
        int beta = maxEval; // Бета (лучший ход для минимизации)

        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            System.out.println("Now evaluateBoard: " + evaluateBoard(board));
            displayBoard(board);

            if (player == 'W') {
                System.out.println("Player " + player + " turn: ");
                System.out.print("Enter row and column of piece to move: ");
                int fromRow = scanner.nextInt();
                int fromCol = scanner.nextInt();
                System.out.print("Enter row and column of destination: ");
                int toRow = scanner.nextInt();
                int toCol = scanner.nextInt();

                if (isValidMove(board, fromRow, fromCol, toRow, toCol, player)) {
                    movePiece(board, fromRow, fromCol, toRow, toCol);

                    if (isWinner(board, player)) {
                        System.out.println("Player " + player + " wins!");
                        gameOver = true;
                    }
                    // После хода белых, ход чёрных
                    player = 'B';
                } else {
                    System.out.println("Invalid move, try again!");
                }
            } else {
                System.out.println("Player Black turn: ");
                int bestScore = Integer.MAX_VALUE;  // Установим начальное значение на максимально возможное
                int bestMoveFromRow = 0, bestMoveFromCol = 0, bestMoveToRow = 0, bestMoveToCol = 0;

                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (board[i][j] == 'B') {
                            ArrayList<Pair<Integer, Integer>> moves = getPossibleMoves(board, i, j);
                            for (Pair<Integer, Integer> move : moves) {
                                char[][] boardCopy = deepCopy(board);
                                movePiece(boardCopy, i, j, move.getFirst(), move.getSecond());
                                int score = alphaBetaMinimax(boardCopy, depth - 1, alpha, beta, false, maxEval, player);

                                if (score < bestScore) {
                                    bestScore = score;
                                    bestMoveFromRow = i;
                                    bestMoveFromCol = j;
                                    bestMoveToRow = move.getFirst();
                                    bestMoveToCol = move.getSecond();
                                }
                            }
                        }
                    }
                }

                movePiece(board, bestMoveFromRow, bestMoveFromCol, bestMoveToRow, bestMoveToCol);
                System.out.println("AI moves from: " + bestMoveFromRow + " " + bestMoveFromCol);
                System.out.println("AI moves to: " + bestMoveToRow + " " + bestMoveToCol);
                if (isWinner(board, 'B')) {  // Проверяем, не победил ли ИИ
                    System.out.println("Черные выиграли!");
                    gameOver = true;
                }

                player = 'W';  // После хода чёрных, ход белых

            }
            clearScreen();
        }

        scanner.close();
    }

    /** Метод для очистки содержимого консоли.
     *
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /** Оценочная функция.
     *
     * @param board - игровое поле
     * @return int - результат работы оценочной функции
     */
    public static int evaluateBoard(char[][] board) {
        // Оценочные веса для каждой ячейки игрового поля для игрока и компьютера
        int[][] boardPlayer = {
                {357, 492, 613, 720, 858, 912, 962, 1008},
                {348, 477, 592, 693, 780, 825, 912, 962},
                {325, 448, 557, 652, 733, 800, 825, 912},
                {288, 405, 508, 597, 672, 733, 780, 858},
                {237, 348, 445, 528, 597, 652, 693, 720},
                {172, 277, 368, 445, 508, 557, 592, 613},
                {93, 192, 277, 348, 405, 448, 477, 492},
                {0, 93, 172, 237, 288, 325, 348, 357}
        };

        int[][] boardAI = {
                {357, 348, 325, 288, 237, 172, 93, 0},
                {492, 477, 448, 405, 348, 277, 192, 93},
                {613, 592, 557, 508, 445, 368, 277, 172},
                {720, 693, 652, 597, 528, 445, 348, 237},
                {858, 780, 733, 672, 597, 508, 405, 288},
                {912, 825, 800, 733, 652, 557, 448, 325},
                {962, 912, 825, 780, 693, 592, 477, 348},
                {1008, 962, 912, 858, 720, 613, 492, 357}
        };

        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                // Добавление веса для фишек игрока
                if (board[i][j] == 'W') {
                    score += (boardPlayer[i][j] + (8 - Math.abs(i) - Math.abs(j - 7)) * 100);
                } else if (board[i][j] == 'B') {
                    // Вычитание веса для фишек компьютера
                    score -= (boardAI[i][j] + (8 - Math.abs(i - 7) - Math.abs(j)) * 100);
                }
            }
        }
        return score;
    }

    /**
     * Метод альфа-бета минимакс для оценки лучшего хода на игровом поле.
     *
     * @param boardCopy          Копия игрового поля.
     * @param depth              Глубина рекурсии.
     * @param alpha              Лучший ход для максимизирующего игрока.
     * @param beta               Лучший ход для минимизирующего игрока.
     * @param maximizingPlayer  Флаг, указывающий, является ли текущий игрок максимизирующим.
     * @param maxEval            Наилучшая оценка.
     * @param player             Текущий игрок.
     * @return                   Оценка лучшего хода с использованием альфа-бета минимакса.
     */
    public static int alphaBetaMinimax(char[][] boardCopy, int depth, int alpha, int beta, boolean maximizingPlayer, int maxEval, char player) {
        // Базовый случай: достигнута максимальная глубина рекурсии или игра завершена
        if (depth == 0 || isGameOver(boardCopy)) {
            int eval = evaluateBoard(boardCopy);
            return eval;
        }
        // Определение текущего игрока
        char currPlayer = maximizingPlayer ? player : (player == 'W' ? 'B' : 'W');
        // Создание списка возможных ходов для текущего игрока
        ArrayList<Pair<Integer, Integer>> possibleMovesFrom = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> possibleMovesTo = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardCopy[i][j] == currPlayer) {
                    ArrayList<Pair<Integer, Integer>> moves = getPossibleMoves(boardCopy, i, j);
                    for (int k = 0; k < moves.size(); k++) {
                        possibleMovesFrom.add(new Pair<>(i, j));
                        possibleMovesTo.add(moves.get(k));
                    }
                }
            }
        }
        // Формирование списка всех возможных ходов в формате пар координат
        ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> possibleMoves = new ArrayList<>();
        for (int i = 0; i < possibleMovesFrom.size(); i++) {
            possibleMoves.add(new Pair<>(possibleMovesFrom.get(i), possibleMovesTo.get(i)));
        }

        // Если нет возможных ходов, возвращается оценка текущего состояния
        if (possibleMoves.isEmpty()) {
            int eval = evaluateBoard(boardCopy);
            return eval;
        }
        // Применение альфа-бета минимакса для оценки лучшего хода
        if (maximizingPlayer) {
            for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move : possibleMoves) {
                char[][] boardCopy2 = deepCopy(boardCopy);
                movePiece(boardCopy2, move.getFirst().getFirst(), move.getFirst().getSecond(), move.getSecond().getFirst(), move.getSecond().getFirst());

                int eval = alphaBetaMinimax(boardCopy2, depth - 1, alpha, beta, false, maxEval, player);

                // Обновление лучшего хода для максимизирующего игрока (alpha)
                if (eval > alpha) {
                    alpha = eval;
                }
                // Обрыв цикла, если beta становится меньше или равно alpha (отсечение)
                if (beta <= alpha) {
                    break;
                }
            }
            // Обновление наилучшей оценки для максимизирующего игрока
            if (alpha > maxEval) {
                maxEval = alpha;
            }
        } else {
            for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move : possibleMoves) {
                char[][] boardCopy2 = deepCopy(boardCopy);
                movePiece(boardCopy2,  move.getFirst().getFirst(), move.getFirst().getSecond(), move.getSecond().getFirst(), move.getSecond().getSecond());

                int eval = alphaBetaMinimax(boardCopy2, depth - 1, alpha, beta, true, maxEval, player);

                // Обновление лучшего хода для минимизирующего игрока (beta)
                if (eval < beta) {
                    beta = eval;
                }

                // Обрыв цикла, если beta становится меньше или равно alpha (отсечение)
                if (beta <= alpha) {
                    break;
                }
            }
            // Обновление наилучшей оценки для минимизирующего игрока
            if (beta < maxEval) {
                maxEval = beta;
            }
        }

        // Возврат наилучшей оценки
        return maxEval;
    }


    /** Метод копирует игровое поле.
     *
     * @param original - игровое поле
     * @return char[][] - копия поля
     */
    private static char[][] deepCopy(char[][] original) {
        char[][] copy = new char[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }


    /** Вывод текущего состояния игрового поле.
     *
     * @param board - игровое поле
     */
    public static void displayBoard(char[][] board) {
        System.out.println(" ");
        System.out.println("   0   1   2   3   4   5   6   7");
        System.out.print(PURPLE_COLOR + " \u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500" +
                "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500" + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510" + RESET_COLOR);

        for (int row = 0; row < 8; row++) {
            System.out.print("\n" + row + PURPLE_COLOR + " \u2502 " + RESET_COLOR);
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == 'B') {
                    System.out.print(BLUE_COLOR + 'X' + RESET_COLOR + PURPLE_COLOR + " \u2502 " + RESET_COLOR);
                } else if (board[row][col] == 'W') {
                    System.out.print(RED_COLOR + 'O' + RESET_COLOR + PURPLE_COLOR + " \u2502 " + RESET_COLOR);
                } else {
                    System.out.print(PURPLE_COLOR + ' ' + PURPLE_COLOR + " \u2502 " + RESET_COLOR);
                }
            }
            if (row < 7) {
                System.out.print(PURPLE_COLOR + "\n \u251C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500" +
                        "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500" + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2524" + RESET_COLOR);
            } else {
                System.out.print(PURPLE_COLOR + "\n \u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500" +
                        "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500" + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518" + RESET_COLOR);
            }
        }
        System.out.println();
    }

    /**
     * Проверяет, является ли ход из указанных координат в целевые координаты допустимым для текущего игрока.
     *
     * @param board  Игровое поле,
     * @param fromRow Строка начальной позиции.
     * @param fromCol Столбец начальной позиции.
     * @param toRow Строка целевой позиции.
     * @param toCol Столбец целевой позиции.
     * @param player Текущий игрок
     * @return  True, если ход допустим, в противном случае - false.
     */
    public static boolean isValidMove(char[][] board, int fromRow, int fromCol, int toRow, int toCol, char player) {
        if (board[fromRow][fromCol] == ' ') {
            return false;
        }

        if (board[fromRow][fromCol] == ' ' && board[toRow][toCol] == ' ') {
            return false;
        }

        if ((fromRow == toRow) && (fromCol == toCol)) {
            return false;
        }

        if (board[fromRow][fromCol] != player) {
            return false;
        }

        if ((player == 'B' && toRow < fromRow) || (player == 'W' && toRow > fromRow)
                || (player == 'W' && toCol < fromCol) || (player == 'B' && toCol > fromCol)) {
            return false;
        }

        if (freeCord(board, toRow, toCol) && nearCord(fromRow, fromCol, toRow, toCol)) {
            return true;
        }

        length = 1;
        path[0][0] = fromRow;
        path[0][1] = fromCol;
        return way(board, fromRow, fromCol, toRow, toCol, -1);
    }

    /** Проверяет, является ли указанная координата свободной на игровом поле.
     *
     * @param board  Игровое поле,
     * @param toRow Строка проверяемой координаты
     * @param toCol Столбец проверяемой координаты.
     * @return True, если координата свободна, в противном случае - false.
     */
    public static boolean freeCord(char[][] board, int toRow, int toCol) {
        return toRow >= 0 && toRow <= 7 && toCol >= 0 && toCol <= 7 && board[toRow][toCol] == ' ';
    }

    /**
     * Проверяет, находятся ли указанные координаты рядом друг с другом на игровом поле.
     *
     * @param fromRow Строка начальной координаты
     * @param fromCol Столбец начальной координаты.
     * @param toRow Строка целевой координаты.
     * @param toCol Столбец целевой координаты.
     * @return True, если координаты находятся рядом, в противном случае - false.
     */
    public static boolean nearCord(int fromRow, int fromCol, int toRow, int toCol) {
        int dx = Math.abs(fromRow - toRow);
        int dy = Math.abs(fromCol - toCol);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    /** Возвращает противоположное направление от указанного.
     *
     * @param direction Направление (0, 1, 2 или 3), где 0 - вверх, 1 - вниз, 2 - влево, 3 - вправо.
     * @return Противоположное направление.
     * @throws IllegalArgumentException Если передано недопустимое направление.
     */
    public static int opposite(int direction) {
        if (direction == 0) {
            return 1;
        } else if (direction == 1) {
            return 0;
        } else if (direction == 2) {
            return 3;
        } else if (direction == 3) {
            return 2;
        }
        // Вернуть значение по умолчанию или бросить исключение, в зависимости от логики
        return -1; // или бросить исключение
    }

    /** Рекурсивно проверяет возможность преодоления пути от начальных координат до целевых координат на игровом поле.
     *
     * @param board       Игровое поле, представленное двумерным массивом.
     * @param fromRow     Строка начальных координат.
     * @param fromCol     Столбец начальных координат.
     * @param toRow       Строка целевых координат.
     * @param toCol       Столбец целевых координат.
     * @param direction   Направление движения (0, 1, 2 или 3), где 0 - вверх, 1 - вниз, 2 - влево, 3 - вправо.
     * @return            True, если существует путь до целевых координат, в противном случае - false.
     */
    public static boolean way(char[][] board, int fromRow, int fromCol, int toRow, int toCol, int direction) {
        for (int i = 0; i < 4; i++) {
            Pair<Integer, Integer> toCoord1 = coord(fromRow, fromCol, i, 2);
            int toRow1 = toCoord1.getFirst();
            int toCol1 = toCoord1.getSecond();

            if (toRow1 < 10 && freeCord(board, toRow1, toCol1)) {
                Pair<Integer, Integer> toCoord2 = coord(fromRow, fromCol, i, 1);
                int toRow2 = toCoord2.getFirst();
                int toCol2 = toCoord2.getSecond();

                if (!freeCord(board, toRow2, toCol2)) {
                    if (toRow1 == toRow && toCol1 == toCol) {
                        return true;
                    } else if (i != direction) {
                        boolean go = true;
                        for (int k = 0; k < length; k++) {
                            if (path[k][0] == toRow1 && path[k][1] == toCol1) {
                                go = false;
                            }
                        }

                        if (go) {
                            path[length][0] = toRow1;
                            path[length][1] = toCol1;
                            length++;
                            boolean repeat = way(board, toRow1, toCol1, toRow, toCol, opposite(i));
                            length--;

                            if (repeat) {
                                return true;
                            }
                            continue;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Вычисляет новые координаты после выполнения прыжка в указанном направлении от начальных координат.
     *
     * @param fromRow  Строка начальных координат.
     * @param fromCol  Столбец начальных координат.
     * @param dir      Направление движения (0, 1, 2 или 3), где 0 - вверх, 1 - вниз, 2 - влево, 3 - вправо.
     * @param jump     Длина прыжка.
     * @return         Объект Pair, содержащий новые координаты (строка, столбец).
     */
    public static Pair<Integer, Integer> coord(int fromRow, int fromCol, int dir, int jump) {
        int toRow = fromRow;
        int toCol = fromCol;

        if (dir == 0) {
            if (fromRow - jump >= 0) {
                toRow = fromRow - jump;
            }
        } else if (dir == 1) {
            if (fromRow + jump <= 7) {
                toRow = fromRow + jump;
            }
        } else if (dir == 2) {
            if (fromCol - jump >= 0) {
                toCol = fromCol - jump;
            }
        } else if (dir == 3) {
            if (fromCol + jump <= 7) {
                toCol = fromCol + jump;
            }
        }

        return new Pair<>(toRow, toCol);
    }

    /**
     * Перемещает фигуру с одной позиции на другую на игровом поле.
     *
     * @param board   Игровое поле, представленное двумерным массивом.
     * @param fromRow Строка начальной позиции.
     * @param fromCol Столбец начальной позиции.
     * @param toRow   Строка целевой позиции.
     * @param toCol   Столбец целевой позиции.
     */
    public static void movePiece(char[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        char piece = board[fromRow][fromCol];
        board[fromRow][fromCol] = ' ';
        board[toRow][toCol] = piece;
    }

    /**
     * Проверка завершения игры.
     *
     * @param board Игровое поле.
     * @return true, если игра завершена (нет возможных ходов для всех фишек), в противном случае - false.
     */
    public static boolean isGameOver(char[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'B' || board[i][j] == 'W') {
                    ArrayList<Pair<Integer, Integer>> moves = getPossibleMoves(board, i, j);
                    if (!moves.isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Возвращает список возможных ходов для фигуры на указанной позиции на игровом поле.
     *
     * @param board    Игровое поле, представленное двумерным массивом.
     * @param fromRow  Строка начальной позиции.
     * @param fromCol  Столбец начальной позиции.
     * @return         Список пар координат (строка, столбец), представляющих возможные ходы для фигуры.
     */
    public static ArrayList<Pair<Integer, Integer>> getPossibleMoves(char[][] board, int fromRow, int fromCol) {
        ArrayList<Pair<Integer, Integer>> moves = new ArrayList<>();
        char player = board[fromRow][fromCol];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isValidMove(board, fromRow, fromCol, i, j, player)) {
                    moves.add(new Pair<>(i, j));
                }
            }
        }

        return moves;
    }
}
