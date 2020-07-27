package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.print.Book;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;

public class MainUI {
    private JPanel RootPanel;
    private JLabel WindowTitle;
    private JLabel SearchLbl;
    private JTextField Searchtxtfld;
    private JButton SearchBtn;
    private JTable ShowTable;
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


    public MainUI() {
        createTable(); }

    public JPanel getRootPanel() {
        return RootPanel;
    }

    private void createTable() {
        ShowTable.setModel(new DefaultTableModel(
                null,
                new String[]{"Title", "Author", "Section", "X", "Y", "Barcode", "Description", "OnLoan"}
        ));
    }
    public static class ReadFile
    {
        public void main(String[] args) throws Exception
        {
            File file = new File("C:\\Users\\Gauge\\Desktop\\Semster 2\\Java2_Mark\\Student\\Assessment\\Sample_CD_Archive_Data");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) !=null)
            {
                String[] data = st.split(";");
                Book book = createBook(data);
                System.out.print(st);
            }
        }

        private static ArrayList<Book> getBooksFromFile(String filePath) throws Exception
        {
            File file = new File("C:\\Users\\Gauge\\Desktop\\Semster 2\\Java2_Mark\\Student\\Assessment\\Sample_CD_Archive_Data");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) !=null)
            {
                String[] data = st.split(";");
                Book book = createBook(data);
                System.out.print(st);
            }
        }

        private static Book createBook(String[] data)
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

            return new Book(id, title, author, section, x, y, barcode, description, onLoan);
        }


    }
}

