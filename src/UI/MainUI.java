package UI;

import com.Gauger.CDModel;
import com.Gauger.MyArraySorts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainUI
{
    private JPanel RootPanel;
    private JLabel WindowTitle;
    private JLabel SearchLbl;
    private JTextField Searchtxtfld;
    private JButton SearchBtn;
    private JTable CDTable;
    private JLabel Titlelbl;
    private JLabel AuthorLbl;
    private JLabel Sectionlbl;
    private JPanel XLbl;
    private JLabel YLbl;
    private JLabel BarcodeLbl;
    private JLabel descriptionlbl;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextArea textArea1;
    private JButton retrieveButton;
    private JButton removeButton;
    private JButton returnButton;
    private JButton addToCollectionButton;
    private JTextField textField7;
    private JButton randomCollectionSortButton;
    private JButton mostlyOrderSortButton;
    private JButton reverseOrderSortButton;
    private JButton exitButton;
    private JButton processLogButton;
    private JTextPane textPane1;
    private JButton preOrderButton;
    private JButton inOrderButton;
    private JButton postOrderButton;
    private JButton graphicalButton;
    private JButton saveButton;
    private JButton displayButton;
    private JButton newItemButton;
    private JButton saveUpdateButton;
    private JButton byTitleButton;
    private JButton byAuthorButton;
    private JButton byBarcodeButton;

    private ArrayList<CDModel> cds;

    public MainUI(ArrayList<CDModel> cds)
    {
        this.cds = cds;
        createTable();
        assignCdsToTable();
        byTitleButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                MyArraySorts.bubbleSort(cds);
                clearCdsTable();
                assignCdsToTable();
            }

        });

        byAuthorButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                MyArraySorts.insertionSort(cds);
                clearCdsTable();
                assignCdsToTable();
            }
        });
        byBarcodeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                MyArraySorts.selectionSort(cds);
                clearCdsTable();
                assignCdsToTable();
            }
        });
    };

    private void clearCdsTable()
    {
        DefaultTableModel tableModel = (DefaultTableModel) CDTable.getModel();
        tableModel.setRowCount(0);
    }

    public JPanel getRootPanel()
    {
        return RootPanel;
    }

    private void createTable()
    {
        CDTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "OnLoan"}
        ));
    }

    private void assignCdsToTable()
    {
        DefaultTableModel model = (DefaultTableModel) CDTable.getModel();

        for (CDModel cd : cds)
        {
            model.addRow(new Object[]{cd.title, cd.author, cd.section, cd.x, cd.y, cd.barcode, cd.description, cd.onLoan});
        }
    }
}




