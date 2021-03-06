package org.acronia.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.acronia.category.*;
import org.acronia.dto.*;
import org.acronia.sql.ConnectSql;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ConnectSql connectSql;

    private static final Properties properties = new Properties();

    @FXML private ComboBox<Category> comboCategoryId;

    @FXML private ToggleGroup categoryToggle;

    @FXML private ToggleGroup searchToggle;

    @FXML private RadioButton weaponButton;

    @FXML private RadioButton armorButton;

    @FXML private RadioButton unselectedButton;

    @FXML private RadioButton partnerButton;

    @FXML private RadioButton furnitureButton;

    @FXML private RadioButton avatarButton;

    @FXML private RadioButton demButton;

    @FXML private RadioButton itemNameButton;

    @FXML private RadioButton descriptionButton;

    @FXML private RadioButton rubyButton;

    @FXML private TextField query;

    @FXML private CheckBox commandFlag;

    @FXML private TableView<TableItem> table;
    @FXML private TableColumn<TableItem, String> itemId;
    @FXML private TableColumn<TableItem, String> itemName;
    @FXML private TableColumn<TableItem, String> categoryName;
    private ObservableList<TableItem> data;
    private Tooltip tooltip;
    private Stage thisStage;

    @FXML
    private TextField logArea;

    // stage注入
    public void setThisStage(Stage stage) {
        thisStage = stage;
    }

    // テキストフィールドでエンター押下
    @FXML
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onSearchButtonClick();
        }
    }

    // 検索ボタン押下
    @FXML
    private void onSearchButtonClick() {
        // System.out.println("query:" + query.getText());
        // System.out.println("category:" + comboCategoryId.getValue());
        // System.out.println("parent:" + categoryToggle.getSelectedToggle().getUserData());
        List<TableItem> tableItems = connectSql.searchItemName(query.getText(), comboCategoryId.getValue(),
                categoryToggle.getSelectedToggle().getUserData().toString(),
                searchToggle.getSelectedToggle().getUserData().toString());
        data.removeAll();
        data.setAll(tableItems);
        table.scrollTo(0);
        logArea.setText("検索結果：" + data.size() + "件");
    }

    // クリアボタン押下
    @FXML
    private void onClearButtonClick() {
        query.clear();
        query.requestFocus();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connectSql = new ConnectSql();

        // テーブル定義
        data = FXCollections.observableArrayList();
        table.itemsProperty().setValue(data);
        table.setItems(data);
        table.setPlaceholder(new Label("検索結果がありません！"));
        itemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemId.setStyle("-fx-alignment: CENTER;");
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        categoryName.setCellValueFactory(new PropertyValueFactory<>("descriptionPlain"));
        tooltip = new Tooltip();
        tooltip.setAutoHide(true);
        tooltip.setStyle("-fx-background-color: rgb(246, 228, 172); "
                + "-fx-text-fill: black; "
                + "-fx-border-color: rgb(174, 109, 54); "
                + "-fx-background-radius: 5; "
                + "-fx-border-radius: 5; "
        );

        // スクロールでツールチップ非表示
        thisStage.addEventFilter(EventType.ROOT, event -> {
            if (event.getEventType() == ScrollEvent.SCROLL){
                tooltip.hide();
            }
        });

        // テーブルのイベント
        table.setOnMouseClicked(event -> {
            tooltip.hide();
            boolean doubleClicked
                    = event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2;
            TableItem selected = table.getSelectionModel().selectedItemProperty().getValue();
            // ダブルクリック時にアイテムIDをコピー
            if (doubleClicked && selected != null) {
                Clipboard cb = Clipboard.getSystemClipboard();
                ClipboardContent clipboardContent = new ClipboardContent();
                String selectedItem = selected.getItemId();
                if (commandFlag.isSelected()){
                    selectedItem = "/item " + selectedItem;
                }
                clipboardContent.putString(selectedItem);
                cb.setContent(clipboardContent);
                logArea.setText("アイテムIDコピー：" + selected.getItemName());
            } else if (event.getButton().equals(MouseButton.SECONDARY) && selected != null) { // 右クリで詳細表示
                tooltip.setText(selected.getDescription());
                ImageView imageView = new ImageView();
                try {
                    imageView.setImage(new Image(new ByteArrayInputStream(connectSql.searchImage(selected.getIconId()))));
                    tooltip.setGraphic(imageView);
                } catch (NullPointerException ex) { // 画像がない場合
                    imageView.setImage(new Image(getClass().getResourceAsStream("no_image.png")));
                    tooltip.setGraphic(imageView);
                }
                tooltip.show(table, event.getScreenX()+10, event.getScreenY()+3);
            }
        });

        // ラジオボタンの名前付け
        weaponButton.setUserData("weapon");
        armorButton.setUserData("armor");
        partnerButton.setUserData("partner");
        furnitureButton.setUserData("furniture");
        avatarButton.setUserData("avatar");
        demButton.setUserData("dem");
        unselectedButton.setUserData("unselected");
        itemNameButton.setUserData("item_name");
        descriptionButton.setUserData("description");
        rubyButton.setUserData("ruby");

        // カテゴリ選択ボックスの初期化
        Callback<ListView<Category>, ListCell<Category>> newCellFactory = getCellFactory();
        comboCategoryId.setCellFactory(newCellFactory);
        comboCategoryId.setButtonCell(newCellFactory.call(null));

        refreshCategories(comboCategoryId, NullCategory.values(), "カテゴリ");

        // カテゴリリスト更新
        categoryToggle.selectedToggleProperty()
                .addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
                    switch (new_toggle.getUserData().toString()) {
                        case "weapon":
                            refreshCategories(comboCategoryId, WeaponCategory.values(), "武器");
                            break;
                        case "armor":
                            refreshCategories(comboCategoryId, ArmorCategory.values(), "防具");
                            break;
                        case "partner":
                            refreshCategories(comboCategoryId, PartnerCategory.values(), "パートナー");
                            break;
                        case "furniture":
                            refreshCategories(comboCategoryId, FurnitureCategory.values(), "飛空庭家具");
                            break;
                        case "avatar":
                            refreshCategories(comboCategoryId, AvatarCategory.values(), "アバター");
                            break;
                        case "dem":
                            refreshCategories(comboCategoryId, DemCategory.values(), "DEMパーツ");
                            break;
                        case "unselected":
                            refreshCategories(comboCategoryId, NullCategory.values(), "カテゴリ");
                    }
                });

        // トグル変更時のフォーカス移行処理
        categoryToggle.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle)
                        -> query.requestFocus());
        searchToggle.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle)
                        -> query.requestFocus());
        comboCategoryId.valueProperty().addListener(
                (ObservableValue<? extends Category> observable, Category oldValue, Category newValue)
                        -> query.requestFocus());
        // 起動時メッセージ
        logArea.setText("使用方法：検索結果をダブルクリックでアイテムIDをコピー、右クリックで詳細表示");

        // コマンドに/itemをつけるかの設定を読み込む
        try(FileInputStream f = new FileInputStream("setting.conf");
            BufferedInputStream b = new BufferedInputStream(f)){
                properties.load(b);
                if("true".equals(properties.getProperty("commandFlag"))){
                    commandFlag.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 終了時に設定を保存
        thisStage.showingProperty().addListener((observable, oldValue, newValue) -> {
            try(FileOutputStream f = new FileOutputStream("setting.conf");
                BufferedOutputStream b = new BufferedOutputStream(f)){
                    properties.setProperty("commandFlag", String.valueOf(commandFlag.isSelected()));
                    properties.store(b, "setting");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // CellFactory生成
    public Callback<ListView<Category>, ListCell<Category>> getCellFactory() {
        return (ListView<Category> param) -> new ListCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                 } else {
                    setText(item.getName());
                }
                setGraphic(null);
            }
        };
    }

    // カテゴリメニュー更新
    public void refreshCategories(ComboBox<Category> comboCategoryId, Category[] categoryValues, String prompt) {
        comboCategoryId.getItems().clear();
        comboCategoryId.getItems().addAll(categoryValues);
        comboCategoryId.setPromptText(prompt);
        comboCategoryId.setButtonCell(comboCategoryId.getCellFactory().call(null));
    }
}
