org 0x410
test1_n: word 0x2222
test1_ans: word 0

test2_n: word 0xA222
test2_ans: word 0

test1_res: word ?
test2_res: word ?

org 0x4BA
start:  cla
        ld $test1_n
        neg
        st $test1_ans

        ld $test1_n
        word 0x9410

        ld $test1_ans
        cmp $test1_n
        beq w_1
        jump l_1

w_1:     ld #0x1
        st $test1_res
        jump test2

l_1:     cla
        st $test1_res

test2:  ld $test2_n
        neg
        st $test2_ans

        ld $test2_n
        word 0x9412

        ld $test2_ans
        cmp $test2_n
        beq w_2
        jump l_2

w_2:     ld #0x1
        st $test2_res
        jump end_p

l_2:     cla
        st $test2_res

end_p:    ld $test1_res
        and $test2_res
        cmp #0x1
        beq win
        ld #0xff
        hlt

win:    ld #0x1
        hlt