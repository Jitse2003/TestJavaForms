import java.util.ArrayList;

public class DailyTasks {
private ArrayList<Task> task = new ArrayList<>();
private String dateDailyTask;

    @Override
    public String toString() {
        return "DailyTasks{" +
                "task=" + task +
                ", dateDailyTask='" + dateDailyTask + '\'' +
                '}';
    }

    public ArrayList<Task> getTask() {
        return task;
    }

    public void addTask(Task task) {
        this.task.add(task);
    }

    public String getDateDailyTask() {
        return dateDailyTask;
    }

    public void setDateDailyTask(String dateDailyTask) {
        this.dateDailyTask = dateDailyTask;
    }
}
