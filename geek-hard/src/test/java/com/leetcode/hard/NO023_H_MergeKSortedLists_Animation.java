package com.leetcode.hard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * NO.23 合并K个升序链表 - 动画演示
 */
public class NO023_H_MergeKSortedLists_Animation extends JFrame {
    private java.util.List<JLabel> listNodes;
    private JPanel mainPanel;
    private JButton startButton;
    private Timer animationTimer;
    private PriorityQueue<ListNode> pq;
    private ListNode mergedListHead;

    public NO023_H_MergeKSortedLists_Animation() {
        setTitle("NO.23 合并K个升序链表 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        initComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        startButton = new JButton("开始合并");
        listNodes = new ArrayList<>();
        pq = new PriorityQueue<>((a, b) -> a.val - b.val);

        // 示例链表
        ListNode[] lists = new ListNode[]{
            new ListNode(1, new ListNode(4, new ListNode(5))),
            new ListNode(1, new ListNode(3, new ListNode(4))),
            new ListNode(2, new ListNode(6))
        };

        for (ListNode node : lists) {
            while (node != null) {
                pq.offer(node);
                node = node.next;
            }
        }
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
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
        animationTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!pq.isEmpty()) {
                    ListNode minNode = pq.poll();
                    attachNode(minNode);
                    if (minNode.next != null) {
                        pq.offer(minNode.next);
                    }
                } else {
                    animationTimer.stop();
                }
            }
        });
        animationTimer.start();
    }

    private void attachNode(ListNode node) {
        if (mergedListHead == null) {
            mergedListHead = node;
        } else {
            ListNode temp = mergedListHead;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }

        JLabel nodeLabel = new JLabel(String.valueOf(node.val));
        nodeLabel.setOpaque(true);
        nodeLabel.setBackground(Color.CYAN);
        nodeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        listNodes.add(nodeLabel);
        updateDisplay();
    }

    private void updateDisplay() {
        mainPanel.removeAll();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        for (JLabel label : listNodes) {
            mainPanel.add(label);
        }
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NO023_H_MergeKSortedLists_Animation().setVisible(true));
    }
}
