package a04.e2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

public class LogicImpl implements Logic {
    
    private int size;
    private Optional<Pair<Integer, Integer>> previousMove = Optional.empty();
    private Queue<Integer> numbersQueue = new LinkedList<>();
    private Queue<String> operatorsQueue = new LinkedList<>();
    private Optional<Integer> tempResult = Optional.empty();
    private Move currentMoveType = Move.NUMBER;
    
    public LogicImpl(int size) {
        this.size = size;
    }

    // TODO: improve performances.
    @Override
    public Map<Pair<Integer, Integer>, String> initGrid() {
        Map<Pair<Integer, Integer>, String> result = new HashMap<>();
        var numbers = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        var operators = List.of("+", "-", "*");
        var rand = new Random();

        while (result.size() < (size * size) / 2) {
            var randPos = new Pair<>(rand.nextInt(size), rand.nextInt(size));
            result.putIfAbsent(randPos, numbers.get(rand.nextInt(numbers.size())).toString());
        }
        while (result.size() < (size * size)) {
            var randPos = new Pair<>(rand.nextInt(size), rand.nextInt(size));
            result.putIfAbsent(randPos, operators.get(rand.nextInt(operators.size())));
        }
        return result;
    }

    @Override
    public boolean registerAction(Pair<Integer, Integer> action, String content) {
        if (isValid(content) && isAdjacent(action)) {
            addMove(content, currentMoveType);
            nextMoveType();
            if (!numbersQueue.isEmpty()) {
                if (tempResult.isEmpty() && numbersQueue.size() == 2) {
                    var n1 = numbersQueue.poll();
                    var n2 = numbersQueue.poll();
                    tempResult = Optional.of(operation(n1, n2, operatorsQueue.poll()));
                } 
                if (tempResult.isPresent() && numbersQueue.size() == 1) {
                    var n = numbersQueue.poll();
                    tempResult = Optional.of(operation(tempResult.get(), n, operatorsQueue.poll()));
                }
            }
            previousMove = Optional.of(action);
            return true;
        }
        return false;
    }

    @Override
    public int computeResult() {
        return tempResult.get();
    }

    private boolean isAdjacent(Pair<Integer, Integer> move) {
        return previousMove.isEmpty() ||
                Math.abs(previousMove.get().getX() - move.getX()) +
                Math.abs(previousMove.get().getY() - move.getY()) == 1;
    }

    private boolean isValid(String name) {
        try {
            Integer.parseInt(name);
        } catch (NumberFormatException e) {
            return currentMoveType.equals(Move.OPERATOR);
        }
        return currentMoveType.equals(Move.NUMBER);
    }

    private void nextMoveType() {
        if (currentMoveType.equals(Move.NUMBER)) {
            currentMoveType = Move.OPERATOR;
        } else {
            currentMoveType = Move.NUMBER;
        }
    }

    private boolean addMove(String content, Move type) {
        if (type.equals(Move.NUMBER)) {
            return numbersQueue.add(Integer.parseInt(content));
        } else {
            return operatorsQueue.add(content);
        }
    }

    private int operation(int a, int b, String operation) {
        switch (operation) {
            case "+":
                return Math.addExact(a, b);
            case "-":
                return Math.subtractExact(a, b);
            case "*":
                return Math.multiplyExact(a, b);
            default:
                throw new IllegalStateException("Unsupported operation.");
        }
    }

}
