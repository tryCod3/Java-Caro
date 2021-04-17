package view;

import control.Heuristic;
import control.Heuristic_2;
import control.Heuristic_3;
import model.AI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import javax.swing.*;

public class Board extends JPanel implements Display {

    /**
     * tạo ROW hàng và COL cột
     */
    private static final int ROW = 13;
    private static final int COL = 13;
    private static boolean firstMove = true;

    public static boolean isFirstMove() {
        return firstMove;
    }

    public static void setFirstMove(boolean firstMove) {
        Board.firstMove = firstMove;
    }

    public static int getROW() {
        return ROW;
    }

    public static int getCOL() {
        return COL;
    }

    /**
     * first move ai
     */
    private static int nextMove;

    public static int getNextMove() {
        return nextMove;
    }

    public static void setNextMove(int stepMove) {
        nextMove = stepMove;
    }

    /**
     * score winning
     */
    private static final int scoreWin = (int) 1e7;
    private static final int way4 = (int) 1e5;
    private static final int way3 = (int) 1e3;
    private static final int way2 = (int) 1e1;

    public static int getWay4() {
        return way4;
    }

    public static int getWay3() {
        return way3;
    }

    public static int getWay2() {
        return way2;
    }

    public int getScoreWin() {
        return scoreWin;
    }

    // 1 , 2 , 3 , 4
    public static final int evalutionG[] = {1, 10, 1000, 50000};
    public static final int evalutionB[] = {0, 5, 5000, 25000};

    private static final Board instance = new Board();

    public static Board getBoard() {
        return instance;
    }

    private final ButtonXO buttonXO = ButtonXO.getInstance();
    private static final AI Ai = AI.getInstance();

    /**
     * bảng 2d , là những obj để Entity có thể play
     */
    private final ButtonXO[][] iconXO = new ButtonXO[ROW][COL];

    public ButtonXO getIconXO(int x, int y) {
        return iconXO[x][y];
    }

    /**
     * những Point đã được đánh
     */
    private final ArrayList<Point> wentGo;

    {
        wentGo = new ArrayList<>();
    }

    public ArrayList<Point> getWentGo() {
        return wentGo;
    }

    /**
     * bảng 2d , thể hiển tình hình của trò chơi
     * 1 : người
     * 2 : ai
     * 0 : null
     */
    private final int[][] valueBoard;

    {
        valueBoard = new int[ROW][COL];
    }

    public int[][] getValueBoard() {
        return valueBoard;
    }

    @Override
    public void setUpView() {
        // TODO Auto-generated method stub
        instance.setBounds(50, 50, 600, 550);
        instance.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        generatorBoard();
    }

    @Override
    public void reset() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (valueBoard[i][j] != 0) {
                    valueBoard[i][j] = 0;
                }
            }
        }
        wentGo.clear();
        setFirstMove(true);
        // off ai
        setNextMove(scoreWin);
        synchronized (Ai) {
            Ai.notifyAll();
        }
        instance.removeAll();
        instance.revalidate();
        instance.repaint();
    }

    // draw board for play game
    private void generatorBoard() {
        instance.setLayout(new GridLayout(ROW, COL, 2, 2));
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                iconXO[i][j] = buttonXO.generatorButton(i, j);
                instance.add(iconXO[i][j]);
            }
        }

        instance.revalidate();
        instance.repaint();
        // set Ai move first
        Thread thread = new Thread(Ai);
        setNextMove(0);
        thread.start();

    }

    /**
     * @param valueBoard bảng 2d
     * @return trả về giá trị , điểm của người choi AI và Player
     * <p>
     * AI
     * - đánh đường có thể dẫn tới 4
     * - chặn nếu phía player có đường win
     * - đánh tạo nhiều đường nhất có thể
     * </P>
     */
    public int getState(int[][] valueBoard, int idPlayer) {
        return new Heuristic_3().think(valueBoard, idPlayer);
    }

    /**
     * @return true if full else false
     */
    public boolean isFullBoard() {
        if (wentGo.size() == ROW * COL) {
            return true;
        }
        return false;
    }

    /**
     * @param x     vị trí x
     * @param y     vị trí y
     * @param value giá trị thay thế
     * @return thay đổi vị trí x , y trong valueBoard = value
     */
    public void changeValue(int x, int y, int value) {
        valueBoard[x][y] = value;
    }

    /**
     * @param path đường dẫn
     * @param xo   : obj
     * @return xét icon cho obj
     */
    public void changeIcon(String path, ButtonXO xo) {
        ImageIcon icon = new ImageIcon(path);
        xo.setIcon(icon);
        instance.revalidate();
        instance.repaint();
    }

    /**
     * @param icon cần tìm
     * @return trả về 1 Point có giá trị x và y trùng với vị trí xuất hiện của icon
     * trong iconXO[][] , else x = -1 , y = -1
     */
    public Point findIcon(ButtonXO icon) {
        int x = -1, y = -1;
        boolean isFind = false;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (iconXO[i][j].equals(icon)) {
                    x = i;
                    y = j;
                    isFind = true;
                    break;
                }
            }
            if (isFind) {
                break;
            }
        }

        return new Point(x, y);
    }

    /**
     * @param x : vị trí x
     * @param y : vị trí y
     * @return true nếu 2 điểm này nằm trong khoảng cho phép
     * (x >= 0 && x < ROW && y >= 0 && y < COL);
     */
    public boolean isInBoard(int x, int y) {
        return (x >= 0 && x < ROW && y >= 0 && y < COL);
    }

    private boolean isInRanger(int x, int lx1, int lx2) {
        return (x >= lx1 && x <= lx2);
    }

    /**
     * @param valueBoard bảng 2d
     * @return trả về một ArrayList<Point> có thể đi được
     */
    public ArrayList<Point> pointCanGo(int[][] valueBoard) {
        ArrayList<Point> arrays = new ArrayList<>();
        // đánh dấu , những ô false sẽ được kiểm tra
        boolean[][] checked = new boolean[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            Arrays.fill(checked[i], false);
        }

        for (Point location : wentGo) {
            // search 8 ô xung quanh 1 điểm
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int stepI = location.x + i;
                    int stepJ = location.y + j;
                    // nếu nó có trong bảng và ô đó chưa ai đánh
//                    if (wentGo.size() >= 1) {
//                        if (isInBoard(stepI, stepJ) && valueBoard[stepI][stepJ] == 0 && !checked[stepI][stepJ] &&
//                                isInRanger(stepI, rx1, rx2) && isInRanger(stepJ, ry1, ry2)) {
//                            arrays.add(new Point(stepI, stepJ));
//                            checked[stepI][stepJ] = true;
//                        }
//                    } else {
                    if (isInBoard(stepI, stepJ) && valueBoard[stepI][stepJ] == 0 && !checked[stepI][stepJ]) {
                        arrays.add(new Point(stepI, stepJ));
                        checked[stepI][stepJ] = true;
                    }
//                    }
                }
            }
        }

        return arrays;
    }

}
