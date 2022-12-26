public class Main {
    public static void main(String[] args) {
        DailyPlanner dailyplanner = new DailyPlanner();
        Task task1 = new Task("task1", 06, 30, "IMPORTANT");
        Task task2 = new Task("task2", 06, 45, "KINDA_IMPORTANT");
        Task task3 = new Task("task1", 07, 00, "NOT_IMPORTANT");

        dailyplanner.run();
    }
}
