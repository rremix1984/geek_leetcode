package com.animation.normal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * NO764 最大加号标志 动画演示
 * 
 * 题目描述：
 * 在一个 n × n 的矩阵 grid 中，除了在数组 mines 中给出的元素为 0，其他每个元素都为 1。
 * mines[i] = [xi, yi] 表示 grid[xi][yi] == 0
 * 返回 grid 中包含 1 的最大的 轴对齐 加号标志的阶数。如果未找到加号标志，则返回 0。
 * 
 * @author AI Assistant
 */
public class NO764_N_OrderOfLargestPlusSign_Animation extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // UI组件
    private JTextField sizeField;
    private JTextField minesField;
    private JButton generateButton;
    private JButton solveButton;
    private JButton clearButton;
    private JTextArea logArea;
    private JPanel gridPanel;
    private JLabel resultLabel;
    
    // 数据
    private int n;
    private int[][] grid;
    private int[][] mines;
    private int maxOrder;
    private int maxCenterX, maxCenterY;
    private boolean solved;
    
    // 可视化相关
    private static final int CELL_SIZE = 30;
    private static final Color CELL_ONE = Color.WHITE;
    private static final Color CELL_ZERO = Color.BLACK;
    private static final Color PLUS_HIGHLIGHT = Color.RED;
    private static final Color BORDER_COLOR = Color.GRAY;
    
    public NO764_N_OrderOfLargestPlusSign_Animation() {
        n = 5;
        initializeUI();
        generateDefaultGrid();
    }
    
    private void initializeUI() {
        setTitle("NO764 - 最大加号标志 动画演示");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // 顶部控制面板
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
        
        // 中央面板
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // 网格面板
        gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGrid(g);
            }
        };
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setPreferredSize(new Dimension(400, 400));
        centerPanel.add(gridPanel, BorderLayout.CENTER);
        
        // 右侧信息面板
        JPanel infoPanel = createInfoPanel();
        centerPanel.add(infoPanel, BorderLayout.EAST);
        
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
        
        addLog("最大加号标志算法初始化完成");
        addLog("请设置网格大小和地雷位置，然后点击求解");
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("参数设置"));
        
        panel.add(new JLabel("网格大小 n:"));
        sizeField = new JTextField("5", 5);
        panel.add(sizeField);
        
        panel.add(new JLabel("地雷位置 (格式: x1,y1;x2,y2):"));
        minesField = new JTextField("1,1;2,2", 15);
        panel.add(minesField);
        
        generateButton = new JButton("生成网格");
        generateButton.addActionListener(e -> generateGrid());
        panel.add(generateButton);
        
        solveButton = new JButton("求解");
        solveButton.addActionListener(e -> solve());
        panel.add(solveButton);
        
        clearButton = new JButton("清空");
        clearButton.addActionListener(e -> clear());
        panel.add(clearButton);
        
        // 预设按钮
        JButton demoButton = new JButton("演示数据");
        demoButton.addActionListener(e -> loadDemoData());
        panel.add(demoButton);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("结果信息"));
        panel.setPreferredSize(new Dimension(200, 400));
        
        resultLabel = new JLabel("<html>最大加号阶数: 未计算<br/>中心位置: 未知</html>");
        resultLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        panel.add(resultLabel);
        
        panel.add(Box.createVerticalStrut(20));
        
        JLabel instructionLabel = new JLabel("<html><b>说明:</b><br/>" +
            "• 白色格子表示 1<br/>" +
            "• 黑色格子表示 0 (地雷)<br/>" +
            "• 红色高亮显示最大加号<br/>" +
            "• 加号阶数 = 臂长 + 1<br/>" +
            "<br/><b>算法思路:</b><br/>" +
            "1. 对每个位置计算四个方向的连续1的长度<br/>" +
            "2. 取四个方向的最小值作为该位置的加号阶数<br/>" +
            "3. 找出所有位置中的最大阶数</html>");
        instructionLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
        panel.add(instructionLabel);
        
        return panel;
    }
    
    private void generateDefaultGrid() {
        n = 5;
        grid = new int[n][n];
        // 初始化为全1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 1;
            }
        }
        
        // 设置默认地雷
        int[][] defaultMines = {{1, 1}, {2, 2}};
        for (int[] mine : defaultMines) {
            if (mine[0] >= 0 && mine[0] < n && mine[1] >= 0 && mine[1] < n) {
                grid[mine[0]][mine[1]] = 0;
            }
        }
        
        solved = false;
        maxOrder = 0;
        SwingUtilities.invokeLater(() -> gridPanel.repaint());
    }
    
    private void generateGrid() {
        try {
            n = Integer.parseInt(sizeField.getText().trim());
            if (n < 1 || n > 10) {
                JOptionPane.showMessageDialog(this, "网格大小应在 1-10 之间", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 初始化网格
            grid = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    grid[i][j] = 1;
                }
            }
            
            // 解析地雷位置
            String minesText = minesField.getText().trim();
            if (!minesText.isEmpty()) {
                String[] mineStrings = minesText.split(";");
                List<int[]> minesList = new ArrayList<>();
                
                for (String mineStr : mineStrings) {
                    String[] coords = mineStr.trim().split(",");
                    if (coords.length == 2) {
                        int x = Integer.parseInt(coords[0].trim());
                        int y = Integer.parseInt(coords[1].trim());
                        if (x >= 0 && x < n && y >= 0 && y < n) {
                            grid[x][y] = 0;
                            minesList.add(new int[]{x, y});
                        }
                    }
                }
                
                mines = minesList.toArray(new int[0][]);
            } else {
                mines = new int[0][];
            }
            
            solved = false;
            maxOrder = 0;
            addLog("生成 " + n + "x" + n + " 网格，地雷数量: " + mines.length);
            SwingUtilities.invokeLater(() -> gridPanel.repaint());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void solve() {
        if (grid == null) {
            JOptionPane.showMessageDialog(this, "请先生成网格", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        addLog("开始求解最大加号标志...");
        
        // 计算每个位置四个方向的连续1的长度
        int[][] left = new int[n][n];
        int[][] right = new int[n][n];
        int[][] up = new int[n][n];
        int[][] down = new int[n][n];
        
        // 计算左方向
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    left[i][j] = (j == 0) ? 1 : left[i][j-1] + 1;
                } else {
                    left[i][j] = 0;
                }
            }
        }
        
        // 计算右方向
        for (int i = 0; i < n; i++) {
            for (int j = n-1; j >= 0; j--) {
                if (grid[i][j] == 1) {
                    right[i][j] = (j == n-1) ? 1 : right[i][j+1] + 1;
                } else {
                    right[i][j] = 0;
                }
            }
        }
        
        // 计算上方向
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if (grid[i][j] == 1) {
                    up[i][j] = (i == 0) ? 1 : up[i-1][j] + 1;
                } else {
                    up[i][j] = 0;
                }
            }
        }
        
        // 计算下方向
        for (int j = 0; j < n; j++) {
            for (int i = n-1; i >= 0; i--) {
                if (grid[i][j] == 1) {
                    down[i][j] = (i == n-1) ? 1 : down[i+1][j] + 1;
                } else {
                    down[i][j] = 0;
                }
            }
        }
        
        // 找最大加号
        maxOrder = 0;
        maxCenterX = -1;
        maxCenterY = -1;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int order = Math.min(Math.min(left[i][j], right[i][j]), 
                                       Math.min(up[i][j], down[i][j]));
                    if (order > maxOrder) {
                        maxOrder = order;
                        maxCenterX = i;
                        maxCenterY = j;
                    }
                }
            }
        }
        
        solved = true;
        
        String result = "最大加号阶数: " + maxOrder;
        if (maxOrder > 0) {
            result += "，中心位置: (" + maxCenterX + ", " + maxCenterY + ")";
        }
        
        resultLabel.setText("<html>" + result.replace("，", "<br/>") + "</html>");
        addLog(result);
        
        SwingUtilities.invokeLater(() -> gridPanel.repaint());
    }
    
    private void clear() {
        n = 5;
        grid = null;
        mines = null;
        solved = false;
        maxOrder = 0;
        sizeField.setText("5");
        minesField.setText("");
        resultLabel.setText("<html>最大加号阶数: 未计算<br/>中心位置: 未知</html>");
        addLog("已清空所有数据");
        SwingUtilities.invokeLater(() -> gridPanel.repaint());
    }
    
    private void loadDemoData() {
        sizeField.setText("5");
        minesField.setText("1,1;2,2;3,0");
        generateGrid();
        addLog("加载演示数据完成");
    }
    
    private void drawGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (grid == null) {
            g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            g2d.setColor(Color.GRAY);
            String message = "请先生成网格";
            FontMetrics fm = g2d.getFontMetrics();
            int x = (gridPanel.getWidth() - fm.stringWidth(message)) / 2;
            int y = gridPanel.getHeight() / 2;
            g2d.drawString(message, x, y);
            return;
        }
        
        int startX = (gridPanel.getWidth() - n * CELL_SIZE) / 2;
        int startY = (gridPanel.getHeight() - n * CELL_SIZE) / 2;
        
        // 绘制网格
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int x = startX + j * CELL_SIZE;
                int y = startY + i * CELL_SIZE;
                
                // 绘制单元格
                if (grid[i][j] == 1) {
                    g2d.setColor(CELL_ONE);
                } else {
                    g2d.setColor(CELL_ZERO);
                }
                g2d.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // 绘制边框
                g2d.setColor(BORDER_COLOR);
                g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                
                // 绘制坐标
                g2d.setColor(Color.DARK_GRAY);
                g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
                g2d.drawString(i + "," + j, x + 2, y + 10);
            }
        }
        
        // 绘制最大加号
        if (solved && maxOrder > 0) {
            g2d.setColor(PLUS_HIGHLIGHT);
            g2d.setStroke(new BasicStroke(3));
            
            int centerX = startX + maxCenterY * CELL_SIZE + CELL_SIZE / 2;
            int centerY = startY + maxCenterX * CELL_SIZE + CELL_SIZE / 2;
            
            // 绘制水平线
            int armLength = maxOrder - 1;
            int leftX = centerX - armLength * CELL_SIZE;
            int rightX = centerX + armLength * CELL_SIZE;
            g2d.drawLine(leftX, centerY, rightX, centerY);
            
            // 绘制垂直线
            int topY = centerY - armLength * CELL_SIZE;
            int bottomY = centerY + armLength * CELL_SIZE;
            g2d.drawLine(centerX, topY, centerX, bottomY);
            
            // 绘制中心点
            g2d.setColor(Color.RED);
            g2d.fillOval(centerX - 3, centerY - 3, 6, 6);
        }
        
        // 绘制标题和说明
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g2d.drawString("网格大小: " + n + "x" + n, 10, 20);
        
        if (solved) {
            g2d.setColor(Color.BLUE);
            g2d.drawString("最大加号阶数: " + maxOrder, 10, gridPanel.getHeight() - 10);
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
            new NO764_N_OrderOfLargestPlusSign_Animation().setVisible(true);
        });
    }
}