package com.animation.normal.permutations2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimationState {
    public final int[] array;
    public final List<Integer> currentPermutation;
    public final boolean[] used;
    public final int depth;
    public final String action;
    public final Map<String, Object> details;

    public AnimationState(int[] array, List<Integer> currentPermutation, boolean[] used, int depth, String action, Map<String, Object> details) {
        this.array = array.clone();
        this.currentPermutation = new ArrayList<>(currentPermutation);
        this.used = used.clone();
        this.depth = depth;
        this.action = action;
        this.details = details;
    }
}