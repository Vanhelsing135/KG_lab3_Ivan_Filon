import javax.swing.*;
import java.awt.*;

public class BresenhamCircleAlgorithm implements Algorithms {
    @Override
    public void draw(Graphics g, int r, int x0, int y0, int x1, int y1, JTextArea logArea, int cellSize, int centerX, int centerY) {
        long start = System.currentTimeMillis();
        logArea.setText("circle bresenham:\n");
        g.setColor(Color.BLACK);

        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        while (x <= y) {
            drawcircle(g, x0, y0, x, y, cellSize, centerX, centerY);
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
        }
        long end = System.currentTimeMillis() - start;
        logArea.append("Time: " + end / 1000.0 + " seconds\n");

    }

    private void drawcircle(Graphics g, int xc, int yc, int x, int y, int cellSize, int centerX, int centerY) {
        drawpoint(g, xc + x, yc + y, cellSize, centerX, centerY);
        drawpoint(g, xc - x, yc + y, cellSize, centerX, centerY);
        drawpoint(g, xc + x, yc - y, cellSize, centerX, centerY);
        drawpoint(g, xc - x, yc - y, cellSize, centerX, centerY);
        drawpoint(g, xc + y, yc + x, cellSize, centerX, centerY);
        drawpoint(g, xc - y, yc + x, cellSize, centerX, centerY);
        drawpoint(g, xc + y, yc - x, cellSize, centerX, centerY);
        drawpoint(g, xc - y, yc - x, cellSize, centerX, centerY);
    }

    private void drawpoint(Graphics g, int x, int y, int cellSize, int centerX, int centerY) {
        g.fillRect(x * cellSize + centerX, -y * cellSize + centerY - cellSize, cellSize, cellSize);
    }

}
