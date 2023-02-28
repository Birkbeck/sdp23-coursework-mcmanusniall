package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

public class DivideInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;

    public static final String OP_CODE = "div";

    public DivideInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        if (value1 == 0 || value2 == 0) {
            System.out.println("Error: Unable to execute command \"" + this.toString() + "\" - / by zero. ");
            throw new ArithmeticException();
        } else {
            m.getRegisters().set(result, value1 / value2);
        }
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DivideInstruction that = (DivideInstruction) o;
        return Objects.equals(result, that.result) && Objects.equals(source, that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, source);
    }
}

