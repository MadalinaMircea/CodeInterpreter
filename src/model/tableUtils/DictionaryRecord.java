package model.tableUtils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DictionaryRecord {
    private SimpleStringProperty varName;
    private SimpleIntegerProperty value;
    public DictionaryRecord(String n, Integer v)
    {
        varName = new SimpleStringProperty(n);
        value = new SimpleIntegerProperty(v);
    }
    public String getVarName()
    {
        return varName.get();
    }
    public int getValue()
    {
        return value.get();
    }

    public SimpleStringProperty varNameProperty() {
        return varName;
    }
    public SimpleIntegerProperty valueProperty()
    {
        return value;
    }
    public void setVarName(String varName) {
        this.varName.set(varName);
    }

    public void setValue(int value) {
        this.value.set(value);
    }
}
