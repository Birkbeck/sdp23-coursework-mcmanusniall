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

    }

    @org.junit.jupiter.api.Test
    public void testSubtractTwoRegisters() {

    }

    @org.junit.jupiter.api.Test
    public void testMultiplyTwoRegisters() {

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