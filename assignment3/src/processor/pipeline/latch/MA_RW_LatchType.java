package processor.pipeline.latch;

public class MA_RW_LatchType {
	
	boolean RW_enable;
	int ldResult = 0;
	int aluResult = 0;
	int opcode = 0;
	int pc = 0;
	int rd = 0;
	int r31;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	public int getLdResult() {
		return ldResult;
	}

	public void setLdResult(int ldResult) {
		this.ldResult = ldResult;
	}

	public int getAluResult() {
		return aluResult;
	}

	public void setAluResult(int aluResult) {
		this.aluResult = aluResult;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public int getR31() {
		return r31;
	}

	public void setR31(int r31) {
		this.r31 = r31;
	}

}
