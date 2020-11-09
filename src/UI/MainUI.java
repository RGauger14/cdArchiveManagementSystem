package UI;

import com.Gauger.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtSection;
    private JTextField txtX;
    private JTextField txtY;
    private JTextField txtBarcode;
    private JTextArea txtDescription;
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
    private JTextPane ProcessLogTextArea;
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
    private JLabel labelOnLoan;
    private JCheckBox checkboxOnLoan;
    private ArrayList<CDModel> cds;
    private int selectedCdId;

    binaryTree bTree = new binaryTree();

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
                bTree = new binaryTree();
                values();

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
        SearchBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CDSearch.searchTableContents(CDTable, Searchtxtfld.getText());
            }
        });

        CDTable.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent mouseEvent)
            {
                super.mouseClicked(mouseEvent);
                selectedCdId = 0;
                int i = CDTable.convertRowIndexToModel(CDTable.getSelectedRow());
                TableModel cdTableModel = CDTable.getModel();
                selectedCdId = (int) cdTableModel.getValueAt(i, 0);
                int selectedCd = getCdIndexById(selectedCdId);
                if (selectedCd == -1)
                {
                    return;
                }

                populateCdForm(selectedCd);
            }
        });

        saveUpdateButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                updateCd();
            }
        });

        newItemButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                addNewCd();
            }
        });
        preOrderButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                ProcessLogTextArea.setText("");
                binaryTree.SB.setLength(0);
                bTree.preorderTraverseTree(bTree.root);
                ProcessLogTextArea.setText(String.valueOf(binaryTree.SB));
            }
        });
        inOrderButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                processLogButton.setText("");
                binaryTree.SB.setLength(0);
                bTree.inOrderTraverseTree(bTree.root);
                ProcessLogTextArea.setText(String.valueOf(binaryTree.SB));
            }
        });
        postOrderButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                processLogButton.setText("");
                binaryTree.SB.setLength(0);
                bTree.postOrderTraverseTree(bTree.root);
                ProcessLogTextArea.setText(String.valueOf(binaryTree.SB));
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
                new String[]{"Id", "Title", "Author", "Section", "X", "Y", "Barcode", "Description", "OnLoan"}
        ));
    }

    private void assignCdsToTable()
    {
        DefaultTableModel model = (DefaultTableModel) CDTable.getModel();

        for (CDModel cd : cds)
        {
            model.addRow(new Object[]{cd.id, cd.title, cd.author, cd.section, cd.x, cd.y, cd.barcode, cd.description, cd.onLoan});
        }
    }

    private int getCdIndexById(int id)
    {
        for (int i=0; i<cds.size(); i++)
        {
            if (cds.get(i).id == id)
            {
                return i;
            }
        }

        return -1;
    }

    private void populateCdForm(int cdIndex)
    {
        CDModel cd = cds.get(cdIndex);
        txtTitle.setText(cd.title);
        txtAuthor.setText(cd.author);
        txtSection.setText(cd.section);
        txtX.setText(String.valueOf(cd.x));
        txtY.setText(String.valueOf(cd.y));
        txtBarcode.setText(String.valueOf(cd.barcode));
        txtDescription.setText(cd.description);
        checkboxOnLoan.setSelected(cd.onLoan);
    }

    private void updateCd()
    {
        int cdIndex = getCdIndexById(selectedCdId);
        CDModel selectedCd = cds.get(cdIndex);
        selectedCd.title = txtTitle.getText();
        selectedCd.author = txtAuthor.getText();
        selectedCd.section = txtSection.getText();
        selectedCd.x = Integer.parseInt(txtX.getText());
        selectedCd.y = Integer.parseInt(txtY.getText());
        selectedCd.barcode = Integer.parseInt(txtBarcode.getText());
        selectedCd.description = txtDescription.getText();
        selectedCd.onLoan = checkboxOnLoan.isSelected();
        cds.set(cdIndex, selectedCd);
        refreshTable();
        clearCdForm();
    }

    private void addNewCd()
    {
        CDModel newCd = new CDModel(
            getNewCdId(),
            txtTitle.getText(),
            txtAuthor.getText(),
            txtSection.getText(),
            Integer.parseInt(txtX.getText()),
            Integer.parseInt(txtY.getText()),
            Integer.parseInt(txtBarcode.getText()),
            txtDescription.getText(),
            checkboxOnLoan.isSelected()
        );
        cds.add(newCd);
        refreshTable();
        clearCdForm();
    }

    private int getNewCdId()
    {
        int highestId = 0;
        for (CDModel cd : cds)
        {
            if (cd.id > highestId)
            {
                highestId = cd.id;
            }
        }
        return highestId + 1;
    }

    private void refreshTable()
    {
        clearCdsTable();
        assignCdsToTable();
    }

    private void clearCdForm()
    {
        String empty = "";
        selectedCdId = -1;
        txtTitle.setText(empty);
        txtAuthor.setText(empty);
        txtSection.setText(empty);
        txtX.setText(empty);
        txtY.setText(empty);
        txtBarcode.setText(empty);
        txtDescription.setText(empty);
        checkboxOnLoan.setSelected(false);
    }

    public void values() {
        for(int i = 0;1<CDTable.getModel().getRowCount();i++)
        {
            bTree.addNode(Integer.parseInt((CDTable.getModel().getValueAt(i,6)).toString()),
                    CDTable.getModel().getValueAt(i,1).toString());
        }
    }
}