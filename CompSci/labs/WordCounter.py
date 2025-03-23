from PyPDF2 import PdfReader

pdf_path = "C:/Users/Анна/Desktop/itmo/informatika/abstracts/elibrary_48660445_53052346.pdf"
reader = PdfReader(pdf_path)

text = ""
for page in reader.pages:
    text += page.extract_text()

word_count = len(text.split())
print(word_count)
