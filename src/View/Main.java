package View;

import Model.Exceptions.ProgramException;
import Model.Expressions.ArithExp;
import Model.Expressions.ValueExp;
import Model.Expressions.VarExp;
import Model.ProgramState.Dictionary;
import Model.ProgramState.PrgState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.RepoInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Repository.Repository;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {





    @Override
    public void start(Stage primaryStage) throws Exception{
        SelectController selectController = new SelectController();
        selectController.showStage();
    }

    public static void main(String[] args) {

        launch(args);

    }
}
