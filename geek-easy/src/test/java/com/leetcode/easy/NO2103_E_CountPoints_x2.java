/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [STRING]
    (简单)
    2103. 环和杆
        总计有 n 个环，环的颜色可以是红、绿、蓝中的一种。这些环分布穿在 10 根编号为 0 到 9 的杆上。
        给你一个长度为 2n 的字符串 rings ，表示这 n 个环在杆上的分布。rings 中每两个字符形成一个 颜色位置对 ，用于描述每个环：
            第 i 对中的 第一个 字符表示第 i 个环的 颜色（'R'、'G'、'B'）。
            第 i 对中的 第二个 字符表示第 i 个环的 位置，也就是位于哪根杆上（'0' 到 '9'）。
        例如，"R3G2B1" 表示：共有 n == 3 个环，红色的环在编号为 3 的杆上，绿色的环在编号为 2 的杆上，蓝色的环在编号为 1 的杆上。
        找出所有集齐 全部三种颜色 环的杆，并返回这种杆的数量。
    示例 1：
        输入：rings = "B0B6G0R6R0R6G9"
        输出：1
        解释：
            - 编号 0 的杆上有 3 个环，集齐全部颜色：红、绿、蓝。
            - 编号 6 的杆上有 3 个环，但只有红、蓝两种颜色。
            - 编号 9 的杆上只有 1 个绿色环。
        因此，集齐全部三种颜色环的杆的数目为 1 。
    示例 2：
        输入：rings = "B0R0G0R9R0B0G0"
        输出：1
        解释：
            - 编号 0 的杆上有 6 个环，集齐全部颜色：红、绿、蓝。
            - 编号 9 的杆上只有 1 个红色环。
            因此，集齐全部三种颜色环的杆的数目为 1 。
    示例 3：
        输入：rings = "G4"
        输出：0
        解释：只给了一个环，因此，不存在集齐全部三种颜色环的杆。
*/
public class NO2103_E_CountPoints_x2 {

    @Test
    public void test() {
        assert 1 == countPoints("B0B6G0R6R0R6G9");
        assert 1 == countPoints("B0R0G0R9R0B0G0");
        assert 0 == countPoints("G4");
    }

    public int countPoints(String rings) {
        // 统计集齐三色环的杆的数量
        int res = 0;
        return res;
    }

}




















/**
// 方法1：
public int countPoints(String rings) {
    int count = 0;
    char[] chars = rings.toCharArray();
    int[] num = new int[10];
    for (int i = 0; i < chars.length; i += 2)
        num[chars[i + 1] - '0'] |= chars[i] == 'R' ? 1 : chars[i] == 'G' ? 2 : 4;

    for (int i : num)
        if (i == 7)
            count++;

    return count;
}

// 方法2：
public int countPoints(String rings) {
    // 状态数组
    int[] status = new int[10];
    // 遍历颜色位置对维护状态数组，每次进2位
    for (int i = 0; i < rings.length(); i += 2) {
        // 颜色
        char color = rings.charAt(i);
        // 数量
        int num = rings.charAt(i + 1) - '0';
        //num = rings.charAt(i + 1) - '0';
        switch(color) {
            case 'R':
                status[num] |= 1;// 0 | 1 （001）
                break;
            case 'G':
                status[num] |= 2;// 0 | 2（010）
                break;
            case 'B':
                status[num] |= 4;// 0 | 4 （100）
                break;
        }
    }

    // 统计集齐三色环的杆的数量
    int res = 0;
    // 如果每一位都是1（111）代表三个灯全亮
    for (int s : status)
        if (s == 7)
            res++;
    return res;
}
*/