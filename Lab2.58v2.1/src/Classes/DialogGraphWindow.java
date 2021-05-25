package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import javax.print.DocFlavor;
import java.awt.event.MouseEvent;


public class DialogGraphWindow {
    private GraphController graph;
    private GraphController mainGraph;
    private ObservableList<LineEquation> lines = FXCollections.<LineEquation>observableArrayList();
    private ObservableList<Line> classLineLines;
    private Line selectedLine;
    private LineEquation thisLine = null;
    private LineEquation originalLine;
    private Dialog<LineEquation> dialog;
    private AnchorPane root;

    public DialogGraphWindow(ObservableList<LineEquation> lines, String title, GraphController mainGraph) {

        this.mainGraph = mainGraph;
        //this.classLineLines = classLineLines;
        //this.lines = lines;
        this.lines.addAll(lines);

        //this.thisLine = line;
        dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        createButtons();

        //System.err.println(graph.getLines());
        System.out.println(lines);
        graph = new GraphController(new ViewMini(thisLine));
        graph.createAnchorPane();
     //   classLineLines.stream().filter(e -> !e.equals(selectedLine)).forEach(e -> graph.addGhostLine(e));

        root = graph.getAnchorPane();

        dialog.getDialogPane().setContent(root);

    }

    public void paintGhostLines(){
        lines.stream().filter(e -> !e.equals(originalLine)).forEach(cooLine -> {
            Line line = new Line(cooLine.getX1()+250, 250-cooLine.getY1(), 250+cooLine.getX2(), 250-cooLine.getY2());

            line.setSmooth(true);
            line.setStrokeLineCap(StrokeLineCap.ROUND);
   //color         line.strokeProperty().bind(cooLine.colorProperty());

//            if (cooLine.equals(thisLine)){
//                line.setStrokeWidth(2);
//                cooLine.setColor(Color.BLACK);
//            }
//            else {
                line.setStrokeWidth(0.7);
   //color             cooLine.setColor(Color.DIMGRAY);
            line.setStroke(Color.DIMGRAY);
//            }


            root.getChildren().add(line);
        });

    }


    public void createPerpendicularLine(LineEquation cooLine) {
        Line selectedLine = new Line(cooLine.getX1()+250, 250-cooLine.getY1(), 250+cooLine.getX2(), 250-cooLine.getY2());

        selectedLine.setSmooth(true);
        selectedLine.setStrokeLineCap(StrokeLineCap.ROUND);
        selectedLine.setStrokeWidth(2.0);
        selectedLine.setStroke(Color.BLACK);
        root.getChildren().add(selectedLine);
        root.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                double k = -1/cooLine.getK();
                double b = (250-event.getY())-k*(event.getX()-250);
                thisLine = new LineEquation(1, 1, 2, 2);
                thisLine.kProperty().set(k);
                thisLine.bProperty().set(b);
                thisLine.setEquation();
                thisLine.calcCoordinates();

                graph.addElement(thisLine);

            }return;
        });
    }

    public void createParallelLine(LineEquation cooLine) {
        Line selectedLine = new Line(cooLine.getX1()+250, 250-cooLine.getY1(), 250+cooLine.getX2(), 250-cooLine.getY2());

        selectedLine.setSmooth(true);
        selectedLine.setStrokeLineCap(StrokeLineCap.ROUND);
        selectedLine.setStrokeWidth(2.0);
        selectedLine.setStroke(Color.BLACK);
        root.getChildren().add(selectedLine);
        root.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                double k = cooLine.getK();
                double b = (250-event.getY())-k*(event.getX()-250);
                thisLine = new LineEquation(1, 1, 2, 2);
                thisLine.kProperty().set(k);
                thisLine.bProperty().set(b);
                thisLine.setEquation();
                thisLine.calcCoordinates();

                graph.addElement(thisLine);
                    } return;
                });
    }

    public void createNewLine() {

        root.setOnMouseClicked( event -> {
            if(event.getClickCount() == 2) {
                Circle point1 = new Circle(event.getX(), event.getY(), 2);
                root.getChildren().add(point1);
                System.err.println(("12345"));
                root.setOnMouseClicked(event2 -> {
                    System.err.println("2222233");
                    if (event2.getClickCount() == 2) {
                        System.err.println("321321");
                        Circle point2 = new Circle(event2.getX(), event2.getY(), 2);
                        root.getChildren().add(point2);
                        thisLine = new LineEquation(event.getX(), event.getY(), event2.getX(), event2.getY());
                        root.getChildren().removeAll(point1, point2);
                        //originalLine = new Line();

                        graph.addElement(thisLine);
                       // originalLine = new LineEquation(event.getX(), event.getY(), event2.getX(), event2.getY());
                      //  mainGraph.addElement(originalLine);
                       // this.thisLine = new LineEquation(line.getX1()+250, 250-line.getY1(), line.getX2()+250, 250-line.getY2());
//                        Line newLine = graph.createLine(thisLine);
//                        selectedLine = newLine;
//                        root.getChildren().add(newLine);
                       // graph.createLine(cooLine);
                        return;
                    }

                });
                return;
            }

        });
    }

    public void addEditableLine(LineEquation line, Line selectedLine) {

        this.selectedLine = selectedLine;
        originalLine = new LineEquation(line.getX1()+250, 250-line.getY1(), line.getX2()+250, 250-line.getY2());
        mainGraph.removeLine(line, selectedLine);
       // graph.removeSelectedElement();
        this.thisLine = new LineEquation(line.getX1()+250, 250-line.getY1(), line.getX2()+250, 250-line.getY2());
        //root.getChildren().add(graph.createLine(thisLine));
        graph.addElement(thisLine);
    }

    private void createButtons() {
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                if ( mainGraph.getLines().stream().anyMatch(e -> e.equals(thisLine))) return null;
                else  {
                    handleOk();
                    return thisLine;
                }
            } else {
                mainGraph.addElement(originalLine);
               // originalLine = null;
               // selectedLine = null;
                return originalLine;
            }
        });
    }

    private void message(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Data entry error");
        alert.setHeaderText("Name entry error");
        alert.setContentText("The name of organization is empty");
        alert.showAndWait();
    }

    private void handleOk() {
        mainGraph.addElement(thisLine);
    }



    public Dialog<LineEquation> getDialog() {
        return dialog;
    }

}
