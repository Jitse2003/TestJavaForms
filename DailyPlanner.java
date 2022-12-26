import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class DailyPlanner extends JFrame {

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

    private DefaultListModel<Task> listModel;
    private JList<Task> taskUIList;

    HashMap<Integer, Task> tasksHashMap = new HashMap<>();

    public DailyPlanner() {}

    public void run() {
        frame.setContentPane(panel);
        frame.setTitle("Daily planner");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        panelCreation();
        JdatePicker();
        frame.setVisible(true);
    }

    public void panelCreation() {
        panel2.setLayout(new BorderLayout());
        listModel = new DefaultListModel<>();
        taskUIList = new JList<>(listModel);

        JScrollPane scrollPane = new JScrollPane(taskUIList);
        scrollPane.setPreferredSize(new Dimension(250, 80));
        taskUIList.setCellRenderer(new MyCellRenderer() {});

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
                int hours = Integer.parseInt(hoursTextField.getText()) * 100;
                int minutes = Integer.parseInt(minutesTextField.getText());
                tasksHashMap.put((hours + minutes), task);

                TreeMap<Integer, Task> sorted = new TreeMap<>(sortHashMap());
                listModel.clear();

                for (Task t : sorted.values()) {
                    listModel.addElement(t);
                }

                taskUIList.setFont(new Font("serif", Font.PLAIN, 20));

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

    public void JdatePicker() {
        UtilDateModel model = new UtilDateModel();

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setPreferredSize(new Dimension(700,700));
        datePicker.getComponent(0).setPreferredSize(new Dimension(100,26)); //JFormattedTextField
        mainCalendarPanel.add(datePicker);
    }

}