/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.udea.edu.batallaNaval.modelo;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Vanesa
 */
public class Tablero {

    public static void main(String[] args) {
        Tablero t = new Tablero();
        System.out.println(Arrays.deepToString(t.getBoard()));
        int[][] b = t.getBoard();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(b[i][j] + " ");

            }
            System.out.println("");
        }

    }
    private int board[][];

    public Tablero() {
        board = new int[10][10];
        ubicarBarcos();
    }

    public int[][] getBoard() {
        return board;
    }

    public int disparo(int x, int y) {
        int puntaje = 0;
        if (board[x][y] == 0) {
            board[x][y] = 2;

        } else if (board[x][y] == 1) {
            board[x][y] = 3;
            puntaje = 10;
        }
        return puntaje;
    }

    public void ubicarBarcos() {
        int cantBarcos[] = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        int actual = 0;
        boolean posible;
        int x, y, orientacion;
        while (actual < cantBarcos.length) {
            posible = true;
            x = ThreadLocalRandom.current().nextInt(0, 10);
            y = ThreadLocalRandom.current().nextInt(0, 10);
            orientacion = ThreadLocalRandom.current().nextInt(0, 4);
            switch (orientacion) {
                case 0:
                    if ((x + cantBarcos[actual]) <= 10) {
                        for (int i = x; i < x + cantBarcos[actual]; i++) {
                            if (board[i][y] != 0) {
                                posible = false;
                            }
                        }
                    } else {
                        posible = false;
                    }
                    break;
                case 1:
                    if ((x - cantBarcos[actual]) >= -1) {
                        for (int i = x; i > x - cantBarcos[actual]; i--) {
                            if (board[i][y] != 0) {
                                posible = false;
                            }
                        }
                    } else {
                        posible = false;
                    }

                    break;
                case 2:
                    if ((y + cantBarcos[actual]) <= 10) {
                        for (int i = y; i < y + cantBarcos[actual]; i++) {
                            if (board[x][i] != 0) {
                                posible = false;
                            }
                        }
                    } else {
                        posible = false;
                    }
                    break;
                case 3:
                    if ((y - cantBarcos[actual]) >= -1) {
                        for (int i = y; i > y - cantBarcos[actual]; i--) {
                            if (board[x][i] != 0) {
                                posible = false;
                            }
                        }
                    } else {
                        posible = false;
                    }

                    break;
            }
            if (posible) {
                switch (orientacion) {
                    case 0:
                        for (int i = x; i < x + cantBarcos[actual]; i++) {
                            board[i][y] = 1;
                        }
                        break;
                    case 1:
                        for (int i = x; i > x - cantBarcos[actual]; i--) {
                            board[i][y] = 1;
                        }
                        break;
                    case 2:
                        for (int i = y; i < y + cantBarcos[actual]; i++) {
                            board[x][i] = 1;
                        }
                        break;

                    case 3:
                        for (int i = y; i > y - cantBarcos[actual]; i--) {
                            board[x][i] = 1;
                        }
                        break;
                }
                actual++;
            }

        }

    }

}
