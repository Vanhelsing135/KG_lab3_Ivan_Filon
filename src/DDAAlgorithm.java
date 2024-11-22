import javax.swing.*;
import java.awt.*;

public class DDAAlgorithm implements Algorithms {
    @Override
    public void draw(Graphics g, int r, int x0, int y0, int x1, int y1, JTextArea logArea, int cellSize, int centerX, int centerY) {
        long start = System.currentTimeMillis();
        logArea.setText("DDAAlgorithm:\n");
        g.setColor(Color.BLACK);
        logArea.append("dx = " + x1 + " - " + x0 + "\n");
        logArea.append("dy = " + y1 + " - " + y0 + "\n");
        int dx = x1 - x0;
        int dy = y1 - y0;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        logArea.append("steps = max(abs(dx), abs(dy)) = " + "max(" + dx + "," + dy + ")" + " = " + steps + "\n");
        logArea.append("xInc = dx / steps = " + dx + " / " + steps + " = " + dx / (double) steps + "\n");
        double xInc = dx / (double) steps;
        logArea.append("yInc = dy / steps = " + dy + " / " + steps + " = " + dy / (double) steps + "\n");
        double yInc = dy / (double) steps;
        double x = x0, y = y0;
        logArea.append("x = x0, y = y0\n");

        for (int i = 0; i <= steps; i++) {
            logArea.append("point (" + (int) Math.round(x) + ", " + (int) Math.round(y) + ")\n");
            var cellX = (int) Math.round(x) * cellSize + centerX;
            var cellY = -(int) Math.round(y) * cellSize + centerY - cellSize;
            g.fillRect(cellX, cellY, cellSize, cellSize);
            logArea.append("x = x + xInc = " + x + " + " + xInc + " = " + (x + xInc) + "\n");
            x += xInc;
            logArea.append("y = y + xInc = " + y + " + " + yInc + " = " + (y + yInc) + "\n");
            y += yInc;
        }
        long end = System.currentTimeMillis() - start;
        logArea.append("Time: " + end / 1000.0 + " seconds\n");
    }
}
