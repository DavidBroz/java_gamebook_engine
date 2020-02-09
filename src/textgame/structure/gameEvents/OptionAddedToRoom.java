/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Option;
import textgame.structure.Room;
import textgame.utility.Pair;

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
    public Class getReturnClass() {
        return Pair.class;
    }

    @Override
    public Class getSettingClass() {
        return Option.class;
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof Pair))throw new IllegalArgumentException("Expected Pair got " + o.getClass());
        Pair temp = (Pair) o;
        if(temp.first instanceof Option) throw new IllegalArgumentException("Expected first Option in Pair got " + temp.first.getClass());
        if(temp.second instanceof Room) throw new IllegalArgumentException("Expected second Room in Pair got " + temp.second.getClass());
        room = (Room) temp.second;
        option = (Option) temp.first;
    }

    @Override
    public Object getValue() {
        return new Pair<>(option,room);
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
