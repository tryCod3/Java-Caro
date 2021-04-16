package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import control.Heuristic_2;
import view.Board;

public abstract class Entity {

    private final Board board = Board.getBoard();
    private static final int depth = 3;

    // move AI AND PLAYER
    public abstract void move(int[][] valueBoard);

    protected Point getBestMove(int[][] valueBoard) {
        // chạy những node có thể đi và thử xem đi node nò là tốt nhất

        Resurt bestMove = minimaxAlphaBeta(valueBoard, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        System.out.println(bestMove.toString());
//        System.out.println("new ======================================");
        return new Point(bestMove.x, bestMove.y);
    }

    // thuật toán
    private Resurt minimaxAlphaBeta(int[][] valueBoard, int depth, int alpha, int beta, boolean isMax) {

        int valuePlayer = board.getState(valueBoard, 1);
        int valueAi = board.getState(valueBoard, 2);
        boolean isFull = board.isFullBoard();

        ArrayList<Point> listCango = board.pointCanGo(valueBoard);

        if (isNodeTree(depth) || isNodeTree(listCango.size()) || someThingWin(valuePlayer, valueAi) || isFull) {
            if (valuePlayer == 0) valuePlayer = 1;
            if (valueAi == 0) valueAi = 1;
            if (isMax) valueAi <<= 1;
            else valuePlayer <<= 1;
//            System.out.println("Ai = " + valueAi + " player = " + valuePlayer);
            int value = valueAi - valuePlayer;
            if (value > 0) value += depth;
            else if (value < 0) value -= depth;
            return new Resurt(0, 0, value);
        }


        int id = 2;
        ArrayList<Resurt> sortList = new ArrayList<>();
        Iterator<Point> iterator = listCango.iterator();
        while (iterator.hasNext()) {
            Point i = iterator.next();
            valueBoard[i.x][i.y] = 2;
            board.getWentGo().add(i);
            int attack = new Heuristic_2().atttack(i, valueBoard, id);
            sortList.add(new Resurt(i.x, i.y, attack));
            valueBoard[i.x][i.y] = 0;
            board.getWentGo().remove(i);
        }
        Collections.sort(sortList);

        if (isMax) {
            Resurt bestMove = new Resurt(0, 0, Integer.MIN_VALUE);
            for(Resurt j : sortList){
                Point i = new Point(j.x , j.y);
                valueBoard[i.x][i.y] = 2;
                board.getWentGo().add(i);
                Board.setNextMove(1);
                Resurt tmp = minimaxAlphaBeta(valueBoard, depth - 1, alpha, beta, !isMax);
                Board.setNextMove(0);
                board.getWentGo().remove(i);
                valueBoard[i.x][i.y] = 0;
                if (tmp.score > bestMove.score) {
                    tmp.x = i.x;
                    tmp.y = i.y;
                    bestMove = tmp;
                }
                if (bestMove.score >= beta) {
                    return new Resurt(bestMove.x, bestMove.y, bestMove.score);
                }
                alpha = Math.max(alpha, bestMove.score);
                if (alpha >= beta) break;
            }
            return bestMove;
        } else {
            Resurt bestMove = new Resurt(0, 0, Integer.MAX_VALUE);
            for(Resurt j : sortList){
                Point i = new Point(j.x , j.y);
                valueBoard[i.x][i.y] = 1;
                board.getWentGo().add(i);
                Board.setNextMove(0);
                Resurt tmp = minimaxAlphaBeta(valueBoard, depth - 1, alpha, beta, !isMax);
                Board.setNextMove(1);
                board.getWentGo().remove(i);
                valueBoard[i.x][i.y] = 0;
                if (tmp.score < bestMove.score) {
                    tmp.x = i.x;
                    tmp.y = i.y;
                    bestMove = tmp;
                }
                if (bestMove.score <= alpha) {
                    return new Resurt(bestMove.x, bestMove.y, bestMove.score);
                }
                beta = Math.min(beta, bestMove.score);
                if (alpha >= beta) break;
            }
            return bestMove;
        }

    }


    private boolean isNodeTree(int depth) {
        return depth == 0;
    }

    private boolean someThingWin(int player, int ai) {
        return (player >= board.getScoreWin() || ai >= board.getScoreWin());
    }

    class Resurt implements Comparable<Resurt> {
        private int x;
        private int y;
        private int score;

        public Resurt(int x, int y, int score) {
            this.x = x;
            this.y = y;
            this.score = score;
        }

        @Override
        public String toString() {
            return "Resurt{" +
                    "x=" + x +
                    ", y=" + y +
                    ", score=" + score +
                    '}';
        }

        @Override
        public int compareTo(Resurt o) {
            return o.score - score;
        }
    }

}
