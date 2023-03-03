package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers;

import java.util.Objects;

/**
 * This class is a subclass of the <code>abstract</code> class <code>sml.Instruction</code> and models the Small Machine
 * Language (SML) instruction that performs an <i>addition</i> of two registers - <code>result</code> (of type
 * <code>sml.RegisterName</code>) and <code>source</code> (of type <code>sml.RegisterName</code>).
 *
 * @author mcmanusniall
 * @version 1.0
 */
public class AddInstruction extends Instruction {
	private final RegisterName result;
	private final RegisterName source;
	public static final String OP_CODE = "add";

	/**
	 * Constructor: an instruction with a label, an 'add' opcode and two registers.
	 *
	 * @param label optional label (can be null).
	 * @param result the first <code>sml.Register</code> (referenced by <code>Register.RegisterName</code>).
	 *               The result of executing the instruction is stored in this register.
	 * @param source the second <code>sml.Register</code> (referenced (by <code>Register.RegisterName</code>) to be
	 *               added to the first.
	 */
	public AddInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	/**
	 * Executes the instruction, adding the contents of the two referenced registers and storing the result in the
	 * first.
	 *
	 * @param m the machine the instruction runs on.
	 * @return NORMAL_PROGRAM_COUNTER_UPDATE - assures the program counter is increased by 1 if successful.
	 */
	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 + value2);
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
		AddInstruction that = (AddInstruction) o;
		return result.equals(that.result) && source.equals(that.source);
	}

	@Override
	public int hashCode() {
		return Objects.hash(result, source);
	}
}
