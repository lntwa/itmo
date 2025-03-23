import re

def remove_students(studs):
    
    res = r'([А-Я])([а-яё]+)\s\1[а-яё]+\s\1[а-яё]+\s' + '3113'
    
    deleted_studs = re.sub(res, '-', studs)
    
    return deleted_studs

students = """Иванов Иван Иванович 3113
Петров Петр Петрович 3112
Иванов Илья Иванович 3113
Смирнова Светлана Сергеевна 3113
Сидоров Сергей Сергеевич 3113
Иванов Иван Иванович 3113""" 

print(remove_students(students))