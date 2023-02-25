package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		if(labels.get(label) != null) {
			System.out.println(label + " is already associated with another Instruction. " + "Please revise the SML program");
			System.exit(1);
		}
		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 */
	public int getAddress(String label) throws RuntimeException {
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
	 * representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		// TODO: Implement the method using the Stream API (see also class Registers).
		return "";
	}

	// TODO: Implement equals and hashCode (needed in class Machine).

	/**
	 * Removes the labels
	 */
	public void reset() {
		labels.clear();
	}
}
