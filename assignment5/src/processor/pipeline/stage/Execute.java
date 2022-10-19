package processor.pipeline.stage;

import configuration.Configuration;
import generic.*;
import processor.Clock;
import processor.Processor;
import processor.pipeline.latch.EX_IF_LatchType;
import processor.pipeline.latch.EX_MA_LatchType;
import processor.pipeline.latch.OF_EX_LatchType;
import generic.Event;

public class Execute implements Element {
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
		OF_EX_Latch.setIsBusy(EX_MA_Latch.isBusy());

		if(OF_EX_Latch.isEX_enable() && !OF_EX_Latch.isBusy() && !EX_MA_Latch.isBusy())
		{
			// Pass on the bubble signal
			EX_MA_Latch.setIsBubbled(OF_EX_Latch.isBubbled());

			if(!OF_EX_Latch.isBubbled())
			{
				int opcode = OF_EX_Latch.getOpcode();
				int imm = OF_EX_Latch.getImm();
				int op1 = OF_EX_Latch.getOp1();
				int op2 = OF_EX_Latch.getOp2();
				int pc = OF_EX_Latch.getPc();
				int branchPC = OF_EX_Latch.getBranchPC();
				long _aluResult = 0;
				int r31 = -1;
				int remainder = -1;
				int underflowAmt = -1;

				
				switch(opcode)
				{
					case 0: _aluResult = (long)op1 + op2;
							break;
					case 1: _aluResult = (long)op1 + imm;
							break;
					case 2: _aluResult = (long)op1 - op2;
							break;
					case 3: _aluResult = (long)op1 - imm;
							break;
					case 4: _aluResult = (long)op1 * op2;
							break;
					case 5: _aluResult = (long)op1 * imm;
							break;
					case 6: _aluResult = (long)op1 / op2;
							remainder = op1 % op2;
							break;
					case 7: _aluResult = (long)op1 / imm;
							remainder = op1 % imm;
							break;
					case 8: _aluResult = (long)op1 & op2;
							break;
					case 9: _aluResult = (long)op1 & imm;
							break;
					case 10: _aluResult = (long)op1 | op2;
							break;
					case 11: _aluResult = (long)op1 | imm;
							break;
					case 12: _aluResult = (long)op1 ^ op2;
							break;
					case 13: _aluResult = (long)op1 ^ imm;
							break;
					case 14: if(op1 < op2)
								_aluResult = 1;
							else
								_aluResult = 0;
							break;
					case 15: if(op1 < imm)
								_aluResult = 1;
							else
								_aluResult = 0;
							break;

					case 16: _aluResult = (long)op1 << op2;
							break;
					case 17: _aluResult = (long)op1 << imm;
							break;
					case 18: _aluResult = (long)op1 >> op2;
							underflowAmt = op2;
							break;
					case 19: _aluResult = (long)op1 >> imm;
							underflowAmt = imm;
							break;
					case 20: _aluResult = (long)op1 >>> op2;
							underflowAmt = op2;
							break;
					case 21: _aluResult = (long)op1 >>> imm;
							underflowAmt = imm;
							break;

					// Memory Instructions

					case 22: _aluResult = (long)op1 + imm;
							break;

					case 23: _aluResult = (long)op2 + imm;
							break;

					//Branch Unit

					case 24: containingProcessor.setIsBranchTaken(true);
							break;

					case 25: containingProcessor.setIsBranchTaken(op1 == op2);
							break;
					
					case 26: containingProcessor.setIsBranchTaken(op1 != op2);
							break;
					
					case 27: containingProcessor.setIsBranchTaken(op1 < op2);
							break;

					case 28: containingProcessor.setIsBranchTaken(op1 > op2);
							break;	
				}

				if(opcode >= 0 && opcode <= 28)
				{
					Event event;
					if(opcode == 4 || opcode == 5)
					{
						// Multiplication
						event = new ExecutionCompleteEvent(Clock.getCurrentTime() + Configuration.multiplier_latency, this, this);
					}
					else if (opcode == 6 || opcode == 7)
					{
						// Division
						event = new ExecutionCompleteEvent(Clock.getCurrentTime() + Configuration.divider_latency, this, this);
					}
					else
					{
						// ALU
						event = new ExecutionCompleteEvent(Clock.getCurrentTime() + Configuration.ALU_latency, this, this);
					}
					Simulator.getEventQueue().addEvent(event);
					OF_EX_Latch.setIsBusy(true);

					if(Simulator.isDebugMode())
					{
						System.out.println("[Debug] (EX) Operation has started execution.");
					}
				}
				else
				{
					EX_MA_Latch.setMA_enable(true);
				}
				
				// Take only the lower 32 bits of the result
				int aluResult = (int)_aluResult;
				// Put overflow into r31
				int overflow = (int)(_aluResult >> 32);
				if (overflow != 0) {
					r31 = overflow;
				}
				// Put underflow into r31
				if(underflowAmt != -1)
				{
					r31 = (int)(op1 << (32 - underflowAmt));
				}
				// Put remainder into r31
				if(remainder != -1)
				{
					r31 = remainder;
				}

				// If branch is taken, update the branchPC
				if(containingProcessor.getIsBranchTaken())
				{
					containingProcessor.setBranchPC(branchPC);

					// Bubble the pipeline
					containingProcessor.getIF_OF_Latch().setIsBubbled(true);
					containingProcessor.getIF_EnableLatch().setIsBubbled(true);

					Statistics.setNumberOfIncorrectBranches(Statistics.getNumberOfIncorrectBranches() + 1);
				}

				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (EX) ALU Result: " + aluResult);
					System.out.println("[Debug] (EX) r31: " + r31);
					System.out.println("[Debug] (EX) isBranchTaken: " + containingProcessor.getIsBranchTaken());
				}

				EX_MA_Latch.setAluResult(aluResult);
				EX_MA_Latch.setOpcode(opcode);
				EX_MA_Latch.setOp1(op1);
				EX_MA_Latch.setPc(pc);
				EX_MA_Latch.setRd(OF_EX_Latch.getRd());
				EX_MA_Latch.setR31(r31);
				OF_EX_Latch.setEX_enable(false);
			}
			else
			{
				EX_MA_Latch.setMA_enable(true);
				OF_EX_Latch.setEX_enable(false);
			}
		}
	}

	// handle the event generated by the event queue
	@Override
	public void handleEvent(Event e) {
		if(EX_MA_Latch.isBusy())
		{
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			if (e.getEventType() == Event.EventType.ExecutionComplete) 
			{
				OF_EX_Latch.setIsBusy(false);
				EX_MA_Latch.setMA_enable(true);
			}
		}
	}
}

