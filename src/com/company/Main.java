package com.company;

import UI.MainUI;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception
    {
        ArrayList<CDModel> cds = CDFactory.createCDModelsFromFile("C:\\Users\\Ryan\\Documents\\Java Repos\\Sample_CD_Archive_Data.txt");
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                createGUI(cds);
            }
        });
    }

    private static void createGUI(ArrayList<CDModel> cds)
    {
        MainUI ui = new MainUI(cds);
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
