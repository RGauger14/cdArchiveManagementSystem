package UI;

import com.Gauger.Client;

import javax.swing.*;

public class AutomationConsole
{
    private final int serverPort;
    private JPanel root;
    private JLabel portLabel;
    private Client client;

    public AutomationConsole(int serverPort)
    {
        this.serverPort = serverPort;
        portLabel.setText(Integer.toString(serverPort));
        CreateClient();
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
