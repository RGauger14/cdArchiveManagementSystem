package com.Gauger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Vector;

public class CDSearch
{
    public static void searchTableContents(JTable cds, String searchTerm)
    {
        int rowCount = cds.getRowCount();
        int colCount = cds.getColumnCount();
        String searchTermLowerCase = searchTerm.toLowerCase();
        for (int row = 0; row < rowCount; row++)
        {
            for (int col = 0; col < colCount; col++)
            {
                try {
                    String cellText = (String) cds.getValueAt(row, col);
                    boolean isMatch = cellText.toLowerCase().contains(searchTermLowerCase);
                    if (isMatch)
                    {
                        // TODO - set match cell color, just concat matched text for now
                        String matchedCellText = cellText.concat(" - (Matched)");
                        cds.setValueAt(matchedCellText, row, col);
                    }
                    else
                    {
                        // TODO - set default cell color, just concat unmatched text for now
                        String unmatchedCellText = cellText.concat(" - (Unmatched)");
                        cds.setValueAt(unmatchedCellText, row, col);
                    }
                } catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
