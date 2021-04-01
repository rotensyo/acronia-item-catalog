package org.acronia.category;

public enum NullCategory implements Category {
    NULL("全アイテム"),
    SERISSA("serissa使用可能");

    final private String name;

    private NullCategory(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Category[] getArray() {
        return values();
    }
}
