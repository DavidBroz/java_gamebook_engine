/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.GameEventListener;

/**
 *
 * @author David Brož
 */
public class GameEventListenerActed extends GameEvent{

    GameEventListener listener;
    
    public GameEventListenerActed() {
    }

    public GameEventListenerActed(GameEventListener listener) {
        this.listener = listener;
    }
    
    @Override
    public GameEventType getEventType() {
        return GameEventType.EVENT_LISTENER_ACTED;
    }

    @Override
    public Class getReturnClass() {
        return GameEventListener.class;
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof GameEventListener))throw new IllegalArgumentException("Expected GameEventListener got " + o.getClass());
        listener =((GameEventListener)o);
    }

    @Override
    public Object getValue() {
        return listener;
    }

    @Override
    public Class getSettingClass() {
        return GameEventListener.class;
    }

    @Override
    public String toString() {
        return "GameEventListenerActed: "+ listener;
    }
    
}
