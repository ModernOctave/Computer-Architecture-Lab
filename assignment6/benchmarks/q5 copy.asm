	.data
a:
	1
	2
	3
	4
	5
	6
	.text
main:
	load %x3, $a, %x4
	addi %x0, 100, %x10
loop:
	addi %x0, 0, %x3
	load %x3, $a, %x4
	addi %x3, 8, %x3
	load %x3, $a, %x4
	addi %x3, 8, %x3
	load %x3, $a, %x4
	addi %x3, 8, %x3
	load %x3, $a, %x4
	addi %x3, 8, %x3
	load %x3, $a, %x4
	addi %x3, 8, %x3
	load %x3, $a, %x4
	addi %x3, 8, %x3
	load %x3, $a, %x4
	addi %x3, 8, %x3
	load %x3, $a, %x4
	subi %x10, 1, %x10
	bgt %x10, 0, loop
	end