import javax.swing.*;
import java.awt.*;

public interface Algorithms {
    void draw(Graphics g, int r, int x0, int y0, int x1, int y1, JTextArea logArea, int cellSize, int centerX, int centerY);
}