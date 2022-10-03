/**
 * copyright 2022/1/19
 */
package com.leetcode;

import org.junit.Test;

/**
    (简单)
    2399. 检查相同字母间的距离
        给你一个下标从 0 开始的字符串 s ，该字符串仅由小写英文字母组成，s 中的每个字母都 恰好 出现 两次 。另给你一个下标从 0 开始、长度为 26 的的整数数组 distance 。
        字母表中的每个字母按从 0 到 25 依次编号（即，'a' -> 0, 'b' -> 1, 'c' -> 2, ... , 'z' -> 25）。
        在一个 匀整 字符串中，第 i 个字母的两次出现之间的字母数量是 distance[i] 。如果第 i 个字母没有在 s 中出现，那么 distance[i] 可以 忽略 。
        如果 s 是一个 匀整 字符串，返回 true ；否则，返回 false 。
    示例 1：
        输入：s = "abaccb",  distance = {1, 3, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        输出：true
        解释：- 'a' 在下标 0 和下标 2 处出现，所以满足 distance[0] = 1 。
             - 'b' 在下标 1 和下标 5 处出现，所以满足 distance[1] = 3 。
             - 'c' 在下标 3 和下标 4 处出现，所以满足 distance[2] = 0 。
             注意 distance[3] = 5 ，但是由于 'd' 没有在 s 中出现，可以忽略。
             因为 s 是一个匀整字符串，返回 true 。
    示例 2：
        输入：s = "aa",  distance = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        输出：false
        解释：- 'a' 在下标 0 和 1 处出现，所以两次出现之间的字母数量为 0 。
             但是 distance[0] = 1 ，s 不是一个匀整字符串。
*/
public class NO2399_E_CheckDistances {

    @Test
    public void test() {
        assert checkDistances("abaccb",
            new int[]{1, 3, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        assert !checkDistances("aa",
            new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }

    public boolean checkDistances(String s, int[] distance) {
        //数组用于判断字母是否在之前出现过
        int[] tf = new int[26];

        for (int i = 0; i < s.length(); i++) {
            if(tf[s.charAt(i) - 'a'] == 0) {
                // s 第一次遇到此字母，distance[s.charAt(i) - 'a'] 等于其第二次出现的位置下标i的i - 1；
                distance[s.charAt(i) - 'a'] = distance[s.charAt(i) - 'a'] + i;
                tf[s.charAt(i) - 'a'] = 1;
            } else {
                // s 中第二次遇到此字母，若distance[s.charAt(i) - 'a'] 符合题目条件 将会等于0
                distance[s.charAt(i) - 'a'] = distance[s.charAt(i) - 'a'] - (i - 1);
                //若最初提供的distance[s.charAt(i) - 'a']正确，此时 distance[s.charAt(i) - 'a'] 等于 0
                //由于在s中未出现过的字母，可以忽略，故只需要判断出现过的字母的distance是否符合条件
                if (distance[s.charAt(i) - 'a'] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

}