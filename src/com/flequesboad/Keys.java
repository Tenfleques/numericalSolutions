package com.flequesboad;

public enum Keys {
    ASER(6),
    ILIDIO(5);
    private final int value;
    Keys(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
