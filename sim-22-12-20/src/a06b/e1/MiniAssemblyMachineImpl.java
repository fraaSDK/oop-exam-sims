package a06b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MiniAssemblyMachineImpl implements MiniAssemblyMachine {

    public interface Instruction {
        Pair<Optional<Integer>, List<Integer>> execute(int index, List<Integer> registers);
    }

    private List<Instruction> instructions = new ArrayList<>();

    @Override
    public void inc(int register) {
        Instruction instruction = (i, r) -> {
            r.set(register, r.get(register) + 1);
            return new Pair<>(Optional.of(i + 1), r);
        };
        instructions.add(instruction);
    }

    @Override
    public void dec(int register) {
        Instruction instruction = (i, r) -> {
            r.set(register, r.get(register) - 1);
            return new Pair<>(Optional.of(i + 1), r);
        };
        instructions.add(instruction);
    }

    @Override
    public void jnz(int register, int target) {
        Instruction instruction = (i, r) -> new Pair<>(Optional.of(r.get(register) != 0 ? target : i + 1), r);
        instructions.add(instruction);
    }

    @Override
    public void ret() {
        Instruction instruction = (i, r) -> new Pair<>(Optional.empty(), r);
        instructions.add(instruction);
    }

    @Override
    public List<Integer> execute(List<Integer> registers) {
        List<Integer> registersCopy = new ArrayList<>(registers);
        
        int line = 0;
        while (true) {
            if (instructions.size() <= line) {
                throw new IllegalStateException();
            }
            var res = instructions.get(line).execute(line, registersCopy);
            if (res.getX().isEmpty()) {
                return res.getY();
            } else {
                line = res.getX().get();
                registersCopy = res.getY();
            }
        }
    }

}
