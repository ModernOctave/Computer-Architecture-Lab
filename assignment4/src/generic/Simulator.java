package generic;

import java.io.FileInputStream;
import java.math.BigInteger;

import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	static boolean debugMode = false;
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		try {
			FileInputStream file = new FileInputStream(assemblyProgramFile);
			byte[] b = new byte[4];
			file.read(b);
			int pc = new BigInteger(b).intValue();
			processor.getRegisterFile().setProgramCounter(pc-1);
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);
			int i = 0;
			while(file.read(b) != -1) {
				int val = new BigInteger(b).intValue();
				processor.getMainMemory().setWord(i, val);
				i++;
			}
			file.close();
			Statistics.setNumberOfStaticInstructions(i-pc);
		} catch (Exception e) {
			Misc.printErrorAndExit("[Error]: (Load Program) " + e.getMessage());
		}
	}
	
	public static void simulate()
	{
		Statistics.setNumberOfDynamicInstructions(0);
		Statistics.setNumberOfCycles(0);

		while(simulationComplete == false)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();


			// Update statistics
			Statistics.setNumberOfDynamicInstructions(Statistics.getNumberOfDynamicInstructions() + 1);
			Statistics.setNumberOfCycles(Statistics.getNumberOfCycles() + 1);
		}
		
		// Set statistics
		Statistics.setIPC((float)Statistics.getNumberOfDynamicInstructions() / Statistics.getNumberOfCycles());
		Statistics.setFrequency((float)Statistics.getNumberOfCycles() / Clock.getCurrentTime());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}

	public static boolean isDebugMode() {
		return debugMode;
	}
}
