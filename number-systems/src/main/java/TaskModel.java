import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigInteger;
import java.util.Random;

public class TaskModel {
    private String input;
    private String input2;
    private String output;
    private String answer;
    private int system;
    private final int whatTask;
    private boolean additionSign;
    private boolean isRight;
    private final StringProperty problem = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();

    public TaskModel(int whatTask) {
        this.whatTask = whatTask;
        generateTask();
        createProblem();
    }

    public void generateTask() {
        Random random = new Random();
        system = random.nextInt(11) + 2;
        if (system == 10) system = 2;
        input = String.valueOf(random.nextInt(200) + 1);
        input2 = String.valueOf(random.nextInt(200) + 1);
        additionSign = random.nextBoolean();
        BigInteger bigInteger;


        switch (whatTask) {
            case 1:
                answer = input;
                bigInteger = new BigInteger(answer);
                input = bigInteger.toString(system);
                break;

            case 2:
                bigInteger = new BigInteger(input);
                answer = bigInteger.toString(system);
                break;
            case 3:
                if (additionSign)
                    answer = String.valueOf(Integer.parseInt(input) + Integer.parseInt(input2));
                else
                    answer = String.valueOf(Integer.parseInt(input) - Integer.parseInt(input2));
                bigInteger = new BigInteger(input);
                input = bigInteger.toString(system);
                bigInteger = new BigInteger(input2);
                input2 = bigInteger.toString(system);
                bigInteger = new BigInteger(answer);
                answer = bigInteger.toString(system);
                break;

        }
    }

    public void createProblem() {
        if (getWhatTask() == 1) {
            setProblem("convert number " + getInput() + "\nfrom " + getSystem() + " to decimal");
        } else if (getWhatTask() == 2) {
            setProblem("convert number " + getInput() + "\nfrom decimal to " + getSystem());
        } else {
            if (additionSign) {
                setProblem("calculate: " + getInput() + " + " + getInput2() + "\nin " + getSystem() + " system");
            } else {
                setProblem("calculate: " + getInput() + " - " + getInput2() + "\nin " + getSystem() + " system");

            }
        }
    }

    public void updateResult() {
        if (output.equals(answer)) {
            setResult("Right");
            setRight(true);
        }
        else {
            setResult("Wrong");
            setRight(false);
        }
    }

    public String getInput() {
        return input;
    }

    public String getInput2() {
        return input2;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public int getSystem() {
        return system;
    }

    public int getWhatTask() {
        return whatTask;
    }

    public StringProperty problemProperty() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem.set(problem);
    }

    public StringProperty resultProperty() {
        return result;
    }

    public void setResult(String result) {
        this.result.set(result);
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
