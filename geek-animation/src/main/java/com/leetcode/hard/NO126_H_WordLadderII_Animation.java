package com.leetcode.hard;

import com.animation.Animation;


import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class NO126_H_WordLadderII_Animation extends JPanel implements Animation {

    private String beginWord;
    private String endWord;
    private List<String> wordList;
    private List<List<String>> ladders = new ArrayList<>();

    public NO126_H_WordLadderII_Animation(String beginWord, String endWord, List<String> wordList) {
        this.beginWord = beginWord;
        this.endWord = endWord;
        this.wordList = wordList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 动画绘制逻辑将在这里实现
        int y = 50;
        for (List<String> ladder : ladders) {
            g.drawString(String.join(" -> ", ladder), 50, y);
            y += 20;
        }
    }

    public void start() {
        // 动画执行逻辑将在这里实现
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordSet.contains(endWord)) {
            repaint();
            return;
        }

        Map<String, List<String>> map = new HashMap<>();
        Map<String, Integer> dist = new HashMap<>();

        bfs(beginWord, endWord, wordSet, map, dist);
        dfs(beginWord, endWord, map, new LinkedList<>());
        repaint();
    }

    private void bfs(String beginWord, String endWord, Set<String> wordSet, Map<String, List<String>> map, Map<String, Integer> dist) {
        Queue<String> q = new LinkedList<>();
        q.offer(beginWord);
        dist.put(beginWord, 0);

        for (String w : wordSet) {
            map.put(w, new ArrayList<>());
        }

        int step = 0;
        boolean found = false;
        while (!q.isEmpty()) {
            step++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String curr = q.poll();
                char[] chars = curr.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    char old = chars[j];
                    for (char c = 'a'; c <= 'z'; c++) {
                        chars[j] = c;
                        String next = new String(chars);
                        if (dist.containsKey(next) && dist.get(next) == step) {
                            map.get(next).add(curr);
                        }
                        if (wordSet.contains(next) && !dist.containsKey(next)) {
                            dist.put(next, step);
                            q.offer(next);
                            map.get(next).add(curr);
                            if (next.equals(endWord)) {
                                found = true;
                            }
                        }
                    }
                    chars[j] = old;
                }
            }
            if (found) break;
        }
    }

    private void dfs(String beginWord, String curr, Map<String, List<String>> map, LinkedList<String> path) {
        path.addFirst(curr);
        if (curr.equals(beginWord)) {
            ladders.add(new ArrayList<>(path));
        } else {
            for (String prev : map.get(curr)) {
                dfs(beginWord, prev, map, path);
            }
        }
        path.removeFirst();
    }

    public static void main(String[] args) {
        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        JFrame frame = new JFrame("Word Ladder II Animation");
        NO126_H_WordLadderII_Animation animation = new NO126_H_WordLadderII_Animation(beginWord, endWord, wordList);
        frame.add(animation);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        animation.start();
    }
}