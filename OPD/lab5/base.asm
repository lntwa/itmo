ORG 0x301
RES_ADR: WORD $RES ; адрес текущей ячейки
EOF: WORD 0x00         ; стоп символ 
TMP: WORD ?            ; временная переменная

ORG 0x333
START:          CLA    ; очистка аккумулятора

FIRST_SYMBOL:   IN 7   ; чтение SR ВУ-3 (адрес #7) ожидание ввода
                AND #0x40 ; проверка статуса
                BEQ FIRST_SYMBOL ; спин луп

                IN 6   ; чтение первого символа
                SWAB   ; перемещаем в старший байт
                ST (RES_ADR) ; сохраняем в текущей ячейке

                SWAB   ; возвращаем для проверки
                CMP EOF ; проверяем на стоп символ
                BEQ STOP ; если стоп символ то останов

SECOND_SYMBOL:  IN 7
                AND #0x40
                BEQ SECOND_SYMBOL

                IN 6   ; чтение второго символа
                ST TMP ; сохраняем
                ADD (RES_ADR) ; объединяем два символа
                ST (RES_ADR)+ ; сохраняем 
                
                LD TMP
                CMP EOF
                BEQ STOP
                
                JUMP FIRST_SYMBOL ; переходим в начало

STOP:           HLT

ORG 0x61D       ; буфер для строки 
RES: WORD ?     ; начало строки