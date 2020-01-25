/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author David Brož
 */
public abstract class GameEvent implements java.io.Serializable {

    public static Class[] gameEventClasses = {
        ItemAddedToInventory.class,
        OptionSelected.class,
        PlayerEnteredRoom.class,
        PlayerLeftRoom.class,
        RandomNumber.class,
        GameEventListenerActed.class,
        RoomLostItem.class,
        ItemRemovedFromInventory.class
    };
    
    public enum GameEventType{
        ITEM_ADDED_TO_INVENTORY,
        ITEM_REMOVED_FROM_ROOM,
        OPTION_SELECTED,
        RANDOM_NUMBER,
        PLAYER_ENTERED_ROOM,
        PLAYER_LEFT_ROOM,
        ROOM_LOST_ITEM,
        EVENT_LISTENER_ACTED,
        ITEM_REMOVED_FROM_INVENTORY
    };
    
    public abstract GameEventType getEventType();
    
    public abstract Class getReturnClass();
    
    public abstract Class getSettingClass();
    
    public abstract void setValue(Object o);
    
    public abstract Object getValue();
    
    public abstract String toString();
    
    public static Class getReturnClass(GameEventType type){
        GameEvent temp;
        try {
            for (Class c : gameEventClasses) {
                    temp =(GameEvent)c.newInstance();
                    if(temp.getEventType().equals(type)){
                        return temp.getReturnClass();
                    }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(GameEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
    
    public static Class getSettingClass(GameEventType type) {
        GameEvent temp;
        try {
            for (Class c : gameEventClasses) {
                    temp =(GameEvent)c.newInstance();
                    if(temp.getEventType().equals(type)){
                        return temp.getSettingClass();
                    }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(GameEvent.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }
}
