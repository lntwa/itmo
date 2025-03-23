n = int(input('Введите число: '))
base1 = int(input('Введите систему счисления этого числа: '))
base2 = int(input('Введите систему счисления, в которую нужно перевести число: '))
ost = ''
res = ''

if base2 != -10:
    print( "please just -10")
    if base1 < base2:
        print('please write base bigger than base2')
else:
        while n != 0:
            ost = n % base2
            n //= base2
            if ost < 0:
                ost += ((-1) * base2)
                n += 1
            res = str(ost) + res
        print(res)