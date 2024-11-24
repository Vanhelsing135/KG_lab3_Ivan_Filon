import javax.swing.*;
import java.awt.*;

public class StepByStepAlgorithm implements Algorithms {
    @Override
    public void draw(Graphics g, int r, int x0, int y0, int x1, int y1, JTextArea logArea, int cellSize, int centerX, int centerY) {
        long start = System.currentTimeMillis();
        if (x0 > x1) {
            var buf = x0;
            x0 = x1;
            x1 = buf;
        }

        if (y0 > y1) {
            var buf = y0;
            y0 = y1;
            y1 = buf;
        }

        logArea.setText("stepByStep:\n");
        g.setColor(Color.BLACK);

        int dx = x1 - x0;
        int dy = y1 - y0;

        if (dx > dy) {
            double slope = (double) dy / dx;

            for (int x = x0; x <= x1; x++) {
                double y = y0 + slope * (x - x0);
                int yRounded = (int) Math.round(y);
                logArea.append("point (" + x + ", " + yRounded + ")\n");
                var cellX = x * cellSize + centerX;
                var cellY = -yRounded * cellSize + centerY - cellSize;
                g.fillRect(cellX, cellY, cellSize, cellSize);
            }
        } else {
            double slope = (double) dx / dy;
            for (int y = y0; y <= y1; y++) {
                double x = x0 + slope * (y - y0);
                int xRounded = (int) Math.round(x);
                logArea.append("point (" + xRounded + ", " + y + ")\n");
                var cellX = xRounded * cellSize + centerX;
                var cellY = -y * cellSize + centerY - cellSize;
                g.fillRect(cellX, cellY, cellSize, cellSize);
            }
        }
        long end = System.currentTimeMillis() - start;
        logArea.append("Time: " + end / 1000.0 + " seconds\n");

    }
}
