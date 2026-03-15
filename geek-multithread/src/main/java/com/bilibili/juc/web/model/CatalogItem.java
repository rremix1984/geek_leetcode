package com.bilibili.juc.web.model;

public class CatalogItem {

    private String id;
    private String categoryPath;
    private String name;
    private String launchName;
    private String difficulty;
    private String technique;
    private String description;
    private String code;
    private boolean launchable;

    public CatalogItem() {
    }

    public CatalogItem(String id, String categoryPath, String name, String launchName, String difficulty, String technique,
            String description, String code, boolean launchable) {
        this.id = id;
        this.categoryPath = categoryPath;
        this.name = name;
        this.launchName = launchName;
        this.difficulty = difficulty;
        this.technique = technique;
        this.description = description;
        this.code = code;
        this.launchable = launchable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isLaunchable() {
        return launchable;
    }

    public void setLaunchable(boolean launchable) {
        this.launchable = launchable;
    }
}
