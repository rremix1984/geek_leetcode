package com.animation.normal.permutations2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacktrackingPermutation2 implements Permutation2Algorithm {

    @Override
    public void generatePermutations(int[] nums, List<AnimationState> states) {
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        backtrack(nums, new ArrayList<>(), used, states, 0);
    }

    private void backtrack(int[] nums, List<Integer> currentPermutation, boolean[] used, List<AnimationState> states, int depth) {
        Map<String, Object> details = new HashMap<>();
        details.put("depth", depth);
        details.put("currentPermutation", new ArrayList<>(currentPermutation));
        states.add(new AnimationState(nums, new ArrayList<>(currentPermutation), used.clone(), depth, "start_level", details));

        if (currentPermutation.size() == nums.length) {
            states.add(new AnimationState(nums, new ArrayList<>(currentPermutation), used.clone(), depth, "found_solution", null));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            details = new HashMap<>();
            details.put("currentIndex", i);
            states.add(new AnimationState(nums, new ArrayList<>(currentPermutation), used.clone(), depth, "check_condition", details));

            if (used[i] || (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                details.put("skipped", true);
                states.add(new AnimationState(nums, new ArrayList<>(currentPermutation), used.clone(), depth, "check_condition", details));
                continue;
            }

            used[i] = true;
            currentPermutation.add(nums[i]);
            details.put("path", new ArrayList<>(currentPermutation));
            states.add(new AnimationState(nums, new ArrayList<>(currentPermutation), used.clone(), depth, "select", details));

            backtrack(nums, currentPermutation, used, states, depth + 1);

            used[i] = false;
            currentPermutation.remove(currentPermutation.size() - 1);
            details = new HashMap<>();
            details.put("removedIndex", i);
            details.put("path", new ArrayList<>(currentPermutation));
            states.add(new AnimationState(nums, new ArrayList<>(currentPermutation), used.clone(), depth, "backtrack", details));
        }
    }
}