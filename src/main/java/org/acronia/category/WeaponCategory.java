package org.acronia.category;

public enum WeaponCategory implements Category {
    NULL("指定なし"),
    SHORT_SWORD("短剣"),
    SWORD("剣"),
    RAPIER("レイピア"),
    SPEAR("槍"),
    HAMMER("ハンマー"),
    AXE("斧"),
    CLAW("爪"),
    ROPE("ロープ"),
    THROW("投擲武器"),
    CARD("カード"),
    BOW("弓"),
    GUN("銃"),
    DUALGUN("2丁拳銃"),
    RIFLE("ライフル"),
    STAFF("杖"),
    STRINGS("楽器"),
    BOOK("本"),
    HANDBAG("カバン"),
    LEFT_HANDBAG("左手用カバン"),
    ETC_WEAPON("特殊武器");

    final private String name;

    private WeaponCategory(String name) {
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
