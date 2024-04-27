/**
 * copyright 2022/1/19
 */
package com.leetcode.util;

import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

@SuppressWarnings("unused")
public class BoardUtil {

    public static void printBoard(char[][] board) {
        out.println("-----------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0)
                    out.print("| ");
                out.print(board[i][j] + " ");
            }
            out.print("| ");
            out.println();
            if (i % 3 == 2)
                out.println("-------------------------");
        }
    }

    public static class Node {
        public int x;
        public int y;
        public int step;

        public Node(int start, int end, int step) {
            this.x = start;
            this.y = end;
            this.step = step;
        }
    }

    public static class Trie {
        public String word;
        public Map<Character, Trie> children;

        public Trie() {
            this.word = "";
            this.children = new HashMap<>();
        }

        public void insert(String word) {
            Trie cur = this;
            // 一个字符接一个字符的遍历，最后把word赋值给最后一层 p -> e -> a -> pea -> null
            for (Character c : word.toCharArray()) {
                cur.children.putIfAbsent(c, new Trie());
                cur = cur.children.get(c);
            }
            cur.word = word;
        }
    }

    public static void swap(char[] array, int x, int y) {
        char temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }
}















/*
public static class Trie {

    public String word;

    public Map<Character, Trie> children;

    public Trie() {
        this.word = "";
        this.children = new HashMap<>();
    }

    public void insert(String word) {
        Trie cur = this;
        for (Character c : word.toCharArray()) {
            cur.children.putIfAbsent(c, new Trie());
            cur = cur.children.get(c);
        }
        cur.word = word;
    }
}
*/