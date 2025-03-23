import re
def parsingFG():
    text=open("orig.yaml", encoding="utf-8").read()
    ar=text.splitlines()
    ar.append(" ")
    steck=[]
    ktire=0
    flug = False
    for i in range(len(ar)-1):
        if not re.search(r": ", ar[i]):
            ar[i]= ar[i] + ("  ")
        dlin=(len(ar[i]) - len(ar[i].lstrip()))
        key, value = ar[i].lstrip().split(': ', 1)
        ar[i] = ' ' * dlin + f'"{key}": {value}'
        ar[i] = ar[i].replace('"- ','- "')
        if re.search(r"-", ar[i]):
            dlin1 = (len(ar[i]) - len(ar[i].lstrip()))
        if i<len(ar)-1:
            if re.search(r":  ", ar[i]) and re.search(r"-", ar[i+1]):
                ar[i]=ar[i].replace(':  ',': [')
    for i in range(len(ar)-1):
        if (len(ar[i]) - len(ar[i].lstrip()))<(len(ar[i+1]) - len(ar[i+1].lstrip())):
            if re.search(r"\[", ar[i]):
                fg=(len(ar[i+1]) - len(ar[i+1].lstrip()))
            else:
                ar[i]+="{"
                fg=(len(ar[i]) - len(ar[i].lstrip()))
        if (len(ar[i]) - len(ar[i].lstrip()))>(len(ar[i+1]) - len(ar[i+1].lstrip())):
            ar[i]+="\n  "+ " "* fg+"}"
        if (len(ar[i]) - len(ar[i].lstrip()))==0:
            ar[i-1]=ar[i-1]+"\n  ]"
    for i in range(len(ar)):
        if re.search(r"\[", ar[i]):
            flug = True
        if re.search(r"]", ar[i]):
            flug = False
        if flug:
            ar[i + 1] += ","
        if ":  {" in ar[i]:
            ar[i]=ar[i].replace(",","")
    for i in range(len(ar)):
        if re.search(r"\[", ar[i]):
            flug = True
        if re.search(r"]", ar[i]):
            flug = False
            ktire=0
        if flug and re.search(r"-", ar[i]):
            ktire += 1
        if re.search(r"-", ar[i]):
            if ktire==1:
                ar[i]=ar[i].replace("-"," ").replace("{","")
                ar[i - 1] += "\n   {"
            elif ktire>1:
                ar[i] = ar[i].replace("-", " ").replace("{", "")
                ar[i-1]=ar[i-1].replace(",","")
                ar[i-1]+="\n  },\n  {"
        if "},\n" in ar[i] and re.search(r" \{ |' ' ", ar[i+1]):
            ar[i]=ar[i].replace("},","}\n   },")
    for i in range(len(ar)-1):
        if "[" in ar[i]:
            fg1=(len(ar[i+1]) - len(ar[i+1].lstrip()))
    for i in range(len(ar)):
        if re.search(r"]", ar[i]):
            ar[i]=ar[i].replace("],", " }\n],")
    for i in range(len(ar)-1,0,-1):
        if re.search(r" \{ | ] ", ar[i]):
            ar[i]=ar[i].replace(",","")
        else:
            break
    st=""
    ar.append("}")
    ar.insert(0,"{")
    for i in range(len(ar)):
        if i==0 or i==len(ar)-1:
            print(ar[i])
        else:
            ar[i]="  "+ar[i]
            print(ar[i])
    output_file = "output.json"
    with open(output_file, 'w', encoding='utf-8') as file:
        for line in ar:
            file.write(line + "\n")
            st+=line + "\n"
    print(f"Данные успешно записаны в файл {(output_file)}")

    try:
        dict(st)
    except Exception as e:
        print(e)
parsingFG()