/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.leetcode.util.LogUtil.info;
import static org.junit.Assert.assertEquals;

/**

    126. 单词接龙 II
        按字典 wordList 完成从单词 beginWord 到单词 endWord 转化，一个表示此过程的 转换序列 是形式上像 beginWord -> s1 -> s2 -> ... -> sk 这样的单词序列，并满足：
        每对相邻的单词之间仅有单个字母不同。
        转换过程中的每个单词 si（1 <= i <= k）必须是字典 wordList 中的单词。注意，beginWord 不必是字典 wordList 中的单词。
        sk == endWord
        给你两个单词 beginWord 和 endWord ，以及一个字典 wordList 。请你找出并返回所有从 beginWord 到 endWord 的 最短转换序列 ，如果不存在这样的转换序列，返回一个空列表。每个序列都应该以单词列表 [beginWord, s1, s2, ..., sk] 的形式返回。
    示例 1：
        输入：beginWord = "hit", endWord = "cog",
            wordList = ["hot","dot","dog","lot","log","cog"]
        输出：[["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
        解释：存在 2 种最短的转换序列：
        "hit" -> "hot" -> "dot" -> "dog" -> "cog"
        "hit" -> "hot" -> "lot" -> "log" -> "cog"
    示例 2：
        输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
        输出：[]
        解释：endWord "cog" 不在字典 wordList 中，所以不存在符合要求的转换序列。
*/
@SuppressWarnings("all")
public class NO126_H_WordLadderII {

    @Test
    public void test() {
        // assertEquals(//[[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]
        info(findLadders("hit", "cog",
                Arrays.asList("hot","dot","dog","lot","log","cog")));
    }

    int min = Integer.MAX_VALUE;
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> ans = new ArrayList<>();
        List<String> temp = new ArrayList<>();
        //temp 用来保存当前的路径
        temp.add(beginWord);
        findLaddersHelper(beginWord, endWord, wordList, temp, ans);
        return ans;
    }

    private void findLaddersHelper(String beginWord, String endWord, List<String> wordList,
                                   List<String> temp, List<List<String>> ans) {
        if (beginWord.equals(endWord)) {
            if (min > temp.size()) {
                ans.clear();
                min = temp.size();
                ans.add(new ArrayList<>(temp));
            } else if (min == temp.size())
                ans.add(new ArrayList<>(temp));
            return;
        }
        //当前的长度到达了 min，还是没有到达结束单词就提前结束
        if (temp.size() >= min)
            return;

        //遍历当前所有的单词
        for (int i = 0; i < wordList.size(); i++) {
            String curWord = wordList.get(i);
            //路径中已经含有当前单词，如果再把当前单词加到路径，那肯定会使得路径更长，所以跳过
            if (temp.contains(curWord))
                continue;

            //符合只有一个单词不同，就进入递归
            if (oneChanged(beginWord, curWord)) {
                temp.add(curWord);
                findLaddersHelper(curWord, endWord, wordList, temp, ans);
                temp.remove(temp.size() - 1);
            }
        }
    }

    private boolean oneChanged(String beginWord, String curWord) {
        int count = 0;
        for (int i = 0; i < beginWord.length(); i++) {
            if (beginWord.charAt(i) != curWord.charAt(i))
                count++;

            if (count == 2)
                return false;
        }
        return count == 1;
    }

}
