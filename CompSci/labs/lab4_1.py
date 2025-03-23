import yaml
import json

with open('orig.yaml', 'r', encoding='utf-8') as yaml_file:
    yaml_data = yaml.safe_load(yaml_file)
    json_data = json.dumps(yaml_data, indent=4, ensure_ascii=False)
    with open('output.json', 'w', encoding='utf-8') as json_file:
        json_file.write(json_data)