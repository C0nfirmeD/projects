import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.FileHandler;

public class TableController {
    private TableView<Resident> table = new TableView();
    private Resident thisResident;
    private ObservableList<Resident> residents;


    public TableController() {
    }

    public TableView getTable() {
        return table;
    }

    public Resident getThisResident() {
        return thisResident;
    }

    public ObservableList<Resident> getList(){
        return residents;
    }

    public void setResidents(ObservableList<Resident> residents) {
        this.residents.clear();
        this.residents.addAll(residents);
    }
    public void setResidents(ArrayList<Resident> residents) {
        this.residents.clear();
        this.residents.addAll(residents);
    }

    public void createTable(ArrayList list) {
        residents = FXCollections.<Resident>observableArrayList();
        residents.addAll(list);
        table = new TableView<Resident>(residents);
        table.setPrefWidth(450);
        table.setPrefHeight(100);

        TableColumn<Resident, String> cityColumn = new TableColumn<>("City");
        // определяем фабрику для столбца с привязкой к свойству name
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        // добавляем столбец

        TableColumn<Resident, String> streetColumn = new TableColumn<>("Street");
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));

        TableColumn<Resident, Integer> houseColumn = new TableColumn<>("House");
        houseColumn.setCellValueFactory(new PropertyValueFactory<>("house"));

        TableColumn<Resident, Integer> apartmentColumn = new TableColumn<>("Apartment");
        apartmentColumn.setCellValueFactory(new PropertyValueFactory<>("apartment"));

        TableColumn<Resident, Integer> roomsColumn = new TableColumn<>("Rooms");
        roomsColumn.setCellValueFactory(new PropertyValueFactory<>("rooms"));

        TableColumn<Resident, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(cityColumn, streetColumn, houseColumn, apartmentColumn, roomsColumn, priceColumn);

        TableView.TableViewSelectionModel<Resident> selectionModel = table.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new ChangeListener<Resident>(){

            @Override
            public void changed(ObservableValue<? extends Resident> observable, Resident oldValue, Resident newValue) {
                if(newValue != null) thisResident = newValue;
            }

        });
    }

    public void deleteElement(){
        residents.remove(thisResident);
    }

    public void addElement(Resident resident){
        residents.add(resident);
    }

    public void refactorThis(Resident resident){
        residents.get(residents.indexOf(thisResident)).setResident(resident);





    }


}
