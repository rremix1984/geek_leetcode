package com.leetcode.todo;

import org.junit.Test;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    （简单）
    NO.2682 找出转圈游戏输家
    n 个朋友在玩游戏。这些朋友坐成一个圈，按 顺时针方向 从 1 到 n 编号。
    准确的说，从第i个朋友的位置开始顺时针移动1步会到达第(i+1)个朋友的位置（1<=i<n），
    而从第 n 个朋友的位置开始顺时针移动1步会回到第 1 个朋友的位置。
    游戏规则如下：
      1）第 1 个朋友接球。
      2）接着，第 1 个朋友将球传给距离他顺时针方向 k 步的朋友。
      3）然后，接球的朋友应该把球传给距离他顺时针方向 2 * k 步的朋友。
      4）接着，接球的朋友应该把球传给距离他顺时针方向 3 * k 步的朋友，以此类推。
    换句话说，在第 i 轮中持有球的那位朋友需要将球传递给距离他顺时针方向 i * k 步的朋友。
    当某个朋友第 2 次接到球时，游戏结束。
    在整场游戏中没有接到过球的朋友是 输家 。
    给你参与游戏的朋友数量 n 和一个整数 k ，请按升序排列返回包含所有输家编号的数组 answer 作为答案。
    示例 1：
        输入：n = 5, k = 2
        输出：[4, 5]
        解释：以下为游戏进行情况：
            1）第 1 个朋友接球，第 1 个朋友将球传给距离他顺时针方向 2 步的玩家 —— 第 3 个朋友。
            2）第 3 个朋友将球传给距离他顺时针方向 4 步的玩家 —— 第 2 个朋友。
            3）第 2 个朋友将球传给距离他顺时针方向 6 步的玩家 —— 第 3 个朋友。
            4）第 3 个朋友接到两次球，游戏结束。
    示例 2：
        输入：n = 4, k = 4
        输出：[2, 3, 4]
        解释：以下为游戏进行情况：
            1）第 1 个朋友接球，第 1 个朋友将球传给距离他顺时针方向 4 步的玩家 —— 第 1 个朋友。
            2）第 1 个朋友接到两次球，游戏结束。
    提示：
        1 <= k <= n <= 50
    Related Topics:数组,哈希表,模拟
*/
public class NO2682_E_CircularGameLosers {

    @Test
    public void test() {
        assertArrayEquals(getArrays(4, 5), circularGameLosers(5, 2));
        assertArrayEquals(getArrays(2, 3, 4), circularGameLosers(4, 4));
    }

    public int[] circularGameLosers(int n, int k) {
        //输家可以是多个人，如何判断谁没有接过球、如果判断谁接过2次球,用set存储所有人，接球删除，二次接球就不在set中结束  30% 89%
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n; i++)
            set.add(i);

        int index = 0; //首先为1，第一个朋友
        int count = 1; //游戏次数
        while (set.contains(index)) { //如果set中还包含该人，则进行下一次
            set.remove(index); //删除掉这个位置，并寻找下一个位置
            index = ((index + (count*k)) % n); //当前位置加上传递位置，最后取模一下
            count++;
        }

        int [] result = new int[set.size()];
        count = 0;
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext())
            result[count++] = iterator.next() + 1;

        return result;
    }

}
