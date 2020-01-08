package Model.ProgramState;

import Model.Values.StringValue;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileTable extends Dictionary<StringValue, BufferedReader> {

    public String toString() {
        String string = "File Table:\n";
        for (Map.Entry<StringValue, BufferedReader> entry : table.entrySet()) {

            string = string + entry.getKey() + " = " + entry.getValue().toString() + '\n';
        }
        return string;
    }

    public List<StringValue> getFileNames()
    {
        List<StringValue> list = new ArrayList<>();
        for(Map.Entry<StringValue, BufferedReader> entry : table.entrySet())
        {
            list.add(entry.getKey());
        }
        return list;
    }
}
