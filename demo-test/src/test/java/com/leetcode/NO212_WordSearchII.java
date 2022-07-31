package com.leetcode;

import java.util.*;

/**
    212. 单词搜索 II
        给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words，
        返回所有二维网格上的单词 。单词必须按照字母顺序，通过 相邻的单元格内
        的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个
        单元格内的字母在一个单词中不允许被重复使用。
    示例 1：
        输入：board = [["o","a","a","n"],
                      ["e","t","a","e"],
                      ["i","h","k","r"],
                      ["i","f","l","v"]],
              words = ["oath","pea","eat","rain"]
        输出：["eat","oath"]
    示例 2：
        输入：board = [["a","b"],
                      ["c","d"]],
             words = ["abcb"]
        输出：[]
*/
public class NO212_WordSearchII {
    int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        Set<String> ans = new HashSet<>();
        for (int i = 0; i < board.length; ++i) {
            for (int j = 0; j < board[0].length; ++j) {
                dfs(board, trie, i, j, ans);
            }
        }
        return new ArrayList<>(ans);
    }

    public void dfs(char[][] board, Trie now, int i1, int j1, Set<String> ans) {
        if (!now.children.containsKey(board[i1][j1])) {
            return;
        }
        char ch = board[i1][j1];
        now = now.children.get(ch);
        if (!"".equals(now.word)) {
            ans.add(now.word);
        }

        board[i1][j1] = '#';
        for (int[] dir : dirs) {
            int i2 = i1 + dir[0], j2 = j1 + dir[1];
            if (i2 >= 0 && i2 < board.length && j2 >= 0 && j2 < board[0].length) {
                dfs(board, now, i2, j2, ans);
            }
        }
        board[i1][j1] = ch;
    }

    class Trie {
        String word;
        Map<Character, Trie> children;
        boolean isWord;

        public Trie() {
            this.word = "";
            this.children = new HashMap<>();
        }

        public void insert(String word) {
            Trie cur = this;
            for (int i = 0; i < word.length(); ++i) {
                char c = word.charAt(i);
                if (!cur.children.containsKey(c)) {
                    cur.children.put(c, new Trie());
                }
                cur = cur.children.get(c);
            }
            cur.word = word;
        }
    }
}

