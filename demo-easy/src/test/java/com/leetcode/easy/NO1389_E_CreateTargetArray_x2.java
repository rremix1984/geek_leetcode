/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    1389. 按既定顺序创建目标数组
        给你两个整数数组 nums 和 index。你需要按照以下规则创建目标数组：
        目标数组 target 最初为空。
        按从左到右的顺序依次读取 nums[i] 和 index[i]，在 target 数组中的下标 index[i] 处【插入】值 nums[i] 。
        重复上一步，直到在 nums 和 index 中都没有要读取的元素。
        请你返回目标数组。
        题目保证数字插入位置总是存在。
    示例 1：
        输入：nums = {0, 1, 2, 3, 4},  index = {0, 1, 2, 2, 1}
        输出：{0, 4, 1, 3, 2}
        解释：
        nums       index     target
        0            0        {0}
        1            1        {0, 1}
        2            2        {0, 1, 2}
        3            2        {0, 1, 3, 2}
        4            1        {0, 4, 1, 3, 2}
    示例 2：
        输入：nums = {1, 2, 3, 4, 0},  index = {0, 1, 2, 3, 0}
        输出：{0, 1, 2, 3, 4}
        解释：
        nums       index     target
        1            0        {1}
        2            1        {1, 2}
        3            2        {1, 2, 3}
        4            3        {1, 2, 3, 4}
        0            0        {0, 1, 2, 3, 4}
    示例 3：
        输入：nums = {1},  index = {0}
        输出：{1}
*/
public class NO1389_E_CreateTargetArray_x2 {

    @Test
    public void test() {
        assertArrayEquals(new int[]{0, 4, 1, 3, 2},
                createTargetArray(new int[]{0, 1, 2, 3, 4}, new int[]{0, 1, 2, 2, 1}));
        assertArrayEquals(new int[]{0, 1, 2, 3, 4},
                createTargetArray(new int[]{1, 2, 3, 4, 0}, new int[]{0, 1, 2, 3, 0}));
        assertArrayEquals(new int[]{1},
                createTargetArray(new int[]{1}, new int[]{0}));
    }

    public int[] createTargetArray(int[] nums, int[] index) {
        int[] ans = new int[index.length];
        return ans;
    }

}
















/**
// 方法1：
public int[] createTargetArray(int[] nums, int[] index) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i < nums.length; i++)
        list.add(index[i], nums[i]);

    int[] ret = new int[nums.length];
    for (int i = 0; i < nums.length; i++)
        ret[i] = list.get(i);

    return ret;
}

// 方法2：
public int[] createTargetArray(int[] nums, int[] index) {
    int[] ans = new int[index.length];
    for (int i = 0; i < index.length; i++) {
        for (int j = ans.length - 1; j > index[i]; j--)
            ans[j] = ans[j - 1];

        ans[index[i]] = nums[i];
    }
    return ans;
}
*/