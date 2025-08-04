package com.leetcode.todo;

import javax.swing.*;
import java.awt.*;

public class SwingTest {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Swing测试");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);
                
                JLabel label = new JLabel("如果你能看到这个窗口，说明Swing正常工作！", JLabel.CENTER);
                label.setFont(new Font("Dialog", Font.BOLD, 16));
                frame.add(label);
                
                System.out.println("正在显示测试窗口...");
                frame.setVisible(true);
                
                // 5秒后自动关闭
                Timer timer = new Timer(5000, e -> {
                    System.out.println("测试完成，窗口将关闭");
                    System.exit(0);
                });
                timer.setRepeats(false);
                timer.start();
            }
        });
    }
}
