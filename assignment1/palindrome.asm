	.data
a:
	121
	.text
main:
	load %x0, $a, %x4
	addi %x0, 10, %x27
	blt %x4, %x27, zero
	add %x0, %x0, %x5
	addi %x0, 65535, %x7
	jmp loop
zero:
	addi %x0, 1, %x10
	end
loop:
	divi %x4, 10, %x25
	add %x31, %x0, %x6
	store %x6, 0, %x7
	subi %x7, 1, %x7
	addi %x5, 1, %x5
	addi %x25, %x0, %x4
	beq %x25, %x0, check
	jmp loop
check:
	divi %x5, 2, %x20
	addi %x0, 1, %x10
	addi %x0, 65535, %x23
	addi %x7, 1, %x7
	jmp checkloop
checkloop:
	beq %x20, 0, done
	load %x7, 0, %x11
	load %x23, 0, %x12
	bne %x11, %x12, notpal
	subi %x20, 1, %x20
	addi %x7, 1, %x7
	subi %x23, 1, %x23
	jmp checkloop
notpal:
	subi %x10, 2, %x10
	end
done:
	end