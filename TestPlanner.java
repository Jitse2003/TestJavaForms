import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestPlanner {
    public static void main(String[] args) {

        DailyPlanner planner = new DailyPlanner();
        DailyTasks tasks = new DailyTasks();
        Task task1 = new Task("TestTask", 6, 50, "IMPORTANT");
        Task task2 = new Task("TestTask2", 8, 50, "IMPORTANT");
        Task task3 = new Task("TestTask3", 10, 50, "KINDA_IMPORTANT");

        tasks.addTask(task1);
        tasks.addTask(task2);
        tasks.addTask(task3);


        System.out.println("Voeg Task Toe \n");
        planner.mapOfPlannings.put(planner.dateString, tasks);
        System.out.println(planner.mapOfPlannings);


        System.out.println("-------------------------------------------------------------------------------------------------------");

        System.out.println("Remove Task \n");
        planner.mapOfPlannings.put(planner.dateString, tasks);
        System.out.println(planner.mapOfPlannings);


        System.out.println("-------------------------------------------------------------------------------------------------------");

        System.out.println("Write to CSV file \n");
        planner.writeMapToCSV("Files/tasks.csv");
        planner.mapOfPlannings.clear();
        planner.readMapFromCSV("Files/tasks.csv");
        System.out.println(planner.mapOfPlannings);


        System.out.println("-------------------------------------------------------------------------------------------------------");


        System.out.println("Clear");
        planner.mapOfPlannings.clear();
        System.out.println(planner.mapOfPlannings);


        System.out.println("-------------------------------------------------------------------------------------------------------\n");


        System.out.println("Read CSV file \n");

        planner.readMapFromCSV("Files/tasks.csv");
        System.out.println(planner.mapOfPlannings);

        System.out.println("-------------------------------------------------------------------------------------------------------");


    }
}
