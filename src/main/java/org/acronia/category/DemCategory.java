package org.acronia.category;

public enum DemCategory implements Category {

    NULL("指定なし"),
    PARTS_HEAD("頭部パーツ"),
    PARTS_BODY("胴体パーツ"),
    PARTS_LEG("脚部パーツ"),
    PARTS_BACK("背部パーツ"),
    PARTS_BLOW("アーム(打)"),
    PARTS_SLASH("アーム(斬)"),
    PARTS_STAB("アーム(突)"),
    PARTS_LONGRANGE("アーム(射)"),
    SETPARTS_BLOW("セットパーツ(打)"),
    SETPARTS_STAB("セットパーツ(突)"),
    SETPARTS_SLASH("セットパーツ(斬)"),
    SETPARTS_LONGRANGE("セットパーツ(射)");

    final private String name;

    private DemCategory(String name) {
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
