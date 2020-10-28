// Ahror Abdulhamidov
// This is a Graphical User Interface Class

package sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIForm {

    // GUI components
    private JFrame frame;
    private JPanel panel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addPageButton;
    private JButton extractBestPageButton;
    private JButton showBestPageButton;
    private JButton increaseRankButton;
    private JScrollPane jScrollPane;
    private JButton makePriorityQueueButton;
    private JTextPane textPane;

    MaxHeapPriorityPageList pageList;  // ArrayList of Pages

    // Launches the GUI application, shows the main window
    public void launch() {
        frame = new JFrame("Search Engine Simulator");
        frame.setContentPane(new GUIForm().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Getter for the panel
    public JPanel getPanel() {
        return panel;
    }

    // Contains ActionListener methods for the buttons
    public GUIForm() {

        // Gets the keyword from the textfield, searches it with webcrawler, and heapsorts it
        // If an exception occurred, shows error message dialog
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    WebCrawler webCrawler = new WebCrawler(searchField.getText());
                    webCrawler.search();
                    pageList = new MaxHeapPriorityPageList(webCrawler.getUrls());
                    pageList.heapSort();
                    textPane.setText(pageList.toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Search Failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }

            }
        });

        // Asks the user for size and makes a priority queue of that size
        // If an exception occurred, shows error message dialog
        makePriorityQueueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int heapSize = Integer.parseInt(JOptionPane.showInputDialog("Specify the heap size."));
                    pageList.makePriorityQueueOfSize(heapSize);
                    textPane.setText(pageList.heapToString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "MakeHeapOfSize Failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        });

        // Asks user for the URL and 4 ranks of new Page and adds that Page to the priority queue
        // If an exception occurred, shows error message dialog
        addPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String link = JOptionPane.showInputDialog("Enter URL");
                    int r1 = Integer.parseInt(JOptionPane.showInputDialog("Enter first rank"));
                    int r2 = Integer.parseInt(JOptionPane.showInputDialog("Enter second rank"));
                    int r3 = Integer.parseInt(JOptionPane.showInputDialog("Enter third rank"));
                    int r4 = Integer.parseInt(JOptionPane.showInputDialog("Enter fourth rank"));
                    pageList.maxHeapInsert(link, r1, r2, r3, r4);
                    textPane.setText(pageList.heapToString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "MaxHeapInsert Failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        });

        // Asks the user which Page's ranks to change, then asks for the 4 ranks, and then updates the priority queue
        // If an exception occurred, shows error message dialog
        increaseRankButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = Integer.parseInt(JOptionPane.showInputDialog("Choose the page by its index"));
                    int r1 = Integer.parseInt(JOptionPane.showInputDialog("Enter first rank"));
                    int r2 = Integer.parseInt(JOptionPane.showInputDialog("Enter second rank"));
                    int r3 = Integer.parseInt(JOptionPane.showInputDialog("Enter third rank"));
                    int r4 = Integer.parseInt(JOptionPane.showInputDialog("Enter fourth rank"));
                    pageList.heapIncreaseKey(i, r1, r2, r3, r4);
                    textPane.setText(pageList.heapToString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "HeapIncreaseKey Failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        });

        // Pops up a message dialog with the highest ranked Page
        // If an exception occurred, shows error message dialog
        showBestPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(
                            frame,
                            pageList.heapMaximum(),
                            "Best Page",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "HeapMaximum Failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        });

        // Pops up a message dialog with the highest ranked Page and then extracts that Page from the priority queue
        // If an exception occurred, shows error message dialog
        extractBestPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(
                            frame,
                            pageList.heapExtractMaximum(),
                            "Best Page",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    textPane.setText(pageList.heapToString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "HeapExtractMaximum Failed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        });
    }

}
