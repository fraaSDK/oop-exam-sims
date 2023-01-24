package a04.e2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.IntBinaryOperator;

public class LogicImpl implements Logic {
    
    private int size;
    private List<Integer> numbers = new ArrayList<>();
    private List<String> operators = new ArrayList<>();
    private Move currentMoveType = Move.NUMBER;
    private List<Integer> pending = new ArrayList<>(2);
    private int tempResult;
    
    private static enum Move {
        NUMBER, OPERATOR
    }

    private static enum Operator {
        PLUS((a, b) -> a + b), MINUS((a, b) -> a - b), TIMES((a, b) -> a * b);
        Operator(IntBinaryOperator operator) { }
    }

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

    // TODO: add adjacency checks.
    @Override
    public boolean registerAction(Pair<Integer, Integer> action, String content) {
        if (isValid(content)) {
            addMove(content, currentMoveType);
            nextMoveType();
            if (hasPendingOperation()) {
                int j = 0;
                for (int i = 0; i < numbers.size(); i += 2) {
                    var n1 = numbers.get(i);
                    var n2 = numbers.get(i + 1);
                    // TODO: instead of getting operators with operation() method, use enum.
                    tempResult += operation(n1, n2, operators.get(j++));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int computeResult() {
        // TODO
        return tempResult;
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
            pending.add(Integer.parseInt(content));
            return numbers.add(Integer.parseInt(content));
        } else {
            return operators.add(content);
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
                throw new IllegalStateException();
        }
    }

    private boolean hasPendingOperation() {
        return pending.size() == 2;
    }

}
