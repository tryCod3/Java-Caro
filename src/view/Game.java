package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Game extends JFrame implements Display {

    private final Board board = Board.getBoard();

    public static JLabel label;
    public JButton jbReset;

    public Game() {
        // TODO Auto-generated constructor stub
        board.setUpView();
        setUpView();
        label = new JLabel();
        label.setBounds(0, 0, 70, 30);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.white);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        jbReset = new JButton("reset");
        jbReset.setBounds(200, 0, 100, 30);
        jbReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        getContentPane().add(board);
        getContentPane().add(label);
        getContentPane().add(jbReset);
    }

    @Override
    public void setUpView() {
        // TODO Auto-generated method stub
        setSize(700, 700);
        setLocation(700, 100);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void reset() {
        board.reset();
        getContentPane().remove(board);
        board.setUpView();
        getContentPane().add(board);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Game game = new Game();
                game.setVisible(true);
            }
        });

    }

}
