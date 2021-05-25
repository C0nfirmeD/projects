import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CostElement {
    private SimpleIntegerProperty rooms = new SimpleIntegerProperty();
    private SimpleDoubleProperty average = new SimpleDoubleProperty();
    private int maxPrice;
    private int minPrice;

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public CostElement(int rooms, double average, int maxPrice, int minPrice) {
        setRooms(rooms);
        setAverage(average);
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;

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

    public double getAverage() {
        return average.get();
    }

    public SimpleDoubleProperty averageProperty() {
        return average;
    }

    public void setAverage(double average) {
        this.average.set(average);
    }


}
