import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TaskController {
    private final int typeOfTask;
    private ArrayList<TaskField> listOfTaskView = new ArrayList<>();
    private ScrollPane scrollPane;

    public TaskController(int whatTypeOfTask) {
        this.typeOfTask = whatTypeOfTask;
    }

    public void createTaskList(){
        listOfTaskView = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            TaskField taskField = new TaskField(typeOfTask);
            listOfTaskView.add(taskField);
            taskField.getBtn().setOnAction(event -> {
                taskField.getModel().setOutput(taskField.getOutput().getText());
                taskField.getModel().updateResult();
            });

        }
        VBox vBox = new VBox();
        listOfTaskView.forEach(t -> vBox.getChildren().add((t.getTaskField())));
        scrollPane = new ScrollPane(vBox);
    }

    public Node stats(){

        int rightCount = (int) listOfTaskView.stream().filter(t -> t.getModel().isRight()).count();
        if(rightCount < 6) {
            createTaskList();
            showMessage("correct answers: " + rightCount + "\ntry to solve them again!");
            return scrollPane;

        }else {
            showMessage("Congrats!!!\n" + rightCount + " correct answers");
            return new Label("select the type of task from the menu");
        }

    }

    public static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Stats");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ScrollPane getScrollPane(){
        return scrollPane;
    }
}
