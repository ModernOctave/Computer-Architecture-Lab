package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{
		if(MA_RW_Latch.isRW_enable())
		{
			//TODO
			
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			
			
			// if instruction being processed is a load instruction, remember to set the corresponding register in the register file

			// if instruction being processed is a store instruction, remember to write the value to the memory location

			// if instruction being processed is a branch instruction, remember to set the PC of the processor

			if(MA_RW_Latch.opcode>=0 && MA_RW_Latch.opcode <=21)
			{
				containingProcessor.getRegisterFile().setValue(MA_RW_Latch.rd, MA_RW_Latch.aluResult);
			}
			
			if(MA_RW_Latch.opcode == 22)
			{
				containingProcessor.getRegisterFile().setValue(MA_RW_Latch.rd, MA_RW_Latch.ldResult);
			}

			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
