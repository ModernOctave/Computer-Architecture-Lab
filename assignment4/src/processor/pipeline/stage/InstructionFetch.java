package processor.pipeline.stage;

import generic.Simulator;
import processor.Processor;
import processor.pipeline.latch.EX_IF_LatchType;
import processor.pipeline.latch.IF_EnableLatchType;
import processor.pipeline.latch.IF_OF_LatchType;

public class InstructionFetch {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		if(Simulator.isDebugMode()) 
		{
			System.out.println();
		}
		
		if(IF_EnableLatch.isIF_enable())
		{
			if(!IF_EnableLatch.isBubbled())
			{
				// Update PC
				if(containingProcessor.getIsBranchTaken())
				{
					containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getBranchPC());
					containingProcessor.setIsBranchTaken(false);
					if(Simulator.isDebugMode())
					{
						System.out.println("[Debug] (IF) Branch taken, PC updated to " + containingProcessor.getBranchPC());
					}
				}
				else
				{
					containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter() + 1);
					if(Simulator.isDebugMode())
					{
						System.out.println("[Debug] (IF) PC incremented to " + containingProcessor.getRegisterFile().getProgramCounter());
					}
				}

				// IF stage
				int PC = containingProcessor.getRegisterFile().getProgramCounter();
				IF_OF_Latch.setPc(PC);
				int instruction = containingProcessor.getMainMemory().getWord(PC);
				IF_OF_Latch.setInstruction(instruction);
				
				// Set OF_enable
				IF_OF_Latch.setOF_enable(true);
			}

			// Pass the bubble signal to the next stage
			IF_OF_Latch.setIsBubbled(IF_EnableLatch.isBubbled());
		}
	}

}
