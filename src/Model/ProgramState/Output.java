package Model.ProgramState;

import java.util.ArrayList;

public class Output implements OutputInterface<String> {
    private ArrayList<String> List;

    public Output() {
        this.List = new ArrayList<String>();
    }

    @Override
    public void add(String Message) {
        List.add(Message);
    }

    @Override
    public String toString() {
        String output = "Output:\n";
        for (Object s : List) {
            output += s + "\n";
        }
        return output;
    }

    public ArrayList<String> getList()
    {
        return List;
    }
}
