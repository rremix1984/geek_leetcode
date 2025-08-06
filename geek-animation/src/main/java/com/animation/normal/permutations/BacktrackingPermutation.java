package com.animation.normal.permutations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacktrackingPermutation implements PermutationAlgorithm {

    private List<AnimationState> animationStates;
    private int[] nums;
    private List<Integer> currentPath;
    private boolean[] used;

    @Override
    public List<AnimationState> generateStates(int[] nums) {
        this.nums = nums;
        this.animationStates = new ArrayList<>();
        this.currentPath = new ArrayList<>();
        this.used = new boolean[nums.length];
        backtrack(0);
        return animationStates;
    }

    private void backtrack(int depth) {
        if (depth == nums.length) {
            animationStates.add(new AnimationState(nums, currentPath, used, depth, "found", -1, new ArrayList<>(currentPath), null));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                currentPath.add(nums[i]);
                animationStates.add(new AnimationState(nums, currentPath, used, depth, "choose", i, null, null));

                backtrack(depth + 1);

                used[i] = false;
                currentPath.remove(currentPath.size() - 1);
                animationStates.add(new AnimationState(nums, currentPath, used, depth, "backtrack", i, null, null));
            }
        }
    }
}