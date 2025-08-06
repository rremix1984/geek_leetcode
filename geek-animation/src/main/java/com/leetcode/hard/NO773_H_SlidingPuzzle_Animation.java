package com.leetcode.hard;

import com.animation.Animation;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class NO773_H_SlidingPuzzle_Animation extends JPanel implements Animation {

    private static final int TILE_SIZE = 80;
    private int[][] board;
    private int moves = -1;

    public NO773_H_SlidingPuzzle_Animation(int[][] board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    private void drawBoard(Graphics g) {
        if (board == null) return;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != 0) {
                    g.setColor(Color.CYAN);
                    g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Arial", Font.BOLD, 32));
                    g.drawString(String.valueOf(board[i][j]), j * TILE_SIZE + 30, i * TILE_SIZE + 50);
                }
                g.setColor(Color.GRAY);
                g.drawRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public void start() {
        // 动画执行逻辑将在这里实现
        String target = "123450";
        StringBuilder startSb = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                startSb.append(cell);
            }
        }
        String start = startSb.toString();

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(start);
        visited.add(start);

        int step = 0;
        int[][] dirs = {{1, 3}, {0, 2, 4}, {1, 5}, {0, 4}, {1, 3, 5}, {2, 4}};

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();
                if (curr.equals(target)) {
                    moves = step;
                    SwingUtilities.invokeLater(() -> this.repaint());
                    return;
                }
                int zeroIndex = curr.indexOf('0');
                for (int nextIndex : dirs[zeroIndex]) {
                    char[] chars = curr.toCharArray();
                    char temp = chars[zeroIndex];
                    chars[zeroIndex] = chars[nextIndex];
                    chars[nextIndex] = temp;
                    String next = new String(chars);
                    if (!visited.contains(next)) {
                        queue.offer(next);
                        visited.add(next);
                    }
                }
            }
            step++;
        }
        moves = -1;
        SwingUtilities.invokeLater(() -> this.repaint());
    }

    public static void main(String[] args) {
        int[][] board = {{1, 2, 3}, {4, 0, 5}};
        JFrame frame = new JFrame("Sliding Puzzle Animation");
        NO773_H_SlidingPuzzle_Animation animation = new NO773_H_SlidingPuzzle_Animation(board);
        frame.add(animation);
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        animation.start();
    }
}