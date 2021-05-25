package Classes;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

public class GraphController {
    private LineEquation thisLine;
    private LineEquation lastLine = null;
    private DoubleProperty k, b;
    private Line selectedLine;
    private Line lastSelectedLine = null;
    private AnchorPane anchorPane;
    private ObservableList<LineEquation> lines;
    private ObservableList<Line> classLineLines;
    private ViewMini viewMini;

    public GraphController(ViewMini viewMini) {
        this.viewMini = viewMini;
    }

    public LineEquation getThisLine() {
        return thisLine;
    }

    public Line getSelectedLine() {
        return  selectedLine;
    }

    public ObservableList<Line> getClassLineLines() {
        return classLineLines;
    }

//    public String getSelectedEquation() {
//        return selectedEquationProperty().get();
//    }
//
//    public StringProperty selectedEquationProperty() {
//        if (selectedEquation == null)
//           selectedEquation = new SimpleStringProperty();
//        return selectedEquation;
//    }
//
//    public void setSelectedEquation(String selectedEquation) {
//        selectedEquationProperty().set(selectedEquation);
//    }

    public ObservableList<LineEquation> getLines() {
        return lines;
    }




//    public GraphController(LineEquation thisLine) {
//        this.thisLine = thisLine;
//    }

    public AnchorPane getAnchorPane(){ return anchorPane;}

    public void addElement(LineEquation line){

        if(line == null) return;
        lines.add(line);
        anchorPane.getChildren().add(createLine(line));

    }

    public void removeLine(LineEquation line, Line thisLine) {
        lines.remove(line);
        anchorPane.getChildren().remove(thisLine);
        viewMini.getLines().remove(line);
    }

    public void removeSelectedElement() {
        lines.remove(thisLine);
        anchorPane.getChildren().remove(selectedLine);
        viewMini.getLines().remove(thisLine);

        thisLine = null;
        lastLine = null;
        selectedLine = null;
        lastSelectedLine = null;
    }

    public void addGhostLine(Line line) {
        anchorPane.getChildren().add(line);
    }


    public void createAnchorPane(){
        anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-font-size: 22");
        anchorPane.setMinSize(505,505);
        // anchorPane.setPadding(new Insets(150));

        //anchorPane.getChildren().add(createCoordinate());

        lines = FXCollections.<LineEquation>observableArrayList();
        classLineLines = FXCollections.<Line>observableArrayList();
        //viewMini.setLines(lines);


        createCoordinate();

    }

    public void createCoordinate(){
        for (int i = 0; i < 20; i++) {
            Line wLine = new Line(i * 25, 0, i * 25, 500);
            wLine.setStrokeWidth(0.5);
            wLine.getStrokeDashArray().addAll(5.0,2.0);
            wLine.setStroke(Color.GRAY);
            anchorPane.getChildren().add(wLine);
            Text xMark = new Text(i*25, 260, String.valueOf(i-10));
            xMark.setFont(Font.font(10.0));
            anchorPane.getChildren().add(xMark);

            Line hLine = new Line(0, i * 25, 500, i * 25);
            hLine.setStrokeWidth(0.5);
            hLine.getStrokeDashArray().addAll(5.0,2.0);
            hLine.setStroke(Color.GRAY);
            anchorPane.getChildren().add(hLine);
            if(i==10) continue;
            Text yMark = new Text(255, 10+i*25, String.valueOf(10-i));
            yMark.setFont(Font.font(10.0));
            anchorPane.getChildren().add(yMark);
        }

        //оси
        Line xAxis = new Line(0, 250, 500, 250);
        xAxis.setStrokeWidth(1.5);
        xAxis.setStroke(Color.BLACK);
        anchorPane.getChildren().add(xAxis);

        Line yAxis = new Line(250, 0, 250, 500);
        xAxis.setStrokeWidth(1.5);
        xAxis.setStroke(Color.BLACK);
        anchorPane.getChildren().add(yAxis);
    }




    public Line createLine(LineEquation cooLine) {

        /////////////////////cooLine.calcCoordinates();

  //      System.out.println(cooLine.getX1()+" "+cooLine.getY1() +' '+' '+ cooLine.getX2()+' '+cooLine.getY2());
        Line line = new Line(cooLine.getX1()+250, 250-cooLine.getY1(), 250+cooLine.getX2(), 250-cooLine.getY2());

        line.setSmooth(true);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStrokeWidth(2);

  //color      line.strokeProperty().bind(cooLine.colorProperty());
   //color     cooLine.setColor(Color.BLACK);
        line.setStroke(Color.BLACK);


        addTranslateListener(line, cooLine);
        viewMini.addLineToList(cooLine);


        //line.setStroke(Color.RED);
       // line.setStyle("-fx-color-value: RED");
        //System.out.println(line.getStyle());

        return line;
    }

    private Point2D delta;
    private double pivotX;
    private double pivotY;
    private double xBeforeDrag;
    private double yBeforeDrag;
    private boolean isRotate;
    private void addTranslateListener(Line node, LineEquation line) {
        node.addEventHandler(MouseEvent.ANY, (e)-> {
            node.requestFocus();
        });


        node.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent mouseEvent) -> {
            node.toFront();
            node.setStrokeWidth(5);

            System.out.printf("%s 44 %s 6 %s   %s  %s   %s%n", mouseEvent.getSceneX(), node.getEndX(), anchorPane.getLayoutX(), anchorPane.getLayoutY(), node.getEndY(), mouseEvent.getSceneY());

//            pivotX = (node.getEndX()+node.getStartX())/2;
//            pivotY = (node.getEndY()+node.getStartY())/2;
//            line.editLine(pivotX, pivotY, mouseEvent.getSceneX(), mouseEvent.getSceneY());
            viewMini.setElement(line);
        });

        node.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent mouseEvent) -> {
                node.setStrokeWidth(2);
               // line.setColor(Color.BLACK);
        });

        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
                });

        node.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent mouseEvent) -> {
            xBeforeDrag = mouseEvent.getSceneX();
            yBeforeDrag = mouseEvent.getSceneY();
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                isRotate = true;
            }
            pivotX = (node.getEndX()+node.getStartX())/2;
            pivotY = (node.getEndY()+node.getStartY())/2;


            if (line == thisLine) ;
            else {
                lastLine = thisLine;
                lastSelectedLine = selectedLine;

                thisLine = line;
                selectedLine = node;

   //color             thisLine.setColor(Color.DARKGREEN);
                selectedLine.setStroke(Color.DARKGREEN);
                if (lastLine == null) ;
                else {
   //color                 lastLine.setColor(Color.BLACK);

                    lastSelectedLine.setStroke(Color.BLACK);
                    lastLine = line;
                    lastSelectedLine = node;
                }
                // anchorPane.getChildren().stream().filter(e -> e.getClass() == Line.class).filter(e -> !e.equals(node)).filter(e -> !((Line) e).getStrokeDashArray().isEmpty()).forEach(e -> ((Line) e).getStrokeDashArray().removeAll(6.0));
                //node.getStrokeDashArray().addAll(6.0);
            }

            System.out.println(pivotX+" 00000000 "+ pivotY + " 00  " + line.getX2()+"  9 " +node.getEndX()+" 66 "+line.getY2()+" 77 "+ node.getEndY()+ " 55 " +mouseEvent.getSceneY() );
        });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, (MouseEvent mouseEvent) -> {

            isRotate = false;
        });

        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent mouseEvent) -> {
            node.setStrokeWidth(5);
            //line.setColor(Color.BLACK);
            //lastLine = line;
            if (isRotate) {

                line.editLine(pivotX, pivotY, mouseEvent.getSceneX()-anchorPane.getLayoutX(), mouseEvent.getSceneY()-anchorPane.getLayoutY());

                node.setStartX(line.getX1()+250);
                node.setStartY(250-line.getY1());
                node.setEndX(line.getX2()+250);
                node.setEndY(250-line.getY2());

//                System.out.println(lines.get(0).getEquation());
//                double dx1 = mouseEvent.getSceneX() - node.getLayoutX();
//                double dy1 = mouseEvent.getSceneY() - node.getLayoutY();
//                double angle = Math.acos(dx1/Math.sqrt(dx1 * dx1 + dy1 * dy1));
//                if (dy1 < 0) {
//                    angle = Math.PI - angle;
//                }
//                node.setRotate(Math.toDegrees(angle));
            } else {
                delta = new Point2D(mouseEvent.getSceneX() - xBeforeDrag, mouseEvent.getSceneY() - yBeforeDrag);
                if(((mouseEvent.getSceneX()-anchorPane.getLayoutX() -node.getStartX()) < 10)&&((mouseEvent.getSceneY()-anchorPane.getLayoutY() -node.getStartY()) < 10)){
                    line.editLine(line.getX2()+250+delta.getX(), 250-line.getY2()+delta.getY(), mouseEvent.getSceneX()-anchorPane.getLayoutX(), mouseEvent.getSceneY()-anchorPane.getLayoutY());
                }else {
                    line.editLine(line.getX1()+250 + delta.getX(), 250-line.getY1() + delta.getY(), mouseEvent.getSceneX() - anchorPane.getLayoutX(), mouseEvent.getSceneY() - anchorPane.getLayoutY());
                }
                node.setStartX(line.getX1()+250);
                node.setStartY(250-line.getY1());
                node.setEndX(line.getX2()+250);
                node.setEndY(250-line.getY2());
                node.setVisible(true);

                double yOxRight = line.getK() * (250) + line.getB();
                double yOxLeft = line.getK() * (-250) + line.getB();
                if ((yOxRight <= -250) && line.getK() > 0) {
                      node.setVisible(false);
                } else if ((yOxRight >= 250) && (line.getK() < 0)) {
                    node.setVisible(false);
                } else if ((yOxLeft >= 250) && (line.getK() > 0)) {
                    node.setVisible(false);
                } else if ((yOxLeft <=-250)&&(line.getK() < 0)) {
                    node.setVisible(false);
                }


                xBeforeDrag = mouseEvent.getSceneX();
                yBeforeDrag = mouseEvent.getSceneY();
                pivotX = (node.getEndX()+node.getStartX())/2;
                pivotY = (node.getEndY()+node.getStartY())/2;

//                node.setLayoutX(mouseEvent.getSceneX() - delta.getX());
//                node.setLayoutY(mouseEvent.getSceneY() - delta.getY());getY
            }

        });
    }

}
