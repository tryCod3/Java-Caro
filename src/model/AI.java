package model;

import java.awt.Point;

import view.Board;

import javax.swing.*;

public class AI extends Entity implements Runnable {

    private static final AI instance = new AI();
    private static final Board board = Board.getBoard();

    public static AI getInstance() {
        return instance;
    }

    @Override
    public void move(int valueBoard[][]) {
        // TODO Auto-generated method stub

        Point point = null;

        System.out.println("AI MOVE");

        if (Board.isFirstMove()) {
            point = new Point(Board.getROW() / 2, Board.getCOL() / 2);
            Board.setFirstMove(false);
        } else {
            point = getBestMove(valueBoard);
        }

        int x = point.x;
        int y = point.y;
        board.getWentGo().add(point);
        board.changeValue(x, y, 2);
        board.changeIcon("O.png", board.getIconXO(x, y));
        Board.setNextMove(1);
    }


    @Override
    public void run() {

        while (Board.getNextMove() < board.getScoreWin()) {

            move(board.getValueBoard());

            int value = board.getState(board.getValueBoard(), 2);
            if (value >= board.getScoreWin()) {
                JOptionPane.showConfirmDialog(board , "AI WIN");
                return;
            }
            if (board.isFullBoard()) {
                JOptionPane.showConfirmDialog(board , "YOU DRAW");
                return;
            }
            // wait player move
            synchronized (instance) {
                try {
                    System.out.println("PLAYER MOVE");
                    instance.wait();
                } catch (InterruptedException e) {
                    System.out.println("ai error move");
                    e.printStackTrace();
                }
            }

        }

        Board.setNextMove(Board.getNextMove() * 2);

    }
}
