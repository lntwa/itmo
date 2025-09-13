ORG 0x301
RES_ADR: WORD $RES ; адрес текущей ячейки
EOF: WORD 0x00         ; стоп символ 
TMP: WORD ?            ; временная переменная
MAX_NUMBER: WORD 0x80
COUNTER: WORD 0x06
MASK1: WORD 0x0F
MASK2: WORD 0xF0

ORG 0x333
START:          CLA    ; очистка аккумулятора

FIRST_SYMBOL:   IN 7   ; чтение SR ВУ-3 (адрес #7) ожидание ввода
                AND #0x40 ; проверка статуса
                BEQ FIRST_SYMBOL ; спин луп

                IN 6   ; чтение первого символа
                ST (RES_ADR)+ ; сохраняем в текущей ячейке
                CMP EOF ; проверяем на стоп символ
                BEQ SUBPROGRAM ; если стоп символ то останов

SECOND_SYMBOL:  IN 7
                AND #0x40
                BEQ SECOND_SYMBOL

                IN 6   ; чтение второго символа
                ST (RES_ADR)+ ; сохраняем 
                CMP EOF
                BEQ SUBPROGRAM
                
                JUMP FIRST_SYMBOL ; переходим в начало

SUBPROGRAM:     PUSH
                CALL $MAX_ELEM
                POP
                ST 0x330
                AND $MASK1
                OUT 0x14
                LD 0x330
                AND $MASK2
                ASR
                ASR
                ASR
                ASR
                ADD #0x10
                OUT 0x14

STOP:           HLT

ORG 0x3D4
MAX_ELEM:       
                CURRENT_MAX: WORD 0x3E2
                LD (CURRENT_MAX)+
                CMP $MAX_NUMBER
                BLT LABEL_LESS

LABEL_LESS:     ST $MAX_NUMBER
                LOOP $COUNTER
                JUMP $MAX_ELEM
                ST &01
                RET

ORG 0x3E2       ; буфер для массива 
RES: WORD ?     ; начало массива