/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.player;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import textgame.structure.Game;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class ResourceManager {
   public static void save(Serializable data, String fileName) throws Exception{
       try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
           oos.writeObject(data);
       }
   }
   public static Object load(String fileName) throws Exception{
       System.out.println(fileName);
       try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
           Game g = (Game)ois.readObject();
           File roomImageFile;
           String roomImagePath = Paths.get(fileName).getParent().toString()+"\\Images\\Rooms\\";
           ObjectInputStream ois2;
           Object img;
           
           for(Room r : g.getAllRooms()){
               roomImageFile = new File(roomImagePath+r.toString()) ;
               if(roomImageFile.exists()){
                   ois2 = new ObjectInputStream(Files.newInputStream(roomImageFile.toPath()));
                   r.readAndSetImage(ois2);
               }
           }
           System.out.println("LOADED");
           
           return g;
       }
   }

    public static void saveProject(Serializable data, String dirPath, String projectName) throws Exception {
        new File(dirPath+"\\"+projectName).mkdirs();
        File directory = new File(dirPath+"\\"+projectName);
        new File(dirPath+"\\"+projectName+"\\Images").mkdirs();
        new File(dirPath+"\\"+projectName+"\\Images\\Rooms").mkdirs();
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath+"\\"+projectName+"\\"+projectName)))){
           oos.writeObject(data);
            ObjectOutputStream oos2; 
            for(Room r: Game.getInstance().getAllRooms()){
                
                if(r.getImage()!=null){
                    oos2 = new ObjectOutputStream(Files.newOutputStream(Paths.get(dirPath+"\\"+projectName+"\\Images\\Rooms\\"+r.toString())));
                    r.writeImage(oos2);
                }
                else System.out.println("Room has no Image: "+r.toString());
            }
        }  
       
        
        System.out.println(dirPath+"\\"+projectName);

    }
}
