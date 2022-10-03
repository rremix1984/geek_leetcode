/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.*;

/**
    (简单)
    2423. 删除字符使频率相同
        给你一个下标从 0 开始的字符串 word ，字符串只包含小写英文字母。你需要选择 一个 下标并 删除 下标处的字符，使得 word 中剩余每个字母出现 频率 相同。
        如果删除一个字母后，word 中剩余所有字母的出现频率都相同，那么返回 true ，否则返回 false 。
        注意：字母 x 的 频率 是这个字母在字符串中出现的次数。
             你必须恰好删除一个字母，不能一个字母都不删除。
    示例 1：
        输入：word = "abcc"
        输出：true
        解释：选择下标 3 并删除该字母，word 变成 "abc" 且每个字母出现频率都为 1 。
    示例 2：
        输入：word = "aazz"
        输出：false
        解释：我们必须删除一个字母，所以要么 "a" 的频率变为 1 且 "z" 的频率为 2 ，要么两个字母频率反过来。所以不可能让剩余所有字母出现频率相同。

*/
public class NO2423_E_EqualFrequency {

    @Test
    public void name() {
        assert equalFrequency("abcc");
        assert !equalFrequency("aazz");
    }

    public boolean equalFrequency(String word) {
        // 每个字母出现次数
        Map<Character, Integer> countMap = new HashMap<>();

        // 每种次数，对应有几个字母
        TreeMap<Integer, Integer> charMap = new TreeMap<>();

        for (char c : word.toCharArray())
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);

        for (Character k : countMap.keySet())
            charMap.put(countMap.get(k), charMap.getOrDefault(countMap.get(k), 0) + 1);

        if (charMap.size() > 2)
            return false;

        if (charMap.size() == 1)
            return countMap.size() == 1 || charMap.containsKey(1);

        return (charMap.containsKey(1) && charMap.get(1) == 1)
            || (charMap.lastKey() - charMap.firstKey() == 1 && charMap.lastEntry().getValue() == 1);
    }

}