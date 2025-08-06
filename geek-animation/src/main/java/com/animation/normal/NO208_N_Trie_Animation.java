package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * NO208 实现 Trie (前缀树) 动画演示
 * 
 * 题目描述：
 * Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，
 * 用于高效地存储和检索字符串数据集中的键。
 * 
 * @author AI Assistant
 */
public class NO208_N_Trie_Animation extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // Trie节点类
    private static class TrieNode {
        TrieNode[] children;
        boolean isEnd;
        
        public TrieNode() {
            children = new TrieNode[26]; // a-z
            isEnd = false;
        }
    }
    
    // Trie类
    private static class Trie {
        private TrieNode root;
        
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    node.children[index] = new TrieNode();
                }
                node = node.children[index];
            }
            node.isEnd = true;
        }
        
        public boolean search(String word) {
            TrieNode node = searchPrefix(word);
            return node != null && node.isEnd;
        }
        
        public boolean startsWith(String prefix) {
            return searchPrefix(prefix) != null;
        }
        
        private TrieNode searchPrefix(String prefix) {
            TrieNode node = root;
            for (char c : prefix.toCharArray()) {
                int index = c - 'a';
                if (node.children[index] == null) {
                    return null;
                }
                node = node.children[index];
            }
            return node;
        }
    }
    
    // UI组件
    private JTextField inputField;
    private JButton insertButton;
    private JButton searchButton;
    private JButton startsWithButton;
    private JButton clearButton;
    private JTextArea logArea;
    private JPanel visualPanel;
    
    // 数据
    private Trie trie;
    private List<String> insertedWords;
    
    public NO208_N_Trie_Animation() {
        trie = new Trie();
        insertedWords = new ArrayList<>();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("NO208 - Trie (前缀树) 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // 中央可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTrie(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(600, 400));
        add(visualPanel, BorderLayout.CENTER);
        
        // 底部日志面板
        logArea = new JTextArea(8, 50);
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("操作日志"));
        add(scrollPane, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
        
        // 添加示例数据
        addLog("Trie (前缀树) 初始化完成");
        addLog("可以进行插入、搜索、前缀匹配操作");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("操作控制"));
        
        panel.add(new JLabel("输入单词:"));
        
        inputField = new JTextField(15);
        panel.add(inputField);
        
        insertButton = new JButton("插入");
        insertButton.addActionListener(e -> insertWord());
        panel.add(insertButton);
        
        searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> searchWord());
        panel.add(searchButton);
        
        startsWithButton = new JButton("前缀匹配");
        startsWithButton.addActionListener(e -> checkPrefix());
        panel.add(startsWithButton);
        
        clearButton = new JButton("清空");
        clearButton.addActionListener(e -> clearTrie());
        panel.add(clearButton);
        
        // 预设按钮
        JButton demoButton = new JButton("演示数据");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        return panel;
    }
    
    private void insertWord() {
        String word = inputField.getText().trim().toLowerCase();
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入单词", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!word.matches("[a-z]+")) {
            JOptionPane.showMessageDialog(this, "只支持小写字母", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        trie.insert(word);
        insertedWords.add(word);
        addLog("插入单词: " + word);
        inputField.setText("");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void searchWord() {
        String word = inputField.getText().trim().toLowerCase();
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入要搜索的单词", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean found = trie.search(word);
        addLog("搜索单词 '" + word + "': " + (found ? "找到" : "未找到"));
        
        if (found) {
            JOptionPane.showMessageDialog(this, "找到单词: " + word, "搜索结果", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "未找到单词: " + word, "搜索结果", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void checkPrefix() {
        String prefix = inputField.getText().trim().toLowerCase();
        if (prefix.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入前缀", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean hasPrefix = trie.startsWith(prefix);
        addLog("前缀匹配 '" + prefix + "': " + (hasPrefix ? "存在" : "不存在"));
        
        if (hasPrefix) {
            // 找出所有以该前缀开头的单词
            List<String> matchedWords = new ArrayList<>();
            for (String word : insertedWords) {
                if (word.startsWith(prefix)) {
                    matchedWords.add(word);
                }
            }
            JOptionPane.showMessageDialog(this, 
                "前缀 '" + prefix + "' 匹配的单词: " + matchedWords, 
                "前缀匹配结果", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "前缀 '" + prefix + "' 不存在", "前缀匹配结果", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void clearTrie() {
        trie = new Trie();
        insertedWords.clear();
        addLog("Trie 已清空");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void loadDemoData() {
        String[] demoWords = {"apple", "app", "application", "apply", "cat", "car", "card", "care"};
        
        for (String word : demoWords) {
            trie.insert(word);
            insertedWords.add(word);
        }
        
        addLog("加载演示数据: " + String.join(", ", demoWords));
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void drawTrie(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (insertedWords.isEmpty()) {
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            g2d.setColor(Color.GRAY);
            String message = "Trie 为空，请插入单词";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (visualPanel.getWidth() - fm.stringWidth(message)) / 2;
            int y = visualPanel.getHeight() / 2;
            g2d.drawString(message, x, y);
            return;
        }
        
        // 绘制已插入的单词列表
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g2d.setColor(Color.BLACK);
        g2d.drawString("已插入的单词:", 20, 30);
        
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        int y = 50;
        for (int i = 0; i < insertedWords.size(); i++) {
            String word = insertedWords.get(i);
            g2d.setColor(new Color(0, 100, 200));
            g2d.drawString((i + 1) + ". " + word, 30, y);
            y += 20;
            
            if (y > visualPanel.getHeight() - 50) {
                g2d.setColor(Color.GRAY);
                g2d.drawString("... (还有 " + (insertedWords.size() - i - 1) + " 个单词)", 30, y);
                break;
            }
        }
        
        // 绘制Trie结构示意图
        drawTrieStructure(g2d);
    }
    
    private void drawTrieStructure(Graphics2D g2d) {
        int startX = visualPanel.getWidth() / 2;
        int startY = 80;
        
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Trie 结构示意:", startX - 50, 50);
        
        // 绘制根节点
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(startX - 15, startY - 15, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(startX - 15, startY - 15, 30, 30);
        g2d.drawString("root", startX - 12, startY + 5);
        
        // 绘制一些分支示例
        if (!insertedWords.isEmpty()) {
            // 获取第一个字母的分支
            java.util.Set<Character> firstChars = new java.util.HashSet<>();
            for (String word : insertedWords) {
                if (!word.isEmpty()) {
                    firstChars.add(word.charAt(0));
                }
            }
            
            int branchCount = Math.min(firstChars.size(), 5);
            int branchSpacing = 80;
            int branchStartX = startX - (branchCount - 1) * branchSpacing / 2;
            
            int i = 0;
            for (Character c : firstChars) {
                if (i >= 5) break;
                
                int branchX = branchStartX + i * branchSpacing;
                int branchY = startY + 60;
                
                // 绘制连线
                g2d.setColor(Color.GRAY);
                g2d.drawLine(startX, startY + 15, branchX, branchY - 15);
                
                // 绘制子节点
                g2d.setColor(new Color(173, 216, 230));
                g2d.fillOval(branchX - 12, branchY - 12, 24, 24);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(branchX - 12, branchY - 12, 24, 24);
                g2d.drawString(String.valueOf(c), branchX - 4, branchY + 4);
                
                i++;
            }
        }
    }
    
    private void addLog(String message) {
        logArea.append("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new NO208_N_Trie_Animation().setVisible(true);
        });
    }
}