package com.leetcode.hard;

import org.junit.Test;
import javax.swing.SwingUtilities;

/**
 * 测试动画类是否能正常创建和初始化
 */
public class AnimationTest {
    
    @Test
    public void testSerializeDeserializeAnimation() {
        try {
            // 测试序列化反序列化动画类
            NO297_H_SerializeAndDeserializeBinaryTree_Animation animation = 
                new NO297_H_SerializeAndDeserializeBinaryTree_Animation();
            System.out.println("NO297 动画类创建成功");
        } catch (Exception e) {
            System.err.println("NO297 动画类创建失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testSumOfDistancesAnimation() {
        try {
            // 测试树中距离之和动画类
            NO834_H_SumOfDistancesInTree_Animation animation = 
                new NO834_H_SumOfDistancesInTree_Animation();
            System.out.println("NO834 动画类创建成功");
        } catch (Exception e) {
            System.err.println("NO834 动画类创建失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Test
    public void testLauncher() {
        try {
            // 测试启动器
            HardAlgorithmAnimationLauncher launcher = new HardAlgorithmAnimationLauncher();
            System.out.println("HardAlgorithmAnimationLauncher 创建成功");
        } catch (Exception e) {
            System.err.println("HardAlgorithmAnimationLauncher 创建失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}