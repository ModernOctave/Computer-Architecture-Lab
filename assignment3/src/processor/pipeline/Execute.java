package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		if(OF_EX_Latch.isEX_enable())
		{
			int opcode = OF_EX_Latch.getOpcode();
			int imm = OF_EX_Latch.getImm();
			int op1 = OF_EX_Latch.getOp1();
			int op2 = OF_EX_Latch.getOp2();
			int aluResult = 0;
			int pc = OF_EX_Latch.getPc();

			
			switch(opcode)
			{
				case 0: aluResult = op1 + op2;
						break;
				case 1: aluResult = op1 + imm;
						break;
				case 2: aluResult = op1 - op2;
						break;
				case 3: aluResult = op1 - imm;
						break;
				case 4: aluResult = op1 * op2;
						break;
				case 5: aluResult = op1 * imm;
						break;
				case 6: aluResult = op1 / op2;
						break;
				case 7: aluResult = op1 / imm;
						break;
				case 8: aluResult = op1 & op2;
						break;
				case 9: aluResult = op1 & imm;
						break;
				case 10: aluResult = op1 | op2;
						break;
				case 11: aluResult = op1 | imm;
						break;
				case 12: aluResult = op1 ^ op2;
						break;
				case 13: aluResult = op1 ^ imm;
						break;
				case 14: if(op1 < op2)
							aluResult = 1;
						else
							aluResult = 0;
						break;
				case 15: if(op1 < imm)
							aluResult = 1;
						else
							aluResult = 0;
						break;

				case 16: aluResult = op1 << op2;
						break;
				case 17: aluResult = op1 << imm;
						break;
				case 18: aluResult = op1 >> op2;
						break;
				case 19: aluResult = op1 >> imm;
						break;
				case 20: aluResult = op1 >>> op2;
						break;
				case 21: aluResult = op1 >>> imm;
						break;

				//Branch Unit

				case 22: containingProcessor.setIsBranchTaken(true);
						break;

				case 23: containingProcessor.setIsBranchTaken(op1 == op2);
						break;
				
				case 24: containingProcessor.setIsBranchTaken(op1 != op2);
						break;
				
				case 25: containingProcessor.setIsBranchTaken(op1 < op2);
						break;

				case 26: containingProcessor.setIsBranchTaken(op1 > op2);
						break;	
			}

			EX_MA_Latch.setAluResult(aluResult);
			EX_MA_Latch.setOpcode(opcode);
			EX_MA_Latch.setOp1(op1);
			EX_MA_Latch.setPc(pc);
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.setMA_enable(true);
		}
	}
}

