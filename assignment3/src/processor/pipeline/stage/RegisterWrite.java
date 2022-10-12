package processor.pipeline.stage;

import generic.Simulator;
import processor.Processor;
import processor.pipeline.latch.IF_EnableLatchType;
import processor.pipeline.latch.MA_RW_LatchType;

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
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			
			// if instruction being processed is a load instruction, remember to set the corresponding register in the register file

			// if instruction being processed is a store instruction, remember to write the value to the memory location

			// if instruction being processed is a branch instruction, remember to set the PC of the processor

			int opcode = MA_RW_Latch.getOpcode();
			int rd = MA_RW_Latch.getRd();
			int aluResult = MA_RW_Latch.getAluResult();
			int ldResult = MA_RW_Latch.getLdResult();

			if(opcode>=0 && opcode <=21)
			{
				containingProcessor.getRegisterFile().setValue(rd, aluResult);
				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (RW) Write to register " + rd + " data " + aluResult);
				}
			}
			
			if(opcode == 22)
			{
				containingProcessor.getRegisterFile().setValue(rd, ldResult);
				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (RW) Write register " + rd + " to " + ldResult);
				}
			}

			r31 = MA_RW_Latch.getR31();
			if(opcode >= 0 && opcode <= 21 && r31 != -1)
			{
				containingProcessor.getRegisterFile().setValue(31, r31);
				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (RW) Write to register 31 data " + MA_RW_Latch.getR31());
				}
			}

			// Handle end instruction
			if(opcode == 29)
			{
				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter() + 1);
				Simulator.setSimulationComplete(true);
				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (RW) End instruction detected");
				}
			}
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
