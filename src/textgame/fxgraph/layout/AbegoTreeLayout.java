package textgame.fxgraph.layout;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.StyleConstants;
import org.abego.treelayout.Configuration;
import org.abego.treelayout.Configuration.Location;
import org.abego.treelayout.NodeExtentProvider;
import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;
import textgame.fxgraph.cells.AbstractCell;
import textgame.fxgraph.edges.AbstractEdge;

import textgame.fxgraph.graph.Graph;
import textgame.fxgraph.graph.ICell;
import textgame.fxgraph.graph.IEdge;

public class AbegoTreeLayout implements Layout {

    private final Configuration<ICell> configuration;
    private ArrayList<IEdge> edge_buffer;

    public AbegoTreeLayout() {
        this(100, 45, Location.Top);
    }

    public AbegoTreeLayout(double gapBetweenLevels, double gapBetweenNodes, Location location) {
        this(new DefaultConfiguration<ICell>(gapBetweenLevels, gapBetweenNodes, location));
    }

    public AbegoTreeLayout(Configuration<ICell> configuration) {
        this.configuration = configuration;
    }

    @Override
    public void execute(Graph graph) {

        
        final DefaultTreeForTreeLayout<ICell> layout = new DefaultTreeForTreeLayout<>(graph.getModel().getRoot());
        addRecursively(layout, graph.getModel().getRoot());
        final NodeExtentProvider<ICell> nodeExtentProvider = new NodeExtentProvider<ICell>() {

            @Override
            public double getWidth(ICell tn) {
                if (tn == graph.getModel().getRoot()) {
                    return 0;
                }
                return graph.getGraphic(tn).getWidth();
            }

            @Override
            public double getHeight(ICell tn) {
                if (tn == graph.getModel().getRoot()) {
                    return 0;
                }
                return graph.getGraphic(tn).getHeight();
            }
        };
        final TreeLayout<ICell> treeLayout = new TreeLayout<>(layout, nodeExtentProvider, configuration);
        treeLayout.getNodeBounds().entrySet().stream().filter(entry -> entry.getKey() != graph.getModel().getRoot()).forEach(entry -> {
            graph.getGraphic(entry.getKey()).setLayoutX(entry.getValue().getX());
            graph.getGraphic(entry.getKey()).setLayoutY(entry.getValue().getY());
        });

    }

    public void addRecursively(DefaultTreeForTreeLayout<ICell> layout, ICell node) {
        node.getCellChildren().forEach(child -> {
            if (!layout.hasNode(child)) {
                layout.addChild(node, child);
                addRecursively(layout, child);
            }
        });
    }

   

}
