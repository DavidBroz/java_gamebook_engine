/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.abego.treelayout.Configuration;
import textgame.fxgraph.cells.RoomCell;
import textgame.fxgraph.cells.RectangleCell;
import textgame.fxgraph.cells.TriangleCell;
import textgame.fxgraph.edges.CorneredEdge;
import textgame.fxgraph.edges.DoubleCorneredEdge;
import textgame.fxgraph.edges.Edge;
import textgame.fxgraph.graph.Graph;
import textgame.fxgraph.graph.ICell;
import textgame.fxgraph.graph.IEdge;
import textgame.fxgraph.graph.Model;
import textgame.fxgraph.layout.AbegoTreeLayout;
import textgame.fxgraph.layout.CustomLayout;
import textgame.fxgraph.layout.RandomLayout;
import textgame.utility.ResourceManager;
import textgame.structure.Game;
import textgame.structure.gameEvents.GameEvent;
import textgame.structure.GameEventListener;
import textgame.structure.Item;
import textgame.structure.Option;
import textgame.structure.Player;
import textgame.structure.Room;
import textgame.structure.StaticObject;
import textgame.structure.actions.Action;
import textgame.structure.actions.AddOptionToRoom;
import textgame.structure.actions.AddPathFromRoom;
import textgame.structure.actions.RemoveOptionFromRoom;
import textgame.structure.actions.RemovePathFromRoom;
import textgame.structure.actions.ThrowGameEvent;
import textgame.structure.gameEvents.RandomNumber;

/**
 * FXML Controller class
 *
 * @author David Brož
 */
public class GameEditorFXMLController implements Initializable {

    private Object inspectedObject;
    @FXML
    private Label warning_label;
    private ArrayList<String> warnings;
    
    @FXML
    private BorderPane borderPane;

    @FXML
    private ScrollPane centerScrollPane;
    //--------------------------------------------------------------------------
    @FXML
    private ListView allRoomListView,
            allItemListView,
            allEventListenerListView,
            allStaticObjectListView,
            allOptionsListView;
    //----Room-inspector--------------------------------------------------------
    @FXML
    private VBox roomInspectorVBox;
    @FXML
    private TextField selectedRoomName;
    @FXML
    private Label selectedRoomIdLabel;
    @FXML
    private TextArea selectedRoomDesArea;
    @FXML
    private ListView itemsInRoomInspectorListView,
            roomInspectorPathsListView,
            roomInspectorStaticObjectListView,
            roomInspectorOptionListView;
    @FXML
    private ImageView roomImageView;
    //---Item-inspector---------------------------------------------------------
    @FXML
    private VBox ItemInspectorVBox;
    @FXML
    private TextField selectedItemName;
    @FXML
    private Label selectedItemIdLabel;
    @FXML
    private TextArea selectedItemDesArea;
    //---Static-object-inspector------------------------------------------------
    @FXML
    private VBox StaticObjectInspectorVBox;
    @FXML
    private TextField selectedStaticObjectName;
    @FXML
    private TextArea selectedStaticObjectDesArea;
    @FXML
    private Label selectedStaticObjectIdLabel;
    @FXML
    private ListView selectedStaticObjectOptions;

    //---Game-Event-Listner-inspector------------------------------------------------
    @FXML
    private VBox EventListenerInspectorVBox;

    @FXML
    private CheckBox eventListenerEnabledCheckBox;
    @FXML
    private Label selectedEventListenerIdLabel;
    @FXML
    private TextField selectedEventListenerName,
            selectedEventListenenExpects;

    @FXML
    private ListView selectedEvenListenerActions;
    @FXML
    private ChoiceBox<GameEvent.GameEventType> selectedEventListenerListensFor;

    @FXML
    private ChoiceBox inspectedGameEventListenerExpects_ChoiceBox;

    private boolean selectedEventListenenTextFieldIsString;
    //---Option-inspector------------------------------------------------
    @FXML
    private VBox OptionInspectorVBox;

    @FXML
    private Label inspectedOptionIDLabel;
    @FXML
    private TextField inspectedOptionLabel;
    @FXML
    private ListView inspectedOptionActions;
    //---Player----------------------------------------------------------------
    @FXML
    private VBox playerInspectorVBox;
    @FXML
    private ListView playerOptionsListView,
            playerCustomValuesListView,
            playerInventoryListView;
    @FXML
    private ChoiceBox<Room> startRoom_ChoiceBox;
    //----Game-----------------------------------------------------------------
    @FXML
    private VBox gameInspectorVBox;
    @FXML
    private TextField gameInspectorName_TextField;
    //-------------------------------------------------------------------------
    private Graph graph;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warning_label.setStyle("-fx-text-fill: #AB4642;");
        initializeContextMenus();
        warnings=new ArrayList();

        //-SET-CHOICE-BOX-
        selectedEventListenerListensFor.getItems().addAll(GameEvent.GameEventType.values());

        allRoomListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        allStaticObjectListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        allItemListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        allEventListenerListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setListViewOnClickEventHandler(allRoomListView);
        setListViewOnClickEventHandler(allStaticObjectListView);
        setListViewOnClickEventHandler(allItemListView);
        setListViewOnClickEventHandler(allEventListenerListView);
        setListViewOnClickEventHandler(allOptionsListView);

        setDiagram();
        setInstantUpdates();
        setDefaultGame();

        update();
    }

    private void setListViewOnClickEventHandler(ListView listView) {
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                inspectedObject = listView.getSelectionModel().getSelectedItem();
                updateInspector();
            }
        });
    }

    private void setInstantUpdates() {
        inspectedOptionLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                Option o = (Option) inspectedObject;
                o.setLabel(newValue);
                updateListVeiws(false);
            }
        });
        selectedStaticObjectName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                StaticObject so = (StaticObject) inspectedObject;
                so.setName(newValue);
                updateListVeiws(false);
            }
        });

        selectedStaticObjectDesArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                StaticObject sc = (StaticObject) inspectedObject;
                sc.setDesctiption(newValue);
                updateListVeiws(false);
            }
        });

        selectedRoomName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                Room r = (Room) inspectedObject;
                r.setName(newValue);
                updateListVeiws(false);
            }
        });
        selectedRoomDesArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                Room r = (Room) inspectedObject;
                r.setDescription(newValue);
            }
        });

        selectedItemName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                Item i = (Item) inspectedObject;
                i.setName(newValue);
                updateListVeiws(false);
            }
        });

        selectedItemDesArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                Item i = (Item) inspectedObject;
                i.setDescription(newValue);
                updateListVeiws(false);
            }
        });

        selectedEventListenerName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                GameEventListener i = (GameEventListener) inspectedObject;
                i.setName(newValue);
                updateListVeiws(false);
            }
        });
        selectedEventListenenExpects.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                GameEventListener i = (GameEventListener) inspectedObject;
                if (selectedEventListenenTextFieldIsString) {
                    i.setExpectedValue(new String(newValue));
                } else {
                    if (!newValue.matches("\\d*")) {
                        selectedEventListenenExpects.setText(newValue.replaceAll("[^\\d]", ""));
                    } else if (!newValue.isEmpty()) {
                        i.setExpectedValue(new Integer(Integer.parseInt(newValue)));
                    }

                }
                updateListVeiws(false);
            }
        });
        
        gameInspectorName_TextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                Game.getInstance().setName(newValue);
                updateInspector();
            }
        });
    }
    //-----------------UPDATE------------------------------------------------------

    public void update() {
        updateDiagramCells(graph);
        updateListVeiws(true);
        updateInspector();

    }

    private void updateListVeiws(boolean reset) {

        if (reset) {
            Game g = Game.getInstance();
            allRoomListView.getItems().clear();
            allItemListView.getItems().clear();
            allEventListenerListView.getItems().clear();
            allStaticObjectListView.getItems().clear();
            allOptionsListView.getItems().clear();

            for (Room r : g.getAllRooms()) {
                allRoomListView.getItems().add(r);
            }
            for (Item i : g.getAllItems()) {
                allItemListView.getItems().add(i);
            }
            for (StaticObject so : g.getAllStaticObjects()) {
                allStaticObjectListView.getItems().add(so);
            }
            for (GameEventListener el : g.getAllGameEventListeners()) {
                allEventListenerListView.getItems().add(el);
            }
            for (Option op : g.getAllOptions()) {
                allOptionsListView.getItems().add(op);
            }
        } else {
            allOptionsListView.refresh();
            allRoomListView.refresh();
            allItemListView.refresh();
            allEventListenerListView.refresh();
            allStaticObjectListView.refresh();
        }
    }

    private void updateInspector() {
        checkForWarnings();
        displayWarnings();
        
        gameInspectorVBox.setVisible(false);
        playerInspectorVBox.setVisible(false);
        roomInspectorVBox.setVisible(false);
        ItemInspectorVBox.setVisible(false);
        StaticObjectInspectorVBox.setVisible(false);
        EventListenerInspectorVBox.setVisible(false);
        OptionInspectorVBox.setVisible(false);

        if (inspectedObject instanceof Room) {
            Room r = (Room) inspectedObject;
            updateRoomInspector(r);
        }
        if (inspectedObject instanceof Item) {
            Item i = (Item) inspectedObject;
            updateItemInspector(i);
        }
        if (inspectedObject instanceof GameEventListener) {
            GameEventListener ev = (GameEventListener) inspectedObject;
            updateGameEventListenerInspector(ev);
        }
        if (inspectedObject instanceof StaticObject) {
            StaticObject so = (StaticObject) inspectedObject;
            updateStaticObjectInspector(so);
        }
        if (inspectedObject instanceof Option) {
            Option o = (Option) inspectedObject;
            updateOptionInspector(o);
        }
        if (inspectedObject instanceof Player) {
            Player p = (Player) inspectedObject;
            updatePlayerInspector(p);
        }

        if (inspectedObject instanceof Game) {
            Game g = (Game) inspectedObject;
            updateGameInspector();
        }
    }

    private void updateRoomInspector(Room r) {
        roomInspectorVBox.setVisible(true);
        selectedRoomIdLabel.setText(String.valueOf(r.getId()));
        selectedRoomName.setText(r.getName());
        selectedRoomDesArea.setText(r.getDescription());

        itemsInRoomInspectorListView.getItems().clear();
        for (Item i : r.getAllItems()) {
            itemsInRoomInspectorListView.getItems().add(i);
        }
        roomInspectorPathsListView.getItems().clear();
        for (Room p : r.getAllPaths()) {
            roomInspectorPathsListView.getItems().add(p);
        }
        roomInspectorStaticObjectListView.getItems().clear();
        for (StaticObject so : r.getAllStaticObjects()) {
            roomInspectorStaticObjectListView.getItems().add(so);
        }
        roomInspectorOptionListView.getItems().clear();
        for (Option o : r.getAllOptions()) {
            roomInspectorOptionListView.getItems().add(o);
        }
        roomImageView.setImage(r.getImage());
    }

    private void updateItemInspector(Item i) {
        ItemInspectorVBox.setVisible(true);
        selectedItemIdLabel.setText(String.valueOf(i.getId()));
        selectedItemName.setText(i.getName());
        selectedItemDesArea.setText(i.getDescription());
    }

    private void updateGameEventListenerInspector(GameEventListener ev) {
        Class requiredClass = GameEvent.getReturnClass(ev.getExpectedEventType());
        selectedEventListenenExpects.setVisible(false);
        inspectedGameEventListenerExpects_ChoiceBox.setVisible(false);

        if (requiredClass.equals(Integer.class)) {
            selectedEventListenenTextFieldIsString = false;
            selectedEventListenenExpects.setVisible(true);
            selectedEventListenenExpects.setText(ev.getExpectedValue().toString());
            System.out.println("Request for Integer");
        } else if (requiredClass.equals(String.class)) {
            selectedEventListenenTextFieldIsString = true;
            selectedEventListenenExpects.setVisible(true);
            selectedEventListenenExpects.setText(ev.getExpectedValue().toString());
            System.out.println("Request for String");
        } else {
            inspectedGameEventListenerExpects_ChoiceBox.setVisible(true);
            inspectedGameEventListenerExpects_ChoiceBox.getItems().clear();
            inspectedGameEventListenerExpects_ChoiceBox.getItems().addAll(Game.getInstance().getAllIntancesOf(requiredClass));
            if (!inspectedGameEventListenerExpects_ChoiceBox.getItems().isEmpty()) {
                inspectedGameEventListenerExpects_ChoiceBox.getSelectionModel().select(0);
            }
        }

        EventListenerInspectorVBox.setVisible(true);
        eventListenerEnabledCheckBox.setSelected(ev.isEnabled());
        selectedEventListenerIdLabel.setText("" + ev.getId());
        selectedEventListenerName.setText(ev.getName());
        selectedEventListenenExpects.setText(ev.getExpectedValue().toString());

        selectedEventListenerListensFor.setValue(ev.getExpectedEventType());
        selectedEvenListenerActions.getItems().clear();
        for (Action a : ev.getActions()) {
            selectedEvenListenerActions.getItems().add(a);
        }

    }

    private void updateStaticObjectInspector(StaticObject so) {
        StaticObjectInspectorVBox.setVisible(true);
        selectedStaticObjectName.setText(so.getName());
        selectedStaticObjectDesArea.setText(so.getDesctiption());
        selectedStaticObjectIdLabel.setText(String.valueOf(so.getId()));

        selectedStaticObjectOptions.getItems().clear();
        for (Option o : so.getAllOptions()) {
            selectedStaticObjectOptions.getItems().add(o);
        }
    }
    
    private void updatePlayerInspector(Player player) {
        playerInspectorVBox.setVisible(true);
        playerOptionsListView.getItems().clear();
        playerOptionsListView.getItems().addAll(player.getOptions());

        playerCustomValuesListView.getItems().clear();
        player.getCustomValues().forEach((t, u) -> {
            playerCustomValuesListView.getItems().add(t+"="+u);
        });

        playerInventoryListView.getItems().clear();
        playerInventoryListView.getItems().addAll(player.getInventory());
        
        startRoom_ChoiceBox.getItems().clear();
        startRoom_ChoiceBox.getItems().addAll(Game.getInstance().getAllRooms());
        
        
        if(player.getCurrentRoom()!=null){   
            startRoom_ChoiceBox.getSelectionModel().select(player.getCurrentRoom());
        }
        
    }
     
    private void updateGameInspector() {
        gameInspectorVBox.setVisible(true);
    }
     
     private void updateOptionInspector(Option o) {
        OptionInspectorVBox.setVisible(true);
        inspectedOptionLabel.setText(o.getOptionLabel());
        inspectedOptionActions.getItems().clear();
        for (Action a : o.getActionList()) {
            inspectedOptionActions.getItems().add(a);
        }
        inspectedOptionIDLabel.setText("" + o.getId());
    }
    //---------EVENT-HANDLERS--------------------------

    //---------BUTTONS----------------------------------------------------------
    @FXML
    private void loadButtonClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(workingDirectory);
        File selectedFile = fc.showOpenDialog(null);

        try {
            Game.setInstance((Game) ResourceManager.load(selectedFile.getPath()));
            update();
        } catch (Exception e) {
            System.out.println("Something went wrong with loading the game:");
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void saveButtonClick(ActionEvent event) {

        File workingDirectory = new File(System.getProperty("user.dir"));
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("JavaFX Projects");
        chooser.setInitialDirectory(workingDirectory);
        File selectedDirectory = chooser.showDialog(null);

        try {
            ResourceManager.saveProject(Game.getInstance(), selectedDirectory.getPath(), Game.getInstance().getName());
        } catch (Exception e) {
            System.out.println("Something went wrong with saving the game");
            System.out.println(e.toString());
        }

    }

    @FXML
    private void newGameButonClick(ActionEvent event) {
        Game game = Game.getInstance(true);
        setDefaultGame();
        update();
    }

    @FXML
    private void addNewActionToOption() {
        Option o = (Option) inspectedObject;
        Action actionToAdd = addNewActionDialog();
        if (actionToAdd != null) {
            o.addAction(actionToAdd);
        }

        updateInspector();
    }

    @FXML
    private void addNewOption() {
        Option temp = new Option("New option");
        //temp.addAction(new ThrowGameEvent(new GameEvent(GameEvent.GameEventType.MESSAGE, "Option selected")));
        Game.getInstance().addNewOption(temp);
        update();
    }

    @FXML
    private void addNewActionToSelectedEventListener() {
        GameEventListener gel = (GameEventListener) inspectedObject;
        Action actionToAdd = addNewActionDialog();
        if (actionToAdd != null) {
            gel.addAction(actionToAdd);
        }
        updateInspector();
    }

    //--------------------------------------------------------------------------
    @FXML
    private void selectedEventListenerEnabledChecked() {
        if (inspectedObject instanceof GameEventListener) {
            GameEventListener gel = (GameEventListener) inspectedObject;
            gel.setEnabled(eventListenerEnabledCheckBox.isSelected());
            update();
        }
    }

    @FXML
    private void addNewRoom(ActionEvent event) {
        Game.getInstance().addNewRoom();
        update();
    }

    @FXML
    private void addNewItem(ActionEvent event) {
        Game.getInstance().addNewItem();
        update();
    }

    @FXML
    private void addNewEventListener() {
        Game.getInstance().addEventListener("Unnamed_listener",
                GameEvent.GameEventType.RANDOM_NUMBER, "Listen me.",
                new ThrowGameEvent(new RandomNumber(10, 0)));
        update();
    }

    @FXML
    private void addNewStaticObject() {
        Game.getInstance().addNewStaticObject();
        update();
    }

    @FXML
    private void updateInspectedRoomName(ActionEvent event) {
        Room r = (Room) inspectedObject;
        r.setName(selectedRoomName.getText());
        update();
    }

    //---------------ON-DRAG-DETECTED-------------------------------------------
    @FXML
    private void roomListViewOnDragDetected(MouseEvent event) {
        Dragboard db = allRoomListView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        Room r = (Room) allRoomListView.getSelectionModel().getSelectedItems().get(0);
        if(r==null)return;
        cb.put(DataFormat.PLAIN_TEXT, r.toString());
        db.setContent(cb);
        event.consume();
    }

    @FXML
    private void staticObjectListViewOnDragDetected(MouseEvent event) {
        Dragboard db = allStaticObjectListView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        StaticObject so = (StaticObject) allStaticObjectListView.getSelectionModel().getSelectedItems().get(0);
        if(so==null)return;
        cb.put(DataFormat.PLAIN_TEXT, so.toString());
        db.setContent(cb);
        event.consume();
    }

    @FXML
    private void itemListViewOnDragDetected(MouseEvent event) {
        Dragboard db = allItemListView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        Item r = (Item) allItemListView.getSelectionModel().getSelectedItems().get(0);
        if(r==null)return;
        cb.put(DataFormat.PLAIN_TEXT, r.toString());
        db.setContent(cb);
        event.consume();
    }

    @FXML
    private void OptionListViewOnDragDetected(MouseEvent event) {
        Dragboard db = allOptionsListView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        Option o = (Option) allOptionsListView.getSelectionModel().getSelectedItems().get(0);
        if(o==null)return;
        cb.put(DataFormat.PLAIN_TEXT, o.toString());
        db.setContent(cb);
        event.consume();
    }

    //--------------ON-DRAG-OVER------------------------------------------------
    @FXML
    private void itemsInRoomListVieOnDragOver(DragEvent event) {
        if (event.getDragboard().hasString() && Game.getInstance().getItemWithToSting(event.getDragboard().getString()) != null) {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    @FXML
    private void roomPathsListVieOnDragOver(DragEvent event) {
        if (event.getDragboard().hasString() && Game.getInstance().getRoomWithToSting(event.getDragboard().getString()) != null) {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    @FXML
    private void staticObjectInRoomListVieOnDragOver(DragEvent event) {
        if (event.getDragboard().hasString() && Game.getInstance().getStaticObjectWithToSting(event.getDragboard().getString()) != null) {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    @FXML
    private void RoomImageViewOnDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }

    }

    @FXML
    private void OptionInRoomListVieOnDragOver(DragEvent event) {
        if (event.getDragboard().hasString() && Game.getInstance().getOptionWithToSting(event.getDragboard().getString()) != null) {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        }
    }

    @FXML
    private void OptionInStaticObjectListVieOnDragOver(DragEvent event) {
        OptionInRoomListVieOnDragOver(event);
    }

    //-------------ON-DRAG-DROPPED----------------------------------------------
    @FXML
    private void PlayerInventoryOnDragDropped(DragEvent event){
        Game game = Game.getInstance();
        String str = event.getDragboard().getString();
        Player pl = (Player) inspectedObject;
        pl.addItemToInvenotory(game.getItemWithToSting(str),false);
        updateInspector();
    }
    
    @FXML
    private void playerOptionsOnDragDrop(DragEvent event){
        Game game = Game.getInstance();
        String str = event.getDragboard().getString();
        Player pl = (Player) inspectedObject;
        pl.addOption(game.getOptionWithToSting(str),false);
        updateInspector();
    }
    
    @FXML
    private void RoomImageViewOnDragDropped(DragEvent event) {
        try {
            List<File> files = event.getDragboard().getFiles();
            Image img = new Image(new FileInputStream(files.get(0)));

            Room r = (Room) inspectedObject;
            r.setImage(img);
            update();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            Logger.getLogger(GameEditorFXMLController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    private void OptionInStaticObjectListViewOnDragDropped(DragEvent event) {
        Game game = Game.getInstance();
        String str = event.getDragboard().getString();
        selectedStaticObjectOptions.getItems().add(game.getOptionWithToSting(str));
        StaticObject so = (StaticObject) inspectedObject;
        so.addOption(game.getOptionWithToSting(str));
        updateInspector();
    }

    @FXML
    private void OptionInRoomListViewOnDragDropped(DragEvent event) {
        Game game = Game.getInstance();
        String str = event.getDragboard().getString();
        roomInspectorOptionListView.getItems().add(game.getOptionWithToSting(str));
        Room r = (Room) inspectedObject;
        r.addOption(game.getOptionWithToSting(str));
        update();
    }

    @FXML
    private void roomPathListViewOnDragDropped(DragEvent event) {
        Game game = Game.getInstance();
        String str = event.getDragboard().getString();
        roomInspectorPathsListView.getItems().add(game.getRoomWithToSting(str));
        Room r = (Room) inspectedObject;
        r.addPath(game.getRoomWithToSting(str));
        update();
    }

    @FXML
    private void staticObjectInRoomListViewOnDragDropped(DragEvent event) {
        Game game = Game.getInstance();
        String str = event.getDragboard().getString();
        roomInspectorStaticObjectListView.getItems().add(game.getStaticObjectWithToSting(str));
        Room r = (Room) inspectedObject;
        r.addStaticObjectToRoom(game.getStaticObjectWithToSting(str));
        update();
    }

    @FXML
    private void itemsInRoomListViewOnDragDropped(DragEvent event) {
        Game game = Game.getInstance();
        String str = event.getDragboard().getString();
        itemsInRoomInspectorListView.getItems().add(game.getItemWithToSting(str));
        Room r = (Room) inspectedObject;
        r.addItemToRoom(game.getItemWithToSting(str));
        update();
    }

    //-----------CHOICE-BOX-----------------------------------------------------
    @FXML
    private void selectedEventListenerListensForChoice() {
        if (inspectedObject instanceof GameEventListener) {
            GameEventListener gel = (GameEventListener) inspectedObject;
            gel.setExpectedvEventType(selectedEventListenerListensFor.getValue());
            update();
        }
    }

    //-----------TEXT-FIELD-----------------------------------------------------
    @FXML
    private void updateInspectedEventListenerName() {

        if (inspectedObject instanceof GameEventListener) {
            GameEventListener gel = (GameEventListener) inspectedObject;
            gel.setName(selectedEventListenerName.getText());
            update();
        }
    }

    @FXML
    private void updateInspectedEventListenerExpects() {

        if (inspectedObject instanceof GameEventListener) {
            GameEventListener gel = (GameEventListener) inspectedObject;
            gel.setExpectedValue(selectedEventListenenExpects.getText());
            update();
        }
    }

    //--------------------------------------------------------------------------
    private void initializeContextMenus() {
        //----------------------CONTEXT-MENU------------------------------------
        ContextMenu itemsInToomContextMenu = new ContextMenu();
        MenuItem deleteIIR = new MenuItem("Delete");
        deleteIIR.setOnAction((event) -> {
            Object item = itemsInRoomInspectorListView.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof Room && item instanceof Item) {
                Room temp = (Room) inspectedObject;
                temp.removeItem((Item) item, false);
                update();
            }
        });

        ContextMenu optionsInStaticObjectContextMenu = new ContextMenu();
        MenuItem deleteOSO = new MenuItem("Delete");
        deleteOSO.setOnAction((event) -> {
            Object item = selectedStaticObjectOptions.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof StaticObject && item instanceof Option) {
                StaticObject temp = (StaticObject) inspectedObject;
                temp.removeOption((Option) item);
                update();
            }
        });

        optionsInStaticObjectContextMenu.getItems().add(deleteOSO);
        selectedStaticObjectOptions.setContextMenu(optionsInStaticObjectContextMenu);

        ContextMenu roomPathsContextMenu = new ContextMenu();
        MenuItem deleteRP = new MenuItem("Delete");
        deleteRP.setOnAction((event) -> {
            Object item = roomInspectorPathsListView.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof Room && item instanceof Room) {
                Room temp = (Room) inspectedObject;
                temp.removePathToRoom((Room) item);
                update();
            }
        });
        roomPathsContextMenu.getItems().add(deleteRP);
        roomInspectorPathsListView.setContextMenu(roomPathsContextMenu);

        ContextMenu eventListnerActionsContextMenu = new ContextMenu();
        MenuItem deleteMI = new MenuItem("Delete");
        deleteMI.setOnAction((event) -> {
            Object item = selectedEvenListenerActions.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof GameEventListener && item instanceof Action) {
                GameEventListener temp = (GameEventListener) inspectedObject;
                temp.deleteAction((Action) item);
                update();
            }
        });
        eventListnerActionsContextMenu.getItems().add(deleteMI);
        selectedEvenListenerActions.setContextMenu(eventListnerActionsContextMenu);

        ContextMenu roomStaticObjectsContextMenu = new ContextMenu();
        MenuItem deleteRSO = new MenuItem("Delete");
        deleteRSO.setOnAction((event) -> {
            Object item = roomInspectorStaticObjectListView.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof Room && item instanceof StaticObject) {
                Room temp = (Room) inspectedObject;
                temp.removeStaticObjectFromRoom((StaticObject) item, false);
                update();
            }
        });
        roomStaticObjectsContextMenu.getItems().add(deleteRSO);
        roomInspectorStaticObjectListView.setContextMenu(roomStaticObjectsContextMenu);

        ContextMenu optionActionsContextMenu = new ContextMenu();
        MenuItem deleteOA = new MenuItem("Delete");
        deleteOA.setOnAction((event) -> {
            Object action = inspectedOptionActions.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof Option && action instanceof Action) {
                Option temp = (Option) inspectedObject;
                temp.deleteAction((Action) action);
                update();
            }
        });
        optionActionsContextMenu.getItems().add(deleteOA);
        inspectedOptionActions.setContextMenu(optionActionsContextMenu);

//-----------------------
        ContextMenu allStaticObjectsContextMenu = new ContextMenu();
        MenuItem deleteSO = new MenuItem("Delete");
        deleteSO.setOnAction((event) -> {
            Object item = allStaticObjectListView.getSelectionModel().getSelectedItem();
            if (item instanceof StaticObject) {
                Game.getInstance().removeStaticObject((StaticObject) item);
                update();
            }
        });
        allStaticObjectsContextMenu.getItems().add(deleteSO);
        allStaticObjectListView.setContextMenu(allStaticObjectsContextMenu);

        ContextMenu allRoomsContextMenu = new ContextMenu();
        MenuItem deleteR = new MenuItem("Delete");
        deleteR.setOnAction((event) -> {
            Object item = allRoomListView.getSelectionModel().getSelectedItem();
            if (item instanceof Room) {
                Game.getInstance().removeRoom((Room) item);
                update();
            }
        });
        allRoomsContextMenu.getItems().add(deleteR);
        allRoomListView.setContextMenu(allRoomsContextMenu);

        ContextMenu allItemsContextMenu = new ContextMenu();
        MenuItem deleteI = new MenuItem("Delete");
        deleteI.setOnAction((ActionEvent event) -> {
            Object item = allItemListView.getSelectionModel().getSelectedItem();
            if (item instanceof Item) {
                Game.getInstance().removeItem((Item) item);
                update();
            }
        });
        allItemsContextMenu.getItems().add(deleteI);
        allItemListView.setContextMenu(allItemsContextMenu);

        ContextMenu allEventListenersContextMenu = new ContextMenu();
        MenuItem deleteEL = new MenuItem("Delete");
        deleteEL.setOnAction((ActionEvent event) -> {
            Object item = allEventListenerListView.getSelectionModel().getSelectedItem();
            if (item instanceof GameEventListener) {
                Game.getInstance().removeGameEventListener((GameEventListener) item);
                update();
            }
        });
        allEventListenersContextMenu.getItems().add(deleteEL);
        allEventListenerListView.setContextMenu(allEventListenersContextMenu);

        ContextMenu allOptionsContextMenu = new ContextMenu();
        MenuItem deleteOPT = new MenuItem("Delete");
        deleteOPT.setOnAction((ActionEvent event) -> {
            Object item = allOptionsListView.getSelectionModel().getSelectedItem();
            if (item instanceof Option) {
                Game.getInstance().removeOption((Option) item);
                update();
            }
        });
        allOptionsContextMenu.getItems().add(deleteOPT);
        allOptionsListView.setContextMenu(allOptionsContextMenu);

        ContextMenu roomOptionsContextMenu = new ContextMenu();
        MenuItem deleteRO = new MenuItem("Delete");
        deleteRO.setOnAction((event) -> {
            Object item = roomInspectorOptionListView.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof Room && item instanceof Option) {
                Room temp = (Room) inspectedObject;
                temp.removeOption((Option) item,false);
                update();
            }
        });

        roomOptionsContextMenu.getItems().add(deleteRO);
        roomInspectorOptionListView.setContextMenu(roomOptionsContextMenu);

        ContextMenu roomItemContextMenu = new ContextMenu();
        MenuItem deleteRI = new MenuItem("Delete");
        deleteRI.setOnAction((event) -> {
            Object item = itemsInRoomInspectorListView.getSelectionModel().getSelectedItem();
            if (inspectedObject instanceof Room && item instanceof Item) {
                Room temp = (Room) inspectedObject;
                temp.removeItem((Item) item, false);
                update();
            }
        });

        roomItemContextMenu.getItems().add(deleteRI);
        itemsInRoomInspectorListView.setContextMenu(roomItemContextMenu);
    }



    private Action addNewActionDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddAction.fxml"));
            Parent addActionRoot = (Parent) fxmlLoader.load();
            Stage addActionStage = new Stage();
            AddActionController.addActionStage = addActionStage;
            addActionStage.initModality(Modality.APPLICATION_MODAL);
            addActionStage.setTitle("Add new action");

            Scene scene = new Scene(addActionRoot);
            //scene.getStylesheets().add("/CSS/editorStyle.css");

            addActionStage.setScene(scene);

            addActionStage.showAndWait();
            return AddActionController.result;
        } catch (IOException ex) {
            Logger.getLogger(GameEditorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }



    private void setDiagram() {
        graph = new Graph();
        updateDiagramCells(graph);
        centerScrollPane.setContent(graph.getCanvas());
    }

    private void updateDiagramCells(Graph graph) {
        final Model model = graph.getModel();   
        saveRoomLocation(graph);
        graph.beginUpdate();

        ArrayList<Room> rooms = Game.getInstance().getAllRooms();
        Map<Room, ICell> map = new HashMap<>();
        RoomCell temp;
        for (Room r : rooms) {
            //System.out.println("ROOM: "+r);
            temp = new RoomCell(this, r);
            map.put(r, temp);
            model.addCell(temp);
        }

        map.forEach((Room r_1, ICell cell_1) -> {
            map.forEach((Room _r2, ICell cell_2) -> {
                if (r_1.hasPathTo(_r2) && !r_1.equals(_r2)) {
                    addEdge(cell_1, cell_2, model);
                }
            });
        });

        graph.endUpdate();
        graph.layout(new CustomLayout());
        //graph.layout(new AbegoTreeLayout(200, 300, Configuration.Location.Top));
//graph.layout(new RandomLayout());
    }

    public Object getInspectedObject() {
        return inspectedObject;

    }

    public void setInspectedObject(Object inspectedObject) {
        this.inspectedObject = inspectedObject;
        update();
    }

    private void addEdge(ICell cell_1, ICell cell_2, Model model) {
        Edge edge;
        edge = new Edge(cell_1, cell_2);
        model.addEdge(edge);
    }

    private void saveRoomLocation(Graph graph) {
        if (graph == null) {
            return;
        }
        RoomCell rc;
        List<ICell> cells = graph.getModel().getAllCells();
        for (ICell c : cells) {
            rc = (RoomCell) c;
            rc.getRoom().setLocation_x(graph.getGraphic(c).getLayoutX());
            rc.getRoom().setLocation_y(graph.getGraphic(c).getLayoutY());
        }

    }

    @FXML
    public void inspectPlayer() {
        inspectedObject = Game.getInstance().getPlayer();
        updateInspector();
    }

    @FXML
    private void playerAddNewCustomValue() {
        Game.getInstance().getPlayer().addCustomValues(getNewCustomValueDialog());
        updateInspector();
    }

    private Map<String, Integer> getNewCustomValueDialog() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddCustomValue.fxml"));
            Parent addCustomCalueRoot = (Parent) fxmlLoader.load();
            Stage addCustomValueStage = new Stage();
            AddCustomValueController.addCustomValueStage = addCustomValueStage;
            addCustomValueStage.initModality(Modality.APPLICATION_MODAL);
            addCustomValueStage.setTitle("Add new action");

            Scene scene = new Scene(addCustomCalueRoot);

            addCustomValueStage.setScene(scene);

            addCustomValueStage.showAndWait();
            return AddCustomValueController.getResult();
        } catch (IOException ex) {
            Logger.getLogger(GameEditorFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void inspectGame() {
        inspectedObject = Game.getInstance();
        updateInspector();
    }

   

    private void displayWarnings() {
        System.out.println("Warinings:"+warnings);
        if(warnings.isEmpty()) warning_label.setText("");
        else warning_label.setText(warnings.get(0));
    }
    
    private void checkForWarnings(){
        System.out.println("Checked for warnings");
        Game game = Game.getInstance();
        String[] warnings_messages = {"Starting room is missing. (Go Project>Player)"};
        
        System.out.println("Start room:"+game.getPlayer().getCurrentRoom());
        
        if (!warnings.contains(warnings_messages[0]) && game.getPlayer().getCurrentRoom()==null) {
            warnings.add(warnings_messages[0]);
        }else if(warnings.contains(warnings_messages[0]) && game.getPlayer().getCurrentRoom()!=null)warnings.remove(warnings_messages[0]);
        
    }
    
        private void setDefaultGame() {
        Game g = Game.getInstance();
       
        g.addNewItem();
        
        g.addNewRoom();
        g.addNewRoom();
        g.addNewRoom();
        
        g.addNewStaticObject();
        
        g.addNewOption( new Option("Open the door"));
        g.addNewOption( new Option("Close the door"));
        
        
        ArrayList<Room> rs = g.getAllRooms();
        ArrayList<Item> is = g.getAllItems();
        ArrayList<Option> os = g.getAllOptions();

        rs.get(0).addPath(rs.get(1));
        rs.get(1).addPath(rs.get(0));
        rs.get(1).addPath(rs.get(2));
        rs.get(2).addPath(rs.get(1));

        rs.get(1).addItemToRoom(is.get(0));
        rs.get(1).addOption(os.get(2));
        
        os.get(1).addAction( new RemoveOptionFromRoom(os.get(1), rs.get(1)));
        os.get(1).addAction( new AddPathFromRoom(rs.get(1), rs.get(0)));
        os.get(1).addAction( new AddOptionToRoom(os.get(2),  rs.get(1)));
        
        os.get(2).addAction( new RemoveOptionFromRoom(os.get(2), rs.get(1)));
        os.get(2).addAction( new RemovePathFromRoom(rs.get(1), rs.get(0)));
        os.get(2).addAction( new AddOptionToRoom(os.get(1),  rs.get(1)));
        
        rs.get(0).setName("Start room");
        rs.get(2).setName("End room");
        rs.get(1).setName("Mid room");
        
        
        rs.get(0).setDescription("This is a Start room");
        rs.get(1).setDescription("This prolly is Mid room");
        rs.get(2).setDescription("Mayhaps the End room");
        
        is.get(0).setName("Banana");
        is.get(0).setDescription("Potentially dangersou fruit, rich source of potasium.");
        
        g.getPlayer().setCurrentRoom(rs.get(0));
        
    }
}
