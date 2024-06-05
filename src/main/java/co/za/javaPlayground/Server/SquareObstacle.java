package co.za.javaPlayground.Server;

public class SquareObstacle implements Obstacle {

    private final int bottomLeftX;
    private final int bottomLeftY;
    private final int size;

    public SquareObstacle(int bottomLeftX, int bottomLeftY, int size) {
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        this.size = size;
    }

    @Override
    public int getX1() {
        return bottomLeftX;
    }

    @Override
    public int getY1() {
        return bottomLeftY;
    }

    @Override
    public int getX2() {
        return bottomLeftX + size;
    }

    @Override
    public int getY2() {
        return bottomLeftY + size;
    }

    @Override
    public boolean blocksPosition(Position position) {
        return position.getX() >= bottomLeftX && position.getX() < bottomLeftX + size &&
                position.getY() >= bottomLeftY && position.getY() < bottomLeftY + size;
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
