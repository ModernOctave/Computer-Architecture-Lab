    .data
a:
    1
    2
    3
    4
    5
    6
    7
    8
    9
    1
    2
    3
    4
    5
    6
    7
    8
    9
    1
    2
    3
    4
    5
    6
    7
    8
    9
    1
    2
    3
    4
    5
    6
    7
    8
    9
    1
    2
    3
    4
    5
    6
    7
    8
    9
    1
    2
    3
    4
    5
    6
    7
    8
    9
    1
    2
    3
    4
    5
    6
    7
    8
    9
    .text
main:
    addi %x0, 100, %x15
    addi %x0, 2, %x10
    load %x0, $a, %x4
    addi %x6, %x10, %x6
loop:
    load %x6, $a, %x4
    addi %x4, %x10, %x4
    subi %x15, 1, %x15
    bgt %x15, %x0, loop
    end