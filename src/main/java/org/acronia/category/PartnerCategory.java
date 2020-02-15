package org.acronia.category;

public enum PartnerCategory implements Category {
    NULL("指定なし"),
    PARTNER("パートナー"),
    BACK_DEMON("ブースト "),
    RIDE_PET_ROBOT("騎乗ロボ"),
    RIDE_PARTNER("ライドパートナー");

    final private String name;

    private PartnerCategory(String name) {
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
