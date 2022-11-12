/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;
import static java.lang.Math.min;
import static org.junit.Assert.assertArrayEquals;

/**
    (简单)
    1103. 分糖果 II
        排排坐，分糖果。
        我们买了一些糖果candies，打算把它们分给排好队的n = num_people个小朋友。
        给第一个小朋友1颗糖果，第二个小朋友2颗，依此类推，直到给最后一个小朋友n颗糖果。
        然后，我们再回到队伍的起点，给第一个小朋友n+1颗糖果，第二个小朋友n+2颗，依此类推，
        直到给最后一个小朋友2 * n颗糖果。
        重复上述过程（每次都比上一次多给出一颗糖果，当到达队伍终点后再次从队伍起点开始），
        直到我们分完所有的糖果。
        注意，就算我们手中的剩下糖果数不够（不比前一次发出的糖果多），这些糖果也会全部发给当前的小朋友。
        返回一个长度为 num_people、元素之和为 candies 的数组，
        以表示糖果的最终分发情况（即 ans[i] 表示第 i 个小朋友分到的糖果数）。
    示例 1：
        输入：candies = 7, num_people = 4
        输出：{1, 2, 3, 1}
        解释：第一次，ans[0] += 1，数组变为 [1, 0, 0, 0]。
             第二次，ans[1] += 2，数组变为 [1, 2, 0, 0]。
             第三次，ans[2] += 3，数组变为 [1, 2, 3, 0]。
             第四次，ans[3] += 1（因为此时只剩下 1 颗糖果），最终数组变为 [1, 2, 3, 1]。
    示例 2：
        输入：candies = 10, num_people = 3
        输出：{5, 2, 3}
        解释：第一次，ans[0] += 1，数组变为 [1, 0, 0]。
             第二次，ans[1] += 2，数组变为 [1, 2, 0]。
             第三次，ans[2] += 3，数组变为 [1, 2, 3]。
             第四次，ans[0] += 4，最终数组变为 [5, 2, 3]。
    思路
        最直观的方法是不断地遍历数组，如果还有糖就一直分，直到没有糖为止。
*/
public class NO1103_E_DistributeCandies {

    @Test
    public void test() {
        assertArrayEquals(new int[]{1, 2, 3, 1},
                distributeCandies(7, 4));
        assertArrayEquals(new int[]{5, 2, 3},
                distributeCandies(10, 3));
    }

    public int[] distributeCandies(int candies, int num_people) {
        int[] ans = new int[num_people];
        int i = 0;
        while (candies != 0) {
            ans[i % num_people] += min(candies, i + 1);
            candies -= min(candies, i + 1);
            i++;
        }
        return ans;
    }

}















/**
// 方法2：
public int[] distributeCandies(int candies, int num_people) {
    int n = num_people;
    // how many people received complete gifts
    int p = (int) (Math.sqrt(2 * candies + 0.25) - 0.5);
    int remaining = (int)(candies - (p + 1) * p * 0.5);
    int rows = p / n, cols = p % n;

    int[] d = new int[n];
    for (int i = 0; i < n; ++i) {
        // complete rows
        d[i] = (i + 1) * rows + (int)(rows * (rows - 1) * 0.5) * n;
        // cols in the last row
        if (i < cols) {
            d[i] += i + 1 + rows * n;
    }
    // remaining candies
    d[cols] += remaining;
    return d;
}


// 方法1：
public int[] distributeCandies(int candies, int num_people) {
    int[] ans = new int[num_people];
    int i = 0;
    while (candies != 0) {
        ans[i % num_people] += min(candies, i + 1);
        candies -= min(candies, i + 1);
        i++;
    }
    return ans;
}
*/
