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
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import textgame.structure.actions.EnableGameEventListner;
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
            changeCurrentImage_ImageView;

    @FXML
    private HBox throwGameEvent_ChoiceBox_HBox,
            throwGameEvent_SingleTextField_HBox,
            throwGameEvent_DoubleTextField_HBox;

    @FXML
    private TextArea changeItemDescription_TextArea,
            changeRoomDescription_TextArea,
            changeStaticObjectDescription_TextArea,
            pushMessage_TextArea;

    @FXML
    private TextField changeStaticObjectName_TextField,
            changeRoomName_TextField,
            changeItemName_TextField,
            throwGameEvent_SingleTextField_TextField,
            throwGameEvent_DoubleTextField_TextField_1,
            throwGameEvent_DoubleTextField_TextField_2,
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
    private ChoiceBox throwGameEvent_ChoiceBox,
            customValue_choiceBox;

    @FXML
    private Label throwGameEvent_ChoiceBox_Label;

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
            result = new AddItemToPlayer(addItemToPlayer_ChoiceBox.getValue());
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
            result = new PickUpItem(pickUpItem_ChoiceBox.getValue());
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
            result = new AddItemToRoom(addItemToRoom_ChoiceBox_Room_ChoiceBox.getValue(),
                    addItemToRoom_ChoiceBox_Item_ChoiceBox.getValue());
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
            result = new AddOptionToRoom(addOptionToRoom_ChoiceBox_Option_ChoiceBox.getValue(),
                    addOptionToRoom_ChoiceBox_Room_ChoiceBox.getValue());
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
            result = new AddPathFromRoom(addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getValue(),
                    addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getValue());
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
                    addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getValue(),
                    addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getValue());
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
            result = new DescribeStaticObject(describeStaticObject_StaticObject_ChoiceBox.getValue());
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
            result = new EnableGameEventListner(enableGameEventListener_ChoiceBox.getValue());
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
            result = new DisableGameEventListener(disableGameEventListener_ChoiceBox.getValue());
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
            result = new MovePlayerToRoom(movePlayerToRoom_ChoiceBox.getValue());
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
            result = new RemoveItemFromRoom(removeItemFromRoom_Item_ChoiceBox.getValue(),
                    removeItemFromRoom_Room_ChoiceBox.getValue());
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
            result = new RemoveOptionFromRoom(removeOptionFromRoom_Option_ChoiceBox.getValue(),
                    removeOptionFromRoom_Room_ChoiceBox.getValue());
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
            result = new RemovePathFromRoom(removePathFromRoom_From_Room_ChoiceBox.getValue(),
                    removePathFromRoom_Where_Room_ChoiceBox.getValue());
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
            result = new RemoveStaticObjectFromRoom(removeStaticObjectFromRoom_StaticObject_ChoiceBox.getValue(),
                    removeStaticObjectFromRoom_Room_ChoiceBox.getValue());
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
            result = new ChangeItemDescription(changeItemDescription_ChoiceBox.getValue(),
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
            result = new ChangeRoomDescription(changeRoomDescription_ChoiceBox.getValue(),
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
            result = new ChangeStaticObjectDescription(changeStaticObjectDescription_ChoiceBox.getValue(),
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
            result = new ChangeStaticObjectName(changeStaticObjectName_ChoiceBox.getValue(),
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
            result = new ChangeRoomName(changeRoomName_ChoiceBox.getValue(),
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
            result = new ChangeItemName(changeItemName_ChoiceBox.getValue(),
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

        throwGameEvent_ChoiceBox_HBox.setVisible(true);
        throwGameEvent_DoubleTextField_HBox.setVisible(false);
        throwGameEvent_SingleTextField_HBox.setVisible(false);

        GameEvent.GameEventType geType = throwGameEvent_ChoiceBox_GameEvent.getValue();

        ArrayList<Object> objects = new ArrayList();
        objects.addAll(Game.getInstance().getAllIntancesOf(GameEvent.getReturnClass(geType)));

        throwGameEvent_ChoiceBox.getItems().clear();
        throwGameEvent_ChoiceBox.getItems().addAll(objects);
        throwGameEvent_ChoiceBox.getSelectionModel().select(0);

        resetLayout();
        currentActionLabel.setText("Throw game event: " + throwGameEvent_ChoiceBox_GameEvent.getValue());
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
        if (isOK) {
            if (isSetting) {
                result = new SetCustomValue((String) customValue_choiceBox.getSelectionModel().getSelectedItem(), 0);
                currentActionLabel.setText("Set Custom value");
            } else {
                result = new AddToCustomValue((String) customValue_choiceBox.getSelectionModel().getSelectedItem(), 0);
                currentActionLabel.setText("Add to Custom value");
            }
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
    private void update_addItemToPlayer() {
        if (result instanceof AddItemToPlayer) {
            AddItemToPlayer temp = (AddItemToPlayer) result;
            temp.setWhatToAdd(addItemToPlayer_ChoiceBox.getValue());
        }
    }

    private void update_pickUpItem() {
        if (result instanceof PickUpItem) {
            PickUpItem temp = (PickUpItem) result;
            temp.setWhatToPickUp(pickUpItem_ChoiceBox.getValue());
        }
    }

    private void update_addItemToRoom() {
        if (result instanceof AddItemToRoom) {
            AddItemToRoom temp = (AddItemToRoom) result;
            temp.setWhatToAdd(addItemToRoom_ChoiceBox_Item_ChoiceBox.getValue());
            temp.setWhereToAdd(addItemToRoom_ChoiceBox_Room_ChoiceBox.getValue());
        }
    }

    private void update_addPathToRoom() {
        if (result instanceof AddPathFromRoom) {
            AddPathFromRoom temp = (AddPathFromRoom) result;
            temp.setFromWhere(addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getValue());
            temp.setToWhere(addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getValue());
        }
    }

    private void update_addOptionToRoom() {
        if (result instanceof AddOptionToRoom) {
            AddOptionToRoom temp = (AddOptionToRoom) result;
            temp.setWhatToAdd(addOptionToRoom_ChoiceBox_Option_ChoiceBox.getValue());
            temp.setWhereToAdd(addOptionToRoom_ChoiceBox_Room_ChoiceBox.getValue());
        }
    }

    private void update_addStaticObjectToRoom() {
        if (result instanceof AddStaticObjectToRoom) {
            AddStaticObjectToRoom temp = (AddStaticObjectToRoom) result;
            temp.setWhatToAdd(addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getValue());
            temp.setWhereToAdd(addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getValue());
        }
    }

    private void update_describeStaticObject() {
        if (result instanceof DescribeStaticObject) {
            DescribeStaticObject temp = (DescribeStaticObject) result;
            temp.setWhatToDescribe(describeStaticObject_StaticObject_ChoiceBox.getValue());
        }
    }

    private void update_disableGameEventListener() {
        if (result instanceof DisableGameEventListener) {
            DisableGameEventListener temp = (DisableGameEventListener) result;
            temp.setWhatToDisable(disableGameEventListener_ChoiceBox.getValue());
        }
    }

    private void update_enableGameEventListener() {
        if (result instanceof EnableGameEventListner) {
            EnableGameEventListner temp = (EnableGameEventListner) result;
            temp.setWhatToEnable(enableGameEventListener_ChoiceBox.getValue());
        }
    }

    private void update_movePlayerToRoom() {
        if (result instanceof MovePlayerToRoom) {
            MovePlayerToRoom temp = (MovePlayerToRoom) result;
            temp.setWhereToMove(movePlayerToRoom_ChoiceBox.getValue());
        }
    }

    private void update_removeItemFromRoom() {
        if (result instanceof RemoveItemFromRoom) {
            RemoveItemFromRoom temp = (RemoveItemFromRoom) result;
            temp.setWhatToRemove(removeItemFromRoom_Item_ChoiceBox.getValue());
            temp.setWhereToRemove(removeItemFromRoom_Room_ChoiceBox.getValue());
        }
    }

    private void update_removeOptionFromRoom() {
        if (result instanceof RemoveOptionFromRoom) {
            RemoveOptionFromRoom temp = (RemoveOptionFromRoom) result;
            temp.setWhatToRemove(removeOptionFromRoom_Option_ChoiceBox.getValue());
            temp.setWhereToRemove(removeOptionFromRoom_Room_ChoiceBox.getValue());
        }
    }

    private void update_removePathFromRoom() {

        if (result instanceof RemovePathFromRoom) {
            RemovePathFromRoom temp = (RemovePathFromRoom) result;
            temp.setFromWhere(removePathFromRoom_From_Room_ChoiceBox.getValue());
            temp.setToWhere(removePathFromRoom_Where_Room_ChoiceBox.getValue());
        }
    }

    private void update_removeStaticObjectFromRoom() {
        if (result instanceof RemoveStaticObjectFromRoom) {
            RemoveStaticObjectFromRoom temp = (RemoveStaticObjectFromRoom) result;
            temp.setWhatToRemove(removeStaticObjectFromRoom_StaticObject_ChoiceBox.getValue());
            temp.setWhereToRemove(removeStaticObjectFromRoom_Room_ChoiceBox.getValue());
        }
    }

    private void update_changeItemDescription() {
        if (result instanceof ChangeItemDescription) {
            ChangeItemDescription temp = (ChangeItemDescription) result;
            temp.setWhatToChange(changeItemDescription_ChoiceBox.getValue());
            temp.setNewDescription(changeItemDescription_TextArea.getText());
        }
    }

    private void update_changeRoomDescription() {
        if (result instanceof ChangeRoomDescription) {
            ChangeRoomDescription temp = (ChangeRoomDescription) result;
            temp.setWhatToChange(changeRoomDescription_ChoiceBox.getValue());
            temp.setNewDesctiption(changeRoomDescription_TextArea.getText());
        }
    }

    private void update_changeStaticObjectDescription() {
        if (result instanceof ChangeStaticObjectDescription) {
            ChangeStaticObjectDescription temp = (ChangeStaticObjectDescription) result;
            temp.setNewDescription(changeStaticObjectDescription_TextArea.getText());
            temp.setWhatToChange(changeStaticObjectDescription_ChoiceBox.getValue());
        }
    }

    private void update_changeStaticObjectName() {
        if (result instanceof ChangeStaticObjectName) {
            ChangeStaticObjectName temp = (ChangeStaticObjectName) result;
            temp.setNewName(changeStaticObjectName_TextField.getText());
            temp.setWhatToChange(changeStaticObjectName_ChoiceBox.getValue());
        }
    }

    private void update_changeRoomName() {
        if (result instanceof ChangeRoomName) {
            ChangeRoomName temp = (ChangeRoomName) result;
            temp.setNewName(changeRoomName_TextField.getText());
            temp.setWhatToChange(changeRoomName_ChoiceBox.getValue());
        }
    }

    private void update_changeItemName() {
        if (result instanceof ChangeItemName) {
            ChangeItemName temp = (ChangeItemName) result;
            temp.setNewName(changeItemName_TextField.getText());
            temp.setWhatToChange(changeItemName_ChoiceBox.getValue());
        }
    }

    private void update_pushMessage() {
        if (result instanceof PushMessage) {
            PushMessage temp = (PushMessage) result;
            temp.setMessage(pushMessage_TextArea.getText());
        }
    }

    private void update_throwGameEvent() {
        try {

            isOK = false;
            currentActionLabel.setText("Throw game event: " + throwGameEvent_ChoiceBox_GameEvent.getValue());
            GameEvent ge = null;
            Class settingClass = GameEvent.getSettingClass(throwGameEvent_ChoiceBox_GameEvent.getValue());
            throwGameEvent_ChoiceBox_HBox.setVisible(false);
            throwGameEvent_SingleTextField_HBox.setVisible(false);
            throwGameEvent_DoubleTextField_HBox.setVisible(false);

            GameEventType geType = throwGameEvent_ChoiceBox_GameEvent.getValue();
            if (geType.equals(GameEventType.RANDOM_NUMBER)) {
                throwGameEvent_DoubleTextField_HBox.setVisible(true);

                String maxStr = throwGameEvent_DoubleTextField_TextField_2.getText();
                String minStr = throwGameEvent_DoubleTextField_TextField_1.getText();
                if (!maxStr.matches("\\d*")) {
                    throwGameEvent_DoubleTextField_TextField_2.setText(maxStr.replaceAll("[^\\d]", ""));
                }
                if (!minStr.matches("\\d*")) {
                    throwGameEvent_DoubleTextField_TextField_1.setText(minStr.replaceAll("[^\\d]", ""));
                }

                boolean tf1isEmpty = throwGameEvent_DoubleTextField_TextField_1.getText().isEmpty();
                boolean tf2isEmpty = throwGameEvent_DoubleTextField_TextField_2.getText().isEmpty();

                if (!tf1isEmpty && !tf2isEmpty) {
                    int min = Integer.parseInt(throwGameEvent_DoubleTextField_TextField_1.getText());
                    int max = Integer.parseInt(throwGameEvent_DoubleTextField_TextField_2.getText());
                    if (min <= max) {
                        ge = new RandomNumber(max, min);
                        isOK = true;
                    }
                }
            } else if (settingClass.equals(String.class)) {
                ge = (GameEvent) settingClass.newInstance();
                ge.setValues(throwGameEvent_SingleTextField_TextField.getText());
                isOK = true;

            } else if (settingClass.equals(null)) {
                ge = (GameEvent) settingClass.newInstance();
                isOK = true;
            } else if (!throwGameEvent_ChoiceBox.getItems().isEmpty()) {
                ge = (GameEvent) settingClass.newInstance();
                ge.setValues(throwGameEvent_ChoiceBox.getValue());
                isOK = true;
            }

            if (ge != null) {
                result = new ThrowGameEvent(ge);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AddActionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update_changeCurrentImage() {
        isOK=true;
        result = new ChangeCurrentImage(changeCurrentImage_ImageView.getImage());
        resetLayout();
        changeCurrentImage_GridPane.setVisible(isOK);
        
                
    }

    private void update_customValue() {
        if (!customValue_TextField.getText().matches("\\d*")) {
            customValue_TextField.setText(customValue_TextField.getText().replaceAll("[^\\d]", ""));
        }
        if (result instanceof AddToCustomValue) {
            AddToCustomValue temp = (AddToCustomValue) result;
            if (customValue_TextField.getText().equals("")) {
                temp.setValue(0);
            }
            temp.setValue(Integer.parseInt(customValue_TextField.getText()));
            temp.setValueName(customValue_choiceBox.getSelectionModel().getSelectedItem().toString());
        } else if (result instanceof SetCustomValue) {
            AddToCustomValue temp = (AddToCustomValue) result;
            if (customValue_TextField.getText().equals("")) {
                temp.setValue(0);
            }
            temp.setValue(Integer.parseInt(customValue_TextField.getText()));
            temp.setValueName(customValue_choiceBox.getSelectionModel().getSelectedItem().toString());
        }
    }

    private void update_changeRoomImage() {
        isOK = false;
        if (!changeRoomImage_ChoiceBox.getItems().isEmpty() && changeRoomImage_ImageView.getImage() != null) {
            isOK = true;
            result = new ChangeRoomImage(changeRoomImage_ImageView.getImage(), changeRoomImage_ChoiceBox.getValue());
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
        removeStaticObjectFromRoom_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removeStaticObjectFromRoom();
        });
        addOptionToRoom_ChoiceBox_Option_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addOptionToRoom();
        });
        removeOptionFromRoom_Option_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addOptionToRoom();
        });
        addItemToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addItemToRoom();
        });
        addOptionToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addOptionToRoom();
        });
        addPathToRoom_ChoiceBox_Where_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addPathToRoom();
        });
        addPathToRoom_ChoiceBox_From_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addPathToRoom();
        });
        addStaticObjectToRoom_ChoiceBox_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addStaticObjectToRoom();
        });
        movePlayerToRoom_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_movePlayerToRoom();
        });
        removeItemFromRoom_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removeItemFromRoom();
        });
        removeOptionFromRoom_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removeOptionFromRoom();
        });
        removePathFromRoom_From_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removePathFromRoom();
        });
        removePathFromRoom_Where_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removePathFromRoom();
        });
        removeStaticObjectFromRoom_Room_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removeStaticObjectFromRoom();
        });
        addStaticObjectToRoom_ChoiceBox_StaticObject_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addStaticObjectToRoom();
        });
        describeStaticObject_StaticObject_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_describeStaticObject();
        });
        removeStaticObjectFromRoom_StaticObject_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removeStaticObjectFromRoom();
        });
        disableGameEventListener_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_disableGameEventListener();
        });
        enableGameEventListener_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_enableGameEventListener();
        });
        addItemToPlayer_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addItemToPlayer();
        });
        addItemToRoom_ChoiceBox_Item_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_addItemToRoom();
        });
        removeItemFromRoom_Item_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_removeItemFromRoom();
        });
        changeItemDescription_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_changeItemDescription();
        });
        changeItemDescription_TextArea.textProperty().addListener((observable) -> {
            update_changeItemDescription();
        });
        changeRoomDescription_TextArea.textProperty().addListener((observable) -> {
            update_changeRoomDescription();
        });
        changeRoomDescription_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_changeRoomDescription();
        });
        changeStaticObjectDescription_TextArea.textProperty().addListener((observable) -> {
            update_changeStaticObjectDescription();
        });
        changeStaticObjectDescription_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_changeStaticObjectDescription();
        });
        changeStaticObjectName_TextField.textProperty().addListener((observable) -> {
            update_changeStaticObjectName();
        });
        changeStaticObjectName_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            update_changeStaticObjectName();
        });
        changeRoomName_TextField.textProperty().addListener((observable) -> {
            update_changeRoomName();
        });
        changeRoomName_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            update_changeRoomName();
        });
        changeItemName_TextField.textProperty().addListener((observable) -> {
            update_changeItemName();
        });
        changeItemName_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            update_changeItemName();
        });
        pushMessage_TextArea.textProperty().addListener((observable) -> {
            update_pushMessage();
        });

        throwGameEvent_ChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            update_throwGameEvent();
        });
        throwGameEvent_ChoiceBox_GameEvent.getSelectionModel().selectedItemProperty().addListener((observable) -> {
            update_throwGameEvent();
        });
        throwGameEvent_DoubleTextField_TextField_1.textProperty().addListener((observable) -> {
            update_throwGameEvent();
        });
        throwGameEvent_DoubleTextField_TextField_2.textProperty().addListener((observable) -> {
            update_throwGameEvent();
        });
        throwGameEvent_SingleTextField_TextField.textProperty().addListener((observable) -> {
            update_throwGameEvent();
        });

        customValue_choiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_customValue();
        });
        customValue_TextField.textProperty().addListener((observable) -> {
            update_customValue();
        });

        changeRoomImage_ChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable) -> {
            update_changeRoomImage();
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
                update_changeRoomImage();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found");
                Logger.getLogger(GameEditorFXMLController.class.getName()).log(Level.SEVERE, null, ex);

            }
        });

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

    }

}
