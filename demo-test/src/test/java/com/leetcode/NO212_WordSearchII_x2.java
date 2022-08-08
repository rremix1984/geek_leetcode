package com.leetcode;

import org.junit.Test;

import static com.leetcode.util.BoardUtil.*;
import static com.leetcode.util.LogUtil.info;

import java.util.*;

/**
    212. 单词搜索 II
        给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words，
        返回所有二维网格上的单词 。单词必须按照字母顺序，通过 相邻的单元格内
        的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个
        单元格内的字母在一个单词中不允许被重复使用。
    示例 1：
        输入：board = {{'o','a','a','n'},
                      {'e','t','a','e'},
                      {'i','h','k','r'},
                      {'i','f','l','v'}},
              words = {"oath","pea","eat","rain"}
        输出：["eat","oath"]
    示例 2：
        输入：board = {{'a','b'},
                      {'c','d'}},
             words = {"abcb"}
        输出：[]
*/
public class NO212_WordSearchII_x2 {

    int[][] dirs = {{ 1, 0},
                    {-1, 0},
                    { 0, 1},
                    { 0,-1}};

    @Test
    public void test() {
        info(findWords(new char[][]{{'o','a','a','n'},
                                    {'e','t','a','e'},
                                    {'i','h','k','r'},
                                    {'i','f','l','v'}},
                        new String[]{"oath","pea","eat","rain"}));// [oath, eat]

        info(findWords(new char[][]{{'a','b'},
                                    {'c','d'}},
                        new String[]{"abcb"}));// []
    }

    public List<String> findWords(char[][] board, String[] words) {
        // 首先构建一个 Trie 树
        Trie trie = new Trie();

        // 把字符一个一个添加进去
        for (String word : words) {
            trie.insert(word);
        }

        // 定义结果集 HashSet
        Set<String> ans = new HashSet<>();

        // 遍历每一行、每一列进行深度遍历，结果写入ans
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, trie, i, j, ans);
            }
        }

        // 返回结果集
        return new ArrayList<>(ans);
    }

    public void dfs(char[][] board, Trie now, int i, int j, Set<String> ans) {
        // 如果子节点中没有这个元素，就不用遍历了
        if (!now.children.containsKey(board[i][j]))
            return;

        // 取出第i行、第j列，这个元素 ch
        char ch = board[i][j];

        // 获取 ch 对应的 Trie 树
        now = now.children.get(ch);

        // 如果 Trie 树本身不为空，就加入结果集中
        if (!"".equals(now.word))
            ans.add(now.word);

        // 先把自己设置唯一个非法字符 '#' 这样就不会在遍历到自己了
        board[i][j] = '#';

        // 遍历4个方向 { 1, 0},
        //           {-1, 0},
        //           { 0, 1},
        //           { 0,-1}
        for (int[] arr : dirs) {
            int m = i + arr[0];
            int n = j + arr[1];
            // 行、列都没有到达边界时，继续进行深度遍历
            if (m >= 0 && m < board.length &&
                n >= 0 && n < board[0].length)
                dfs(board, now, m, n, ans);
        }
        // 遍历完成后，把当前元素复原
        board[i][j] = ch;
    }
}


















/**
public List<String> findWords(char[][] board, String[] words) {
    // 构造一个Trie树
    Trie trie = new Trie();

    // 把word字符一个一个添加进去
    for (String word : words)
        trie.insert(word);

    // 构建结果集
    Set<String> ans = new HashSet<>();

    // 遍历每一个数组元素，进行深度遍历
    for (int i = 0; i < board.length; ++i)
        for (int j = 0; j < board[0].length; ++j)
            dfs(board, trie, i, j, ans);

    // 返回结果集
    return new ArrayList<>(ans);
}



public void dfs(char[][] board, Trie now, int i, int j, Set<String> ans) {
    // 如果子节点中没有这个元素，就不用遍历了
    if (!now.children.containsKey(board[i][j]))
        return;

    // 取出第i行、第j列，这个元素 ch
    char ch = board[i][j];

    // 获取 ch 对应的 Trie 树
    now = now.children.get(ch);

    // 如果 Trie 树本身不为空，就加入结果集中
    if (!"".equals(now.word))
        ans.add(now.word);

    // 先把自己设置唯一个非法字符 '#' 这样就不会在遍历到自己了
    board[i][j] = '#';

    // 遍历4个方向 { 1, 0},
    //           {-1, 0},
    //           { 0, 1},
    //           { 0,-1}
    for (int[] dir : dirs) {
        int m = i + dir[0];// 横方向 m
        int n = j + dir[1];// 竖方向 n
        // 行、列都没有到达边界时，继续进行深度遍历
        if (m >= 0 && m < board.length
                && n >= 0 && n < board[0].length)
            dfs(board, now, m, n, ans);
    }

    // 遍历完成后，把当前元素复原
    board[i][j] = ch;
}


static class Trie {
    String word;
    Map<Character, Trie> children;

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
*/