/**
 * copyright 2022/1/19
 */
package com.lcp;

import org.junit.Test;

import static java.lang.Math.*;

/**
    [ARRAY] ||
    (简单)
    LCP 33. 蓄水
        给定N个无限容量且初始均空的水缸，每个水缸配有一个水桶用来打水，
        第i个水缸配备的水桶容量记作bucket[i]。小扣有以下两种操作：
        升级水桶：选择任意一个水桶，使其容量增加为 bucket[i]+1
        蓄水：将全部水桶接满水，倒入各自对应的水缸每个水缸对应最低蓄水量记作 vat[i]，
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