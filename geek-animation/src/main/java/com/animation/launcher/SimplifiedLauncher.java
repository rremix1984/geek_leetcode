package com.animation.launcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * 简化版算法动画启动器
 * - 清晰的代码结构
 * - 分类管理算法
 * - 搜索功能
 * - 最近访问记录
 */
public class SimplifiedLauncher extends JFrame {
    
    private static SimplifiedLauncher instance;
    
    private final AnimationRegistry registry;
    private final RecentAlgorithmManager recentManager;
    
    private JTree algorithmTree;
    private JTextField searchField;
    private JTextArea descriptionArea;
    private JLabel statusLabel;
    private JList<String> recentList;
    private DefaultListModel<String> recentModel;
    
    // 当前选中的算法
    private String selectedAlgorithm;
    
    public SimplifiedLauncher() {
        this.registry = new AnimationRegistry();
        this.recentManager = new RecentAlgorithmManager();
        
        initUI();
        loadRecentAlgorithms();
    }
    
    private void initUI() {
        setTitle("LeetCode 算法动画演示系统 - 简化版");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLocationRelativeTo(null);
        
        // 设置外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        
        // 主面板
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // 顶部：搜索栏
        JPanel topPanel = createSearchPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // 中间：左侧树 + 右侧信息
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(createTreePanel());
        splitPane.setRightComponent(createInfoPanel());
        splitPane.setDividerLocation(350);
        mainPanel.add(splitPane, BorderLayout.CENTER);
        
        // 底部：状态栏
        mainPanel.add(createStatusBar(), BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
        
        // 快捷键
        setupKeyboardShortcuts();
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        JLabel searchLabel = new JLabel("🔍 搜索: ");
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        searchField.setToolTipText("输入算法名称或题号...");
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTree(searchField.getText());
            }
        });
        
        JButton clearBtn = new JButton("✕");
        clearBtn.setMargin(new Insets(2, 8, 2, 8));
        clearBtn.addActionListener(e -> {
            searchField.setText("");
            filterTree("");
        });
        
        panel.add(searchLabel, BorderLayout.WEST);
        panel.add(searchField, BorderLayout.CENTER);
        panel.add(clearBtn, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createTreePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "算法分类",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 13)
        ));
        
        // 构建树
        DefaultMutableTreeNode root = buildTree();
        algorithmTree = new JTree(root);
        algorithmTree.setRootVisible(false);
        algorithmTree.setShowsRootHandles(true);
        algorithmTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        algorithmTree.setCellRenderer(new AlgorithmTreeCellRenderer());
        algorithmTree.setFont(new Font("SansSerif", Font.PLAIN, 13));
        
        // 双击启动
        algorithmTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    launchSelectedAlgorithm();
                }
            }
        });
        
        // 单击显示描述
        algorithmTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) algorithmTree.getLastSelectedPathComponent();
            if (node != null && node.isLeaf()) {
                selectedAlgorithm = node.getUserObject().toString();
                updateDescription(selectedAlgorithm);
            }
        });
        
        // 工具栏
        JPanel toolBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton expandBtn = new JButton("全部展开");
        JButton collapseBtn = new JButton("全部折叠");
        expandBtn.addActionListener(e -> expandAllNodes(true));
        collapseBtn.addActionListener(e -> expandAllNodes(false));
        toolBar.add(expandBtn);
        toolBar.add(collapseBtn);
        
        panel.add(toolBar, BorderLayout.NORTH);
        panel.add(new JScrollPane(algorithmTree), BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        
        // 算法描述
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "算法描述",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 13)
        ));
        
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        descriptionArea.setText("请从左侧选择一个算法...");
        descPanel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        
        // 操作按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton startBtn = new JButton("▶ 启动动画");
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        startBtn.setPreferredSize(new Dimension(120, 40));
        startBtn.addActionListener(e -> launchSelectedAlgorithm());
        
        buttonPanel.add(startBtn);
        
        // 最近访问
        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "最近访问",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 13)
        ));
        
        recentModel = new DefaultListModel<>();
        recentList = new JList<>(recentModel);
        recentList.setFont(new Font("SansSerif", Font.PLAIN, 12));
        recentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String algo = recentList.getSelectedValue();
                    if (algo != null) {
                        launchAnimation(algo);
                    }
                }
            }
        });
        recentPanel.add(new JScrollPane(recentList), BorderLayout.CENTER);
        
        panel.add(descPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(recentPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createStatusBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        statusLabel = new JLabel("就绪 | 已加载 " + registry.size() + " 个算法动画");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        statusLabel.setForeground(Color.GRAY);
        
        panel.add(statusLabel, BorderLayout.WEST);
        
        return panel;
    }
    
    private DefaultMutableTreeNode buildTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("算法");
        
        // 分类节点
        addCategory(root, "数组", "NO.001 两数之和", "NO.026 删除有序数组中的重复项", 
                   "NO.027 移除元素", "NO.088 合并两个有序数组", "NO.283 移动零", 
                   "NO.977 有序数组的平方", "NO.1051 高度检查器", "NO.2529 正整数和负整数的最大计数");
        addCategory(root, "排序", "插入排序算法演示");
        addCategory(root, "搜索", "NO.035 搜索插入位置", "NO.704 二分查找");
        addCategory(root, "动态规划", "NO.118 杨辉三角");
        addCategory(root, "贪心", "NO.045 跳跃游戏 II", "NO.055 跳跃游戏", "NO.121 买卖股票的最佳时机");
        addCategory(root, "DFS/BFS", "NO.1306 跳跃游戏 III", "NO.1654 到家的最少跳跃次数");
        addCategory(root, "字符串", "NO.1002 查找常用字符", "NO.2828 是否首字母缩写", "NO.2942 查找包含字符的单词");
        addCategory(root, "位运算", "NO.136 只出现一次的数字", "NO.190 颠倒二进制位");
        addCategory(root, "图论", "NO.463 岛屿的周长", "NO.200 岛屿数量", "NO.695 岛屿的最大面积", 
                   "NO.994 腐烂的橘子", "图的深度优先搜索(DFS)", "图的广度优先搜索(BFS)", 
                   "Dijkstra最短路径算法", "Kruskal最小生成树算法", "拓扑排序算法");
        addCategory(root, "数学", "NO.009 回文数", "NO.066 加一", "NO.069 x的平方根", 
                   "NO.1266 访问所有点的最小时间", "NO.2549 不同整数的个数");
        addCategory(root, "树", "NO.094 二叉树的中序遍历", "NO.100 相同的树", "NO.101 对称二叉树", 
                   "NO.104 二叉树的最大深度", "NO.108 将有序数组转换为二叉搜索树", 
                   "NO.144 二叉树的前序遍历", "NO.145 二叉树的后序遍历", "NO.199 二叉树的右视图", 
                   "二叉树的顶层视图", "B+树动画");
        addCategory(root, "数据结构", "NO.225 用队列实现栈", "NO.234 回文链表", 
                   "NO.703 数据流中的第K大元素", "NO.705 设计哈希集合", "NO.706 设计哈希映射");
        addCategory(root, "中等难度", "NO.005 最长回文子串", "NO.015 三数之和", "NO.046 全排列", 
                   "NO.047 全排列 II", "NO.053 最大子数组和", "NO.139 单词拆分", "NO.208 实现 Trie", 
                   "NO.211 添加与搜索单词", "NO.284 窥探迭代器", "NO.322 零钱兑换");
        addCategory(root, "原子类", "AtomicInteger 动画", "AtomicReference 动画", 
                   "LongAdder 动画", "AtomicIntegerArray 动画", 
                   "AtomicStampedReference 动画", "AtomicMarkableReference 动画");
        addCategory(root, "区间问题", "NO.02.05 两数相加", "NO.04.05 验证BST", "NO.04.06 中序后继", 
                   "NO.04.10 检查子树", "NO.04.12 路径总和", "NO.08.09 括号生成");
        addCategory(root, "困难难度", "NO.023 合并K个升序链表", "NO.403 青蛙过河", 
                   "NO.51 N皇后", "NO.52 N皇后 II", "NO.85 最大矩形", 
                   "NO.773 滑动谜题", "NO.1206 跳表", "NO.1235 工作调度");
        addCategory(root, "其他", "NO.5 矩阵链表", "NO.6 矩阵链表最短路径", 
                   "NO.9 贪吃蛇", "NO.10 二叉树", "NO.11 二叉树遍历");
        
        return root;
    }
    
    private void addCategory(DefaultMutableTreeNode parent, String categoryName, String... algorithms) {
        DefaultMutableTreeNode category = new DefaultMutableTreeNode(categoryName);
        for (String algo : algorithms) {
            if (registry.hasAnimation(algo)) {
                category.add(new DefaultMutableTreeNode(algo));
            }
        }
        if (category.getChildCount() > 0) {
            parent.add(category);
        }
    }
    
    private void filterTree(String keyword) {
        // 简单实现：展开所有节点
        if (!keyword.isEmpty()) {
            expandAllNodes(true);
        }
    }
    
    private void expandAllNodes(boolean expand) {
        if (expand) {
            for (int i = 0; i < algorithmTree.getRowCount(); i++) {
                algorithmTree.expandRow(i);
            }
        } else {
            for (int i = algorithmTree.getRowCount() - 1; i >= 0; i--) {
                algorithmTree.collapseRow(i);
            }
        }
    }
    
    private void updateDescription(String algorithmName) {
        String desc = getAlgorithmDescription(algorithmName);
        descriptionArea.setText(desc);
    }
    
    private String getAlgorithmDescription(String name) {
        Map<String, String> descriptions = new HashMap<>();
        descriptions.put("NO.001 两数之和", 
            "给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那两个整数。\n\n" +
            "时间复杂度: O(n)\n空间复杂度: O(n)");
        descriptions.put("NO.704 二分查找", 
            "给定一个 n 个元素有序的整型数组 nums 和一个目标值 target，搜索 target 的下标。\n\n" +
            "时间复杂度: O(log n)\n空间复杂度: O(1)");
        descriptions.put("NO.200 岛屿数量", 
            "给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算网格中岛屿的数量。\n\n" +
            "时间复杂度: O(mn)\n空间复杂度: O(mn)");
        
        return descriptions.getOrDefault(name, 
            "算法: " + name + "\n\n选择后点击\"启动动画\"查看可视化演示。");
    }
    
    private void launchSelectedAlgorithm() {
        if (selectedAlgorithm != null && registry.hasAnimation(selectedAlgorithm)) {
            launchAnimation(selectedAlgorithm);
        } else {
            JOptionPane.showMessageDialog(this, "请先选择一个算法", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void launchAnimation(String name) {
        try {
            registry.launch(name);
            statusLabel.setText("正在运行: " + name);
            
            // 保存到最近访问
            recentManager.addRecentAlgorithm(name, "normal", "algorithm");
            updateRecentList();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "启动动画失败: " + e.getMessage(), 
                "错误", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void loadRecentAlgorithms() {
        updateRecentList();
    }
    
    private void updateRecentList() {
        recentModel.clear();
        List<RecentAlgorithmManager.RecentAlgorithmInfo> recent = recentManager.getRecentAlgorithms();
        for (RecentAlgorithmManager.RecentAlgorithmInfo info : recent) {
            recentModel.addElement(info.getAlgorithmName());
        }
    }
    
    private void setupKeyboardShortcuts() {
        // Enter 键启动
        InputMap im = algorithmTree.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = algorithmTree.getActionMap();
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "launch");
        am.put("launch", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchSelectedAlgorithm();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            instance = new SimplifiedLauncher();
            instance.setVisible(true);
        });
    }
    
    public static SimplifiedLauncher getInstance() {
        return instance;
    }
}

/**
 * 自定义树渲染器
 */
class AlgorithmTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
            boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        if (leaf) {
            setIcon(UIManager.getIcon("FileView.fileIcon"));
        } else {
            setIcon(UIManager.getIcon("FileView.directoryIcon"));
        }
        
        return this;
    }
}
