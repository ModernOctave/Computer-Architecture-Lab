    .data
a:
    10
    .text
main:
    load %x0, $a, %x4
    store %x4, 4, %x0
    end