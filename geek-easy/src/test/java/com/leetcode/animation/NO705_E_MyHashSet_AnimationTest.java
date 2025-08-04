package com.leetcode.animation;

import com.leetcode.animation.datastructure.NO705_E_MyHashSet_Animation;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.*;
import java.util.Set;

/**
 * NO.705 设计哈希集合动画测试类
 * 测试MyHashSet数据结构的核心逻辑和UI交互
 * 
 * 设计文档：
 * 1. 功能需求：验证MyHashSet数据结构的正确性和动画交互
 * 2. 测试范围：集合操作、边界条件、UI组件、性能验证
 * 3. 测试策略：添加、删除、查找、重复操作、边界值
 * 4. 边界条件：空集合、大量元素、重复元素、边界值
 * 5. 性能验证：基本操作时间复杂度O(1)平均情况
 */
public class NO705_E_MyHashSet_AnimationTest extends BaseAnimationTest {
    
    private NO705_E_MyHashSet_Animation animation;
    private JTextField valueInput;
    private JButton addButton;
    private JButton removeButton;
    private JButton containsButton;
    private JButton resetButton;
    private JLabel statusLabel;
    private JLabel resultLabel;
    
    @Override
    protected void setupSwingComponents() {
        animation = new NO705_E_MyHashSet_Animation();
        testFrame = animation;
        
        try {
            valueInput = (JTextField) getPrivateField(animation, "valueInput");
            addButton = (JButton) getPrivateField(animation, "addButton");
            removeButton = (JButton) getPrivateField(animation, "removeButton");
            containsButton = (JButton) getPrivateField(animation, "containsButton");
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
        assertTrue("添加按钮应该启用", isComponentEnabled(addButton));
        assertTrue("删除按钮应该启用", isComponentEnabled(removeButton));
        assertTrue("查找按钮应该启用", isComponentEnabled(containsButton));
        assertTrue("重置按钮应该启用", isComponentEnabled(resetButton));
        
        // 测试默认输入值
        assertEquals("默认值输入", "1", getTextFieldValue(valueInput));
    }
    
    @Test
    public void testBasicAddOperation() throws Exception {
        setTextFieldValue(valueInput, "5");
        clickButton(addButton);
        waitFor(200);
        
        String statusText = statusLabel.getText();
        assertTrue("应该显示添加操作", statusText.contains("添加") || statusText.contains("add"));
    }
    
    @Test
    public void testBasicContainsOperation() throws Exception {
        // 先添加元素
        setTextFieldValue(valueInput, "5");
        clickButton(addButton);
        waitFor(200);
        
        // 然后查找
        setTextFieldValue(valueInput, "5");
        clickButton(containsButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("应该找到元素", resultText.contains("true") || resultText.contains("存在"));
    }
    
    @Test
    public void testContainsNonExistentElement() throws Exception {
        // 查找不存在的元素
        setTextFieldValue(valueInput, "999");
        clickButton(containsButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("应该显示元素不存在", resultText.contains("false") || resultText.contains("不存在"));
    }
    
    @Test
    public void testBasicRemoveOperation() throws Exception {
        // 先添加元素
        setTextFieldValue(valueInput, "5");
        clickButton(addButton);
        waitFor(200);
        
        // 然后删除
        setTextFieldValue(valueInput, "5");
        clickButton(removeButton);
        waitFor(200);
        
        String statusText = statusLabel.getText();
        assertTrue("应该显示删除操作", statusText.contains("删除") || statusText.contains("remove"));
        
        // 验证删除后不存在
        clickButton(containsButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("删除后应该不存在", resultText.contains("false") || resultText.contains("不存在"));
    }
    
    @Test
    public void testRemoveNonExistentElement() throws Exception {
        // 删除不存在的元素
        setTextFieldValue(valueInput, "999");
        clickButton(removeButton);
        waitFor(200);
        
        // 操作应该成功完成，不会报错
        assertTrue("程序应该仍然可用", isComponentVisible(animation));
    }
    
    @Test
    public void testDuplicateAdd() throws Exception {
        // 添加相同元素两次
        setTextFieldValue(valueInput, "5");
        clickButton(addButton);
        waitFor(200);
        
        clickButton(addButton); // 再次添加相同元素
        waitFor(200);
        
        // 验证元素仍然存在（集合不允许重复）
        clickButton(containsButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("重复添加后元素应该仍然存在", resultText.contains("true") || resultText.contains("存在"));
    }
    
    @Test
    public void testMultipleOperations() throws Exception {
        // 测试多个操作的组合
        int[] testValues = {1, 2, 3, 4, 5};
        
        // 添加多个元素
        for (int value : testValues) {
            setTextFieldValue(valueInput, String.valueOf(value));
            clickButton(addButton);
            waitFor(100);
        }
        
        // 验证所有元素都存在
        for (int value : testValues) {
            setTextFieldValue(valueInput, String.valueOf(value));
            clickButton(containsButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("元素 " + value + " 应该存在", 
                      resultText.contains("true") || resultText.contains("存在"));
        }
        
        // 删除部分元素
        for (int i = 0; i < testValues.length; i += 2) {
            setTextFieldValue(valueInput, String.valueOf(testValues[i]));
            clickButton(removeButton);
            waitFor(100);
        }
        
        // 验证删除的元素不存在，未删除的元素存在
        for (int i = 0; i < testValues.length; i++) {
            setTextFieldValue(valueInput, String.valueOf(testValues[i]));
            clickButton(containsButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            if (i % 2 == 0) {
                assertTrue("删除的元素 " + testValues[i] + " 应该不存在", 
                          resultText.contains("false") || resultText.contains("不存在"));
            } else {
                assertTrue("未删除的元素 " + testValues[i] + " 应该存在", 
                          resultText.contains("true") || resultText.contains("存在"));
            }
        }
    }
    
    @Test
    public void testBoundaryValues() throws Exception {
        // 测试边界值
        int[] boundaryValues = {0, 1, Integer.MAX_VALUE, Integer.MIN_VALUE};
        
        for (int value : boundaryValues) {
            setTextFieldValue(valueInput, String.valueOf(value));
            clickButton(addButton);
            waitFor(100);
            
            clickButton(containsButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("边界值 " + value + " 应该正确处理", 
                      resultText.contains("true") || resultText.contains("存在"));
        }
    }
    
    @Test
    public void testNegativeNumbers() throws Exception {
        // 测试负数
        int[] negativeValues = {-1, -100, -999};
        
        for (int value : negativeValues) {
            setTextFieldValue(valueInput, String.valueOf(value));
            clickButton(addButton);
            waitFor(100);
            
            clickButton(containsButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("负数 " + value + " 应该正确处理", 
                      resultText.contains("true") || resultText.contains("存在"));
        }
    }
    
    @Test
    public void testLargeNumbers() throws Exception {
        // 测试大数
        int[] largeValues = {1000000, 999999, 1000001};
        
        for (int value : largeValues) {
            setTextFieldValue(valueInput, String.valueOf(value));
            clickButton(addButton);
            waitFor(100);
            
            clickButton(containsButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("大数 " + value + " 应该正确处理", 
                      resultText.contains("true") || resultText.contains("存在"));
        }
    }
    
    @Test
    public void testResetFunctionality() throws Exception {
        // 添加一些元素
        for (int i = 1; i <= 5; i++) {
            setTextFieldValue(valueInput, String.valueOf(i));
            clickButton(addButton);
            waitFor(100);
        }
        
        // 重置
        clickButton(resetButton);
        waitFor(200);
        
        // 验证重置后元素不存在
        setTextFieldValue(valueInput, "1");
        clickButton(containsButton);
        waitFor(200);
        
        String resultText = resultLabel.getText();
        assertTrue("重置后元素应该不存在", resultText.contains("false") || resultText.contains("不存在"));
        
        // 验证UI状态
        assertTrue("重置后所有按钮应该可用", isComponentEnabled(addButton));
        assertEquals("重置后状态标签应该清空", "", statusLabel.getText());
    }
    
    @Test
    public void testInvalidInput() throws Exception {
        // 测试无效输入
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
    public void testEmptyInput() throws Exception {
        // 测试空输入
        setTextFieldValue(valueInput, "");
        
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
        setTextFieldValue(valueInput, "5");
        clickButton(addButton);
        waitFor(200);
        
        // 获取内部数据结构
        Object myHashSet = getPrivateField(animation, "myHashSet");
        assertNotNull("MyHashSet对象应该存在", myHashSet);
    }
    
    @Test
    public void testUIComponentsExist() throws Exception {
        assertNotNull("值输入框应该存在", valueInput);
        assertNotNull("添加按钮应该存在", addButton);
        assertNotNull("删除按钮应该存在", removeButton);
        assertNotNull("查找按钮应该存在", containsButton);
        assertNotNull("重置按钮应该存在", resetButton);
        assertNotNull("状态标签应该存在", statusLabel);
        assertNotNull("结果标签应该存在", resultLabel);
    }
    
    @Test
    public void testSequentialOperations() throws Exception {
        // 测试一系列操作的正确性
        
        // 1. 添加元素1
        setTextFieldValue(valueInput, "1");
        clickButton(addButton);
        waitFor(100);
        
        // 2. 查找元素1（应该存在）
        clickButton(containsButton);
        waitFor(100);
        String result1 = resultLabel.getText();
        assertTrue("添加后应该能找到元素1", result1.contains("true") || result1.contains("存在"));
        
        // 3. 添加元素2
        setTextFieldValue(valueInput, "2");
        clickButton(addButton);
        waitFor(100);
        
        // 4. 查找元素2（应该存在）
        clickButton(containsButton);
        waitFor(100);
        String result2 = resultLabel.getText();
        assertTrue("添加后应该能找到元素2", result2.contains("true") || result2.contains("存在"));
        
        // 5. 删除元素1
        setTextFieldValue(valueInput, "1");
        clickButton(removeButton);
        waitFor(100);
        
        // 6. 查找元素1（应该不存在）
        clickButton(containsButton);
        waitFor(100);
        String result3 = resultLabel.getText();
        assertTrue("删除后应该找不到元素1", result3.contains("false") || result3.contains("不存在"));
        
        // 7. 查找元素2（应该仍然存在）
        setTextFieldValue(valueInput, "2");
        clickButton(containsButton);
        waitFor(100);
        String result4 = resultLabel.getText();
        assertTrue("删除元素1后元素2应该仍然存在", result4.contains("true") || result4.contains("存在"));
    }
    
    @Test
    public void testPerformanceWithManyElements() throws Exception {
        // 测试大量元素的性能
        int numElements = 100;
        
        long startTime = System.currentTimeMillis();
        
        // 添加大量元素
        for (int i = 0; i < numElements; i++) {
            setTextFieldValue(valueInput, String.valueOf(i));
            clickButton(addButton);
            waitFor(10); // 减少等待时间以加快测试
        }
        
        long addTime = System.currentTimeMillis() - startTime;
        
        // 查找所有元素
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            setTextFieldValue(valueInput, String.valueOf(i));
            clickButton(containsButton);
            waitFor(10);
            
            String resultText = resultLabel.getText();
            assertTrue("元素 " + i + " 应该存在", 
                      resultText.contains("true") || resultText.contains("存在"));
        }
        long searchTime = System.currentTimeMillis() - startTime;
        
        // 删除所有元素
        startTime = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            setTextFieldValue(valueInput, String.valueOf(i));
            clickButton(removeButton);
            waitFor(10);
        }
        long removeTime = System.currentTimeMillis() - startTime;
        
        // 验证性能合理（这里只是基本检查）
        assertTrue("添加操作时间应该合理", addTime < 30000);
        assertTrue("查找操作时间应该合理", searchTime < 30000);
        assertTrue("删除操作时间应该合理", removeTime < 30000);
        
        // 验证所有元素都被删除
        setTextFieldValue(valueInput, "0");
        clickButton(containsButton);
        waitFor(100);
        String resultText = resultLabel.getText();
        assertTrue("删除后元素应该不存在", resultText.contains("false") || resultText.contains("不存在"));
    }
    
    @Test
    public void testHashCollisionHandling() throws Exception {
        // 测试可能产生哈希冲突的值
        // 这些值在简单的哈希函数下可能产生冲突
        int[] collisionValues = {1, 1001, 2001, 3001}; // 假设使用模1000的哈希函数
        
        // 添加所有值
        for (int value : collisionValues) {
            setTextFieldValue(valueInput, String.valueOf(value));
            clickButton(addButton);
            waitFor(100);
        }
        
        // 验证所有值都能正确查找
        for (int value : collisionValues) {
            setTextFieldValue(valueInput, String.valueOf(value));
            clickButton(containsButton);
            waitFor(100);
            
            String resultText = resultLabel.getText();
            assertTrue("冲突值 " + value + " 应该正确处理", 
                      resultText.contains("true") || resultText.contains("存在"));
        }
        
        // 删除部分值
        setTextFieldValue(valueInput, "1");
        clickButton(removeButton);
        waitFor(100);
        
        setTextFieldValue(valueInput, "2001");
        clickButton(removeButton);
        waitFor(100);
        
        // 验证删除的值不存在，未删除的值存在
        setTextFieldValue(valueInput, "1");
        clickButton(containsButton);
        waitFor(100);
        String result1 = resultLabel.getText();
        assertTrue("删除的值1应该不存在", result1.contains("false") || result1.contains("不存在"));
        
        setTextFieldValue(valueInput, "1001");
        clickButton(containsButton);
        waitFor(100);
        String result2 = resultLabel.getText();
        assertTrue("未删除的值1001应该存在", result2.contains("true") || result2.contains("存在"));
    }
}