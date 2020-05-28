/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import java.util.ArrayList;
import textgame.structure.actions.Action;

/**
 *
 * @author David Brož
 */
public class Item implements java.io.Serializable{

    private int id;
    private String name;
    private String description;
    private ArrayList referencedBy;
    private ArrayList<Option> options;

    public Item(String name, String description) {
        referencedBy = new ArrayList();
        options = new ArrayList();
        this.id = Game.getInstance().getItemMaxID();;
        Game.getInstance().incItemMaxID();
        this.name = name;
        this.description = description;
    }
    @Override
    public String toString(){
        return  id + "_" + name; 
    }

    public void setDescription(String newDescription) {
        description=newDescription;
    }

    public void setName(String newName) {
        name=newName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    void removeReferencedBy(Object o) {
        if(referencedBy.contains(o))referencedBy.remove(o);
    }

    public void deleteAllReferencesToThis() {
        for (Object o : referencedBy) {
            if (o instanceof Room) {
                Room r = (Room) o;                
                r.removeItem(this, false);
            } else if (o instanceof Player) {
                Player pl = (Player) o;                
                pl.removeItem(this,false);
            }if(o instanceof Action){
                Action a = (Action)o;
                a.setValidity(false);
            }
        }
    }
   

    void addReferencedBy(Object o) {
        referencedBy.add(o);
    }
}
