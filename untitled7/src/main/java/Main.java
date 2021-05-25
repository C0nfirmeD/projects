import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    private MenuBar menuBar;
    private TableController table;
    private Resident resident;
    private ArrayList<Resident> arrayRes = new ArrayList<>();


    private void createFileMenu() {
        Menu fileMenu = new Menu("_File");


        MenuItem save = new MenuItem("Save");
        MenuItem exit = new MenuItem("Exit");
        fileMenu.getItems().addAll(save, new SeparatorMenuItem(), exit);
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
        MenuItem findResident = new MenuItem("Find an apartment");
        //MenuItem byCity = new MenuItem("List by city");
        //MenuItem byRooms = new MenuItem("List by rooms");

        optionsMenu.getItems().addAll(findResident);

        findResident.setOnAction((ActionEvent event) -> handleFindResident());
        //byCity.setOnAction((ActionEvent event) -> handleByCity());
        //byRooms.setOnAction((ActionEvent event) ->handleByRooms());
        menuBar.getMenus().add(optionsMenu);

    }

    private void handleFindResident() {
        FindApartmentDialog dialog = new FindApartmentDialog("Find an apartment", table);
    }

    private void handleByRooms() {

    }

    private void handleByCity() {
        FindApartmentDialog dialog = new FindApartmentDialog("option", table);
        //dialog.getDialog().showAndWait();
    }

    private void handleDelete() {
        table.deleteElement();
        //System.out.println(graph.getLines());
    }
//
    private void handleAdd() {

        AddDialogWindow dialog = new AddDialogWindow("Add Apartment", table);
        dialog.getDialog().showAndWait();


    }
//
    private void handleRefactor() {
        resident = table.getThisResident();


        if (resident != null) {
            RefactorDialogWindow dialog = new RefactorDialogWindow("Edit Apartment", table);
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

    public void readFile() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();

            File file = new File(classLoader.getResource("apartments.txt").getFile());
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);

            String str = br.readLine();
            System.out.println(str);

            while (str != null) {
                String[] buf = str.split(" ");
                Resident resident = new Resident(buf[0], buf[1], Integer.parseInt(buf[2]), Integer.parseInt(buf[3]), Integer.parseInt(buf[4]), Integer.parseInt(buf[5]));
                arrayRes.add(resident);
                str = br.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Straight line graph");
        BorderPane root = new BorderPane();
        menuBar = new MenuBar();
        table = new TableController();

        createFileMenu();
        createEditMenu();
        createOptionsMenu();
        readFile();
        table.createTable(arrayRes);
        //createEditMenu();
        //createOptionsMenu();
        //createViewMenu();
        root.setTop(menuBar);
        root.setCenter(table.getTable());

        Scene scene = new Scene(root, 706, 525);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
