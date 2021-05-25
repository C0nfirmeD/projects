import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RefactorDialogWindow {
    private Dialog<Resident> dialog;
    private TextField city = new TextField();
    private TextField street = new TextField();
    private TextField house = new TextField();
    private TextField apartment = new TextField();
    private TextField rooms = new TextField();
    private TextField price = new TextField();
    private GridPane root = new GridPane();
    private TableController table;
    private Resident resident;

    public RefactorDialogWindow(String title, TableController table) {
        dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        this.table = table;
        city.setText(table.getThisResident().getCity());
        street.setText(table.getThisResident().getStreet());
        house.setText(Integer.toString(table.getThisResident().getHouse()));
        apartment.setText(Integer.toString(table.getThisResident().getApartment()));
        rooms.setText(Integer.toString(table.getThisResident().getRooms()));
        price.setText(Integer.toString(table.getThisResident().getPrice()));


        createButtons();

        //root.getChildren().addAll(city, street, house, apartment, rooms, price);

        root.add(new Text("city:"), 0, 0);
        root.add(city, 1, 0);
        root.add(new Text("street:"), 0, 1);
        root.add(street, 1, 1);
        root.add(new Text("house:"), 0, 2);
        root.add(house, 1, 2);
        root.add(new Text("apartment:"), 0, 3);
        root.add(apartment, 1, 3);
        root.add(new Text("rooms:"), 0, 4);
        root.add(rooms, 1, 4);
        root.add(new Text("price:"), 0, 5);
        root.add(price, 1, 5);

        dialog.getDialogPane().setContent(root);
    }

    public Dialog<Resident> getDialog() {return dialog;}

    private void createButtons() {
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                resident = new Resident(city.getText(), street.getText(), Integer.parseInt(house.getText()), Integer.parseInt(apartment.getText()), Integer.parseInt(rooms.getText()), Integer.parseInt(price.getText()));
                handleOk();
                return resident;

            } else {
                return resident;
            }
        });
    }

    private void handleOk() {
        table.refactorThis(resident);
    }

}