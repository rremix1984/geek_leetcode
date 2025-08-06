package com.animation.normal.lettercase;

import com.animation.normal.lettercase.AnimationState;

import java.util.List;

public interface LetterCaseAlgorithm {
    void generatePermutations(String s, List<AnimationState> states);
}