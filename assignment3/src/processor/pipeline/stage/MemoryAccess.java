package processor.pipeline.stage;

import generic.Simulator;
import processor.Processor;
import processor.pipeline.latch.EX_MA_LatchType;
import processor.pipeline.latch.MA_RW_LatchType;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable())
		{
			int opcode = EX_MA_Latch.getOpcode();
	
			if(opcode == 22)
			{
				// load
				int address = EX_MA_Latch.getAluResult();
				int data = containingProcessor.getMainMemory().getWord(address);
				MA_RW_Latch.setLdResult(data);
				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (MA) Load from address " + address + " data " + data);
				}
			}
			else if(opcode == 23)
			{
				// store
				int data = EX_MA_Latch.getOp1();
				int address = EX_MA_Latch.getAluResult();
				containingProcessor.getMainMemory().setWord(address, data);
				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (MA) Store to address " + address + " data " + data);
				}
			}

			// Set the values in MA_RW_Latch
			MA_RW_Latch.setPc(EX_MA_Latch.getPc());
			MA_RW_Latch.setAluResult(EX_MA_Latch.getAluResult());
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setRd(EX_MA_Latch.getRd());
			MA_RW_Latch.setR31(EX_MA_Latch.getR31());

			// Set RW_enable
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(true);
		}
	}

}
