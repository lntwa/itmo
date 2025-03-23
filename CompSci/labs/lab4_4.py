import time
import yaml
import json

def custom_parser(yaml_data):
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

    def yamlToDict(yaml_data):
        result = dict()
        lines = yaml_data.splitlines()
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
                for i in range((last_level - level) // 2):
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

    return yamlToDict(yaml_data)

custom_parser_test = lambda x: json.dumps(custom_parser(x))
yaml_lib_test = lambda x: json.dumps(yaml.safe_load(x))

def test(f, data):
    start_time = time.time()
    for _ in range(100):
        f(data)
    return time.time() - start_time

if __name__ == "__main__":
    with open("orig.yaml", "r", encoding="utf-8") as f:
        data = f.read()

    print(
        f"Custom Parser: {test(custom_parser_test, data)} seconds",
        f"YAML Library Parser: {test(yaml_lib_test, data)} seconds",
        sep="\n",
    )