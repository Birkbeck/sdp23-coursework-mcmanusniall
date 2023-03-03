package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import java.util.Objects;

/**
 * This class is a subclass of the <code>abstract</code> class <code>sml.Instruction</code> and models the Small Machine
 * Language (SML) instruction that performs a <i>division</i> of one register - <code>result</code>
 * (of type <code>sml.RegisterName</code>) - by a second register - <code>source</code> (of type
 * <code>sml.RegisterName</code>).
 *
 * @author mcmanusniall
 * @version 1.0
 */
public class DivideInstruction extends Instruction {
    private final RegisterName result;
    private final RegisterName source;
    public static final String OP_CODE = "div";

    /**
     * Constructor: an instruction with a label, a 'div' opcode and two registers.
     *
     * @param label optional label (can be null).
     * @param result the first <code>sml.Register</code> (referenced by <code>Register.RegisterName</code>).
     * 	 *               The result of executing the instruction is stored in this register.
     * @param source the second <code>sml.Register</code> (referenced by <code>Register.RegisterName</code>)
     *              - the divider.
     */
    public DivideInstruction(String label, RegisterName result, RegisterName source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    /**
     * Executes the instruction, dividing the contents of the first referenced register by the second and storing the
     * result in the first.
     * If the divider contains the value of 0, an <code>ArithmeticException</code> is thrown and the program exited.
     *
     * @param m the machine the instruction runs on.
     * @return NORMAL_PROGRAM_COUNTER_UPDATE - assures the program counter is increased by 1 if successful.
     * @throws ArithmeticException - when dividing by 0.
     */
    @Override
    public int execute(Machine m) {
        int value1 = m.getRegisters().get(result);
        int value2 = m.getRegisters().get(source);
        if (value1 == 0 || value2 == 0) {
            System.out.println("Error: Unable to execute command \"" + this + "\" - / by zero. ");
            throw new ArithmeticException();
        } else {
            m.getRegisters().set(result, value1 / value2);
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

