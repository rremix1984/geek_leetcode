package com.animation.normal;

import com.animation.normal.permutations.AnimationState;
import com.animation.normal.permutations.PermutationAlgorithm;
import com.animation.normal.permutations2.Permutation2Algorithm;

import java.util.ArrayList;
import java.util.List;

public class Permutation2Adapter implements PermutationAlgorithm {

    private final Permutation2Algorithm algorithm2;

    public Permutation2Adapter(Permutation2Algorithm algorithm2) {
        this.algorithm2 = algorithm2;
    }

    @Override
    public List<AnimationState> generateStates(int[] nums) {
        List<com.animation.normal.permutations2.AnimationState> states2 = new ArrayList<>();
        algorithm2.generatePermutations(nums, states2);

        List<AnimationState> states = new ArrayList<>();
        // Convert states2 to states
        for (com.animation.normal.permutations2.AnimationState state2 : states2) {
            List<Integer> foundPermutation = null;
            if ("found_solution".equals(state2.action)) {
                foundPermutation = state2.currentPermutation;
            }
            states.add(new AnimationState(
                state2.array,
                state2.currentPermutation,
                state2.used,
                state2.depth,
                state2.action,
                state2.details != null && state2.details.containsKey("currentIndex") ? (int) state2.details.get("currentIndex") : -1,
                foundPermutation,
                state2.details
            ));
        }
        return states;
    }
}