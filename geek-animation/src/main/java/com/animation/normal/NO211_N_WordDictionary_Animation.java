package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * NO211 添加与搜索单词 - 数据结构设计 动画演示
 * 
 * 题目描述：
 * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配。
 * 实现词典类 WordDictionary，支持通配符 '.' 的搜索。
 * 
 * @author AI Assistant
 */
public class NO211_N_WordDictionary_Animation extends JFrame {
    
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
    
    // WordDictionary类
    private static class WordDictionary {
        private TrieNode root;
        
        public WordDictionary() {
            root = new TrieNode();
        }
        
        public void addWord(String word) {
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
            return searchHelper(word, 0, root);
        }
        
        private boolean searchHelper(String word, int index, TrieNode node) {
            if (index == word.length()) {
                return node.isEnd;
            }
            
            char c = word.charAt(index);
            if (c == '.') {
                // 通配符，尝试所有可能的字符
                for (TrieNode child : node.children) {
                    if (child != null && searchHelper(word, index + 1, child)) {
                        return true;
                    }
                }
                return false;
            } else {
                // 普通字符
                int charIndex = c - 'a';
                TrieNode child = node.children[charIndex];
                if (child == null) {
                    return false;
                }
                return searchHelper(word, index + 1, child);
            }
        }
    }
    
    // UI组件
    private JTextField inputField;
    private JButton addButton;
    private JButton searchButton;
    private JButton clearButton;
    private JTextArea logArea;
    private JPanel visualPanel;
    private JList<String> wordList;
    private DefaultListModel<String> listModel;
    
    // 数据
    private WordDictionary wordDict;
    private List<String> addedWords;
    private String currentSearchWord;
    private boolean lastSearchResult;
    
    public NO211_N_WordDictionary_Animation() {
        wordDict = new WordDictionary();
        addedWords = new ArrayList<>();
        listModel = new DefaultListModel<>();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("NO211 - 添加与搜索单词 (支持通配符) 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // 中央面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // 左侧单词列表
        JPanel leftPanel = createWordListPanel();
        centerPanel.add(leftPanel, BorderLayout.WEST);
        
        // 右侧可视化面板
        visualPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawVisualization(g);
            }
        };
        visualPanel.setBackground(Color.WHITE);
        visualPanel.setPreferredSize(new Dimension(500, 400));
        centerPanel.add(visualPanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
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
        addLog("WordDictionary 初始化完成");
        addLog("支持添加单词和通配符搜索 (使用 '.' 作为通配符)");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("操作控制"));
        
        panel.add(new JLabel("输入单词/搜索模式:"));
        
        inputField = new JTextField(15);
        panel.add(inputField);
        
        addButton = new JButton("添加单词");
        addButton.addActionListener(e -> addWord());
        panel.add(addButton);
        
        searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> searchWord());
        panel.add(searchButton);
        
        clearButton = new JButton("清空");
        clearButton.addActionListener(e -> clearDictionary());
        panel.add(clearButton);
        
        // 预设按钮
        JButton demoButton = new JButton("演示数据");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        // 通配符示例按钮
        JButton wildcardButton = new JButton("通配符示例");
        wildcardButton.addActionListener(e -> showWildcardExamples());
        panel.add(wildcardButton);
        
        return panel;
    }
    
    private JPanel createWordListPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("已添加的单词"));
        panel.setPreferredSize(new Dimension(200, 400));
        
        wordList = new JList<>(listModel);
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(wordList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void addWord() {
        String word = inputField.getText().trim().toLowerCase();
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入单词", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!word.matches("[a-z]+")) {
            JOptionPane.showMessageDialog(this, "只支持小写字母", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (addedWords.contains(word)) {
            JOptionPane.showMessageDialog(this, "单词已存在", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        wordDict.addWord(word);
        addedWords.add(word);
        listModel.addElement(word);
        addLog("添加单词: " + word);
        inputField.setText("");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void searchWord() {
        String pattern = inputField.getText().trim().toLowerCase();
        if (pattern.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入搜索模式", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!pattern.matches("[a-z.]+")) {
            JOptionPane.showMessageDialog(this, "只支持小写字母和通配符 '.'", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        currentSearchWord = pattern;
        lastSearchResult = wordDict.search(pattern);
        
        String wildcardInfo = pattern.contains(".") ? " (包含通配符)" : "";
        addLog("搜索模式 '" + pattern + "'" + wildcardInfo + ": " + (lastSearchResult ? "匹配" : "不匹配"));
        
        if (lastSearchResult) {
            // 找出所有匹配的单词
            List<String> matchedWords = findMatchingWords(pattern);
            String message = "搜索模式 '" + pattern + "' 匹配成功!\n匹配的单词: " + matchedWords;
            JOptionPane.showMessageDialog(this, message, "搜索结果", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "搜索模式 '" + pattern + "' 没有匹配的单词", "搜索结果", JOptionPane.WARNING_MESSAGE);
        }
        
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private List<String> findMatchingWords(String pattern) {
        List<String> matches = new ArrayList<>();
        for (String word : addedWords) {
            if (isMatch(word, pattern)) {
                matches.add(word);
            }
        }
        return matches;
    }
    
    private boolean isMatch(String word, String pattern) {
        if (word.length() != pattern.length()) {
            return false;
        }
        
        for (int i = 0; i < word.length(); i++) {
            char w = word.charAt(i);
            char p = pattern.charAt(i);
            if (p != '.' && p != w) {
                return false;
            }
        }
        return true;
    }
    
    private void clearDictionary() {
        wordDict = new WordDictionary();
        addedWords.clear();
        listModel.clear();
        currentSearchWord = null;
        addLog("WordDictionary 已清空");
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void loadDemoData() {
        String[] demoWords = {"bad", "dad", "mad", "pad", "bat", "cat", "rat", "hat"};
        
        for (String word : demoWords) {
            if (!addedWords.contains(word)) {
                wordDict.addWord(word);
                addedWords.add(word);
                listModel.addElement(word);
            }
        }
        
        addLog("加载演示数据: " + String.join(", ", demoWords));
        SwingUtilities.invokeLater(() -> visualPanel.repaint());
    }
    
    private void showWildcardExamples() {
        if (addedWords.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请先添加一些单词", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String examples = "通配符搜索示例:\n\n";
        examples += "如果已添加: bad, dad, mad, pad\n";
        examples += "• 搜索 '.ad' 可以匹配: bad, dad, mad, pad\n";
        examples += "• 搜索 'b.d' 可以匹配: bad\n";
        examples += "• 搜索 '..d' 可以匹配: bad, dad, mad, pad\n";
        examples += "• 搜索 'b..' 可以匹配: bad\n\n";
        examples += "'.' 可以匹配任意单个字母";
        
        JOptionPane.showMessageDialog(this, examples, "通配符使用说明", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void drawVisualization(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        g2d.setColor(Color.BLACK);
        g2d.drawString("WordDictionary 可视化", 20, 30);
        
        if (addedWords.isEmpty()) {
            g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
            g2d.setColor(Color.GRAY);
            g2d.drawString("字典为空，请添加单词", 20, 60);
            return;
        }
        
        // 绘制搜索结果
        if (currentSearchWord != null) {
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g2d.setColor(Color.BLUE);
            g2d.drawString("最近搜索: " + currentSearchWord, 20, 60);
            
            g2d.setColor(lastSearchResult ? Color.GREEN : Color.RED);
            g2d.drawString("结果: " + (lastSearchResult ? "匹配" : "不匹配"), 20, 80);
            
            if (lastSearchResult) {
                List<String> matches = findMatchingWords(currentSearchWord);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawString("匹配的单词: " + String.join(", ", matches), 20, 100);
            }
        }
        
        // 绘制字典统计信息
        int yOffset = currentSearchWord != null ? 130 : 80;
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("字典统计:", 20, yOffset);
        
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        g2d.drawString("• 总单词数: " + addedWords.size(), 30, yOffset + 20);
        
        // 按长度分组统计
        java.util.Map<Integer, Integer> lengthCount = new java.util.HashMap<>();
        for (String word : addedWords) {
            lengthCount.put(word.length(), lengthCount.getOrDefault(word.length(), 0) + 1);
        }
        
        int y = yOffset + 40;
        for (java.util.Map.Entry<Integer, Integer> entry : lengthCount.entrySet()) {
            g2d.drawString("• 长度 " + entry.getKey() + ": " + entry.getValue() + " 个单词", 30, y);
            y += 20;
        }
        
        // 绘制Trie结构示意
        drawTrieStructure(g2d, y + 20);
    }
    
    private void drawTrieStructure(Graphics2D g2d, int startY) {
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("Trie 结构示意 (支持通配符搜索):", 20, startY);
        
        int rootX = 100;
        int rootY = startY + 30;
        
        // 绘制根节点
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(rootX - 15, rootY - 15, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(rootX - 15, rootY - 15, 30, 30);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        g2d.drawString("root", rootX - 12, rootY + 3);
        
        // 绘制一些分支
        if (!addedWords.isEmpty()) {
            java.util.Set<Character> firstChars = new java.util.HashSet<>();
            for (String word : addedWords) {
                if (!word.isEmpty()) {
                    firstChars.add(word.charAt(0));
                }
            }
            
            int branchCount = Math.min(firstChars.size(), 4);
            int branchSpacing = 60;
            int branchStartX = rootX - (branchCount - 1) * branchSpacing / 2;
            
            int i = 0;
            for (Character c : firstChars) {
                if (i >= 4) break;
                
                int branchX = branchStartX + i * branchSpacing;
                int branchY = rootY + 50;
                
                // 绘制连线
                g2d.setColor(Color.GRAY);
                g2d.drawLine(rootX, rootY + 15, branchX, branchY - 12);
                
                // 绘制子节点
                g2d.setColor(new Color(173, 216, 230));
                g2d.fillOval(branchX - 12, branchY - 12, 24, 24);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(branchX - 12, branchY - 12, 24, 24);
                g2d.drawString(String.valueOf(c), branchX - 4, branchY + 3);
                
                i++;
            }
            
            // 绘制通配符说明
            g2d.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
            g2d.setColor(Color.BLUE);
            g2d.drawString("通配符 '.' 可以匹配任意字符", 20, startY + 120);
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
            new NO211_N_WordDictionary_Animation().setVisible(true);
        });
    }
}