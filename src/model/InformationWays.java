package model;

import java.util.ArrayList;

public class InformationWays{

    private int x;
    private int y;

    private int numberRight;
    private int numberLeft;
    private int numberDown;
    private int numberDownRight;
    private int numberDownLeft;
    private int numberUp;
    private int numberUpRight;
    private int numberUpLeft;
    private int mider_Right_Left;
    private int mider_Up_Down;
    private int mider_DownLeft_UpRight;
    private int mider_DownRight_UpLeft;

    @Override
    public String toString() {
        return "InformationWays{" +
                "x=" + x +
                ", y=" + y +
                ", numberRight=" + numberRight +
                ", numberLeft=" + numberLeft +
                ", numberDown=" + numberDown +
                ", numberDownRight=" + numberDownRight +
                ", numberDownLeft=" + numberDownLeft +
                ", numberUp=" + numberUp +
                ", numberUpRight=" + numberUpRight +
                ", numberUpLeft=" + numberUpLeft +
                ", mider_Right_Left=" + mider_Right_Left +
                ", mider_Up_Down=" + mider_Up_Down +
                ", mider_DownLeft_UpRight=" + mider_DownLeft_UpRight +
                ", mider_DownRight_UpLeft=" + mider_DownRight_UpLeft +
                '}';
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

    public int getNumberLeft() {
        return numberLeft;
    }

    public void setNumberLeft(int numberLeft) {
        this.numberLeft = numberLeft;
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

    public int getNumberUp() {
        return numberUp;
    }

    public void setNumberUp(int numberUp) {
        this.numberUp = numberUp;
    }

    public int getNumberUpRight() {
        return numberUpRight;
    }

    public void setNumberUpRight(int numberUpRight) {
        this.numberUpRight = numberUpRight;
    }

    public int getNumberUpLeft() {
        return numberUpLeft;
    }

    public void setNumberUpLeft(int numberUpLeft) {
        this.numberUpLeft = numberUpLeft;
    }

    public int getMider_Right_Left() {
        return mider_Right_Left;
    }

    public void setMider_Right_Left(int mider_Right_Left) {
        this.mider_Right_Left = mider_Right_Left;
    }

    public int getMider_Up_Down() {
        return mider_Up_Down;
    }

    public void setMider_Up_Down(int mider_Up_Down) {
        this.mider_Up_Down = mider_Up_Down;
    }

    public int getMider_DownLeft_UpRight() {
        return mider_DownLeft_UpRight;
    }

    public void setMider_DownLeft_UpRight(int mider_DownLeft_UpRight) {
        this.mider_DownLeft_UpRight = mider_DownLeft_UpRight;
    }

    public int getMider_DownRight_UpLeft() {
        return mider_DownRight_UpLeft;
    }

    public void setMider_DownRight_UpLeft(int mider_DownRight_UpLeft) {
        this.mider_DownRight_UpLeft = mider_DownRight_UpLeft;
    }

    public ArrayList<Integer> listNumber() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(numberRight);
        list.add(numberLeft);

        list.add(numberDown);
        list.add(numberDownLeft);
        list.add(numberDownRight);

        list.add(numberUp);
        list.add(numberUpRight);
        list.add(numberUpLeft);

        list.add(mider_Right_Left);
        list.add(mider_Up_Down);
        list.add(mider_DownLeft_UpRight);
        list.add(mider_DownRight_UpLeft);

        return list;
    }

}
