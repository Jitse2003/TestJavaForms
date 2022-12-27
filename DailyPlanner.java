import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
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


    public DailyPlanner() {

    }

    public void run() {
        frame.setContentPane(panel);
        frame.setTitle("Daily planner");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        panelCreation();
        JdateChooser();
        frame.setVisible(true);
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
                String[] s = dateChooser.getDate().toLocaleString().split(" ");
                String dateString = s[0] + "/" + s[1] + "/" + s[2];

                Task task = new Task(taskField.getText(), Integer.parseInt(hoursTextField.getText()), Integer.parseInt(minutesTextField.getText()), (String) importanceBox.getSelectedItem());
                mapOfPlannings.get(dateString).addTasks(task);

                voegJlistToe();

                taskField.setText("Add new task");
                hoursTextField.setText("Hour");
                minutesTextField.setText("Minutes");
            }
        });

        //REMOVE BUTTON
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskUIList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                    taskUIList.setSelectedIndex(-1);
                    taskField.setText("");
                }
            }
        });
        panel2.add(scrollPane, BorderLayout.CENTER);
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
        String dateString = s[0] + "/" + s[1] + "/" + s[2];
        if (mapOfPlannings.containsKey(dateString)) {

            voegJlistToe();
            System.out.println("already in map");
        }
        else {
            listModel.clear();
            mapOfPlannings.put(dateString, new DailyTasks());
            mapOfPlannings.get(dateString).setDateDailyTask(dateString);
            System.out.println(mapOfPlannings.entrySet());
        }
    }
    public void voegJlistToe(){
        String[] s = dateChooser.getDate().toLocaleString().split(" ");
        String dateString = s[0] + "/" + s[1] + "/" + s[2];

        ArrayList<Task> list = mapOfPlannings.get(dateString).getTasks();
        tasksHashMap.clear();
        for(Task t : list){
            tasksHashMap.put((t.getHours() + t.getMinutes()), t);
        }

        listModel.clear();

        TreeMap<Integer, Task> sorted = new TreeMap<>(sortHashMap());
        for (Task t : sorted.values()) {

            mapOfPlannings.get(dateString).addTasks(t);
            listModel.addElement(t);
        }
        taskUIList.setFont(new Font("serif", Font.PLAIN, 20));
    }
}