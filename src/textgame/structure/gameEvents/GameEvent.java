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
        ItemRemovedFromInventory.class,
        OptionAddedToPlayer.class,
        CustomValueChanged.class,
        OptionRemovedFromPlayer.class,
        OptionRemovedFromRoom.class,
        StaticObjectRemovedFromRoom.class,
        OptionAddedToRoom.class,
        StaticObjectAddedToRoom.class,
        ItemRemovedFromRoom.class,
        ItemAddedToRoom.class
    };

    public enum GameEventType {
        ITEM_ADDED_TO_INVENTORY,
        ITEM_REMOVED_FROM_ROOM,
        OPTION_SELECTED,
        RANDOM_NUMBER,
        PLAYER_ENTERED_ROOM,
        PLAYER_LEFT_ROOM,
        ROOM_LOST_ITEM,
        EVENT_LISTENER_ACTED,
        ITEM_REMOVED_FROM_INVENTORY,
        OPTION_ADDED_TO_PLAYER,
        CUSTOM_VALUE_CHANGED,
        OPTION_REMOVED_FROM_PLAYER,
        OPTION_REMOVED_FROM_ROOM,
        STATIC_OBJECT_REMOVED_FROM_ROOM,
        STATIC_OBJECT_ADDED_TO_ROOM,
        OPTION_ADDED_TO_ROOM,
        ITEM_ADDED_TO_ROOM
    };

    public abstract GameEventType getEventType();

    public abstract Class[] getReturnClasses();

    public abstract Class[] getSettingClasses();

    public abstract void setValues(Object[] o);

    public abstract Object[] getValues();

    @Override
    public abstract String toString();

    public static Class[] getReturnClasses(GameEventType type) {
        GameEvent temp;
        try {
            for (Class c : gameEventClasses) {
                temp = (GameEvent) c.newInstance();
                if (temp.getEventType().equals(type)) {
                    return temp.getReturnClasses();
                }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GameEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("---GAME-EVENT---: getReturnClasses DID NOT FIND "+type.toString());
        return null;
    }

    public static Class[] getSettingClasses(GameEventType type) {
        GameEvent temp;
        try {
            for (Class c : gameEventClasses) {
                temp = (GameEvent) c.newInstance();
                if (temp.getEventType().equals(type)) {
                    return temp.getSettingClasses();
                }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GameEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("---GAME-EVENT---: getSettingClasses DID NOT FIND "+type.toString());
        return null;
    }

    public static GameEvent getInstanceOf(GameEventType type) {
        GameEvent temp;
        try {
            for (Class c : gameEventClasses) {
                temp = (GameEvent) c.newInstance();
                if (temp.getEventType().equals(type)) {
                    return temp;
                }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GameEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
