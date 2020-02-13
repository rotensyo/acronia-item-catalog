package org.acronia.category;

public enum AvatarCategory implements Category {

    NULL("指定なし"),
    HAIR_CATALOG("紹介状"),
    FACECHANGE("お顔スイッチャー");

    final private String name;

    private AvatarCategory(String name) {
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
