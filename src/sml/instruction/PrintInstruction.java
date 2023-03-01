package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import java.util.Objects;

/**
 * This class is a subclass of the <code>abstract</code> class <code>sml.Instruction</code> and models the Small Machine
 * Language (SML) instruction that <i>prints</i> the <code>value</code>, of type <code>Integer</code>, stored in a
 * <code>sml.Register</code> - <code>source</code>, and accessed by the <code>sml.RegisterName</code>, to the console.
 *
 * @author mcmanusniall
 * @version 1.0
 */
public class PrintInstruction extends Instruction {
    private final RegisterName source;

    public static final String OP_CODE = "out";

    /**
     * Constructor: an instruction with a label, an 'out' opcode, and a register.
     *
     * @param label optional label (can be null).
     * @param source the sml.Register that is to be printed to the console alongside it's content.
     */
    public PrintInstruction(String label, RegisterName source) {
        super(label, OP_CODE);
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        String message = source.name() + " - " + m.getRegisters().get(source);
        System.out.println(message);
        return NORMAL_PROGRAM_COUNTER_UPDATE;
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrintInstruction that = (PrintInstruction) o;
        return source.equals(that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source);
    }
}
