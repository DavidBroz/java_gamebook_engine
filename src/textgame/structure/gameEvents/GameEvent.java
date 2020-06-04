/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.GameEventListener;
import textgame.structure.Item;
import textgame.structure.Option;
import textgame.structure.Room;
import textgame.structure.StaticObject;

/**
 *
 * @author David Brož
 */
public class GameEvent implements java.io.Serializable {
    private Object [] values;
    private GameEventType gameEventType;

    public GameEvent(Object[] values, GameEventType gameEventType) {
        this.values = values;
        this.gameEventType = gameEventType;
    }

    public GameEvent(GameEventListener aThis) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public GameEventType getGameEventType() {
        return gameEventType;
    }

    public void setGameEventType(GameEventType gameEventType) {
        this.gameEventType = gameEventType;
    }
    
    public static Class[] getValueClassesOf(GameEventType type){
        switch(type){
            case ITEM_REMOVED_FROM_INVENTORY:
            case ITEM_ADDED_TO_INVENTORY:
                return new Class[]{Item.class};
            case ITEM_REMOVED_FROM_ROOM:
            case ITEM_ADDED_TO_ROOM:
                return new Class[]{Item.class, Room.class};
            case OPTION_SELECTED:
            case OPTION_REMOVED_FROM_PLAYER:
            case OPTION_ADDED_TO_PLAYER:
                return new Class[]{Option.class};
            case PLAYER_ENTERED_ROOM:
            case PLAYER_LEFT_ROOM:
                return new Class[]{Room.class};
            case GAME_EVENT_LISTENER_ACTED:
                return new Class[]{GameEventListener.class};
            case CUSTOM_VALUE_CHANGED:
                return new Class[]{String.class, Integer.class};
            case OPTION_REMOVED_FROM_ROOM:
            case OPTION_ADDED_TO_ROOM:
                return new Class[]{Option.class, Room.class};
            case STATIC_OBJECT_REMOVED_FROM_ROOM:
            case STATIC_OBJECT_ADDED_TO_ROOM:
                return new Class[]{StaticObject.class, Room.class};
            case ROOM_LOST_ITEM:
                return new Class[]{Room.class, Item.class};
            case RANDOM_NUMBER:
                return new Class[]{Integer.class};
            case CUSTOM_EVENT:
                return new Class[]{String.class};
            default: return null;
            
        }
    }
    
    public enum GameEventType {
        ITEM_ADDED_TO_INVENTORY,
        ITEM_REMOVED_FROM_ROOM,
        OPTION_SELECTED,
        RANDOM_NUMBER,
        PLAYER_ENTERED_ROOM,
        PLAYER_LEFT_ROOM,
        ROOM_LOST_ITEM,
        GAME_EVENT_LISTENER_ACTED,
        ITEM_REMOVED_FROM_INVENTORY,
        OPTION_ADDED_TO_PLAYER,
        CUSTOM_VALUE_CHANGED,
        OPTION_REMOVED_FROM_PLAYER,
        OPTION_REMOVED_FROM_ROOM,
        STATIC_OBJECT_REMOVED_FROM_ROOM,
        STATIC_OBJECT_ADDED_TO_ROOM,
        OPTION_ADDED_TO_ROOM,
        ITEM_ADDED_TO_ROOM,
        CUSTOM_EVENT
    };

    @Override
    public String toString() {
        return "GameEvent{" + gameEventType + ", values=" + values + '}';
    }
    
    
}
