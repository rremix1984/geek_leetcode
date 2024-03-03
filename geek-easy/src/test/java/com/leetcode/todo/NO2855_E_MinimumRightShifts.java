package com.leetcode.todo;

import org.junit.Test;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    [ARRAY]
    （简单）
    NO.2855 使数组成为递增数组的最少右移次数
    给你一个长度为 n 下标从 0 开始的数组 nums ，数组中的元素为 互不相同 的正整数。
    请你返回让 nums 成为递增数组的 最少右移 次数，如果无法得到递增数组，返回 -1 。
    一次 右移 指的是同时对所有下标进行操作，将下标为 i 的元素移动到下标 (i + 1) % n 处。
    示例 1：
        输入：nums = [3,4,5,1,2]
        输出：2
        解释：
        第一次右移后，nums = [2,3,4,5,1] 。
        第二次右移后，nums = [1,2,3,4,5] 。
        现在 nums 是递增数组了，所以答案为 2 。
    示例 2：
        输入：nums = [1,3,5]
        输出：0
        解释：nums 已经是递增数组了，所以答案为 0 。
    示例 3：
        输入：nums = [2,1,4]
        输出：-1
        解释：无法将数组变为递增数组。
    提示：
        1 <= nums.length <= 100
        1 <= nums[i] <= 100
        nums 中的整数互不相同。
    Related Topics:数组
*/
public class NO2855_E_MinimumRightShifts {

    @Test
    public void test() {
        assert 2 == minimumRightShifts(getArray(3, 4, 5, 1, 2));
        assert 0 == minimumRightShifts(getArray(1, 3, 5));
        assert -1 == minimumRightShifts(getArray(2, 1, 4));
    }

    public int minimumRightShifts(List<Integer> nums) {
        int size = nums.size();
        // 一次遍历，找到最小值对应索引
        int minValIndex = 0;
        int preVal = nums.get(0);
        // 条件1：列表最多只有两段递增
        boolean changeFlag = false;
        for (int i = 1; i < size; i++) {
            int val = nums.get(i);
            if (val > preVal) {
                // do not thing
            } else {
                if (changeFlag) {
                    return -1;
                }
                minValIndex = i;
                changeFlag = true;
            }
            preVal = val;
        }

        if (minValIndex == 0) {
            return 0;
        }
        // 条件2：头>尾值，才能相连后递增
        if (nums.get(0) < nums.get(size - 1)) {
            return -1;
        }
        // 通过求差来求操作次数
        return size - minValIndex;
    }

}
