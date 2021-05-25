import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
    private TaskController controller;
    private final BorderPane root = new BorderPane();
    private MenuBar menuBar;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Straight line graph");

        createMenuBar();


        root.setTop(menuBar);
        root.setCenter(new Label("select the type of task from the menu"));
        root.setBottom(getEndButton());

        Scene scene = new Scene(root, 706, 525);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    private void createMenuBar(){
        menuBar = new MenuBar();

        Menu toDecMenu = new Menu("_Decimal conversion");
        //toDecMenu.setOnAction((ActionEvent event) -> handleToDec());
        MenuItem go1 = new MenuItem("start test");
        toDecMenu.getItems().add(go1);
        go1.setOnAction((ActionEvent event) -> handleToDec());
        menuBar.getMenus().add(toDecMenu);


        Menu toAnyMenu = new Menu("_Any system conversion");
        //toAnyMenu.setOnAction((ActionEvent event) -> handleToAny());
        MenuItem go2 = new MenuItem("start test");
        toAnyMenu.getItems().add(go2);
        go2.setOnAction((ActionEvent event) -> handleToAny());
        menuBar.getMenus().add(toAnyMenu);


        Menu addSubMenu = new Menu("_Addition subtraction");
        //addSubMenu.setOnAction((ActionEvent event) -> handleAddSub());
        MenuItem go3 = new MenuItem("start test");
        addSubMenu.getItems().add(go3);
        go3.setOnAction((ActionEvent event) -> handleAddSub());
        menuBar.getMenus().add(addSubMenu);

    }

    private Button getEndButton(){
        Button endB = new Button("Result");
        endB.setOnAction(event -> showStats());
        return endB;
    }

    private void handleToDec() {
        controller = new TaskController(1);
        controller.createTaskList();
        root.setCenter(controller.getScrollPane());

    }

    private void handleToAny() {
        controller = new TaskController(2);
        controller.createTaskList();
        root.setCenter(controller.getScrollPane());
    }
    private void handleAddSub() {
        controller = new TaskController(3);
        controller.createTaskList();
        root.setCenter(controller.getScrollPane());
    }

    public void showStats(){
        root.setCenter(controller.stats());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
