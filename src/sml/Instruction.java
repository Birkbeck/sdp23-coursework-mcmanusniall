package sml;

/**
 * This class is an <code>abstract</code> class that models a Small Machine Language (SML) instruction.
 * Each <code>Instruction</code> is comprised of the fields:
 * <code>label</code> (of type <code>String</code>), representing an optionally passed label, <code>opcode</code> (of
 * type <code>String</code>), representing the SML operation to be undertaken, and
 * <code>NORMAL_PROGRAM_COUNTER_UPDATE</code> (of type <code>int</code>), representing the update to the program
 * counter.
 *
 * @author mcmanusniall
 * @version 1.0
 */
public abstract class Instruction {
	protected final String label;
	protected final String opcode;
	public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

	/**
	 * Constructor: an instruction with a label and an opcode
	 * (opcode must be an operation of the language)
	 *
	 * @param label optional label (can be null)
	 * @param opcode operation name
	 */
	public Instruction(String label, String opcode) {
		this.label = label;
		this.opcode = opcode;
	}

	/**
	 * Returns a <code>String</code> object of the label of an Instruction.
	 * @return the <code>label String</code> of an <code>sml.Instruction</code> object.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Returns a <code>String</code> object of the operator of an Instruction.
	 * @return the <code>opcode String</code> of an <code>sml.Instruction</code> object.
	 */
	public String getOpcode() {
		return opcode;
	}

	/**
	 * Executes the instruction in the given machine.
	 *
	 * @param machine the machine the instruction runs on.
	 * @return the new program counter (for jump instructions)
	 *          or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *          the instruction with the next address is to be executed.
	 */
	public abstract int execute(Machine machine);

	/**
	 * Returns a <code>String</code> object of the label of an Instruction as written in an SML program.
	 * @return the <code>label String</code> of a raw SML instruction (i.e. including the ':').
	 */
	protected String getLabelString() {
		return (getLabel() == null) ? "" : getLabel() + ": ";
	}

	// What does abstract in the declaration below mean?
	// The responsibility of the implementation of the method is placed on the subclass.
	// The subclass can implement the method using the @Override tag.

	/**
	 * Returns a String representation of the instruction.
	 *
	 * @return a String representation of the instruction.
	 */
	@Override
	public abstract String toString();

	@Override
	public abstract int hashCode();

	public abstract boolean equals(Object o);
}
