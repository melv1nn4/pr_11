import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean codeIsRunning = true;
        int boardSize = 3;

        while (codeIsRunning) {
            char choice = displayMenu(scanner);
            switch (choice) {
                case '1':
                    startGame(scanner, boardSize);
                    break;
                case '2':
                    boardSize = configureBoardSize(scanner);
                    break;
                case '3':
                    codeIsRunning = exitGame(scanner);
                    break;
                default:
                    System.out.println("Помилка в запиті, спробуйте ще раз");
            }
        }
        System.out.println("Ви закрили гру");
        scanner.close();
    }

    public static char displayMenu(Scanner scanner) {
        System.out.println("Почати гру - (1)");
        System.out.println("Налаштування - (2)");
        System.out.println("Вихід - (3)");
        System.out.println("Введіть сюди ваше число:");
        return scanner.nextLine().charAt(0);
    }

    public static void startGame(Scanner scanner, int boardSize) {
        char[][] board = initializeBoard(boardSize);
        char currentPlayer = 'X';
        boolean gameEnd = false;

        while (!gameEnd) {
            displayBoard(board, boardSize);
            int[] move = getPlayerMove(scanner, board, boardSize);
            if (move == null) break;

            board[move[0]][move[1]] = currentPlayer;
            if (checkWin(board, boardSize, currentPlayer)) {
                displayBoard(board, boardSize);
                System.out.println("Гравець " + currentPlayer + " переміг!");
                gameEnd = true;
            } else if (isBoardFull(board, boardSize)) {
                displayBoard(board, boardSize);
                System.out.println("Нічия!");
                gameEnd = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    public static char[][] initializeBoard(int size) {
        int rows = size * 2 - 1;
        int cols = size * 2 - 1;
        char[][] board = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i % 2 == 1) {
                    board[i][j] = '-';
                } else if (j % 2 == 1) {
                    board[i][j] = '|';
                } else {
                    board[i][j] = ' ';
                }
            }
        }
        return board;
    }

    public static void displayBoard(char[][] board, int size) {
        System.out.print("   ");
        for (int i = 0; i < size; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            if (i % 2 == 0) {
                System.out.print((i / 2 + 1) + " ");
            } else {
                System.out.print("  ");
            }
            System.out.println(board[i]);
        }
    }

    public static int[] getPlayerMove(Scanner scanner, char[][] board, int size) {
        while (true) {
            System.out.println("Введіть рядок і колонку (від 1 до " + size + ") або 2, щоб вийти:");
            String input = scanner.nextLine();
            if (input.equals("2")) return null;

            Scanner inputScanner = new Scanner(input);
            if (inputScanner.hasNextInt()) {
                int row = inputScanner.nextInt() - 1;
                if (inputScanner.hasNextInt()) {
                    int col = inputScanner.nextInt() - 1;
                    if (row >= 0 && row < size && col >= 0 && col < size) {
                        row *= 2;
                        col *= 2;
                        if (board[row][col] == ' ') {
                            return new int[]{row, col};
                        } else {
                            System.out.println("Помилка! Клітинка зайнята, спробуйте ще раз.");
                        }
                    } else {
                        System.out.println("Помилка! Введіть числа від 1 до " + size);
                    }
                }
            }
        }
    }

    public static boolean checkWin(char[][] board, int size, char player) {
        for (int i = 0; i < size; i++) {
            if (checkRow(board, i, size, player) || checkColumn(board, i, size, player)) return true;
        }
        return checkDiagonals(board, size, player);
    }

    public static boolean checkRow(char[][] board, int row, int size, char player) {
        for (int i = 0; i < size; i++) {
            if (board[row * 2][i * 2] != player) return false;
        }
        return true;
    }

    public static boolean checkColumn(char[][] board, int col, int size, char player) {
        for (int i = 0; i < size; i++) {
            if (board[i * 2][col * 2] != player) return false;
        }
        return true;
    }

    public static boolean checkDiagonals(char[][] board, int size, char player) {
        boolean diag1 = true, diag2 = true;
        for (int i = 0; i < size; i++) {
            if (board[i * 2][i * 2] != player) diag1 = false;
            if (board[i * 2][(size - 1 - i) * 2] != player) diag2 = false;
        }
        return diag1 || diag2;
    }

    public static boolean isBoardFull(char[][] board, int size) {
        for (int i = 0; i < size * 2 - 1; i += 2) {
            for (int j = 0; j < size * 2 - 1; j += 2) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    public static int configureBoardSize(Scanner scanner) {
        System.out.println("Оберіть розмір поля: 3x3 (1), 5x5 (2), 7x7 (3), 9x9 (4)");
        System.out.println("Напишіть 2 щоб повернутися до головного меню");
        char input = scanner.nextLine().charAt(0);
        switch (input) {
            case '1': return 3;
            case '2': return 5;
            case '3': return 7;
            case '4': return 9;
            default: return 3;
        }
    }

    public static boolean exitGame(Scanner scanner) {
        System.out.println("Бажаєте вийти? (Введіть 1(так) або 2(ні))");
        return scanner.nextLine().charAt(0) != '1';
    }
}
