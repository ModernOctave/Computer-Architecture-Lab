package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;

import generic.Operand.OperandType;


public class Simulator {
		
	static FileInputStream inputcodeStream = null;
	
	public static void setupSimulation(String assemblyProgramFile)
	{	
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	public static void assemble(String objectProgramFile)
	{

        try {
            //1. open the objectProgramFile in binary mode
            FileOutputStream outputStream = new FileOutputStream(objectProgramFile);

            //2. write the firstCodeAddress to the file
            outputStream.write(to4ByteArray(ParsedProgram.firstCodeAddress));

            //3. write the data to the file
            for (int i = 0; i < ParsedProgram.data.size(); i++) {
                outputStream.write(to4ByteArray(ParsedProgram.data.get(i)));
            }

            //4. assemble one instruction at a time, and write to the file
            for (int i = 0; i < ParsedProgram.code.size(); i++) {
                Instruction instruction = ParsedProgram.code.get(i);

                String instructionString = "";
                Long instructionLong = 0L;

                // Find opcode string
                String opcode = Integer.toBinaryString(instruction.getOperationType().ordinal());
                while (opcode.length() < 5) {
                    opcode = "0" + opcode;
                }

                switch (getInstructionFormat(instruction)) {
                    // R3 Format
                    case 6: {
                        // Find rs1 string
                        Operand operand = instruction.getSourceOperand1();
                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 1 is not a register in R3 format instruction", i+1));
                        }
                        String rs1 = Integer.toBinaryString(operand.getValue());
                        while (rs1.length() < 5) {
                            rs1 = "0" + rs1;
                        }

                        // Find rs2 string
                        operand = instruction.getSourceOperand2();
                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 2 is not a register in R3 format instruction", i+1));
                        }
                        String rs2 = Integer.toBinaryString(operand.getValue());
                        while (rs2.length() < 5) {
                            rs2 = "0" + rs2;
                        }

                        // Find rd string
                        operand = instruction.getDestinationOperand();
                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Destination operand is not a register in R3 format instruction", i+1));
                        }
                        String rd = Integer.toBinaryString(operand.getValue());
                        while (rd.length() < 5) {
                            rd = "0" + rd;
                        }

                        instructionString = opcode + rs1 + rs2 + rd;
                        while(instructionString.length() < 32) {
                            instructionString = instructionString + "0";
                        }
                        instructionLong = Long.parseLong(instructionString, 2);
                        
                        break;
                    }

                    // R2I Format Immediate Arithematic
                    case 5: {

                        // Find rs1 string 
                        Operand operand = instruction.getSourceOperand1();
                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 1 is not a register in R2I format instruction", i+1));
                        }

                        String rs1 = Integer.toBinaryString(operand.getValue());
                        while (rs1.length() < 5) {
                            rs1 = "0" + rs1;
                        }

                        // Find rd string
                        operand = instruction.getDestinationOperand();

                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Destination operand is not a register in R2I format instruction", i+1));
                        }

                        String rd = Integer.toBinaryString(operand.getValue());
                        while (rd.length() < 5) {
                            rd = "0" + rd;
                        }

                        // Find imm string
                        operand = instruction.getSourceOperand2();

                        if (operand.getOperandType() != OperandType.Immediate) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 2 is not an immediate in R2I format instruction", i+1));
                        }

                        String imm = Integer.toBinaryString(operand.getValue());
                        while (imm.length() < 17) {
                            imm = "0" + imm;
                        }

                        instructionString = opcode + rs1 + rd + imm;
                        instructionLong = Long.parseLong(instructionString, 2);

                        break;
                    }

                    // R2I Format Memory
                    case 4: {

                        // Find rs1 string 
                        Operand operand = instruction.getSourceOperand1();
                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 1 is not a register in R2I format instruction", i+1));
                        }

                        String rs1 = Integer.toBinaryString(operand.getValue());
                        while (rs1.length() < 5) {
                            rs1 = "0" + rs1;
                        }

                        // Find rd string
                        operand = instruction.getDestinationOperand();

                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Destination operand is not a register in R2I format instruction", i+1));
                        }

                        String rd = Integer.toBinaryString(operand.getValue());
                        while (rd.length() < 5) {
                            rd = "0" + rd;
                        }

                        // Find imm string
                        operand = instruction.getSourceOperand2();

                        String imm = "";
                        if (operand.getOperandType() == OperandType.Immediate) {
							imm = Integer.toBinaryString(operand.getValue());
                            while (imm.length() < 17) {
                                imm = "0" + imm;
                            }
                        } else if (operand.getOperandType() == OperandType.Label) {
                            imm = Integer.toBinaryString(ParsedProgram.symtab.get(operand.getLabelValue()));
                            while (imm.length() < 17) {
                                imm = "0" + imm;
                            }
                        } else {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 2 is not an immediate or label in R2I format instruction", i+1));
                        }

                        instructionString = opcode + rs1 + rd + imm;
                        instructionLong = Long.parseLong(instructionString, 2);

                        break;
                    }

                    // R2I Contol Flow
                    case 3: {

                        // Find rs1 string 
                        Operand operand = instruction.getSourceOperand1();
                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 1 is not a register in R2I format instruction", i+1));
                        }

                        String rs1 = Integer.toBinaryString(operand.getValue());
                        while (rs1.length() < 5) {
                            rs1 = "0" + rs1;
                        }

                        // Find rd string
                        operand = instruction.getSourceOperand2();

                        if (operand.getOperandType() != OperandType.Register) {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 2 is not a register in R2I format instruction", i+1));
                        }

                        String rd = Integer.toBinaryString(operand.getValue());
                        while (rd.length() < 5) {
                            rd = "0" + rd;
                        }

                        // Find imm string
                        operand = instruction.getDestinationOperand();

                        String imm = "";
                        if (operand.getOperandType() == OperandType.Immediate) {
                            imm = Integer.toBinaryString(Math.abs(i + 1 - operand.getValue()));
                            while (imm.length() < 17) {
                                imm = "0" + imm;
                            }
                            if(i + 1 - operand.getValue() > 0) {
                                imm = get2sComplementString(imm);
                            }
                        } else if (operand.getOperandType() == OperandType.Label) {
                            imm = Integer.toBinaryString(Math.abs(i + 1 - ParsedProgram.symtab.get(operand.getLabelValue())));
                            while (imm.length() < 17) {
                                imm = "0" + imm;
                            }
                            if(i + 1 - ParsedProgram.symtab.get(operand.getLabelValue()) > 0) {
                                imm = get2sComplementString(imm);
                            }
                        } else {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 2 is not an immediate or label in R2I format instruction", i+1));
                        }

                        instructionString = opcode + rs1 + rd + imm;
                        instructionLong = Long.parseLong(instructionString, 2);

                        break;
                    }

                    // RI jmp
                    case 2: {
                        Operand operand = instruction.getDestinationOperand();

                        // Find rd string
                        String rd = "00000";

                        // Find imm string
                        String imm = "";
                        if (operand.getOperandType() == OperandType.Immediate) {
                            imm = Integer.toBinaryString(Math.abs(i + 1 - operand.getValue()));
                            while (imm.length() < 22) {
                                imm = "0" + imm;
                            }
                            if(i + 1 - operand.getValue() > 0) {
                                imm = get2sComplementString(imm);
                            }
                        } else if (operand.getOperandType() == OperandType.Label) {
                            imm = Integer.toBinaryString(Math.abs(i + 1 - ParsedProgram.symtab.get(operand.getLabelValue())));
                            while (imm.length() < 22) {
                                imm = "0" + imm;
                            }
                            if(i + 1 - ParsedProgram.symtab.get(operand.getLabelValue()) > 0) {
                                imm = get2sComplementString(imm);
                            }
                        } else {
                            Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - Source operand 2 is not an immediate or label in R2I format instruction", i+1));
                        }
                        

                        instructionString = opcode + rd + imm;
                        instructionLong = Long.parseLong(instructionString, 2);
                        break;
                    }

                    // RI end
                    case 1: {
                        String rest = "";
                        for(int j=0; j<27; j++) {
                            rest = rest + '0';
                        }
                        instructionString = opcode + rest;
                        instructionLong = Long.parseLong(instructionString, 2);
                        break;
                    }
                        
                    default: {
                        Misc.printErrorAndExit("[Assembly Error]: Invalid instruction type");
                    }
                }
                // Ensure instructionString is 32 bits
                if (instructionString.length() != 32) {
                    Misc.printErrorAndExit(String.format("[Assembly Error]: Line %d - instructionString is not 32 bits", i+1));
                }

                outputStream.write(to4ByteArray(instructionLong));
            }

            //5. close the file
            outputStream.close();
        }
        catch (Exception e) {
            Misc.printErrorAndExit("[Assembly Error]: " + e.getMessage());
        }
	}
	
    private static int getInstructionFormat(Instruction instruction) {
        switch(instruction.getOperationType()) {
            //R3 type
            case add : 
            case sub : 
            case mul : 
            case div : 
            case and : 
            case or : 
            case xor : 
            case slt : 
            case sll : 
            case srl : 
            case sra : return 6;

            //R2I type
            case addi :
            case subi :
            case muli :
            case divi : 
            case andi : 
            case ori : 
            case xori : 
            case slti : 
            case slli : 
            case srli : 
            case srai : return 5;

            case load :
            case store : return 4;
            
            case beq : 
            case bne : 
            case blt : 
            case bgt : return 3;

            //RI type :
            case jmp : return 2;
            
            case end : return 1;

            default : return 0;
        }
    }
	
    private static byte[] to4ByteArray(long value) {
        byte[] bytes = new byte[4];
        // Convert to byte array
        byte[] rawBytes = BigInteger.valueOf(value).toByteArray();
        // If rawBytes is longer than 4 bytes, then take the most significant 4 bytes
        if (rawBytes.length >= 4) {
            for (int i = 0; i < 4; i++) {
                bytes[i] = rawBytes[i + rawBytes.length - 4];
            }
        }
        // If rawBytes is less than 4 bytes, then pad the left with 0s
        else if (rawBytes.length < 4) {
            for (int i = 0; i < 4 - rawBytes.length; i++) {
                bytes[i] = 0;
            }
            for (int i = 0; i < rawBytes.length; i++) {
                bytes[i + 4 - rawBytes.length] = rawBytes[i];
            }
        }
        return bytes;
    }

    private static byte[] to4ByteArray(int value) {
        byte[] bytes = new byte[4];
        // Convert to byte array
        byte[] rawBytes = BigInteger.valueOf(value).toByteArray();
        // If rawBytes is longer than 4 bytes, then take the most significant 4 bytes
        if (rawBytes.length >= 4) {
            for (int i = 0; i < 4; i++) {
                bytes[i] = rawBytes[i + rawBytes.length - 4];
            }
        }
        // If rawBytes is less than 4 bytes, then pad the left with 0s
        else if (rawBytes.length < 4) {
            for (int i = 0; i < 4 - rawBytes.length; i++) {
                bytes[i] = 0;
            }
            for (int i = 0; i < rawBytes.length; i++) {
                bytes[i + 4 - rawBytes.length] = rawBytes[i];
            }
        }
        return bytes;
    }

    private static String get2sComplementString(String binaryString) {
        String result = "";
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '0') {
                result = result + '1';
            } else {
                result = result + '0';
            }
        }
        int value = Integer.parseInt(result, 2);
        value = value + 1;
        result = Integer.toBinaryString(value);
        while (result.length() < 17) {
            result = "0" + result;
        }
        return result;
    }
}
