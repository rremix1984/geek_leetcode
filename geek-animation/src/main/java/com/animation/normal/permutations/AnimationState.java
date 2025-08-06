package com.animation.normal.permutations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimationState {
    public int[] nums;
    public List<Integer> currentPath;
    public boolean[] used;
    public int depth;
    public String action; // "choose", "backtrack", "found"
    public int chosenIndex;
    public List<Integer> foundPermutation;
    public Map<String, Object> details;

    public AnimationState(int[] nums, List<Integer> path, boolean[] used, int depth,
                        String action, int chosenIndex, List<Integer> foundPermutation, Map<String, Object> details) {
        this.nums = nums.clone();
        this.currentPath = new ArrayList<>(path);
        this.used = used.clone();
        this.depth = depth;
        this.action = action;
        this.chosenIndex = chosenIndex;
        this.foundPermutation = foundPermutation != null ? new ArrayList<>(foundPermutation) : null;
        this.details = details;
    }
}