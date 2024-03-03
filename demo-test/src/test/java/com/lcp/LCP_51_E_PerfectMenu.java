/**
 * copyright 2022/1/19
 */
package com.lcp;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.max;

/**
    [ARRAY]
    (简单)
    LCP 51. 烹饪料理
        欢迎各位勇者来到力扣城，城内设有烹饪锅供勇者制作料理，为自己恢复状态。
        勇者背包内共有编号为 0 ~ 4 的五种食材，其中 materials[j] 表示第 j 种食材的数量。
        通过这些食材可以制作若干料理，cookbooks[i][j] 表示制作第 i 种料理需要第 j 种食材的数量，
        而 attribute[i] = [x,y] 表示第 i 道料理的美味度 x 和饱腹感 y。
        在饱腹感不小于 limit 的情况下，请返回勇者可获得的最大美味度。如果无法满足饱腹感要求，则返回 -1。
        注意：每种料理只能制作一次。
    示例 1：
        输入：materials = {3,2,4,1,2}
             cookbooks = {{1,1,0,1,2},{2,1,4,0,0},{3,2,4,1,0}}
             attribute = {{3,2},{2,4},{7,6}}
             limit = 5
        输出：7
        解释：食材数量可以满足以下两种方案：
             方案一：制作料理 0 和料理 1，可获得饱腹感 2+4、美味度 3+2
             方案二：仅制作料理 2， 可饱腹感为 6、美味度为 7
             因此在满足饱腹感的要求下，可获得最高美味度 7
    示例 2：
        输入：materials = {10,10,10,10,10}
             cookbooks = {{1,1,1,1,1},{3,3,3,3,3},{10,10,10,10,10}}
             attribute = {{5,5},{6,6},{10,10}}
             limit = 1
        输出：11
        解释：通过制作料理 0 和 1，可满足饱腹感，并获得最高美味度 11
    提示：
        materials.length == 5
        1 <= cookbooks.length == attribute.length <= 8
        cookbooks[i].length == 5
        attribute[i].length == 2
        0 <= materials[i], cookbooks[i][j], attribute[i][j] <= 20
        1 <= limit <= 100
*/
public class LCP_51_E_PerfectMenu {

    @Test
    public void test() {
        assert 7 == perfectMenu(new int[]{3,2,4,1,2},
                new int[][]{{1,1,0,1,2},{2,1,4,0,0},{3,2,4,1,0}},
                new int[][]{{3,2},{2,4},{7,6}}, 5);
        assert 11 == perfectMenu(new int[]{10,10,10,10,10},
                new int[][]{{1,1,1,1,1},{3,3,3,3,3},{10,10,10,10,10}},
                new int[][]{{5,5},{6,6},{10,10}}, 1);
    }

    int[] materials;
    int[][] cookbooks, attribute;
    int limit, ans = -1;
    List<Integer> list = new ArrayList<>();

    public int perfectMenu(int[] materials, int[][] cookbooks, int[][] attribute, int limit) {
        this.materials = materials;
        this.cookbooks = cookbooks;
        this.attribute = attribute;
        this.limit = limit;
        dfs(0, 0, 0, new int[5]);
        return ans;
    }

    /**
     *
     * @param index  表示当前为第几种料理
     * @param l 当前的饱腹感
     * @param s 当前的美味度
     * @param ma  保存当前所消耗的原材料
     */
    private void dfs(int index, int l, int s, int[] ma) {
        if (l >= limit) {
            boolean f=true;
            for (int i = 0; i < ma.length&&f; i++)//判断所消耗材料是否超出原有材料
                if (ma[i] > materials[i])
                    f = false;

            if (f)
                ans = max(ans, s);
        }

        for (int i = index; i < attribute.length; i++) {
            //attribute[i] = [x,y] 表示第 i 道料理的美味度 x 和饱腹感 y。
            int x = attribute[i][0], y = attribute[i][1];
            list.add(i);
            for (int t = 0; t < ma.length; t++)
                ma[t]+=cookbooks[i][t];

            dfs(i + 1, l + y, x + s, ma);// 如果以index递归进去，则有大量重复的数据
            int tmp = list.get(list.size() - 1);
            list.remove(list.size() - 1);//回溯
            for (int t = 0; t < ma.length; t++)
                ma[t]-=cookbooks[tmp][t];//回溯
        }
    }

}
