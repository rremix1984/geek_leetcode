/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.SwapUtil.swap;

/**
    (简单)
    2231. 按奇偶性交换后的最大数字
        给你一个正整数 num 。你可以交换 num 中 奇偶性 相同的任意两位数字（即，都是奇数或者偶数）。
        返回交换 任意 次之后 num 的 最大 可能值。
    示例 1：
        输入：num = 1234
        输出：3412
        解释：交换数字 3 和数字 1 ，结果得到 3214 。
             交换数字 2 和数字 4 ，结果得到 3412 。
             注意，可能存在其他交换序列，但是可以证明 3412 是最大可能值。
             注意，不能交换数字 4 和数字 1 ，因为它们奇偶性不同。
    示例 2：
        输入：num = 65875
        输出：87655
        解释：交换数字 8 和数字 6 ，结果得到 85675 。
             交换数字 5 和数字 7 ，结果得到 87655 。
             注意，可能存在其他交换序列，但是可以证明 87655 是最大可能值。
*/
public class NO2231_E_LargestInteger_x2 {

    @Test
    public void test() {

    }

    public int largestInteger(int num) {
        char[] s = ("" + num).toCharArray();   // 转化为字符串
        // 进行选择排序
        for (int i = 0; i < s.length - 1; i++)
            for (int j = i + 1; j < s.length; j++)
                // 只有下标数值奇偶相同才进行判断
                if ((s[i] - s[j]) % 2 == 0 && s[i] < s[j])
                    swap(s, i, j);

        // 转化为最终的整数
        return Integer.parseInt(new String(s));
    }

}
