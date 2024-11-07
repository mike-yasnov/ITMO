import numpy as np
import matplotlib.pyplot as plt

def julia_set(width, height, c, max_iter=100, zoom=1, x_center=0, y_center=0):
    # Создание комплексной плоскости
    x = np.linspace(x_center - 1.5 / zoom, x_center + 1.5 / zoom, width)
    y = np.linspace(y_center - 1.5 / zoom, y_center + 1.5 / zoom, height)
    X, Y = np.meshgrid(x, y)
    Z = X + 1j * Y

    # Итерации для вычисления множества Жюлиа
    img = np.zeros(Z.shape, dtype=int)
    for i in range(max_iter):
        mask = np.abs(Z) < 2
        img[mask] = i
        Z[mask] = Z[mask] ** 2 + c

    return img

plt.figure(figsize=(16, 16))
plt.suptitle('Множество Жюлиа', fontsize=32, y=1.05)

c = complex(-0.5251993, 0.5251993)  # Пример значения c
width, height = 800, 800
iterations_zoom_list = [(100, 1), (200, 2), (300, 3), (500, 4)]

# Генерация изображений множества Жюлиа при разном числе итераций и приближении
for idx, (max_iter, zoom) in enumerate(iterations_zoom_list):
    plt.subplot(2, 2, idx + 1)
    julia_img = julia_set(width, height, c, max_iter=max_iter, zoom=zoom)
    plt.imshow(julia_img, cmap='hot', extent=[-1.5 / zoom, 1.5 / zoom, -1.5 / zoom, 1.5 / zoom])
    plt.colorbar()
    plt.title(f'Итерации = {max_iter}, Приближение = {zoom}x')

plt.tight_layout()
plt.savefig('julia_set_plot.png')  # Сохранение графика в файл
