import re

s = ''
my_isu = 472157
s += str(my_isu % 6)
s += str(my_isu % 4)
s += str(my_isu % 8)

def count_smiles(text):

  matches = re.findall(r"\[\>\/", text)
  return len(matches)

test1 =  r"Here is a smiley [>/ in the text."
test2 =  r"No smileys here, just text."
test3 =  r":There are two smileys here [>/ and another one [>/."
test4 =  r"Almost a smiley >/, but it's missing the opening bracket."
test5 =  r"[>/ at the start and [>/ at the end of the string."

count = count_smiles(test2)

print(f"Test1: {count_smiles(test1)}")
print(f"Test2: {count_smiles(test2)}")
print(f"Test3: {count_smiles(test3)}")
print(f"Test4: {count_smiles(test4)}")
print(f"Test5: {count_smiles(test5)}")