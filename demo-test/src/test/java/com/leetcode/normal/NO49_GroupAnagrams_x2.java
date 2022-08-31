/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

import java.util.List;

import static com.leetcode.util.LogUtil.info;

/**
    （中等）
    49. 字母异位词分组
    给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
    字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。

    示例 1:
        输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
        输出: [["bat"], ["nat", "tan"], ["ate", "eat", "tea"]]
    示例 2:
        输入: strs = [""]
        输出: [[""]]
*/
public class NO49_GroupAnagrams_x2 {

    @Test
    public void test() {
        // [
        //  ["bat"],
        //  ["nat","tan"],
        //  ["ate","eat","tea"]
        // ]
        info(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        return null;
    }
}













/**
public List<List<String>> groupAnagrams(String[] strs) {
    HashMap<String, ArrayList<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String st = String.valueOf(chars);
        map.putIfAbsent(st, new ArrayList<>());
        map.get(st).add(s);
    }
    return new ArrayList(map.values());
}
*/