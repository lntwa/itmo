import yaml
import csv

def yaml_to_csv(yaml_file, csv_file):
    with open(yaml_file, 'r', encoding='utf-8') as file:
        yaml_data = yaml.safe_load(file)

    with open(csv_file, 'w', encoding='utf-8', newline='') as file:
        csv_writer = csv.writer(file)

        if isinstance(yaml_data, list):
            headers = yaml_data[0].keys()
            csv_writer.writerow(headers)

            for entry in yaml_data:
                csv_writer.writerow(entry.values())

        elif isinstance(yaml_data, dict):
            csv_writer.writerow(["Key", "Value"])

            for key, value in yaml_data.items():
                csv_writer.writerow([key, value])

        else:
            raise ValueError("Unsupported YAML format for conversion to CSV.")

    print(f"Данные успешно преобразованы из {yaml_file} в {csv_file}.")

yaml_to_csv("orig.yaml", "output.csv")
