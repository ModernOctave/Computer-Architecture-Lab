	.data
a:
	3
	.text
main:
	load %x0, $a, %x4
	divi %x4, 2, %x5
	beq %x0, %x31, even
	addi %x0, 1, %x10
	end
even:
	subi %x0, 1, %x10
	end