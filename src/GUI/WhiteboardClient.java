package GUI;
/**
 *
 * @author anant
 */
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class WhiteboardClient extends JFrame {
    private ArrayList<ColoredPoint> points = new ArrayList<>();
    private Socket socket;

    public WhiteboardClient() {
        setTitle("Student's Whiteboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        CanvasPanel canvas = new CanvasPanel();
        add(canvas);

        new Thread(this::connectToServer).start();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Connected to Teacher's Whiteboard!");

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

    class CanvasPanel extends JPanel {
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
        SwingUtilities.invokeLater(() -> new WhiteboardClient().setVisible(true));
    }
}
