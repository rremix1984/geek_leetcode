/**
 * copyright 2022/1/19
 */
package com.leetcode.normal ;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
    (中等)
    2405. 子字符串的最优划分
        给你一个字符串s，请你将该字符串划分成一个或多个子字符串，
        并满足每个子字符串中的字符都是唯一的。也就是说，在单个子字符串中，
        字母的出现次数都不超过一次 。
        满足题目要求的情况下，返回最少需要划分多少个子字符串。
        注意，划分后，原字符串中的每个字符都应该恰好属于一个子字符串。
    示例 1：
        输入：s = "abacaba"
        输出：4
        解释：
        两种可行的划分方法分别是 ("a", "ba", "cab", "a") 和 ("ab", "a", "ca", "ba") 。
        可以证明最少需要划分 4 个子字符串。
    示例 2：
        输入：s = "ssssss"
        输出：6
        解释：
        只存在一种可行的划分方法 ("s", "s", "s", "s", "s", "s") 。
    提示：
        1 <= s.length <= 105
        s 仅由小写英文字母组成
*/
public class NO2405_N_PartitionString_x2 {

    @Test
    public void test() {
        assert 4 == partitionString("abacaba");
        assert 6 == partitionString("ssssss");
    }

    public int partitionString(String s) {
        int res = 1;
        Set<Character> set = new HashSet<>();
        for (char C : s.toCharArray()) {
            if (set.contains(C)) {
                res++;
                set.clear();
            }
            set.add(C);
        }
        return res;
    }

}














/**
// 方法1：
public int partitionString(String s) {
    int res = 1;
    Set<Character> set = new HashSet<>();
    for (char C : s.toCharArray()) {
        if (set.contains(C)) {
            res++;
            set.clear();
        }
        set.add(C);
    }
    return res;
}
*/