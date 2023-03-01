package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * This class is a <code>final</code> class that represents the machine in which a Small Machine Language (SML)
 * program runs. An instance contains the <code>labels</code> (of type <code>sml.Labels</code>), the
 * <code>program</code> (of type <code>List<sml.Instruction></code>), and the 8 <code>registers</code>
 * (of type sml.Registers).
 *
 * @author mcmanusniall
 * @version 1.0
 */
public final class Machine {

	private final Labels labels = new Labels();

	private final List<Instruction> program = new ArrayList<>();

	private final Registers registers;

	// The program counter; it contains the index (in program)
	// of the next instruction to be executed.
	private int programCounter = 0;

	public Machine(Registers registers) {
		this.registers = registers;
	}

	/**
	 * Execute the program in program, beginning at instruction 0.
	 * Precondition: the program and its labels have been stored properly.
	 */
	public void execute() {
		programCounter = 0;
		registers.clear(); // resets the values of all registers to 0.
		while (programCounter < program.size()) { // while there are instructions in the ArrayList<Instruction>.
			Instruction ins = program.get(programCounter); //use the program counter as an index to fetch the instructions in sequence.
			// Add if statement that avoid executing a labelled instruction when read.
			// Labelled instructions should only be executed from within a jnz command.
			if(ins.label == null) {
				int programCounterUpdate = ins.execute(this);
				programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE) // if the programCounterUpdate is equal to NORMAL_PROGRAM_COUNTER_UPDATE
						? programCounter + 1 // add one to the program counter.
						: programCounterUpdate; // else program counter equals the value of programCounterUpdate.
			}
			else { programCounter = programCounter + 1; }
			// TODO: check code formatting
		}
	}

	/**
	 * Returns Labels object with field labels - a HashMap<String, Integer>
	 * object where the String object refers to the label e.g. ":f3" and
	 * the Integer object refers to the position of the instruction e.g. [0] is the first label.
	 * @return Labels object containing all the labels.
	 */
	public Labels getLabels() {
		return this.labels;
	}

	/**
	 * Returns the List<> containing all Instruction objects defined in the program.
	 * @return List<> object containing all the Instructions.
	 */
	public List<Instruction> getProgram() {
		return this.program;
	}

	/**
	 * Returns the Registers object with field registers - a HashMap<Register, Integer>
	 * object where Register is the register and the Integer is the value the register contains.
	 * @return Registers object containing all the registers.
	 */
	public Registers getRegisters() {
		return this.registers;
	}

	/**
	 * String representation of the program under execution.
	 *
	 * @return pretty formatted version of the code.
	 */
	@Override
	public String toString() {
		return program.stream()
				.map(Instruction::toString)
				.collect(Collectors.joining("\n"));
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Machine other) {
			return Objects.equals(this.labels, other.labels)
					&& Objects.equals(this.program, other.program)
					&& Objects.equals(this.registers, other.registers)
					&& this.programCounter == other.programCounter;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(labels, program, registers, programCounter);
	}
}
