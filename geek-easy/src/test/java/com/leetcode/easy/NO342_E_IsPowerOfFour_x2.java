/**
 * copyright 2022/1/19
 */
package com.leetcode.easy;

import org.junit.Test;

/**
    (简单)
    342. 4的幂
        给定一个整数，写一个函数来判断它是否是 4 的幂次方。
        如果是，返回 true ；否则，返回 false 。
        整数 n 是 4 的幂次方需满足：存在整数 x 使得 n == 4x
    示例 1：
        输入：n = 16
        输出：true
    示例 2：
        输入：n = 5
        输出：false
    示例 3：
        输入：n = 1
        输出：true
    提示：
        -231 <= n <= 231 - 1

    方法一：二进制表示中 11 的位置
        思路与算法
        如果 n 是 4 的幂，那么 n 的二进制表示中有且仅有一个 1，
    并且这个 1 出现在从低位开始的第偶数个二进制位上（这是因为这个 1 后面必须有偶数个 0）。
    这里我们规定最低位为第 0 位，例如 n = 16 时，n 的二进制表示为:
        (10000)2
    唯一的 1 出现在第 4 个二进制位上，因此 n 是 4 的幂。
    由于题目保证了 n 是一个 32 位的有符号整数，因此我们可以构造一个整数mask，
    它的所有偶数二进制位都是 0，所有奇数二进制位都是 1。
    这样一来，我们将 n 和 mask 进行按位与运算，如果结果为 0，
    说明 n 二进制表示中的 1 出现在偶数的位置，否则说明其出现在奇数的位置。
    根据上面的思路，mask 的二进制表示为：
        mask = (10101010101010101010101010101010)2
    我们也可以将其表示成 16 进制的形式，使其更加美观：
        mask = (AAAAAAAA)16

    方法二：取模性质
        思路与算法
        如果 n 是 4 的幂，那么它可以表示成 4 ^ x
    的形式，我们可以发现它除以 3 的余数一定为 1，即：
    4 ^ x ≡ (3 + 1) ^ x≡ 1 ^ x ≡ 1 (mod3)
    如果 n 是 2 的幂却不是 4 的幂，那么它可以表示成 4^x × 2 的形式，
    此时它除以 3 的余数一定为 2。
    因此我们可以通过 n 除以 3 的余数是否为 1 来判断 n 是否是 4 的幂。
*/
public class NO342_E_IsPowerOfFour_x2 {

    @Test
    public void test() {
        assert isPowerOfFour(16);
        assert !isPowerOfFour(5);
        assert isPowerOfFour(1);
        assert !isPowerOfFour(7);
    }

    public boolean isPowerOfFour(int n) {
        return false;
    }

}
















/**
// 方法1：
public boolean isPowerOfFour(int n) {
    return n > 0
        && (n & (n - 1)) == 0
        && (n & 0xaaaaaaaa) == 0;
}

// 方法2：
public boolean isPowerOfFour(int n) {
    // 例如：7  111 不是 4 的幂
    //  111 & 110 != 0
    // 但是：16 是 4 的幂
    //  10000 & 1111 == 0 说明每一位都相反
    return (n & (n - 1)) == 0
            && n % 3 == 1;
}
*/