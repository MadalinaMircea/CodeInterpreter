package model.tableUtils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import model.statements.Statement;

import java.util.List;

public class ProcRecord {
    private SimpleStringProperty name, body;

    public ProcRecord(String name, List<String> params, Statement st) {
        String n = name + "(";
        for (String s : params)
            n = n + s + " ";
        n = n + ")";
        this.name = new SimpleStringProperty(n);
        body = new SimpleStringProperty(st.toString());
    }

    public String getBody() {
        return body.get();
    }

    public SimpleStringProperty bodyProperty() {
        return body;
    }

    public void setBody(String body) {
        this.body.set(body);
    }

    public String getName() {

        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
