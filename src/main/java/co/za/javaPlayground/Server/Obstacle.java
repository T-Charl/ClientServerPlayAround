package co.za.javaPlayground.Server;

public interface Obstacle {
    int getX1();
    int getY1();
    int getX2();
    int getY2();
    boolean blocksPosition(Position position);
    boolean blocksPath(Position start, Position end);
}
