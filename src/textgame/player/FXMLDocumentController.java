/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.player;

import textgame.structure.StaticObject;
import textgame.structure.Option;
import textgame.structure.Room;
import textgame.structure.actions.Action;
import textgame.structure.Game;
import textgame.structure.Item;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author David Brož
 */
public class FXMLDocumentController implements Initializable {

    private String currentImagePath;
    @FXML
    private Label room_label;
    @FXML
    private Label describtion_text;

    @FXML
    private Label info_line;

    @FXML
    private VBox optionBox;

    @FXML
    private MenuItem load_button;
    @FXML
    private MenuItem save_button;
    
    @FXML
    private ImageView imageView;
    /*@FXML
    private List option_list;*/
    @FXML
    private void loadButtonClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(workingDirectory);
        File selectedFile = fc.showOpenDialog(null);
        
        try{
            Game.setInstance((Game)ResourceManager.load(selectedFile.getName()));
            update();
        }catch(Exception e){
            System.out.println("Something went wrong with saving the game:");
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void saveButtonClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(workingDirectory);
        File selectedFile = fc.showSaveDialog(null);
        
        try{
            ResourceManager.save(Game.getInstance(), selectedFile.getPath());
        }catch(Exception e){
            System.out.println("Something went wrong with saving the game");
        }
        
    }

    @FXML
    private MenuItem start_game_button;

    @FXML
    private void startGame(ActionEvent event) {
        //Game.setInstance(null);
        testStart();
        System.out.println("Game started");
        update();
        System.out.println("Game updated");
        /*
        for(Option option : game.player.location.options){
            option_list.add(option);
        }*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //optionBox.setPrefWidth();
       
    }

    private void populateOptionBar() {
        optionBox.getChildren().clear();
        for (Option opt : Game.getInstance().getPlayer().getCurrentRoom().GenerateOptions()) {
            Button tempButton = new Button(opt.getOptionLabel());
            tempButton.setPrefWidth(optionBox.getPrefWidth());
            optionBox.getChildren().add(tempButton);

            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event2) {
                    for(Action a : opt.getActionList()){
                        a.act();
                    }
                    update();
                }
            });
        }

    }

    private void update() {
        System.out.println("---GAME UPDATED---");
        room_label.setText(Game.getInstance().getPlayer().getCurrentRoom().getName());
        info_line.setText(Game.getInstance().getInfo_line());
        describtion_text.setText(Game.getInstance().getPlayer().getCurrentRoom().getDescription());
        populateOptionBar();
        currentImagePath = Game.getInstance().getCurrentImagePath();
        if(currentImagePath != null)
        {
            imageView.setImage(new Image(currentImagePath));
            HBox parent = (HBox)imageView.getParent();
            imageView.fitWidthProperty().bind(parent.widthProperty());
            imageView.fitHeightProperty().bind(parent.heightProperty());
        }
        
    }

    public static void testStart() {
        System.out.println("TEMP START");
        //------ROOMS--------------------
        Game game = Game.getInstance(); 
        Room r0 = game.addNewRoom("Pred ruinami", "Pred tebou stoji "
                + "obrovska brana starodavneho Azsteckeho charmu. Cesta je vsak"
                + " zavalena. Je tu rozbity kemp.");
        Room r1 = game.addNewRoom("Jezero", "Krasne jezero s krasne "
                + "cistou vodou.");
        Room r2 = game.addNewRoom("Velká hala", "Velká hala spoujující vchod"
                + " do chrámu s ostattními místnosti.");
        System.out.println("Rooms Added");
 
        r1.setImagePath("file:predRuinami_programmer art.png");
        Room r3 = game.addNewRoom("Hanojske veze","Mala mistnost ve ktere se nachazi 3 tyce a na leve z nic 3 kamene kruhy. Prava tyc je pozlacena.");
        Room r4 = game.addNewRoom("Arena", "Velka arena pro boj. Stoji zde valecnik a nabizi ti mec a souboj.");

        game.getPlayer().setCurrentRoom(r0);

        r0.addPath(r1);
        r1.addPath(r0);
        
        r2.addPath(r0);
        r2.addPath(r3);
        r2.addPath(r4);
        
        r3.addPath(r2);
        r4.addPath(r2);

        //------ITEMS-------------------- 
        r0.addItemToRoom(new Item("Lopata", "Pevna lopata. "
                + "Ta bude kopat jak das."));
        r0.addItemToRoom(new Item("Lano", "Pevne a dlouhe, "
                + "to te unese"));
        //------STATIC-OBJECTS------------
        r0.addStaticObjectToRoom(new StaticObject("Zaval",
                "Vchod do chrámu je zavalen, ale dalo by se to odházet kdybch měl"
                + " lopatu nebo přelést kdybych měl lano."));
        game.getAllStaticObjects().add(new StaticObject("Zaval", "Cesta je do "
                + "chramu je zavalena. To chce naradi"));
        StaticObject velkeDvere = new StaticObject("Velke dvere", "Velke dvere s napisem ve starem jazyce. Vez pomoci jej neprectu.");
        game.getAllStaticObjects().add(velkeDvere);
        r2.addStaticObjectToRoom(velkeDvere);
        //------EVENT-LISTENERS------------
        //game.addEventListener(GameEventType.ITEM_PICKED, "0", new Action(ActionType.ADD_ROOM_PATH,r0.getId(), r2.getId()));
        //game.addEventListener(GameEventType.ITEM_PICKED, "1", new Action(ActionType.ADD_ROOM_PATH, 0, 2));

    }

}
