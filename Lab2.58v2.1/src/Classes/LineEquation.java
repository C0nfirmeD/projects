package Classes;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.paint.Color;


public class LineEquation extends Node {
    private DoubleProperty k, b;
    private StringProperty equation;
    private BooleanProperty isSelected;
   // private ObjectProperty<Color> color;
    private double x1, y1, x2, y2;

    public boolean isIsSelected() {
        return isSelectedProperty().get();
    }

    public BooleanProperty isSelectedProperty() {
        if ( isSelected == null) {
            isSelected = new SimpleBooleanProperty();
        }
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        isSelectedProperty().set(isSelected);
    }

//    public Color getColor() {
//        return colorProperty().get();
//    }
//
//    public ObjectProperty<Color> colorProperty() {
//        if ( color == null) {
//            color = new SimpleObjectProperty<>();
//        }
//        return color;
//    }
//
//    public void setColor(Color color) {
//        colorProperty().set(color);
//    }

    public StringProperty equationProperty(){
        if (equation == null){
            equation = new SimpleStringProperty();
        }
        return equation;
    }

    public void setEquation(){
        String equation;
        if(getK() == 1E16) equation = "x = "+getB() ;
        else {
            equation = String.format("y = %fx + %f", getK(), b.get()/25);
        }
        equationProperty().set(equation);
    }

    public String getEquation(){
        return equationProperty().get();
    }

    public DoubleProperty kProperty(){
        if (k == null){
            k = new SimpleDoubleProperty();
        }
        return k;
    }
    public void setK(double k){
        kProperty().set(k);
    }
    public double getK(){
        return kProperty().get();
    }

    public DoubleProperty bProperty(){
        if (b == null) {
            b = new SimpleDoubleProperty();
        }
        return b;
    }
    public void setB(double b){
        bProperty().set(b);
    }
    public double getB(){
        return bProperty().get();
    }

    public LineEquation(double x1, double y1, double x2, double y2){
        if ((x2-x1) == 0){
            kProperty().set(1E16);
            bProperty().set(x1); //вместо б - абцисса прямой
        }
        else {
            kProperty().set(-(y2-y1)/(x2-x1));
            bProperty().set((250 - y1)-getK()*(-250 + x1));   //в новой системе отсчета
            System.out.println(getB());
        }
        setEquation();
        calcCoordinates();
    }

    public void editLine(double x1, double y1, double x2, double y2){
        if ((x2-x1) == 0){
            kProperty().set(1E16);
            bProperty().set(x1); //вместо б - абцисса прямой
        }
        else {
            kProperty().set(-(y2-y1)/(x2-x1));
            bProperty().set((250 - y1)-getK()*(-250 + x1));   //в новой системе отсчета
            System.out.println(getB());
        }
        setEquation();
        calcCoordinates();
    }

    public void calcCoordinates() {
        double k = this.getK();
        double b = this.getB();
        System.out.println(this.getK());
        // x1 , y1 , x2 , y2 ;     //в новой системе отсчета

        double yOxLeft = k * (-250) + b;
        double yOxRight = k * (250) + b;
        double xOyBot = (-250 - b) / k;
        double xOyTop = (250 - b) / k;


        if (Math.abs(yOxLeft) < 250) {
            x1 = -250;
            y1 = yOxLeft;
        } else if ((yOxLeft <= -250) && ( k > 0)) {
            y1 = -250;
            x1 = xOyBot;
        } else if ((yOxLeft >= 250)&&(k < 0)) {
            y1 = 250;
            x1 = xOyTop;
        }
        if (Math.abs(yOxRight) < 250) {
            x2 = 250;
            y2 = yOxRight;
        } else if ((yOxRight <= -250) && (k < 0)) {
            y2 = -250;
            x2 = xOyBot;
        } else if ((yOxRight >= 250)&&(k > 0)) {
            y2 = 250;
            x2 = xOyTop;
        }

        if (((yOxRight <= -250) && (k > 0))||((yOxRight >= 250) && (k < 0))||((yOxLeft >= 250)&&(k > 0))||((yOxLeft <=-250)&&(k<0))) {
            x2 = 250;
            y2 = yOxRight;
            x1 = -250;
            y1 = yOxLeft;
        }
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }
}
