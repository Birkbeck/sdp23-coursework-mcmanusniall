package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class Test {

    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;

    @BeforeEach
    public void setUpOutputStream() {
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void windDownOutputStream() {
        System.setOut(originalOutput);
    }

    @org.junit.jupiter.api.Test
    public void testFileExists() {
        Translator t = new Translator("./test/resources/programWithLegalInstructions.txt");
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
        Instruction addInstruction = new sml.instruction.AddInstruction(null, resultRegisterName, sourceRegisterName);
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
        Instruction divideInstruction = new sml.instruction.DivideInstruction(null, resultRegisterName, sourceRegisterName);
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
        Instruction divideInstruction = new sml.instruction.DivideInstruction(null, resultRegisterName, sourceRegisterName);
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
        Instruction labelledInstruction = new sml.instruction.MoveInstruction("test", resultRegisterName, 8);
        // Add Labelled Instruction to the program.
        m.getProgram().add(labelledInstruction);
        // Add Labelled Instruction to the Labels HashMap.
        m.getLabels().addLabel("test", 0);
        // Create jnz Instruction
        Instruction jnzInstruction = new sml.instruction.JumpIfNotZeroInstruction(null, sourceRegisterName, "test");
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
        Instruction labelledInstruction = new sml.instruction.MoveInstruction("test", resultRegisterName, 8);
        // Add Labelled Instruction to the program.
        m.getProgram().add(labelledInstruction);
        // Add Labelled Instruction to the Labels HashMap.
        m.getLabels().addLabel("test", 0);
        // Create jnz Instruction
        Instruction jnzInstruction = new sml.instruction.JumpIfNotZeroInstruction(null, sourceRegisterName, "test");
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
        Instruction instruction = new sml.instruction.MoveInstruction(null, registerName, 2);
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
        Instruction multiplyInstruction = new sml.instruction.MultiplyInstruction(null, resultRegisterName, sourceRegisterName);
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
        // Create Print Instruction
        Instruction printInstruction = new sml.instruction.PrintInstruction(null, sourceRegisterName);
        printInstruction.execute(m);
        // Test
        assertEquals("EAX - 2", output.toString().trim());
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
        Instruction subtractInstruction = new sml.instruction.SubtractInstruction(null, resultRegisterName, sourceRegisterName);
        subtractInstruction.execute(m);
        // Test
        assertEquals(-5, registers.get(resultRegisterName));
    }

    @org.junit.jupiter.api.Test
    public void testLabelDefinitionAlreadyExists() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Add a label to the Labels object.
        m.getLabels().addLabel("test", 0);
        // Test
        assertThrows(IllegalArgumentException.class,
                () -> m.getLabels().addLabel("test", 1));
    }

    @org.junit.jupiter.api.Test
    public void testLabelHasWhitespaceCharacters() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Create a Translator
        Translator t = new Translator("./test/resources/programWithIllegalWhitespaceInstruction.txt");
        // Test
        assertThrows(RuntimeException.class, () -> t.readAndTranslate(m.getLabels(), m.getProgram()));
    }

    @org.junit.jupiter.api.Test
    public void testLegalProgramWorks() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Create a Translator
        Translator t = new Translator("./test/resources/programWithLegalInstructions.txt");

        try {
            t.readAndTranslate(m.getLabels(), m.getProgram());
            m.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @org.junit.jupiter.api.Test
    public void testInstructionIsEqual() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        registers.set(resultRegisterName, 2);
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 5);
        // Create Subtract Instruction
        Instruction subtractInstruction = new sml.instruction.SubtractInstruction(
                null, resultRegisterName, sourceRegisterName
        );
        Instruction subtractInstruction2 = new sml.instruction.SubtractInstruction(
                null, resultRegisterName, sourceRegisterName
        );
        // Test
        assertEquals(subtractInstruction, subtractInstruction2);
    }

    @org.junit.jupiter.api.Test
    public void testInstructionIsNotEqual() {
        // Create a Machine to execute the instructions.
        Machine m = new Machine(new Registers());
        // Get the Register object of the Machine
        Registers registers = m.getRegisters();
        // Populate result register with Integer.
        Registers.Register resultRegisterName = Registers.Register.EAX;
        registers.set(resultRegisterName, 2);
        // Populate source register with Integer.
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        registers.set(sourceRegisterName, 5);
        // Create Subtract Instruction
        Instruction subtractInstruction = new sml.instruction.SubtractInstruction(
                null, resultRegisterName, sourceRegisterName
        );
        Instruction addInstruction = new sml.instruction.AddInstruction(
                null, resultRegisterName, sourceRegisterName
        );
        // Test
        assertNotEquals(addInstruction, subtractInstruction);
    }

    @org.junit.jupiter.api.Test
    public void testAddInstructionToString() {
        Registers.Register resultRegisterName = Registers.Register.EAX;
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        Instruction addInstruction = new sml.instruction.AddInstruction(null, resultRegisterName, sourceRegisterName);
        assertEquals("add EAX ECX", addInstruction.toString());
    }

    @org.junit.jupiter.api.Test
    public void testDivideInstructionToString() {
        Registers.Register resultRegisterName = Registers.Register.EAX;
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        Instruction divideInstruction = new sml.instruction.DivideInstruction(null, resultRegisterName, sourceRegisterName);
        assertEquals("div EAX ECX", divideInstruction.toString());
    }
    @org.junit.jupiter.api.Test
    public void testJNZInstructionToString() {
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        Instruction jnzInstruction = new sml.instruction.JumpIfNotZeroInstruction(null, sourceRegisterName, "test");
        assertEquals("jnz ECX test", jnzInstruction.toString());
    }

    @org.junit.jupiter.api.Test
    public void testMoveInstructionToString() {
        Registers.Register resultRegisterName = Registers.Register.EAX;
        Instruction moveInstruction = new sml.instruction.MoveInstruction(null, resultRegisterName, 8);
        assertEquals("mov EAX 8", moveInstruction.toString());
    }

    @org.junit.jupiter.api.Test
    public void testMultiplyInstructionToString() {
        Registers.Register resultRegisterName = Registers.Register.EAX;
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        Instruction multiplyInstruction = new sml.instruction.MultiplyInstruction(null, resultRegisterName, sourceRegisterName);
        assertEquals("mul EAX ECX", multiplyInstruction.toString());
    }

    @org.junit.jupiter.api.Test
    public void testPrintInstructionToString() {
        Registers.Register resultRegisterName = Registers.Register.EAX;
        Instruction printInstruction = new sml.instruction.PrintInstruction(null, resultRegisterName);
        assertEquals("out EAX", printInstruction.toString());
    }

    @org.junit.jupiter.api.Test
    public void testSubtractInstructionToString() {
        Registers.Register resultRegisterName = Registers.Register.EAX;
        Registers.Register sourceRegisterName = Registers.Register.ECX;
        Instruction subtractInstruction = new sml.instruction.SubtractInstruction(null, resultRegisterName, sourceRegisterName);
        assertEquals("sub EAX ECX", subtractInstruction.toString());
    }
}

