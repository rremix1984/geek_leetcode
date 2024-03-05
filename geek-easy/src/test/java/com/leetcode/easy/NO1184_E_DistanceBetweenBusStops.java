/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    [ARRAY]
    (简单)
    1184. 公交站间的距离
        环形公交路线上有 n 个站，按次序从 0 到 n - 1 进行编号。我们已知每一对相邻公交站之间的距离，
        distance[i] 表示编号为 i 的车站和编号为 (i + 1) % n 的车站之间的距离。
        环线上的公交车都可以按顺时针和逆时针的方向行驶。
        返回乘客从出发点 start 到目的地 destination 之间的最短距离。
    示例 1：
        输入：distance = {1, 2, 3, 4},  start = 0,  destination = 1
        输出：1
        解释：公交站 0 和 1 之间的距离是 1 或 9，最小值是 1。
    示例 2：
        输入：distance = {1, 2, 3, 4},  start = 0,  destination = 2
        输出：3
        解释：公交站 0 和 2 之间的距离是 3 或 7，最小值是 3。
    示例 3：
        输入：distance = {1, 2, 3, 4},  start = 0,  destination = 3
        输出：4
        解释：公交站 0 和 3 之间的距离是 6 或 4，最小值是 4。

 方法一：一次遍历
    记数组 distance 的长度为 n。假设 start ≤ destination，那么我们可以：
    1）顺时针走：从 start 到 destination，距离为
            destination − 1
             ∑ distance[i]；
            i = start
    2）逆时针走：从 start 到 0，再从 0 到 destination，距离为
            start − 1              n − 1
             ∑ distance[i]    +     ∑ distance[i]
            i = 0                  i = destination
    答案为这两个距离（顺时针、逆时针）的最小值。
*/
public class NO1184_E_DistanceBetweenBusStops {

    @Test
    public void test() {
        assert 1 == distanceBetweenBusStops(new int[]{1, 2, 3, 4},0,1);
        assert 3 == distanceBetweenBusStops(new int[]{1, 2, 3, 4},0,2);
        assert 4 == distanceBetweenBusStops(new int[]{1, 2, 3, 4},0,3);
        assert 17 == distanceBetweenBusStops(new int[]{7, 10, 1, 12, 11, 14, 5, 0}, 7, 2);
    }

    public int distanceBetweenBusStops(int[] distance, int start, int dest) {
        return -1;
    }

}
















/**
public int distanceBetweenBusStops(int[] distance, int start, int dest) {
    if (start > dest) {
        int temp = start;
        start = dest;
        dest = temp;
    }

    int sum1 = 0;
    int sum2 = 0;
    for (int i = 0; i < distance.length; i++)
        if (i >= start && i < dest)
            sum1 += distance[i];
        else
            sum2 += distance[i];

    return min(sum1, sum2);
}
*/
