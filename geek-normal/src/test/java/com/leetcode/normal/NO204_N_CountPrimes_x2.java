/**
 * copyright 2022/1/19
 */
package com.leetcode.normal;

import org.junit.Test;

/**
    (中等)
    204. 计数质数
        给定整数 n ，返回 所有小于非负整数 n 的质数的数量 。
    示例 1：
        输入：n = 10
        输出：4
        解释：小于 10 的质数一共有 4 个, 它们是 2, 3, 5, 7 。
    示例 2：
        输入：n = 0
        输出：0
    示例 3：
        输入：n = 1
        输出：0
*/
public class NO204_N_CountPrimes_x2 {

    @Test
    public void test() {
        assert 4 == countPrimes(10);
        assert 0 == countPrimes(0);
        assert 0 == countPrimes(1);
        assert 0 == countPrimes(2);
    }

    public int countPrimes(int n) {
        int ans = 0;
        return ans;
    }

}



















/**
// 方法1：
public int countPrimes(int n) {
    int ans = 0;
    for (int i = 2; i < n; i++)
        // 从2开始计算，如果是素数那么 +1
        ans += isPrime(i) ? 1 : 0;

    return ans;
}

//是否是素数
public boolean isPrime(int x) {
    for (int i = 2; i * i <= x; i++)
        // 找到一个约数的时候，说明不是质数
        if (x % i == 0)
            return false;

    return true;
}

// 方法2：
public int countPrimes(int n) {
    boolean[] isPrime = new boolean[n];
    Arrays.fill(isPrime, true);

    for (int i = 2; i * i < n; i++)
        if (isPrime[i])
            for (int j = i * i; j < n; j += i)
                isPrime[j] = false;

    int cnt = 0;
    for (int i = 2; i < n; i++)
        if (isPrime[i])
            ++cnt;

    return cnt;
}
*/