package src.ui;

import javafx.beans.property.SimpleStringProperty;

public class EventRow {
    private final SimpleStringProperty ownerName;
    private final SimpleStringProperty start;
    private final SimpleStringProperty title;

    public EventRow(String ownerName, String start, String title) {
        this.ownerName = new SimpleStringProperty(ownerName);
        this.start = new SimpleStringProperty(start);
        this.title = new SimpleStringProperty(title);
    }

    public String getOwnerName() {
        return ownerName.get();
    }

    public SimpleStringProperty ownerNameProperty() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName.set(ownerName);
    }

    public String getStart() {
        return start.get();
    }

    public SimpleStringProperty startProperty() {
        return start;
    }

    public void setStart(String start) {
        this.start.set(start);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}
