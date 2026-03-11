package com.bilibili.juc.web.model;

public class CatalogItem {

    private String categoryPath;
    private String name;
    private String launchName;
    private String difficulty;
    private String technique;
    private boolean launchable;

    public CatalogItem() {
    }

    public CatalogItem(String categoryPath, String name, String launchName, String difficulty, String technique,
            boolean launchable) {
        this.categoryPath = categoryPath;
        this.name = name;
        this.launchName = launchName;
        this.difficulty = difficulty;
        this.technique = technique;
        this.launchable = launchable;
    }

    public String getCategoryPath() {
        return categoryPath;
    }

    public void setCategoryPath(String categoryPath) {
        this.categoryPath = categoryPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLaunchName() {
        return launchName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTechnique() {
        return technique;
    }

    public void setTechnique(String technique) {
        this.technique = technique;
    }

    public boolean isLaunchable() {
        return launchable;
    }

    public void setLaunchable(boolean launchable) {
        this.launchable = launchable;
    }
}
