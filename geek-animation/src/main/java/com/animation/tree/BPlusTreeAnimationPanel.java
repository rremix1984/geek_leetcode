package com.animation.tree;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BPlusTreeAnimationPanel extends JPanel {

    private BPlusTree<Integer, String> bPlusTree;
    private static final int NODE_WIDTH = 120;
    private static final int NODE_HEIGHT = 40;
    private static final int HORIZONTAL_GAP = 20;
    private static final int VERTICAL_GAP = 60;

    public BPlusTreeAnimationPanel() {
        this.bPlusTree = new BPlusTree<>(4); // 示例阶数
    }

    public void setTree(BPlusTree<Integer, String> tree) {
        this.bPlusTree = tree;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bPlusTree != null && bPlusTree.root != null) {
            drawNode(g, bPlusTree.root, getWidth() / 2, 50, getWidth() / 4);
        }
    }

    private void drawNode(Graphics g, BPlusTree.Node<Integer> node, int x, int y, int horizontalOffset) {
        if (node == null) {
            return;
        }

        // 绘制节点
        String keys = node.keys.toString();
        g.setColor(Color.WHITE);
        g.fillRect(x - NODE_WIDTH / 2, y - NODE_HEIGHT / 2, NODE_WIDTH, NODE_HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect(x - NODE_WIDTH / 2, y - NODE_HEIGHT / 2, NODE_WIDTH, NODE_HEIGHT);
        g.drawString(keys, x - g.getFontMetrics().stringWidth(keys) / 2, y);

        // 绘制子节点
        if (!node.isLeaf()) {
            BPlusTree.InternalNode<Integer> internalNode = (BPlusTree.InternalNode<Integer>) node;
            List<BPlusTree.Node<Integer>> children = internalNode.children;
            int childCount = children.size();
            int startX = x - (childCount - 1) * (NODE_WIDTH + HORIZONTAL_GAP) / 2;

            for (int i = 0; i < childCount; i++) {
                int childX = startX + i * (NODE_WIDTH + HORIZONTAL_GAP);
                int childY = y + VERTICAL_GAP + NODE_HEIGHT;
                g.drawLine(x, y + NODE_HEIGHT / 2, childX, childY - NODE_HEIGHT / 2);
                drawNode(g, children.get(i), childX, childY, horizontalOffset / 2);
            }
        }
    }
}