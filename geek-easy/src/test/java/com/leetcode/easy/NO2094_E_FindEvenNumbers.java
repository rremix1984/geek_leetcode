/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.leetcode.util.SystemUtil.arraysAllMatch;
import static com.leetcode.util.SystemUtil.printArr;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY] |
    (简单)
    2094. 找出3位偶数
        给你一个整数数组digits，其中每个元素是一个数字（0-9）。数组中可能存在重复元素。
        你需要找出所有满足下述条件且互不相同的整数：
          1）该整数由digits中的三个元素按任意顺序依次连接组成。
          2）该整数不含前导零
          3）该整数是一个偶数
        例如，给定的 digits 是 [1, 2, 3] ，整数 132 和 312 满足上面列出的全部条件。
        将找出的所有互不相同的整数按 递增顺序 排列，并以数组形式返回。
    示例 1：
        输入：digits = {2, 1, 3, 0}
        输出：{102, 120, 130, 132, 210, 230, 302, 310, 312, 320}
        解释：
        所有满足题目条件的整数都在输出数组中列出。
        注意，答案数组中不含有 奇数 或带 前导零 的整数。
    示例 2：
        输入：digits = {2, 2, 8, 8, 2}
        输出：{222, 228, 282, 288, 822, 828, 882}
        解释：
        同样的数字（0 - 9）在构造整数时可以重复多次，重复次数最多与其在 digits 中出现的次数一样。
        在这个例子中，数字 8 在构造 288、828 和 882 时都重复了两次。
    示例 3：
        输入：digits = {3, 7, 5}
        输出：{}
        解释：
        使用给定的 digits 无法构造偶数。
*/
public class NO2094_E_FindEvenNumbers {

    @Test
    public void test() {
        assert arraysAllMatch(new int[]{102, 120, 130, 132, 210, 230, 302, 310, 312, 320},
                findEvenNumbers(new int[]{2, 1, 3, 0}));
        assert arraysAllMatch(new int[]{222, 228, 282, 288, 822, 828, 882},
                findEvenNumbers(new int[]{2, 2, 8, 8, 2}));
        assert arraysAllMatch(new int[]{},
                findEvenNumbers(new int[]{3, 7, 5}));
    }

    public int[] findEvenNumbers(int[] digits) {
        // 2024/3/10 NO.1
        return null;
    }

}
















/*
// 方法1：这个能看懂
public int[] findEvenNumbers(int[] digits) {
    int length = digits.length;
    Set<Integer> set = new HashSet<>();
    int index = 0;
    for (int i = 0;i < length;i++) {
        for (int j = 0;j < length;j++) {
            for (int k = 0;k < length;k++) {
                if (i != j && i != k && k != j && digits[i] != 0) {
                    int tempAns = digits[i] * 100 + digits[j] * 10 + digits[k];
                    if(tempAns % 2 == 0){
                        set.add(tempAns);
                    }
                }
            }
        }
    }
    int[] ans = new int[set.size()];
    for (Integer temp : set)
        ans[index++] = temp;

    Arrays.sort(ans);
    return ans;
}


// 方法2：
public int[] findEvenNumbers(int[] digits) {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < digits.length && digits[i] != 0; i++)
        for (int j = 0; j < digits.length && i != j; j++)
            for (int k = 0; k < digits.length; k++) {
                if (k == i || k == j || digits[k] % 2 != 0)
                    continue;

                int num = digits[i] * 100 + digits[j] * 10 + digits[k];
                set.add(num);
            }

    int[] res = new int[set.size()];
    int index = 0;
    for (int num : set)
        res[index++] = num;

    Arrays.sort(res);
    return res;
}

// 方法2：同1，最后优化了一点
public int[] findEvenNumbers(int[] digits) {
    // 2024/3/10 NO.1
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < digits.length; i++)
        for (int j = 0; j < digits.length; j++)
            for (int k = 0; k < digits.length; k++)
                if (i != j && i != k && k != j && digits[i] != 0) {
                    // 累计和
                    int tempAns = digits[i] * 100 + digits[j] * 10 + digits[k];

                    // 是偶数就行
                    if (digits[k] % 2 == 0)
                        set.add(tempAns);
                }

    return set.stream().mapToInt(a->a).toArray();
}
*/