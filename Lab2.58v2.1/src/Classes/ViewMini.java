package Classes;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.time.LocalDate;

public class ViewMini {

    private LineEquation line;
    private AnchorPane pane;
   // private Label id;
    private ObservableList<LineEquation> lines = FXCollections.<LineEquation>observableArrayList();
    private Label equation;
    private TableView<LineEquation> dataTableView;


//    public void setLines(ObservableList lines){
//        lines = FXCollections.<LineEquation>observableArrayList();
//        this.lines = lines;
//    }

    public ObservableList<LineEquation> getLines(){
        return lines;
    }

    public void addLineToList(LineEquation line){
        lines.add(line);
    }

    private void createTableView() {

        //TableColumn<LineEquation, String> idCol = new TableColumn<>("Id");
        //idCol.setCellValueFactory(new PropertyValueFactory("id"));
        //idCol.setMinWidth(50);

        TableColumn<LineEquation, String> equationCol = new TableColumn<>("Straight line equation");
        equationCol.setCellValueFactory(new PropertyValueFactory("equation"));
        equationCol.setMinWidth(200);
        equationCol.setMaxWidth(200);

        dataTableView.getColumns().setAll(equationCol);
    }

    private void createPane() {
        pane = new AnchorPane();
        //pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(25, 25, 25, 25));

        equation = new Label();
        equation.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        //equation.setMinWidth(100);
        //equation.setAlignment(Pos.TOP_CENTER);
        equation.setLayoutY(50);
        equation.setLayoutX(20);

        //id = new Label();
       //id.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        pane.getChildren().addAll(equation);

        dataTableView = new TableView<>();

        createTableView();
        dataTableView.setItems(lines);
       // dataTableView.setLayoutY(150);
        //dataTableView.setLayoutX(20);
        //dataTableView.setMaxWidth(150);
        dataTableView.setMinWidth(150);


        pane.getChildren().add(dataTableView);

        AnchorPane.setTopAnchor(dataTableView, 100.0);
        //AnchorPane.setLeftAnchor(dataTableView, 10.0);
       //AnchorPane.setRightAnchor(dataTableView, 5.6);
    }

    private  void addListenersOrg(){
        equation.textProperty().bind(line.equationProperty());
        //equation.textProperty().bind(graph.selectedEquationProperty());
       // id.textProperty().bind(line.idProperty());
    }

    public void setElement(LineEquation line) {
        this.line = line;
        addListenersOrg();
    }

    public AnchorPane getPane(){
        return pane;
    }

    public ViewMini(LineEquation line) {
        if (line == null){
            createPane();

        }
        else {
            this.line = line;

            createPane();
            addListenersOrg();
        }
    }
}
