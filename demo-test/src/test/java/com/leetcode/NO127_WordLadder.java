/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;

/**
    （困难）
    127. 单词接龙
        字典 wordList 中从单词 beginWord 和 endWord 的 转换序列
        是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：
        每一对相邻的单词【只差一个字母】。
        对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意， beginWord
        不需要在 wordList 中。sk == endWord
        给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从
        beginWord 到 endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0 。
    示例 1：
        输入：beginWord = "hit", endWord = "cog",
                wordList = ["hot","dot","dog","lot","log","cog"]
        输出：5
        解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。
    示例 2：
        输入：beginWord = "hit", endWord = "cog",
                wordList = ["hot","dot","dog","lot","log"]
        输出：0
        解释：endWord "cog" 不在字典中，所以无法进行转换。
*/
public class NO127_WordLadder {

    @Test
    public void test() {
        info(ladderLength("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log","cog")));// 5
        info(ladderLength("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log")));// 0
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // 第 1 步：先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
        Set<String> wordSet = new HashSet<>(wordList);
        if (wordSet.size() == 0 || !wordSet.contains(endWord))
            return 0;

        // 第 2 步：已经访问过的 word 添加到 visited 哈希表里
        Set<String> visited = new HashSet<>();

        // 分别用左边和右边扩散的哈希表代替单向 BFS 里的队列，它们在双向 BFS 的过程中交替使用
        Set<String> begin = new HashSet<>();
        begin.add(beginWord);

        Set<String> end = new HashSet<>();
        end.add(endWord);

        // 第 3 步：执行双向 BFS，左右交替扩散的步数之和为所求
        // 双向BFS，每次都保证是小的一方先扩散
        int step = 1;
        while (!begin.isEmpty() && !end.isEmpty()) {
            // 优先选择小的哈希表进行扩散，考虑到的情况更少
            if (begin.size() > end.size()) {
                Set<String> temp = begin;
                begin = end;
                end = temp;
            }

            // 逻辑到这里，保证 begin 是相对较小的集合，next 在扩散完成以后，会成为新的 begin
            Set<String> next = new HashSet<>();
            for (String word : begin)
                if (changeLetter(word, end, visited, wordSet, next))
                    return step + 1;

            // 原来的 begin 废弃，从 next 开始新的双向 BFS
            begin = next;
            step++;
        }
        return 0;
    }

    /**
     * 尝试对 word 修改每一个字符，看看是不是能落在 end 中，扩展得到的新的 word 添加到 next 里
     */
    private boolean changeLetter(String word, Set<String> end,
                                             Set<String> visited,
                                             Set<String> wordSet,
                                             Set<String> next) {
        char[] charArray = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char originChar = charArray[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (originChar == c)
                    continue;

                charArray[i] = c;
                String nextStr = String.valueOf(charArray);
                if (wordSet.contains(nextStr)) {
                    if (end.contains(nextStr))
                        return true;

                    if (!visited.contains(nextStr)) {
                        next.add(nextStr);
                        visited.add(nextStr);
                    }
                }
            }
            // 恢复，下次再用
            charArray[i] = originChar;
        }
        return false;
    }

}


















/*
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    // 第 1 步：先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
    Set<String> wordSet = new HashSet<>(wordList);
    if (wordSet.size() == 0 || !wordSet.contains(endWord))
        return 0;

    // 第 2 步：已经访问过的 word 添加到 visited 哈希表里
    Set<String> visited = new HashSet<>();

    // 分别用左边和右边扩散的哈希表代替单向 BFS 里的队列，它们在双向 BFS 的过程中交替使用
    Set<String> begin = new HashSet<>();
    begin.add(beginWord);

    Set<String> end = new HashSet<>();
    end.add(endWord);

    // 第 3 步：执行双向 BFS，左右交替扩散的步数之和为所求
    // 双向BFS，每次都保证是小的一方先扩散
    int step = 1;
    while (!begin.isEmpty() && !end.isEmpty()) {
        // 优先选择小的哈希表进行扩散，考虑到的情况更少
        if (begin.size() > end.size()) {
            Set<String> temp = begin;
            begin = end;
            end = temp;
        }

        // 逻辑到这里，保证 begin 是相对较小的集合，next 在扩散完成以后，会成为新的 begin
        Set<String> next = new HashSet<>();
        for (String word : begin)
            if (changeLetter(word, end, visited, wordSet, next))
                return step + 1;

        // 原来的 begin 废弃，从 next 开始新的双向 BFS
        begin = next;
        step++;
    }
    return 0;
}

// 尝试对 word 修改每一个字符，看看是不是能落在 end 中，扩展得到的新的 word 添加到 next 里
private boolean changeLetter(String word, Set<String> end,
                             Set<String> visited,
                             Set<String> wordSet,
                             Set<String> next) {
    char[] charArray = word.toCharArray();
    for (int i = 0; i < word.length(); i++) {
        char originChar = charArray[i];
        for (char c = 'a'; c <= 'z'; c++) {
            if (originChar == c)
                continue;

            charArray[i] = c;
            String nextStr = String.valueOf(charArray);
            if (wordSet.contains(nextStr)) {
                if (end.contains(nextStr))
                    return true;

                if (!visited.contains(nextStr)) {
                    next.add(nextStr);
                    visited.add(nextStr);
                }
            }
        }
        // 恢复，下次再用
        charArray[i] = originChar;
    }
    return false;
}
*/