/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.fxgraph.layout;

import java.util.ArrayList;
import java.util.List;
import textgame.fxgraph.cells.AbstractCell;
import textgame.fxgraph.cells.RoomCell;
import textgame.fxgraph.graph.Graph;
import textgame.fxgraph.graph.ICell;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class CustomLayout implements Layout {

    @Override
    public void execute(Graph graph) {
        List<ICell> cells = graph.getModel().getAllCells();
        ArrayList<RoomCell> rooms_cells = new ArrayList<>();
        for(ICell c: cells){
            rooms_cells.add((RoomCell)c);
        }
        Room r;
        for(RoomCell rc: rooms_cells){
            r = rc.getRoom();
            graph.getGraphic(rc).relocate(r.getLocation_x(), r.getLocation_y()); 
        }
    }
}
