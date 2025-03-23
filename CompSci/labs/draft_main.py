def format_json(data, level=1):
    json_str = ""
    space = " " * (level * 4)
    if isinstance(data, dict):
        json_str += "{\n"
        items = []
        for key, value in data.items():
            items.append(f'{space}"{key}": {format_json(value, level + 1)}')
        json_str += ",\n".join(items)
        json_str += f"\n}}"
    elif isinstance(data, list):
        json_str += "[\n"
        items = [f"{space}{format_json(item, level + 1)}" for item in data]
        json_str += ",\n".join(items)
        json_str += f"\n{space}]"
    elif isinstance(data, str):
        json_str += f'"{data}"'
    return json_str


def yaml_to_json(yaml_file, json_file):
    result = {}
    memory_key = None
    yaml_list = None
    indent = []
    with open(yaml_file, "r", encoding='utf-8') as file:
        lines = file.readlines()
        for line in lines:
            indent_level = len(line) - len(line.lstrip()) 

            if ":" in line:
                word = line.split(": ", 1)
                key = word[0].strip() 
                if len(word) > 1:
                    value = word[1].strip()
                else: 
                    value = ""
                
                if value:
                    value = value.strip('"')
                    if yaml_list is not None: #если список есть, то добавляем значение
                        yaml_list.append(value)
                    else:
                        result[key] = value
                    memory_key = None  
                else:
                    if line.startswith('- ') or line.startswith('Вторник:') or line.startswith('Четверг:'):
                        if memory_key:
                            if memory_key not in result:
                                result[memory_key] = []
                        else:
                            memory_key = key
                            result[memory_key] = []
                        yaml_list = result[memory_key]
                        indent.append(indent_level)

            if indent_level < indent[-1]:
                yaml_list = None
                indent.pop()
        
        formatted_result = format_json(result)
        with open(json_file, 'w', encoding='utf-8') as file:
            file.writelines(str(formatted_result))

yaml_to_json("orig.yml", "output.json")
print("Файл конвертирован!")

