/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import com.leetcode.util.MathUtils;
import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArray;

/**
    (简单)
    1018. 可被 5 整除的二进制前缀
        给定一个二进制数组 nums ( 索引从0开始 )。
        我们将xi 定义为其二进制表示形式为子数组 nums[0..i] (从最高有效位到最低有效位)。
        例如，如果 nums =[1, 0, 1] ，那么 x0 = 1, x1 = 2, 和 x2 = 5。
        返回布尔值列表 answer，只有当 xi 可以被 5 整除时，答案 answer[i] 为 true，否则为 false。
    示例 1：
        输入：nums = [0, 1, 1]
        输出：[true, false, false]
        解释：
        输入数字为 0, 01, 011；也就是十进制中的 0, 1, 3 。只有第一个数可以被 5 整除，因此 answer[0] 为 true 。
    示例 2：
        输入：nums = [1, 1, 1]
        输出：[false, false, false]
*/
public class NO1018_E_PrefixesDivBy5_x2 {

    @Test
    public void test() {
        assert getArray(true, false, false).equals(
                prefixesDivBy5(new int[]{0, 1, 1}));
        assert getArray(false, false, false).equals(
                prefixesDivBy5(new int[]{1, 1, 1}));
    }

    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> ret = new ArrayList<>();
        int prefix = 0;
        for (int num : nums) {
            prefix = (prefix * 2 + num) % 5;
            ret.add(prefix == 0);
        }
        return ret;
    }

}















/**
public List<Boolean> prefixesDivBy5(int[] nums) {
    List<Boolean> ret = new ArrayList<>();
    int prefix = 0;
    for (int num : nums) {
        prefix = (prefix * 2 + num) % 5;
        ret.add(prefix == 0);
    }
    return ret;
}
*/
