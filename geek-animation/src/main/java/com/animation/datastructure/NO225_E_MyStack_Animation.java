package com.animation.datastructure;

import javax.swing.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.*;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionEvent;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.awt.event.ActionListener;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.LinkedList;
import com.animation.launcher.AlgorithmTreeLauncher;
import java.util.Queue;
import com.animation.launcher.AlgorithmTreeLauncher;

/**
 * NO.225 用队列实现栈 - 动画演示
 * 演示用两个队列实现栈的过程
 */
public class NO225_E_MyStack_Animation extends JFrame {
    private MyStackVisual stack;
    private JPanel mainPanel;
    private JPanel controlPanel;
    private JTextField inputField;
    private JButton pushButton, popButton, topButton, emptyButton, clearButton;
    private JLabel resultLabel;
    private JLabel queue1Label, queue2Label;
    
    public NO225_E_MyStack_Animation() {
        setTitle("NO.225 用队列实现栈 - 动画演示");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        stack = new MyStackVisual();
        initComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initComponents() {
        mainPanel = new JPanel();
        controlPanel = new JPanel();
        
        inputField = new JTextField(10);
        pushButton = new JButton("Push");
        popButton = new JButton("Pop");
        topButton = new JButton("Top");
        emptyButton = new JButton("Empty");
        clearButton = new JButton("Clear");
        resultLabel = new JLabel("结果: ");
        queue1Label = new JLabel("Queue1 (主队列): []");
        queue2Label = new JLabel("Queue2 (辅助队列): []");
        
        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 14);
        resultLabel.setFont(font);
        queue1Label.setFont(font);
        queue2Label.setFont(font);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 主面板
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new StackVisualizationPanel(), BorderLayout.CENTER);
        
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.add(queue1Label);
        labelPanel.add(queue2Label);
        mainPanel.add(labelPanel, BorderLayout.NORTH);
        
        // 返回首页按钮
        JButton homeButton = new JButton("返回首页");
        homeButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            // 启动主界面
            SwingUtilities.invokeLater(() -> {
                 try {
                     dispose(); // 关闭当前动画窗口
                     com.animation.launcher.AlgorithmTreeLauncher.showMainWindow();
                 } catch (Exception ex) {
                     ex.printStackTrace();
                 }
             });
        });

        // 控制面板
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("输入值:"));
        controlPanel.add(inputField);
        controlPanel.add(pushButton);
        controlPanel.add(popButton);
        controlPanel.add(topButton);
        controlPanel.add(emptyButton);
        controlPanel.add(clearButton);
        controlPanel.add(homeButton);
        controlPanel.add(resultLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    stack.push(value);
                    updateDisplay();
                    resultLabel.setText("结果: Push " + value + " 成功");
                    inputField.setText("");
                } catch (NumberFormatException ex) {
                    resultLabel.setText("错误: 请输入有效数字");
                }
            }
        });
        
        popButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stack.empty()) {
                    int value = stack.pop();
                    updateDisplay();
                    resultLabel.setText("结果: Pop " + value);
                } else {
                    resultLabel.setText("错误: 栈为空");
                }
            }
        });
        
        topButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!stack.empty()) {
                    int value = stack.top();
                    resultLabel.setText("结果: Top " + value);
                } else {
                    resultLabel.setText("错误: 栈为空");
                }
            }
        });
        
        emptyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isEmpty = stack.empty();
                resultLabel.setText("结果: 栈" + (isEmpty ? "为空" : "不为空"));
            }
        });
        
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stack.clear();
                updateDisplay();
                resultLabel.setText("结果: 栈已清空");
            }
        });
    }
    
    private void updateDisplay() {
        queue1Label.setText("Queue1 (主队列): " + stack.getQueue1Display());
        queue2Label.setText("Queue2 (辅助队列): " + stack.getQueue2Display());
        repaint();
    }
    
    // 栈的可视化面板
    private class StackVisualizationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawQueue(g2d, stack.queue1, "Queue1 (主队列)", 50, 50, Color.BLUE);
            drawQueue(g2d, stack.queue2, "Queue2 (辅助队列)", 50, 250, Color.RED);
            
            // 绘制栈的概念图
            drawStackConcept(g2d, 450, 50);
        }
        
        private void drawQueue(Graphics2D g2d, Queue<Integer> queue, String title, int x, int y, Color color) {
            g2d.setColor(color);
            g2d.drawString(title, x, y - 10);
            
            int index = 0;
            for (Integer value : queue) {
                Rectangle rect = new Rectangle(x + index * 50, y, 40, 30);
                g2d.setColor(color.brighter());
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                g2d.drawString(value.toString(), x + index * 50 + 15, y + 20);
                index++;
            }
            
            if (queue.isEmpty()) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("Empty", x, y + 20);
            }
        }
        
        private void drawStackConcept(Graphics2D g2d, int x, int y) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("栈的概念视图:", x, y - 10);
            
            // 绘制栈
            int stackHeight = 200;
            int stackWidth = 60;
            g2d.drawRect(x, y, stackWidth, stackHeight);
            
            // 绘制栈中的元素
            int elementHeight = 30;
            int elementCount = 0;
            for (Integer value : stack.queue1) {
                int elementY = y + stackHeight - (elementCount + 1) * elementHeight;
                Rectangle rect = new Rectangle(x, elementY, stackWidth, elementHeight);
                g2d.setColor(Color.CYAN);
                g2d.fill(rect);
                g2d.setColor(Color.BLACK);
                g2d.draw(rect);
                g2d.drawString(value.toString(), x + 20, elementY + 20);
                elementCount++;
            }
            
            // 绘制栈顶指针
            if (!stack.empty()) {
                g2d.setColor(Color.RED);
                g2d.drawString("TOP", x + 70, y + stackHeight - elementCount * elementHeight + 20);
                g2d.drawLine(x + 60, y + stackHeight - elementCount * elementHeight + 15,
                           x + 70, y + stackHeight - elementCount * elementHeight + 15);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new NO225_E_MyStack_Animation().setVisible(true);
            }
        });
    }
}

// 可视化的栈实现
class MyStackVisual {
    Queue<Integer> queue1, queue2;

    public MyStackVisual() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    public void push(int x) {
        queue2.offer(x);
        while (!queue1.isEmpty())
            queue2.offer(queue1.poll());

        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;
    }

    public int pop() {
        return queue1.poll();
    }

    public int top() {
        return queue1.peek();
    }

    public boolean empty() {
        return queue1.isEmpty();
    }
    
    public void clear() {
        queue1.clear();
        queue2.clear();
    }
    
    public String getQueue1Display() {
        return queue1.toString();
    }
    
    public String getQueue2Display() {
        return queue2.toString();
    }
}
