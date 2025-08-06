package com.animation.tree;

import javax.swing.*;
import java.awt.*;

public class BPlusTreeAnimation extends JFrame {

    private BPlusTree<Integer, String> bPlusTree;
    private BPlusTreeAnimationPanel animationPanel;
    private JTextField textField;

    public BPlusTreeAnimation() {
        this.bPlusTree = new BPlusTree<>(4); // 默认阶数为4
        initUI();
    }

    private void initUI() {
        setTitle("B+-树动画演示");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        animationPanel = new BPlusTreeAnimationPanel();
        animationPanel.setTree(bPlusTree);
        add(animationPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        textField = new JTextField(5);
        JButton insertButton = new JButton("插入");
        JButton deleteButton = new JButton("删除");
        JButton searchButton = new JButton("查找");

        controlPanel.add(new JLabel("数值:"));
        controlPanel.add(textField);
        controlPanel.add(insertButton);
        controlPanel.add(deleteButton);
        controlPanel.add(searchButton);

        insertButton.addActionListener(e -> {
            try {
                int key = Integer.parseInt(textField.getText());
                bPlusTree.insert(key, String.valueOf(key));
                animationPanel.setTree(bPlusTree);
                textField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(BPlusTreeAnimation.this, "请输入一个有效的整数。", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int key = Integer.parseInt(textField.getText());
                bPlusTree.delete(key);
                animationPanel.setTree(bPlusTree);
                textField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(BPlusTreeAnimation.this, "请输入一个有效的整数。", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> {
            try {
                int key = Integer.parseInt(textField.getText());
                String value = bPlusTree.search(key);
                if (value != null) {
                    JOptionPane.showMessageDialog(BPlusTreeAnimation.this, "找到值: " + value, "查找成功", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(BPlusTreeAnimation.this, "未找到键: " + key, "查找失败", JOptionPane.WARNING_MESSAGE);
                }
                textField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(BPlusTreeAnimation.this, "请输入一个有效的整数。", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(controlPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BPlusTreeAnimation frame = new BPlusTreeAnimation();
            frame.setVisible(true);
        });
    }
}