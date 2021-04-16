package control;

import view.Board;

import java.awt.*;
import java.util.ArrayList;

public class Heuristic_2 {

    private static final Board board = Board.getBoard();

    private static final int arrAttack[][] = {
            {0, 0}, // 0
            {1, 1}, // 1
            {100, 30}, // 2
            {1000, 300}, // 3
            {100000, 30000}, // 4
            {10000000, 10000000} // 5
    };

    private static final int arrDefen[][] = {
            {0, 0}, // 0
            {1, 1}, // 1
            {100, 30}, // 2
            {1000, 300}, // 3
            {100000, 30000}, // 4
            {10000000, 10000000} // 5
    };

    public int isWin(int idPlayer) {
        for (Point i :
                board.getWentGo()) {
            if (board.getValueBoard()[i.x][i.y] == idPlayer) {
                // atack > boad.getWin
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
        if(sum <= 300) {
            sum = Math.max(sum, defen(newPoint, valueBoard, idPlayer));
        }
        return sum;
    }


    private int defen(Point point, int[][] valueBoard, int idPlayer) {
        int sum = 0;
        ArrayList<Point> list = defen_all(point, valueBoard, idPlayer);
        for (Point i :
                list) {
            int x = i.x;
            int y = i.y;
            if(x >= 4){
                sum += arrDefen[x][y];
            }
        }
        return sum;
    }

    private ArrayList<Point> defen_all(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Point> list = new ArrayList<>();
        int x = point.x;
        int y = point.y;

        int left = Math.max(0, y - 4);
        int mx = 0;
        for (int i = y - 1; i >= left; i--) {
            if (valueBoard[x][i] > 0 && valueBoard[x][i] != idPlayer) {
                mx++;
            } else if (valueBoard[x][i] == idPlayer) {
                break;
            }
        }

        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));

        int right = Math.min(Board.getCOL() - 1, y + 4);
        mx = 0;
        for (int i = y + 1; i <= right; i++) {
            if (valueBoard[x][i] > 0 && valueBoard[x][i] != idPlayer) {
                mx++;
            } else if (valueBoard[x][i] == idPlayer) {
                break;
            }
        }
        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));

        int up = Math.max(0, x - 4);
        mx = 0;
        for (int i = x - 1; i >= up; i--) {
            if (valueBoard[i][y] > 0 && valueBoard[x][i] != idPlayer) {
                mx++;
            } else if (valueBoard[x][i] == idPlayer) {
                break;
            }
        }
        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));

        int down = Math.min(Board.getROW() - 1, x + 4);
        mx = 0;
        for (int i = x + 1; i <= down; i++) {
            if (valueBoard[i][y] > 0 && valueBoard[x][i] != idPlayer) {
                mx++;
            } else if (valueBoard[x][i] == idPlayer) {
                break;
            }
        }
        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));

        // downLeft
        mx = 0;
        int i1 = x + 1;
        int i2 = y - 1;
        int time = 4;
        while (time > 0 && board.isInBoard(i1, i2)) {
            if (valueBoard[i1][i2] > 0 && valueBoard[i1][i2] != idPlayer) {
                mx++;
            } else if (valueBoard[i1][i2] == idPlayer) {
                break;
            }
            i1++;
            i2--;
            time--;
        }
        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));


        // downRight
        mx = 0;
        i1 = x + 1;
        i2 = y + 1;
        time = 4;
        while (time > 0 && board.isInBoard(i1, i2)) {
            if (valueBoard[i1][i2] > 0 && valueBoard[i1][i2] != idPlayer) {
                mx++;
            } else if (valueBoard[i1][i2] == idPlayer) {
                break;
            }
            i1++;
            i2++;
            time--;
        }
        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));

        // upLeft
        mx = 0;
        i1 = x - 1;
        i2 = y - 1;
        time = 4;
        while (time > 0 && board.isInBoard(i1, i2)) {
            if (valueBoard[i1][i2] > 0 && valueBoard[i1][i2] != idPlayer) {
                mx++;
            } else if (valueBoard[i1][i2] == idPlayer) {
                break;
            }
            i1--;
            i2--;
            time--;
        }
        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));


        // upRight
        mx = 0;
        i1 = x - 1;
        i2 = y + 1;
        time = 4;
        while (time > 0 && board.isInBoard(i1, i2)) {
            if (valueBoard[i1][i2] > 0 && valueBoard[i1][i2] != idPlayer) {
                mx++;
            } else if (valueBoard[i1][i2] == idPlayer) {
                break;
            }
            i1--;
            i2++;
            time--;
        }
        list.add(new Point(mx, (mx >= 4 ? 0 : 1)));


        return list;
    }


    public int atttack(Point point, int[][] valueBoard, int idPlayer) {

        ArrayList<Point> listHorizal = getHorizal(point, valueBoard, idPlayer);
        ArrayList<Point> listVertival = getVertical(point, valueBoard, idPlayer);
        ArrayList<Point> listDiagonalMain = getDiagonalMain(point, valueBoard, idPlayer);
        ArrayList<Point> listDiagonalSecond = getDiagonalSecond(point, valueBoard, idPlayer);

        ArrayList<Point> allList = new ArrayList<>();
        allList.addAll(listHorizal);
        allList.addAll(listVertival);
        allList.addAll(listDiagonalMain);
        allList.addAll(listDiagonalSecond);

        int sum = 0;
        for (Point i :
                allList) {
            int xx = i.x;
            int yy = i.y;
            sum += arrAttack[xx][yy];
        }
//        System.out.println("sum = " + sum);
        return sum;
    }


    private ArrayList<Point> getHorizal(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Point> list = new ArrayList<>();
        int x = point.x;
        int y = point.y;
        int bg = Math.max(0, y - 4);

        for (int i = y; i >= bg; i--) {
            if (valueBoard[x][i] > 0 && valueBoard[x][i] != idPlayer) {
                bg = i + 1;
                break;
            }
        }

        int en = Math.min(Board.getCOL() - 1, y + 4);
        for (int i = y; i <= en; i++) {
            if (valueBoard[x][i] > 0 && valueBoard[x][i] != idPlayer) {
                en = i - 1;
                break;
            }
        }

        if (en - bg + 1 >= 5) {
            for (int i = bg; i <= y; i++) {
                int mx = 0;
                int checkIndexZero4 = -1;
                int checkIndexZero3_1 = -1;
                int checkIndexZero3_2 = -1;
                int checkIndex2_1 = -1;
                int checkIndex2_2 = -1;
                int en2 = Math.min(Board.getCOL() - 1, i + 4);
//                System.out.println(i + " " +  en2);
                if (en2 > en) break;
                for (int j = i; j <= en2; j++) {
                    if (valueBoard[x][j] == idPlayer) {
                        mx++;
                        if (checkIndex2_1 == -1) {
                            checkIndex2_1 = j;
                            continue;
                        }
                        if (checkIndex2_2 == -1) {
                            checkIndex2_2 = j;
                            continue;
                        }
                    } else {
                        checkIndexZero4 = j;
                        if (checkIndexZero3_1 == -1) {
                            checkIndexZero3_1 = j;
                            continue;
                        }
                        if (checkIndexZero3_1 != -1) {
                            checkIndexZero3_2 = j;
                            continue;
                        }
                    }
                }
                if (mx == 5) {
                    list.add(new Point(mx, 0));
//                    break;
                } else if (mx == 4) {
                    list.add(new Point(mx, checkFour(checkIndexZero4, i, en2)));
                } else if (mx == 3) {
                    list.add(new Point(mx, checkThree(checkIndexZero3_1, checkIndexZero3_2, i, en2)));
                } else if (mx == 2) {
                    list.add(new Point(mx, checkTwo(checkIndex2_1, checkIndex2_2)));
                } else if (mx == 1) {
                    list.add(new Point(mx, 0));
                }
            }
        }

        return list;
    }

    private ArrayList<Point> getVertical(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Point> list = new ArrayList<>();
        int x = point.x;
        int y = point.y;

        int bg = Math.max(0, x - 4);
        for (int i = x; i >= bg; i--) {
            if (valueBoard[i][y] > 0 && valueBoard[i][y] != idPlayer) {
                bg = i + 1;
                break;
            }
        }

        int en = Math.min(Board.getROW() - 1, x + 4);
        for (int i = x; i <= en; i++) {
            if (valueBoard[i][y] > 0 && valueBoard[i][y] != idPlayer) {
                en = i - 1;
                break;
            }
        }

        if (en - bg + 1 >= 5) {
            for (int i = bg; i <= x; i++) {
                int mx = 0;
                int checkIndexZero4 = -1;
                int checkIndexZero3_1 = -1;
                int checkIndexZero3_2 = -1;
                int checkIndex2_1 = -1;
                int checkIndex2_2 = -1;
                int en2 = Math.min(Board.getROW() - 1, i + 4);
//                System.out.println(i + " " + en2);
                if (en2 > en) break;
                for (int j = i; j <= en2; j++) {
//                    System.out.println(j + " " + y);
                    if (valueBoard[j][y] == idPlayer) {
                        mx++;
                        if (checkIndex2_1 == -1) {
                            checkIndex2_1 = j;
                            continue;
                        }
                        if (checkIndex2_1 != -1 && checkIndex2_2 == -1) {
                            checkIndex2_2 = j;
                            continue;
                        }
                    } else {
                        checkIndexZero4 = j;
                        if (checkIndexZero3_1 == -1) {
                            checkIndexZero3_1 = j;
                            continue;
                        }
                        if (checkIndexZero3_1 != -1) {
                            checkIndexZero3_2 = j;
                            continue;
                        }
                    }
                }
                if (mx == 5) {
                    list.add(new Point(mx, 0));
//                    break;
                } else if (mx == 4) {
                    list.add(new Point(mx, checkFour(checkIndexZero4, i, en2)));
                } else if (mx == 3) {
                    list.add(new Point(mx, checkThree(checkIndexZero3_1, checkIndexZero3_2, i, en2)));
                } else if (mx == 2) {
                    list.add(new Point(mx, checkTwo(checkIndex2_1, checkIndex2_2)));
                } else if (mx == 1) {
                    list.add(new Point(mx, 0));
                }
            }
        }

        return list;
    }

    private ArrayList<Point> getDiagonalMain(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Point> list = new ArrayList<>();

        int x = point.x;
        int y = point.y;

        int _min = Math.min(x, y);

        int beg_x = Math.max(0, x - 4);
        int beg_y = Math.max(0, y - 4);

        for (int i = x, j = y; i >= beg_x && j >= beg_y; i--, j--) {
            if (valueBoard[i][j] > 0 && valueBoard[i][j] != idPlayer) {
                beg_x = i + 1;
                beg_y = j + 1;
                break;
            }
        }

        int end_x = Math.min(Board.getROW() - 1, x + 4);
        int end_y = Math.min(Board.getCOL() - 1, y + 4);

        for (int i = x, j = y; i <= end_x && j <= end_y; i++, j++) {
            if (valueBoard[i][j] > 0 && valueBoard[i][j] != idPlayer) {
                end_x = i - 1;
                end_y = j - 1;
                break;
            }
        }

        if (end_x - beg_x + 1 >= 5) {

            for (int i = beg_x, j = beg_y; i <= x && j <= y; i++, j++) {


                int end_x1 = Math.min(Board.getROW() - 1, i + 4);
                int end_y1 = Math.min(Board.getCOL() - 1, j + 4);

                int mx = 0;
                int checkIndexZero4 = -1;
                int checkIndexZero3_1 = -1;
                int checkIndexZero3_2 = -1;
                int checkIndex2_1 = -1;
                int checkIndex2_2 = -1;

//                System.out.println(i + " " + j + " " + end_x1 + " " + end_y1);
                if (end_x1 > end_x) break;

                for (int i1 = i, j1 = j; i1 <= end_x1 && j1 <= end_y1; i1++, j1++) {
                    try {
                        if (valueBoard[i1][j1] == idPlayer) {
                            mx++;
                            if (checkIndex2_1 == -1) {
                                checkIndex2_1 = j1;
                                continue;
                            }
                            if (checkIndex2_1 != -1) {
                                checkIndex2_2 = j1;
                                continue;
                            }
                        } else {
                            checkIndexZero4 = j1;
                            if (checkIndexZero3_1 == -1) {
                                checkIndexZero3_1 = j1;
                                continue;
                            }
                            if (checkIndexZero3_1 != -1) {
                                checkIndexZero3_2 = j1;
                                continue;
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("try : " + i1 + " " + j1);
                        e.printStackTrace();
                    }
                }
                if (mx == 5) {
                    list.add(new Point(mx, 0));
                    break;
                } else if (mx == 4) {
                    list.add(new Point(mx, checkFour(checkIndexZero4, j, end_y1)));
                } else if (mx == 3) {
                    list.add(new Point(mx, checkThree(checkIndexZero3_1, checkIndexZero3_2, j, end_y1)));
                } else if (mx == 2) {
                    list.add(new Point(mx, checkTwo(checkIndex2_1, checkIndex2_2)));
                } else {
                    list.add(new Point(mx, 0));
                }

            }

        }

        return list;
    }


    private ArrayList<Point> getDiagonalSecond(Point point, int[][] valueBoard, int idPlayer) {
        ArrayList<Point> list = new ArrayList<>();

        int x = point.x;
        int y = point.y;

        int beg_x = Math.max(0, x - 4);
        int beg_y = Math.min(Board.getCOL() - 1, y + 4);

        for (int i = x, j = y; i >= beg_x && j <= beg_y; i--, j++) {
            if (valueBoard[i][j] > 0 && valueBoard[i][j] != idPlayer) {
                beg_x = i + 1;
                beg_y = j - 1;
                break;
            }
        }

        int end_x = Math.min(Board.getROW() - 1, x + 4);
        int end_y = Math.max(0, y - 4);

        for (int i = x, j = y; i <= end_x && j >= end_y; i++, j--) {
            if (valueBoard[i][j] > 0 && valueBoard[i][j] != idPlayer) {
                end_x = i - 1;
                end_y = j + 1;
                break;
            }
        }

        if (end_x - beg_x + 1 >= 5) {

            for (int i = beg_x, j = beg_y; i <= x && j >= y; i++, j--) {

                int end_x1 = Math.min(Board.getROW() - 1, i + 4);
                int end_y1 = Math.max(0, j - 4);

                int mx = 0;
                int checkIndexZero4 = -1;
                int checkIndexZero3_1 = -1;
                int checkIndexZero3_2 = -1;
                int checkIndex2_1 = -1;
                int checkIndex2_2 = -1;

//                System.out.println(i + " " + j + " " + end_x1 + " " + end_y1);
                if (end_x1 > end_x) break;

                for (int i1 = i, j1 = j; i1 <= end_x1 && j1 >= end_y1; i1++, j1--) {
                    try {

                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("try : " + i1 + " " + j1);
                        e.printStackTrace();
                    }
                    if (valueBoard[i1][j1] == idPlayer) {
                        mx++;
                        if (checkIndex2_1 == -1) {
                            checkIndex2_1 = j1;
                            continue;
                        }
                        if (checkIndex2_1 != -1) {
                            checkIndex2_2 = j1;
                            continue;
                        }
                    } else {
                        checkIndexZero4 = j1;
                        if (checkIndexZero3_1 == -1) {
                            checkIndexZero3_1 = j1;
                            continue;
                        }
                        if (checkIndexZero3_1 != -1) {
                            checkIndexZero3_2 = j1;
                            continue;
                        }
                    }
                }
                if (mx == 5) {
                    list.add(new Point(mx, 0));
                    break;
                } else if (mx == 4) {
                    list.add(new Point(mx, checkFour(checkIndexZero4, j, end_y1)));
                } else if (mx == 3) {
                    list.add(new Point(mx, checkThree(checkIndexZero3_1, checkIndexZero3_2, end_y1, j)));
                } else if (mx == 2) {
                    list.add(new Point(mx, checkTwo(checkIndex2_1, checkIndex2_2)));
                } else {
                    list.add(new Point(mx, 0));
                }

            }

        }

        return list;
    }


    private int checkFour(int checkIndexZero4, int bg, int en) {
        return (checkIndexZero4 == bg || checkIndexZero4 == en ? 0 : 1);
    }

    private int checkThree(int checkIndexZero3_1, int checkIndexZero3_2, int bg, int en) {
        if (checkIndexZero3_1 == bg && checkIndexZero3_2 == en) {
            return 0;
        }
        if (checkIndexZero3_1 + 1 == checkIndexZero3_2 && (checkIndexZero3_1 == bg || checkIndexZero3_2 == en)) {
            if (checkIndexZero3_1 == bg) return 0;
            if (checkIndexZero3_2 == en) return 0;
        }
        return 1;
    }

    private int checkTwo(int checkIndex2_1, int checkIndex2_2) {
        return (checkIndex2_1 + 1 == checkIndex2_2 ? 0 : 1);
    }


//    public static void main(String[] args) {
//
//        board.getValueBoard()[4][4] = 1;
//        board.getValueBoard()[5][5] = 0;
//        board.getValueBoard()[6][10] = 0;
//        board.getValueBoard()[7][10] = 0;
//        board.getValueBoard()[8][10] = 1;
//        board.getValueBoard()[9][10] = 1;
//        board.getValueBoard()[10][10] = 1;
//        board.getValueBoard()[11][9] = 1;
//        board.getValueBoard()[12][8] = 1;
//        board.getValueBoard()[13][7] = 1;
//        board.getValueBoard()[14][6] = 1;
//        board.getValueBoard()[15][5] = 1;
//        board.getValueBoard()[16][4] = 2;

//        int value = new Heuristic_2().atttack(new Point(10, 10), board.getValueBoard(), 1);
//        System.out.println(value);
//    }

}
