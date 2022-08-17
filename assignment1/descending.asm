	.data
a:
	70
	80
	40
	20
	10
	30
	50
	60
n:
	8
	.text
main:
    load %x0, $n, %x3
    add %x0, %x0, %x4
loopa:
    add %x0, %x0, %x5
    addi %x0, 1, %x6
loopb:
    load %x5, $a, %x7
    load %x6, $a, %x8
    bgt %x7, %x8, continue
swap:
    store %x8, $a, %x5
    store %x7, $a, %x6
continue:
    addi %x5, 1, %x5
    addi %x6, 1, %x6
    blt %x6, %x3, loopb
    addi %x4, 1, %x4
    blt %x4, %x3, loopa
    end
