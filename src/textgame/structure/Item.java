/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

/**
 *
 * @author David Brož
 */
public class Item implements java.io.Serializable{

    private int id;
    private String name;
    private String description;

    public Item(String name, String description) {
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
}
