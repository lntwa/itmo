message = input('Введите сообщение: ')
s = ''

if len(message) != 7:
    print('Сообщение должно содержать 7 бит.')
else:
    bits = [int(bit) for bit in message]
    r1 = str((bits[0] + bits[2] + bits[4] + bits[6]) % 2)
    r2 = str((bits[1] + bits[2] + bits[5] + bits[6]) % 2)
    r3 = str((bits[3] + bits[4] + bits[5] + bits[6]) % 2)
    s = r1 + r2 + r3
    s = s[::-1]
    if s != '000':
        wrong_pos = int(s, 2) - 1
        bits[wrong_pos] = 1 - bits[wrong_pos]

inf_bits = [bits[2], bits[4], bits[5], bits[6]]
print("Правильное сообщение:", ''.join(map(str, inf_bits)))
print("Бит с ошибкой: ", wrong_pos + 1)