import java.util.ArrayList;
import java.util.List;

public class DailyTasks {
private ArrayList<Task> tasks = new ArrayList<>();
private String dateDailyTask;



    @Override
    public String toString() {
        return "DailyTasks{" +
                "task=" + tasks +
                ", dateDailyTask='" + dateDailyTask + '\'' +
                '}';
    }



    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public String getDateDailyTask() {
        return dateDailyTask;
    }

    public void setDateDailyTask(String dateDailyTask) {
        this.dateDailyTask = dateDailyTask;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }


}
