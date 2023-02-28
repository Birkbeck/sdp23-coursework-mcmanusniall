package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

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
        String message = source.name() + " - " + m.getRegisters().get(source);
        System.out.println(message);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintInstruction that = (PrintInstruction) o;
        return source.equals(that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source);
    }
}
