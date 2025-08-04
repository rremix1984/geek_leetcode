package com.leetcode.todo;

/**
 * 游戏状态管理类
 * 管理分数、生命值、关卡等游戏状态
 */
public class GameState {
    private static GameState instance;
    
    private int score = 0;
    private int lives = 3;
    private int level = 1;
    private int coinsCollected = 0;
    private int enemiesDefeated = 0;
    
    private GameState() {
        // 私有构造函数，确保单例
    }
    
    /**
     * 获取游戏状态实例（单例模式）
     */
    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }
    
    /**
     * 重置游戏状态
     */
    public void reset() {
        score = 0;
        lives = 3;
        level = 1;
        coinsCollected = 0;
        enemiesDefeated = 0;
    }
    
    /**
     * 添加分数
     */
    public void addScore(int points) {
        score += points;
        
        // 每1000分加一条命
        if (score > 0 && score % 1000 == 0) {
            addLife();
            System.out.println("获得额外生命！");
        }
    }
    
    /**
     * 收集金币
     */
    public void collectCoin(int points) {
        coinsCollected++;
        addScore(points);
        
        // 每收集10个金币加一条命
        if (coinsCollected % 10 == 0) {
            addLife();
            System.out.println("收集10个金币，获得额外生命！");
        }
    }
    
    /**
     * 击败敌人
     */
    public void defeatEnemy(int points) {
        enemiesDefeated++;
        addScore(points);
    }
    
    /**
     * 增加生命
     */
    public void addLife() {
        lives++;
    }
    
    /**
     * 失去生命
     */
    public void loseLife() {
        if (lives > 0) {
            lives--;
            System.out.println("失去一条生命！剩余生命: " + lives);
        }
        
        if (lives <= 0) {
            System.out.println("游戏结束！最终分数: " + score);
        }
    }
    
    /**
     * 检查游戏是否结束
     */
    public boolean isGameOver() {
        return lives <= 0;
    }
    
    /**
     * 升级到下一关
     */
    public void nextLevel() {
        level++;
        addScore(1000); // 完成关卡奖励
        System.out.println("恭喜通过第" + (level-1) + "关！");
    }
    
    // Getter方法
    public int getScore() { return score; }
    public int getLives() { return lives; }
    public int getLevel() { return level; }
    public int getCoinsCollected() { return coinsCollected; }
    public int getEnemiesDefeated() { return enemiesDefeated; }
    
    /**
     * 获取格式化的分数字符串
     */
    public String getFormattedScore() {
        return String.format("%06d", score);
    }
    
    /**
     * 获取游戏状态摘要
     */
    public String getStatusSummary() {
        return String.format("分数: %s | 生命: %d | 关卡: %d | 金币: %d",
            getFormattedScore(), lives, level, coinsCollected);
    }
}
