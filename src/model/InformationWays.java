package model;

import java.util.ArrayList;

public class InformationWays implements Comparable<InformationWays> {

    private int x;
    private int y;

    private int numberRight;
    private int numberDown;
    private int numberDownRight;
    private int numberDownLeft;

    public InformationWays(int x, int y, int numberRight, int numberDown, int numberDownRight, int numberDownLeft) {
        this.x = x;
        this.y = y;
        this.numberRight = numberRight;
        this.numberDown = numberDown;
        this.numberDownRight = numberDownRight;
        this.numberDownLeft = numberDownLeft;
    }

    @Override
    public String toString() {
        return "InformationWays{" +
                "x=" + x +
                ", y=" + y +
                ", numberRight=" + numberRight +
                ", numberDown=" + numberDown +
                ", numberDownRight=" + numberDownRight +
                ", numberDownLeft=" + numberDownLeft +
                '}';
    }

    public InformationWays() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumberRight() {
        return numberRight;
    }

    public void setNumberRight(int numberRight) {
        this.numberRight = numberRight;
    }

    public int getNumberDown() {
        return numberDown;
    }

    public void setNumberDown(int numberDown) {
        this.numberDown = numberDown;
    }

    public int getNumberDownRight() {
        return numberDownRight;
    }

    public void setNumberDownRight(int numberDownRight) {
        this.numberDownRight = numberDownRight;
    }

    public int getNumberDownLeft() {
        return numberDownLeft;
    }

    public void setNumberDownLeft(int numberDownLeft) {
        this.numberDownLeft = numberDownLeft;
    }

    public int getSum() {
        return numberRight + numberDown + numberDownLeft + numberDownRight;
    }

    public boolean haveNumber(int number) {
        return (numberRight == number || numberDown == number || numberDownRight == number || numberDownLeft == number);
    }

    public ArrayList<Integer> listNumber() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(numberRight);
        list.add(numberDown);
        list.add(numberDownLeft);
        list.add(numberDownRight);
        return list;
    }

    @Override
    public int compareTo(InformationWays o) {
        // tang dan
        return o.getSum() - this.getSum();
    }
}
