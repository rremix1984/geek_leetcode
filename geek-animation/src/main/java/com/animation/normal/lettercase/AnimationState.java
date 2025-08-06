package com.animation.normal.lettercase;

import java.util.Map;

public class AnimationState {
    public final char[] chars;
    public final int index;
    public final String action;
    public final Map<String, Object> details;

    public AnimationState(char[] chars, int index, String action, Map<String, Object> details) {
        this.chars = chars.clone();
        this.index = index;
        this.action = action;
        this.details = details;
    }
}