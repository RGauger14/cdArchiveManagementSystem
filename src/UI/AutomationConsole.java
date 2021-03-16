package UI;

import com.Gauger.AutomationCommand;
import com.Gauger.AutomationCommandResult;
import com.Gauger.CDModel;
import com.Gauger.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AutomationConsole extends JFrame
{
    private final int serverPort;
    private JPanel root;
    private JLabel portLabel;
    private JTable AutomationConsoleTable;
    private JLabel CurrentRequest;
    private JComboBox RequestActionComboBox;
    private JTextField Barcodetextvox;
    private JLabel section;
    private JButton processButton;
    private JTextField sectionTextBox;
    private JButton addItemButton;
    private JButton ExitButton;
    private Client client;

    private CDModel cd;
    private ArrayList<CDModel> cds;

    private AutomationConsole(int serverPort)
    {
        this.serverPort = serverPort;
        portLabel.setText(Integer.toString(serverPort));
        createClient();
        JFrame jframe = this;
        Component component = this;
        ExitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                jframe.dispose();
            }
        });
        addItemButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                JOptionPane.showMessageDialog(component, "The CD has been sent to the robotic ARM to add.");
                AutomationCommandResult result = client.sendAddCommand(cd);
                processResult(result);
            }
        });
        processButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                AutomationCommandResult result = null;
                switch ((String) RequestActionComboBox.getSelectedItem())
                {
                    case "Retrieve":
                        JOptionPane.showMessageDialog(component, "The CD has been sent to the robotic ARM to retrieve.");
                        result = client.sendRetrieveCommand(cd);
                        break;
                    case "Add":
                        JOptionPane.showMessageDialog(component, "The CD has been sent to the robotic ARM to add.");
                        result = client.sendAddCommand(cd);
                        break;
                    case "Return":
                        JOptionPane.showMessageDialog(component, "The CD has been sent to the robotic ARM to return.");
                        result = client.sendReturnCommand(cd);
                        break;
                    case "Remove":
                        JOptionPane.showMessageDialog(component, "The CD has been sent to the robotic ARM to remove.");
                        result = client.sendRemoveCommand(cd);
                        break;
                    case "Sort":
                        JOptionPane.showMessageDialog(component, "The CDs have been sent to the robotic ARM to sort.");
                        result = client.sendSortCommand(sectionTextBox.getText());
                        break;
                    default:
                }

                if (result == null)
                {
                    // Handle error processing command
                }

                processResult(result);
            }
        });
    }

    private AutomationConsole(int serverPort, CDModel cd)
    {
        this(serverPort);
        this.cd = cd;
        createTable();
        assignCdToTable();
    }

    public AutomationConsole(int serverPort, ArrayList<CDModel> cds)
    {
        this(serverPort);
        this.cds = cds;
        createTable();
        assignCdsToTable();
    }

    public static AutomationConsole createRetrieveAutomationConsole(int serverPort, CDModel cd)
    {
        AutomationConsole console = new AutomationConsole(serverPort, cd);
        console.setActionTo("Retrieve");
        return console;
    }

    public static AutomationConsole createAddAutomationConsole(int serverPort, CDModel cd)
    {
        AutomationConsole console = new AutomationConsole(serverPort, cd);
        console.setActionTo("Add");
        return console;
    }

    public static AutomationConsole createReturnAutomationConsole(int serverPort, CDModel cd)
    {
        AutomationConsole console = new AutomationConsole(serverPort, cd);
        console.setActionTo("Return");
        return console;
    }

    public static AutomationConsole createSortAutomationConsole(int serverPort, String section, ArrayList<CDModel> cds)
    {
        AutomationConsole console = new AutomationConsole(serverPort, cds);
        console.setActionTo("Sort");
        return console;
    }

    public static AutomationConsole createRemoveAutomationConsole(int serverPort, CDModel cd)
    {
        AutomationConsole console = new AutomationConsole(serverPort, cd);
        console.setActionTo("Remove");
        return console;
    }

    private void setActionTo(String action)
    {
        RequestActionComboBox.setSelectedItem(action);
    }

    private void createTable()
    {
        AutomationConsoleTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Id", "Title", "Author", "Section", "X", "Y", "Barcode", "Description", "OnLoan"}
        ));
    }

    private void assignCdToTable()
    {
        DefaultTableModel model = (DefaultTableModel) AutomationConsoleTable.getModel();
        model.addRow(new Object[]{cd.id, cd.title, cd.author, cd.section, cd.x, cd.y, cd.barcode, cd.description, cd.onLoan});
    }

    private void assignCdsToTable()
    {
        DefaultTableModel model = (DefaultTableModel) AutomationConsoleTable.getModel();
        for (CDModel cd : cds)
        {
            model.addRow(new Object[]{cd.id, cd.title, cd.author, cd.section, cd.x, cd.y, cd.barcode, cd.description, cd.onLoan});
        }
    }

    private void processResult(AutomationCommandResult result)
    {
        JOptionPane.showMessageDialog(this, result.message);
    }

    private void createClient()
    {
        client = new Client("localhost", serverPort, this);
        client.startClient();
    }

    public JPanel getRootPanel()
    {
        return root;
    }
}
