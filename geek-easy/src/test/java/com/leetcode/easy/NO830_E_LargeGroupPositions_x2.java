/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.leetcode.util.MathUtils.getArray;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
    (简单)
    830. 较大分组的位置
        在一个由小写字母构成的字符串s中，包含由一些连续的相同字符所构成的分组。
        例如，在字符串s = "abbxxxxzyy"中，就含有"a","bb","xxxx","z","yy"
        这样的一些分组。分组可以用区间[start, end]表示，其中 start 和 end
        分别表示该分组的起始和终止位置的下标。上例中的"xxxx"分组用区间表示为[3,6]。
        我们称所有包含【大于或等于3个】连续字符的分组为较大分组 。
        找到每一个较大分组的区间，按起始位置下标递增顺序排序后，返回结果。
    示例 1：
        输入：s = "abbxxxxzzy"
        输出：[[3,6]]
        解释："xxxx" 是一个起始于 3 且终止于 6 的较大分组。
    示例 2：
        输入：s = "abc"
        输出：[]
        解释："a","b" 和 "c" 均不是符合要求的较大分组。
    示例 3：
        输入：s = "abcdddeeeeaabbbcd"
        输出：[[3,5],[6,9],[12,14]]
        解释：较大分组为 "ddd", "eeee" 和 "bbb"
    示例 4：
        输入：s = "aba"
        输出：[]
    提示：
        1 <= s.length <= 1000
        s 仅含小写英文字母
*/
public class NO830_E_LargeGroupPositions_x2 {

    @Test
    public void test() {
        assert getArray(new int[][]{{3, 6}}).equals(
                largeGroupPositions("abbxxxxzzy"));
        assert emptyList().equals(
                largeGroupPositions("abc"));
        assert getArray(new int[][]{{3, 5}, {6, 9}, {12, 14}}).equals(
            largeGroupPositions("abcdddeeeeaabbbcd"));
        assert emptyList().equals(
                largeGroupPositions("aba"));
    }

    public List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> ret = new ArrayList<>();
        return ret;
    }

}















/**
// 方法1：
public List<List<Integer>> largeGroupPositions(String s) {
    List<List<Integer>> ret = new ArrayList<>();
    int num = 1;
    for (int i = 0; i < s.length(); i++)
        if (i == s.length() - 1 || s.charAt(i) != s.charAt(i + 1)) {
            if (num >= 3)
                ret.add(asList(i - num + 1, i));
            num = 1;
        } else {
            num++;
        }
    return ret;
}
*/
