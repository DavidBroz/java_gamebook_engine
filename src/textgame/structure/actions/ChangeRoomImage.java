/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import textgame.structure.Game;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class ChangeRoomImage implements Action, java.io.Serializable {
    private transient Image newImage;
    private Room whereToChange;
    private int imageSavedID;
    public ChangeRoomImage(Image newImage, Room whereToChange) {
        Game.getInstance().addActionImageToSave(this);
        this.newImage = newImage;
        this.whereToChange = whereToChange;
    }

    public Image getNewImage() {
        return newImage;
    }

    public void setNewImage(Image newImage) {
        this.newImage = newImage;
    }

    public int getImageSavedID() {
        return imageSavedID;
    }

    public void setImageSavedID(int imageSavedID) {
        this.imageSavedID = imageSavedID;
    }
    
    
    
    @Override
    public void act() {
        whereToChange.setImage(newImage);
    }

    @Override
    public String toString() {
        return "ChangeRoomImage{" +whereToChange+'}';
    }
    private boolean isValid = true;

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid = b;
        if(!isValid){
            Game.getInstance().removeActionImageToSave(this);
        }
    }
    
    public void readAndSetImage(ObjectInputStream s) throws IOException, ClassNotFoundException {
        //s.defaultReadObject();
        newImage = SwingFXUtils.toFXImage(ImageIO.read(s), null);
    }
    
    public void writeImage(ObjectOutputStream s) throws IOException {
        // s.defaultWriteObject();
        ImageIO.write(SwingFXUtils.fromFXImage(newImage, null), "png", s);
    }
}
