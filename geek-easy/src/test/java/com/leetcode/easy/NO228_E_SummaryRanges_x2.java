/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static com.leetcode.util.MathUtils.getArray;

/**
    [LIST]
    (简单)
    228. 汇总区间
        给定一个  无重复元素 的 有序 整数数组 nums。
        返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表。
        也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，
        并且不存在属于某个范围但不属于 nums 的数字 x 。
        列表中的每个区间范围 [a,b] 应该按如下格式输出：
        "a->b" ，如果 a != b
        "a" ，如果 a == b
    示例 1：
        输入：nums = [0, 1, 2, 4, 5, 7]
        输出：["0->2", "4->5", "7"]
        解释：区间范围是：
             [0,2] --> "0->2"
             [4,5] --> "4->5"
             [7,7] --> "7"
    示例 2：
        输入：nums = [0, 2, 3, 4, 6, 8, 9]
        输出：["0", "2->4", "6", "8->9"]
        解释：区间范围是：
             [0,0] --> "0"
             [2,4] --> "2->4"
             [6,6] --> "6"
             [8,9] --> "8->9"
*/
public class NO228_E_SummaryRanges_x2 {

    @Test
    public void test() {
        assert getArray("0->2", "4->5", "7").equals(summaryRanges(new int[]{0, 1, 2, 4, 5, 7}));
        assert getArray("0", "2->4", "6", "8->9").equals(summaryRanges(new int[]{0, 2, 3, 4, 6, 8, 9}));
    }

    public List<String> summaryRanges(int[] nums) {
        List<String> res = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < nums.length; i++)
            // 当扫描到最后一个元素，或前后两个元素相差不为1时
            // 需要在数组中写数据了
            if (i == nums.length - 1 || nums[i] + 1 != nums[i + 1]) {
                StringBuilder sb = new StringBuilder("" + nums[j]);
                if (i != j)
                    sb.append("->").append(nums[i]);

                res.add(sb.toString());
                j = i + 1;
            }

        return res;
    }

}
















/**
// 方法1：
public List<String> summaryRanges(int[] nums) {
    List<String> ret = new ArrayList<>();
    int i = 0;
    while (i < nums.length) {
        int low = i;
        i++;
        while (i < nums.length && nums[i] == nums[i - 1] + 1)
            i++;

        int high = i - 1;
        StringBuilder temp = new StringBuilder("" + nums[low]);
        if (low < high) {
            temp.append("->");
            temp.append(nums[high]);
        }
        ret.add(temp.toString());
    }
    return ret;
}

// 方法2：
public List<String> summaryRanges(int[] nums) {
    List<String> res = new ArrayList<>();
    // i 初始指向第 1 个区间的起始位置
    int i = 0;
    for (int j = 0; j < nums.length; j++) {
        // j 向后遍历，直到不满足连续递增(即 nums[j] + 1 != nums[j + 1])
        // 或者 j 达到数组边界，则当前连续递增区间 [i, j] 遍历完毕，将其写入结果列表。
        if (j + 1 == nums.length || nums[j] + 1 != nums[j + 1]) {
            // 将当前区间 [i, j] 写入结果列表
            StringBuilder sb = new StringBuilder();
            sb.append(nums[i]);
            if (i != j)
                sb.append("->").append(nums[j]);

            res.add(sb.toString());
            // 将 i 指向更新为 j + 1，作为下一个区间的起始位置
            i = j + 1;
        }
    }
    return res;
}
*/