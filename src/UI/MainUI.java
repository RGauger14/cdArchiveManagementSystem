package UI;

import com.Gauger.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class MainUI extends JFrame
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
    private JTextField sortSection;
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
    private int serverPort;

    private HashSet<String> processLogHashSet = new HashSet<>();

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
                createBinaryTreeFromTable();
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
        retrieveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CDModel selectedCD = getSelectedCd(cds);
                createRetrieveAutomationConsole(selectedCD);
                String log = getProcessLogFromString("Retrieve CD", selectedCD.barcode);
                addToProcessLog(log);
            }
        });
        addToCollectionButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CDModel selectedCD = getSelectedCd(cds);
                createAddAutomationConsole(selectedCD);
                String log = getProcessLogFromString("Add CD", selectedCD.barcode);
                addToProcessLog(log);
            }
        });
        removeButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CDModel selectedCD = getSelectedCd(cds);
                createRemoveAutomationConsole(selectedCD);
                String log = getProcessLogFromString("Remove CD", selectedCD.barcode);
                addToProcessLog(log);
            }
        });
        returnButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                CDModel selectedCD = getSelectedCd(cds);
                createReturnAutomationConsole(selectedCD);
                String log = getProcessLogFromString("Return CD", selectedCD.barcode);
                addToProcessLog(log);
            }
        });
        randomCollectionSortButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String section = sortSection.getText();
                ArrayList<CDModel> cdsBySection = getCdsBySection(section);
                createSortAutomationConsole(cdsBySection, section);
                String log = getProcessLogFromString("Randomly sort CDs", -1);
                addToProcessLog(log);
            }
        });
        mostlyOrderSortButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String section = sortSection.getText();
                ArrayList<CDModel> cdsBySection = getCdsBySection(section);
                createSortAutomationConsole(cdsBySection, section);
                String log = getProcessLogFromString("Mostly sort CDs", -1);
                addToProcessLog(log);
            }
        });
        reverseOrderSortButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                String section = sortSection.getText();
                ArrayList<CDModel> cdsBySection = getCdsBySection(section);
                createSortAutomationConsole(cdsBySection, section);
                String log = getProcessLogFromString("Reverse sort CDs", -1);
                addToProcessLog(log);
            }
        });

        Component component = this;
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                saveProcessLogHashSet();
                JOptionPane.showMessageDialog(component, "The process log has been saved to process.log");
            }
        });
    }

    private ArrayList<CDModel> getCdsBySection(String section)
    {
        ArrayList cdsBySection = new ArrayList();
        for (CDModel cd : cds)
        {
            if (cd.section.equalsIgnoreCase(section))
            {
                cdsBySection.add(cd);
            }
        }
        return cdsBySection;
    }

    private CDModel getSelectedCd(ArrayList<CDModel> cds)
    {
        int selectedCDIndex =  getCdIndexById(selectedCdId);
        return cds.get(selectedCDIndex);
    }

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

    public void createBinaryTreeFromTable() {
        bTree = new binaryTree();
        TableModel tableModel = CDTable.getModel();
        for(int i = 0; 1 < tableModel.getRowCount(); i++)
        {
            String textFieldText = tableModel.getValueAt(i,6).toString();
            int barcode = Integer.parseInt(textFieldText);
            String title = tableModel.getValueAt(i,1).toString();
            bTree.addNode(barcode, title);
        }
    }

    public void saveProcessLogHashSet()
    {
        FileWriter fileWriter = null;
        try
        {
            fileWriter = new FileWriter("process.log");
            for (String log : processLogHashSet)
            {
                fileWriter.write(log);
                fileWriter.write("\n");
            }

            fileWriter.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /*
    public void values() {
        for(int i = 0;1<CDTable.getModel().getRowCount();i++)
        {
            bTree.addNode(Integer.parseInt((CDTable.getModel().getValueAt(i,6)).toString()),
                    CDTable.getModel().getValueAt(i,1).toString());
        }
    }
     */

    public void createAutomationConsole(AutomationConsole automationConsole)
    {
        JPanel root = automationConsole.getRootPanel();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public void createRetrieveAutomationConsole(CDModel selectedCD)
    {
        AutomationConsole ui = AutomationConsole.createRetrieveAutomationConsole(serverPort, selectedCD);
        createAutomationConsole(ui);
    }

    public void createReturnAutomationConsole(CDModel selectedCD)
    {
        AutomationConsole ui = AutomationConsole.createReturnAutomationConsole(serverPort, selectedCD);
        createAutomationConsole(ui);
    }

    public void createRemoveAutomationConsole(CDModel selectedCD)
    {
        AutomationConsole ui = AutomationConsole.createRemoveAutomationConsole(serverPort, selectedCD);
        createAutomationConsole(ui);
    }

    public void createAddAutomationConsole(CDModel selectedCD)
    {
        AutomationConsole ui = AutomationConsole.createAddAutomationConsole(serverPort, selectedCD);
        createAutomationConsole(ui);
    }

    private void createSortAutomationConsole(ArrayList<CDModel> cdsBySection, String section)
    {
        AutomationConsole ui = AutomationConsole.createSortAutomationConsole(serverPort, section, cdsBySection);
        createAutomationConsole(ui);
    }

    public void setServerPort(int serverPort)
    {
        this.serverPort = serverPort;
    }

    public void addToProcessLog(AutomationCommand command)
    {
        String processLogText = getProcessLogTextFromCommand(command);
        addToProcessLog(processLogText);
    }

    public void addToProcessLog(String log)
    {
        processLogHashSet.add(log);
        StringBuilder sb = new StringBuilder();
        String currentProcessLogText = ProcessLogTextArea.getText();
        sb.append(currentProcessLogText);
        sb.append("\n" + log);
        ProcessLogTextArea.setText(sb.toString());
    }

    public String getProcessLogFromString(String sendCommandType, int barcode)
    {
        String date = new SimpleDateFormat("dd/MM/yyyy - hh:mm a").format(new Date());
        String text = date + " - SENT - " + sendCommandType;
        if (barcode != -1)
        {
            text += " - " + barcode;
        }
        return text;
    }


    private String getProcessLogTextFromCommand(AutomationCommand command)
    {
        String date = new SimpleDateFormat("dd/MM/yyyy - hh:mm a").format(new Date());
        String commandTypeText = command.getCommandTypeText();
        String text = date + " - RCVD - " + commandTypeText;
        if (command.getCommandType() != AutomationCommand.CommandType.Sort)
        {
            text += " - " + command.getCd().barcode;
        }
        return text;
    }
}