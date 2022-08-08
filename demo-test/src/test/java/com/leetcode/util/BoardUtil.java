/**
 * copyright 2022/1/19
 */
package com.leetcode.util;

import java.util.HashMap;
import java.util.Map;

import static com.leetcode.util.LogUtil.info;

public class BoardUtil {

    public static void printBoard(char[][] board) {
        System.out.println("-----------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0)
                    System.out.print("| ");
                System.out.print(board[i][j] + " ");
            }
            System.out.print("| ");
            System.out.println();
            if (i % 3 == 2)
                System.out.println("-------------------------");
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

}















/**
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