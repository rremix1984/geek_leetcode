/**
 * copyright 2022/1/19
 */
package com.lcp;

import org.junit.Test;

import static java.lang.Math.*;

/**
    [ARRAY] ||
    (简单)
    LCP.33. 蓄水
    给定 N 个无限容量且初始均空的水缸，每个水缸配有一个水桶用来打水，
    第i个水缸配备的水桶容量记作bucket[i]。小扣有以下两种操作：
      1）升级水桶：选择任意一个水桶，使其容量增加为 bucket[i] + 1
      2）蓄水：将全部水桶接满水，倒入各自对应的水缸
    每个水缸对应最低蓄水量记作 vat[i]，
    返回小扣至少需要多少次操作可以完成所有水缸蓄水要求。
    注意：实际蓄水量达到或超过最低蓄水量，即完成蓄水要求。
    示例 1：
        输入：bucket = [1, 3], vat = [6, 8]
        输出：4
        解释：第 1 次操作升级 bucket[0]；
             第 2 ~ 4 次操作均选择蓄水，即可完成蓄水要求。
    示例 2：
        输入：bucket = [9, 0, 1], vat = [0, 2, 2]
        输出：3
        解释：第 1 次操作均选择升级 bucket[1]
             第 2~3 次操作选择蓄水，即可完成蓄水要求。
    提示：
        1 <= bucket.length == vat.length <= 100
        0 <= bucket[i], vat[i] <= 10^4
    解题思路：
      可以想象你负责一个需要灌溉的花园，花园里有各种容器，每个
    容器都需要填满水才能满足灌溉需求。你有一个可调节大小的水桶，
    你可以选择升级水桶的容量（但这需要时间和努力），也可以用水
    桶多次往容器里倒水。问题在于，你想找到一个平衡，用最少的努
    力让所有容器都得到足够的水。
    1）检查是否需要操作：
        如果最大的容器（maxVat）容量为0，意味着没有灌溉需求，
    因此操作次数为0。
    2）枚举倒水次数：
        通过循环来考虑每一次倒水的可能性，从1次到10000次。这相
    当于考虑用水桶向容器倒水的每一种可能的频率。
    3）计算升级次数：
        对于每一种倒水频率，计算为了满足每个容器的需求，水桶需要
    升级的最小次数。这一步骤涉及到数学计算，即将每个容器的需求除
    以倒水次数，然后减去水桶当前的容量，得到水桶需要升级的次数。
    4）计算总操作次数：
        对于每一种倒水频率，总操作次数等于倒水次数加上所有水桶升
    级的总次数。然后，从所有可能的方案中选择操作次数最少的。
*/
public class LCP_33_E_StoreWater {

    @Test
    public void test() {
        assert 4 == storeWater(
            new int[]{1, 3},new int[]{6, 8});
        assert 3 == storeWater(
            new int[]{9, 0, 1},new int[]{0, 2, 2});
    }

    public int storeWater(int[] bucket, int[] vat) {
        // 2024/3/8  NO.1
        // 2024/3/31 NO.2 发烧，头疼根本没耐心看
        int maxVat = 0;
        for (int v : vat)
            maxVat = max(v, maxVat);

        if (maxVat == 0)
            return 0; //最大容量为0，代表不需蓄水，直接返回0

        int ans = 10001;
        for (int pour = 1; pour <= 10000; pour++) { //枚举倒水次数1-10000
            if (pour >= ans)
                break;

            int upgrade = 0;
            for (int i = 0; i < vat.length; i++) { //枚举每个水桶，计算总升级次数
                int cur = (int) ceil((double)vat[i] / pour - bucket[i]); //容量/倒水次数-初始蓄水量=升级次数
                upgrade += max(cur, 0);
                if (upgrade >= ans)
                    break;
            }
            ans = min(ans, upgrade + pour); //倒水次数 + 总升级次数 = 总次数
        }
        return ans;
    }

}















/*
// 方法1：
public int storeWater(int[] bucket, int[] vat) {
    int maxVat = 0;
    for (int v : vat)
        maxVat = Math.max(v, maxVat);

    if (maxVat == 0)
        return 0; //最大容量为0，代表不需蓄水，直接返回0

    int ans = 10001;
    for (int pour = 1; pour <= 10000; pour++) { //枚举倒水次数1-10000
        if (pour >= ans)
            break;

        int upgrade = 0;
        for (int i = 0; i < vat.length; i++) { //枚举每个水桶，计算总升级次数
            int cur = (int) Math.ceil((double)vat[i] / pour - bucket[i]); //容量/倒水次数-初始蓄水量=升级次数
            upgrade += cur > 0 ? cur : 0;
            if (upgrade >= ans)
                break;
        }
        ans = min(ans, upgrade + pour); //倒水次数 + 总升级次数 = 总次数
    }
    return ans;
}
*/