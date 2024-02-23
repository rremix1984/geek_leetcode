/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    [STRING]
    [ARRAY]
    (简单)
    2418. 按身高排序
        给你一个字符串数组 names ，和一个由 互不相同 的正整数组成的数组 heights 。两个数组的长度均为 n 。
        对于每个下标 i，names[i] 和 heights[i] 表示第 i 个人的名字和身高。
        请按身高 降序 顺序返回对应的名字数组 names 。
    示例 1：
        输入：names = {"Mary", "John", "Emma"},  heights = {180, 165, 170}
        输出：{"Mary", "Emma", "John"}
        解释：Mary 最高，接着是 Emma 和 John 。
    示例 2：
        输入：names = {"Alice", "Bob", "Bob"},  heights = {155, 185, 150}
        输出：{"Bob", "Alice", "Bob"}
        解释：第一个 Bob 最高，然后是 Alice 和第二个 Bob 。
*/
public class NO2418_E_SortPeople_x2 {
    
    @Test
    public void test() {
        assertArrayEquals(new String[]{"Mary", "Emma", "John"},
            sortPeople(new String[]{"Mary", "John", "Emma"}, new int[]{180, 165, 170}));
        assertArrayEquals(new String[]{"Bob", "Alice", "Bob"},
            sortPeople(new String[]{"Alice", "Bob", "Bob"}, new int[]{155, 185, 150}));
    }

    public String[] sortPeople(String[] names, int[] heights) {
        return names;
    }

}
















/**
// 方法1：冒泡排序
public String[] sortPeople(String[] names, int[] heights) {
    for (int i = 0; i < heights.length - 1; i++)
        for (int j = 0; j < heights.length - i - 1; j++)
            if (heights[j] < heights[j + 1]) {
                swap(heights, j + 1, j);
                swap(names, j + 1, j);
            }
    return names;
}
*/