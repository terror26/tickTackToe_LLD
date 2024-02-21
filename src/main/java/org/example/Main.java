package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        new Main().driver();
    }

    private void driver() {
        int size = 3;
        Player p1 = new Player(PlayingSign.X, "Kanishk");
        Player p2 = new Player(PlayingSign.O, "Anmol");
        Deque<Player> players = new ArrayDeque<>();
        players.add(p1);
        players.add(p2);
        Game game = new Game(size, players);
        System.out.println("won by"+ game.startGame());;
    }

    public class Game {
        PlayingSign[][] board;
        int size;
        Deque<Player> players;

        public Game(int size, Deque<Player> players) {
            this.size = size;
            board = new PlayingSign[size][size];
            this.players = players;
        }

        Scanner sc = new Scanner(System.in);
        public String startGame() {
            int positions = size*size;
            while(positions>0) {
                System.out.println("Enter the location x,y to place your piece");
                int x = sc.nextInt();
                int y = sc.nextInt();
                if(board[x][y] != null) { // occupied try again
                    System.out.println("Oops already occupied, try again");
                    continue;
                }
                Player player = players.poll();
                positions--;
                board[x][y] = player.playingSign;
                printBoard();
                players.addLast(player);
                if(isThereWinner(x,y,player.playingSign)) {
                    return player.name;
                }
                // chance to next player
            }
            return "tie";
        }

        private void printBoard() {
            for(int i = 0;i<size;i++) {
                for(int j = 0;j<size;j++) {
                    System.out.print(board[i][j] +   "    |   ");
                }
                System.out.println();
            }
        }

        public boolean isThereWinner(int row, int column, PlayingSign pieceType) {

            boolean rowMatch = true;
            boolean columnMatch = true;
            boolean diagonalMatch = true;
            boolean antiDiagonalMatch = true;

            //need to check in row
            for(int i=0;i<size;i++) {

                if(board[row][i] == null || board[row][i] != pieceType) {
                    rowMatch = false;
                }
            }

            //need to check in column
            for(int i=0;i<size;i++) {

                if(board[i][column] == null || board[i][column] != pieceType) {
                    columnMatch = false;
                }
            }

            //need to check diagonals
            for(int i=0, j=0; i<size;i++,j++) {
                if (board[i][j] == null || board[i][j] != pieceType) {
                    diagonalMatch = false;
                }
            }

            //need to check anti-diagonals
            for(int i=0, j=size-1; i<size;i++,j--) {
                if (board[i][j] == null || board[i][j] != pieceType) {
                    antiDiagonalMatch = false;
                }
            }

            return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
        }

    }

    public enum PlayingSign {
        X, O;
    }

    class Player {
        PlayingSign playingSign;
        String name;

        public Player(PlayingSign sign, String name) {
            this.playingSign = sign;
            this.name = name;
        }
    }

}