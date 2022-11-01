/**
 * copyright 2022/1/19
 */
package com.lcp.easy;

import org.junit.Test;

/**
    (简单)
    LCP 22. 黑白方格画
        小扣注意到秋日市集上有一个创作黑白方格画的摊位。摊主给每个顾客提供一个固定在墙上的白色画板，
        画板不能转动。画板上有 n * n 的网格。绘画规则为，小扣可以选择任意多行以及任意多列的格子涂成黑色
        （选择的整行、整列均需涂成黑色），所选行数、列数均可为 0。
        小扣希望最终的成品上需要有 k 个黑色格子，请返回小扣共有多少种涂色方案。
        注意：两个方案中任意一个相同位置的格子颜色不同，就视为不同的方案。
    示例 1：
        输入：n = 2, k = 2
        输出：4
        解释：一共有四种不同的方案：
             第一种方案：涂第一列；
             第二种方案：涂第二列；
             第三种方案：涂第一行；
             第四种方案：涂第二行。
    示例 2：
        输入：n = 2, k = 1
        输出：0
        解释：不可行，因为第一次涂色至少会涂两个黑格。
    示例 3：
        输入：n = 2, k = 4
        输出：1
        解释：共有 2 * 2 = 4 个格子，仅有一种涂色方案。
    限制：
        1 <= n <= 6
        0 <= k <= n * n
    解题思路：
        我们设将 a 行 b 列涂成黑色，会形成 sum 个黑色格子，而 sum = a⋅n + b⋅n − a⋅b 。
        遍历统计所有 sum = k 的涂色方案即可。      a        a
        从 n 行 n 列中选 a 行 b 列，选择方案有  C  n  x  C  n  种。
              m        n!
        其中 C   = ------------
              n    m!⋅(n − m)!
*/
public class LCP_22_E_PaintingPlan_x2 {

    @Test
    public void test() {
        assert 4 == paintingPlan(2,2);
        assert 0 == paintingPlan(2,1);
        assert 1 == paintingPlan(2,4);
    }

    public int paintingPlan(int n, int k) {
        if (k == n * n)
            return 1;

        int ans = 0;
        for (int a = 0; a < n; a++)
            for (int b = 0; b < n; b++)
                if (a * n + b * n - a * b == k)
                    //   a
                    // C n  就是  combination(n, a);
                    ans += combination(n, a) * combination(n, b);

        return ans;
    }

    private int combination(int n, int m) {
        int ans = 1;
        for (int i = n; i > m; i--)
            ans *= i;

        for (int i = n - m; i > 0; i--)
            ans /= i;

        return ans;
    }

}
