	.data
n:
	10
	.text
main:
	load %x0, $n, %x4
	addi %x0, 65535, %x5
    addi %x0, 1, %x27
	beq %x4, %x27, one
	addi %x0, 2, %x30
	beq %x4, %x30, two
	bgt %x4, 2, more
	end
one:
	store %x0, 0, %x5
	end
two:
	store %x0, 0, %x5
	addi %x0, 1, %x6
	subi %x5, 1, %x5
	store %x6, 0, %x5
	end
more:
	store %x0, 0, %x5
	addi %x0, 1, %x6
	subi %x5, 1, %x5
	store %x6, 0, %x5
	subi %x5, 1, %x5
	subi %x4, 2, %x4
	addi %x0, 1, %x14
	jmp loop
loop:
	beq %x4, 0, done
	store %x14, 0, %x5
	subi %x5, 1, %x5
	load %x5, 1, %x8
	load %x5, 2, %x9
	add %x8, %x9, %x14
	subi %x4, 1, %x4
	jmp loop
done:
	end