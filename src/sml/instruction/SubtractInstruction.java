package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import java.util.Objects;

/**
 * This class is a subclass of the <code>abstract</code> class <code>sml.Instruction</code> and models the Small Machine
 * Language (SML) instruction that performs a <i>subtraction</i> of one register - <code>source</code> (of type
 * <code>sml.RegisterName</code>) - from a second register - <code>result</code> (of type <code>sml.RegisterName</code>).
 *
 * @author mcmanusniall
 * @version 1.0
 */
public class SubtractInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;
    public static final String OP_CODE = "sub";

    /**
     * Constructor: an instruction with a label, a 'sub' opcode and two registers.
     *
     * @param label optional label (can be null).
     * @param result the first <code>sml.Register</code> (referenced by <code>Register.RegisterName</code>).
     *               The result of executing the instruction is stored in this register.
     * @param source the second <code>sml.Register</code> (referenced (by <code>Register.RegisterName</code>) to be
     *               subtracted from the first.
     */
    public SubtractInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the instruction, subtracting the integer stored in the second register from the integer stored in the
     * first.
     *
     * @param m the machine the instruction runs on.
     * @return NORMAL_PROGRAM_COUNTER_UPDATE - assures the program counter is increased by 1 if successful.
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        m.getRegisters().set(result, value1 - value2);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    /**
     * Returns a String representation of the instruction.
     *
     * @return a String representation of the instruction.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubtractInstruction that = (SubtractInstruction) o;
        return result.equals(that.result) && source.equals(that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, source);
    }
}
