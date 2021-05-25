import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Resident {
    private SimpleStringProperty city;
    private SimpleStringProperty street;
    private SimpleIntegerProperty house;
    private SimpleIntegerProperty apartment;
    private SimpleIntegerProperty rooms;
    private SimpleIntegerProperty price;

    Resident(String city, String street, int house, int apartment, int rooms, int price){
        this.city = new SimpleStringProperty(city);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleIntegerProperty(house);
        this.apartment = new SimpleIntegerProperty(apartment);
        this.rooms = new SimpleIntegerProperty(rooms);
        this.price = new SimpleIntegerProperty(price);
    }

    public void setResident(Resident resident){
        setCity(resident.getCity());
        setStreet(resident.getStreet());
        setHouse(resident.getHouse());
        setApartment(resident.getApartment());
        setRooms(resident.getRooms());
        setPrice(resident.getPrice());
    }

    public String getCity() {
        return city.get();
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getStreet() {
        return street.get();
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public int getHouse() {
        return house.get();
    }

    public SimpleIntegerProperty houseProperty() {
        return house;
    }

    public void setHouse(int house) {
        this.house.set(house);
    }

    public int getApartment() {
        return apartment.get();
    }

    public SimpleIntegerProperty apartmentProperty() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment.set(apartment);
    }

    public int getRooms() {
        return rooms.get();
    }

    public SimpleIntegerProperty roomsProperty() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms.set(rooms);
    }

    public int getPrice() {
        return price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    @Override
    public String toString() {
        return "Resident{" +
                "city=" + city +
                ", street=" + street +
                ", house=" + house +
                ", apartment=" + apartment +
                ", rooms=" + rooms +
                ", price=" + price +
                '}';
    }
}
