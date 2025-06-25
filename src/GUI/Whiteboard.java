//package GUI;
//import java.awt.*;
//import java.util.*;
//import javax.swing.*;
//import java.awt.event.*;
///**
// *
// * @author anant
// */
//
//class Whiteboard extends JFrame {
//    private ArrayList<Point> points = new ArrayList<>();
//    private boolean isTeacher;
//
//    public Whiteboard(boolean isTeacher) {
//        this.isTeacher = isTeacher;
//        setTitle("Whiteboard");
//        setSize(600, 400);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//                for (Point p : points) {
//                    g.fillOval(p.x, p.y, 5, 5);
//                }
//            }
//        };
//        panel.setBackground(Color.WHITE);
//        add(panel);
//
//        if (isTeacher) {
//            panel.addMouseMotionListener(new MouseMotionAdapter() {
//                @Override
//                public void mouseDragged(MouseEvent e) {
//                    points.add(e.getPoint());
//                    repaint();
//                }
//            });
//        }
//    }
//}
