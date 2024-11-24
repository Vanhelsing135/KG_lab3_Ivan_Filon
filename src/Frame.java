import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private final JTextField x0Field = new JTextField("0");
    private final JTextField y0Field = new JTextField("0");
    private final JTextField x1Field = new JTextField("0");
    private final JTextField y1Field = new JTextField("0");
    private final JTextField radiusField = new JTextField("0");
    private final JRadioButton stepByStepAlgoButton, DDAAlgoButton, bresenhamAlgoButton, bresenhamCircleAlgoButton;
    private JTextArea logArea = null;
    private int centerX, centerY, cellSize = 20;
    private final JPanel scene;
    private final JTextField cellSizeField = new JTextField(String.valueOf(cellSize));
    private Algorithms curAlgo = new StepByStepAlgorithm();

    public Frame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        scene = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
                drawAxis(g);
                draw(g);
            }
        };
        scene.setPreferredSize(new Dimension(800, 800));
        add(scene, BorderLayout.CENTER);

        JPanel algosPanel = new JPanel();
        algosPanel.setLayout(new BoxLayout(algosPanel, BoxLayout.Y_AXIS));
        algosPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(algosPanel, BorderLayout.EAST);

        ButtonGroup groupButton = new ButtonGroup();
        stepByStepAlgoButton = new JRadioButton("StepByStep");
        stepByStepAlgoButton.setSelected(true);
        DDAAlgoButton = new JRadioButton("DDA");
        bresenhamAlgoButton = new JRadioButton("Bresenham");
        bresenhamCircleAlgoButton = new JRadioButton("BresenhamCircle");
        groupButton.add(stepByStepAlgoButton);
        groupButton.add(DDAAlgoButton);
        groupButton.add(bresenhamAlgoButton);
        groupButton.add(bresenhamCircleAlgoButton);

        JPanel choosingPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        choosingPanel.add(stepByStepAlgoButton);
        choosingPanel.add(DDAAlgoButton);
        choosingPanel.add(bresenhamAlgoButton);
        choosingPanel.add(bresenhamCircleAlgoButton);
        algosPanel.add(choosingPanel);
        algosPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        algosPanel.add(createInputField("x0:", x0Field));
        algosPanel.add(createInputField("y0:", y0Field));
        algosPanel.add(createInputField("x1:", x1Field));
        algosPanel.add(createInputField("y1:", y1Field));
        algosPanel.add(createInputField("radius:", radiusField));
        radiusField.setVisible(false);
        algosPanel.add(createInputField("cellSize:", cellSizeField));

        JButton drawButton = new JButton("Draw");
        drawButton.setFont(new Font("Arial", Font.BOLD, 16));
        drawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        drawButton.addActionListener(e -> {
            try {
                cellSize = Integer.parseInt(cellSizeField.getText());
                scene.repaint();
            } catch (NumberFormatException ex) {
                logArea.setText("Invalid cell size!");
            }
        });
        algosPanel.add(drawButton);
        algosPanel.add(Box.createRigidArea(new Dimension(0, 20)));// Log area
        logArea = new JTextArea();
        logArea.setFont(new Font("", Font.PLAIN, 12));
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(300, 150));
        scrollPane.setBorder(BorderFactory.createTitledBorder("logArea"));
        add(scrollPane, BorderLayout.SOUTH);

        stepByStepAlgoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curAlgo = new StepByStepAlgorithm();
                radiusField.setVisible(false);
                x1Field.setVisible(true);
                y1Field.setVisible(true);
                algosPanel.revalidate();
                algosPanel.repaint();
            }
        });
        DDAAlgoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curAlgo = new DDAAlgorithm();
                radiusField.setVisible(false);
                x1Field.setVisible(true);
                y1Field.setVisible(true);
                algosPanel.revalidate();
                algosPanel.repaint();
            }
        });
        bresenhamAlgoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curAlgo = new BresenhamAlgorithm();
                radiusField.setVisible(false);
                x1Field.setVisible(true);
                y1Field.setVisible(true);
                algosPanel.revalidate();
                algosPanel.repaint();
            }
        });
        bresenhamCircleAlgoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curAlgo = new BresenhamCircleAlgorithm();
                radiusField.setVisible(true);
                x1Field.setVisible(false);
                y1Field.setVisible(false);
                algosPanel.revalidate();
                algosPanel.repaint();
            }
        });
    }

    private JPanel createInputField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return panel;
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < scene.getWidth(); i += cellSize) {
            g.drawLine(i, 0, i, scene.getHeight());
        }
        for (int i = 0; i < scene.getHeight(); i += cellSize) {
            g.drawLine(0, i, scene.getWidth(), i);
        }
        g.setColor(Color.GRAY);
        int width = scene.getWidth();
        int height = scene.getHeight();
        centerX = (width / 2 / cellSize) * cellSize;
        centerY = (height / 2 / cellSize) * cellSize;

        for (int i = centerX; i < scene.getWidth(); i += cellSize) {
            g.drawLine(i, centerY - 5, i, centerY + 5);
        }
        for (int i = centerX; i > 0; i -= cellSize) {
            g.drawLine(i, centerY - 5, i, centerY + 5);
        }
        g.setColor(Color.BLACK);
        g.drawString("1", centerX + cellSize - 3, centerY + cellSize);
        g.drawString("-1", centerX - cellSize - 3, centerY + cellSize);

        for (int i = centerY; i < scene.getHeight(); i += cellSize) {
            g.drawLine(centerX - 5, i, centerX + 5, i);
        }
        for (int i = centerY; i > 0; i -= cellSize) {
            g.drawLine(centerX - 5, i, centerX + 5, i);
        }
    }

    private void drawAxis(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        int width = scene.getWidth();
        int height = scene.getHeight();
        centerX = (width / 2 / cellSize) * cellSize;
        centerY = (height / 2 / cellSize) * cellSize;
        g.drawLine(centerX, 0, centerX, height);
        g.drawLine(0, centerY, width, centerY);
        g.setColor(Color.DARK_GRAY);
        g.drawLine(width - 20, centerY - 10, width, centerY);
        g.drawLine(width - 20, centerY + 10, width, centerY);
        g.drawLine(centerX - 10, 20, centerX, 0);
        g.drawLine(centerX + 10, 20, centerX, 0);
        g.setColor(Color.BLACK);
        g.drawString("X", width - 30, centerY - 5);
        g.drawString("Y", centerX + 5, 30);
    }

    private void draw(Graphics g) {
        int x0 = Integer.parseInt(x0Field.getText());
        int y0 = Integer.parseInt(y0Field.getText());

        if (bresenhamCircleAlgoButton.isSelected()) {
            int radius = Integer.parseInt(radiusField.getText());
            curAlgo.draw(g, radius, x0, y0, 0, 0, logArea, cellSize, centerX, centerY);
        } else {
            int x1 = Integer.parseInt(x1Field.getText());
            int y1 = Integer.parseInt(y1Field.getText());
            if (stepByStepAlgoButton.isSelected()) {
                curAlgo.draw(g, 0, x0, y0, x1, y1, logArea, cellSize, centerX, centerY);
            } else if (DDAAlgoButton.isSelected()) {
                curAlgo.draw(g, 0, x0, y0, x1, y1, logArea, cellSize, centerX, centerY);
            } else if (bresenhamAlgoButton.isSelected()) {
                curAlgo.draw(g, 0, x0, y0, x1, y1, logArea, cellSize, centerX, centerY);
            }
        }
    }
}