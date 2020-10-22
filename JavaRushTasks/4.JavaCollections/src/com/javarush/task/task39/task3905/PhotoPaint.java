package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        if (y < 0 || y >= image.length || x < 0 || x >= image[0].length) {
            return false;
        }
        Color originalColor = image[y][x];
        if (originalColor.equals(desiredColor)) {
            return false;
        }
        paint(image, x, y, desiredColor, originalColor);
        return true;
    }

    private void  paint(Color[][] image, int x, int y, Color desiredColor, Color originalColor) {
        if (y < 0 || y >= image.length || x < 0 || x >= image[0].length) {
            return;
        }
        if (image[y][x] == originalColor) {
            image[y][x] = desiredColor;
            paint(image, x - 1, y, desiredColor, originalColor);
            paint(image, x + 1, y, desiredColor, originalColor);
            paint(image, x, y - 1, desiredColor, originalColor);
            paint(image, x, y + 1, desiredColor, originalColor);
        }
    }

}
