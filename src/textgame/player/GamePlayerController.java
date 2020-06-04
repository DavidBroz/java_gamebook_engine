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
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
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
    private Label description_text;

    @FXML
    private Label info_line;

    @FXML
    private VBox optionBox,
            middleScrollPane_VBox;

    @FXML
    private MenuItem load_button;
    @FXML
    private MenuItem save_button;

    @FXML
    private MenuItem start_game_button;

    @FXML
    private ImageView imageView;
    @FXML
    private Pane imageViewPane;
    @FXML
    private StackPane optionBarStackPane;
    @FXML
    private ScrollPane middleScrollPane;
    /*@FXML
    private List option_list;*/
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageView.fitHeightProperty().bind(imageViewPane.heightProperty());
        optionBox.setPrefWidth(200);
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
        setGUI();

        System.out.println("--GAME-PLAYER-CONTROLLER--: Stats first update");
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
        setGUI();
        optionBox.getChildren().clear();
        ArrayList<Option> options = Game.getInstance().getPlayer().getCurrentRoom().generateOptions();
        for (Option opt : options) {
            System.out.println(opt);
            Button tempButton = new Button(opt.getOptionLabel());
            tempButton.setPrefWidth(optionBox.getPrefWidth());
            optionBox.getChildren().add(tempButton);

            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event2) {
                    opt.runActions();
                    update();
                }
            });
            tempButton.setMinWidth(optionBox.getPrefWidth());
            tempButton.setStyle("-fx-font: "+Game.getInstance().getFontButton()+";");
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
            tempButton.setMinWidth(optionBox.getPrefWidth());
            tempButton.setStyle("-fx-font: "+Game.getInstance().getFontButton()+";");
        }

    }

    private void update() {

        //BorderImage bdrimg = new BorderImage(Game.getInstance().getCurrentImage(), new BorderWidths(100,10,10,10), new Insets(30), new BorderWidths(2,2,2,2), false, BorderRepeat.ROUND, BorderRepeat.ROUND);
        //Border brd =new Border(bdrimg);
        System.out.println("---GAME UPDATED---");
        System.out.println("ROOM: " + Game.getInstance());
        room_label.setText(Game.getInstance().getPlayer().getCurrentRoom().getName());
        info_line.setText(Game.getInstance().getInfo_line());
        description_text.setText(Game.getInstance().getPlayer().getCurrentRoom().getDescription());
        description_text.setWrapText(true);
        description_text.setPrefWidth(300);
        info_line.setWrapText(true);
        info_line.setPrefWidth(300);
        System.out.println("DO: PopulateObtionBar");
        populateOptionBar();
        //optionBarStackPane.setBorder(brd);
        System.out.println("DONE: Polulate option bar");
        imageView.setImage(Game.getInstance().getCurrentImage());
        setGUI();

    }

    private void setGUI() {
        description_text.setStyle("-fx-font: "+Game.getInstance().getFontDescription()+";");
        info_line.setStyle("-fx-font: "+Game.getInstance().getFontInfoLine()+";");
        room_label.setStyle("-fx-font: "+Game.getInstance().getFontRoomName()+";");
        
        
        if (Game.getInstance().getGUI("optionBarBg")!=null) {
            BackgroundImage optionBarBI = new BackgroundImage(Game.getInstance().getGUI("optionBarBg"),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            optionBox.setBackground(new Background(optionBarBI));
        }
        if (Game.getInstance().getGUI("textAreaBg")!=null) {
        BackgroundImage textPaneBarBI = new BackgroundImage(Game.getInstance().getGUI("textAreaBg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        middleScrollPane_VBox.setBackground(new Background(textPaneBarBI));
        }
    }

}
