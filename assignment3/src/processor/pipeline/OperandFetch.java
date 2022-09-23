package processor.pipeline;

import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			String binaryInstruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());
			while(binaryInstruction.length() < 32)
			{
				binaryInstruction = "0" + binaryInstruction;
			}

			// OF stage
			// opcode
			int opcode = Integer.parseInt(binaryInstruction.substring(0, 4), 2);

			// imm
			int imm = Integer.parseInt(binaryInstruction.substring(15, 31), 2);

			// branchPC
			int branchPC;
			if(opcode != 24)
			{
				// R2I
				branchPC = IF_OF_Latch.getPc()+Integer.parseInt(binaryInstruction.substring(15, 31), 2);
			}
			else
			{
				// RI
				branchPC = IF_OF_Latch.getPc()+Integer.parseInt(binaryInstruction.substring(5, 9), 2)+Integer.parseInt(binaryInstruction.substring(10, 31), 2);
			}

			// op1
			int rs1 = Integer.parseInt(binaryInstruction.substring(5, 9), 2);
			int op1 = containingProcessor.getRegisterFile().getValue(rs1);

			// op2
			int rs2 = Integer.parseInt(binaryInstruction.substring(10, 14), 2);
			int op2 = containingProcessor.getRegisterFile().getValue(rs2);

			// rd
			int rd;
			if(opcode != 22)
			{
				// Arithematic
				rd = Integer.parseInt(binaryInstruction.substring(15, 19), 2);
			}
			else
			{
				// load
				rd = Integer.parseInt(binaryInstruction.substring(10, 14), 2);
			}
			
			// Set in latch
			OF_EX_Latch.setOpcode(opcode);
			OF_EX_Latch.setImm(imm);
			OF_EX_Latch.setOp1(op1);
			OF_EX_Latch.setOp2(op2);
			OF_EX_Latch.setPc(IF_OF_Latch.getPc());
			OF_EX_Latch.setRd(rd);

			// Set branchPC
			containingProcessor.setBranchPC(branchPC);

			// Set EX_enable
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
