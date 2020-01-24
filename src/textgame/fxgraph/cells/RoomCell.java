/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.fxgraph.cells;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import textgame.editor.GameEditorFXMLController;
import textgame.fxgraph.graph.Graph;
import textgame.structure.Game;
import textgame.structure.Item;
import textgame.structure.Option;
import textgame.structure.Room;
import textgame.structure.StaticObject;

/**
 *
 * @author David Brož
 */
public class RoomCell extends AbstractCell {

    private final Room room;
    private final GameEditorFXMLController gec;
    private final double size = 70;
    private final MouseButton mouseButton = MouseButton.PRIMARY;

    public RoomCell(GameEditorFXMLController gec, Room room) {
        this.gec = gec;
        this.room = room;
    }

    @Override
    public Region getGraphic(Graph graph) {
        
        final ImageView view = new ImageView(room.getImage());
        final Pane image_pane = new Pane(view);
        final Label room_label = new Label(room.getName());
        image_pane.setPrefSize(size, size);
        image_pane.setMinSize(size, size);
        room_label.setPadding(new Insets(0, 0, 5, 0));
       
        
        image_pane.setStyle("-fx-border-color: #B8B8B8;-fx-border-radius: 1;-fx-border-width: 2;");
        /*final Pane pane = new Pane(room_label,image_pane);
        pane.setPrefSize(size, size);*/
        final VBox pane = new VBox(room_label,image_pane);
        pane.setAlignment(Pos.CENTER);
        pane.setPrefSize(size, size);
        view.setFitHeight(size);
        view.setFitWidth(size);
        //CellGestures.makeResizable(pane);

        pane.setOnMouseClicked((e) -> {
            if (e.getButton().equals(mouseButton)) {
                gec.setInspectedObject(room);
            }
        });
        pane.setOnDragDetected((e) -> {
            if (e.getButton().equals(mouseButton)) {
                Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
                ClipboardContent cb = new ClipboardContent();
                cb.put(DataFormat.PLAIN_TEXT, room.toString());
                db.setContent(cb);
                e.consume();
            }
        });
        pane.setOnDragOver((event) -> {
            event.acceptTransferModes(TransferMode.ANY);
            event.consume();
        });
        pane.setOnDragDropped((event) -> {
            if(event.getDragboard().hasFiles()){
                addImageToRoom(event.getDragboard().getFiles());
            }else if(event.getDragboard().hasString()){
                String s = event.getDragboard().getString();
                Room temp_r = Game.getInstance().getRoomWithToSting(s);
                Item temp_i = Game.getInstance().getItemWithToSting(s);
                StaticObject temp_sc = Game.getInstance().getStaticObjectWithToSting(s);
                Option temp_o = Game.getInstance().getOptionWithToSting(s);
                
                
                if(temp_r!= null){
                    addPathToRoom(temp_r);
                }else if(temp_i!=null){
                    addItemToRoom(temp_i);
                }else if(temp_sc!=null){
                    addStaticObjectToRoom(temp_sc);
                }else if(temp_o!=null){
                    addOptionToRoom(temp_o);
                }
                
            }
            gec.update();
        });

        return pane;
    }

    private void addImageToRoom( List<File> files) {
         try {
            Image img = new Image(new FileInputStream(files.get(0)));
            room.setImage(img);
            gec.update();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
            Logger.getLogger(GameEditorFXMLController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void addPathToRoom(Room r) {
        room.addPath(r);
    }

    private void addStaticObjectToRoom(StaticObject temp_sc) {
        room.addStaticObjectToRoom(temp_sc);
    }

    private void addOptionToRoom(Option temp_o) {
        room.addOption(temp_o);
    }

    private void addItemToRoom(Item temp_i) {
        room.addItemToRoom(temp_i);
    }

    public Room getRoom() {
        return room;
    }
    
    

    @Override
    public String toString() {
        return room.toString();
    }
    
    
}
