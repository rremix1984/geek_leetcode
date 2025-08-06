package com.animation.normal.permutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LexicographicalPermutation implements PermutationAlgorithm {

    @Override
    public List<AnimationState> generateStates(int[] nums) {
        List<AnimationState> states = new ArrayList<>();
        Arrays.sort(nums);

        int[] currentPerm = nums.clone();
        states.add(new AnimationState(currentPerm, new ArrayList<>(), null, 0, "found", -1, toList(currentPerm), null));

        while (true) {
            // 1. Find the largest index i such that nums[i] < nums[i+1]
            int i = -1;
            for (int k = currentPerm.length - 2; k >= 0; k--) {
                if (currentPerm[k] < currentPerm[k + 1]) {
                    i = k;
                    break;
                }
            }

            if (i == -1) {
                break; // Last permutation
            }

            // 2. Find the largest index j > i such that nums[i] < nums[j]
            int j = -1;
            for (int k = currentPerm.length - 1; k > i; k--) {
                if (currentPerm[k] > currentPerm[i]) {
                    j = k;
                    break;
                }
            }

            // 3. Swap nums[i] and nums[j]
            Map<String, Object> swapDetails = new HashMap<>();
            swapDetails.put("index1", i);
            swapDetails.put("index2", j);
            swap(currentPerm, i, j);
            states.add(new AnimationState(currentPerm, new ArrayList<>(), null, 0, "swap", -1, toList(currentPerm), swapDetails));

            // 4. Reverse the sub-array after index i
            Map<String, Object> reverseDetails = new HashMap<>();
            reverseDetails.put("start_index", i + 1);
            reverse(currentPerm, i + 1);
            states.add(new AnimationState(currentPerm, new ArrayList<>(), null, 0, "reverse", -1, toList(currentPerm), reverseDetails));
            states.add(new AnimationState(currentPerm, new ArrayList<>(), null, 0, "found", -1, toList(currentPerm), null));
        }

        return states;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private List<Integer> toList(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        return list;
    }
}