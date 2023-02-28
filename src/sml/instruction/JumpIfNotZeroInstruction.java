package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

// TODO: write a JavaDoc for the class

public class JumpIfNotZeroInstruction extends Instruction {
    private final RegisterName source;
    private final String instructionLabel;

    public static final String OP_CODE = "jnz";

    public JumpIfNotZeroInstruction(String label, RegisterName source, String instructionLabel) {
        super(label, OP_CODE);
        this.source = source;
        this.instructionLabel = instructionLabel;
    }

    @Override
    public int execute(Machine m) {
        if(m.getRegisters().get(source) == 0) {
            System.out.println("Did not execute \"" + this.toString() + "\" as " + source.toString() +
                                " = 0. Moving to execute next valid command.");
        }
        else if(m.getRegisters().get(source) != 0) {
            Instruction labelledInstruction = m.getProgram().get(m.getLabels().getAddress(instructionLabel));
            labelledInstruction.execute(m);
        }
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source + " " + instructionLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JumpIfNotZeroInstruction that = (JumpIfNotZeroInstruction) o;
        return source.equals(that.source) && instructionLabel.equals(that.instructionLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, instructionLabel);
    }
}
