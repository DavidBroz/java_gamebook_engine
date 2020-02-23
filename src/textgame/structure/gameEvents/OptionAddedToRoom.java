/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Option;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class OptionAddedToRoom extends GameEvent{

    private Option option;
    private Room room;
    public OptionAddedToRoom() {
    }

    
    @Override
    public GameEventType getEventType() {
        return GameEventType.OPTION_ADDED_TO_ROOM;
    }

    @Override
    public Class[] getReturnClasses() {
        return new Class[]{Option.class,Room.class};
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Option.class,Room.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(o[0] instanceof Option) throw new IllegalArgumentException("Expected Option, got " + o[0].getClass());
        if(o[1] instanceof Room) throw new IllegalArgumentException("Expected Room, got " + o[1].getClass());
        room = (Room) o[1];
        option = (Option) o[0];
    }

    @Override
    public Object[] getValues() {
        return new Object[]{option,room};
    }

    public OptionAddedToRoom(Option option, Room room) {
        this.option = option;
        this.room = room;
    }

    @Override
    public String toString() {
        return "OptionAddedToRoom{" + option + ", Room: " + room + '}';
    }
    
}
