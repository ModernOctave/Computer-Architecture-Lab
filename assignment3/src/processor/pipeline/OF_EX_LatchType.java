package processor.pipeline;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int opcode;
	int imm;
	int aluResult;
	int result;
	int op1;
	int op2;
	int branchTarget;
	int pc;

	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public int getOpcode() {
		return opcode;
	}

	public int getImm() {
		return imm;
	}

	public int getOp1() {
		return op1;
	}

	public int getOp2() {
		return op2;
	}

	public int getAluResult() {
		return aluResult;
	}

	public int getResult() {
		return result;
	}

	public int getPc() {
		return pc;
	}


	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	
	public void setImm(int imm) {
		this.imm = imm;
	}

	public void setOp1(int op1) {
		this.op1 = op1;
	}

	public void setOp2(int op2) {
		this.op2 = op2;
	}


	public void setAluResult(int aluResult) {
		this.aluResult = aluResult;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getBranchTarget() {
		return branchTarget;
	}

	public void setBranchTarget(int branchTarget) {
		this.branchTarget = branchTarget;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

}
