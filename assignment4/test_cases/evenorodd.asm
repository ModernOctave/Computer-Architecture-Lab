	.data
n:
	11						0
	.text
main:
	load %x0, $n, %x3		1
	divi %x3, 2, %x3		2
	beq %x0, %x31, even		3
	sub %x10, %x10, %x10	4
	addi %x10, 1, %x10		5
	end						6
even:
	sub %x10, $x10, %x10	7
	subi %x10, 1, %x10		8
	end						9
