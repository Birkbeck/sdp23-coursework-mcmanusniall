//package sml.instruction;
//
//import sml.Instruction;
//import sml.Machine;
//import sml.RegisterName;
//
//// TODO: write a JavaDoc for the class
//
//public class JumpIfNotZeroInstruction extends Instruction {
//    private final RegisterName source;
//    private final String labelReference;
//
//    public static final String OP_CODE = "jnz";
//
//    public JumpIfNotZeroInstruction(String label, RegisterName source, String labelReference) {
//        super(label, OP_CODE);
//        this.source = source;
//        this.labelReference = labelReference;
//    }
//
//    @Override
//    public int execute(Machine m) {
//
//        try {
//            if(m.getRegisters().get(source) == 0);
//            System.out.println("Unable to execute ");
//        } catch(Exception e){
//            e.printStackTrace(System.out);
//        }
//        return NORMAL_PROGRAM_COUNTER_UPDATE;
//    }
//
//    @Override
//    public String toString() {
//        return getLabelString() + getOpcode() + " " + result + " " + value;
//    }
//
//    @Override
//    public int hashCode() {
//        return 0;
//    }
//
//    @Override
//    public boolean equals() {
//        return false;
//    }
//}
