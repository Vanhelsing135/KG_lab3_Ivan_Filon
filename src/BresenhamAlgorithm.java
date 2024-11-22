import javax.swing.*;
import java.awt.*;

public class BresenhamAlgorithm implements Algorithms{
    @Override
    public void draw(Graphics g, int r, int x0, int y0, int x1, int y1, JTextArea logArea, int cellSize, int centerX, int centerY) {
        long start = System.currentTimeMillis();
        logArea.setText("bresenham:\n");
        g.setColor(Color.BLACK);

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            logArea.append("point (" + x0 + ", " + y0 + ")\n");
            var cellX = x0 * cellSize + centerX;
            var cellY = -y0 * cellSize + centerY - cellSize;
            g.fillRect(cellX, cellY, cellSize, cellSize);

            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
        long end = System.currentTimeMillis() - start;
        logArea.append("Time: " + end / 1000.0 + " seconds\n");
    }
}
