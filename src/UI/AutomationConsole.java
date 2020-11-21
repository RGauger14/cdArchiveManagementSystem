package UI;

import com.Gauger.AutomationCommand;
import com.Gauger.AutomationCommandResult;
import com.Gauger.CDModel;
import com.Gauger.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AutomationConsole extends JFrame
{
    private final int serverPort;
    private JPanel root;
    private JLabel portLabel;
    private JTable table1;
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

    public AutomationConsole(int serverPort)
    {
        this.serverPort = serverPort;
        portLabel.setText(Integer.toString(serverPort));
        CreateClient();
        processButton.addActionListener(new ActionListener()
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
                    case "Sort":
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

    public AutomationConsole(int serverPort, CDModel cd)
    {
        this(serverPort);
        this.cd = cd;
        portLabel.setText(serverPort + " " + cd.title);
    }

    private void processResult(AutomationCommandResult result)
    {
        if (result.command.getCommandType() == AutomationCommand.CommandType.Retrieve)
        {
            JOptionPane.showMessageDialog(this, result.message);
        }
    }

    private void CreateClient()
    {
        client = new Client("localhost", serverPort, this);
        client.startClient();
    }

    public JPanel getRootPanel()
    {
        return root;
    }
}
