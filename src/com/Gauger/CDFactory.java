package com.Gauger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class CDFactory {
    public static ArrayList<CDModel> createCDModelsFromFile(String filePath) throws Exception
    {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<CDModel> cds = new ArrayList<>();
        String line;
        reader.readLine(); // to ignore first line of the txtfile.
        while ((line = reader.readLine()) != null)
        {
            String[] data = line.split(";");
            CDModel cd = createCD(data);
            cds.add(cd);
            System.out.print(line);
        }
        return cds;
    }

    private static CDModel createCD(String[] data)
    {
        int id = Integer.parseInt(data[0]);
        String title = data[1];
        String author = data[2];
        String section = data[3];
        int x = Integer.parseInt(data[4]);
        int y = Integer.parseInt(data[5]);
        int barcode = Integer.parseInt(data[6]);
        String description = data[7];
        boolean onLoan = Boolean.parseBoolean(data[8]);

        return new CDModel(id, title, author, section, x, y, barcode, description, onLoan);
    }

}
