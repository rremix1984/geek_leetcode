/**
 * copyright 2022/1/19
 */
package com.leetcode.undo;

import org.junit.Test;

/**
    (简单)
    1716. 计算力扣银行的钱
        Hercy 想要为购买第一辆车存钱。他 每天 都往力扣银行里存钱。
        最开始，他在周一的时候存入 1 块钱。从周二到周日，他每天都比前一天多存入 1 块钱。
        在接下来每一个周一，他都会比 前一个周一 多存入 1 块钱。
        给你 n ，请你返回在第 n 天结束的时候他在力扣银行总共存了多少块钱。
    示例 1：
        输入：n = 4
        输出：10
        解释：第 4 天后，总额为 1 + 2 + 3 + 4 = 10 。
    示例 2：
        输入：n = 10
        输出：37
        解释：第 10 天后，总额为 (1 + 2 + 3 + 4 + 5 + 6 + 7) + (2 + 3 + 4) = 37 。
             注意到第二个星期一，Hercy 存入 2 块钱。
    示例 3：
        输入：n = 20
        输出：96
        解释：第 20 天后，总额为
              (1 + 2 + 3 + 4 + 5 + 6 + 7)
            + (2 + 3 + 4 + 5 + 6 + 7 + 8)
            + (3 + 4 + 5 + 6 + 7 + 8) = 96 。
*/
public class NO1716_E_TotalMoney {

    @Test
    public void test() {
        assert 10 == totalMoney(4);
        assert 37 == totalMoney(10);
        assert 96 == totalMoney(20);
    }

    public int totalMoney(int n) {
        int res = 0;
        int week = 1;
        int day = 1;
        for (int i = 0; i < n; i++) {
            res += week + day - 1;
            if (++day == 8) {
                day = 1;
                week++;
            }
        }
        return res;
    }

}
