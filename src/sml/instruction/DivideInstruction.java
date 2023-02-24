package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

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
        if(value1 == 0 || value2 == 0){
            System.out.println("Unable to execute command \"" + this.toString() + "\" - / by zero. " +
                                "Please rewrite the program with legal instructions.");
            throw new ArithmeticException();
        }
        else {
            m.getRegisters().set(result, value1 / value2);
        }
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
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
