package sml;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class Test {

    @org.junit.jupiter.api.Test
    public void testFileExists() {

    }

    @org.junit.jupiter.api.Test
    public void testFileDoesNotExists() {

    }
    @org.junit.jupiter.api.Test
    public void testMoveInstruction() {
        Machine m = new Machine(new Registers());
        Registers.Register registerName = Registers.Register.EAX;
        Instruction instruction = new sml.instruction.MoveInstruction("", registerName, 2);
        instruction.execute(m);
        Registers registers = m.getRegisters();
        assertEquals(2, registers.get(registerName));
    }

    @org.junit.jupiter.api.Test
    public void testAddTwoRegisters() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        registers.set(resultRegisterName, 2);
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 7);
        // Create Add Instruction
        Instruction addInstruction = new sml.instruction.AddInstruction("", resultRegisterName, sourceRegisterName);
        addInstruction.execute(m);
        // Test
        assertEquals(9, registers.get(resultRegisterName));
    }
    @org.junit.jupiter.api.Test
    public void testMultiplyTwoRegisters() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        registers.set(resultRegisterName, 10);
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 7);
        // Create Add Instruction
        Instruction multiplyInstruction = new sml.instruction.MultiplyInstruction("", resultRegisterName, sourceRegisterName);
        multiplyInstruction.execute(m);
        // Test
        assertEquals(70, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testSubtractTwoRegisters() {

    }



    @org.junit.jupiter.api.Test
    public void testDivideTwoRegisters() {

    }

    @org.junit.jupiter.api.Test
    public void testPrintRegisterContents() {

    }

    @org.junit.jupiter.api.Test
    public void testMoveIntegerBetweenTwoRegisters() {

    }

    @org.junit.jupiter.api.Test
    public void testContentsOfRegisterEqualZero() {

    }

    @org.junit.jupiter.api.Test
    public void testInstructionStructureIsCorrect() {

    }

    @org.junit.jupiter.api.Test
    public void testLabelFormattingCorrect() {

    }

    @org.junit.jupiter.api.Test
    public void testLabelDefinitionAlreadyStated() {

    }

    @org.junit.jupiter.api.Test
    public void testLabelledInstructionDoesNotExist() {

    }
}