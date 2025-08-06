package com.leetcode.hard;

import com.animation.Animation;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NO127_H_WordLadder_Animation extends JPanel implements Animation {

    private String beginWord;
    private String endWord;
    private List<String> wordList;
    private int ladderLength = 0;

    public NO127_H_WordLadder_Animation(String beginWord, String endWord, List<String> wordList) {
        this.beginWord = beginWord;
        this.endWord = endWord;
        this.wordList = wordList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 动画绘制逻辑将在这里实现
        g.drawString("Ladder Length: " + ladderLength, 50, 50);
    }

    public void start() {
        // 动画执行逻辑将在这里实现
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            ladderLength = 0;
            SwingUtilities.invokeLater(() -> repaint());
            return;
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int level = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String currentWord = queue.poll();
                if (currentWord.equals(endWord)) {
                    ladderLength = level;
                    SwingUtilities.invokeLater(() -> repaint());
                    return;
                }
                char[] charArray = currentWord.toCharArray();
                for (int j = 0; j < charArray.length; j++) {
                    char originalChar = charArray[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == originalChar) continue;
                        charArray[j] = c;
                        String nextWord = new String(charArray);
                        if (wordSet.contains(nextWord) && !visited.contains(nextWord)) {
                            queue.offer(nextWord);
                            visited.add(nextWord);
                        }
                    }
                    charArray[j] = originalChar;
                }
            }
            level++;
        }
        ladderLength = 0;
        SwingUtilities.invokeLater(() -> repaint());
    }

    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        JFrame frame = new JFrame("Word Ladder Animation");
        NO127_H_WordLadder_Animation animation = new NO127_H_WordLadder_Animation(beginWord, endWord, wordList);
        frame.add(animation);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        animation.start();
    }
}