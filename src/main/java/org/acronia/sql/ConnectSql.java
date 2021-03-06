package org.acronia.sql;


import org.acronia.category.*;
import org.acronia.dto.*;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class ConnectSql {

    private Connection connection;

    private String weaponCategorySql;

    private String armorCategorySql;

    private String partnerCategorySql;

    private String furnitureCategorySql;

    private String avatarCategorySql;

    private String demCategorySql;

    private String serissaCategorySql;

    private String commonSql;

    private String iconSql;

    public ConnectSql(){
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite::resource:org/acronia/item.db");
        }catch (SQLException | ClassNotFoundException ex){
            ex.printStackTrace();
        }

        // 前半部分
        commonSql = "SELECT item_id, item_name, category_id, description, icon_id FROM item WHERE ";

        weaponCategorySql = createCategorySql(WeaponCategory.values().length);
        armorCategorySql = createCategorySql(ArmorCategory.values().length);
        partnerCategorySql = createCategorySql(PartnerCategory.values().length);
        furnitureCategorySql = createCategorySql(FurnitureCategory.values().length);
        avatarCategorySql = createCategorySql(AvatarCategory.values().length);
        demCategorySql = createCategorySql(DemCategory.values().length);
        serissaCategorySql = createCategorySql(ArmorCategory.values().length + WeaponCategory.values().length + PartnerCategory.values().length + FurnitureCategory.values().length + AvatarCategory.values().length);
        iconSql = "SELECT image_bin FROM image WHERE pict_id = ?";
    }

    // 後半部分
    public String createCategorySql(int len){
        return " LIKE ? AND category_id IN ("
                + StringUtils.repeat("?", ",", len)
                + ") ORDER BY category_id, item_id";
    }

    public List<TableItem> searchItemName(String query, Category category, String parentCategory, String column) {
        try {
            PreparedStatement pstmt = createSql(query, category, parentCategory, column);
            ResultSet rs = pstmt.executeQuery();
            List<TableItem> tableItems = new ArrayList<>();
            while (rs.next()) {
                tableItems.add(new TableItem(rs.getString("item_id"), rs.getString("item_name"),
                        rs.getString("category_id"), rs.getString("description"),
                        rs.getString("icon_id")));
            }
            return tableItems;
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public byte[] searchImage(String iconId) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(iconSql);
            pstmt.setString(1, iconId);
            ResultSet rs = pstmt.executeQuery();
            return rs.getBytes("image_bin");
        }catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public PreparedStatement createSql(String query, Category category, String parentCategory, String column) throws SQLException{
        PreparedStatement pstmt = null;
        if (category == null || "NULL".equals(category.toString())){  // サブカテゴリ未選択か"NULL"
            switch (parentCategory){
                case "weapon":
                    pstmt = createPstmt(column, query, WeaponCategory.values(), weaponCategorySql);
                    break;
                case "armor":
                    pstmt = createPstmt(column, query, ArmorCategory.values(), armorCategorySql);
                    break;
                case "partner":
                    pstmt = createPstmt(column, query, PartnerCategory.values(), partnerCategorySql);
                    break;
                case "furniture":
                    pstmt = createPstmt(column, query, FurnitureCategory.values(), furnitureCategorySql);
                    break;
                case "avatar":
                    pstmt = createPstmt(column, query, AvatarCategory.values(), avatarCategorySql);
                    break;
                case "dem":
                    pstmt = createPstmt(column, query, DemCategory.values(), demCategorySql);
                    break;
                case "unselected":
                    String sql = "SELECT item_id, item_name, category_id, description, icon_id FROM item " +
                            "WHERE " + column+ " LIKE ? ORDER BY category_id, item_id";
                    pstmt = connection.prepareStatement(sql);
                    pstmt.setString(1, "%" + query + "%");
                    break;
            }
        } else {  // サブカテゴリに何かしらが入っている
            if ("SERISSA".equals(category.toString())) {
                pstmt = createPstmt(column, query, Stream.of(Arrays.asList(ArmorCategory.values()), Arrays.asList(WeaponCategory.values()), Arrays.asList(PartnerCategory.values()), Arrays.asList(FurnitureCategory.values()), Arrays.asList(AvatarCategory.values())).flatMap(Collection::stream).toArray(Category[]::new), serissaCategorySql);
            } else {
                String sql = "SELECT item_id, item_name, category_id, description, icon_id FROM item " +
                        "WHERE " + column + " LIKE ? AND category_id = ? ORDER BY category_id, item_id";
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, "%" + query + "%");
                pstmt.setString(2, category.toString());
            }
        }
        return pstmt;
    }

    private PreparedStatement createPstmt(String column, String query,
                                          Category[] values, String sql) throws SQLException{
        PreparedStatement pstmt = connection.prepareStatement(commonSql + column + sql);
        pstmt.setString(1, "%" + query + "%");
        for(int i=0; i < values.length; i++){
            pstmt.setString(i+2, values[i].toString());
        }
        return pstmt;
    }
}
