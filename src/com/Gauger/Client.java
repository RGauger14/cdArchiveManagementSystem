package com.Gauger;

import UI.AutomationConsole;

import java.net.*;
import java.io.*;

public class Client
{

    private ObjectInputStream clientReadStream;
    private ObjectOutputStream clientWriteStream;
    private Socket socket;
    private AutomationConsole automationConsole;
    private String server;
    private int port;

    public Client(String server, int port, AutomationConsole automationConsole)
    {
        this.server = server;
        this.port = port;
        this.automationConsole = automationConsole;
    }

    public boolean startClient()
    {
        try
        {
            socket = new Socket(server, port);
        } catch (Exception ec)
        {
            return false;
        }

        String debugMessage = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();

        try
        {
            clientReadStream = new ObjectInputStream(socket.getInputStream());
            clientWriteStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO)
        {
            disconnect();
            return false;
        }

        return true;
    }

    private void disconnect()
    {
        try
        {
            if (clientReadStream != null)
            {
                clientReadStream.close();
            }

            if (clientWriteStream != null)
            {
                clientWriteStream.close();
            }

            if (socket != null)
            {
                socket.close();
            }
        } catch (IOException ex)
        {
            // TODO
        }
    }

    public AutomationCommandResult sendRetrieveCommand(CDModel cd)
    {
        AutomationCommand command = AutomationCommand.Retrieve(cd);
        try
        {
            clientWriteStream.writeObject(command);
            AutomationCommandResult result = (AutomationCommandResult) clientReadStream.readObject();
            return result;
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public AutomationCommandResult sendAddCommand(CDModel cd)
    {
        // TODO
        return null;
    }

    public AutomationCommandResult sendRemoveCommand(CDModel cd)
    {
        // TODO
        return null;
    }

    public AutomationCommandResult sendReturnCommand(CDModel cd)
    {
        // TODO
        return null;
    }

    public AutomationCommandResult sendSortCommand(CDModel cd)
    {
        // TODO
        return null;
    }
}