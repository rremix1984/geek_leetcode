package com.leetcode.todo;

import javax.swing.*;
import java.awt.*;

/**
 * 超级玛丽游戏主类
 * 阶段1：基础框架搭建
 */
public class SuperMarioGame extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final String GAME_TITLE = "超级玛丽游戏 - Java版";
    
    private GamePanel gamePanel;
    
    public SuperMarioGame() {
        initializeWindow();
        initializeGamePanel();
        startGame();
    }
    
    /**
     * 初始化游戏窗口
     */
    private void initializeWindow() {
        setTitle(GAME_TITLE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 居中显示
        setResizable(false); // 禁止调整窗口大小
        
        // 设置窗口图标（可选）
        setIconImage(Toolkit.getDefaultToolkit().getImage("mario_icon.png"));
    }
    
    /**
     * 初始化游戏面板
     */
    private void initializeGamePanel() {
        gamePanel = new GamePanel();
        add(gamePanel);
    }
    
    /**
     * 启动游戏
     */
    private void startGame() {
        setVisible(true);
        gamePanel.startGameLoop();
    }
    
    public static void main(String[] args) {
        // 设置系统外观
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 在事件分发线程中创建游戏
        SwingUtilities.invokeLater(SuperMarioGame::new);
    }
}
