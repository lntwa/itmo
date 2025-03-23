def putValue(dictionary, path, value):
    if len(path) != 1:
        
        if "- " in path[0]:
            return putValue(dictionary[int(path[0][-1])][path[0][2:-1]], path[1::], value)
        else:
            return putValue(dictionary[path[0]], path[1::], value)
    else:
        dictionary[path[0]] = value


def indentDict(dictionary, path):
    if len(path) != 1:
        
        if "- " in path[0]:
            return indentDict(dictionary[int(path[0][-1])][path[0][2:-1]], path[1::])
        else:
            return indentDict(dictionary[path[0]], path[1::])
    else:
        dictionary[path[0]] = dict()


def addList(dictionary, path, key):
    if len(path) != 1:
        return addList(dictionary[path[0]], path[1::], key)
    else:
        if not (dictionary[path[0]]):
            dictionary[path[0]] = [dict()]
        if (int(key[-1]) == 0):
            dictionary[path[0]][int(key[-1])][key[0:-1]] = dict()
        else:
            dictionary[path[0]].append(dict())
            dictionary[path[0]][int(key[-1])][key[0:-1]] = dict()


def yamlToDict (filename):
    result = dict()
    with open(filename, 'r', encoding='utf-8') as file:
        lines = file.readlines()
        path = []
        last_level = 0
        listIndex = 0
        inList = False
        for line in lines:
            if (line.strip() == ""):
                continue
            line = line.rstrip()
            level = len(line) - len(line.lstrip())
            line = line.lstrip()
            line = line.split(":", 1)
            key = line[0]
            value = ""
            if (len(line) > 1):
                value = line[1]
            value = value.strip().replace('"', '')
            if last_level > level:
                for i in range((last_level-level) // 2):
                    path.pop()
                if (last_level - level > 2):
                    listIndex = 0
            path.append(key)
            if value:
                putValue(result, path, value)
                path.pop()
            else:
                if "-" in key: 
                    key = key.lstrip("- ")
                    inList = True
                    key = key + str(listIndex)
                    path.pop()
                    if (listIndex == 0):
                        addList(result, path, key)
                    else:
                        addList(result, path, key)
                    path.append("- " + key)
                    listIndex += 1
                else:
                    indentDict(result, path)
            if (inList):
                last_level = level - 2
            else: 
                last_level = level
    return result


filw = open("output.json", "w")
print(yamlToDict("orig.yaml"))


def DictToJSON(dictionary, level=0):
    json = "{\n"
    for key, value in dictionary.items():
        if isinstance(value, dict):
            json += level * '    ' + '"' + key + '":' + DictToJSON(value, level + 1) + ",\n"
        elif isinstance(value, list):
            json += level * '    ' + '"' + key + '":'+ ListToJSON(value, level + 1) + ",\n"
        else:
            json += level * '    ' + '"' + key + '": "'+ str(value) + '",\n'
    json = json.rstrip(",\n") + "\n"+ level*'    '+"}"
    return json


def ListToJSON(list,level):
    json = "["
    for item in list:
        if isinstance(item, dict):
            json += DictToJSON(item, level + 1) + ",\n"
        elif isinstance(item, list):
            json += ListToJSON(item, level + 1) + ",\n"
        else:
            json += str(item) + ",\n"
    json = json.rstrip(",\n") + "]"
    return json

filw.write(DictToJSON(yamlToDict("orig.yaml")))