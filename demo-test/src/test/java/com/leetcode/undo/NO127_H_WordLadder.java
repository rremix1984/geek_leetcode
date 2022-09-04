/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.LogUtil.info;

/**
    （困难）
    127. 单词接龙
    字典 wordList 中从单词 beginWord 和 endWord 的 转换序列
    是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：
    每一对相邻的单词只差一个字母。对于 1 <= i <= k 时，每个 si
    都在 wordList 中。注意， beginWord 不需要在 wordList 中。
    sk == endWord
    给你两个单词 beginWord 和 endWord 和一个字典 wordList ，
    返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。
    如果不存在这样的转换序列，返回 0 。

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
@SuppressWarnings("all")
public class NO127_H_WordLadder {

    @Test
    public void test() {
        // 0
        info(ladderLength("hit", "cog",
                Arrays.asList("hot", "dot", "dog", "lot", "log")));
        // 5
        info(ladderLength("hit", "cog",
                Arrays.asList("hot", "dot", "dog", "lot", "log", "cog")));
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        return 0;
    }
}
















/**
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> set = new HashSet<>(wordList);
    if (set.isEmpty() || !set.contains(endWord))
        return 0;

    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    int level = 1;

    // 层序遍历
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String currWord = queue.poll();
            if (currWord.equals(endWord))
                return level;

            for (char j = 'a'; j <= 'z'; j++) {
                for (int k = 0; k < currWord.length(); k++) {
                    String newWord = currWord.substring(0, k) + j + currWord.substring(k + 1);
                    if (set.contains(newWord)) {
                        queue.offer(newWord);
                        set.remove(newWord);
                    }
                }
            }
        }
        level++;
    }
    return 0;
}
*/