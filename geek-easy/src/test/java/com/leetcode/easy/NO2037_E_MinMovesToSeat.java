/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;
import java.util.Arrays;
import static java.lang.Math.abs;

/**
    [ARRAY] ||
    (简单)
    NO.2037. 使每位学生都有座位的最少移动次数
        一个房间里有n个座位和n名学生，房间用一个数轴表示。
        1）给你一个长度为n的数组seats，其中seats[i]是第i个座位的位置。
        2）同时给你一个长度为n的数组students，其中students[j]是第j位学生的位置。
        3）你可以执行以下操作任意次：
            增加或者减少第i位学生的位置，每次变化量为1
            （也就是将第i位学生从位置x移动到x+1或者x-1）
        请你返回使所有学生都有座位坐的【最少】移动次数，并确保没有两位学生的座位相同。
        请注意，初始时有可能有多个座位或者多位学生在同一位置。
    示例 1：
        输入：seats = {3, 1, 5},  students = {2, 7, 4}
        输出：4
        解释：学生移动方式如下：
             - 第一位学生从位置2移动到位置1，移动1次。
             - 第二位学生从位置7移动到位置5，移动2次。
             - 第三位学生从位置4移动到位置3，移动1次。
             总共 1 + 2 + 1 = 4 次移动。
    示例 2：
        输入：seats = {4, 1, 5, 9},  students = {1, 3, 2, 6}
        输出：7
        解释：学生移动方式如下：
             - 第一位学生不移动。
             - 第二位学生从位置3移动到位置4，移动1次。
             - 第三位学生从位置2移动到位置5，移动3次。
             - 第四位学生从位置6移动到位置9，移动3次。
             总共 0 + 1 + 3 + 3 = 7 次移动。
    示例 3：
        输入：seats = {2, 2, 6, 6},  students = {1, 3, 2, 6}
        输出：4
        解释：学生移动方式如下：
             - 第一位学生从位置1移动到位置2，移动1次。
             - 第二位学生从位置3移动到位置6，移动3次。
             - 第三位学生不移动。
             - 第四位学生不移动。
             总共 1 + 3 + 0 + 0 = 4 次移动。
    思路：
        由于座位和学生数相同，一个萝卜一个坑，将座位和学生位置排序后，
        第i个学生可以对应第i个座位。
        由于交换任意两个学生对应的座位不会产生更少的移动次数（可以画一画，证明略），
        所以上述对应关系可以产生最少移动次数，累加位置之差即为答案。
*/
public class NO2037_E_MinMovesToSeat {

    @Test
    public void test() {
        assert 4 == minMovesToSeat(
            new int[]{3, 1, 5},    new int[]{2, 7, 4});
        assert 7 == minMovesToSeat(
            new int[]{4, 1, 5, 9}, new int[]{1, 3, 2, 6});
        assert 4 == minMovesToSeat(
            new int[]{2, 2, 6, 6}, new int[]{1, 3, 2, 6});
    }

    public int minMovesToSeat(int[] seats, int[] students) {
        // 2024/2/27 NO.3 用了排序算法
        // 2024/3/30 NO.4 没思路，能看懂
        int ans = 0;
        return ans;
    }

}















/*
// 方法1：
public int minMovesToSeat(int[] seats, int[] students) {
    Arrays.sort(seats);
    Arrays.sort(students);
    int ans = 0;
    for (int i = 0; i < seats.length; i++)
        ans += abs(students[i] - seats[i]);

    return ans;
}
*/
