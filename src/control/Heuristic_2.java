package control;

import view.Board;


import java.awt.*;
import java.util.ArrayList;

class Infor {

    int node = 0;
    int br = 0;
    int stop = 0;

    public Infor(int node, int br, int stop) {
        this.node = node;
        this.br = br;
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "Infor{" +
                "node=" + node +
                ", br=" + br +
                ", stop=" + stop +
                '}';
    }
}

public class Heuristic_2 {

    private static final Board board = Board.getBoard();

    private static final int arrAttack[][] = {
            {0, 0}, // 0
            {1, 1, 1}, // 1
            {100, 30}, // 2
            {1000, 300}, // 3
            {100000, 30000}, // 4
            {10000000, 10000000} // 5
    };

    public int isWin(int idPlayer) {
        for (Point i :
                board.getWentGo()) {
            if (board.getValueBoard()[i.x][i.y] == idPlayer) {
                int sum = atttack(i, board.getValueBoard(), idPlayer);
                if (sum >= board.getScoreWin()) {
                    return sum;
                }
            }
        }
        return 0;
    }

    public int think(int[][] valueBoard, int idPlayer) {
        int sum = 0;
        int sz = board.getWentGo().size();
        Point newPoint = board.getWentGo().get(sz - 1);
        for (Point i :
                board.getWentGo()) {
            if (valueBoard[i.x][i.y] == idPlayer) {
                int attack = atttack(i, valueBoard, idPlayer);
                sum += attack;
            }

        }

        return sum;
    }

    void show(ArrayList<Infor> list , String name){
        System.out.println("name = " + name);
        for (Infor i:
             list) {
            System.out.println(i.toString());
        }
    }

    public int atttack(Point point, int[][] valueBoard, int idPlayer) {

        ArrayList<Infor> listHorizal = getHorizal(point, valueBoard, idPlayer);
        ArrayList<Infor> listVertival = getVertical(point, valueBoard, idPlayer);
        ArrayList<Infor> listDiagonalMain = getDiagonalMain(point, valueBoard, idPlayer);
        ArrayList<Infor> listDiagonalSecond = getDiagonalSecond(point, valueBoard, idPlayer);


//        show(listHorizal , "ngang");
//        show(listVertival , "doc");
//        show(listDiagonalMain , "dcc");
//        show(listDiagonalSecond , "dcp");

        ArrayList<Infor> allList = new ArrayList<>();
        allList.addAll(listHorizal);
        allList.addAll(listVertival);
        allList.addAll(listDiagonalMain);
        allList.addAll(listDiagonalSecond);

        int sum = 0;
        for (Infor i :
                allList) {
            int x = i.node;
            int y = i.br;
            int z = i.stop;
            if (x == 1 && y == 0 && z == 0) sum += 1;

            if (x == 2 && y == 0 && z == 0) sum += 80;
            if (x == 2 && y == 0 && z == 1) sum += 50;
            if (x == 2 && y == 1 && z == 0) sum += 50;
            if (x == 2 && y == 1 && z == 1) sum += 30;

            if (x == 3 && y == 0 && z == 0) sum += 1000;
            if (x == 3 && y == 0 && z == 1) sum += 500;
            if (x == 3 && y == 1 && z == 0) sum += 500;
            if (x == 3 && y == 1 && z == 1) sum += 100;

            if (x == 4 && y == 0 && z == 0) sum += 100000;
            if (x == 4 && y == 0 && z == 1) sum += 60000;
            if (x == 4 && y == 1 && z == 0) sum += 60000;
            if (x == 4 && y == 1 && z == 1) sum += 40000;

            if (x == 5 && y == 0 && z == 0) sum += 10000000;
        }
//        System.out.println("sum = " + sum);
        return sum;
    }


    private ArrayList<Infor> getHorizal(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Infor> list = new ArrayList<>();
        int x = point.x;
        int y = point.y;
        int bg = Math.max(0, y - 4);

        for (int i = y; i >= bg; i--) {
            if (valueBoard[x][i] > 0 && valueBoard[x][i] != idPlayer) {
                bg = i + 1;
                break;
            }
        }

        for (int i = bg; i <= y; i++) {
            int mx = 0;
            int checkIndexZero4 = -1;
            int checkIndexZero3_1 = -1;
            int checkIndexZero3_2 = -1;
            int checkIndex2_1 = -1;
            int checkIndex2_2 = -1;
            int en2 = Math.min(i + 4, Board.getCOL() - 1);
//                System.out.println(i + " " +  en2);
            for (int k = i; k <= Math.min(i + 4, Board.getCOL() - 1); k++) {
                if (valueBoard[x][k] > 0 && valueBoard[x][k] != idPlayer) {
                    en2 = k - 1;
                    break;
                }
            }

            for (int j = i; j <= en2; j++) {
                if (valueBoard[x][j] == idPlayer) {
                    mx++;
                    if (checkIndex2_1 == -1) {
                        checkIndex2_1 = j;
                        continue;
                    }
                    if (checkIndex2_2 == -1) {
                        checkIndex2_2 = j;
                    }
                } else {
                    checkIndexZero4 = j;
                    if (checkIndexZero3_1 == -1) {
                        checkIndexZero3_1 = j;
                        continue;
                    }
                    checkIndexZero3_2 = j;
                }
            }
            if (mx == 5) {
                list.add(new Infor(5, 0, 0));
                break;
            } else if (mx == 4) {
                list.add(checkFourH(checkIndexZero4, i, en2, x, y));
            } else if (mx == 3) {
                list.add(checkThreeH(checkIndexZero3_1, checkIndexZero3_2, i, en2, x, y));
            } else if (mx == 2) {
                list.add(checkTwoH(checkIndex2_1, checkIndex2_2, i, en2, x, y));
            } else if (mx == 1) {
                list.add(new Infor(1, 0, 0));
            }
        }


        return list;
    }

    private ArrayList<Infor> getVertical(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Infor> list = new ArrayList<>();
        int x = point.x;
        int y = point.y;

        int bg = Math.max(0, x - 4);
        for (int i = x; i >= bg; i--) {
            if (valueBoard[i][y] > 0 && valueBoard[i][y] != idPlayer) {
                bg = i + 1;
                break;
            }
        }

        for (int i = bg; i <= x; i++) {
            int mx = 0;
            int checkIndexZero4 = -1;
            int checkIndexZero3_1 = -1;
            int checkIndexZero3_2 = -1;
            int checkIndex2_1 = -1;
            int checkIndex2_2 = -1;
            int en2 = Math.min(Board.getROW() - 1, i + 4);
//                System.out.println(i + " " + en2);

            for (int k = i; k <= Math.min(Board.getROW() - 1, i + 4); k++) {
                if (valueBoard[k][y] > 0 && valueBoard[k][y] != idPlayer) {
                    en2 = k - 1;
                    break;
                }
            }

            for (int j = i; j <= en2; j++) {
//                    System.out.println(j + " " + y);
                if (valueBoard[j][y] == idPlayer) {
                    mx++;
                    if (checkIndex2_1 == -1) {
                        checkIndex2_1 = j;
                        continue;
                    }
                    checkIndex2_2 = j;
                } else {
                    checkIndexZero4 = j;
                    if (checkIndexZero3_1 == -1) {
                        checkIndexZero3_1 = j;
                        continue;
                    }
                    checkIndexZero3_2 = j;
                }
            }
            if (mx == 5) {
                list.add(new Infor(5, 0, 0));
                break;
            } else if (mx == 4) {
                list.add(checkFourV(checkIndexZero4, i, en2, x, y));
            } else if (mx == 3) {
                list.add(checkThreeV(checkIndexZero3_1, checkIndexZero3_2, i, en2, x, y));
            } else if (mx == 2) {
                list.add(checkTwoV(checkIndex2_1, checkIndex2_2, i, en2, x, y));
            } else if (mx == 1) {
                list.add(new Infor(1, 0, 0));
            }
        }


        return list;
    }

    private ArrayList<Infor> getDiagonalMain(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Infor> list = new ArrayList<>();

        int x = point.x;
        int y = point.y;

        int beg_x = x;
        int beg_y = y;
        int time = 5;

        while (time > 0 && beg_x >= 0 && beg_y >= 0) {
            if (valueBoard[beg_x][beg_y] > 0 && valueBoard[beg_x][beg_y] != idPlayer) {
                beg_x++;
                beg_y++;
                break;
            }
            if (beg_x - 1 < 0 || beg_y - 1 < 0) break;
            beg_x--;
            beg_y--;
            time--;
        }

        for (int i = beg_x, j = beg_y; i <= x && j <= y; i++, j++) {

            int end_x1 = i;
            int end_y1 = j;
            time = 4;

            while (time > 0 && end_x1 < Board.getROW() && end_y1 < Board.getCOL()) {
                if (valueBoard[end_x1][end_y1] > 0 && valueBoard[end_x1][end_y1] != idPlayer) {
                    end_x1--;
                    end_y1--;
                    break;
                }
                if (end_x1 + 1 >= Board.getROW() || end_y1 + 1 >= Board.getCOL()) break;
                end_x1++;
                end_y1++;
                time--;
            }

            int mx = 0;
            int checkIndexZero4 = -1;
            int checkIndexZero3_1 = -1;
            int checkIndexZero3_2 = -1;
            int checkIndex2_1 = -1;
            int checkIndex2_2 = -1;

            for (int i1 = i, j1 = j; i1 <= end_x1 && j1 <= end_y1; i1++, j1++) {
                try {
                    if (valueBoard[i1][j1] == idPlayer) {
                        mx++;
                        if (checkIndex2_1 == -1) {
                            checkIndex2_1 = j1;
                            continue;
                        }
                        checkIndex2_2 = j1;
                    } else {
                        checkIndexZero4 = j1;
                        if (checkIndexZero3_1 == -1) {
                            checkIndexZero3_1 = j1;
                            continue;
                        }
                        checkIndexZero3_2 = j1;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("try : " + i1 + " " + j1);
                    e.printStackTrace();
                }
            }
            if (mx == 5) {
                list.add(new Infor(5, 0, 0));
                break;
            } else if (mx == 4) {
                list.add(checkFourDm(checkIndexZero4, i, j, end_x1, end_y1));
            } else if (mx == 3) {
                list.add(checkThreeDm(checkIndexZero3_1, checkIndexZero3_2, i, j, end_x1, end_y1));
            } else if (mx == 2) {
                list.add(checkTwoDm(checkIndex2_1, checkIndex2_2, i, j, end_x1, end_y1));
            } else {
                list.add(new Infor(1, 0, 0));
            }

        }

        return list;
    }

    private ArrayList<Infor> getDiagonalSecond(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Infor> list = new ArrayList<>();

        int x = point.x;
        int y = point.y;

        int beg_x = x;
        int beg_y = y;
        int time = 5;

        while (time > 0 && beg_x >= 0 && beg_y < Board.getCOL()) {
            if (valueBoard[beg_x][beg_y] > 0 && valueBoard[beg_x][beg_y] != idPlayer) {
                beg_x++;
                beg_y--;
                break;
            }
            if (beg_x - 1 < 0 || beg_y + 1 >= Board.getCOL()) {
                break;
            }
            beg_x--;
            beg_y++;
            time--;
        }

        for (int i = beg_x, j = beg_y; i <= x && j >= y; i++, j--) {

            int end_x1 = i;
            int end_y1 = j;
            time = 4;

            while (time > 0 && end_x1 < Board.getROW() && end_y1 >= 0) {
                if (valueBoard[end_x1][end_y1] > 0 && valueBoard[end_x1][end_y1] != idPlayer) {
                    end_x1--;
                    end_y1++;
                    break;
                }
                if (end_x1 + 1 >= Board.getROW() || end_y1 - 1 < 0) {
                    break;
                }
                end_x1++;
                end_y1--;
                time--;
            }

            int mx = 0;
            int checkIndexZero4 = -1;
            int checkIndexZero3_1 = -1;
            int checkIndexZero3_2 = -1;
            int checkIndex2_1 = -1;
            int checkIndex2_2 = -1;

//                System.out.println(i + " " + j + " " + end_x1 + " " + end_y1);

            for (int i1 = i, j1 = j; i1 <= end_x1 && j1 >= end_y1; i1++, j1--) {
                try {

                    if (valueBoard[i1][j1] == idPlayer) {
                        mx++;
                        if (checkIndex2_1 == -1) {
                            checkIndex2_1 = j1;
                            continue;
                        }
                        checkIndex2_2 = j1;
                    } else {
                        checkIndexZero4 = j1;
                        if (checkIndexZero3_1 == -1) {
                            checkIndexZero3_1 = j1;
                            continue;
                        }
                        checkIndexZero3_2 = j1;
                    }

                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("try : " + i1 + " " + j1);
                    e.printStackTrace();
                }
            }
            if (mx == 5) {
                list.add(new Infor(5, 0, 0));
                break;
            } else if (mx == 4) {
                list.add(checkFourDs(checkIndexZero4, i, j, end_x1, end_y1));
            } else if (mx == 3) {
                list.add(checkThreeDs(checkIndexZero3_1, checkIndexZero3_2, i, j, end_x1, end_y1));
            } else if (mx == 2) {
                list.add(checkTwoDs(checkIndex2_1, checkIndex2_2, i, j, end_x1, end_y1));
            } else {
                list.add(new Infor(1, 0, 0));
            }

        }


        return list;
    }

    private Infor checkFourDs(int checkIndexZero4, int rx1, int rx2, int ry1, int ry2) {
        if (checkIndexZero4 == rx2 || checkIndexZero4 == ry2) {
            if (checkIndexZero4 == rx2) {
                if (ry1 + 1 < Board.getROW() && ry2 - 1 >= 0) { // |.xxxx.
                    if (board.getValueBoard()[ry1 + 1][ry2 - 1] == board.getValueBoard()[ry1][ry2] ||
                            board.getValueBoard()[ry1 + 1][ry2 - 1] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            } else {
                if (rx1 - 1 >= 0 && rx2 + 1 < Board.getCOL()) { // .xxxx.|
                    if (board.getValueBoard()[rx1 - 1][rx2 + 1] == board.getValueBoard()[rx1][rx2] ||
                            board.getValueBoard()[rx1 - 1][rx2 + 1] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            }
            return new Infor(4, 0, 1);
        }
        //.oo.oo.
        if ((rx1 - 1 >= 0 && rx2 + 1 < Board.getCOL()) && (ry1 + 1 < Board.getROW() && ry2 - 1 >= 0)) {
            if ((board.getValueBoard()[rx1 - 1][rx2 + 1] == board.getValueBoard()[rx1][rx2] ||
                    board.getValueBoard()[rx1 - 1][rx2 + 1] == 0) &&
                    (board.getValueBoard()[ry1 + 1][ry2 - 1] == board.getValueBoard()[ry1][ry2] ||
                            board.getValueBoard()[ry1 + 1][ry2 - 1] == 0)) {
                return new Infor(4, 1, 0);
            }
        }
        // xooo.ox
        return new Infor(4, 1, 1);
    }

    private Infor checkThreeDs(int checkIndexZero3_1, int checkIndexZero3_2, int rx1, int rx2, int ry1, int ry2) {
        if (checkIndexZero3_1 == rx2 && checkIndexZero3_2 == ry2) { // .000.
            return new Infor(3, 0, 0);
        }
        // ..ooo
        if (checkIndexZero3_1 == rx2 && checkIndexZero3_1 - 1 == checkIndexZero3_2) {
            if (ry1 + 1 < Board.getROW() && ry2 - 1 >= 0) { // |.xxxx.
                if (board.getValueBoard()[ry1 + 1][ry2 - 1] == board.getValueBoard()[ry1][ry2] ||
                        board.getValueBoard()[ry1 + 1][ry2 - 1] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // ..ooox
                return new Infor(3, 0, 1);
            }
        }
        // ooo..
        if (checkIndexZero3_2 == ry2 && checkIndexZero3_1 - 1 == checkIndexZero3_2) {
            if (rx1 - 1 >= 0 && rx2 + 1 < Board.getCOL()) { // .xxxx.|
                if (board.getValueBoard()[rx1 - 1][rx2 + 1] == board.getValueBoard()[rx1][rx2] ||
                        board.getValueBoard()[rx1 - 1][rx2 + 1] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // xooo..
                return new Infor(3, 0, 1);
            }
        }
        // _o.o.o_
        if ((rx1 - 1 >= 0 && rx2 + 1 < Board.getCOL()) && (ry1 + 1 < Board.getROW() && ry2 - 1 >= 0)) {
            if ((board.getValueBoard()[rx1 - 1][rx2 + 1] == board.getValueBoard()[rx1][rx2] ||
                    board.getValueBoard()[rx1 - 1][rx2 + 1] == 0) &&
                    (board.getValueBoard()[ry1 + 1][ry2 - 1] == board.getValueBoard()[ry1][ry2] ||
                            board.getValueBoard()[ry1 + 1][ry2 - 1] == 0)) {
                return new Infor(3, 1, 0);
            }
        }
        // xo.o.ox
        return new Infor(3, 1, 1);
    }

    private Infor checkTwoDs(int checkIndex2_1, int checkIndex2_2, int rx1, int rx2, int ry1, int ry2) {
        if (checkIndex2_1 - 1 == checkIndex2_2) {
            if ((rx1 - 1 >= 0 && rx2 + 1 < Board.getCOL()) && (ry1 + 1 < Board.getROW() && ry2 - 1 >= 0)) {
                if ((board.getValueBoard()[rx1 - 1][rx2 + 1] == board.getValueBoard()[rx1][rx2] ||
                        board.getValueBoard()[rx1 - 1][rx2 + 1] == 0) &&
                        (board.getValueBoard()[ry1 + 1][ry2 - 1] == board.getValueBoard()[ry1][ry2] ||
                                board.getValueBoard()[ry1 + 1][ry2 - 1] == 0)) {
                    return new Infor(2, 0, 0);
                }
            }
        }
        // .o...o.
        if ((rx1 - 1 >= 0 && rx2 + 1 < Board.getCOL()) && (ry1 + 1 < Board.getROW() && ry2 - 1 >= 0)) {
            if ((board.getValueBoard()[rx1 - 1][rx2 + 1] == board.getValueBoard()[rx1][rx2] ||
                    board.getValueBoard()[rx1 - 1][rx2 + 1] == 0) &&
                    (board.getValueBoard()[ry1 + 1][ry2 - 1] == board.getValueBoard()[ry1][ry2] ||
                            board.getValueBoard()[ry1 + 1][ry2 - 1] == 0)) {
                return new Infor(2, 1, 0);
            }
        }
        //xoo...x
        return new Infor(2, 1, 1);

    }

    private Infor checkFourDm(int checkIndexZero4, int rx1, int rx2, int ry1, int ry2) {
        if (checkIndexZero4 == rx2 || checkIndexZero4 == ry2) {
            if (checkIndexZero4 == rx2) {
                if (ry1 + 1 < Board.getROW() && ry2 + 1 < Board.getCOL()) { // .xxxx.|
                    if (board.getValueBoard()[ry1 + 1][ry2 + 1] == board.getValueBoard()[ry1][ry2] ||
                            board.getValueBoard()[ry1 + 1][ry2 + 1] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            } else {
                if (rx1 - 1 >= 0 && rx2 - 1 >= 0) { // |.xxxx.
                    if (board.getValueBoard()[rx1 - 1][rx2 - 1] == board.getValueBoard()[rx1][rx2] ||
                            board.getValueBoard()[rx1 - 1][rx2 - 1] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            }
            return new Infor(4, 0, 1);
        }
        //.oo.oo.
        if ((ry1 + 1 < Board.getROW() && ry2 + 1 < Board.getCOL()) &&
                (rx1 - 1 >= 0 && rx2 - 1 >= 0)) {
            if ((board.getValueBoard()[ry1 + 1][ry2 + 1] == board.getValueBoard()[ry1][ry2] ||
                    board.getValueBoard()[ry1 + 1][ry2 + 1] == 0) &&
                    (board.getValueBoard()[rx1 - 1][rx2 - 1] == board.getValueBoard()[rx1][rx2] ||
                            board.getValueBoard()[rx1 - 1][rx2 - 1] == 0)) {
                return new Infor(4, 1, 0);
            }
        }
        // xooo.ox
        return new Infor(4, 1, 1);
    }

    private Infor checkThreeDm(int checkIndexZero3_1, int checkIndexZero3_2, int rx1, int rx2, int ry1, int ry2) {
//        System.out.println(rx1 + " " + rx2 + " " + ry1 + " " + ry2);
        if (checkIndexZero3_1 == rx2 && checkIndexZero3_2 == ry2) { // .000.
            return new Infor(3, 0, 0);
        }
        // ..ooo
        if (checkIndexZero3_1 == rx2 && checkIndexZero3_1 + 1 == checkIndexZero3_2) {
            if (ry1 + 1 < Board.getROW() && ry2 + 1 < Board.getCOL()) {
                if (board.getValueBoard()[ry1 + 1][ry2 + 1] == board.getValueBoard()[ry1][ry2] ||
                        board.getValueBoard()[ry1 + 1][ry2 + 1] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // ..ooox
                return new Infor(3, 0, 1);
            }
        }
        // ooo..
        if (checkIndexZero3_2 == ry2 && checkIndexZero3_1 + 1 == checkIndexZero3_2) {
            if (rx1 - 1 >= 0 && rx2 - 1 >= 0) { // |.xxxx.
                if (board.getValueBoard()[rx1 - 1][rx2 - 1] == board.getValueBoard()[rx1][rx2] ||
                        board.getValueBoard()[rx1 - 1][rx2 - 1] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // xooo..
                return new Infor(3, 0, 1);
            }
        }
        // _o.o.o_
        if ((ry1 + 1 < Board.getROW() && ry2 + 1 < Board.getCOL()) &&
                (rx1 - 1 >= 0 && rx2 - 1 >= 0)) {
            if ((board.getValueBoard()[ry1 + 1][ry2 + 1] == board.getValueBoard()[ry1][ry2] ||
                    board.getValueBoard()[ry1 + 1][ry2 + 1] == 0) &&
                    (board.getValueBoard()[rx1 - 1][rx2 - 1] == board.getValueBoard()[rx1][rx2] ||
                            board.getValueBoard()[rx1 - 1][rx2 - 1] == 0)) {
                return new Infor(3, 1, 0);
            }
        }
        // xo.o.ox
        return new Infor(3, 1, 1);
    }

    private Infor checkTwoDm(int checkIndex2_1, int checkIndex2_2, int rx1, int rx2, int ry1, int ry2) {
        if (checkIndex2_1 + 1 == checkIndex2_2) {
            if ((ry1 + 1 < Board.getROW() && ry2 + 1 < Board.getCOL()) &&
                    (rx1 - 1 >= 0 && rx2 - 1 >= 0)) {
                if ((board.getValueBoard()[ry1 + 1][ry2 + 1] == board.getValueBoard()[ry1][ry2] ||
                        board.getValueBoard()[ry1 + 1][ry2 + 1] == 0) &&
                        (board.getValueBoard()[rx1 - 1][rx2 - 1] == board.getValueBoard()[rx1][rx2] ||
                                board.getValueBoard()[rx1 - 1][rx2 - 1] == 0)) {
                    return new Infor(2, 0, 0);
                }
            }
        }
        // .o...o.
        if ((ry1 + 1 < Board.getROW() && ry2 + 1 < Board.getCOL()) &&
                (rx1 - 1 >= 0 && rx2 - 1 >= 0)) {
            if ((board.getValueBoard()[ry1 + 1][ry2 + 1] == board.getValueBoard()[ry1][ry2] ||
                    board.getValueBoard()[ry1 + 1][ry2 + 1] == 0) &&
                    (board.getValueBoard()[rx1 - 1][rx2 - 1] == board.getValueBoard()[rx1][rx2] ||
                            board.getValueBoard()[rx1 - 1][rx2 - 1] == 0)) {
                return new Infor(2, 1, 0);
            }
        }
        //xoo...x
        return new Infor(2, 1, 1);

    }

    private Infor checkFourV(int checkIndexZero4, int x, int y, int rx, int ry) {
        if (checkIndexZero4 == x || checkIndexZero4 == y) {
            if (checkIndexZero4 == x) {
                if (y + 1 < Board.getROW()) { // .xxxx.|
                    if (board.getValueBoard()[y + 1][ry] == board.getValueBoard()[y][ry] ||
                            board.getValueBoard()[y + 1][ry] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            } else {
                if (x - 1 >= 0) { // |.xxxx.
                    if (board.getValueBoard()[x - 1][ry] == board.getValueBoard()[x][ry] ||
                            board.getValueBoard()[x - 1][ry] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            }
            return new Infor(4, 0, 1);
        }
        //.oo.oo.
        if (x - 1 >= 0 && y + 1 < Board.getROW()) {
            if ((board.getValueBoard()[y + 1][ry] == board.getValueBoard()[y][ry] ||
                    board.getValueBoard()[y + 1][ry] == 0) &&
                    (board.getValueBoard()[x - 1][ry] == board.getValueBoard()[x][ry] ||
                            board.getValueBoard()[x - 1][ry] == 0)) {
                return new Infor(4, 1, 0);
            }
        }
        // xooo.ox
        return new Infor(4, 1, 1);
    }

    private Infor checkThreeV(int checkIndexZero3_1, int checkIndexZero3_2, int bg, int en, int rx, int ry) {
        if (checkIndexZero3_1 == bg && checkIndexZero3_2 == en) { // .000.
            return new Infor(3, 0, 0);
        }
        // ..ooo
        if (checkIndexZero3_1 == bg && checkIndexZero3_1 + 1 == checkIndexZero3_2) {
            if (en + 1 < Board.getROW()) {
                // x..ooo.|
                if (board.getValueBoard()[en + 1][ry] == board.getValueBoard()[en][ry] ||
                        board.getValueBoard()[en + 1][ry] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // ..ooox
                return new Infor(3, 0, 1);
            }
        }
        // ooo..
        if (checkIndexZero3_2 == en && checkIndexZero3_1 + 1 == checkIndexZero3_2) {
            if (bg - 1 >= 0) {
                // .ooo..x
                if (board.getValueBoard()[bg - 1][ry] == board.getValueBoard()[bg][ry] ||
                        board.getValueBoard()[bg - 1][ry] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // xooo..
                return new Infor(3, 0, 1);
            }
        }
        // _o.o.o_
        if (bg - 1 >= 0 && en + 1 < Board.getROW()) {
            if ((board.getValueBoard()[en + 1][ry] == board.getValueBoard()[en][ry] ||
                    board.getValueBoard()[en + 1][ry] == 0) &&
                    (board.getValueBoard()[bg - 1][ry] == board.getValueBoard()[bg][ry] ||
                            board.getValueBoard()[bg - 1][ry] == 0)) {
                return new Infor(3, 1, 0);
            }
        }
        // xo.o.ox
        return new Infor(3, 1, 1);
    }

    private Infor checkTwoV(int checkIndex2_1, int checkIndex2_2, int x, int y, int rx, int ry) {
        if (checkIndex2_1 + 1 == checkIndex2_2) {
            if (x - 1 >= 0 && y + 1 < Board.getROW()) {
                if ((board.getValueBoard()[x - 1][ry] == board.getValueBoard()[x - 1][ry] ||
                        board.getValueBoard()[x - 1][ry] == 0) &&
                        (board.getValueBoard()[y + 1][ry] == board.getValueBoard()[y][ry] ||
                                board.getValueBoard()[y + 1][ry] == 0)) {
                    return new Infor(2, 0, 0);
                }
            }
            return new Infor(2, 0, 1);
        }
        if (x - 1 >= 0 && y + 1 < Board.getROW()) {
            if ((board.getValueBoard()[x - 1][ry] == board.getValueBoard()[x - 1][ry] ||
                    board.getValueBoard()[x - 1][ry] == 0) &&
                    (board.getValueBoard()[y + 1][ry] == board.getValueBoard()[y][ry] ||
                            board.getValueBoard()[y + 1][ry] == 0)) {
                return new Infor(2, 1, 0);
            }
        }
        return new Infor(2, 1, 1);
    }

    private Infor checkFourH(int checkIndexZero4, int x, int y, int rx, int ry) {
        if (checkIndexZero4 == x || checkIndexZero4 == y) {
            if (checkIndexZero4 == x) {
                if (y + 1 < Board.getCOL()) { // .xxxx.|
                    if (board.getValueBoard()[rx][y + 1] == board.getValueBoard()[rx][y] ||
                            board.getValueBoard()[rx][y + 1] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            } else {
                if (x - 1 >= 0) { // |.xxxx.
                    if (board.getValueBoard()[rx][x - 1] == board.getValueBoard()[rx][x] ||
                            board.getValueBoard()[rx][x - 1] == 0) {
                        return new Infor(4, 0, 0);
                    }
                }
            }
            return new Infor(4, 0, 1);
        }
        //.oo.oo.
        if (x - 1 >= 0 && y + 1 < Board.getCOL()) {
            if ((board.getValueBoard()[rx][x - 1] == board.getValueBoard()[rx][x] ||
                    board.getValueBoard()[rx][x - 1] == 0) && (board.getValueBoard()[rx][y + 1] == board.getValueBoard()[rx][y] ||
                    board.getValueBoard()[rx][y + 1] == 0)) {
                return new Infor(4, 1, 0);
            }
        }
        // xooo.ox
        return new Infor(4, 1, 1);
    }

    private Infor checkThreeH(int checkIndexZero3_1, int checkIndexZero3_2, int bg, int en, int rx, int ry) {
        if (checkIndexZero3_1 == bg && checkIndexZero3_2 == en) { // .000.
            return new Infor(3, 0, 0);
        }
        // ..ooo
        if (checkIndexZero3_1 == bg && checkIndexZero3_1 + 1 == checkIndexZero3_2) {
            if (en + 1 < Board.getCOL()) {
                // x..ooo.|
                if (board.getValueBoard()[rx][en + 1] == board.getValueBoard()[rx][en] ||
                        board.getValueBoard()[rx][en + 1] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // ..ooox
                return new Infor(3, 0, 1);
            }
        }
        // ooo..
        if (checkIndexZero3_2 == en && checkIndexZero3_1 + 1 == checkIndexZero3_2) {
            if (bg - 1 >= 0) {
                // .ooo..x
                if (board.getValueBoard()[rx][bg] == board.getValueBoard()[rx][bg - 1] ||
                        board.getValueBoard()[rx][bg - 1] == 0) {
                    return new Infor(3, 0, 0);
                }
            } else {
                // xooo..
                return new Infor(3, 0, 1);
            }
        }
        // _o.o.o_
        if (bg - 1 >= 0 && en + 1 < Board.getCOL()) {
            if ((board.getValueBoard()[rx][bg] == board.getValueBoard()[rx][bg - 1] ||
                    board.getValueBoard()[rx][bg - 1] == 0) &&
                    (board.getValueBoard()[rx][en + 1] == board.getValueBoard()[rx][en] ||
                            board.getValueBoard()[rx][en + 1] == 0)) {
                return new Infor(3, 1, 0);
            }
        }
        // xo.o.ox
        return new Infor(3, 1, 1);
    }

    private Infor checkTwoH(int checkIndex2_1, int checkIndex2_2, int x, int y, int rx, int ry) {

        if (checkIndex2_1 + 1 == checkIndex2_2) {
            if (x - 1 >= 0 && y + 1 < Board.getCOL()) {
                if (((board.getValueBoard()[rx][x - 1] == board.getValueBoard()[rx][x]) ||
                        board.getValueBoard()[rx][x - 1] == 0) &&
                        ((board.getValueBoard()[rx][y + 1] == board.getValueBoard()[rx][y]) ||
                                board.getValueBoard()[rx][y + 1] == 0)) {
                    return new Infor(2, 0, 0);
                }

            }
            return new Infor(2, 0, 1);
        }

        if (x - 1 >= 0 && y + 1 < Board.getCOL()) {
            if (((board.getValueBoard()[rx][x - 1] == board.getValueBoard()[rx][x]) ||
                    board.getValueBoard()[rx][x - 1] == 0) &&
                    ((board.getValueBoard()[rx][y + 1] == board.getValueBoard()[rx][y]) ||
                            board.getValueBoard()[rx][y + 1] == 0)) {
                return new Infor(2, 1, 0);
            }

        }


        return new Infor(2, 1, 1);
    }


    public static void main(String[] args) {

        board.getValueBoard()[10][10] = 1;
        board.getValueBoard()[9][9] = 1;
        board.getValueBoard()[8][8] = 1;
        board.getValueBoard()[11][9] = 1;
        board.getValueBoard()[12][8] = 0;
        board.getValueBoard()[11][7] = 1;

        new Heuristic_2().atttack(new Point(10 , 10) , board.getValueBoard() , 1);


    }
}
