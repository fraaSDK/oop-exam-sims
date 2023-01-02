package a04.e2;

public interface Logic {
    
    boolean isValidMove(int x, int y);

    boolean isOver();

    void addToGrid(int x, int y, boolean selected);
}
