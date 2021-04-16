package control;

import model.InformationWays;
import view.Board;

import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.util.*;

public class Heuristic {

    private static final Heuristic instance = new Heuristic();
    private static final Board board = Board.getBoard();

    /**
     * @param valueBoard bảng 2d
     * @param idPlayer   số hiệu
     */
    public int think(int[][] valueBoard, int idPlayer) {

        ArrayList<InformationWays> list = listWays(valueBoard, idPlayer);

        if (list.size() == 0) {
            return 0;
        }

//        System.out.println(list.toString());

//        for (int i = 0; i < valueBoard.length; i++) {
//            for (int j = 0; j < valueBoard[0].length; j++) {
//                System.out.print(valueBoard[i][j]);
//            }
//            System.out.println();
//        }

        int bestMove = Integer.MIN_VALUE;
//        System.out.println("idPlayer = " + idPlayer);
        for (int i = 0; i < list.size(); i++) {
            int[] cnt = cntNumber(list.get(i));
//            System.out.println("x , y = " + list.get(i).getX() + " " + list.get(i).getY());
//            for (int j = 1; j <= 5; j++) {
//                System.out.print(cnt[j] + " ");
//            }
//            System.out.println();
            int sumObj = getSumArr(cnt);
//            System.out.println(list.get(i).toString() + " " + sumObj);
//            System.out.println(sumObj);
            if (sumObj > bestMove) {
                bestMove = sumObj;
            }
            if (bestMove >= board.getScoreWin()) {
                return bestMove;
            }
        }
//        System.out.println("---------------");
//        System.out.println(bestMove);
        return bestMove;
    }

    private int[] cntNumber(InformationWays ways) {
        int[] w = new int[6];
        for (int i = 1; i <= 5; i++) {
            w[i] = 0;
        }
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> list = ways.listNumber();
//        System.out.println(list.toString());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 0) continue;
            if (map.containsKey(list.get(i))) {
                map.put(list.get(i), map.get(list.get(i)) + 1);
            } else {
                map.put(list.get(i), 1);
            }
            w[list.get(i)] = map.get(list.get(i));
        }
        return w;
    }

    private int getSumArr(int[] w) {
        return w[1] + w[2] * board.getWay2() + w[3] * board.getWay3() + w[4] * board.getWay4() + w[5] * board.getScoreWin();
    }

    public ArrayList<InformationWays> listWays(int valueBoard[][], int idPlayer) {

        ArrayList<InformationWays> list = new ArrayList<>();

        boolean[][] haveStepRight = new boolean[Board.getROW()][Board.getCOL()];
        boolean[][] haveStepDown = new boolean[Board.getROW()][Board.getCOL()];
        boolean[][] haveStepDownRight = new boolean[Board.getROW()][Board.getCOL()];
        boolean[][] haveStepDownLeft = new boolean[Board.getROW()][Board.getCOL()];

        for (int i = 0; i < Board.getROW(); i++) {
            Arrays.fill(haveStepRight[i], false);
            Arrays.fill(haveStepDown[i], false);
            Arrays.fill(haveStepDownRight[i], false);
            Arrays.fill(haveStepDownLeft[i], false);
        }

//        Point local = board.getWentGo().get(board.getWentGo().size() - 1)

        for (Point local : board.getWentGo()) {
            int i = local.x;
            int j = local.y;
            if (valueBoard[i][j] == idPlayer) {
                InformationWays information = new InformationWays();
                information.setX(i);
                information.setY(j);
                // ngang
                if (!haveStepRight[i][j]) {
                    int numberRight = getNumberRight(valueBoard, i, j, idPlayer, haveStepRight);
                    information.setNumberRight(numberRight);
                }
                // down
                if (!haveStepDown[i][j]) {
                    int numberDown = getNumberDown(valueBoard, i, j, idPlayer, haveStepDown);
                    information.setNumberDown(numberDown);
                }
                // downRight
                if (!haveStepDownRight[i][j]) {
                    int numberDownRight = getNumberDownRight(valueBoard, i, j, idPlayer, haveStepDownRight);
                    information.setNumberDownRight(numberDownRight);
                }
                // downLeft
                if (!haveStepDownLeft[i][j]) {
                    int numberDownLeft = getNumberDownLeft(valueBoard, i, j, idPlayer, haveStepDownLeft);
                    information.setNumberDownLeft(numberDownLeft);
                }

                list.add(information);

            }
        }

        return list;
    }

    private int getNumberDownLeft(int[][] valueBoard, int i, int j, int idPlayer, boolean[][] haveStepDownLeft) {
        int numberDonwLeft = 0;
        int space = 0;
        int end = -1;
        int i1 = i, i2 = j;
        int n = Math.min(Board.getROW() - 1, i + 4);
        int m = Math.max(0, j - 4);
        while (i1 <= n && i2 >= m) {
            if (valueBoard[i1][i2] != idPlayer && valueBoard[i1][i2] != 0) {
                end = i1;
                break;
            }
            haveStepDownLeft[i1][i2] = true;
            if (valueBoard[i1][i2] > 0)
                numberDonwLeft++;
            else
                space++;
            i1++;
            i2--;
        }
        if (end == -1 && numberDonwLeft + space == 5) return numberDonwLeft;
        boolean canMove = false;
        int add = 0;
        i1 = i - 1;
        i2 = j + 1;
        while (i1 >= 0 && i2 <= Board.getCOL() - 1) {
            if (valueBoard[i1][i2] > 0 && valueBoard[i1][i2] != idPlayer) {
                break;
            }
            if (numberDonwLeft + space + (++add) == 5) {
                canMove = true;
                break;
            }
            i1--;
            i2++;
        }
        if (!canMove) {
            i1 = i;
            i2 = j;
            while (i1 <= n && i2 >= m) {
                haveStepDownLeft[i1][i2] = false;
                i1++;
                i2--;
            }
        } else {
            haveStepDownLeft[n][m] = false;
        }
        return (canMove ? numberDonwLeft : 0);
    }

    private int getNumberDownRight(int[][] valueBoard, int i, int j, int idPlayer, boolean[][] haveStepDownRight) {
        int numberDonwRight = 0;
        int space = 0;
        int end = -1;
        int i1 = i, i2 = j;
        int n = Math.min(Board.getROW() - 1, i + 4);
        int m = Math.min(Board.getCOL() - 1, j + 4);
        while (i1 <= n && i2 <= m) {
            if (valueBoard[i1][i2] != idPlayer && valueBoard[i1][i2] != 0) {
                end = i1;
                break;
            }
            haveStepDownRight[i1][i2] = true;
            if (valueBoard[i1][i2] > 0)
                numberDonwRight++;
            else
                space++;
            i1++;
            i2++;
        }
        if (end == -1 && numberDonwRight + space == 5) return numberDonwRight;
        boolean canMove = false;
        int add = 0;
        i1 = i - 1;
        i2 = j - 1;

        while (i1 >= 0 && i2 >= 0) {
            if (valueBoard[i1][i2] > 0 && valueBoard[i1][i2] != idPlayer) {
                break;
            }
            if (numberDonwRight + space + (++add) == 5) {
                canMove = true;
                break;
            }
            i1--;
            i2--;
        }
        if (!canMove) {
            i1 = i;
            i2 = j;
            while (i1 <= n && i2 <= m) {
                haveStepDownRight[i1][i2] = false;
                i1++;
                i2++;
            }
        } else {
            haveStepDownRight[n][m] = false;
        }
        return (canMove ? numberDonwRight : 0);
    }


    private int getNumberDown(int[][] valueBoard, int i, int j, int idPlayer, boolean[][] haveStepDown) {
        int numberDown = 0;
        int space = 0;
        int end = -1;
        for (int k = i; k <= Math.min(Board.getROW() - 1, i + 4); k++) {
            if (valueBoard[k][j] != idPlayer && valueBoard[k][j] != 0) {
                end = k;
                break;
            }
            haveStepDown[k][j] = true;
            if (valueBoard[k][j] > 0)
                numberDown++;
            else
                space++;
        }
        // check 5 line
        if (end == -1 && numberDown + space == 5) return numberDown;
        // check phía trước
        boolean canMove = false;
        int add = 0;
        for (int k = i - 1; k >= 0; k--) {
            if (valueBoard[k][j] > 0 && valueBoard[k][j] != idPlayer) {
                break;
            }
            if (numberDown + space + (++add) == 5) {
                canMove = true;
                break;
            }
        }
        // tra lai
        if (!canMove) {
            for (int k = i; k <= Math.min(Board.getROW() - 1, i + 4); k++) {
                haveStepDown[k][j] = false;
            }
        } else {
            haveStepDown[i][Math.min(Board.getROW() - 1, i + 4)] = false;
        }
        return (canMove ? numberDown : 0);
    }

    private int getNumberRight(int[][] valueBoard, int i, int j, int idPlayer, boolean[][] haveStepRight) {
        int numberRight = 0;
        int end = -1;
        int space = 0;
        for (int k = j; k <= Math.min(Board.getCOL() - 1, j + 4); k++) {
            if (valueBoard[i][k] != idPlayer && valueBoard[i][k] != 0) {
                end = k;
                break;
            }
            haveStepRight[i][k] = true;
            if (valueBoard[i][k] > 0)
                numberRight++;
            else
                space++;
        }
        // có đủ 5 line
        if (end == -1 && numberRight + space == 5) return numberRight;
        // không đủ
        // check phía trước
        int add = 0;
        boolean canMove = false;
        for (int k = j - 1; k >= 0; k--) {
            if (valueBoard[i][k] > 0 && valueBoard[i][k] != idPlayer) {
                break;
            }
            if (space + numberRight + (++add) == 5) {
                canMove = true;
                break;
            }
        }
        // tra lai
        if (!canMove) {
            for (int k = j; k <= Math.min(Board.getCOL() - 1, j + 4); k++) {
                haveStepRight[i][k] = false;
            }
        } else {
            haveStepRight[i][Math.min(Board.getCOL() - 1, j + 4)] = false;
        }
        return (canMove ? numberRight : 0);
    }

//    public static void main(String[] args) {
//        board.getValueBoard()[0][0] = 1;
//        board.getValueBoard()[1][0] = 1;
//        board.getValueBoard()[2][0] = 1;
//        board.getValueBoard()[3][0] = 1;
//        board.getValueBoard()[4][0] = 1;
//        board.getWentGo().add(new Point(0 , 0));
//        int value = new Heuristic().think(board.getValueBoard(), 1);
//        System.out.println(value);
//    }

}
