package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is a <code>final</code> class that stores the 8 <code>sml.Registers</code> (of type
 * <code>HashMap<sml.Register, Integer></code>) that are accessed and referenced in a Small Machine Language (SML)
 * program. The key stores each unique <code>sml.Register</code> and the <code>Integer</code>
 * value stored within it (default = 0).
 * The <code>sml.Registers</code> are defined by a <code>sml.RegisterName</code>. The <code>sml.RegisterName</code> is
 * limited to <code>EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI</code>.
 *
 * @author mcmanusniall
 * @version 1.0
 */

public final class Registers {

    /**
     * Creates a <code>HashMap(String, Integer)</code>. The <code>Register</code> key stores the
     * <code>register</code> and the <code>Integer</code> value stores the integer stored in the index (default = 0).
     */
    private final Map<Register, Integer> registers = new HashMap<>();

    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI
    }
    /**
     * Clears the registers, setting their value to 0.
     */
    public Registers() {
        clear(); // the class is final
    }

    /**
     * Populates the HashMap<> registers with the Register
     * and resets the stored value in each register to 0.
     */
    public void clear() {
        for (Register register : Register.values())
            registers.put(register, 0);
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((Register)register);
    }

    /**
     * String representation of the registers.
     *
     * @return pretty formatted version of the code.
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return registers.hashCode();
    }
}
