/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.utility;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javafx.stage.FileChooser;
import textgame.structure.Game;
import textgame.structure.Room;
import textgame.structure.actions.Action;
import textgame.structure.actions.ChangeCurrentImage;
import textgame.structure.actions.ChangeRoomImage;

/**
 *
 * @author David Brož
 */
public class ResourceManager {

    public static void save(Serializable data, String fileName) throws Exception {
        try ( ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            oos.writeObject(data);
        }
    }

    public static Object load(String filePath) throws Exception {
        System.out.println("-RESOURCE-MANAGER: File path:" + filePath);
        try ( ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)))) {
            Game g = (Game) ois.readObject();
            File roomImageFile, actionImageFile;
            String actionImagePath = Paths.get(filePath).getParent().toString() + "\\Images\\Actions\\";
            String roomImagePath = Paths.get(filePath).getParent().toString() + "\\Images\\Rooms\\";
            String guiPath = Paths.get(filePath).getParent().toString() + "\\Images\\GUI\\";
            ObjectInputStream ois2;

            for (Room r : g.getAllRooms()) {
                roomImageFile = new File(roomImagePath + r.toString() + ".jpg");
                if (roomImageFile.exists()) {
                    ois2 = new ObjectInputStream(Files.newInputStream(roomImageFile.toPath()));
                    r.readAndSetImage(ois2);
                }
            }
            for (Action a : g.getAllActionImagesToSave()) {
                if (a instanceof ChangeRoomImage) {
                    ChangeRoomImage cri = (ChangeRoomImage) a;

                    actionImageFile = new File(actionImagePath + cri.getImageSavedID() + ".jpg");
                    if (actionImageFile.exists()) {
                        ois2 = new ObjectInputStream(Files.newInputStream(actionImageFile.toPath()));
                        cri.readAndSetImage(ois2);
                    }

                }
                if (a instanceof ChangeCurrentImage) {
                    ChangeCurrentImage cri = (ChangeCurrentImage) a;
                    actionImageFile = new File(actionImagePath + cri.getImageSavedID() + ".jpg");
                    if (actionImageFile.exists()) {
                        ois2 = new ObjectInputStream(Files.newInputStream(actionImageFile.toPath()));
                        cri.readAndSetImage(ois2);
                    }

                }
            }
            System.out.println("-RESOURCE-MANAGER-: Game is null:"+(g==null));
            List<String> set = g.getGUIKeySet();
            System.out.println("-RESOURCE-MANAGER-: is set null:"+(set==null));
            System.out.println("-RESOURCE-MANAGER-: is set empty:"+set.isEmpty());
            for (String s : set) {
                System.out.println("-RESOURCE-MANAGER-: key set for GUI: " + s);
            }

            for (String s : set) {
                ois2 = new ObjectInputStream(Files.newInputStream(new File(guiPath + s + ".jpg").toPath()));
                System.out.println("-RESOURCE-MANAGER-: Processing: " + s);
                System.out.println("-RESOURCE-MANAGER-: is ios2 null " + (ois2==null));
                g.readAndSetImage(ois2, s);
            }
            System.out.println("-RESOURCE-MANAGER-:LOADED");

            return g;
        }
    }

    public static void saveProject(Serializable data, String dirPath, String projectName) throws Exception {
        new File(dirPath + "\\" + projectName).mkdirs();
        File directory = new File(dirPath + "\\" + projectName);
        new File(dirPath + "\\" + projectName + "\\Images").mkdirs();
        new File(dirPath + "\\" + projectName + "\\Images\\Rooms").mkdirs();
        new File(dirPath + "\\" + projectName + "\\Images\\Actions").mkdirs();
        new File(dirPath + "\\" + projectName + "\\Images\\GUI").mkdirs();
        try ( ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\" + projectName + ".game")))) {
            oos.writeObject(data);
            ObjectOutputStream oos2;
            
            if(Game.getInstance().getGUIKeys()!=null){
                for (String key : Game.getInstance().getGUIKeys()) {
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\Images\\GUI\\" + key + ".jpg")));
                    Game.getInstance().writeImage(oos2, key);
                }
            }

            for (Room r : Game.getInstance().getAllRooms()) {

                if (r.getImage() != null) {
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\Images\\Rooms\\" + r.toString() + ".jpg")));
                    r.writeImage(oos2);
                } else {
                    System.out.println("Room has no Image: " + r.toString());
                }
            }
            int i = 0;
            for (Action a : Game.getInstance().getAllActionImagesToSave()) {
                if (a instanceof ChangeRoomImage) {
                    ChangeRoomImage cri = (ChangeRoomImage) a;
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\Images\\Actions\\" + i + ".jpg")));
                    cri.writeImage(oos2);
                }
                if (a instanceof ChangeCurrentImage) {
                    ChangeCurrentImage cri = (ChangeCurrentImage) a;
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\Images\\Actions\\" + i + ".jpg")));
                    cri.writeImage(oos2);
                }

            }
        }

        System.out.println(dirPath
                + "\\" + projectName);
    }
    
    public static Game loadGameDialog(){
        Game result = null;
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Game project(.game)", "*.game");
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(extFilter);
        File workingDirectory = new File(System.getProperty("user.dir"));
        fc.setInitialDirectory(workingDirectory);
        File selectedFile = fc.showOpenDialog(null);

        try {
            result =(Game)ResourceManager.load(selectedFile.getPath());
        } catch (Exception e) {
            System.out.println("Something went wrong with loading the game:");
            System.out.println(e.toString());
            System.out.println(e.getMessage());
        }
        return result;
    }
}
