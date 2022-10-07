package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int numberOfStaticInstructions;
	static int numberOfDynamicInstructions;
	static int numberOfCycles;
	static int numberOfStalls;
	static int numberOfIncorrectBranches;
	static float IPC;
	static float frequency;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println("Number of static instructions executed = " + numberOfStaticInstructions);
			writer.println("Number of dynamic instructions executed = " + numberOfDynamicInstructions);
			writer.println("Number of cycles taken = " + numberOfCycles);
			writer.println("IPC = " + IPC);
			writer.println("Frequency = " + frequency + " GHz");
			writer.println("Number of stalls = " + numberOfStalls);
			writer.println("Number of incorrect branches = " + numberOfIncorrectBranches);
			
			writer.close();
		}
		catch(Exception e)
		{
			Misc.printErrorAndExit(e.getMessage());
		}
	}
	
	public static void setNumberOfStaticInstructions(int numberOfStaticInstructions) {
		Statistics.numberOfStaticInstructions = numberOfStaticInstructions;
	}

	public static int getNumberOfStaticInstructions() {
		return numberOfStaticInstructions;
	}

	public static void setNumberOfDynamicInstructions(int numberOfDynamicInstructions) {
		Statistics.numberOfDynamicInstructions = numberOfDynamicInstructions;
	}

	public static int getNumberOfDynamicInstructions() {
		return numberOfDynamicInstructions;
	}

	public static void setNumberOfCycles(int numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}

	public static int getNumberOfCycles() {
		return numberOfCycles;
	}

	public static void setIPC(float IPC) {
		Statistics.IPC = IPC;
	}

	public static float getIPC() {
		return IPC;
	}

	public static void setFrequency(float frequency) {
		Statistics.frequency = frequency;
	}

	public static float getFrequency() {
		return frequency;
	}

	public static void setNumberOfStalls(int numberOfStalls) {
		Statistics.numberOfStalls = numberOfStalls;
	}

	public static int getNumberOfStalls() {
		return numberOfStalls;
	}

	public static void setNumberOfIncorrectBranches(int numberOfIncorrectBranches) {
		Statistics.numberOfIncorrectBranches = numberOfIncorrectBranches;
	}

	public static int getNumberOfIncorrectBranches() {
		return numberOfIncorrectBranches;
	}
}
