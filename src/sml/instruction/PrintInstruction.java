package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/**
 * @author
 */

public class PrintInstruction extends Instruction {
    private final RegisterName source;

    public static final String OP_CODE = "out";

    public PrintInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        System.out.println(source.name() + " - " + m.getRegisters().get(source));
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
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
