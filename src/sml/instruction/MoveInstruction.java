package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

import java.util.Objects;

/**
 * This class is a subclass of the <code>abstract</code> class <code>sml.Instruction</code> and models the Small Machine
 * Language (SML) instruction that stores the <code>value</code>, of type <code>Integer</code>, in a given
 * <code>sml.Register</code>.
 *
 * @author mcmanusniall
 * @version 1.0
 */
public class MoveInstruction extends Instruction {
    private final RegisterName result;
    private final Integer value;
    public static final String OP_CODE = "mov";

    /**
     * Constructor: an instruction with a label, a 'mov' opcode, a register, and an integer.
     *
     * @param label optional label (can be null).
     * @param result the <code>sml.Register</code> (referenced by <code>Register.RegisterName</code>) where the value
     *               is to be stored.
     * @param value the integer to store in the register.
     */
    public MoveInstruction(String label, RegisterName result, Integer value) {
        super(label, OP_CODE);
        this.result = result;
        this.value = value;
    }

    /**
     * Executes the instruction, storing the value passed in the referenced register.
     *
     * @param m the machine the instruction runs on.
     * @return NORMAL_PROGRAM_COUNTER_UPDATE - assures the program counter is increased by 1 if successful.
     */
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

    /**
     * Returns a String representation of the instruction.
     *
     * @return a String representation of the instruction.
     */
    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + " " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveInstruction that = (MoveInstruction) o;
        return result.equals(that.result) && value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, value);
    }
}
