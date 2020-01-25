/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.gameEvents.GameEvent;

/**
 *
 * @author David Brož
 */
public class ThrowGameEvent implements Action {

    private GameEvent whatToThrow;
    
    @Override
    public void act() {
        Game.getInstance().throwGameEvent(whatToThrow);
    }
    public ThrowGameEvent(GameEvent whatToThrow) {
        this.whatToThrow = whatToThrow;
    }

    @Override
    public String toString() {
        return "ThrowGameEvent{" + whatToThrow + '}';
    }
    
}
