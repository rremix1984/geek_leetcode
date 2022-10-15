/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
    (简单)
    2085. 统计出现过一次的公共字符串
        给你两个字符串数组 words1 和 words2 ，请你返回在两个字符串数组中 都恰好出现一次 的字符串的数目。
    示例 1：
        输入：words1 = ["leetcode","is","amazing","as","is"], words2 = ["amazing","leetcode","is"]
        输出：2
        解释：- "leetcode" 在两个数组中都恰好出现一次，计入答案。
             - "amazing" 在两个数组中都恰好出现一次，计入答案。
             - "is" 在两个数组中都出现过，但在 words1 中出现了 2 次，不计入答案。
             - "as" 在 words1 中出现了一次，但是在 words2 中没有出现过，不计入答案。
             所以，有 2 个字符串在两个数组中都恰好出现了一次。
    示例 2：
        输入：words1 = ["b","bb","bbb"], words2 = ["a","aa","aaa"]
        输出：0
        解释：没有字符串在两个数组中都恰好出现一次。
    示例 3：
        输入：words1 = ["a","ab"], words2 = ["a","a","a","ab"]
        输出：1
        解释：唯一在两个数组中都出现一次的字符串是 "ab" 。
*/
public class NO2085_E_CountWords {

    @Test
    public void test() {
        assert 2 == countWords(
                new String[]{"leetcode", "is", "amazing", "as", "is"},
                new String[]{"amazing", "leetcode", "is"});
        assert 0 == countWords(
                new String[]{"b", "bb", "bbb"},
                new String[]{"a", "aa", "aaa"});
        assert 1 == countWords(
                new String[]{"a", "ab"},
                new String[]{"a", "a", "a", "ab"});
    }

    public int countWords(String[] words1, String[] words2) {
        int cnt = 0;
        Map<String, Integer> map1 = new HashMap<>();
        // 遍历words1，统计各字符串出现次数
        for (String s : words1)
            map1.put(s, map1.getOrDefault(s, 0) + 1);

        Map<String, Integer> map2 = new HashMap<>();
        // 遍历words2
        for (String s : words2)
            // 若words2的字符串在s1中只出现过一次，次数+1
            if (map1.getOrDefault(s, 0) == 1) {
                // 字符串在words2里可能出现多次，后续要减掉这部分统计数据
                map2.put(s, map2.getOrDefault(s, 0) + 1);
                cnt++;
            }

        for (Integer val : map2.values())
            if (val > 1)
                // 字符串在words2里可能出现多次，减掉这部分统计数据
                cnt -= val;

        return cnt;
    }

}