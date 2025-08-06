package com.animation.normal.permutations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeapPermutation implements PermutationAlgorithm {

    private List<AnimationState> states;
    private int[] nums;

    @Override
    public List<AnimationState> generateStates(int[] nums) {
        this.states = new ArrayList<>();
        this.nums = nums.clone();
        generate(nums.length);
        return states;
    }

    private void generate(int n) {
        if (n == 1) {
            states.add(new AnimationState(nums, toList(nums), null, nums.length, "found", -1, toList(nums), null));
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            generate(n - 1);
            int j = (n % 2 == 0) ? i : 0;
            Map<String, Object> details = new HashMap<>();
            details.put("index1", n - 1);
            details.put("index2", j);
            swap(n - 1, j);
            states.add(new AnimationState(nums, toList(nums), null, nums.length - n, "swap", -1, null, details));
        }
        generate(n - 1);
    }

    private void swap(int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private List<Integer> toList(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i : arr) {
            list.add(i);
        }
        return list;
    }
}