package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.*;

/**
 * This class is a <code>final</code> class that stores the given <code>labels</code> provided in a Small Machine
 * Language (SML) program in a <code>HashMap(String, Integer)</code>. The <code>String</code> key stores the
 * <code>label</code> (of type <code>String</code>) and the <code>Integer</code> value stores the index to the
 * instruction i.e. the line of the relative instruction in the SML program.
 *
 * @author mcmanusniall
 * @version 1.0
 */
public final class Labels {

	/**
	 * Creates a <code>HashMap(String, Integer)</code>. The <code>String</code> key stores the
	 * <code>label</code> (of type <code>String</code>) and the <code>Integer</code> value stores the index to the
	 * instruction i.e. the line of the relative instruction in the SML program.
	 */
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the <code>Labels</code> HashMap.
	 *
	 * @param label the label.
	 * @param address the address the label refers to.
	 * @throws IllegalArgumentException - when label already exists
	 */
	public void addLabel(String label, int address) throws IllegalArgumentException {
		Objects.requireNonNull(label);
		if(labels.containsKey(label)) {
			System.out.println("Error: Label \"" + label +
								"\" is already associated with another instruction.");
			throw new IllegalArgumentException();
		}
		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label.
	 * @return the address the label refers to.
	 * @throws NullPointerException - when the label doesn't exist.
	 * @throws RuntimeException - captures unexpected errors.
	 */
	public int getAddress(String label) {
		// Where can NullPointerException be thrown here?
		// When the String label argument that is passed does not exist in the Labels HashMap,
		// a java.lang.NullPointerException is thrown.
		// e.g. java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()"
		// because the return value of "java.util.Map.get(Object)" is null
		try{
			return labels.get(label);
		}
		catch(NullPointerException e) {
			System.out.println("Error: Instruction labelled " + label + " does not exist.");
			throw e;
		}
		catch(RuntimeException e) {
			System.out.println("Error: An unexpected error has occurred.");
			throw e;
		}
	}

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}

	/**
	 * Returns a <code>String</code> representation of this instance in the form
	 * "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the <code>Labels</code> HashMap.
	 */
	@Override
	public String toString() {
		return labels.entrySet().stream()
									.map(e -> e.getKey() + " -> " + e.getValue())
									.collect(Collectors.joining(", ", "[", "]"));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Labels labels1 = (Labels) o;
		return labels.equals(labels1.labels);
	}

	@Override
	public int hashCode() {
		return Objects.hash(labels);
	}
}
