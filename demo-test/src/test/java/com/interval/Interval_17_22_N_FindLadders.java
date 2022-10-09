/**
 * copyright 2022/1/19
 */
package com.interval;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.leetcode.util.MathUtils.getArray;

/**
    (中等)
    面试题 17.22. 单词转换
        给定字典中的两个词，长度相等。写一个方法，把一个词转换成另一个词， 但是一次只能改变一个字符。每一步得到的新词都必须能在字典中找到。
        编写一个程序，返回一个可能的转换序列。如有多个可能的转换序列，你可以返回任何一个。
    示例 1:
        输入: beginWord = "hit",
             endWord = "cog",
             wordList = ["hot","dot","dog","lot","log","cog"]
        输出: ["hit","hot","dot","lot","log","cog"]
    示例 2:
        输入: beginWord = "hit"
             endWord = "cog"
             wordList = ["hot","dot","dog","lot","log"]
        输出: []
        解释: endWord "cog" 不在字典中，所以不存在符合要求的转换序列。
*/
public class Interval_17_22_N_FindLadders {

    @Test
    public void test() {
        assert getArray("hit","hot","dot","lot","log","cog").equals(
                findLadders("hit", "cog", getArray("hot","dot","dog","lot","log","cog")));
        assert getArray().equals(
                findLadders("hit", "cog", getArray("hot","dot","dog","lot","log")));
    }

    public List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
        //把wordlist添加到set中，当字典用
        Set<String> set = new HashSet<>(wordList);

        //结果集
        List<String> list = new ArrayList<>();

        //访问过的单词字典
        HashSet<String> visited = new HashSet<>();

        //先把源字符串添加到结果集和visited中
        list.add(beginWord);
        visited.add(beginWord);

        //开始dfs
        if (dfs(beginWord.toCharArray(), endWord, set, visited, list))
            return list;

        return new ArrayList<>();
    }

    public boolean dfs(char[] word, String endWord, Set<String> set, Set<String> visited, List<String> list){
        //如果是已经是目标字符串了
        if (endWord.equals(new String(word)))
            return true;

        //对当前字符串的每个字符进行逐个替换
        for (int i = 0; i < word.length; i++) {
            //保存替换前的值
            char c = word[i];
            for (char j = 'a'; j <= 'z'; j++) {
                word[i] = j;
                String s = new String(word);
                if (set.contains(s) && !visited.contains(s)) {
                    visited.add(s);
                    list.add(s);
                    if (dfs(word, endWord, set, visited, list))
                        return true;

                    //说明这条路走不通
                    list.remove(list.size()-1);
                }
            }
            //复原
            word[i] = c;
        }
        return false;
    }

}