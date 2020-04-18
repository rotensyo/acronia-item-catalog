package org.acronia.dto;

import org.apache.commons.lang.StringUtils;

public class TableItem {
    private String itemId;
    private String itemName;
    private String categoryName;
    private String description;
    private String iconId;

public TableItem(String itemId, String itemName, String categoryName, String description, String iconId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.description = description;
        this.iconId = iconId;
    }

    public String getDescription() {
        if (StringUtils.isEmpty(description)){
            return "説明文未実装";
        } else {
            return description.replace("$R", "\n");
        }
    }

    public String getDescriptionPlain() {
        if (StringUtils.isEmpty(description)){
            return "説明文未実装";
        } else {
            return description.replace("$R", " ");
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
