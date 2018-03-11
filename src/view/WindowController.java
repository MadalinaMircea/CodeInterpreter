package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.FileData;
import model.expressions.*;
import model.prgstate.PrgState;
import model.statements.*;
import model.utils.*;
import org.omg.SendingContext.RunTime;
import repository.IPrgStateRepository;
import repository.PrgStateRepository;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.application.Platform.exit;


public class WindowController {
    public static Statement chosen;
    List<Statement> states = new ArrayList<Statement>();
    @FXML private ListView<String> prgStatesList = new ListView<String>();
    @FXML private Button runButton = new Button();
    @FXML
    void initialize() {
        addStates();
        ObservableList<String> stateList = FXCollections.observableArrayList();
        for(Statement p : states)
            stateList.add(p.toString());
        prgStatesList.setItems(stateList);
        runButton.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
                        try {
                            int i = prgStatesList.getSelectionModel().getSelectedIndex();
                            if(i < 0 || i > states.size())
                                throw new RuntimeException("Invalid index.");
                            else
                                chosen = states.get(i);
                            StackPane root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                            Scene scene = new Scene(root,907,597);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Interpreter");
                            stage.show();
                        } catch(Exception e) {
                            System.out.println(e.getMessage());
                        }	            }
		        });
    }

    private void addStates()
    {

        Statement ex1 = new CompoundStatement(new AssignStatement("a", new ArithmeticExpression('-', new ConstantExpression(2),
                new ConstantExpression(2))), new CompoundStatement(new IfStatement(new AssignStatement("v",
                new ConstantExpression(2)), new AssignStatement("v", new ConstantExpression(3)),
                new VariableExpression("a")), new PrintStatement(new VariableExpression("v"))));
        states.add(ex1);

/*
        Statement ex2 = new CompoundStatement(new CompoundStatement(new AssignStatement("b", new ConstantExpression(3)),
                new AssignStatement("a", new ArithmeticExpression('+', new VariableExpression("b"),
                        new ConstantExpression(5)))), new CompoundStatement(new PrintStatement(new VariableExpression("a")),
                new PrintStatement(new VariableExpression("b"))));
        states.add(ex2);

        Statement ex3 = new CompoundStatement(new CompoundStatement(new OpenRFileStatement("var_f", "test.in"),
                new CompoundStatement(new ReadFileStatement(new VariableExpression("var_f"), "var_c"),
                        new PrintStatement(new VariableExpression("var_c")))),
                new CompoundStatement(new IfStatement(new CompoundStatement(new ReadFileStatement(
                        new VariableExpression("var_f"), "var_c"), new PrintStatement(new VariableExpression("var_c"))),
                        new PrintStatement(new ConstantExpression(0)), new VariableExpression("var_c")),
                        new CloseRFile(new VariableExpression("var_f"))));
        states.add(ex3);

        Statement ex4 = new CompoundStatement(new CompoundStatement(new AssignStatement("v", new ConstantExpression(10)),
                new NewHStatement("v",new ConstantExpression(20))), new CompoundStatement(new NewHStatement("a",
                new ConstantExpression(22)), new PrintStatement(new VariableExpression("v"))));
        states.add(ex4);

        //{{{v=10;new(v,20);}{new(a,22);wH(a,30);}}{{print(a);print(rH(a));}a=0}}
        Statement ex5 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new AssignStatement("v",
                new ConstantExpression(10)), new NewHStatement("v", new ConstantExpression(20))),
                new CompoundStatement(new NewHStatement("a", new ConstantExpression(22)), new WriteHStatement("a",
                        new ConstantExpression(30)))), new CompoundStatement(new CompoundStatement(new PrintStatement(
                new VariableExpression("a")), new PrintStatement(new ReadHExpression("a"))),
                new AssignStatement("a", new ConstantExpression(0))));
        states.add(ex5);

        Statement ex6 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new AssignStatement("v", new ConstantExpression(10)),
                new NewHStatement("a", new ConstantExpression(22))), new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHStatement(
                "a", new ConstantExpression(30)), new PrintStatement(new VariableExpression("v")))), new AssignStatement("v", new ConstantExpression(32)))),
                new CompoundStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new ReadHExpression("a"))), new CompoundStatement(new PrintStatement(
                        new VariableExpression("v")), new PrintStatement(new ReadHExpression("a")))));
        states.add(ex6);
*/

        //procedure sum(a,b) v=a+b;print(v)
        Statement s1 = new ProcedureStatement("sum", Arrays.asList("a","b"),new CompoundStatement(new AssignStatement(
                "v", new ArithmeticExpression('+', new VariableExpression("a"), new VariableExpression("b"))), new PrintStatement(new
                VariableExpression("v"))));
        //procedure product(a,b) v=a*b;print(v)
        Statement s2 = new ProcedureStatement("product", Arrays.asList("a","b"),new CompoundStatement(new AssignStatement(
                "v", new ArithmeticExpression('*', new VariableExpression("a"), new VariableExpression("b"))), new PrintStatement(new
                VariableExpression("v"))));
        //v=2;w=5;call sum(v*10,w);print(v);
        Statement s3 = new CompoundStatement(new CompoundStatement(new CompoundStatement(new AssignStatement("v", new ConstantExpression(2)),
                new AssignStatement("w", new ConstantExpression(5))), new CallStatement("sum", Arrays.asList(new ArithmeticExpression('*',
                new VariableExpression("v"), new ConstantExpression(10)), new VariableExpression("w")))), new PrintStatement(new VariableExpression("v")));

        //fork(call sum(v,w))
        Statement s4 = new ForkStatement(new CallStatement("sum", Arrays.asList(new VariableExpression("v"), new VariableExpression("w"))));
        //fork(call product(v,w);fork(call sum(v,w)))
        Statement s5 = new ForkStatement(new CompoundStatement(new CallStatement("product", Arrays.asList(new VariableExpression("v"),
                new VariableExpression("w"))), s4));

        Statement ex7 = new CompoundStatement(new CompoundStatement(new CompoundStatement(s1, s2), s3), s5);
        states.add(ex7);
    }
}
