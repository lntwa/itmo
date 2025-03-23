import re
vowels = "ёуеаоэяиюЁУЕАОЭЯИЮыЫ"
cons = "йцкнгшщзхфвпрлджчсмбЙЦКНГШЩЗХФВПРЛДЖЧСМБ"


def parse(text: str) -> list[str]:
    pattern = fr"\b\w*[{vowels}]{{2}}\w*\b(?= (?:(?:[{vowels}]*[{cons}][{vowels}]*){{1,3}}|(?:[{vowels}]+))\b)"

    output = []
    for a in re.findall(pattern, text):
        output.append(a)

    return output


test1 = "Кривошеее сущево гуляет по парку "
print(*parse(test1))
