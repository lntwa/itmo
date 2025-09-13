; Суммирование каждого k-ого элемента массива в 32-ух разрядную сумму. Элементы — 14-ти разрядные числа

org 0x020
SUM_0_15: WORD 0
SUM_16_31: WORD 0
RES_ADR: WORD $RES
array_counter: word 13
k_check: word 3
k_counter: WORD 0
positive_mask: 0x1FFF
negative_mask: 0xC000
sign_mask: 0x2000
argument: word 0


START:  CLA
        ST SUM_0_15
        ST SUM_16_31

MAIN_LOOP:  LD k_counter
            INC
            CMP k_check
            BEQ SUMM
            ST k_counter
LOOP_END:   LOOP array_counter
            JUMP MAIN_LOOP
            HLT

SUMM:   CLA
        ST k_counter
        LD (RES_ADR)+
        ST argument
        AND sign_mask
        BEQ POS_ELEM
        JUMP NEG_ELEM

POS_ELEM:   LD argument
            AND positive_mask
            ADD SUM_0_15
            ST SUM_0_15
            CLA 
            ADC SUM_16_31
            ST SUM_16_31
            JUMP LOOP_END

NEG_ELEM:   LD argument
            OR negative_mask
            ADD SUM_0_15
            ST SUM_0_15
            LD #0xFF
            ADC SUM_16_31
            ST SUM_16_31
            JUMP LOOP_END

ORG 0x3E0       
RES:    WORD 0
        WORD 0
        WORD 0
        WORD 0
        WORD 0





; Задание №1. Разработать программу обработки для элементов массива M, в которой:
; 1. Массив имеет следующие характеристики:
; - адрес начала массива в памяти БЭВМ - 0x687;
; - число измерений исходного массива - 2;
; - количество элементов исходного массива - 3x3;
; - каждый элемент является знаковым числом с разрядностью 16 бит;
; - нумерация элементов начинается с 1;
; - элементы хранятся в массиве по границам слов, нет необходимости в плотной упаковке;
; 2. Для каждого элемента массива необходимо вычислить функцию:
; - формула функции F(X) = 3 * X + 23479;
; - функцию вычислять только для элементов массива с кратными 3-м i-индексами, четными j-индексами;
; - если результат вычисления функции выходит за пределы области допустимых значений элемента массива из п.1, то он принимается равным 18457
; 3. Из всех полученных значений функции необходимо вычислить исключающее 'ИЛИ' значений, и записать в 32-разрядный результат.
; Примечание: все числа представлены в десятичной системе счисления, если явно не указано иное.
;A XOR B = (A AND (NOT B)) OR ((NOT A) AND B)

	ORG 0x10
VECTOR_ADDR: 	WORD	0x687 ; задаём начало массива и его размерность
	DIM_M: 	WORD 	0x3
	DIM_N:	WORD	0x3
POINTER: 	WORD 	?		  ; задаём указатель массива
	INT_I:	WORD 	0x1
	INT_J: 	WORD 	0x1
CONST1:	WORD 	23479
CONST2: WORD	18457
ARG: 	WORD 	?		  ; переменная для хранения промежуточных значений
RESULT: WORD	?		  ; переменная для хранения результат
START:	LD	VECTOR_ADDR
	ST 	POINTER
NEXT: 	LD 	(POINTER)+
	ST	ARG
	CALL	CHECK_INDEX
	LD	INT_J
	CMP	DIM_N
	BZC	INC_I
	INC
	ST 	INT_J
	JUMP	NEXT
INC_I:	LD 	#0x1
	ST 	INT_J
	LD 	INT_I
	CMP DIM_M
	BZS	STOP_P
	INC
	ST INT_I
	JUMP NEXT
STOP_P: 	HLT
CHECK_INDEX:	LD	INT_I	  ; проверка релевантности индексов
	DIV_I: 	SUB 	#0x3	  ; проверка i на кратность 3
		BZS 	CHECK_J
		BNC		DIV_I
		RET
	CHECK_J:	LD 	INT_J	  ; проверка j на кратность 2
		DIV_J:	SUB #0x2
			BZS	CALCULATE
			BNC	DIV_J
			RET
	CALCULATE:	CALL 	FUNC
				CALL 	XOR
				RET
XOR_PART:	WORD	?
XOR: 	PUSH
	LD	RESULT
	NOT
	AND	&0
	ST 	XOR_PART
	POP
	NOT
	AND	RESULT
	OR 	XOR_PART
	ST 	RESULT
	RET
FUNC: 	LD 	ARG
	ADD 	ARG
	BVS		ERR
	ADD 	ARG
	BVS		ERR
	ADD 	CONST1
	BVS 	ERR
	RET
	ERR:	LD 	CONST2
		RET




org 0x020
elems: word 3
cur: word 0x6c1
static_num: word 1210
smask: word 0x0800
posmask: word 0x07FF
negmask: word 0xF000
chet: word 12

main:
start:
ld (cur)
and smask
bzs if_pos
bzc if_neg

if_pos:
ld (cur)+
and posmask
st temp
st res_l
pos_sum_loop: ld res_l
add temp
st res_l
cla
adc res_h
st res_h
loop chet
jump pos_sum_loop
ld res_l
add static_num
st res_l
cla
adc res_h
st res_h
ld #12
st chet
jump to_array


if_neg:
ld (cur)+
or negmask
st temp
st res_l
cla
ld #0xff
st res_h
neg_sum_loop:
ld res_l
add temp
st res_l
ld #0xFF
adc res_h
st res_h
loop chet
jump neg_sum_loop
ld res_l
add static_num
st res_l
cla
adc res_h
st res_h
ld #12
st chet
jump to_array


array_pointer:word 0x0400
to_array:
ld res_l
st (array_pointer)+
ld res_h
st (array_pointer)+
cla
st res_l
st res_h
loop elems
jump main

prog_end: 
ld (array_pointer)
hlt

temp: word ?
res_h: word ?
res_l: word ?

org 0x6c1
word 0x1111
word 0x2222
word 0x0800
