import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };
    private static char currentPlayer = 'X';
    private static boolean playerVsPlayer = false;
    //
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        //
        System.out.println("Выберите режим:");
        System.out.println("1. Игрок против бота");
        System.out.println("2. Игрок против игрока");
        System.out.print("Ваш выбор - ");
        //
        int choice = scan.nextInt();
        playerVsPlayer = (choice == 2);
        //
        printBoard();
        playGame();
    }
    //
    private static void playGame() {
        while (true) {
            if (currentPlayer == 'X' || playerVsPlayer) {
                playerMove();
            } else {
                computerMove();
            }
            //
            printBoard();
            //
            if (checkWinner()) {
                System.out.println(currentPlayer + " победил!");
                break;
            }
            //
            if (isBoardFull()) {
                System.out.println("Ничья!");
                break;
            }
            //
            switchPlayer();
        }
    }
    //
    private static void playerMove() {
        Scanner scan = new Scanner(System.in);
        int row, col;
        do {
            System.out.print("Ход игрока " + currentPlayer + ". Введите номер строки (1-3) и столбца (1-3), разделенные пробелом: ");
            row = scan.nextInt() - 1;
            col = scan.nextInt() - 1;
        } while (!isValidMove(row, col));
        board[row][col] = currentPlayer;
    }
    //
    private static void computerMove() {
        if (playerVsPlayer) {
            currentPlayer = 'O';
            playerMove();
            currentPlayer = 'X';
        } else {
            // Простая реализация минимакс-алгоритма для выбора оптимального хода
            int[] bestMove = minimax(2, currentPlayer);
            board[bestMove[0]][bestMove[1]] = currentPlayer;
        }
    }
    //
    private static int[] minimax(int depth, char player) {
        int[] bestMove = {-1, -1};
        int bestScore = (player == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        //
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(depth - 1, player == 'X' ? 'O' : 'X');
                    board[i][j] = ' ';
                    //
                    if ((player == 'X' && score > bestScore) || (player == 'O' && score < bestScore)) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        return bestMove;
    }
    //
    private static int minimaxHelper(int depth, char player) {
        if (checkWinner()) {
            return (player == 'X') ? -1 : 1;
        }
        //
        if (isBoardFull()) {
            return 0;
        }
        //
        int bestScore = (player == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        //
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(depth - 1, player == 'X' ? 'O' : 'X');
                    board[i][j] = ' ';
                    //
                    if ((player == 'X' && score > bestScore) || (player == 'O' && score < bestScore)) {
                        bestScore = score;
                    }
                }
            }
        }
        return bestScore;
    }
    //
    private static boolean isValidMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
            return true;
        }
        System.out.println("Некорректный ход. Попробуйте снова.");
        return false;
    }
    //
    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
    //
    private static boolean checkWinner() {
        // Проверка по строкам, столбцам и диагоналям
        return (checkRows() || checkColumns() || checkDiagonals());
    }
    //
    private static boolean checkRows() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }
    //
    private static boolean checkColumns() {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }
    //
    private static boolean checkDiagonals() {
        return (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
                (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }
    //
    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    //
    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
}
