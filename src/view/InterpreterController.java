package view;

import controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import model.FileData;
import model.prgstate.PrgState;
import model.statements.*;
import model.tableUtils.DictionaryRecord;
import model.tableUtils.FileRecord;
import model.tableUtils.HeapRecord;
import model.tableUtils.ProcRecord;
import model.utils.*;
import repository.IPrgStateRepository;
import repository.PrgStateRepository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class InterpreterController {
    private ExecutorService es = Executors.newFixedThreadPool(2);
    private Controller ctrl;
    @FXML private ListView<String> prgStatesList = new ListView<>();
    @FXML private TableView<HeapRecord> heapView = new TableView<>();
    @FXML private TableColumn<HeapRecord, Integer> heapViewAddress = new TableColumn<>();
    @FXML private TableColumn<HeapRecord, Integer> heapViewValue = new TableColumn<>();
    @FXML private ListView<String> outView = new ListView<>();
    @FXML private TableView fileView = new TableView();
    @FXML private TableColumn<DictionaryRecord, String> fileViewName = new TableColumn<>();
    @FXML private TableColumn<DictionaryRecord, Integer> fileViewId = new TableColumn<>();
    @FXML private TableView<DictionaryRecord> symbolView = new TableView<>();
    @FXML private TableColumn<DictionaryRecord, String> symbolViewName = new TableColumn<>();
    @FXML private TableColumn<DictionaryRecord, Integer> symbolViewValue = new TableColumn<>();
    @FXML private TableView<ProcRecord> procTable = new TableView<>();
    @FXML private TableColumn<ProcRecord, String> procTableName = new TableColumn<>();
    @FXML private TableColumn<ProcRecord, Integer> procTableBody = new TableColumn<>();
    @FXML private ListView<String> stackView = new ListView<>();
    @FXML private TextField numberField = new TextField();
    @FXML private Button runButton = new Button();
    @FXML
    void initialize() {
        Statement s = WindowController.chosen;
        IExecStack<Statement> execStack = new ExecStack<>();
        execStack.push(s);
        IDictionary<String, Integer> dict = new Dictionary<>();
        IExecStack<IDictionary<String,Integer>> dictStack = new ExecStack<>();
        dictStack.push(dict);
        IList<Integer> out = new OutputList<>();
        IFileTable<Integer, FileData> file = new FileTable<>();
        IHeap<Integer, Integer> heap = new Heap<>();
        IProcTable<String, Pair<List<String>,Statement>> procTable = new ProcTable<>();
        PrgState p = new PrgState(execStack, dictStack, out, s, file, heap, procTable);
        IPrgStateRepository repo = new PrgStateRepository("log.txt");
        repo.addPrgState(p);
        ctrl = new Controller(repo);
        initializePrgStatesList();
        runButton.setOnAction(new EventHandler<ActionEvent>() {

		            @Override
		            public void handle(ActionEvent event) {
		                handleButton();
		            }
		        });
        prgStatesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String oldV, String newV) {
                initializeViews();
            }
            });
    }

    private void handleButton()
    {
        try
        {
            if(ctrl.getNrStates() == 0)
            {
                es.shutdown();
                throw new RuntimeException("No more steps to execute.");
            }
            List<PrgState> prgList = ctrl.getAll();
            List<Callable<PrgState>> callList = prgList.stream()
                    .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.executeOneStep();}))
                    .collect(Collectors.toList());

            List<PrgState> newPrgList = es.invokeAll(callList).stream()
                    .map(future -> { try { return future.get();}
                    catch(Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }}).filter(p -> p!=null)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            ctrl.setPrograms(prgList);
            initializePrgStatesList();
            initializeViews();
            prgList = ctrl.removeCompletedPrg();
            ctrl.setPrograms(prgList);
            ctrl.logToFile();
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            //e.printStackTrace();
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }

    }

    private void initializePrgStatesList()
    {
        int nr = ctrl.getNrStates();
        numberField.setText("" +  nr + " Program States");

        if(nr > 0)
        {
            ObservableList<String> prgStates = FXCollections.observableArrayList();
            for(int i = 0; i < nr; i++)
                prgStates.add("" + ctrl.get(i).getId());

            prgStatesList.setItems(prgStates);
        }
    }

    private void initializeViews()
    {
        if(ctrl.getNrStates() != 0)
        {
            String str = prgStatesList.getSelectionModel().getSelectedItem();
            PrgState p;
            if(str != null)
            {
                p = ctrl.getById(Integer.parseInt(str));
                if(p != null) {

                    ObservableList<String> execStack = FXCollections.observableArrayList();
                    for (Statement s : p.getExecStack().getAll())
                        execStack.add(s.toString());
                    stackView.setItems(execStack);


                    ObservableList<String> outList = FXCollections.observableArrayList();
                    for (int j : p.getOutputList().getAll())
                        outList.add("" + j);
                    outView.setItems(outList);


                    ObservableList<DictionaryRecord> symbolList = FXCollections.observableArrayList();
                    for (String s : p.getSymbolTable().getAll())
                        symbolList.add(new DictionaryRecord(s, p.getSymbolTable().get(s)));
                    symbolViewName.setCellValueFactory(new PropertyValueFactory<>("varName"));
                    symbolViewValue.setCellValueFactory(new PropertyValueFactory<>("value"));
                    symbolView.setItems(symbolList);


                    ObservableList<FileRecord> fileList = FXCollections.observableArrayList();
                    for (int id : p.getFileTable().getAll())
                        fileList.add(new FileRecord(id, p.getFileTable().get(id).getFileName()));
                    fileViewId.setCellValueFactory(new PropertyValueFactory<>("id"));
                    fileViewName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    fileView.setItems(fileList);


                    ObservableList<HeapRecord> heapList = FXCollections.observableArrayList();
                    for (int id : p.getHeap().getAll())
                        heapList.add(new HeapRecord(id, p.getHeap().get(id)));
                    heapViewAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
                    heapViewValue.setCellValueFactory(new PropertyValueFactory<>("value"));
                    heapView.setItems(heapList);

                    ObservableList<ProcRecord> procList = FXCollections.observableArrayList();
                    for (String s : p.getProcTable().getAll())
                        procList.add(new ProcRecord(s, p.getProcTable().get(s).getKey(), p.getProcTable().get(s).getValue()));
                    procTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
                    procTableBody.setCellValueFactory(new PropertyValueFactory<>("body"));
                    procTable.setItems(procList);
                }
            }
            else
            {
                stackView.getItems().clear();
                outView.getItems().clear();
                symbolView.getItems().clear();
                fileView.getItems().clear();
                heapView.getItems().clear();
            }
        }

    }
}
