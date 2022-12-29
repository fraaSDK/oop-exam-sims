package a06b.e1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MiniAssemblyMachineImpl implements MiniAssemblyMachine {

    // Register id, amount to increase/decrease from given value.
    private Map<Integer, Integer> registersMap = new HashMap<>();
    private boolean returnStatus;
    private boolean canJump;
    private int operationsCount;

    @Override
    public void inc(int register) {
        checkReturn();
        if (!registersMap.containsKey(register)) {
            registersMap.put(register, 1);
        } else {
            registersMap.replace(register, registersMap.get(register) + 1);
        }
        operationsCount++;
    }

    @Override
    public void dec(int register) {
        checkReturn();
        if (!registersMap.containsKey(register)) {
            registersMap.put(register, -1);
        } else {
            registersMap.replace(register, registersMap.get(register) - 1);
        }
        operationsCount++;
    }

    @Override
    public void jnz(int register, int target) {
        checkReturn();
        // TODO
        if (register != 0) {
            canJump = true;
        }
        operationsCount++;
    }

    @Override
    public void ret() {
        returnStatus = true;
        operationsCount++;
    }

    @Override
    public List<Integer> execute(List<Integer> registers) {
        var result = new ArrayList<Integer>();
        for (var value : registers) {
            var index = registers.indexOf(value);
            var delta = getAmount(index);

            // If present we compute the result
            if (delta.isPresent()) {
                result.add(value + delta.get());
            } else {
                // Result is unchanged.
                result.add(value);
            }
        }
        registersMap.clear();
        operationsCount = 0;
        return result;
    }

    /*
     * This method returns an Optional containing the
     * current value associated with the given register index.
     * If the register index is higher than the size of the map,
     * (meaning that no operation was applied to the register) an
     * Optional.empty() will be returned.
     * This is later used in the execute() method, to determine
     * whether we should add/subtract the amount to the given value.
     */
    private Optional<Integer> getAmount(int register) {
        if (register > registersMap.size() - 1) {
            return Optional.empty();
        }
        return Optional.of(registersMap.get(register));
    }

    /*
     * By calling this method at the beginning of each
     * operation, we determine if those methods are allowed
     * to operate or not (in case the ret() method was called
     * beforehand).
     */
    private void checkReturn() {
        if (returnStatus) {
            return;
        }
    }

}
