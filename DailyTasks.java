import java.util.ArrayList;

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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTasks(Task task) {
        this.tasks.add(task);
    }

    public String getDateDailyTask() {
        return dateDailyTask;
    }

    public void setDateDailyTask(String dateDailyTask) {
        this.dateDailyTask = dateDailyTask;
    }
}
