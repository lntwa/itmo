def print_res(result):
    for i in result:
        print(i)

def indirect_addressing_command(code, index):
    if int(code[2:], 16) >= 128:
        a = int(code[2:], 16) - 256
    else:
        a = int(code[2:], 16)
    if code[1] == "E":
        return "S" + hex(index + a + 1)[2:].upper()
    elif code[1] == "8":
        return "(S" + hex(index + a + 1)[2:].upper() + ")"
    elif code[1] == "A":
        return "(S" + hex(index + a + 1)[2:].upper() + ")+"
    elif code[1] == "B":
        return "-(S" + hex(index + a + 1)[2:].upper() + ")"
    elif code[1] == "C":
            return "(SP+" + str(a) + ")"
    elif code[1] == "F":
        return "#0x" + code[2:]
    else:
        return "ERROR"

def branch_command(code, index):
    if int(code[2:], 16) >= 128:
        a = int(code[2:], 16) - 256
    else:
        a = int(code[2:], 16)
    keys = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"]
    values = ["BZS ", "BZC ", "BNS ", "BNC ", "BCS ", "BCC ", "BVS ", "BVC ", "BLT ", "BGE "]
    branch_command_dict = dict(zip(keys, values))
    return branch_command_dict[code[1]] + "S" + hex(index + 1 + a)[2:].upper()

def code_to_command(code, index):
    keys = ["2", "3", "4", "5", "6", "7", "8", "A", "B", "C", "D", "E"]
    values = ["AND ", "OR ", "ADD ", "ADC ", "SUB ", "CMP ", "LOOP ", "LD ", "SWAM ", "JUMP ", "CALL ", "ST "]
    adressed_commands_dict = dict(zip(keys, values))  # словарь адресных команд
    if code[0] in keys:
        if code[1] in "01234567":
            return adressed_commands_dict[code[0]] + "0x" + code[1:]
        else:
            return adressed_commands_dict[code[0]] + indirect_addressing_command(code, index)
    else:
        return branch_command(code, index)

result = []

print("Введите номер начальной ячейки:")
first_cell_index = input()
first_cell_index = (3 - len(first_cell_index)) * '0' + first_cell_index
result.append("ORG 0x" + first_cell_index)

index = int(first_cell_index, 16)

print("Вводите первую часть ячеек с данными (пустая строка = остановка):")
data_cell = input()
result.append("S" + hex(index)[2:].upper() + ": WORD 0x" + data_cell)
index += 1
while data_cell != '':
    data_cell = input()
    if data_cell != "":
        result.append("S" + hex(index)[2:].upper() + ": WORD 0x" + data_cell)
        index += 1

keys = ["0000", "0100", "0200", "0280", "0300", "0380", "0400", "0480", "0500", "0580", "0600", "0680", "0700", "0740", "0780", "0800", "0900", "0A00", "0B00", "0C00", "0D00", "0E00"]
values = ["NOP", "HLT", "CLA", "NOT", "CLC", "CMC", "ROL", "ROR", "ASL", "ASR", "SXTB", "SWAB", "INC", "DEC", "NEG", "POP", "POPF", "RET", "IRET", "PUSH", "PUSHF", "SWAP"]
unadressed_commands_dict = dict(zip(keys, values)) #словарь безадресных команд

print("Теперь вводите ваши команды в 16-ричной системе счисления:")
while True:
    input_code = input(); input_code = (4 - len(input_code)) * "0" + input_code
    if input_code in keys:
        result.append("S" + hex(index)[2:].upper() + ": " + unadressed_commands_dict[input_code])
        index += 1
        if input_code == '0100': break
    else:
        result.append("S" + hex(index)[2:].upper() + ": " + code_to_command(input_code, index))
        index += 1
print("Теперь вводите оставшиеся данные (пустая строка = остановка):")
data_cell = input()
if data_cell == '':
    print_res(result)
else:
    result.append("S"  + hex(index)[2:].upper() + ": WORD 0x" + data_cell)
    index += 1
    while data_cell != '':
        data_cell = input()
        if data_cell != "":
            result.append("S" + hex(index)[2:].upper() + ": WORD 0x" + data_cell)
            index += 1
print_res(result)
