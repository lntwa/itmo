org 0x0
V0: word $default, 0x180 ; задаю вектора прерывания
V1: word $default, 0x180
V2: word $int2, 0x180
V3: word $int3, 0x180
V4: word $default, 0x180
V5: word $default, 0x180
V6: word $default, 0x180
V7: word $default, 0x180
default:    iret ; просто возврат

org 0x01C
x: word ? ; главная переменная х
min: word 0xFFE1 ; минимальное значение х в рамках одз
max: word 0x0020 ; максимальное значение х в рамках одз
const: word 3 ; константа которую я потом добавляю в функции

org 0x20
start:      di ; запрет прерываний для неиспользуемых устройств
            cla
            out 0x1
            out 0x3
            out 0xb
            out 0
            out 0x11
            out 0x15
            out 0x19
            out 0x1d
            ld #0xA ; загрузка в аккумулятор MR (1000|0010=1010)
            out 5 ; разрешение прерываний для ВУ-2
            ld #0xB ; загрузка в аккумулятор MR (1000|0011=1011)
            out 7 ; разрешение прерываний для ВУ-3
            ei ; 

org 0x30
main:       ld x
            inc
            inc
            call check
            st x
            jump main

org 0x40
check:      cmp min ; если х > минимального значения, то проверяем х < максимального
            bpl check_max
            jump ld_min
check_max:  cmp max
            bmi return
ld_min:     ld min
return:     ret

org 0x50
int2:    in 4
            nop
            or x
            st x
            nop
            iret

org 0x60
int3:    ld x
            nop
            asl
            asl
            sub const
            nop
            out 0x6
            iret
