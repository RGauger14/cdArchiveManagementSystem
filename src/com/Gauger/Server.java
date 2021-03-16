package com.Gauger;

import UI.MainUI;

import java.io.*;
import java.net.*;
import java.util.*;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Server
{
    private static int uniqueId;
    private ArrayList<ServerClientProcessThread> serverClientProcessThreads;
    private MainUI mainUI;
    private int port;
    private boolean isServerRunning;
    private ServerSocket serverSocket;


    public Server(int port, MainUI mainUI)
    {
        this.mainUI = mainUI;
        this.port = port;
        serverClientProcessThreads = new ArrayList<>();
    }

    public void startServer()
    {
        isServerRunning = true;
        try
        {
            serverSocket = new ServerSocket(port);
            port = serverSocket.getLocalPort();
            mainUI.setServerPort(port);

            while (isServerRunning)
            {
                Socket socket = serverSocket.accept();
                ServerClientProcessThread serverClientProcessThread = new ServerClientProcessThread(socket);
                serverClientProcessThreads.add(serverClientProcessThread);
                serverClientProcessThread.start();
            }
        } catch (IOException e)
        {
            // TODO
        }
    }

    public void stopServer() throws IOException
    {
        isServerRunning = false;
        serverSocket.close();
        for (int i = 0; i < serverClientProcessThreads.size(); ++i)
        {
            ServerClientProcessThread serverClientProcessThread = serverClientProcessThreads.get(i);
            serverClientProcessThread.close();
        }
    }

    synchronized void removeClient(int id)
    {
        for (int i = 0; i < serverClientProcessThreads.size(); ++i)
        {
            ServerClientProcessThread ct = serverClientProcessThreads.get(i);

            if (ct.id == id)
            {
                serverClientProcessThreads.remove(i);
                return;
            }
        }
    }

    class ServerClientProcessThread extends Thread
    {
        Socket socket;
        ObjectInputStream serverReadStream;
        ObjectOutputStream serverWriteStream;
        int id;

        ServerClientProcessThread(Socket socket)
        {
            id = ++uniqueId;
            this.socket = socket;
            System.out.println("Thread trying to create Object Input/Output Streams");
            try
            {
                serverWriteStream = new ObjectOutputStream(socket.getOutputStream());
                serverReadStream = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e)
            {
                // TODO
                return;
            }
        }

        public void run()
        {
            boolean isClientActive = true;
            while (isClientActive)
            {
                try
                {
                    AutomationCommand command = (AutomationCommand) serverReadStream.readObject();
                    AutomationCommandResult commandResult = processCommand(command);
                    serverWriteStream.writeObject(commandResult);
                    System.out.println();
                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }

            removeClient(id);
            close();
        }

        private AutomationCommandResult processCommand(AutomationCommand command)
        {
            String resultMessage;
            CDModel cd = command.getCd();
            switch (command.getCommandType())
            {
                case Retrieve:
                    resultMessage = "The CD with barcode " + cd.barcode + " has been retrieved and the CD has been set to OnLoan in the database.";
                    mainUI.addToProcessLog(command);
                    break;
                case Add:
                    resultMessage = "The CD with barcode " + cd.barcode + " has been added to the database at Section: " + cd.section + ", X: " + cd.x + ", Y: " + cd.y;
                    mainUI.addToProcessLog(command);
                    break;
                case Sort:
                    resultMessage = "The CDs have been sorted by section: " + command.getSortSection();
                    mainUI.addToProcessLog(command);
                    break;
                case Remove:
                    resultMessage = "The CD with barcode " + cd.barcode + " has been removed from the database";
                    mainUI.addToProcessLog(command);
                    break;
                case Return:
                    resultMessage = "The CD with barcode " + cd.barcode + " has been returned and set to OffLoan in the database.";
                    mainUI.addToProcessLog(command);
                    break;
                default:
                    resultMessage = "The command is unknown and cannot be processed.";
            }

            return new AutomationCommandResult(command, resultMessage);
        }

        private void close()
        {
            try
            {
                if (serverWriteStream != null)
                {
                    serverWriteStream.close();
                }
                if (serverReadStream != null)
                {
                    serverReadStream.close();
                }
                if (socket != null)
                {
                    socket.close();
                }
            } catch (Exception e)
            {
                // TODO
            }
        }
    }
}