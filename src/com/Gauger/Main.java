package com.Gauger;

import UI.MainUI;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static MainUI ui;

    public static void main(String[] args) throws Exception
    {
        String filePath = ".\\Sample_CD_Archive_Data.txt";
        ArrayList<CDModel> cds = CDFactory.createCDModelsFromFile(filePath);

        // Runs MainUI on a new thread
        SwingUtilities.invokeAndWait(new Runnable()
        {
            @Override
            public void run()
            {
                createGUI(cds);
            }
        });

        // Socket listener runs on main thread with a random free port
        Server server = new Server(0, ui);
        server.startServer();
    }


    private static void createGUI(ArrayList<CDModel> cds)
    {
        ui = new MainUI(cds);
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
