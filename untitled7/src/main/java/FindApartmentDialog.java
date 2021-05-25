import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class FindApartmentDialog {
    private Dialog<Resident> dialog;
    private TableController table;
    private TableController tableCity = new TableController();
    private VBox root = new VBox();
    private Resident resident;
    private List<String> lst1; //список городов
    private ArrayList<Resident> arrayCity; //список предложений из одного города
    private List<Integer> lst2; //список квартир
    private String selectedCity;
    private Stage stage = new Stage();

    private boolean flag = false;

    private ChoiceBox choiceBoxCity = new ChoiceBox();
    private ChoiceBox choiceBox = new ChoiceBox();
    private Text maxText;
    private Text minText;


    private TableView averageCost;
    private ObservableList<CostElement> obsList1 = FXCollections.<CostElement>observableArrayList();
    private int selectedRooms;

    public FindApartmentDialog(String title, TableController table) {
        stage.setTitle(title);
        this.table = table;

        ArrayList<String> cities = new ArrayList<>();
        table.getList().stream().forEach(resident -> cities.add(resident.getCity()));
        lst1 = cities.stream().distinct().collect(Collectors.toList());


        choiceBoxCity.getItems().addAll(lst1);
        choiceBoxCity.setValue(lst1.get(0));

        Button showByCityButton = new Button("show");
        showByCityButton.setOnAction(this::handle);

        HBox hBox = new HBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();

        hBox.getChildren().add(choiceBoxCity);
        hBox.getChildren().add(showByCityButton);


        tableCity = new TableController();
        tableCity.createTable(new ArrayList());
        tableCity.setResidents(table.getList());
        averageCost = getAverageCost();

        maxText = new Text();
        minText = new Text();

        hBox2.getChildren().add(maxText);
        hBox2.getChildren().add(minText);



        hBox1.getChildren().add(tableCity.getTable());
        hBox1.getChildren().add(averageCost);

        root.getChildren().add(hBox);
        root.getChildren().add(hBox1);
        root.getChildren().add(hBox2);

        Scene scene = new Scene(root, 706, 525);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();




//        dialog = new Dialog<>();
//        dialog.setTitle(title);
//        dialog.setHeaderText(null);
//        this.table = table;
//
//        ArrayList<String> cities = new ArrayList<>();
//        table.getResidents().stream().forEach(resident -> cities.add(resident.getCity()));
//        lst1 = cities.stream().distinct().collect(Collectors.toList());
//
//        choiceBox.getItems().addAll(lst1);
//
//        showByCity = new Button("show");
//        showByCity.setOnAction(this::handle);
//
//
//
//
//
//        createButtons();
//
//        //root.add(tableCity.getTable(), 0,1);
//        root.add(choiceBox, 0, 0);
//        root.add(showByCity,1,0);
//        dialog.getDialogPane().setContent(root);
    }

    private void handle(javafx.event.ActionEvent actionEvent) {
        System.err.println("sdfsdfsd");
        selectedCity = choiceBoxCity.getValue().toString();
        System.out.println(selectedCity);
        showCity();
        ArrayList<Integer> rooms = new ArrayList<>();
        arrayCity.stream().forEach(resident -> rooms.add(resident.getRooms()));
        lst2 = rooms.stream().distinct().collect(Collectors.toList());
        averageCost = getAverageCost();
    }

    private void showCity(){
        List<Resident> byCity = table.getList().stream().filter(o -> o.getCity().equals(selectedCity)).collect(Collectors.toList());
        arrayCity = new ArrayList<>(byCity);

        tableCity.setResidents(arrayCity);

        System.out.println(arrayCity.toString());
        //root.getChildren().add(tableCity.getTable());
    }

    private TableView getAverageCost(){


        ArrayList<CostElement> costList = new ArrayList<>();

        if (flag){
            System.out.println(lst2);
            lst2.stream().forEach(rooms -> {
                long count = arrayCity.stream().filter(resident1 -> resident1.getRooms() == rooms.intValue()).count();
//                int maxPrice = 0, minPrice = 1000000;
//                arrayCity.stream().filter(resident1 -> resident1.getRooms() == rooms.intValue()).forEach(p -> {
//                    if(p.getPrice()>maxPrice) maxPrice = p.getPrice();
//                    if(p.getPrice()<minPrice) minPrice = p.getPrice();
//                });
                OptionalInt maxPrice = arrayCity.stream().filter(resident1 -> resident1.getRooms() == rooms.intValue()).mapToInt(Resident::getPrice).max();
                OptionalInt minPrice = arrayCity.stream().filter(resident1 -> resident1.getRooms() == rooms.intValue()).mapToInt(Resident::getPrice).min();

                System.out.println(maxPrice.getAsInt() +"  "+ minPrice.getAsInt());


                int sum = arrayCity.stream().filter(resident1 -> resident1.getRooms() == rooms.intValue()).mapToInt(Resident::getPrice).sum();
                costList.add(new CostElement(rooms.intValue(), (sum/count), maxPrice.getAsInt(), minPrice.getAsInt()));
            });
            obsList1.clear();
            obsList1.addAll(costList);
        }


       if (!flag){
           averageCost = new TableView<CostElement>(obsList1);
//        table.setPrefWidth(250);
//        table.setPrefHeight(200);
//
           TableColumn<CostElement, String> roomsColumn = new TableColumn<>("Rooms");
           // определяем фабрику для столбца с привязкой к свойству name
           roomsColumn.setCellValueFactory(new PropertyValueFactory<>("rooms"));
           // добавляем столбец

           TableColumn<CostElement, String> averageColumn = new TableColumn<>("Average cost");
           averageColumn.setCellValueFactory(new PropertyValueFactory<>("average"));

           TableView.TableViewSelectionModel<CostElement> selectionModel = averageCost.getSelectionModel();
           selectionModel.selectedItemProperty().addListener(new ChangeListener<CostElement>(){

               @Override
               public void changed(ObservableValue<? extends CostElement> observable, CostElement oldValue, CostElement newValue) {
                   if(newValue != null) selectedRooms = newValue.getRooms();
                   maxText.setText("Max:  " + String.valueOf(newValue.getMaxPrice()) + "   ");
                   minText.setText("Min:  " + String.valueOf(newValue.getMinPrice()) + "   ");
               }

           });

           averageCost.getColumns().addAll(roomsColumn, averageColumn);
           flag = true;
       }


        return averageCost;
    }

//    public Dialog<Resident> getDialog() {return dialog;}
//
//    private void createButtons() {
//        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
//        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
//        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
//        dialog.setResultConverter((ButtonType b) -> {
//            if (b == buttonTypeOk) {
//                handleOk();
//                return resident;
//
//            } else {
//                return resident;
//            }
//        });
//    }
//
//    private void handleOk() {
//        //table.addElement(resident);
//    }

}
