package control;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import model.AI;
import model.Entity;
import model.InformationWays;
import view.Board;
import view.ButtonXO;
import view.Game;

import javax.swing.*;

public class Mouse implements MouseListener {

    private Board board = Board.getBoard();
    private final AI computer = AI.getInstance();

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

        if (Board.getNextMove() % 2 != 0 && Board.getNextMove() < board.getScoreWin()) {
            ButtonXO area = (ButtonXO) e.getSource();
            if (area.getIcon() == null) {
                Point point = board.findIcon(area);
                int x = point.x;
                int y = point.y;
                if (x != -1 && y != -1) {
                    board.getWentGo().add(point);
                    board.changeIcon("X.png", area);
                    board.changeValue(x, y, 1);
                }
                int value = board.getState(board.getValueBoard(), 1);
                if (value >= board.getScoreWin()) {
                    System.out.println("OK YOU WIN");
                    JOptionPane.showConfirmDialog(board , "YOU WIN");
                }else{
                    value = 0;
                }
                if(board.isFullBoard()){
                    value = board.getScoreWin();
                }
                Board.setNextMove(value);
                // call Ai
                synchronized (computer) {
                    computer.notifyAll();
                }
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        ButtonXO xo = (ButtonXO) e.getSource();
        String text = xo.getI() + "     " + xo.getJ();
        Game.label.setText(text);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
