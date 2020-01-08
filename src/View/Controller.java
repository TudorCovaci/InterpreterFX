package View;

import Model.Exceptions.ProgramException;
import Model.ProgramState.Heap;
import Model.ProgramState.PrgState;
import Model.ProgramState.SymTable;
import Model.Statements.IStmt;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.RepoInterface;
import Repository.Repository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private final SelectController selectController;
    private RepoInterface repository;
    private GarbageCollector garbageCollector;
    private ExecutorService executor;
    @FXML
    private Button selectExampleButton;
    @FXML
    private TableView<HeapTableEntry> heapTableView;
    @FXML
    private TableColumn<HeapTableEntry, Integer> addressColumn;
    @FXML
    private TableColumn<HeapTableEntry, Value> heapValueColumn;
    @FXML
    private TableView<SymTableEntry> symTableView;
    @FXML
    private TableColumn<SymTableEntry, String> nameColumn;
    @FXML
    private TableColumn<SymTableEntry, Value> valueColumn;
    @FXML
    private TextField nrPrgStTxtField;
    @FXML
    private Button runButton;
    @FXML
    private ListView<String> outputListView;
    @FXML
    private ListView<String> exeStackListView;
    @FXML
    private ListView<StringValue> fileTableListView;
    @FXML
    private ListView<PrgState> prgStatesListView;
    @FXML
    private Label errorLabel;

    @FXML
    private Button closeButton;

    private Stage mainStage;

    private Stage errorStage;

    public Controller(SelectController selectController) {

        this.selectController = selectController;
        mainStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow2.fxml"));
            loader.setController(this);
            mainStage.setScene(new Scene(loader.load()));
            mainStage.setTitle("Toy language");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Controller(RepoInterface Repository, SelectController selectController) {

        this.repository = Repository;
        this.selectController = selectController;
        garbageCollector = new GarbageCollector();

    }


    @FXML
    public void initialize() {
        repository = new Repository();
        garbageCollector = new GarbageCollector();
        executor = Executors.newFixedThreadPool(2);
        PrgState state = new PrgState();
        state.getStack().push(selectController.getStatement());
        repository.add(state);

        nrPrgStTxtField.setText(String.valueOf(repository.getLength()));
        prgStatesListView.getItems().addAll(repository.getPrgList());

        outputListView.getItems().addAll(state.getOutput().getList());
        nameColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, String>("nameColumn"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<SymTableEntry, Value>("valueColumn"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<HeapTableEntry, Integer>("addressColumn"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<HeapTableEntry, Value>("valueColumn"));

        runButton.setOnAction(event -> {
            try {
                allStep();
            } catch (ProgramException | InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void updateSymTable(PrgState state) {
        symTableView.getItems().clear();
        symTableView.getItems().addAll(getSymTableEntries(state));
    }

    public void updateHeapTable() {
        PrgState state = prgStatesListView.getSelectionModel().getSelectedItem();
        heapTableView.getItems().clear();
        heapTableView.getItems().addAll(getHeapTableEntries(state));
    }

    public void showStage() {
        mainStage.showAndWait();
    }

    public List<SymTableEntry> getSymTableEntries(PrgState state) {
        SymTable symTable = state.getSymTable();
        List<SymTableEntry> list = new ArrayList<>();
        for (Map.Entry<String, Value> entry : symTable.getTable().entrySet()) {

            list.add(new SymTableEntry(entry.getKey(), entry.getValue()));
        }
        return list;
    }

    public void updateExeStackView(PrgState state) {
        exeStackListView.getItems().clear();
        exeStackListView.getItems().addAll(state.getStack().getStatementList());

        //state.getStack().getStatementList();

    }

    public List<HeapTableEntry> getHeapTableEntries(PrgState state) {
        Heap heap = state.getHeap();
        List<HeapTableEntry> list = new ArrayList<>();
        for (Map.Entry<Integer, Value> entry : heap.getTable().entrySet()) {
            HeapTableEntry heapTableEntry = new HeapTableEntry(entry.getKey(), entry.getValue());
            list.add(heapTableEntry);
        }
        return list;
    }

    public void updatePrgList() {
        prgStatesListView.getItems().clear();
        prgStatesListView.getItems().addAll(repository.getPrgList());

    }

    @FXML
    public void displayPrgDetails() {
        PrgState state = prgStatesListView.getSelectionModel().getSelectedItem();
        if(state != null) {
            updateExeStackView(state);
            updateSymTable(state);
        }
    }

    public void updateNrOfPrg() {
        nrPrgStTxtField.clear();
        nrPrgStTxtField.setText(String.valueOf(repository.getLength()));
        System.out.println("update2");
    }

    public void updateOutput() {
        PrgState state = prgStatesListView.getSelectionModel().getSelectedItem();
        outputListView.getItems().clear();
        outputListView.getItems().addAll(state.getOutput().getList());
    }

    public void updateFileTable() {
        PrgState state = prgStatesListView.getSelectionModel().getSelectedItem();
        fileTableListView.getItems().clear();
        fileTableListView.getItems().addAll(state.getFileTable().getFileNames());
    }

    public void update() {
        displayPrgDetails();
        updateHeapTable();
        updateNrOfPrg();
        updateOutput();
        updateFileTable();
        updatePrgList();
        System.out.println("update");


    }


    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException, ProgramException {
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (ProgramException e) {
                e.printStackTrace();
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        prgList.addAll(newPrgList);
        RepoInterface repoInterface = repository;
        for (PrgState prgState : prgList) {
            repoInterface.logPrgStateExec(prgState);
        }
        repository.setPrgList(new ArrayList<>(prgList));
    }

    public void promptErrorWindow(String message) throws IOException {
        errorStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("errorWindow.fxml"));
        loader.setController(this);
        errorStage.setScene(new Scene(loader.load()));
        errorStage.setTitle("Error");
        errorLabel.setText(message);
        closeButton.setOnAction(event -> errorStage.close());
        errorStage.show();

    }

    public void allStep() throws ProgramException, InterruptedException, IOException {

        List<PrgState> prgStateList = removeCompletedPrg(repository.getPrgList());
        if (prgStatesListView.getSelectionModel().getSelectedItem() == null) {
            promptErrorWindow("Please choose a program state!");
        } else {
            if (prgStateList.size() > 0) {
                prgStateList.get(0).getHeap().setContent(garbageCollector.multithreadedGC(prgStateList));
                oneStepForAllPrg(prgStateList);
                System.out.println(prgStateList.get(0).toString());
                prgStateList = removeCompletedPrg(repository.getPrgList());
                update();

            }


        }
        repository.setPrgList(new ArrayList<PrgState>(prgStateList));
    }


}
