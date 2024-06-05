package co.za.javaPlayground.Server;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isIn(Position topLeft, Position bottomRight) {
        return x >= topLeft.getX() && x <= bottomRight.getX() && y <= topLeft.getY() && y >= bottomRight.getY();
    }
}
