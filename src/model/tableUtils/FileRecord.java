package model.tableUtils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileRecord {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    public FileRecord(Integer i, String n)
    {
        id = new SimpleIntegerProperty(i);
        name = new SimpleStringProperty(n);
    }
    public int geId()
    {
        return id.get();
    }
    public String getName()
    {
        return name.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }
    public SimpleStringProperty nameProperty()
    {
        return name;
    }
}
