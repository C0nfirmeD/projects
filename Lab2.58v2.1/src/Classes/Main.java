package Classes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.IOException;


public class Main extends Application {
    private MenuBar menuBar;
    private ViewMini viewMini;
    private GraphController graph;
    //private ObservableList<LineEquation> lines;


    private void createFileMenu() {
        Menu fileMenu = new Menu("_File");


        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(new SeparatorMenuItem(), exit);
        exit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        menuBar.getMenus().add(fileMenu);
    }

    private void createEditMenu(){
        Menu editMenu = new Menu("_Edit");
        MenuItem refactor = new MenuItem("Refactor this");
        MenuItem add = new MenuItem("Add");
        MenuItem delete = new MenuItem("Delete this");
        editMenu.getItems().addAll(refactor, add, delete);

        refactor.setOnAction((ActionEvent event) -> handleRefactor());
        add.setOnAction((ActionEvent event) ->handleAdd());
        delete.setOnAction((ActionEvent event) ->handleDelete());
        menuBar.getMenus().add(editMenu);
    }


    private void createOptionsMenu(){
        Menu optionsMenu = new Menu("_Options");
        MenuItem parallel = new MenuItem("Build parallel");
        MenuItem perpendicular = new MenuItem("Build Perpendicular");
        //MenuItem delete = new MenuItem("Delete this");
        optionsMenu.getItems().addAll(parallel, perpendicular);

        parallel.setOnAction((ActionEvent event) -> handleParallel());
        perpendicular.setOnAction((ActionEvent event) ->handlePerpendicular());
        //delete.setOnAction((ActionEvent event) ->handleDelete());
        menuBar.getMenus().add(optionsMenu);
    }

    private void handlePerpendicular() {
        LineEquation line = graph.getThisLine();

        if (line != null) {
            DialogGraphWindow dialog = new DialogGraphWindow(graph.getLines(), "Edit line", graph);
            dialog.paintGhostLines();

            dialog.createPerpendicularLine(line);
            //dialog.addEditableLine(line, graph.getSelectedLine());
            dialog.getDialog().showAndWait();
        } else {
            showMessage("No selected item!");
        }

    }

    private void handleParallel() {
            LineEquation line = graph.getThisLine();

            if (line != null) {
                DialogGraphWindow dialog = new DialogGraphWindow(graph.getLines(), "Edit line", graph);
                dialog.paintGhostLines();

                dialog.createParallelLine(line);

                dialog.getDialog().showAndWait();
            } else {
                showMessage("No selected item!");
            }


    }


    private void handleDelete() {
        graph.removeSelectedElement();
        System.out.println(graph.getLines());
    }

    private void handleAdd() {

        DialogGraphWindow dialog = new DialogGraphWindow(graph.getLines(), "Edit line", graph);
        dialog.paintGhostLines();
        dialog.createNewLine();
//        dialog.addEditableLine(line, graph.getSelectedLine());
        dialog.getDialog().show();
        showInform(dialog.getDialog().getX() -  100, dialog.getDialog().getY() + 50, "" +
                "Что бы добавить прямую к списку укажите" +
                " две точки, через которые она проходит двойным щелчком");
        //graph.addElement(new LineEquation(10,10, 100,200));

    }

    private void handleRefactor() {
        LineEquation line = graph.getThisLine();

        if (line != null) {
            DialogGraphWindow dialog = new DialogGraphWindow(graph.getLines(), "Edit line", graph);
            dialog.paintGhostLines();
            dialog.addEditableLine(line, graph.getSelectedLine());
            dialog.getDialog().showAndWait();
        } else {
            showMessage("No selected item!");
        }
    }



    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Data file error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public  static void showInform(double x, double y, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Documentation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setX(x+150);
        alert.setY(y + 150);
        alert.showAndWait();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Straight line graph");
        BorderPane root = new BorderPane();
        menuBar = new MenuBar();
        createFileMenu();
        createEditMenu();
        createOptionsMenu();
        //createViewMenu();
        root.setTop(menuBar);

        viewMini = new ViewMini(null);
        graph = new GraphController(viewMini);
        graph.createAnchorPane();

//        LineEquation l1 = new LineEquation(50,500,500,50);
//        LineEquation l2 = new LineEquation(0,0,100,100);
//        LineEquation l3 = new LineEquation(250,250,500,50);
//        graph.addElement(l1);
//        graph.addElement(l2);
//        graph.addElement(l3);

        root.setLeft(graph.getAnchorPane());

        root.setRight(viewMini.getPane());


        Scene scene = new Scene(root, 706, 525);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        showInform(primaryStage.getX(), primaryStage.getY(), "" +
                "Программа отображает данные о прямых, задаваемых уравнением " +
                "вида y = kx + b. Прямые можно редактировать " +
                "путем ПЕРЕТАСКИВАНИЯ (используйте для этого ЛКМ) " +
                "и ПОВОРОТА (используйте ПКМ)" +
                "");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
