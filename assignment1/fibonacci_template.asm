	.data
n:
	1
	.text
main:
	load %x0, $n, %x4
	addi %x0, 65535, %x5
	beq %x4, 0, one
	beq %x4, 1, two
	end
one:
	store %x0, 0, %x5
	end
two:
	store %x0, 0, %x5
	subi %x5, $a, %x5
	store %x0, 1, %x5
	end