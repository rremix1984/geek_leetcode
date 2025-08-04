package com.leetcode.animation;

import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.util.List;
import com.leetcode.animation.datastructure.NO703_E_KthLargest_Animation;

/**
 * NO.703 数据流中的第K大元素动画测试类
 * 测试KthLargest数据结构的核心逻辑和UI交互
 * 
 * 设计文档：
 * 1. 功能需求：验证KthLargest数据结构的正确性和动画交互
 * 2. 测试范围：数据结构操作、边界条件、UI组件、性能验证
 * 3. 测试策略：初始化、添加元素、获取第K大、边界情况
 * 4. 边界条件：K=1、K等于数组长度、空初始数组、重复元素
 * 5. 性能验证：add操作时间复杂度O(log n)
 */
public class NO703_E_KthLargest_AnimationTest extends BaseAnimationTest {
    
    private NO703_E_KthLargest_Animation animation;
    private JTextField kInput;
    private JTextField arrayInput;
    private JTextField valueInput;
    private JButton initButton;
    private JButton addButton;
    private JButton resetButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    @Override
    protected void setupSwingComponents() {
        animation = new NO703_E_KthLargest_Animation();
        testFrame = animation;
        
        try {
            kInput = (JTextField) getPrivateField(animation, "kInput");
            arrayInput = (JTextField) getPrivateField(animation, "arrayInput");
            valueInput = (JTextField) getPrivateField(animation, "valueInput");
            initButton = (JButton) getPrivateField(animation, "initButton");
            addButton = (JButton) getPrivateField(animation, "addButton");
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
        assertTrue("初始化按钮应该启用", isComponentEnabled(initButton));
        assertFalse("添加按钮应该禁用", isComponentEnabled(addButton));
        
        // 测试默认输入值
        assertEquals("默认K值", "3", getTextFieldValue(kInput));
        assertEquals("默认数组", "4,5,8,2", getTextFieldValue(arrayInput));
    }
    
    @Test
    public void testBasicInitialization() throws Exception {
        setTextFieldValue(kInput, "3");
        setTextFieldValue(arrayInput, "4,5,8,2");
        
        clickButton(initButton);
        waitFor(200);
        
        assertTrue("初始化后添加按钮应该启用", isComponentEnabled(addButton));
        assertFalse("初始化后初始化按钮应该禁用", isComponentEnabled(initButton));
    }
    
    @Test
    public void testAddOperation() throws Exception {
        // 初始化
        setTextFieldValue(kInput, "3");
        setTextFieldValue(arrayInput, "4,5,8,2");
        clickButton(initButton);
        waitFor(200);
        
        // 添加元素
        setTextFieldValue(valueInput, "3");
        clickButton(addButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("应该显示第3大元素", resultText.contains("4"));
    }
    
    @Test
    public void testMultipleAddOperations() throws Exception {
        // 初始化
        setTextFieldValue(kInput, "3");
        setTextFieldValue(arrayInput, "4,5,8,2");
        clickButton(initButton);
        waitFor(200);
        
        // 添加多个元素
        int[] values = {3, 5, 10, 9, 4};
        int[] expectedResults = {4, 5, 5, 8, 8}; // 预期的第3大元素
        
        for (int i = 0; i < values.length; i++) {
            setTextFieldValue(valueInput, String.valueOf(values[i]));
            clickButton(addButton);
            waitFor(200);
            
            String resultText = resultLabel.getText();
            assertTrue("添加 " + values[i] + " 后应该显示正确的第3大元素", 
                      resultText.contains(String.valueOf(expectedResults[i])));
        }
    }
    
    @Test
    public void testKEqualsOne() throws Exception {
        // 测试K=1的情况（最大元素）
        setTextFieldValue(kInput, "1");
        setTextFieldValue(arrayInput, "1,2,3");
        clickButton(initButton);
        waitFor(200);
        
        setTextFieldValue(valueInput, "5");
        clickButton(addButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("K=1时应该返回最大元素", resultText.contains("5"));
    }
    
    @Test
    public void testKEqualsArrayLength() throws Exception {
        // 测试K等于数组长度的情况（最小元素）
        setTextFieldValue(kInput, "4");
        setTextFieldValue(arrayInput, "4,5,8,2");
        clickButton(initButton);
        waitFor(200);
        
        setTextFieldValue(valueInput, "1");
        clickButton(addButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("K等于数组长度时应该返回最小元素", resultText.contains("1"));
    }
    
    @Test
    public void testEmptyInitialArray() throws Exception {
        // 测试空初始数组
        setTextFieldValue(kInput, "2");
        setTextFieldValue(arrayInput, "");
        clickButton(initButton);
        waitFor(200);
        
        // 添加第一个元素
        setTextFieldValue(valueInput, "5");
        clickButton(addButton);
        waitFor(200);
        
        // 添加第二个元素
        setTextFieldValue(valueInput, "3");
        clickButton(addButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("空初始数组添加两个元素后应该正确", resultText.contains("3"));
    }
    
    @Test
    public void testDuplicateElements() throws Exception {
        // 测试重复元素
        setTextFieldValue(kInput, "2");
        setTextFieldValue(arrayInput, "3,3,3");
        clickButton(initButton);
        waitFor(200);
        
        setTextFieldValue(valueInput, "3");
        clickButton(addButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("重复元素应该正确处理", resultText.contains("3"));
    }
    
    @Test
    public void testNegativeNumbers() throws Exception {
        // 测试负数
        setTextFieldValue(kInput, "2");
        setTextFieldValue(arrayInput, "-1,-3,-2");
        clickButton(initButton);
        waitFor(200);
        
        setTextFieldValue(valueInput, "-4");
        clickButton(addButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("负数应该正确处理", resultText.contains("-2"));
    }
    
    @Test
    public void testLargeNumbers() throws Exception {
        // 测试大数
        setTextFieldValue(kInput, "2");
        setTextFieldValue(arrayInput, "1000000,999999,1000001");
        clickButton(initButton);
        waitFor(200);
        
        setTextFieldValue(valueInput, "1000002");
        clickButton(addButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("大数应该正确处理", resultText.contains("1000001"));
    }
    
    @Test
    public void testResetFunctionality() throws Exception {
        // 初始化并添加元素
        setTextFieldValue(kInput, "3");
        setTextFieldValue(arrayInput, "4,5,8,2");
        clickButton(initButton);
        waitFor(200);
        
        setTextFieldValue(valueInput, "10");
        clickButton(addButton);
        waitFor(200);
        
        // 重置
        clickButton(resetButton);
        waitFor(200);
        
        assertTrue("重置后初始化按钮应该启用", isComponentEnabled(initButton));
        assertFalse("重置后添加按钮应该禁用", isComponentEnabled(addButton));
        assertEquals("重置后结果标签应该清空", "", resultLabel.getText());
    }
    
    @Test
    public void testInvalidKValue() throws Exception {
        // 测试无效的K值
        setTextFieldValue(kInput, "0");
        setTextFieldValue(arrayInput, "1,2,3");
        
        try {
            clickButton(initButton);
            waitFor(200);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testKLargerThanArraySize() throws Exception {
        // 测试K大于数组大小
        setTextFieldValue(kInput, "5");
        setTextFieldValue(arrayInput, "1,2,3");
        
        try {
            clickButton(initButton);
            waitFor(200);
            
            setTextFieldValue(valueInput, "4");
            clickButton(addButton);
            waitFor(200);
        } catch (Exception e) {
            // 预期可能会有异常或特殊处理
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testInvalidArrayInput() throws Exception {
        // 测试无效的数组输入
        setTextFieldValue(kInput, "2");
        setTextFieldValue(arrayInput, "a,b,c");
        
        try {
            clickButton(initButton);
            waitFor(200);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testInvalidValueInput() throws Exception {
        // 初始化正常
        setTextFieldValue(kInput, "2");
        setTextFieldValue(arrayInput, "1,2,3");
        clickButton(initButton);
        waitFor(200);
        
        // 输入无效值
        setTextFieldValue(valueInput, "abc");
        
        try {
            clickButton(addButton);
            waitFor(200);
        } catch (Exception e) {
            // 预期可能会有异常
        }
        
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testDataStructureState() throws Exception {
        setTextFieldValue(kInput, "3");
        setTextFieldValue(arrayInput, "4,5,8,2");
        clickButton(initButton);
        waitFor(200);
        
        // 获取内部数据结构
        Object kthLargest = getPrivateField(animation, "kthLargest");
        assertNotNull("KthLargest对象应该存在", kthLargest);
        
        Integer k = (Integer) getPrivateField(animation, "k");
        assertNotNull("k值应该存在", k);
        assertEquals("k值应该正确", Integer.valueOf(3), k);
    }
    
    @Test
    public void testUIComponentsExist() throws Exception {
        assertNotNull("K输入框应该存在", kInput);
        assertNotNull("数组输入框应该存在", arrayInput);
        assertNotNull("值输入框应该存在", valueInput);
        assertNotNull("初始化按钮应该存在", initButton);
        assertNotNull("添加按钮应该存在", addButton);
        assertNotNull("重置按钮应该存在", resetButton);
        assertNotNull("状态标签应该存在", statusLabel);
        assertNotNull("结果标签应该存在", resultLabel);
    }
    
    @Test
    public void testSequentialOperations() throws Exception {
        // 测试连续操作的正确性
        setTextFieldValue(kInput, "3");
        setTextFieldValue(arrayInput, "4,5,8,2");
        clickButton(initButton);
        waitFor(200);
        
        // 按顺序添加元素并验证结果
        String[] testValues = {"3", "5", "10", "9", "4"};
        String[] expectedResults = {"4", "5", "5", "8", "8"};
        
        for (int i = 0; i < testValues.length; i++) {
            setTextFieldValue(valueInput, testValues[i]);
            clickButton(addButton);
            waitFor(200);
            
            String resultText = resultLabel.getText();
            assertTrue("第 " + (i + 1) + " 次操作结果应该正确", 
                      resultText.contains(expectedResults[i]));
        }
    }
    
    @Test
    public void testPerformanceWithLargeDataset() throws Exception {
        // 测试大数据集的性能
        setTextFieldValue(kInput, "10");
        
        // 创建较大的初始数组
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 50; i++) {
            if (i > 1) sb.append(",");
            sb.append(i);
        }
        setTextFieldValue(arrayInput, sb.toString());
        
        long startTime = System.currentTimeMillis();
        clickButton(initButton);
        waitFor(200);
        long initTime = System.currentTimeMillis() - startTime;
        
        // 添加多个元素并测量时间
        startTime = System.currentTimeMillis();
        for (int i = 51; i <= 60; i++) {
            setTextFieldValue(valueInput, String.valueOf(i));
            clickButton(addButton);
            waitFor(50);
        }
        long addTime = System.currentTimeMillis() - startTime;
        
        // 验证性能合理（这里只是基本检查，实际性能要求可能更严格）
        assertTrue("初始化时间应该合理", initTime < 5000);
        assertTrue("添加操作时间应该合理", addTime < 5000);
        
        String resultText = resultLabel.getText();
        assertTrue("最终结果应该正确", resultText.contains("51"));
    }
}