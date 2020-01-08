package View;

import Model.Statements.IStmt;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static View.Examples.*;

public class SelectController {

    private final Stage selectStage;

    @FXML
    private ListView<IStmt> selectExampleListView;

    @FXML
    private Button selectExampleButton;

    public SelectController()
    {
        selectStage = new Stage();
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectWindow.fxml"));
            loader.setController(this);
            selectStage.setScene(new Scene(loader.load()));
            selectStage.setTitle("Toy language");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage()
    {
        selectStage.showAndWait();
    }

    private ArrayList<IStmt> getExampleList()
    {
        ArrayList<IStmt> list = new ArrayList<>();
        Examples exx = new Examples();
        list.add(FirstExample());
        list.add(SecondExample());
        list.add(ThirdExample());
        list.add(FourthExample());
        list.add(exx.HeapReadingEx());
        list.add(exx.HeapAllocationEx());
        list.add(exx.WhileExample());
        list.add(exx.ThreadExample());

        return list;
    }
    @FXML
    private void initialize()
    {
        selectExampleListView.getItems().addAll(getExampleList());
        selectExampleButton.setOnAction(event->openMainWindow());

    }

    private void openMainWindow()
    {
            Controller controller = new Controller(this);
            controller.showStage();
    }

    public IStmt getStatement() {
        return selectExampleListView.getSelectionModel().getSelectedItem();
    }

}
