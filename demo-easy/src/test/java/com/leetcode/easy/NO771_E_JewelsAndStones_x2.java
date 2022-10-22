package com.leetcode.easy;

import org.junit.Test;
import static com.leetcode.util.LogUtil.info;

/**
    （简单）
    771. 宝石与石头
        给你一个字符串 jewels 代表石头中宝石的类型，另有一个字符串 stones 代表你拥有的石头。
        stones 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
        字母区分大小写，因此 "a" 和 "A" 是不同类型的石头。
    示例 1：
        输入：jewels = "aA", stones = "aAAbbbb"
        输出：3
    示例 2：
        输入：jewels = "z", stones = "ZZ"
        输出：0
*/
public class NO771_E_JewelsAndStones_x2 {

    @Test
    public void test() {
        assert 3 == (numJewelsInStones("aA", "aAAbbbb"));// 3
        assert 0 == (numJewelsInStones("z", "ZZ"));// 0
    }

    public int numJewelsInStones(String jewels, String stones) {
        return 0;
    }

}














/**
// 方法1：穷举法 O(n^2)
public int numJewelsInStones(String jewels, String stones) {
    int jewelsCount = 0;
    for ( char stone : stones.toCharArray())
        for (char jewel : jewels.toCharArray())
            if (stone == jewel) {
                jewelsCount++;
                break;
            }
    return jewelsCount;
}

public int numJewelsInStones(String J, String S) {
    return Stream.of(S.split(""))
                 .map(c -> J.contains(c) ? 1 : 0)
                 .reduce((a,b) -> a + b)
                 .get();
}
*/