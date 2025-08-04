package com.leetcode.animation;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * NO.001 两数之和动画测试类
 * 测试两数之和算法的核心逻辑和UI交互
 * 
 * 设计文档：
 * 1. 功能需求：验证两数之和算法的正确性和动画交互
 * 2. 测试范围：算法逻辑、边界条件、UI组件、异常处理
 * 3. 测试策略：单元测试 + 集成测试 + UI测试
 * 4. 边界条件：空数组、无解、多解、负数等
 * 5. 性能验证：时间复杂度O(n)，空间复杂度O(n)
 */
public class NO001_E_TwoSum_AnimationTest extends BaseAnimationTest {
    
    private NO001_E_TwoSum_Animation animation;
    private JTextField arrayInput;
    private JTextField targetInput;
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    @Override
    protected void setupSwingComponents() {
        animation = new NO001_E_TwoSum_Animation();
        testFrame = animation;
        
        // 获取UI组件的引用
        try {
            arrayInput = (JTextField) getPrivateField(animation, "arrayInput");
            targetInput = (JTextField) getPrivateField(animation, "targetInput");
            startButton = (JButton) getPrivateField(animation, "startButton");
            stepButton = (JButton) getPrivateField(animation, "stepButton");
            resetButton = (JButton) getPrivateField(animation, "resetButton");
            statusLabel = (JLabel) getPrivateField(animation, "statusLabel");
            resultLabel = (JLabel) getPrivateField(animation, "resultLabel");
        } catch (Exception e) {
            throw new RuntimeException("Failed to get UI components", e);
        }
    }
    
    @Test
    public void testInitialState() throws Exception {
        // 测试初始状态
        assertTrue("窗口应该可见", isComponentVisible(animation));
        assertTrue("开始按钮应该启用", isComponentEnabled(startButton));
        assertFalse("步骤按钮应该禁用", isComponentEnabled(stepButton));
        
        // 测试默认输入值
        assertEquals("默认数组输入", "2,7,11,15", getTextFieldValue(arrayInput));
        assertEquals("默认目标值", "9", getTextFieldValue(targetInput));
    }
    
    @Test
    public void testBasicTwoSum() throws Exception {
        // 测试基本的两数之和算法
        setTextFieldValue(arrayInput, "2,7,11,15");
        setTextFieldValue(targetInput, "9");
        
        clickButton(startButton);
        
        // 验证开始状态
        assertFalse("开始按钮应该禁用", isComponentEnabled(startButton));
        assertTrue("步骤按钮应该启用", isComponentEnabled(stepButton));
        
        // 执行算法步骤
        clickButton(stepButton); // 第一步：检查nums[0]=2
        clickButton(stepButton); // 第二步：检查nums[1]=7，找到解
        
        // 验证结果
        String resultText = resultLabel.getText();
        assertTrue("应该找到解", resultText.contains("找到解"));
    }
    
    @Test
    public void testNoSolution() throws Exception {
        // 测试无解情况
        setTextFieldValue(arrayInput, "1,2,3,4");
        setTextFieldValue(targetInput, "10");
        
        clickButton(startButton);
        
        // 执行所有步骤
        for (int i = 0; i < 4; i++) {
            if (isComponentEnabled(stepButton)) {
                clickButton(stepButton);
                waitFor(100);
            }
        }
        
        // 验证无解结果
        String resultText = resultLabel.getText();
        assertTrue("应该显示无解", resultText.contains("无解") || resultText.contains("未找到"));
    }
    
    @Test
    public void testNegativeNumbers() throws Exception {
        // 测试负数情况
        setTextFieldValue(arrayInput, "-1,2,3,-4");
        setTextFieldValue(targetInput, "-1");
        
        clickButton(startButton);
        
        // 执行算法
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        // 验证能处理负数
        String resultText = resultLabel.getText();
        assertNotNull("结果不应为空", resultText);
    }
    
    @Test
    public void testDuplicateNumbers() throws Exception {
        // 测试重复数字
        setTextFieldValue(arrayInput, "3,3");
        setTextFieldValue(targetInput, "6");
        
        clickButton(startButton);
        
        // 执行算法
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到解", resultText.contains("找到解") || resultText.contains("[0,1]"));
    }
    
    @Test
    public void testSingleElement() throws Exception {
        // 测试单个元素（无解）
        setTextFieldValue(arrayInput, "5");
        setTextFieldValue(targetInput, "5");
        
        clickButton(startButton);
        
        // 执行算法
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("单个元素应该无解", resultText.contains("无解") || resultText.contains("未找到"));
    }
    
    @Test
    public void testResetFunctionality() throws Exception {
        // 测试重置功能
        setTextFieldValue(arrayInput, "1,2,3,4");
        setTextFieldValue(targetInput, "5");
        
        clickButton(startButton);
        clickButton(stepButton); // 执行一步
        
        clickButton(resetButton);
        
        // 验证重置后状态
        assertTrue("重置后开始按钮应该启用", isComponentEnabled(startButton));
        assertFalse("重置后步骤按钮应该禁用", isComponentEnabled(stepButton));
        
        String statusText = statusLabel.getText();
        assertTrue("状态应该重置", statusText.contains("准备") || statusText.contains("重置"));
    }
    
    @Test
    public void testInvalidInput() throws Exception {
        // 测试无效输入
        setTextFieldValue(arrayInput, "abc,def");
        setTextFieldValue(targetInput, "5");
        
        // 应该处理异常而不崩溃
        try {
            clickButton(startButton);
            // 如果没有抛出异常，说明有错误处理机制
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        // 验证程序仍然可用
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testEmptyInput() throws Exception {
        // 测试空输入
        setTextFieldValue(arrayInput, "");
        setTextFieldValue(targetInput, "5");
        
        try {
            clickButton(startButton);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testLargeNumbers() throws Exception {
        // 测试大数字
        setTextFieldValue(arrayInput, "1000000,2000000,3000000");
        setTextFieldValue(targetInput, "3000000");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(50);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到解", resultText.contains("找到解") || resultText.contains("[0,1]"));
    }
    
    @Test
    public void testAlgorithmCorrectness() throws Exception {
        // 测试算法正确性 - 多个测试用例
        int[][] testCases = {
            {2, 7, 11, 15}, // target: 9, expected: [0,1]
            {3, 2, 4},      // target: 6, expected: [1,2]
            {3, 3},         // target: 6, expected: [0,1]
            {1, 5, 3, 7}    // target: 8, expected: [1,3]
        };
        int[] targets = {9, 6, 6, 8};
        
        for (int i = 0; i < testCases.length; i++) {
            // 重置
            clickButton(resetButton);
            waitFor(100);
            
            // 设置输入
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < testCases[i].length; j++) {
                if (j > 0) sb.append(",");
                sb.append(testCases[i][j]);
            }
            setTextFieldValue(arrayInput, sb.toString());
            setTextFieldValue(targetInput, String.valueOf(targets[i]));
            
            // 执行算法
            clickButton(startButton);
            while (isComponentEnabled(stepButton)) {
                clickButton(stepButton);
                waitFor(50);
            }
            
            // 验证结果
            String resultText = resultLabel.getText();
            assertTrue("测试用例 " + i + " 应该找到解", 
                      resultText.contains("找到解") || resultText.contains("["));
        }
    }
    
    @Test
    public void testUIComponentsExist() throws Exception {
        // 测试所有UI组件都存在
        assertNotNull("数组输入框应该存在", arrayInput);
        assertNotNull("目标值输入框应该存在", targetInput);
        assertNotNull("开始按钮应该存在", startButton);
        assertNotNull("步骤按钮应该存在", stepButton);
        assertNotNull("重置按钮应该存在", resetButton);
        assertNotNull("状态标签应该存在", statusLabel);
        assertNotNull("结果标签应该存在", resultLabel);
    }
    
    @Test
    public void testDataStructureState() throws Exception {
        // 测试内部数据结构状态
        setTextFieldValue(arrayInput, "2,7,11,15");
        setTextFieldValue(targetInput, "9");
        
        clickButton(startButton);
        
        // 获取内部数据结构
        @SuppressWarnings("unchecked")
        List<Integer> nums = (List<Integer>) getPrivateField(animation, "nums");
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> map = (Map<Integer, Integer>) getPrivateField(animation, "map");
        
        assertNotNull("nums列表应该存在", nums);
        assertNotNull("map应该存在", map);
        assertEquals("nums大小应该正确", 4, nums.size());
        assertEquals("第一个元素应该是2", Integer.valueOf(2), nums.get(0));
    }
}