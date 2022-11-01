/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    (中等)
    208. 实现 Trie (前缀树)
        Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
        请你实现 Trie 类：
            Trie() 初始化前缀树对象。
            void insert(String word) 向前缀树中插入字符串 word 。
            boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
            boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
    示例：
        输入
            ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
            [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
        输出
            [null, null, true, false, true, null, true]

      *
      * Your Trie object will be instantiated and called as such:
      * Trie obj = new Trie();
      * obj.insert(word);
      * boolean param_2 = obj.search(word);
      * boolean param_3 = obj.startsWith(prefix);
      *
*/
public class NO208_N_ImplementTriePrefixTree {

    @Test
    public void test() {
        Trie trie = new Trie();
        trie.insert("apple");
        assert trie.search("apple");   // 返回 True
        assert !trie.search("app");     // 返回 False
        assert trie.startsWith("app");       // 返回 True
        trie.insert("app");
        assert trie.search("app");     // 返回 True
    }

    static class Trie {

        public Trie[] children;
        private boolean isEnd;

        public Trie() {
            children = new Trie[26];
            isEnd = false;
        }

        public void insert(String word) {
            Trie node = this;
            for (Character ch : word.toCharArray()) {
                int index = ch - 'a';
                if (node.children[index] == null)
                    node.children[index] = new Trie();
                node = node.children[index];
            }
            node.isEnd = true;
        }

        public boolean search(String word) {
            Trie node = searchPrefix(word);
            return node != null && node.isEnd;
        }

        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }

        private Trie searchPrefix(String prefix) {
            Trie node = this;
            for (Character c : prefix.toCharArray())
                if ((node = node.children[c - 'a']) == null)
                    return null;
            return node;
        }
    }
}















/**
// 方法1：
class Trie {
    private Trie[] children;
    private boolean isEnd;

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = ch - 'a';
            if (node.children[index] == null)
                node.children[index] = new Trie();
            node = node.children[index];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    private Trie searchPrefix(String prefix) {
        Trie node = this;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            if (node.children[index] == null)
                return null;
            node = node.children[index];
        }
        return node;
    }
}
*/