package View;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller ctrl) {
        super(key, description);
        controller = ctrl;
    }

    @Override
    public void execute() {
       // try {
       //     controller.allStep();
       // } catch (Exception ex) {
       //     System.out.println(ex.getMessage());
       // }
    }


}
