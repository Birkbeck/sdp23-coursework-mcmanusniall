package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import java.util.Objects;

/**
 * This class is a subclass of the <code>abstract</code> class <code>sml.Instruction</code> and models the Small Machine
 * Language (SML) instruction that performs the conditional execution of a labelled instruction -
 * <code>instructionLabel</code> (of type <code>String</code>).
 * The condition is that the contents of the register - <code>source</code> (of type <code>sml.RegisterName</code>) -
 * are not equal to 0. This is often referred to as a <i>jump-if-not-zero</i> command.
 *
 * @author mcmanusniall
 * @version 1.0
 */
public class JumpIfNotZeroInstruction extends Instruction {
    private final RegisterName source;
    private final String instructionLabel;
    public static final String OP_CODE = "jnz";

    /**
     * Constructor: an instruction with a label, a 'jnz' opcode, a register, and a label.
     *
     * @param label optional label (can be null).
     * @param source the <code>sml.Register</code> (referenced by <code>Register.RegisterName</code>) to be referenced
     *               in the condition. The labelled instruction will not execute if the value stored in this register
     *               is 0.
     * @param instructionLabel the label of a labelled instruction in the SML program.
     */
    public JumpIfNotZeroInstruction(String label, RegisterName source, String instructionLabel) {
        super(label, OP_CODE);
        this.source = source;
        this.instructionLabel = instructionLabel;
    }

    /**
     * Executes the instruction, checking if the content of the referenced register is equal to 0.
     * If the register contains a value of 0, the labelled command is not executed and the program continues.
     * If the register contains a value that is not 0, the labelled command is executed.
     *
     * @param m the machine the instruction runs on.
     * @return NORMAL_PROGRAM_COUNTER_UPDATE - assures the program counter is increased by 1 if successful.
     */
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

    /**
     * Returns a String representation of the instruction.
     *
     * @return a String representation of the instruction.
     */
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
