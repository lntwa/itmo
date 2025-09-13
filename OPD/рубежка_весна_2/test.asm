ORG 0x020
arr_adr1: word $arr1
arr_adr2: word $arr2
loop_check: word 25
loop_counter: word 1
posmask: 0x0FFF
negmask: 0xF000
signmask: word 0x0800
const: word 1210
argument: word 0
res0_15: word ?
res16_31: word ?
counter: word 13


start:  ld (arr_adr1)+
        st argument
        and signmask
        beq pos_elem


neg_elem:   ld argument
            or negmask
            st argument
            cla

neg_loop:   add argument
            st res0_15
            ld #0xff
            adc res16_31
            st res16_31
            ld res0_15
            loop counter
            jump neg_loop
            add const
            st res0_15
            cla 
            adc res16_31
            st res16_31
            jump saving

pos_elem:   ld argument
            and posmask
            st argument
            cla

pos_loop:   add argument
            st res0_15
            cla
            adc res16_31
            st res16_31
            ld res0_15
            loop counter
            jump pos_loop
            add const
            st res0_15
            cla
            adc res16_31
            st res16_31

saving: ld res0_15
        st (arr_adr2)+
        ld res16_31
        st(arr_adr2)+
        ld loop_counter
        cmp loop_check
        beq stop
        inc 
        st loop_counter
        jump start

stop:   hlt





ORG 0x400
arr2:   word ?



ORG 0x6C1
arr1:   WORD
        WORD
        WORD
        WORD
        WORD


