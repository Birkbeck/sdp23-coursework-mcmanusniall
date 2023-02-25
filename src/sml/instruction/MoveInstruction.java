package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

public class MoveInstruction extends Instruction {
    private final RegisterName result;
    private final Integer value;

    public static final String OP_CODE = "mov";

    public MoveInstruction(String label, RegisterName result, Integer value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    @Override
    public int execute(Machine m) {
        try {
            m.getRegisters().set(result, value);
        } catch(Exception e){
            System.out.println("Error: An unexpected error has occurred.");
            throw e;
        }
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + value;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals() {
        return false;
    }
}
