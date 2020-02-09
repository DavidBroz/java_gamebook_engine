/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.player;

import textgame.utility.ResourceManager;
import textgame.structure.Option;
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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderRepeat;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author David Brož
 */
public class GamePlayerController implements Initializable {

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
    private MenuItem start_game_button;

    @FXML
    private ImageView imageView;
    @FXML
    private StackPane optionBarStackPane;

    /*@FXML
    private List option_list;*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //optionBox.setPrefWidth();

    }

    //--FXML--------------------------------------------------------------------
    @FXML
    private void loadButtonClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(workingDirectory);
        File selectedFile = fc.showOpenDialog(null);

        try {
            Game.setInstance((Game) ResourceManager.load(selectedFile.getPath()));
        } catch (Exception e) {
            System.out.println("Something went wrong with loading the game:");
            System.out.println(e.toString());
            System.out.println(e.getMessage());
            System.out.println(e.fillInStackTrace());
        }
        update();
    }

    @FXML
    private void saveButtonClick(ActionEvent event) {
        FileChooser fc = new FileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(workingDirectory);
        File selectedFile = fc.showSaveDialog(null);

        try {
            ResourceManager.save(Game.getInstance(), selectedFile.getPath());
        } catch (Exception e) {
            System.out.println("Something went wrong with saving the game");
        }

    }

    @FXML
    private void startGame(ActionEvent event) {
        update();
    }

    //--------------------------------------------------------------------------
    private void populateOptionBar() {
        optionBox.getChildren().clear();
        System.out.println("POPULATE OPTION: Bar cleared");
        for (Option opt : Game.getInstance().getPlayer().getCurrentRoom().GenerateOptions()) {
            System.out.println(opt);
            Button tempButton = new Button(opt.getOptionLabel());
            tempButton.setPrefWidth(optionBox.getPrefWidth());
            optionBox.getChildren().add(tempButton);

            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event2) {
                    for (Action a : opt.getActionList()) {
                        a.act();
                    }
                    update();
                }
            });
        }
        for (Option opt : Game.getInstance().getPlayer().getOptions()) {
            Button tempButton = new Button(opt.getOptionLabel());
            tempButton.setPrefWidth(optionBox.getPrefWidth());
            optionBox.getChildren().add(tempButton);

            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event2) {
                    for (Action a : opt.getActionList()) {
                        a.act();
                    }
                    update();
                }
            });
        }

    }

    private void update() {
        BorderImage bdrimg = new BorderImage(Game.getInstance().getCurrentImage(), new BorderWidths(100,10,10,10), new Insets(30), new BorderWidths(2,2,2,2), false, BorderRepeat.ROUND, BorderRepeat.ROUND);
        Border brd =new Border(bdrimg);
        System.out.println("---GAME UPDATED---");
        System.out.println("ROOM: "+Game.getInstance());
        room_label.setText(Game.getInstance().getPlayer().getCurrentRoom().getName());
        info_line.setText(Game.getInstance().getInfo_line());
        describtion_text.setText(Game.getInstance().getPlayer().getCurrentRoom().getDescription());
        System.out.println("DO: PopulateObtionBar");
        populateOptionBar();
        optionBarStackPane.setBorder(brd);
        System.out.println("DONE: Polulate option bar");
        imageView.setImage(Game.getInstance().getCurrentImage());
        HBox parent = (HBox) imageView.getParent();
        imageView.fitWidthProperty().bind(parent.widthProperty());
        imageView.fitHeightProperty().bind(parent.heightProperty());

    }

}
