import matplotlib.pyplot as plt
import pandas as pd


df = pd.read_csv("Биржа_Лаба5.csv", encoding='cp1251')
fig, ax = plt.subplots(1, 4,figsize=(16, 10)) #fig — это фигура, а ax — массив осей (подграфиков), на которых будут строиться графики
df.groupby(['Дата']).boxplot(column="Открытие,Закрытие,Макс,Мин".split(","), ax=ax)
plt.show()
