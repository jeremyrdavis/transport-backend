package io.arrogantprogrammer.domain;

public enum MenuItem {

    SMALL_COFFEE, MEDIUM_COFFEE, LARGE_COFFEE, CAPPUCCINO, ESPRESSO, LATTE, TEA;

    public static MenuItem get(int id) {
        return MenuItem.values()[id];
    }
}
