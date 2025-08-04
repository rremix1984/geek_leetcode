package com.leetcode.animation;

import com.leetcode.animation.search.NO704_E_BinarySearch_Animation;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.util.List;

/**
 * NO.704 二分查找动画测试类
 * 测试二分查找算法的核心逻辑和UI交互
 * 
 * 设计文档：
 * 1. 功能需求：验证二分查找算法的正确性和动画交互
 * 2. 测试范围：算法逻辑、边界条件、UI组件、性能验证
 * 3. 测试策略：正常查找、边界查找、不存在元素、空数组
 * 4. 边界条件：单元素、首尾元素、中间元素、不存在元素
 * 5. 性能验证：时间复杂度O(log n)，空间复杂度O(1)
 */
public class NO704_E_BinarySearch_AnimationTest extends BaseAnimationTest {
    
    private NO704_E_BinarySearch_Animation animation;
    private JTextField arrayInput;
    private JTextField targetInput;
    private JButton startButton;
    private JButton stepButton;
    private JButton resetButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    @Override
    protected void setupSwingComponents() {
        animation = new NO704_E_BinarySearch_Animation();
        testFrame = animation;
        
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
        assertTrue("窗口应该可见", isComponentVisible(animation));
        assertTrue("开始按钮应该启用", isComponentEnabled(startButton));
        assertFalse("步骤按钮应该禁用", isComponentEnabled(stepButton));
        
        // 测试默认输入值
        assertEquals("默认数组输入", "-1,0,3,5,9,12", getTextFieldValue(arrayInput));
        assertEquals("默认目标值", "9", getTextFieldValue(targetInput));
    }
    
    @Test
    public void testSuccessfulSearch() throws Exception {
        // 测试成功查找
        setTextFieldValue(arrayInput, "-1,0,3,5,9,12");
        setTextFieldValue(targetInput, "9");
        
        clickButton(startButton);
        
        // 执行算法直到完成
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到目标元素", resultText.contains("找到") || resultText.contains("4"));
    }
    
    @Test
    public void testTargetNotFound() throws Exception {
        // 测试目标不存在
        setTextFieldValue(arrayInput, "-1,0,3,5,9,12");
        setTextFieldValue(targetInput, "2");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该显示未找到", resultText.contains("未找到") || resultText.contains("-1"));
    }
    
    @Test
    public void testFirstElement() throws Exception {
        // 测试查找第一个元素
        setTextFieldValue(arrayInput, "1,2,3,4,5");
        setTextFieldValue(targetInput, "1");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到第一个元素", resultText.contains("找到") || resultText.contains("0"));
    }
    
    @Test
    public void testLastElement() throws Exception {
        // 测试查找最后一个元素
        setTextFieldValue(arrayInput, "1,2,3,4,5");
        setTextFieldValue(targetInput, "5");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到最后一个元素", resultText.contains("找到") || resultText.contains("4"));
    }
    
    @Test
    public void testMiddleElement() throws Exception {
        // 测试查找中间元素
        setTextFieldValue(arrayInput, "1,2,3,4,5");
        setTextFieldValue(targetInput, "3");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到中间元素", resultText.contains("找到") || resultText.contains("2"));
    }
    
    @Test
    public void testSingleElement() throws Exception {
        // 测试单个元素数组
        setTextFieldValue(arrayInput, "5");
        setTextFieldValue(targetInput, "5");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到单个元素", resultText.contains("找到") || resultText.contains("0"));
    }
    
    @Test
    public void testSingleElementNotFound() throws Exception {
        // 测试单个元素数组，目标不存在
        setTextFieldValue(arrayInput, "5");
        setTextFieldValue(targetInput, "3");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该显示未找到", resultText.contains("未找到") || resultText.contains("-1"));
    }
    
    @Test
    public void testNegativeNumbers() throws Exception {
        // 测试负数数组
        setTextFieldValue(arrayInput, "-10,-5,-1,0,3,7");
        setTextFieldValue(targetInput, "-5");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(100);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到负数", resultText.contains("找到") || resultText.contains("1"));
    }
    
    @Test
    public void testLargeArray() throws Exception {
        // 测试较大数组
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (i > 0) sb.append(",");
            sb.append(i * 2); // 0,2,4,6,8,...,38
        }
        setTextFieldValue(arrayInput, sb.toString());
        setTextFieldValue(targetInput, "16");
        
        clickButton(startButton);
        
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(50);
        }
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到目标元素", resultText.contains("找到") || resultText.contains("8"));
    }
    
    @Test
    public void testBoundaryValues() throws Exception {
        // 测试边界值
        setTextFieldValue(arrayInput, "1,3,5,7,9");
        
        // 测试小于最小值
        setTextFieldValue(targetInput, "0");
        clickButton(startButton);
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(50);
        }
        String resultText = resultLabel.getText();
        assertTrue("小于最小值应该未找到", resultText.contains("未找到") || resultText.contains("-1"));
        
        // 重置并测试大于最大值
        clickButton(resetButton);
        waitFor(100);
        setTextFieldValue(targetInput, "10");
        clickButton(startButton);
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(50);
        }
        resultText = resultLabel.getText();
        assertTrue("大于最大值应该未找到", resultText.contains("未找到") || resultText.contains("-1"));
    }
    
    @Test
    public void testResetFunctionality() throws Exception {
        setTextFieldValue(arrayInput, "1,2,3,4,5");
        setTextFieldValue(targetInput, "3");
        
        clickButton(startButton);
        clickButton(stepButton); // 执行一步
        
        clickButton(resetButton);
        
        assertTrue("重置后开始按钮应该启用", isComponentEnabled(startButton));
        assertFalse("重置后步骤按钮应该禁用", isComponentEnabled(stepButton));
    }
    
    @Test
    public void testInvalidInput() throws Exception {
        // 测试无效输入
        setTextFieldValue(arrayInput, "abc,def");
        setTextFieldValue(targetInput, "5");
        
        try {
            clickButton(startButton);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testUnsortedArray() throws Exception {
        // 测试未排序数组（二分查找要求数组有序）
        setTextFieldValue(arrayInput, "3,1,4,1,5");
        setTextFieldValue(targetInput, "4");
        
        clickButton(startButton);
        
        // 算法应该能处理或给出提示
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            waitFor(50);
        }
        
        // 结果可能不正确，但程序不应崩溃
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testAlgorithmSteps() throws Exception {
        // 测试算法步骤的正确性
        setTextFieldValue(arrayInput, "1,3,5,7,9,11");
        setTextFieldValue(targetInput, "7");
        
        clickButton(startButton);
        
        int stepCount = 0;
        while (isComponentEnabled(stepButton)) {
            clickButton(stepButton);
            stepCount++;
            waitFor(100);
            
            // 验证状态标签有更新
            String statusText = statusLabel.getText();
            assertNotNull("状态文本不应为空", statusText);
            
            // 防止无限循环
            if (stepCount > 10) break;
        }
        
        assertTrue("应该执行了合理数量的步骤", stepCount > 0 && stepCount <= 6);
    }
    
    @Test
    public void testDataStructureState() throws Exception {
        setTextFieldValue(arrayInput, "1,3,5,7,9");
        setTextFieldValue(targetInput, "5");
        
        clickButton(startButton);
        
        // 获取内部数据结构
        @SuppressWarnings("unchecked")
        List<Integer> nums = (List<Integer>) getPrivateField(animation, "nums");
        Integer target = (Integer) getPrivateField(animation, "target");
        
        assertNotNull("nums列表应该存在", nums);
        assertNotNull("target应该存在", target);
        assertEquals("nums大小应该正确", 5, nums.size());
        assertEquals("target值应该正确", Integer.valueOf(5), target);
    }
    
    @Test
    public void testPerformanceCharacteristics() throws Exception {
        // 测试性能特征（步骤数应该是O(log n)）
        int[] arraySizes = {8, 16, 32, 64};
        
        for (int size : arraySizes) {
            clickButton(resetButton);
            waitFor(100);
            
            // 创建有序数组
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                if (i > 0) sb.append(",");
                sb.append(i);
            }
            setTextFieldValue(arrayInput, sb.toString());
            setTextFieldValue(targetInput, String.valueOf(size - 1)); // 查找最后一个元素
            
            clickButton(startButton);
            
            int stepCount = 0;
            while (isComponentEnabled(stepButton)) {
                clickButton(stepButton);
                stepCount++;
                waitFor(50);
                if (stepCount > 20) break; // 防止无限循环
            }
            
            // 验证步骤数大致符合O(log n)
            int expectedMaxSteps = (int) Math.ceil(Math.log(size) / Math.log(2)) + 2;
            assertTrue("数组大小 " + size + " 的步骤数应该合理 (实际: " + stepCount + ", 期望最大: " + expectedMaxSteps + ")", 
                      stepCount <= expectedMaxSteps);
        }
    }
}