import javax.swing.*;
import java.awt.*;

class MyCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {


        DefaultListCellRenderer renderer = (DefaultListCellRenderer)
                list.getCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        Task task = (Task) value;
        if (task.getImportance().equals("IMPORTANT")) {
            setBackground(Color.RED);
        } else if (task.getImportance().equals("KINDA_IMPORTANT")) {
            setBackground(Color.YELLOW);
        } else if (task.getImportance().equals("NOT_IMPORTANT")) {
            setBackground(Color.GREEN);
        }
        return c;
    }
}