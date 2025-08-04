package com.leetcode.todo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * 通用算法动画模板
 */
public class AlgorithmAnimationTemplate extends JFrame {
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JButton startButton;
    private Timer timer;
    private int stepIndex = 0;
    private List<Integer> data;

    public AlgorithmAnimationTemplate(String title, List<Integer> data) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        this.data = data;
        initComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        startButton = new JButton("开始动画");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new VisualizationPanel(), BorderLayout.CENTER);

        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(startButton);

        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (stepIndex < data.size()) {
                    updateDisplay();
                    stepIndex++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private void updateDisplay() {
        repaint();
    }

    private class VisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawData(g2d);
        }

        private void drawData(Graphics2D g2d) {
            int startX = 50;
            int startY = 400;
            int width = 40;
            int heightUnit = 10;

            for (int i = 0; i < data.size(); i++) {
                int value = data.get(i);
                int height = value * heightUnit;
                g2d.setColor(i < stepIndex ? Color.GREEN : Color.GRAY);
                Rectangle rect = new Rectangle(startX + i * (width + 10), startY - height, width, height);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                g2d.drawString(String.valueOf(value), startX + i * (width + 10) + 10, startY + 20);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
new AlgorithmAnimationTemplate("通用算法动画模板", Arrays.asList(5, 10, 15, 20)).setVisible(true);
            }
        });
    }
}

