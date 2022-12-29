import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class DailyPlanner {

    JFrame frame = new JFrame();

    private JPanel panel;
    private JPanel panel2;
    private JPanel calendarPanel;
    private JPanel mainCalendarPanel;

    private JButton searchButton;
    private JButton removeButton;
    private JButton addButton;

    private JTabbedPane tabbedPane;

    private JTextField taskField;
    private JTextField hoursTextField;
    private JTextField minutesTextField;
    private JTextField searchField;

    private JComboBox importanceBox;
    private JButton pickDateButton;

    private DefaultListModel<Task> listModel;
    private JList<Task> taskUIList;

    JDateChooser dateChooser = new JDateChooser();

    HashMap<Integer, Task> tasksHashMap = new HashMap<>();

    HashMap<String, DailyTasks> mapOfPlannings = new HashMap<>();

    String dateString;

    public DailyPlanner() {
    }

    public void run() {
        frame.setContentPane(panel);
        frame.setTitle("Daily planner");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelCreation();
        JdateChooser();
        readMapFromCSV("Files/tasks.csv");

        frame.setVisible(true);
        setDayToday();
    }

    public void panelCreation() {
        panel2.setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        taskUIList = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(taskUIList);
        scrollPane.setPreferredSize(new Dimension(250, 80));
        taskUIList.setCellRenderer(new MyCellRenderer() {
        });

        taskField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                taskField.setText("");
            }
        });
        hoursTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                hoursTextField.setText("");
            }
        });
        minutesTextField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                minutesTextField.setText("");
            }
        });
        panel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        //PICK DATE BUTTON
        pickDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addPlannerIfNewKey();

            }
        });

        //SEARCH BUTTON
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                filterModel(listModel, searchField.getText());
            }
        });

        //ADD BUTTON
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task task = new Task(taskField.getText(), Integer.parseInt(hoursTextField.getText()), Integer.parseInt(minutesTextField.getText()), (String) importanceBox.getSelectedItem());
                mapOfPlannings.get(dateString).addTasks(task);
                voegJlistToe();

                taskField.setText("Add new task");
                hoursTextField.setText("Hour");
                minutesTextField.setText("Minutes");

                writeMapToCSV("Files/tasks.csv");
            }
        });

        //REMOVE BUTTON
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskUIList.getSelectedIndex();
                int hours = listModel.get(selectedIndex).getHours();
                int minutes = listModel.get(selectedIndex).getMinutes();
                ArrayList<Task> taskList = mapOfPlannings.get(dateString).getTasks();
                taskList.removeIf(t -> t.getHours() == hours && t.getMinutes() == minutes);
                mapOfPlannings.get(dateString).setTasks(taskList);

                writeMapToCSV("Files/tasks.csv");
                voegJlistToe();
            }
        });
        panel2.add(scrollPane, BorderLayout.CENTER);
    }

    public void setDayToday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateChooser.setCalendar(new GregorianCalendar(year, month, day) {
        });
        dateString = (month + 1) + "/" + day + "/" + year;
        addPlannerIfNewKey();
    }

    public TreeMap<Integer, Task> sortHashMap() {
        return new TreeMap<>(tasksHashMap);
    }

    public void filterModel(DefaultListModel<Task> model, String filter) {
        for (Task t : sortHashMap().values()) {
            if (!t.getOmschrijving().startsWith(filter)) {
                if (model.contains(t.getOmschrijving())) {
                    model.removeElement(t);
                }
            } else {
                if (!model.contains(t.getOmschrijving())) {
                    model.addElement(t);
                }
            }
        }
    }

    public void JdateChooser() {
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.getJCalendar().setPreferredSize(new Dimension(325, 400));
        mainCalendarPanel.setLayout(new GridLayout(1, 1));
        mainCalendarPanel.add(dateChooser);
    }

    public void addPlannerIfNewKey() {
        String[] s = dateChooser.getDate().toLocaleString().split(" ");
        dateString = s[0] + "/" + s[1] + "/" + s[2];
        if (mapOfPlannings.containsKey(dateString)) {
            voegJlistToe();
        } else {
            listModel.clear();
            mapOfPlannings.put(dateString, new DailyTasks());
            mapOfPlannings.get(dateString).setDateDailyTask(dateString);
        }
    }

    public void voegJlistToe() {
        ArrayList<Task> list = mapOfPlannings.get(dateString).getTasks();
        tasksHashMap.clear();
        for (Task t : list) {
            tasksHashMap.put((t.getHours() + t.getMinutes()), t);
        }
        listModel.clear();

        TreeMap<Integer, Task> sorted = new TreeMap<>(sortHashMap());
        for (Task t : sorted.values()) {
            listModel.addElement(t);
        }
        taskUIList.setFont(new Font("serif", Font.PLAIN, 20));
    }


    public String toCSV(String date) {
        DailyTasks dailyTasks = mapOfPlannings.get(date);
        StringBuilder sb = new StringBuilder();
        sb.append(dailyTasks.getDateDailyTask()).append(",");
        for (Task task : dailyTasks.getTasks()) {
            sb.append(task.getOmschrijving()).append(",")
                    .append(task.getHours()).append(",")
                    .append(task.getMinutes()).append(",")
                    .append(task.getImportance()).append(",");
        }
        return sb.toString();
    }

    public void fromCSV(String csv) {
        String[] values = csv.split(",");
        String date = values[0];
        DailyTasks dailyTasks = new DailyTasks();
        dailyTasks.setDateDailyTask(date);
        for (int i = 1; i < values.length; i += 4) {
            String description = values[i];
            int hours = Integer.parseInt(values[i + 1]);
            int minutes = Integer.parseInt(values[i + 2]);
            String importance = values[i + 3];
            dailyTasks.addTask(new Task(description, hours, minutes, importance));
        }
        mapOfPlannings.put(date, dailyTasks);
    }

    public void writeMapToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String date : mapOfPlannings.keySet()) {
                String csv = toCSV(date);
                writer.write(csv);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMapFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fromCSV(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}