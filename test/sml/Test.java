package sml;

import org.junit.jupiter.api.BeforeEach;
import sml.instruction.PrintInstruction;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class Test {

    @org.junit.jupiter.api.Test
    public void testFileExists() {
        Translator t = new Translator("./program.txt");
        Machine m = new Machine(new Registers());
        assertDoesNotThrow(() -> t.readAndTranslate(m.getLabels(), m.getProgram()));
    }

    @org.junit.jupiter.api.Test
    public void testFileDoesNotExist() {
        Translator t = new Translator("test.txt");
        Machine m = new Machine(new Registers());
        assertThrows(IOException.class, () -> t.readAndTranslate(m.getLabels(), m.getProgram()));
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
        Instruction addInstruction = new sml.instruction.AddInstruction(
                "", resultRegisterName, sourceRegisterName
        );
        addInstruction.execute(m);
        // Test
        assertEquals(9, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testDivideTwoRegisters() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        registers.set(resultRegisterName, 100);
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 5);
        // Create Divide Instruction
        Instruction divideInstruction = new sml.instruction.DivideInstruction(
                "", resultRegisterName, sourceRegisterName
        );
        divideInstruction.execute(m);
        // Test
        assertEquals(20, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testDivideTwoRegistersWhenOneContainsZero() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        registers.set(resultRegisterName, 0);
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 5);
        // Create Divide Instruction
        Instruction divideInstruction = new sml.instruction.DivideInstruction(
                "", resultRegisterName, sourceRegisterName
        );
        // Test
        assertThrows(ArithmeticException.class, () -> divideInstruction.execute(m));
    }

    @org.junit.jupiter.api.Test
    public void testRegisterDoesNotExist() {
        // TODO:
    }

    @org.junit.jupiter.api.Test
    public void testJNZInstructionExecutesIfNotZero() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 5);
        // Create Labelled Instruction
        Instruction labelledInstruction = new sml.instruction.MoveInstruction(
                "test", resultRegisterName, 8
        );
        // Add Labelled Instruction to the program.
        m.getProgram().add(labelledInstruction);
        // Add Labelled Instruction to the Labels HashMap.
        m.getLabels().addLabel("test", 0);
        // Create jnz Instruction
        Instruction jnzInstruction = new sml.instruction.JumpIfNotZeroInstruction(
                "", sourceRegisterName,"test"
        );
        // Test
        jnzInstruction.execute(m);
        assertEquals(8, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testJNZInstructionWhenRegisterContainsZero() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 0);
        // Create Labelled Instruction
        Instruction labelledInstruction = new sml.instruction.MoveInstruction(
                "test", resultRegisterName, 8
        );
        // Add Labelled Instruction to the program.
        m.getProgram().add(labelledInstruction);
        // Add Labelled Instruction to the Labels HashMap.
        m.getLabels().addLabel("test", 0);
        // Create jnz Instruction
        Instruction jnzInstruction = new sml.instruction.JumpIfNotZeroInstruction(
                "", sourceRegisterName,"test"
        );
        jnzInstruction.execute(m);
        // Test
        assertEquals(0, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testJNZInstructionLabelDoesNotExist() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        assertThrows(NullPointerException.class, () -> m.getLabels().getAddress("test"));
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
        Instruction multiplyInstruction = new sml.instruction.MultiplyInstruction(
                "", resultRegisterName, sourceRegisterName
        );
        multiplyInstruction.execute(m);
        // Test
        assertEquals(70, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testPrintRegisterContents() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.EAX;
        registers.set(sourceRegisterName, 2);
        // Create Add Instruction
        Instruction printInstruction = new sml.instruction.PrintInstruction("", sourceRegisterName);
        printInstruction.execute(m);
        // TODO: Add assertion.
    }

    @org.junit.jupiter.api.Test
    public void testSubtractTwoRegisters() {
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
        // Create Subtract Instruction
        Instruction subtractInstruction = new sml.instruction.SubtractInstruction(
                "", resultRegisterName, sourceRegisterName
        );
        subtractInstruction.execute(m);
        // Test
        assertEquals(-5, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testLabelDefinitionAlreadyExists() {
        Machine m = new Machine(new Registers());
        m.getLabels().addLabel("test", 0);
        assertThrows(IllegalArgumentException.class,
                () -> m.getLabels().addLabel("test", 0));
    }

    @org.junit.jupiter.api.Test
    public void testInstructionStructureIsCorrect() {
    // TODO: test unknown instruction throws exception
    }


    @org.junit.jupiter.api.Test
    public void testLegalProgram() {
        Translator t = new Translator("./test/resources/programWithLegalInstructions.txt");
        Machine m = new Machine(new Registers());
        try {
            t.readAndTranslate(m.getLabels(), m.getProgram());
            m.execute();
        } catch (Exception e) {}
        Registers registers = m.getRegisters();

        assertEquals(7, registers.get(Registers.Register.EAX));
        assertEquals(2, registers.get(Registers.Register.EBX));
        assertEquals(60, registers.get(Registers.Register.ECX));
        assertEquals(10, registers.get(Registers.Register.EDX));
        assertEquals(10, registers.get(Registers.Register.ESP));
        assertEquals(2, registers.get(Registers.Register.EBP));
        assertEquals(98, registers.get(Registers.Register.ESI));
        assertEquals(5, registers.get(Registers.Register.EDI));
    }
}
