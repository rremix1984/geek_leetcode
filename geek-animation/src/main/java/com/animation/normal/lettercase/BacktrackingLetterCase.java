package com.animation.normal.lettercase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BacktrackingLetterCase implements LetterCaseAlgorithm {

    @Override
    public void generatePermutations(String s, List<AnimationState> states) {
        backtrack(s.toCharArray(), 0, states);
    }

    private void backtrack(char[] chars, int index, List<AnimationState> states) {
        Map<String, Object> details = new HashMap<>();
        details.put("currentIndex", index);
        states.add(new AnimationState(chars, index, "start_level", details));

        if (index == chars.length) {
            states.add(new AnimationState(chars, index, "found_solution", null));
            return;
        }

        backtrack(chars, index + 1, states);

        if (Character.isLetter(chars[index])) {
            details.put("char", chars[index]);
            states.add(new AnimationState(chars, index, "toggle_case", details));
            chars[index] = toggleCase(chars[index]);
            backtrack(chars, index + 1, states);
            states.add(new AnimationState(chars, index, "backtrack", details));
            chars[index] = toggleCase(chars[index]); // backtrack
        }
    }

    private char toggleCase(char c) {
        if (Character.isUpperCase(c)) {
            return Character.toLowerCase(c);
        } else {
            return Character.toUpperCase(c);
        }
    }
}