	.data
a:
	10
	.text
main:
    load %x0, $a, %x3
    addi %x0, 2, %x4
    addi %x0, 1, %x5
    addi %x0, 3, %x7
    blt %x3, %x7, break
loop:
    div %x3, %x4, %x6
    bne %x31, 0, continue
notprime:
    subi %x0, 1, %x5
    jmp break
continue:
    addi %x4, 1, %x4
    blt %x4, %x3, loop
break:
    add %x0, %x5, %x10
    end