package model.tableUtils;

import javafx.beans.property.SimpleIntegerProperty;

public class HeapRecord {
    private SimpleIntegerProperty address, value;
    public HeapRecord(int a, int v)
    {
        address = new SimpleIntegerProperty(a);
        value = new SimpleIntegerProperty(v);
    }

    public SimpleIntegerProperty addressProperty() {
        return address;
    }
    public SimpleIntegerProperty valueProperty()
    {
        return value;
    }
}
