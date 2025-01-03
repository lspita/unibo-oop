package a04.sol2;

import java.util.Random;

public class LogicImpl implements Logic {
    private Pair<Integer,Integer> rook;
    private Pair<Integer,Integer> king;
    private final Random random = new Random();
    private final int size;

    public LogicImpl(int size) {
        this.size = size;
    }

    @Override
    public void reset() {
        this.rook = new Pair<>(this.random.nextInt(size),this.random.nextInt(size));
        do {
            this.king = new Pair<>(this.random.nextInt(size),this.random.nextInt(size));
        } while (this.rook.equals(this.king));
    }

    @Override
    public boolean rookMove(int x, int y) {
        if (x != this.rook.getX() && y != this.rook.getY()){
            return false;
        }
        if (x == this.rook.getX() && y == this.rook.getY()){
            return false;
        }
        if (x == this.rook.getX() && x == this.king.getX() && inBetween(y, this.rook.getY(), this.king.getY())){
            return false;
        }
        if (y == this.rook.getY() && y == this.king.getY() && inBetween(x, this.rook.getX(), this.king.getX())){
            return false;
        }
        this.rook = new Pair<>(x,y);
        return true;
    }

    private boolean inBetween(int y1, int y2, int y) {
        return y1 < y2 ? (y > y1 && y < y2) : (y > y2 && y < y1);
    }

    @Override
    public void kingMove() {
        if (Math.abs(this.king.getX()-this.rook.getX())<=1 && Math.abs(this.king.getY()-this.rook.getY())<=1){
            this.king = this.rook;
        } else {
            Pair<Integer,Integer> newPos;
            do {
                newPos = new Pair<>(this.king.getX() + random.nextInt(3)-1, this.king.getY() + random.nextInt(3)-1);
            } while (newPos.equals(this.king) || notInBounds(newPos)); 
            this.king = newPos;       
        }
    }

    private boolean notInBounds(Pair<Integer, Integer> newPos) {
        return newPos.getX() < 0 || newPos.getX() >= size ||
            newPos.getY() < 0 || newPos.getY() >= size;
    }

    @Override
    public boolean isOver() {
        return this.rook.equals(this.king);
    }

    @Override
    public boolean hasRook(int x, int y) {
        return this.rook.getX() == x && this.rook.getY() == y;
    }

    @Override
    public boolean hasKing(int x, int y) {
        return this.king.getX() == x && this.king.getY() == y;
    }
}
