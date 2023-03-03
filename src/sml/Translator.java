package sml;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.lang.reflect.*;

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
     * @throws IOException - if input file is not found.
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
     * Translates the current line into an instruction with the given label.
     *
     * @param label the instruction label.
     * @return the new instruction.
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) throws IllegalArgumentException {

        if(line.isEmpty())
            return null;

        String instruction = line;
        String opcode = scan();
        Properties properties = new Properties();

        // Try with resources to access the key-value pairs (format: opcode-InstructionSubclass.class).
        try (var input = Translator.class.getResourceAsStream("/SMLinstructions.properties")) {
            // Load key-value pairs into properties var.
            properties.load(input);
            // Get the name of the instruction subclass from the opcode key.
            String instructionSubclass = properties.getProperty(opcode);
            // Create new class object.
            Class<?> classObject = Class.forName(instructionSubclass);
            // Get the constructor.
            Constructor<?> constructor = classObject.getDeclaredConstructors()[0];
            // Get constructor arguments.
            Object[] argsAsObjects = getArguments(label, constructor);
            // Create an instruction object with the parameters.
            return (Instruction) constructor.newInstance(argsAsObjects);
        }
        catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException |
               InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        catch(IllegalArgumentException e) {
            System.out.println("Error: \"" + instruction + "\" contains an illegal argument.");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Dynamically gets the arguments from a given line in an SML program, given the constructor of the
     * associated SML instruction.
     *
     * @param label the instruction label.
     * @param constructor the constructor method for an SML instruction (subclass).
     * @return An array of Objects (Object[]) containing the parameters for the related instruction.
     */
    private Object[] getArguments(String label, Constructor<?> constructor) throws RuntimeException {

            ArrayList<Object> args = new ArrayList<>();
            args.add(label);

        // Loop through the parameters, starting at the second to discard the label.
        for (int i = 1; i < constructor.getParameterCount(); i++) {
            // Get the first argument.
            String arg = scan();
            // Get the first parameter (that is not the label).
            Class<?> parameterType = constructor.getParameterTypes()[i];
            if (parameterType == RegisterName.class) {
                Register register = Register.valueOf(arg);
                args.add(register);
            } else if (parameterType == Integer.class) {
                Integer integer = Integer.valueOf(arg);
                args.add(integer);
            } else if (parameterType == String.class) {
                String string = String.valueOf(arg);
                args.add(string);
            }
        }
        return args.toArray();
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
