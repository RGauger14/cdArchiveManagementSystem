package UI;

import com.Gauger.AutomationCommand;
import com.Gauger.AutomationCommandResult;
import com.Gauger.CDModel;
import com.Gauger.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        processButton.addActionListener(createRequestActionListener());
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
        // TODO: Add logic that uses the section?
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
        if (result.command.getCommandType() == AutomationCommand.CommandType.Retrieve)
        {
            JOptionPane.showMessageDialog(this, result.message);
        }
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

    public ActionListener createRequestActionListener()
    {
        return new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                AutomationCommandResult result = null;
                switch ((String) RequestActionComboBox.getSelectedItem())
                {
                    case "Retrieve":
                        result = client.sendRetrieveCommand(cd);
                        break;
                    case "Add":
                        result = client.sendAddCommand(cd);
                        break;
                    case "Return":
                        result = client.sendReturnCommand(cd);
                        break;
                    case "Remove":
                        result = client.sendRemoveCommand(cd);
                        break;
                    case "Sort":
                        result = client.sendSortCommand(cd);
                        break;
                    default:
                }

                if (result == null)
                {
                    // Handle error processing command
                }

                processResult(result);
            }
        };
    }
}
