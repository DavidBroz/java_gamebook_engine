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
import java.nio.file.Path;
import java.nio.file.Paths;
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
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            oos.writeObject(data);
        }
    }

    public static Object load(String filePath) throws Exception {
        System.out.println("-RESOURCE-MANAGER: File path:" + filePath);
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)))) {
            Game g = (Game) ois.readObject();
            File roomImageFile, actionImageFile;
            String actionImagePath = Paths.get(filePath).getParent().toString() + "\\Images\\Actions\\";
            String roomImagePath = Paths.get(filePath).getParent().toString() + "\\Images\\Rooms\\";
            ObjectInputStream ois2;
            Object img;

            for (Room r : g.getAllRooms()) {
                roomImageFile = new File(roomImagePath + r.toString());
                if (roomImageFile.exists()) {
                    ois2 = new ObjectInputStream(Files.newInputStream(roomImageFile.toPath()));
                    r.readAndSetImage(ois2);
                }
            }
            for (Action a : g.getAllActionImagesToSave()) {
                if (a instanceof ChangeRoomImage) {
                    ChangeRoomImage cri = (ChangeRoomImage) a;

                    actionImageFile = new File(actionImagePath + cri.getImageSavedID());
                    if (actionImageFile.exists()) {
                        ois2 = new ObjectInputStream(Files.newInputStream(actionImageFile.toPath()));
                        cri.readAndSetImage(ois2);
                    }

                }
                if (a instanceof ChangeCurrentImage) {
                    ChangeCurrentImage cri = (ChangeCurrentImage) a;
                    actionImageFile = new File(actionImagePath + cri.getImageSavedID());
                    if (actionImageFile.exists()) {
                        ois2 = new ObjectInputStream(Files.newInputStream(actionImageFile.toPath()));
                        cri.readAndSetImage(ois2);
                    }

                }
            }
            System.out.println("LOADED");

            return g;
        }
    }

    public static void saveProject(Serializable data, String dirPath, String projectName) throws Exception {
        new File(dirPath + "\\" + projectName).mkdirs();
        File directory = new File(dirPath + "\\" + projectName);
        new File(dirPath + "\\" + projectName + "\\Images").mkdirs();
        new File(dirPath + "\\" + projectName + "\\Images\\Rooms").mkdirs();
        new File(dirPath + "\\" + projectName + "\\Images\\Actions").mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\" + projectName)))) {
            oos.writeObject(data);
            ObjectOutputStream oos2;
            for (Room r : Game.getInstance().getAllRooms()) {

                if (r.getImage() != null) {
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\Images\\Rooms\\" + r.toString())));
                    r.writeImage(oos2);
                } else {
                    System.out.println("Room has no Image: " + r.toString());
                }
            }
            int i = 0;
            for (Action a : Game.getInstance().getAllActionImagesToSave()) {
                if (a instanceof ChangeRoomImage) {
                    ChangeRoomImage cri = (ChangeRoomImage) a;
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\Images\\Actions\\" + i)));
                    cri.writeImage(oos2);
                }
                if (a instanceof ChangeCurrentImage) {
                    ChangeCurrentImage cri = (ChangeCurrentImage) a;
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath + "\\" + projectName + "\\Images\\Actions\\" + i)));
                    cri.writeImage(oos2);
                }

            }
        }
        System.out.println (dirPath + "\\" + projectName);
    }
}
