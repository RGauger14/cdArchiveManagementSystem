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
    private ClientProcessThread clientProcessThread;

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
            clientProcessThread = new ClientProcessThread();
            clientProcessThread.start();
            clientWriteStream.writeObject("test");
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

    class ClientProcessThread extends Thread
    {

        public void run()
        {
            while (true)
            {
                try
                {
                    String msg = (String) clientReadStream.readObject();
                } catch (IOException ex)
                {
                    if (automationConsole != null)
                    // eventually creates pop on automation console to say it broke
                    {
                        break;
                    }
                }
                catch (ClassNotFoundException ex)
                {
                    // TODO - Handle cast exception
                }
            }
        }
    }
}