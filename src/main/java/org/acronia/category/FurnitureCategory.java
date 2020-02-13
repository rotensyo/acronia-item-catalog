package org.acronia.category;

public enum FurnitureCategory implements Category {
    NULL("指定なし"),
    FG_GARDEN_MODELHOUSE("家"),
    FG_GARDEN_FLOOR("庭の床"),
    FG_ROOM_WALL("壁"),
    FG_ROOM_FLOOR("家の床"),
    FG_FLYING_SAIL("飛行帆"),
    FURNITURE("家具");

    final private String name;

    private FurnitureCategory(String name) {
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
