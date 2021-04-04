package view;

import java.awt.Point;

import javax.swing.JButton;

import control.Mouse;

public class ButtonXO extends JButton {

    private int i ;
    private int j ;
    private static final ButtonXO instance = new ButtonXO();

    public static ButtonXO getInstance() {
        return instance;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    // create one button
    protected ButtonXO generatorButton(int i , int j) {
        ButtonXO button = new ButtonXO();
        button.setI(i);
        button.setJ(j);
        button.addMouseListener(new Mouse());
        return button;
    }


    public void removeMouse(){

    }

}
