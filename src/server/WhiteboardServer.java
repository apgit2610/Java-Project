package server;
/**
 *
 * @author anant
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;


public class WhiteboardServer extends JFrame {
    private ArrayList<ColoredPoint> points = new ArrayList<>();
    private ServerSocket serverSocket;
    private ArrayList<Socket> studentSockets = new ArrayList<>();
    private Color currentColor = Color.BLACK;
    private boolean eraserMode = false;

    public WhiteboardServer() {
        setTitle("Teacher's Whiteboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CanvasPanel canvas = new CanvasPanel();
        add(canvas, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        JButton colorButton = new JButton("Pick Color");
        JButton eraserButton = new JButton("Eraser");

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose a color", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                eraserMode = false;
            }
        });

        eraserButton.addActionListener(e -> {
            currentColor = Color.WHITE;
            eraserMode = true;
        });

        controls.add(colorButton);
        controls.add(eraserButton);
        add(controls, BorderLayout.SOUTH);

        new Thread(this::startServer).start();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Whiteboard Server started on port 5000...");

            while (true) {
                Socket studentSocket = serverSocket.accept();
                studentSockets.add(studentSocket);
                System.out.println("Student connected!");

                new Thread(() -> listenForStudents(studentSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForStudents(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                String line = reader.readLine();
                if (line == null) break;

                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                Color color = new Color(Integer.parseInt(parts[2]));

                points.add(new ColoredPoint(x, y, color));
                repaint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastDraw(int x, int y, Color color) {
        for (Socket socket : studentSockets) {
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(x + "," + y + "," + color.getRGB());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class CanvasPanel extends JPanel {
        public CanvasPanel() {
            addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    int x = e.getX();
                    int y = e.getY();
                    points.add(new ColoredPoint(x, y, currentColor));
                    repaint();
                    broadcastDraw(x, y, currentColor);
                }
            });
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (ColoredPoint p : points) {
                g.setColor(p.color);
                g.fillOval(p.x, p.y, 5, 5);
            }
        }
    }

    static class ColoredPoint {
        int x, y;
        Color color;
        ColoredPoint(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WhiteboardServer().setVisible(true));
    }
}