package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static sml.Registers.Register;

/**
 * This class is a <code>final</code> class that represents the translator that processes a Small Machine Language (SML)
 * program into internal form.
 *
 * @author mcmanusniall
 * @version 1.0
 */
public final class Translator {

    private final String fileName;
    private String line = "";
    public Translator(String fileName) {
        this.fileName =  fileName;
    }


    /**
     * Reads in the SML program file line by line and translates it into the internal format. Labels that are stated
     * are stored in the <code>labels</code> HashMap and instructions are stored in the <code>program</code> List.
     *
     * @param labels - empty HashMap object to store labels.
     * @param program - empty List to store <code>sml.Instruction</code> objects.
     * @throws IOException
     */
    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        try(var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();

            // Each iteration processes line and reads the next input line into "line"
            while(sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label);
                if(instruction != null) {
                    if(label != null)
                        labels.addLabel(label, program.size());
                    program.add(instruction);
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) throws IllegalArgumentException {

        if(line.isEmpty())
            return null;

        String instruction = line;
        String opcode = scan();

        try {
            switch(opcode) {
                case AddInstruction.OP_CODE -> {
                    String r = scan();
                    String s = scan();
                    return new AddInstruction(label, Register.valueOf(r), Register.valueOf(s));
                }
                case DivideInstruction.OP_CODE -> {
                    String r = scan();
                    String s = scan();
                    return new DivideInstruction(label, Register.valueOf(r), Register.valueOf(s));
                }
                case JumpIfNotZeroInstruction.OP_CODE -> {
                    String s = scan();
                    String instructionLabel = scan();
                    return new JumpIfNotZeroInstruction(label, Register.valueOf(s), instructionLabel);
                }
                case MoveInstruction.OP_CODE -> {
                    String r = scan();
                    Integer value = Integer.parseInt(scan());
                    return new MoveInstruction(label, Register.valueOf(r), value);
                }
                case MultiplyInstruction.OP_CODE -> {
                    String r = scan();
                    String s = scan();
                    return new MultiplyInstruction(label, Register.valueOf(r), Register.valueOf(s));
                }
                case PrintInstruction.OP_CODE -> {
                    String s = scan();
                    return new PrintInstruction(label, Register.valueOf(s));
                }
                case SubtractInstruction.OP_CODE -> {
                    String r = scan();
                    String s = scan();
                    return new SubtractInstruction(label, Register.valueOf(r), Register.valueOf(s));
                }
                // TODO: Then, replace the switch by using the Reflection API

                // TODO: Next, use dependency injection to allow this machine class
                //       to work with different sets of opcodes (different CPUs)

                default -> {
                    System.out.println("Error: Unknown operation \"" + opcode + "\" found.");
                    throw new IllegalArgumentException();
                }
            }
        } catch(IllegalArgumentException e) {
            System.out.println("Error: \"" + instruction + "\" contains an illegal argument.");
            throw e;
        }
    }

    /**
     * Scans through each word in a given line in an SML program and returns a label for each line.
     * If no label exists <code>null</code> is returned.
     * If a label exists, the <code>String</code> object of the label is returned.
     * @return the optional label of each line.
     */
    private String getLabel() {
        String word = scan();
        if(word.endsWith(":"))
            return word.substring(0, word.length() - 1);
        // undo scanning the word
        line = word + line;
        return null;
    }

    /**
     * Return the first word of each line and remove it from line.
     * A check is made to identify if there are more than 4 spaces in an SML instruction, thereby making it
     * illegally formatted. If this is the case, a <code>RuntimeException()</code> is thrown.
     * @return the first word of a given line. If there is no word, return "".
     **/
    private String scan() throws RuntimeException {
        line = line.trim();
        int whitespaceCounter = 0;

        // Check if the instruction line contains more than 3 spaces, which means
        // the formatting of the instruction is incorrect and should be revised.
        for(int i = 0; i < line.length(); i++)
            if(Character.isWhitespace(line.charAt(i))) {
                whitespaceCounter ++;
                if(whitespaceCounter > 3) {
                    System.out.println("Error: Detected an incorrectly formatted instruction - \"" +
                                        line + "\".");
                    throw new RuntimeException();
                }
            }

        for(int i = 0; i < line.length(); i++)
            if(Character.isWhitespace(line.charAt(i))) {
                String word = line.substring(0, i);
                line = line.substring(i);
                return word;
            }
        return line;
    }
}
