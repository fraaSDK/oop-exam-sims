package a02a.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LogicImpl implements Logic {
    
    private final int size;
    private List<Pair<Integer, Integer>> visited = new ArrayList<>();
    private Pair<Integer, Integer> previousMove;
    private Direction nextDirection;
    private int counter;
    private boolean gameOver;
    private Random rand = new Random();

    public LogicImpl(int size) {
        this.size = size;
    }

    private static enum Direction {
        NORTH(0, -1), SOUTH(0, 1), EAST(1, 0), WEST(-1, 0);

        Direction(int x, int y) { }

        static Pair<Integer, Integer> get(Direction direction) {
            switch (direction) {
                case NORTH:
                    return new Pair<>(0, -1);
                case SOUTH:
                    return new Pair<>(0, 1);
                case EAST:
                    return new Pair<>(1, 0);
                case WEST:
                    return new Pair<>(-1, 0);
                default:
                    throw new IllegalStateException("Unsupported direction");
            }
        }
    }

    @Override
    public Pair<Integer, Pair<Integer, Integer>> makeMove() {
        if (counter == 0) {
            previousMove = new Pair<>(rand.nextInt(size), rand.nextInt(size));
            nextDirection = Direction.NORTH;
            visited.add(previousMove);
            return new Pair<>(counter++, previousMove);
        }

        previousMove = computeCoordinates(nextDirection);
        visited.add(previousMove);
        return new Pair<>(counter++, previousMove);
    }

    @Override
    public boolean isOver() {
        return gameOver;
    }

    private Pair<Integer, Integer> computeCoordinates(Direction direction) {
        Pair<Integer, Integer> nextMove;
        Pair<Integer, Integer> delta;
        Map<Direction, Boolean> tries = new HashMap<>();
        do {
            if (tries.size() == 4 && tries.entrySet().stream().allMatch(e -> e.getValue() == true)) {
                gameOver = true;
            }
            delta = Direction.get(direction);
            nextMove = new Pair<>(previousMove.getX() + delta.getX(), previousMove.getY() + delta.getY());
            tries.putIfAbsent(direction, true);
            direction = getRandomDirection();

            // System.out.println(tries);
        } while (!isValid(nextMove) && !gameOver);

        nextDirection = direction;
        tries.clear();

        return nextMove;
    }

    private Direction getRandomDirection() {
        List<Direction> directions = List.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        return directions.get(rand.nextInt(4));
    }

    private boolean isValid(Pair<Integer, Integer> move) {
        return move.getX() >= 0 && move.getX() < size &&
                move.getY() >= 0 && move.getY() < size &&
                !visited.contains(move);
    }
    
}
