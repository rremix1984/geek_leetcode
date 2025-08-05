package com.animation.atomics;

import com.animation.Animation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Random;

/**
 * AtomicReference 原子引用动画演示
 * 演示CAS操作和对象引用的原子性更新
 */
public class AtomicReferenceAnimation extends JFrame implements Animation {
    
    // 用户类
    static class User {
        private String name;
        private int age;
        
        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() { return name; }
        public int getAge() { return age; }
        
        @Override
        public String toString() {
            return name + "(" + age + "岁)";
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            User user = (User) obj;
            return age == user.age && name.equals(user.name);
        }
    }
    
    private AtomicReference<User> atomicUserRef;
    private JLabel currentUserLabel;
    private JLabel expectedUserLabel;
    private JLabel newUserLabel;
    private JButton casButton;
    private JButton setButton;
    private JButton generateUsersButton;
    private JTextArea logArea;
    private JComboBox<User> expectedUserCombo;
    private JComboBox<User> newUserCombo;
    
    private User[] predefinedUsers = {
        new User("张三", 25),
        new User("李四", 30),
        new User("王五", 28),
        new User("赵六", 35),
        new User("钱七", 22)
    };
    
    public AtomicReferenceAnimation() {
        initComponents();
        setupLayout();
        setupEventHandlers();
        initializeData();
    }
    
    private void initComponents() {
        setTitle("AtomicReference 原子引用动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        atomicUserRef = new AtomicReference<>(predefinedUsers[0]);
        
        currentUserLabel = new JLabel();
        currentUserLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
        currentUserLabel.setForeground(Color.BLUE);
        currentUserLabel.setBorder(BorderFactory.createTitledBorder("当前用户"));
        
        expectedUserLabel = new JLabel("期望用户:");
        newUserLabel = new JLabel("新用户:");
        
        expectedUserCombo = new JComboBox<>(predefinedUsers);
        newUserCombo = new JComboBox<>(predefinedUsers);
        newUserCombo.setSelectedIndex(1);
        
        casButton = new JButton("执行CAS操作");
        setButton = new JButton("直接设置");
        generateUsersButton = new JButton("随机生成用户");
        
        logArea = new JTextArea(15, 40);
        logArea.setEditable(false);
        logArea.setFont(new Font("Consolas", Font.PLAIN, 12));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // 顶部面板 - 当前状态
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("AtomicReference 状态"));
        topPanel.add(currentUserLabel, BorderLayout.CENTER);
        
        // 中间面板 - 操作控制
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("CAS 操作控制"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        middlePanel.add(expectedUserLabel, gbc);
        gbc.gridx = 1;
        middlePanel.add(expectedUserCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        middlePanel.add(newUserLabel, gbc);
        gbc.gridx = 1;
        middlePanel.add(newUserCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(casButton);
        buttonPanel.add(setButton);
        buttonPanel.add(generateUsersButton);
        middlePanel.add(buttonPanel, gbc);
        
        // 底部面板 - 日志
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("操作日志"));
        bottomPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventHandlers() {
        casButton.addActionListener(e -> performCAS());
        setButton.addActionListener(e -> performDirectSet());
        generateUsersButton.addActionListener(e -> generateRandomUsers());
        
        expectedUserCombo.addActionListener(e -> updateExpectedUser());
    }
    
    private void initializeData() {
        updateCurrentUserDisplay();
        logArea.append("AtomicReference 原子引用演示初始化完成\n");
        logArea.append("当前用户: " + atomicUserRef.get() + "\n\n");
        logArea.append("CAS操作说明:\n");
        logArea.append("- 比较当前值与期望值\n");
        logArea.append("- 如果相等，则更新为新值\n");
        logArea.append("- 如果不等，则操作失败\n\n");
    }
    
    private void updateCurrentUserDisplay() {
        User currentUser = atomicUserRef.get();
        currentUserLabel.setText("  " + currentUser.toString() + "  ");
        currentUserLabel.setOpaque(true);
        currentUserLabel.setBackground(new Color(230, 240, 255));
    }
    
    private void updateExpectedUser() {
        // 当期望用户改变时，更新显示
        repaint();
    }
    
    private void performCAS() {
        User expectedUser = (User) expectedUserCombo.getSelectedItem();
        User newUser = (User) newUserCombo.getSelectedItem();
        User currentUser = atomicUserRef.get();
        
        logArea.append("执行CAS操作:\n");
        logArea.append("  当前值: " + currentUser + "\n");
        logArea.append("  期望值: " + expectedUser + "\n");
        logArea.append("  新值: " + newUser + "\n");
        
        boolean success = atomicUserRef.compareAndSet(expectedUser, newUser);
        
        if (success) {
            logArea.append("  结果: CAS操作成功！\n");
            logArea.append("  原因: 当前值等于期望值\n");
            updateCurrentUserDisplay();
            
            // 动画效果
            animateSuccess();
        } else {
            logArea.append("  结果: CAS操作失败！\n");
            logArea.append("  原因: 当前值不等于期望值\n");
            
            // 动画效果
            animateFailure();
        }
        
        logArea.append("  操作后当前值: " + atomicUserRef.get() + "\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void performDirectSet() {
        User newUser = (User) newUserCombo.getSelectedItem();
        User oldUser = atomicUserRef.get();
        
        atomicUserRef.set(newUser);
        
        logArea.append("执行直接设置操作:\n");
        logArea.append("  原值: " + oldUser + "\n");
        logArea.append("  新值: " + newUser + "\n");
        logArea.append("  结果: 设置成功\n\n");
        
        updateCurrentUserDisplay();
        animateDirectSet();
        
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void generateRandomUsers() {
        Random random = new Random();
        String[] names = {"小明", "小红", "小刚", "小丽", "小华", "小强", "小美", "小军"};
        
        for (int i = 0; i < predefinedUsers.length; i++) {
            String name = names[random.nextInt(names.length)];
            int age = 20 + random.nextInt(20);
            predefinedUsers[i] = new User(name, age);
        }
        
        expectedUserCombo.removeAllItems();
        newUserCombo.removeAllItems();
        
        for (User user : predefinedUsers) {
            expectedUserCombo.addItem(user);
            newUserCombo.addItem(user);
        }
        
        logArea.append("已生成新的随机用户列表\n\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void animateSuccess() {
        Timer timer = new Timer(100, null);
        final int[] count = {0};
        
        timer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                currentUserLabel.setBackground(Color.GREEN);
            } else {
                currentUserLabel.setBackground(new Color(230, 240, 255));
            }
            count[0]++;
            
            if (count[0] >= 6) {
                timer.stop();
                currentUserLabel.setBackground(new Color(230, 240, 255));
            }
        });
        
        timer.start();
    }
    
    private void animateFailure() {
        Timer timer = new Timer(100, null);
        final int[] count = {0};
        
        timer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                currentUserLabel.setBackground(Color.RED);
            } else {
                currentUserLabel.setBackground(new Color(230, 240, 255));
            }
            count[0]++;
            
            if (count[0] >= 6) {
                timer.stop();
                currentUserLabel.setBackground(new Color(230, 240, 255));
            }
        });
        
        timer.start();
    }
    
    private void animateDirectSet() {
        Timer timer = new Timer(100, null);
        final int[] count = {0};
        
        timer.addActionListener(e -> {
            if (count[0] % 2 == 0) {
                currentUserLabel.setBackground(Color.YELLOW);
            } else {
                currentUserLabel.setBackground(new Color(230, 240, 255));
            }
            count[0]++;
            
            if (count[0] >= 4) {
                timer.stop();
                currentUserLabel.setBackground(new Color(230, 240, 255));
            }
        });
        
        timer.start();
    }
    
    @Override
    public void start() {
        setVisible(true);
    }
    
    public void startAnimation() {
        setVisible(true);
    }
    
    public void stopAnimation() {
        dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AtomicReferenceAnimation().startAnimation();
        });
    }
}