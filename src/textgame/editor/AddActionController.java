/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import textgame.structure.Game;
import textgame.structure.GameEventListener;
import textgame.structure.Item;
import textgame.structure.Option;
import textgame.structure.Room;
import textgame.structure.StaticObject;
import textgame.structure.actions.Action;
import textgame.structure.actions.AddItemToPlayer;
import textgame.structure.actions.AddItemToRoom;
import textgame.structure.actions.AddOptionToRoom;
import textgame.structure.actions.AddPathFromRoom;
import textgame.structure.actions.AddStaticObjectToRoom;
import textgame.structure.actions.AddToCustomValue;
import textgame.structure.actions.ChangeCurrentImage;
import textgame.structure.actions.ChangeItemDescription;
import textgame.structure.actions.ChangeItemName;
import textgame.structure.actions.ChangeRoomDescription;
import textgame.structure.actions.ChangeRoomImage;
import textgame.structure.actions.ChangeRoomName;
import textgame.structure.actions.ChangeStaticObjectDescription;
import textgame.structure.actions.ChangeStaticObjectName;
import textgame.structure.actions.DescribeStaticObject;
import textgame.structure.actions.DisableGameEventListener;
import textgame.structure.actions.EnableGameEventListener;
import textgame.structure.actions.LoseGame;
import textgame.structure.actions.MovePlayerToRoom;
import textgame.structure.actions.PickUpItem;
import textgame.structure.actions.PushMessage;
import textgame.structure.actions.RemoveItemFromRoom;
import textgame.structure.actions.RemoveOptionFromRoom;
import textgame.structure.actions.RemovePathFromRoom;
import textgame.structure.actions.RemoveStaticObjectFromRoom;
import textgame.structure.actions.SetCustomValue;
import textgame.structure.actions.ShowPlayerInventory;
import textgame.structure.actions.ThrowGameEvent;
import textgame.structure.actions.WinGame;
import textgame.structure.gameEvents.GameEvent;
import textgame.structure.gameEvents.GameEvent.GameEventType;
import textgame.structure.gameEvents.RandomNumber;

/**
 * FXML Controller class
 *
 * @author David Brož
 */
public class AddActionController implements Initializable {

    //TODO:Dodelej akce, udelej souborovy system,osetri context menu delete kdyz neni polozka. zmen barvu MenuItem
    public static Action result;
    public static Stage addActionStage;
    public boolean isOK = false; //can the OK button be clicked?
    @FXML
    private Button closeButton, okButton;
    @FXML
    private Label currentActionLabel;

    @FXML
    private ImageView changeRoomImage_ImageView,
            changeCurrentImage_ImageView
            ;

    @FXML
    private TextArea changeItemDescription_TextArea,
            changeRoomDescription_TextArea,
            changeStaticObjectDescription_TextArea,
            pushMessage_TextArea;

    @FXML
    private TextField changeStaticObjectName_TextField,
            changeRoomName_TextField,
            changeItemName_TextField,
            customValue_TextField;

    @FXML
    ChoiceBox<Option> addOptionToRoom_ChoiceBox_Option_ChoiceBox,
            removeOptionFromRoom_Option_ChoiceBox;

    @FXML
    private ChoiceBox<Item> addItemToPlayer_ChoiceBox,
            addItemToRoom_ChoiceBox_Item_ChoiceBox,
            removeItemFromRoom_Item_ChoiceBox,
            changeItemDescription_ChoiceBox,
            changeItemName_ChoiceBox,
            pickUpItem_ChoiceBox;
    @FXML
    private ChoiceBox<Room> addItemToRoom_ChoiceBox_Room_ChoiceBox,
            addOptionToRoom_ChoiceBox_Room_ChoiceBox,
            addPathToRoom_ChoiceBox_Where_Room_ChoiceBox,
            addPathToRoom_ChoiceBox_From_Room_ChoiceBox,
            addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox,
            movePlayerToRoom_ChoiceBox,
            removeItemFromRoom_Room_ChoiceBox,
            removeOptionFromRoom_Room_ChoiceBox,
            removePathFromRoom_From_Room_ChoiceBox,
            removePathFromRoom_Where_Room_ChoiceBox,
            removeStaticObjectFromRoom_Room_ChoiceBox,
            changeRoomDescription_ChoiceBox,
            changeRoomName_ChoiceBox,
            changeRoomImage_ChoiceBox;
    @FXML
    private ChoiceBox<StaticObject> addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox,
            describeStaticObject_StaticObject_ChoiceBox,
            removeStaticObjectFromRoom_StaticObject_ChoiceBox,
            changeStaticObjectDescription_ChoiceBox,
            changeStaticObjectName_ChoiceBox;
    @FXML
    private ChoiceBox<GameEventListener> disableGameEventListener_ChoiceBox,
            enableGameEventListener_ChoiceBox;

    @FXML
    private ChoiceBox<GameEvent.GameEventType> throwGameEvent_ChoiceBox_GameEvent;

    @FXML
    private ChoiceBox<String> customValue_choiceBox;

    /*THROW GAME EVENT GRID-------------------*/
    @FXML
    private ChoiceBox throwGameEvent_ChoiceBox1,
            throwGameEvent_ChoiceBox2,
            throwGameEvent_ChoiceBox3;
    @FXML
    private HBox throwGameEvent_StackPane1_HBox1,
            throwGameEvent_StackPane1_HBox2,
            throwGameEvent_StackPane2_HBox1,
            throwGameEvent_StackPane2_HBox2,
            throwGameEvent_StackPane3_HBox1,
            throwGameEvent_StackPane3_HBox2;
    @FXML
    private TextField throwGameEvent_TextField1,
            throwGameEvent_TextField2,
            throwGameEvent_TextField3;
    @FXML
    private Label throwGameEvent_ChoiceBox1_Label,
            throwGameEvent_ChoiceBox2_Label,
            throwGameEvent_ChoiceBox3_Label,
            throwGameEvent_TextField1_Label,
            throwGameEvent_TextField2_Label,
            throwGameEvent_TextField3_Label;

    private boolean throwGameEvent_TextField1_isNumber,
            throwGameEvent_TextField2_isNumber,
            throwGameEvent_TextField3_isNumber;
    //----------------------------------------

    @FXML
    private GridPane addItemToPlayer_GridPane,
            showPlayerInventor_GridPane,
            addItemToRoom_GridPane,
            addOptionToRoom_GridPane,
            addPathFromRoom_GridPane,
            addStaticObjectToRoom_GridPane,
            describeStaticObject_GridPane,
            disableGameEventListener_GridPane,
            enableGameEventListener_GridPane,
            movePlayerToRoom_GridPane,
            removeItemFromRoom_GridPane,
            removeOptionFromRoom_GridPane,
            removePathFromRoom_GridPane,
            removeStaticObjectFromRoom_GridPane,
            changeRoomDescription_GridPane,
            changeItemDescription_GridPane,
            changeStaticObjectDescription_GridPane,
            changeStaticObjectName_GridPane,
            changeRoomName_GridPane,
            changeItemName_GridPane,
            winGame_GridPane,
            loseGame_GridPane,
            pickUpItem_GridPane,
            pushMessage_GridPane,
            throwGameEvent_GridPane,
            customValue_GridPane,
            changeRoomImage_GridPane,
            changeCurrentImage_GridPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        result = null;
        throwGameEvent_ChoiceBox_GameEvent.getItems().addAll(GameEvent.GameEventType.values());
        throwGameEvent_ChoiceBox_GameEvent.getSelectionModel().select(0);
        setListeners();
        resetLayout();

    }

    @FXML
    private void cancelButton() {
        result = null;
        addActionStage.close();
    }

    @FXML
    private void okButton() {
        addActionStage.close();
    }

    //---Action-choices---------------------------------------------------------
    @FXML
    private void choiceShowPlayerInventory() {
        isOK = true;
        result = new ShowPlayerInventory();
        resetLayout();
        currentActionLabel.setText("Show Player Inventory");
        showPlayerInventor_GridPane.setVisible(true);
    }

    @FXML
    private void choiceWinGame() {
        isOK = true;
        result = new WinGame();
        resetLayout();
        currentActionLabel.setText("Win game");
        winGame_GridPane.setVisible(true);
    }

    @FXML
    private void choiceLoseGame() {
        isOK = true;
        result = new LoseGame();
        resetLayout();
        currentActionLabel.setText("Lose gaeme");
        loseGame_GridPane.setVisible(true);
    }

    @FXML
    private void choiceAddItemToPlayer() {
        isOK = true;
        ArrayList<Item> items = Game.getInstance().getAllItems();
        addItemToPlayer_ChoiceBox.getItems().clear();
        if (!items.isEmpty()) {
            addItemToPlayer_ChoiceBox.getItems().addAll(items);
            addItemToPlayer_ChoiceBox.setValue(addItemToPlayer_ChoiceBox.getItems().get(0));
            result = new AddItemToPlayer(addItemToPlayer_ChoiceBox.getSelectionModel().getSelectedItem());
        } else {
            isOK = false;
        }

        resetLayout();
        currentActionLabel.setText("Add Item to Player");
        addItemToPlayer_GridPane.setVisible(true);
    }

    @FXML
    private void choicePickUpItem() {
        isOK = true;
        ArrayList<Item> items = Game.getInstance().getAllItems();
        pickUpItem_ChoiceBox.getItems().clear();
        if (!items.isEmpty()) {
            pickUpItem_ChoiceBox.getItems().addAll(items);
            pickUpItem_ChoiceBox.getSelectionModel().select(0);
            result = new PickUpItem(pickUpItem_ChoiceBox.getSelectionModel().getSelectedItem());
        } else {
            isOK = false;
        }

        resetLayout();
        currentActionLabel.setText("Pick up item");
        pickUpItem_GridPane.setVisible(true);
    }

    @FXML
    private void choiceAddItemToRoom() {
        isOK = true;
        ArrayList<Item> items = Game.getInstance().getAllItems();
        addItemToRoom_ChoiceBox_Item_ChoiceBox.getItems().clear();
        if (!items.isEmpty()) {
            addItemToRoom_ChoiceBox_Item_ChoiceBox.getItems().addAll(items);
            addItemToRoom_ChoiceBox_Item_ChoiceBox.setValue(addItemToRoom_ChoiceBox_Item_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        addItemToRoom_ChoiceBox_Room_ChoiceBox.getItems().clear();
        if (!rooms.isEmpty()) {
            addItemToRoom_ChoiceBox_Room_ChoiceBox.getItems().addAll(rooms);
            addItemToRoom_ChoiceBox_Room_ChoiceBox.setValue(addItemToRoom_ChoiceBox_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        if (isOK) {
            result = new AddItemToRoom(addItemToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().getSelectedItem(),
                    addItemToRoom_ChoiceBox_Item_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        //result = new AddItemToRoom(,addItemToPlayer_ChoiceBox.getValues());
        resetLayout();
        currentActionLabel.setText("Add Item to Room");
        addItemToRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceAddOptionToRoom() {
        isOK = true;
        ArrayList<Option> options = Game.getInstance().getAllOptions();
        addOptionToRoom_ChoiceBox_Option_ChoiceBox.getItems().clear();
        if (!options.isEmpty() && addOptionToRoom_ChoiceBox_Option_ChoiceBox.getItems().isEmpty()) {
            addOptionToRoom_ChoiceBox_Option_ChoiceBox.getItems().addAll(options);
            addOptionToRoom_ChoiceBox_Option_ChoiceBox.setValue(addOptionToRoom_ChoiceBox_Option_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        addOptionToRoom_ChoiceBox_Room_ChoiceBox.getItems().clear();
        if (!rooms.isEmpty() && addOptionToRoom_ChoiceBox_Room_ChoiceBox.getItems().isEmpty()) {
            addOptionToRoom_ChoiceBox_Room_ChoiceBox.getItems().addAll(rooms);
            addOptionToRoom_ChoiceBox_Room_ChoiceBox.setValue(addOptionToRoom_ChoiceBox_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new AddOptionToRoom(addOptionToRoom_ChoiceBox_Option_ChoiceBox.getSelectionModel().getSelectedItem(),
                    addOptionToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Add Option to Room");
        addOptionToRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceAddPathFromRoom() {
        isOK = true;
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getItems().clear();
        if (!rooms.isEmpty()) {
            addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getItems().addAll(rooms);
            addPathToRoom_ChoiceBox_From_Room_ChoiceBox.setValue(addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getItems().get(0));

            addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getItems().addAll(rooms);
            addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.setValue(addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        if (isOK) {
            result = new AddPathFromRoom(addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getSelectionModel().getSelectedItem(),
                    addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Add Path from Room");
        addPathFromRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceAddStaticObjectToRoom() {
        isOK = true;
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getItems().clear();
        if (!rooms.isEmpty()) {
            addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getItems().addAll(rooms);
            addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.setValue(addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        ArrayList<StaticObject> so = Game.getInstance().getAllStaticObjects();
        addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getItems().clear();
        if (!so.isEmpty()) {
            addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getItems().addAll(so);
            addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.setValue(addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new AddStaticObjectToRoom(
                    addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getSelectionModel().getSelectedItem(),
                    addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Add Static Object to Room");
        addStaticObjectToRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceDescribeStaticObject() {
        isOK = true;
        ArrayList<StaticObject> so = Game.getInstance().getAllStaticObjects();
        describeStaticObject_StaticObject_ChoiceBox.getItems().clear();
        if (!so.isEmpty() && describeStaticObject_StaticObject_ChoiceBox.getItems().isEmpty()) {
            describeStaticObject_StaticObject_ChoiceBox.getItems().addAll(so);
            describeStaticObject_StaticObject_ChoiceBox.setValue(describeStaticObject_StaticObject_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new DescribeStaticObject(describeStaticObject_StaticObject_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Describe Static Object");
        describeStaticObject_GridPane.setVisible(true);
    }

    @FXML
    private void choiceEnableGameEventListner() {
        isOK = true;
        ArrayList<GameEventListener> listeners = Game.getInstance().getAllGameEventListeners();
        enableGameEventListener_ChoiceBox.getItems().clear();
        if (!listeners.isEmpty()) {
            enableGameEventListener_ChoiceBox.getItems().addAll(listeners);
            enableGameEventListener_ChoiceBox.setValue(enableGameEventListener_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new EnableGameEventListener(enableGameEventListener_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Enable Event Listener");
        enableGameEventListener_GridPane.setVisible(true);
    }

    @FXML
    private void choiceDisableGameEventListner() {
        isOK = true;
        ArrayList<GameEventListener> listeners = Game.getInstance().getAllGameEventListeners();
        disableGameEventListener_ChoiceBox.getItems().clear();
        if (!listeners.isEmpty()) {
            disableGameEventListener_ChoiceBox.getItems().addAll(listeners);
            disableGameEventListener_ChoiceBox.setValue(disableGameEventListener_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new DisableGameEventListener(disableGameEventListener_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Disable Event Listener");
        disableGameEventListener_GridPane.setVisible(true);
    }

    @FXML
    private void choiceMovePlayerToRoom() {
        isOK = true;
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        movePlayerToRoom_ChoiceBox.getItems().clear();
        if (!rooms.isEmpty()) {
            movePlayerToRoom_ChoiceBox.getItems().addAll(rooms);
            movePlayerToRoom_ChoiceBox.setValue(movePlayerToRoom_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new MovePlayerToRoom(movePlayerToRoom_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Move player to room");
        movePlayerToRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceRemoveItemFromRoom() {
        isOK = true;
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();

        if (!rooms.isEmpty()) {
            removeItemFromRoom_Room_ChoiceBox.getItems().clear();
            removeItemFromRoom_Room_ChoiceBox.getItems().addAll(rooms);
            removeItemFromRoom_Room_ChoiceBox.setValue(removeItemFromRoom_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        ArrayList<Item> items = Game.getInstance().getAllItems();

        if (!items.isEmpty()) {
            removeOptionFromRoom_Option_ChoiceBox.getItems().clear();
            removeItemFromRoom_Item_ChoiceBox.getItems().addAll(items);
            removeItemFromRoom_Item_ChoiceBox.setValue(removeItemFromRoom_Item_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new RemoveItemFromRoom(removeItemFromRoom_Item_ChoiceBox.getSelectionModel().getSelectedItem(),
                    removeItemFromRoom_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        //result = new AddItemToRoom(,addItemToPlayer_ChoiceBox.getValues());
        resetLayout();
        currentActionLabel.setText("Remove Item from Room");
        removeItemFromRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceRemoveOptionFromRoom() {
        isOK = true;

        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        removeOptionFromRoom_Room_ChoiceBox.getItems().clear();
        if (!rooms.isEmpty()) {
            removeOptionFromRoom_Room_ChoiceBox.getItems().addAll(rooms);
            removeOptionFromRoom_Room_ChoiceBox.setValue(removeOptionFromRoom_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        ArrayList<Option> options = Game.getInstance().getAllOptions();

        if (!options.isEmpty()) {
            removeOptionFromRoom_Option_ChoiceBox.getItems().clear();
            removeOptionFromRoom_Option_ChoiceBox.getItems().addAll(options);
            removeOptionFromRoom_Option_ChoiceBox.setValue(removeOptionFromRoom_Option_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new RemoveOptionFromRoom(removeOptionFromRoom_Option_ChoiceBox.getSelectionModel().getSelectedItem(),
                    removeOptionFromRoom_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        //result = new AddItemToRoom(,addItemToPlayer_ChoiceBox.getValues());
        resetLayout();
        currentActionLabel.setText("Remove Option from Room");
        removeOptionFromRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceRemovePathFromRoom() {
        isOK = true;
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        removePathFromRoom_From_Room_ChoiceBox.getItems().clear();
        if (!rooms.isEmpty()) {
            removePathFromRoom_From_Room_ChoiceBox.getItems().addAll(rooms);
            removePathFromRoom_From_Room_ChoiceBox.setValue(removePathFromRoom_From_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (!rooms.isEmpty()) {
            removePathFromRoom_Where_Room_ChoiceBox.getItems().clear();
            removePathFromRoom_Where_Room_ChoiceBox.getItems().addAll(rooms);
            removePathFromRoom_Where_Room_ChoiceBox.setValue(removePathFromRoom_Where_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new RemovePathFromRoom(removePathFromRoom_From_Room_ChoiceBox.getSelectionModel().getSelectedItem(),
                    removePathFromRoom_Where_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        //result = new AddItemToRoom(,addItemToPlayer_ChoiceBox.getValues());
        resetLayout();
        currentActionLabel.setText("Remove Option from Room");
        removePathFromRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceRemoveStaticObjectFromRoom() {
        isOK = true;
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        if (!rooms.isEmpty()) {
            removeStaticObjectFromRoom_Room_ChoiceBox.getItems().clear();
            removeStaticObjectFromRoom_Room_ChoiceBox.getItems().addAll(rooms);
            removeStaticObjectFromRoom_Room_ChoiceBox.setValue(removeStaticObjectFromRoom_Room_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        ArrayList<StaticObject> so = Game.getInstance().getAllStaticObjects();
        if (!so.isEmpty()) {
            removeStaticObjectFromRoom_StaticObject_ChoiceBox.getItems().clear();
            removeStaticObjectFromRoom_StaticObject_ChoiceBox.getItems().addAll(so);
            removeStaticObjectFromRoom_StaticObject_ChoiceBox.setValue(removeStaticObjectFromRoom_StaticObject_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new RemoveStaticObjectFromRoom(removeStaticObjectFromRoom_StaticObject_ChoiceBox.getSelectionModel().getSelectedItem(),
                    removeStaticObjectFromRoom_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        }

        resetLayout();
        currentActionLabel.setText("Remove Static Object from Room");
        removeStaticObjectFromRoom_GridPane.setVisible(true);
    }

    @FXML
    private void choiceChangeItemDescription() {
        isOK = true;
        ArrayList<Item> items = Game.getInstance().getAllItems();
        if (!items.isEmpty()) {
            changeItemDescription_ChoiceBox.getItems().clear();
            changeItemDescription_ChoiceBox.getItems().addAll(items);
            changeItemDescription_ChoiceBox.setValue(changeItemDescription_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        resetLayout();
        if (isOK) {
            result = new ChangeItemDescription(changeItemDescription_ChoiceBox.getSelectionModel().getSelectedItem(),
                    changeItemDescription_TextArea.getText());
        }
        currentActionLabel.setText("Change Item description");
        changeItemDescription_GridPane.setVisible(true);
    }

    @FXML
    private void choiceChangeRoomDescription() {
        isOK = true;
        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        if (!rooms.isEmpty()) {
            changeRoomDescription_ChoiceBox.getItems().clear();
            changeRoomDescription_ChoiceBox.getItems().addAll(rooms);
            changeRoomDescription_ChoiceBox.setValue(changeRoomDescription_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        if (isOK) {
            result = new ChangeRoomDescription(changeRoomDescription_ChoiceBox.getSelectionModel().getSelectedItem(),
                    changeRoomDescription_TextArea.getText());
        }
        resetLayout();
        currentActionLabel.setText("Change Room description");
        changeRoomDescription_GridPane.setVisible(true);
    }

    @FXML
    private void choiceChangeStaticObjectDescription() {
        isOK = true;
        ArrayList<StaticObject> so = Game.getInstance().getAllStaticObjects();
        if (!so.isEmpty()) {
            changeStaticObjectDescription_ChoiceBox.getItems().clear();
            changeStaticObjectDescription_ChoiceBox.getItems().addAll(so);
            changeStaticObjectDescription_ChoiceBox.setValue(changeStaticObjectDescription_ChoiceBox.getItems().get(0));
        } else {
            isOK = false;
        }
        if (isOK) {
            result = new ChangeStaticObjectDescription(changeStaticObjectDescription_ChoiceBox.getSelectionModel().getSelectedItem(),
                    changeStaticObjectDescription_TextArea.getText());
        }
        resetLayout();
        currentActionLabel.setText("Change Static Object description");
        changeStaticObjectDescription_GridPane.setVisible(true);
    }

    @FXML
    private void choiceChangeStaticObjectName() {
        isOK = true;

        ArrayList<StaticObject> so = Game.getInstance().getAllStaticObjects();

        if (!so.isEmpty()) {
            changeStaticObjectName_ChoiceBox.getItems().clear();
            changeStaticObjectName_ChoiceBox.getItems().addAll(so);
            changeStaticObjectName_ChoiceBox.getSelectionModel().select(0);
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new ChangeStaticObjectName(changeStaticObjectName_ChoiceBox.getSelectionModel().getSelectedItem(),
                    changeStaticObjectName_TextField.getText());
        }
        resetLayout();
        currentActionLabel.setText("Change Static Object name");
        changeStaticObjectName_GridPane.setVisible(true);
    }

    @FXML
    private void choiceChangeRoomName() {
        isOK = true;

        ArrayList<Room> rooms = Game.getInstance().getAllRooms();

        if (!rooms.isEmpty()) {
            changeRoomName_ChoiceBox.getItems().clear();
            changeRoomName_ChoiceBox.getItems().addAll(rooms);
            changeRoomName_ChoiceBox.getSelectionModel().select(0);
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new ChangeRoomName(changeRoomName_ChoiceBox.getSelectionModel().getSelectedItem(),
                    changeRoomName_TextField.getText());
        }
        resetLayout();
        currentActionLabel.setText("Change Room name");
        changeRoomName_GridPane.setVisible(true);
    }

    @FXML
    private void choiceChangeItemName() {
        isOK = true;

        ArrayList<Item> items = Game.getInstance().getAllItems();

        if (!items.isEmpty()) {
            changeItemName_ChoiceBox.getItems().clear();
            changeItemName_ChoiceBox.getItems().addAll(items);
            changeItemName_ChoiceBox.getSelectionModel().select(0);
        } else {
            isOK = false;
        }

        if (isOK) {
            result = new ChangeItemName(changeItemName_ChoiceBox.getSelectionModel().getSelectedItem(),
                    changeItemName_TextField.getText());
        }
        resetLayout();
        currentActionLabel.setText("Change Item name");
        changeItemName_GridPane.setVisible(true);
    }

    @FXML
    private void choicePushMessage() {
        isOK = true;

        result = new PushMessage(pushMessage_TextArea.getText());

        resetLayout();
        currentActionLabel.setText("Push message");
        pushMessage_GridPane.setVisible(true);
    }

    @FXML
    private void choiceThrowGameEvent() {
        isOK = false;
        throwGameEvent_StackPane1_HBox1.setVisible(false);
        throwGameEvent_StackPane1_HBox2.setVisible(false);
        throwGameEvent_StackPane2_HBox1.setVisible(false);
        throwGameEvent_StackPane2_HBox2.setVisible(false);
        throwGameEvent_StackPane3_HBox1.setVisible(false);
        throwGameEvent_StackPane3_HBox2.setVisible(false);

        throwGameEvent_ChoiceBox_GameEvent.getItems().clear();
        throwGameEvent_ChoiceBox_GameEvent.getItems().addAll(GameEvent.GameEventType.values());

        resetLayout();
        currentActionLabel.setText("Throw game event: ");
        throwGameEvent_GridPane.setVisible(true);
    }

    @FXML
    private void choiceSetCustomValue() {
        custom(true);
    }

    @FXML
    private void choiceAddToCustomValue() {
        custom(false);
    }

    private void custom(boolean isSetting) {
        resetLayout();
        customValue_GridPane.setVisible(true);
        isOK = true;
        HashMap map = (HashMap) Game.getInstance().getPlayer().getCustomValues();
        customValue_choiceBox.getItems().clear();
        if (map.isEmpty()) {
            isOK = false;
        } else {
            customValue_choiceBox.getItems().addAll(map.keySet());
            customValue_choiceBox.getSelectionModel().select(0);
        }
        customValue_TextField.setText("0");

        if (isSetting) {
            if (isOK) {
                result = new SetCustomValue((String) customValue_choiceBox.getSelectionModel().getSelectedItem(), 0);
            } else {
                result = new SetCustomValue("Default", 1);
            }
            currentActionLabel.setText("Set Custom value");
        } else {
            if (isOK) {
                okButton.setDisable(!isOK);
                result = new AddToCustomValue((String) customValue_choiceBox.getSelectionModel().getSelectedItem(), 0);
            } else {
                result = new AddToCustomValue("Default", 1);
            }
            currentActionLabel.setText("Add to Custom value");
        }

    }

    @FXML
    private void choiceChangeRoomImage() {
        isOK = false;

        ArrayList<Room> rooms = Game.getInstance().getAllRooms();

        if (!rooms.isEmpty()) {
            changeRoomImage_ChoiceBox.getItems().clear();
            changeRoomImage_ChoiceBox.getItems().addAll(rooms);
            changeRoomImage_ChoiceBox.getSelectionModel().select(0);
        }
        resetLayout();
        currentActionLabel.setText("Change Room Image");
        changeRoomImage_GridPane.setVisible(true);
    }

    @FXML
    private void choiceChangeCurrentImage() {
        isOK = false;

        resetLayout();
        currentActionLabel.setText("Change Current Image");
        changeCurrentImage_GridPane.setVisible(true);
    }

    //------ChoiceBox_onAction--------------------------------------------------
    private void update_addItemToPlayer(Item item) {
        if (result instanceof AddItemToPlayer) {
            AddItemToPlayer temp = (AddItemToPlayer) result;
            temp.setWhatToAdd(item);
        }
    }

    private void update_pickUpItem() {
        if (result instanceof PickUpItem) {
            PickUpItem temp = (PickUpItem) result;
            temp.setWhatToPickUp(pickUpItem_ChoiceBox.getSelectionModel().getSelectedItem());
        }
    }

    private void update_addItemToRoom(Item item, Room room) {
        if (result instanceof AddItemToRoom) {
            AddItemToRoom temp = (AddItemToRoom) result;
            temp.setWhatToAdd(item);
            temp.setWhereToAdd(room);
        }
    }

    private void update_addPathToRoom(Room from, Room where) {
        if (result instanceof AddPathFromRoom) {
            AddPathFromRoom temp = (AddPathFromRoom) result;
            temp.setFromWhere(from);
            temp.setToWhere(where);
        }
    }

    private void update_addOptionToRoom(Option option, Room room) {
        if (result instanceof AddOptionToRoom) {
            AddOptionToRoom temp = (AddOptionToRoom) result;
            temp.setWhatToAdd(option);
            temp.setWhereToAdd(room);
        }
    }

    private void update_addStaticObjectToRoom(StaticObject so, Room room) {
        if (result instanceof AddStaticObjectToRoom) {
            AddStaticObjectToRoom temp = (AddStaticObjectToRoom) result;
            temp.setWhatToAdd(so);
            temp.setWhereToAdd(room);
        }
    }

    private void update_describeStaticObject(StaticObject so) {
        if (result instanceof DescribeStaticObject) {
            DescribeStaticObject temp = (DescribeStaticObject) result;
            temp.setWhatToDescribe(so);
        }
    }

    private void update_disableGameEventListener(GameEventListener gel) {
        if (result instanceof DisableGameEventListener) {
            DisableGameEventListener temp = (DisableGameEventListener) result;
            temp.setWhatToDisable(gel);
        }
    }

    private void update_enableGameEventListener(GameEventListener gel) {
        if (result instanceof EnableGameEventListener) {
            EnableGameEventListener temp = (EnableGameEventListener) result;
            temp.setWhatToEnable(gel);
        }
    }

    private void update_movePlayerToRoom(Room room) {
        if (result instanceof MovePlayerToRoom) {
            MovePlayerToRoom temp = (MovePlayerToRoom) result;
            temp.setWhereToMove(room);
        }
    }

    private void update_removeItemFromRoom(Item item, Room room) {
        if (result instanceof RemoveItemFromRoom) {
            RemoveItemFromRoom temp = (RemoveItemFromRoom) result;
            temp.setWhatToRemove(item);
            temp.setWhereToRemove(room);
        }
    }

    private void update_removeOptionFromRoom(Option option, Room room) {
        if (result instanceof RemoveOptionFromRoom) {
            RemoveOptionFromRoom temp = (RemoveOptionFromRoom) result;
            temp.setWhatToRemove(option);
            temp.setWhereToRemove(room);
        }
    }

    private void update_removePathFromRoom(Room from, Room where) {

        if (result instanceof RemovePathFromRoom) {
            RemovePathFromRoom temp = (RemovePathFromRoom) result;
            temp.setFromWhere(from);
            temp.setToWhere(where);
        }
    }

    private void update_removeStaticObjectFromRoom(StaticObject so, Room room) {
        if (result instanceof RemoveStaticObjectFromRoom) {
            RemoveStaticObjectFromRoom temp = (RemoveStaticObjectFromRoom) result;
            temp.setWhatToRemove(so);
            temp.setWhereToRemove(room);
        }
    }

    private void update_changeItemDescription(Item item, String text) {
        if (result instanceof ChangeItemDescription) {
            ChangeItemDescription temp = (ChangeItemDescription) result;
            temp.setWhatToChange(item);
            temp.setNewDescription(text);
        }
    }

    private void update_changeRoomDescription(Room room, String text) {
        if (result instanceof ChangeRoomDescription) {
            ChangeRoomDescription temp = (ChangeRoomDescription) result;
            temp.setWhatToChange(room);
            temp.setNewDesctiption(text);
        }
    }

    private void update_changeStaticObjectDescription(StaticObject so, String text) {
        if (result instanceof ChangeStaticObjectDescription) {
            ChangeStaticObjectDescription temp = (ChangeStaticObjectDescription) result;
            temp.setNewDescription(text);
            temp.setWhatToChange(so);
        }
    }

    private void update_changeStaticObjectName(StaticObject so, String text) {
        if (result instanceof ChangeStaticObjectName) {
            ChangeStaticObjectName temp = (ChangeStaticObjectName) result;
            temp.setNewName(text);
            temp.setWhatToChange(so);
        }
    }

    private void update_changeRoomName(Room room, String text) {
        if (result instanceof ChangeRoomName) {
            ChangeRoomName temp = (ChangeRoomName) result;
            temp.setNewName(text);
            temp.setWhatToChange(room);
        }
    }

    private void update_changeItemName(Item item, String text) {
        if (result instanceof ChangeItemName) {
            ChangeItemName temp = (ChangeItemName) result;
            temp.setNewName(text);
            temp.setWhatToChange(item);
        }
    }

    private void update_pushMessage(String text) {
        if (result instanceof PushMessage) {
            PushMessage temp = (PushMessage) result;
            temp.setMessage(text);
        }
    }

    private void update_throwGameEvent(GameEventType newValue) {
        if (newValue == null) {
            return;
        }
        throwGameEvent_StackPane1_HBox1.setVisible(false);
        throwGameEvent_StackPane1_HBox2.setVisible(false);
        throwGameEvent_StackPane2_HBox1.setVisible(false);
        throwGameEvent_StackPane2_HBox2.setVisible(false);
        throwGameEvent_StackPane3_HBox1.setVisible(false);
        throwGameEvent_StackPane3_HBox2.setVisible(false);
        isOK = false;
        //System.out.println("-ADD-ACTION-CONTROLLER-: throwGameEvent_ChoiceBox_GameEvent.getValue() ="+throwGameEvent_ChoiceBox_GameEvent.getValue());
        currentActionLabel.setText("Throw game event: " + newValue);
        System.out.println("-ADD-ACTION-CONTROLLER-:update_throwGameEvent:GameEventType = " + newValue);
        Class[] settingClasses = GameEvent.getSettingClasses(newValue);
        for (Class c : settingClasses) {
            System.out.println(c.toGenericString());
            System.out.println("== String.class? " + c.equals(Integer.class));
        }
        if (settingClasses.length > 0) {
            if (settingClasses[0].equals(String.class)) {
                throwGameEvent_StackPane1_HBox2.setVisible(true);
                throwGameEvent_TextField1_isNumber = false;
                throwGameEvent_TextField1_Label.setText("Text:");
            } else if (settingClasses[0].equals(Integer.class)) {
                throwGameEvent_StackPane1_HBox2.setVisible(true);
                throwGameEvent_TextField1_isNumber = true;
                throwGameEvent_TextField1_Label.setText("Value:");
                if (newValue == GameEventType.RANDOM_NUMBER) {
                    throwGameEvent_TextField1_Label.setText("Min: ");
                }
            } else {
                throwGameEvent_StackPane1_HBox1.setVisible(true);
                throwGameEvent_ChoiceBox1.getItems().clear();
                throwGameEvent_ChoiceBox1.getItems().addAll(Game.getInstance().getAllIntancesOf(settingClasses[0]));
                throwGameEvent_ChoiceBox1_Label.setText(getLabelForClass(settingClasses[0]));
            }
        }
        if (settingClasses.length > 1) {
            if (settingClasses[1].equals(String.class)) {
                throwGameEvent_StackPane2_HBox2.setVisible(true);
                throwGameEvent_TextField2_isNumber = false;
                throwGameEvent_TextField2_Label.setText("Text:");
            } else if (settingClasses[1].equals(Integer.class)) {
                throwGameEvent_StackPane2_HBox2.setVisible(true);
                throwGameEvent_TextField2_isNumber = true;
                throwGameEvent_TextField2_Label.setText("Value:");
                if (newValue == GameEventType.RANDOM_NUMBER) {
                    throwGameEvent_TextField2_Label.setText("Max: ");
                }
            } else {
                throwGameEvent_StackPane2_HBox1.setVisible(true);
                throwGameEvent_ChoiceBox2.getItems().clear();
                throwGameEvent_ChoiceBox2.getItems().addAll(Game.getInstance().getAllIntancesOf(settingClasses[1]));
                throwGameEvent_ChoiceBox2_Label.setText(getLabelForClass(settingClasses[1]));
            }
        }
        if (settingClasses.length > 2) {
            if (settingClasses[2].equals(String.class)) {
                throwGameEvent_StackPane3_HBox2.setVisible(true);
                throwGameEvent_TextField3_isNumber = false;
                throwGameEvent_TextField3_Label.setText("Text:");
            } else if (settingClasses[2].equals(Integer.class)) {
                throwGameEvent_StackPane3_HBox1.setVisible(true);
                throwGameEvent_TextField3_isNumber = true;
                throwGameEvent_TextField3_Label.setText("Value:");
            } else {
                throwGameEvent_StackPane3_HBox2.setVisible(true);
                throwGameEvent_ChoiceBox3.getItems().clear();
                throwGameEvent_ChoiceBox3.getItems().addAll(Game.getInstance().getAllIntancesOf(settingClasses[2]));
                throwGameEvent_ChoiceBox3_Label.setText(getLabelForClass(settingClasses[2]));
            }
        }

    }

    private void update_throwGameEvent_Values(Object newValue, Object value, Object value1, String text, String text1, String text2) {
        isOK = true;
        GameEvent ge = null;
        System.out.println("-ADD-ACTION-CONTROLLER-: Game event: " + throwGameEvent_ChoiceBox_GameEvent.getSelectionModel().getSelectedItem());
        Class[] settingClasses = GameEvent.getSettingClasses(throwGameEvent_ChoiceBox_GameEvent.getSelectionModel().getSelectedItem());
        ge = GameEvent.getInstanceOf(throwGameEvent_ChoiceBox_GameEvent.getSelectionModel().getSelectedItem());
        System.out.println("GE IS INSTANCE OF " + throwGameEvent_ChoiceBox_GameEvent.getSelectionModel().getSelectedItem());
        Object[] settingObject = new Object[settingClasses.length];

        switch (settingClasses.length) {
            case 3:
                settingObject[2] = set_values_shortcut(throwGameEvent_TextField3, throwGameEvent_ChoiceBox3, settingClasses[2]);

            case 2:
                settingObject[1] = set_values_shortcut(throwGameEvent_TextField2, throwGameEvent_ChoiceBox2, settingClasses[1]);

            case 1:
                settingObject[0] = set_values_shortcut(throwGameEvent_TextField1, throwGameEvent_ChoiceBox1, settingClasses[0]);
            default:
                break;
        }

        for (int i = 0; i < settingClasses.length; i++) {
            if (settingObject[i] == null) {
                isOK = false;
                System.out.println("SETTING OBJECT IS FALSE BECAUSE settingObject[" + i + "] IS null");
                break;
            }
        }
        if (isOK) {
            System.out.println("NEW ACTION CRETED");
            ge.setValues(settingObject);
            result = new ThrowGameEvent(ge);
        }
        okButton.setDisable(!isOK);
    }

    private void update_changeCurrentImage() {
        isOK = true;
        result = new ChangeCurrentImage(changeCurrentImage_ImageView.getImage());
        resetLayout();
        changeCurrentImage_GridPane.setVisible(isOK);

    }

    private void update_customValue(String key, String value) {
        isOK = true;
        if (!value.matches("\\d*")) {
            customValue_TextField.setText(value.replaceAll("[^\\d]", ""));
            value = value.replaceAll("[^\\d]", "");
        }

        if (result instanceof AddToCustomValue) {
            AddToCustomValue temp = (AddToCustomValue) result;
            if (value.equals("")) {
                temp.setValue(0);
            } else {
                temp.setValue(Integer.parseInt(value));
            }
            if (key!= null) {
                temp.setValueName(key);
            } else {
                //System.out.println("-ADD-ACTION-CONTROLLER-:update_customValue, customValue_choiceBox.getValue() is NULL");
                isOK = false;
            }
        } else if (result instanceof SetCustomValue) {
            SetCustomValue temp = (SetCustomValue) result;
            if (value.equals("")) {
                temp.setValue(0);
            } else {
                temp.setValue(Integer.parseInt(value));
            }
            if (key != null) {
                temp.setValueName(key);
            } else {
                isOK = false;
            }
        } else {
            isOK = false;
        }
        okButton.setDisable(!isOK);
    }

    private void update_changeRoomImage(Room room) {
        isOK = false;
        if (room!=null && changeRoomImage_ImageView.getImage() != null) {
            isOK = true;
            result = new ChangeRoomImage(changeRoomImage_ImageView.getImage(), room);
        }
        resetLayout();
        changeRoomImage_GridPane.setVisible(true);
    }

    //---------------------------------------------------------------------------
    private void resetLayout() {
        okButton.setDisable(!isOK);
        currentActionLabel.setText("");

        customValue_GridPane.setVisible(false);
        addItemToPlayer_GridPane.setVisible(false);
        showPlayerInventor_GridPane.setVisible(false);
        addItemToRoom_GridPane.setVisible(false);
        addOptionToRoom_GridPane.setVisible(false);
        addPathFromRoom_GridPane.setVisible(false);
        addStaticObjectToRoom_GridPane.setVisible(false);
        describeStaticObject_GridPane.setVisible(false);
        disableGameEventListener_GridPane.setVisible(false);
        enableGameEventListener_GridPane.setVisible(false);
        movePlayerToRoom_GridPane.setVisible(false);
        removeItemFromRoom_GridPane.setVisible(false);
        removeOptionFromRoom_GridPane.setVisible(false);
        removePathFromRoom_GridPane.setVisible(false);
        removeStaticObjectFromRoom_GridPane.setVisible(false);
        changeRoomDescription_GridPane.setVisible(false);
        changeItemDescription_GridPane.setVisible(false);
        changeStaticObjectDescription_GridPane.setVisible(false);
        changeStaticObjectName_GridPane.setVisible(false);
        changeRoomName_GridPane.setVisible(false);
        changeItemName_GridPane.setVisible(false);
        winGame_GridPane.setVisible(false);
        loseGame_GridPane.setVisible(false);
        pickUpItem_GridPane.setVisible(false);
        pushMessage_GridPane.setVisible(false);
        throwGameEvent_GridPane.setVisible(false);
        changeCurrentImage_GridPane.setVisible(false);
        changeRoomImage_GridPane.setVisible(false);

    }

    private void setListeners() {
        addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room temp = addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_addStaticObjectToRoom(addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getSelectionModel().getSelectedItem(),temp);
        });
        addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            StaticObject temp = addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getItems().get((Integer)newValue);
            update_addStaticObjectToRoom(temp,addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        });
        //------------------------------------------------------------------------
        removeStaticObjectFromRoom_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room temp = removeStaticObjectFromRoom_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_removeStaticObjectFromRoom(removeStaticObjectFromRoom_StaticObject_ChoiceBox.getSelectionModel().getSelectedItem(), temp);
        });
        removeStaticObjectFromRoom_StaticObject_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            StaticObject temp = removeStaticObjectFromRoom_StaticObject_ChoiceBox.getItems().get((Integer)newValue);
            update_removeStaticObjectFromRoom(temp, removeStaticObjectFromRoom_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        });
        //------------------------------------------------------------------------
        addOptionToRoom_ChoiceBox_Option_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Option option = addOptionToRoom_ChoiceBox_Option_ChoiceBox.getItems().get((Integer)newValue);
            update_addOptionToRoom(option, addOptionToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        });
        addOptionToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = addOptionToRoom_ChoiceBox_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_addOptionToRoom(addOptionToRoom_ChoiceBox_Option_ChoiceBox.getSelectionModel().getSelectedItem(), room);
        });
        //------------------------------------------------------------------------
        removeOptionFromRoom_Option_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Option option = removeOptionFromRoom_Option_ChoiceBox.getItems().get((Integer)newValue);
            update_removeOptionFromRoom(option, removeOptionFromRoom_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        });
        removeOptionFromRoom_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = removeOptionFromRoom_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_removeOptionFromRoom(removeOptionFromRoom_Option_ChoiceBox.getSelectionModel().getSelectedItem(), room);
        });
        //------------------------------------------------------------------------
        addItemToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = addItemToRoom_ChoiceBox_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_addItemToRoom(addItemToRoom_ChoiceBox_Item_ChoiceBox.getSelectionModel().getSelectedItem(),room);
        });
        addItemToRoom_ChoiceBox_Item_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Item item = addItemToRoom_ChoiceBox_Item_ChoiceBox.getItems().get((Integer)newValue);
            update_addItemToRoom(item,addItemToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        });
        //------------------------------------------------------------------------
        removeItemFromRoom_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = removeItemFromRoom_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_removeItemFromRoom(removeItemFromRoom_Item_ChoiceBox.getSelectionModel().getSelectedItem(),room);
        });
        removeItemFromRoom_Item_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Item item = removeItemFromRoom_Item_ChoiceBox.getItems().get((Integer)newValue);
            update_removeItemFromRoom(item,removeItemFromRoom_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        });
        //------------------------------------------------------------------------
        addItemToPlayer_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Item item = addItemToPlayer_ChoiceBox.getItems().get((Integer)newValue);
            update_addItemToPlayer(item);
        });
         //------------------------------------------------------------------------
        addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
             Room room = addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_addPathToRoom(addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getSelectionModel().getSelectedItem(),room);
        });
        addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_addPathToRoom(addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getSelectionModel().getSelectedItem(),room);
        });
        //------------------------------------------------------------------------
        movePlayerToRoom_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
             Room room = movePlayerToRoom_ChoiceBox.getItems().get((Integer)newValue);
            update_movePlayerToRoom(room);
        });
        //------------------------------------------------------------------------
        removePathFromRoom_From_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = removePathFromRoom_From_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_removePathFromRoom(room, removePathFromRoom_From_Room_ChoiceBox.getSelectionModel().getSelectedItem());
        });
        removePathFromRoom_Where_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = removePathFromRoom_Where_Room_ChoiceBox.getItems().get((Integer)newValue);
            update_removePathFromRoom(removePathFromRoom_From_Room_ChoiceBox.getSelectionModel().getSelectedItem(),room);
        });
        //------------------------------------------------------------------------
        describeStaticObject_StaticObject_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            StaticObject so = describeStaticObject_StaticObject_ChoiceBox.getItems().get((Integer)newValue);
            update_describeStaticObject(so);
        });
        //------------------------------------------------------------------------
        disableGameEventListener_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            GameEventListener gel = disableGameEventListener_ChoiceBox.getItems().get((Integer)newValue);
            update_disableGameEventListener(gel);
        });
        enableGameEventListener_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            GameEventListener gel = enableGameEventListener_ChoiceBox.getItems().get((Integer)newValue);
            update_enableGameEventListener(gel);
        });
        //------------------------------------------------------------------------
        
        changeItemDescription_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Item item = changeItemDescription_ChoiceBox.getItems().get((Integer)newValue);
            update_changeItemDescription(item,changeItemDescription_TextArea.getText());
        });
        changeItemDescription_TextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            update_changeItemDescription(changeItemDescription_ChoiceBox.getSelectionModel().getSelectedItem(),newValue);
        });
        //------------------------------------------------------------------------
        changeRoomDescription_TextArea.textProperty().addListener((observable, oldValue, newValue) -> {  
            update_changeRoomDescription(changeRoomDescription_ChoiceBox.getSelectionModel().getSelectedItem(),newValue);
        });
        
        changeRoomDescription_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = changeRoomDescription_ChoiceBox.getItems().get((Integer)newValue);
            update_changeRoomDescription(room,changeRoomDescription_TextArea.getText());
        });
        //------------------------------------------------------------------------
        changeStaticObjectDescription_TextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            update_changeStaticObjectDescription(changeStaticObjectDescription_ChoiceBox.getSelectionModel().getSelectedItem(), newValue);
        });
        changeStaticObjectDescription_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            StaticObject so = changeStaticObjectDescription_ChoiceBox.getItems().get((Integer)newValue);
            update_changeStaticObjectDescription(so,changeStaticObjectDescription_TextArea.getText());
        });
        //------------------------------------------------------------------------
        changeStaticObjectName_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            update_changeStaticObjectName(changeStaticObjectName_ChoiceBox.getSelectionModel().getSelectedItem(),newValue);
        });
        
        changeStaticObjectName_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            StaticObject so = newValue;
            update_changeStaticObjectName(so, changeStaticObjectName_TextField.getText());
        });
        //------------------------------------------------------------------------
        changeRoomName_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            update_changeRoomName(changeRoomName_ChoiceBox.getSelectionModel().getSelectedItem(),newValue);
        });
        changeRoomName_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            update_changeRoomName(newValue,changeRoomName_TextField.getText());
        });
        //------------------------------------------------------------------------
        changeItemName_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            update_changeItemName(changeItemName_ChoiceBox.getSelectionModel().getSelectedItem(),newValue);
        });
        changeItemName_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            update_changeItemName(newValue, changeItemName_TextField.getText());
        });
        //------------------------------------------------------------------------
        pushMessage_TextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            update_pushMessage(newValue);
        });
        //------------------------------------------------------------------------
        customValue_choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            String s = customValue_choiceBox.getItems().get((Integer)newValue);
            update_customValue(s,customValue_TextField.getText());
        });
        customValue_TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            update_customValue(customValue_choiceBox.getSelectionModel().getSelectedItem(),newValue);
        });
        //------------------------------------------------------------------------
        changeRoomImage_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            Room room = changeRoomImage_ChoiceBox.getItems().get((Integer)newValue);
            update_changeRoomImage(room);
        });
        changeRoomImage_ImageView.setOnDragOver((event) -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        changeRoomImage_ImageView.setOnDragDropped((event) -> {
            try {
                List<File> files = event.getDragboard().getFiles();
                Image img = new Image(new FileInputStream(files.get(0)));
                changeRoomImage_ImageView.setImage(img);
                update_changeRoomImage(changeRoomImage_ChoiceBox.getSelectionModel().getSelectedItem());
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
                Logger.getLogger(GameEditorFXMLController.class.getName()).log(Level.SEVERE, null, ex);

            }
        });
        //------------------------------------------------------------------------
        changeCurrentImage_ImageView.setOnDragOver((event) -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        changeCurrentImage_ImageView.setOnDragDropped((event) -> {
            try {
                List<File> files = event.getDragboard().getFiles();
                Image img = new Image(new FileInputStream(files.get(0)));
                changeCurrentImage_ImageView.setImage(img);
                update_changeCurrentImage();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
                Logger.getLogger(GameEditorFXMLController.class.getName()).log(Level.SEVERE, null, ex);

            }
        });
        //------------------------------------------------------------------------
        throwGameEvent_ChoiceBox_GameEvent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("--ADD-ACTION-CONTROLLER-:throwGameEvent_ChoiceBox_GameEvent value " + throwGameEvent_ChoiceBox_GameEvent);
            System.out.println("--ADD-ACTION-CONTROLLER-:observable value " + observable);
            System.out.println("Old value:" + oldValue);
            System.out.println("New value:" + newValue);
            update_throwGameEvent(newValue);
        });

        throwGameEvent_ChoiceBox1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            update_throwGameEvent_Values(
                    newValue,
                    throwGameEvent_ChoiceBox2.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox3.getSelectionModel().getSelectedItem(),
                    throwGameEvent_TextField1.getText(),
                    throwGameEvent_TextField2.getText(),
                    throwGameEvent_TextField3.getText());
        });
        throwGameEvent_ChoiceBox2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            update_throwGameEvent_Values(throwGameEvent_ChoiceBox1.getSelectionModel().getSelectedItem(),
                    newValue,
                    throwGameEvent_ChoiceBox3.getSelectionModel().getSelectedItem(),
                    throwGameEvent_TextField1.getText(),
                    throwGameEvent_TextField2.getText(),
                    throwGameEvent_TextField3.getText());
        });
        throwGameEvent_ChoiceBox3.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            update_throwGameEvent_Values(throwGameEvent_ChoiceBox1.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox2.getSelectionModel().getSelectedItem(),
                    newValue,
                    throwGameEvent_TextField1.getText(),
                    throwGameEvent_TextField2.getText(),
                    throwGameEvent_TextField3.getText());
        });

        throwGameEvent_TextField1.textProperty().addListener((observable, oldValue, newValue) -> {
            update_throwGameEvent_Values(throwGameEvent_ChoiceBox1.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox2.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox3.getSelectionModel().getSelectedItem(),
                    newValue,
                    throwGameEvent_TextField2.getText(),
                    throwGameEvent_TextField3.getText());
        });
        throwGameEvent_TextField2.textProperty().addListener((observable, oldValue, newValue) -> {
            update_throwGameEvent_Values(throwGameEvent_ChoiceBox1.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox2.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox3.getSelectionModel().getSelectedItem(),
                    throwGameEvent_TextField1.getText(),
                    newValue,
                    throwGameEvent_TextField3.getText());
        });
        throwGameEvent_TextField3.textProperty().addListener((observable, oldValue, newValue) -> {
            update_throwGameEvent_Values(throwGameEvent_ChoiceBox1.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox2.getSelectionModel().getSelectedItem(),
                    throwGameEvent_ChoiceBox3.getSelectionModel().getSelectedItem(),
                    throwGameEvent_TextField1.getText(),
                    throwGameEvent_TextField2.getText(),
                    newValue);
        });
        //------------------------------------------------------------------------
    }

    private String getLabelForClass(Class c) {
        if (c.equals(Room.class)) {
            return "Room: ";
        }
        if (c.equals(StaticObject.class)) {
            return "StaticObject: ";
        }
        if (c.equals(Option.class)) {
            return "Option: ";
        }
        if (c.equals(Item.class)) {
            return "Item: ";
        }
        if (c.equals(GameEventListener.class)) {
            return "GameEventListener: ";
        }
        return "Unknown value: ";
    }

    private Object set_values_shortcut(TextField textField, ChoiceBox choiceBox, Class settingClass) {
        if (settingClass.equals(String.class)) {
            String s = textField.getText();

            if (!"".equals(s)) {
                return new String(s);
            } else {
                isOK = false;
                System.out.println("--ADD-ACTION-CONTROLLER-: isOK = false because string is empty");
            }
        }
        if (settingClass.equals(Integer.class)) {
            String s = textField.getText();
            if (!s.matches("\\d*")) {
                textField.setText(s.replaceAll("[^\\d]", ""));
            }
            if (s != "") {
                return new Integer(Integer.parseInt(s));
            } else {
                isOK = false;
                System.out.println("--ADD-ACTION-CONTROLLER-: isOK = false because string is empty");
            }
        } else {
            Object o = choiceBox.getSelectionModel().getSelectedItem();
            if (o != null) {
                return o;
            } else {
                isOK = false;
                System.out.println("--ADD-ACTION-CONTROLLER-: isOK = false because choiceBox.getValue is empty");
            }
        }
        System.out.println("--ADD-ACTION-CONTROLLER-: set_values_shortcut RETURNS NULL");
        return null;
    }
}
