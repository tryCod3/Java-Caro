package control;

import view.Board;

import java.awt.*;
import java.util.ArrayList;

public class Heuristic_3 {

    private static final Board board = Board.getBoard();

    private static final int arrAttack[][] = {
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
        return sum;
    }

    void show(ArrayList<Point> list , String name){
        System.out.println("name = " + name);
        for (Point i : list){
            System.out.println(i.toString());
        }
        System.out.println("-----------------");
    }

    public int atttack(Point point, int[][] valueBoard, int idPlayer) {

        ArrayList<Point> listHorizal = getHorizal(point, valueBoard, idPlayer);
        ArrayList<Point> listVertival = getVertical(point, valueBoard, idPlayer);
        ArrayList<Point> listDiagonalMain = getDiagonalMain(point, valueBoard, idPlayer);
        ArrayList<Point> listDiagonalSecond = getDiagonalSecond(point, valueBoard, idPlayer);

//        show(listHorizal , "ngang");
//        show(listVertival , "doc");
//        show(listDiagonalMain , "dcc");
//        show(listDiagonalSecond , "dcp");

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
                int en2 = Math.min(i + 4, Board.getCOL() - 1);

                for (int k = i; k <= Math.min(i + 4, Board.getCOL() - 1); k++) {
                    if (valueBoard[x][k] > 0 && valueBoard[x][k] != idPlayer) {
                        en2 = k - 1;
                        break;
                    }
                }
//                System.out.println(i + " " +  en2);
                if (en2 > en) break;
                for (int j = i; j <= en2; j++) {
                    if (valueBoard[x][j] == idPlayer) {
                        mx++;
                        if (checkIndex2_1 == -1) {
                            checkIndex2_1 = j;
                            continue;
                        }
                        checkIndex2_2 = j;
                        ;
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

                for (int k = i; k <= Math.min(Board.getROW() - 1, i + 4); k++) {
                    if (valueBoard[k][y] > 0 && valueBoard[k][y] != idPlayer) {
                        en2 = k - 1;
                        break;
                    }
                }

                if (en2 > en) break;
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

        int beg_x = x;
        int beg_y = y;
        int time = 4;

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

        int end_x = x;
        int eng_y = y;
        time = 4;

        while (time > 0 && end_x < Board.getROW() && eng_y < Board.getCOL()) {
            if (valueBoard[end_x][eng_y] > 0 && valueBoard[end_x][eng_y] != idPlayer) {
                end_x--;
                eng_y--;
                break;
            }
            if (end_x + 1 >= Board.getROW() || beg_y + 1 >= Board.getCOL()) break;
            end_x++;
            eng_y++;
            time--;
        }


        if (end_x - beg_x + 1 >= 5) {

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

        int beg_x = x;
        int beg_y = y;
        int time = 4;

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

        int end_x = x;
        int end_y = y;
        time = 4;

        while (time > 0 && end_x < Board.getCOL() && end_y >= 0) {
            if (valueBoard[end_x][end_y] > 0 && valueBoard[end_x][end_y] != idPlayer) {
                end_x--;
                end_y++;
                break;
            }
            if (end_x + 1 >= Board.getCOL() || end_y - 1 < 0) {
                break;
            }
            end_x++;
            end_y--;
            time--;
        }


        if (end_x - beg_x + 1 >= 5) {

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
                if (end_x1 > end_x) break;

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


    private int checkFour(int checkIndexZero4, int bg, int en) {
        return (checkIndexZero4 == bg || checkIndexZero4 == en ? 0 : 1);
    }

    private int checkThree(int checkIndexZero3_1, int checkIndexZero3_2, int bg, int en) {
        if (checkIndexZero3_1 == bg && checkIndexZero3_2 == en) {
            return 0;
        }
        if (Math.abs(checkIndexZero3_1 - checkIndexZero3_2) == 1
                && (checkIndexZero3_1 == bg || checkIndexZero3_2 == en)) {
            if (checkIndexZero3_1 == bg) return 0;
            return 0;
        }
        return 1;
    }

    private int checkTwo(int checkIndex2_1, int checkIndex2_2) {
        return (Math.abs(checkIndex2_1 - checkIndex2_2) == 1 ? 0 : 1);
    }


    public static void main(String[] args) {

        board.getValueBoard()[6][7] = 1;
        board.getValueBoard()[6][8] = 1;
        board.getValueBoard()[6][9] = 1;
        board.getValueBoard()[6][10] = 1;
        board.getValueBoard()[6][11] = 1;
//        board.getValueBoard()[9][10] = 1;
//        board.getValueBoard()[10][10] = 1;
//        board.getValueBoard()[11][9] = 1;
//        board.getValueBoard()[12][8] = 1;
//        board.getValueBoard()[13][7] = 1;
//        board.getValueBoard()[14][6] = 1;
//        board.getValueBoard()[15][5] = 1;
//        board.getValueBoard()[16][4] = 2;

        int value = new Heuristic_3().atttack(new Point(6 , 11), board.getValueBoard(), 1);
        System.out.println(value);
    }

}