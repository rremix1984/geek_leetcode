/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    (中等)
    179. 最大数
        给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
        注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
    示例 1：
        输入：nums = [10,2]
        输出："210"
    示例 2：
        输入：nums = [3,30,34,5,9]
        输出："9534330"
*/
public class NO179_N_LargestNumber_x2 {

    @Test
    public void test() {
        assert "210".equals(largestNumber(new int[]{10, 2}));
        assert "0".equals(largestNumber(new int[]{0}));
        assert "0".equals(largestNumber(new int[]{0, 0}));
        assert "9534330".equals(largestNumber(new int[]{3, 30, 34, 5, 9}));
        assert "9876543210".equals(largestNumber(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}));
        assert "9876543210".equals(largestNumber(new int[]{0, 9, 8, 7, 6, 5, 4, 3, 2, 1}));
        assert "999999999999999998999999997".equals(largestNumber(new int[]{999999998, 999999997, 999999999}));
    }

    public String largestNumber(int[] nums) {
        return null;
    }

}



















/**
// 方法1：
public String largestNumber(int[] nums) {
    String[] ss = new String[nums.length];
    for (int i = 0; i < nums.length; i++)
        ss[i] = String.valueOf(nums[i]);

    Arrays.sort(ss,
        (a, b) -> (b + a).compareTo(a + b));

    StringBuilder sb = new StringBuilder();
    for (String s : ss)
        sb.append(s);

    int k = 0;
    // 移动前面的 0
    while (k < sb.length() - 1 && sb.charAt(k) == '0')
        k++;

    return sb.substring(k);
}
*/