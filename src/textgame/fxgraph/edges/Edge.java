package textgame.fxgraph.edges;

import textgame.fxgraph.graph.Graph;
import textgame.fxgraph.graph.ICell;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class Edge extends AbstractEdge {

    private transient final StringProperty textProperty;

    public Edge(ICell source, ICell target) {
        super(source, target);
        textProperty = new SimpleStringProperty();
    }

    @Override
    public EdgeGraphic getGraphic(Graph graph) {
        return new EdgeGraphic(graph, this, textProperty);
    }

    public StringProperty textProperty() {
        return textProperty;
    }

    public static class EdgeGraphic extends Pane {

        private final Group group;
        private final Line line;
        private final Text text;

        public EdgeGraphic(Graph graph, Edge edge, StringProperty textProperty) {
            group = new Group();
            line = new Line();

            final DoubleBinding sourceX = edge.getSource().getXAnchor(graph, edge);
            final DoubleBinding sourceY = edge.getSource().getYAnchor(graph, edge);
            final DoubleBinding targetX = edge.getTarget().getXAnchor(graph, edge);
            final DoubleBinding targetY = edge.getTarget().getYAnchor(graph, edge);

            line.startXProperty().bind(sourceX);
            line.startYProperty().bind(sourceY);
            line.endXProperty().bind(targetX);
            line.endYProperty().bind(targetY);
            group.getChildren().add(line);

            line.setStyle("-fx-fill: white;-fx-stroke: white;");

            //setArrow
            final double arrow_size = 10;

            final Polygon view = new Polygon(arrow_size, 0, arrow_size, arrow_size, 0, arrow_size);
            view.setStroke(Color.WHITE);
            view.setFill(Color.WHITE);

            final StackPane pane = new StackPane(view);
            pane.setPrefSize(50, 50);
            pane.setMinSize(50, 50);
            final Scale scale = new Scale(1, 1);
            view.getTransforms().add(scale);
            scale.xProperty().bind(pane.widthProperty().divide(50));
            scale.yProperty().bind(pane.heightProperty().divide(50));
            pane.setAlignment(Pos.CENTER);
            
            pane.layoutXProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2).subtract(pane.widthProperty().divide(2)));
            pane.layoutYProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2).subtract(pane.heightProperty().divide(2)));
            DoubleBinding y = targetY.subtract(sourceY);
            DoubleBinding x = targetX.subtract(sourceX);
            DoubleBinding angle = new DoubleBinding() {

                {
                    super.bind(x,y);
                }

                @Override
                protected double computeValue() {
                    return -45+Math.toDegrees(Math.atan2(y.getValue(),x.getValue()));
                }
            };
            pane.rotateProperty().bind(angle);
            //pane.setStyle("-fx-border-color: #B8B8B8;-fx-border-radius: 1;-fx-border-width: 2;");
            group.getChildren().add(pane);

            final DoubleProperty textWidth = new SimpleDoubleProperty();
            final DoubleProperty textHeight = new SimpleDoubleProperty();
            text = new Text();
            text.textProperty().bind(textProperty);
            text.getStyleClass().add("edge-text");
            text.xProperty().bind(line.startXProperty().add(line.endXProperty()).divide(2).subtract(textWidth.divide(2)));
            text.yProperty().bind(line.startYProperty().add(line.endYProperty()).divide(2).subtract(textHeight.divide(2)));
            final Runnable recalculateWidth = () -> {
                textWidth.set(text.getLayoutBounds().getWidth());
                textHeight.set(text.getLayoutBounds().getHeight());
            };
            text.parentProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
            text.textProperty().addListener((obs, oldVal, newVal) -> recalculateWidth.run());
            group.getChildren().add(text);
            getChildren().add(group);
        }

        public Group getGroup() {
            return group;
        }

        public Line getLine() {
            return line;
        }

        public Text getText() {
            return text;
        }

    }

}
