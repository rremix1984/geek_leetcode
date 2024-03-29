/**
 * copyright 2020-12-22 12:22
 * @author MCT
 * description
 */
package com.leetcode;

import org.junit.Test;
import java.util.*;
import static com.leetcode.util.MathUtils.getArrays;
import static org.junit.Assert.assertArrayEquals;

/**
    [ARRAY]
    (中等)
    NO.1488 避免洪水泛滥
    你的国家有无数个湖泊，所有湖泊一开始都是空的。当第 n 个湖泊下雨前是空的，
    那么它就会装满水。如果第 n 个湖泊下雨前是 满的 ，这个湖泊会发生 洪水 。
    你的目标是避免任意一个湖泊发生洪水。给你一个整数数组 rains ，其中：
     1）rains[i] > 0 表示第 i 天时，第 rains[i] 个湖泊会下雨。
     2）rains[i] == 0 表示第 i 天没有湖泊会下雨，你可以选择 一个
        湖泊并 抽干 这个湖泊的水。
    请返回一个数组ans，满足：
     1）ans.length == rains.length
     2）如果 rains[i] > 0 ，那么ans[i] == -1 。
     3）如果 rains[i] == 0 ，ans[i] 是你第 i 天选择抽干的湖泊。
     4）如果有多种可行解，请返回它们中的 任意一个。如果没办法阻止洪水，请返回一个空的数组。
    请注意，如果你选择抽干一个装满水的湖泊，它会变成一个空的湖泊。
    但如果你选择抽干一个空的湖泊，那么将无事发生。
    示例 1：
        输入：rains = [1, 2, 3, 4]
        输出：[-1, -1, -1, -1]
        解释：第一天后，装满水的湖泊包括 [1]
        第二天后，装满水的湖泊包括 [1, 2]
        第三天后，装满水的湖泊包括 [1, 2, 3]
        第四天后，装满水的湖泊包括 [1, 2, 3, 4]
        没有哪一天你可以抽干任何湖泊的水，也没有湖泊会发生洪水。
    示例 2：
        输入：rains = [1, 2, 0, 0, 2, 1]
        输出：[-1, -1, 2, 1, -1, -1]
        解释：第一天后，装满水的湖泊包括 [1]
        第二天后，装满水的湖泊包括 [1, 2]
        第三天后，我们抽干湖泊 2 。所以剩下装满水的湖泊包括 [1]
        第四天后，我们抽干湖泊 1 。所以暂时没有装满水的湖泊了。
        第五天后，装满水的湖泊包括 [2]。
        第六天后，装满水的湖泊包括 [1, 2]。
        可以看出，这个方案下不会有洪水发生。同时，[-1, -1, 1, 2, -1, -1]
        也是另一个可行的没有洪水的方案。
    示例 3：
        输入：rains = [1, 2, 0, 1, 2]
        输出：[]
        解释：第二天后，装满水的湖泊包括 [1,2]。我们可以在第三天抽干一个湖泊的水。
        但第三天后，湖泊 1 和 2 都会再次下雨，所以不管我们第三天抽干哪个湖泊的水，另一个湖泊都会发生洪水。
    提示：
        1 <= rains.length <= 105
        0 <= rains[i] <= 109
    Related Topics:贪心,数组,哈希表,二分查找,堆（优先队列）

    思路
    无论是解题还是代码上来说，平衡树更容易一些，毕竟 Java 有自带的 TreeMap/TreeSet ，但速度上并查集要更快些，一般的平衡树的题 python 大多用并查集解决；
    假设当天下雨，如果湖是空的，不需要理会，所以某个湖第一次下雨是不需要抽的，关键是同一个湖第二次以后的下雨，那么我们可以开启上帝模式，碰到晴天的日子，不抽，等到再次下雨时时间穿越到之前进行排水，那么就能做到只要晴天的日子大于等于下雨，必然能让全部湖都不泻；
    但仍然存在问题需要，如果只单独记录当前有多少天是晴天，可能一开始都是晴天，然后后面都是下雨，虽然晴天比雨天多，但因为下雨后没有晴天所以之前的晴天就浪费了，当时的晴天湖是空的，所以需要记录具体是哪天晴天，要对比一下当前要泄的湖上次下雨是什么时候，早一个最早的晴天抽干这个湖；
    平衡树
    所以这里就需要平衡树了，假设当前下雨，我们需要知道当前这个湖上一次下雨后最早一次晴天的日期，然后对其进行抽空纪录然后删除，如果后面出现无法找到对应的晴天，也就是说晴天不够，那么只能返回空集，也就是之前说的是需要找到“上一次下雨后最早一次晴天”不能是“最晚”或“随便”，那样会占用后面可能需要排干的机会；
    并查集
    如果没有 TreeMap/TreeSet 或不用 sortlist ，也是可以的，实际上面最关键就是需要知道当前这个湖上一次下雨后最早一次晴天的日期，只要记录好对应的日期，二分也是可以解决的，但直接二分然后删除的操作在有序集是 O(n) ，略显憨厚，可以采用并查集优化，当前给当前下标一个后跳位置，如果当前下标需要删除，设置其后跳位置不是当前位置即可，故二分查找时查找的下标也是后跳后的下标。进一步的，既然实现了后跳功能，那么如果当初记录下雨日期不是下雨日期，就是下次查询晴天日的下标位置，就干脆连二分查询都省了，单纯的并查集了；
    理论上时间复杂度都是 O(n⋅logm) ，n 是 rans 长度， m 是晴天天数。但很明显，并查集处理量要少得多；
    最后处理边界，就是晴天多出来了，那么随便找个湖抽，不能 -1 不能是 0；
*/
public class NO1488_N_AvoidFlood {

    @Test
    public void test() {
        assertArrayEquals(getArrays(),
            avoidFlood(new int[]{1, 2, 0, 1, 2}));
        assertArrayEquals(getArrays(-1, -1, 2, 1, -1, -1),
            avoidFlood(new int[]{1, 2, 0, 0, 2, 1}));
        assertArrayEquals(getArrays(-1, -1, -1, -1),
            avoidFlood(new int[]{1, 2, 3, 4}));
    }

    public int[] avoidFlood(int[] rains) {
        TreeSet<Integer> notRains = new TreeSet<>();
        Map<Integer, Integer> preRains = new HashMap<>();

        for (int i = 0; i < rains.length; i++) {
            int lake = rains[i];
            if (lake > 0) {
                if (preRains.containsKey(lake)) {
                    Integer day = notRains.higher(preRains.get(lake));
                    if (day == null) return new int[0];
                    notRains.remove(day);
                    rains[day] = lake;
                }
                preRains.put(lake, i);
                rains[i] = -1;
            } else {
                notRains.add(i);
                rains[i] = 1;
            }
        }
        return rains;
    }

}
















/*
// 方法1：
public int[] avoidFlood(int[] rains) {
    TreeSet<Integer> notRains = new TreeSet<>();
    Map<Integer, Integer> preRains = new HashMap<>();

    for (int i = 0; i < rains.length; i++) {
        int lake = rains[i];
        if (lake > 0) {
            if (preRains.containsKey(lake)) {
                Integer day = notRains.higher(preRains.get(lake));
                if (day == null) return new int[0];
                notRains.remove(day);
                rains[day] = lake;
            }
            preRains.put(lake, i);
            rains[i] = -1;
        } else {
            notRains.add(i);
            rains[i] = 1;
        }
    }
    return rains;
}
*/