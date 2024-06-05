package co.za.javaPlayground.Server;

public class PitObstacle implements Obstacle {
    private final int x1;
    private final int y1;
    private final int x2;
    private final int y2;

    public PitObstacle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public int getX1() {
        return x1;
    }

    @Override
    public int getY1() {
        return y1;
    }

    @Override
    public int getX2() {
        return x2;
    }

    @Override
    public int getY2() {
        return y2;
    }

    @Override
    public boolean blocksPosition(Position position) {
        return position.getX() >= x1 && position.getX() <= x2 && position.getY() >= y1 && position.getY() <= y2;
    }

    @Override
    public boolean blocksPath(Position start, Position end) {
        for (int x = Math.min(start.getX(), end.getX()); x <= Math.max(start.getX(), end.getX()); x++) {
            for (int y = Math.min(start.getY(), end.getY()); y <= Math.max(start.getY(), end.getY()); y++) {
                if (blocksPosition(new Position(x, y))) {
                    return true;
                }
            }
        }
        return false;
    }
}
