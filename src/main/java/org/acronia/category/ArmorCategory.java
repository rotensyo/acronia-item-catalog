package org.acronia.category;

public enum ArmorCategory implements Category {
    NULL("指定なし"),
    HELM("頭部防具"),
    ACCESORY_HEAD("頭部装飾品"),
    ACCESORY_FACE("顔装飾品"),
    FULLFACE("フルフェイス"),
    ACCESORY_NECK("胸部装飾品"),
    JOINT_SYMBOL("シンボル"),
    BACKPACK("背負い袋"),
    SHIELD("盾"),
    ACCESORY_FINGER("左手飾り"),
    BODYSUIT("ボディスーツ"),
    COSTUME("コスチューム"),
    FACEBODYSUIT("フェイスボディスーツ"),
    OVERALLS("つなぎ"),
    ONEPIECE("ワンピース"),
    ARMOR_UPPER("上半身防具"),
    ARMOR_LOWER("下半身防具"),
    SLACKS("スラックス"),
    LONGBOOTS("ロングブーツ"),
    HALFBOOTS("ハーフブーツ"),
    BOOTS("ブーツ"),
    SHOES("靴"),
    SOCKS("靴下"),
    EFFECT("エフェクト"),
    EQ_ALLSLOT("転職用の服"),
    WEDDING("ウェディング");

    final private String name;

    private ArmorCategory(String name) {
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
