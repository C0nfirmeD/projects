import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TaskField {
    private final TextField output;
    private final VBox root;
    private final TaskModel model;
    private final Button btn;

    public TextField getOutput() {
        return output;
    }

    public TaskField(int question) {
        model = new TaskModel(question);

        root = new VBox();
        root.setSpacing(12);

        Label taskText = new Label();
        taskText.textProperty().bind(model.problemProperty());
        output = new TextField();
        btn = new Button("Ok");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(output, btn);
        Label verdict = new Label();
        verdict.textProperty().bind(model.resultProperty());


        root.getChildren().addAll(taskText, hBox, verdict, new Separator());

    }

    public VBox getTaskField(){
        return root;
    }

    public Button getBtn() {
        return btn;
    }

    public TaskModel getModel() {
        return model;
    }

}
