package sml;

import java.io.IOException;

public class Main {
	/**
	 * Initialises the system and executes the program.
	 *
	 * @param args name of the file containing the program text.
	 */
	public static void main(String... args) {
		if (args.length != 1) {
			System.err.println("Incorrect number of arguments - Machine <file> - required");
			System.exit(-1);
		}

		try {
			// Creates a new Translator Object
			Translator t = new Translator(args[0]);
			// Creates a new Machine Object
			Machine m = new Machine(new Registers());
			// Executes Translator.readAndTranslate method with the Labels object
			// - a HashMap<String, Integer> and the Instruction list (m.getLabels())
			// - a List<> containing all Instruction objects (m.getProgram())
			t.readAndTranslate(m.getLabels(), m.getProgram());

			System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");
			System.out.println(m);

			System.out.println("Beginning program execution.");
			try { m.execute(); }
			catch(Exception e) {
				System.out.println("Please revise your SML program before attempting to rerun.");
			}

			System.out.println("Ending program execution.");

			System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");
		}
		catch (IOException e) {
			System.out.println("Error reading the program from " + args[0]);
		}
	}
}
