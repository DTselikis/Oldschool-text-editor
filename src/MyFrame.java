import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


/**
 *  <p>
 *  Creates a new JFrame and fill it with components.
 *  The base layout manager is BorderLayout (default of JFrame).
 *  To make a more complex layout for the need of this exercise
 *  we make a new Panel object and put the buttons inside of it.
 *  We also add a border for more visual appealing.
 *  Then we put this Panel at the North coordination of Border Layout
 *  and the JTextArea at the Center of the JFrame.
 *  We create the classes's attributes at a global in-class scope
 *  so every method can perform changes on them.
 *  <p>
 *  For each button, we create a new ImageIcon, a ToolTipText
 *  and also both a MouseListener and an ActionListener.
 *  Mouse event (which is handled by a new class) is responsible
 *  of changing the background color of the button that the mouse
 *  have entered and exited, giving visual feedback to user.
 *  Action events is handled via lambda expressions that calling
 *  a class method, for code simplicity. Although not all operations
 *  needed a method, we create one to be consistent.
 *  We eliminate the borders of buttons and zero their margins
 *  to achieve a good UI.
 *  Because the class attributes has global in-class scope
 *  we can create the Objects inside the methods and still
 *  save the new Object.
 *  <p>
 *  For each MenuItem we add an ActionLister and use lambda expressions
 *  to give them functionality. These lambda expression call the same
 *  methods as the corresponding button, so in case a change is needed,
 *  we only need to change the method's code making the code more
 *  maintainable.
 *  <p>
 *  At the JTextArea we add a vertical and a horizontal JScrollPane
 *  in case the content is larger than area's dimensions. Scroll bars
 *  will only appear if needed. We also add a KeyListener with an anonymous
 *  KeyAdapter that will help us determine if changes was made to the
 *  content of JTextArea that eventually will cause the appearance of a different
 *  message during program termination.
 *  <p>
 *  We finally add a WindowListener to this JFrame with an
 *  anonymous WindowAdapter that will handle the exit procedure.
 *
 *  @see <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html">Lambda Expressions</a>
 *
 *  @author Tselikis Dimitrios
 *  @version 1.0
 */

public class MyFrame extends JFrame {

    private File opendFile;
    private JTextArea contentsTA;

    private JButton newBtn;
    private JButton openBtn;
    private JButton saveBtn;
    private JButton copyBtn;
    private JButton clearBtn;
    private JButton statisticsBtn;
    private JButton exitBtn;

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenuItem newMenuItem;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem copyMenuItem;
    private JMenuItem clearMenuItem;
    private JMenuItem statisticsMenuItem;
    private JMenuItem exitMenuItem;

    private Image newImage;
    private Image openImage;
    private Image saveImage;
    private Image copyImage;
    private Image clearImage;
    private Image statisticsImage;
    private Image exitImage;
    private Image  frameImage;

    private boolean hasModified;

    /**
     * Constructor.
     * Creates the components of JFrame and add functionality to them.
     *
     * @param windowTitle the frame's title
     * @since 1.0
     */
    public MyFrame(String windowTitle) {
        super(windowTitle); // Passing frame's title to parent's constructor

        // ##### Creating Menu objects #####
        menuBar = new JMenuBar();
        menuBar.add(fileMenu = new JMenu("File"));
        menuBar.add(editMenu = new JMenu("Edit"));
        fileMenu.add(newMenuItem = new JMenuItem("New"));
        fileMenu.add(openMenuItem = new JMenuItem("Open"));
        fileMenu.add(saveMenuItem = new JMenuItem("Save"));
        fileMenu.add(copyMenuItem = new JMenuItem("Copy"));
        fileMenu.add(statisticsMenuItem = new JMenuItem("Statistics"));
        editMenu.add(clearMenuItem = new JMenuItem("Clear"));
        fileMenu.add(exitMenuItem = new JMenuItem("Exit"));


        // ##### Adding functionality to MenuItems #####
        newMenuItem.addActionListener(event -> newOperation());
        openMenuItem.addActionListener(event -> openOperation());
        saveMenuItem.addActionListener(event -> saveOperation(0));
        copyMenuItem.addActionListener(event -> saveOperation(1));
        clearMenuItem.addActionListener(event -> clearOperation());
        statisticsMenuItem.addActionListener(event -> statistics());
        exitMenuItem.addActionListener(event -> exitOperation());

        // ##### Creating JPanel and adding borders #####
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        btnPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        // ##### Create new Images that will be uses as ImageIcon for buttons #####
        // try block is required because reading from disk may fail
        // Icons are located in folder 'icons' that is market as
        // resources root.
        try {
            newImage = ImageIO.read(getClass().getResource("new_icon.png"));
            openImage = ImageIO.read(getClass().getResource("open_icon.png"));
            saveImage = ImageIO.read(getClass().getResource("save_icon.png"));
            copyImage = ImageIO.read(getClass().getResource("copy_icon.png"));
            clearImage = ImageIO.read(getClass().getResource("clear_icon.png"));
            statisticsImage = ImageIO.read(getClass().getResource("statistics_icon.png"));
            exitImage = ImageIO.read(getClass().getResource("exit_icon.png"));
            frameImage = ImageIO.read(getClass().getResource("editor_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ##### Creating JButtons and ToolTipText for each one #####
        btnPanel.add(newBtn = new JButton(new ImageIcon(newImage)));
        newBtn.setMargin(new Insets(0,0,0,100));
        newBtn.setBorder(null);
        newBtn.setToolTipText("New");
        btnPanel.add(openBtn = new JButton(new ImageIcon(openImage)));
        openBtn.setMargin(new Insets(0,0,0,0));
        openBtn.setBorder(null);
        openBtn.setToolTipText("Open");
        btnPanel.add(saveBtn = new JButton(new ImageIcon(saveImage)));
        saveBtn.setMargin(new Insets(0,0,0,0));
        saveBtn.setBorder(null);
        saveBtn.setToolTipText("Save");
        btnPanel.add(copyBtn = new JButton(new ImageIcon(copyImage)));
        copyBtn.setMargin(new Insets(0,0,0,0));
        copyBtn.setBorder(null);
        copyBtn.setToolTipText("Copy");
        btnPanel.add(clearBtn = new JButton(new ImageIcon(clearImage)));
        clearBtn.setMargin(new Insets(0,0,0,0));
        clearBtn.setBorder(null);
        clearBtn.setToolTipText("Clear");
        btnPanel.add(statisticsBtn = new JButton(new ImageIcon(statisticsImage)));
        statisticsBtn.setMargin(new Insets(0,0,0,0));
        statisticsBtn.setBorder(null);
        statisticsBtn.setToolTipText("Statistics");
        btnPanel.add(exitBtn = new JButton(new ImageIcon(exitImage)));
        exitBtn.setMargin(new Insets(0,0,0,0));
        exitBtn.setBorder(null);
        exitBtn.setToolTipText("Exit");

        // ##### Adding MouseListener to each JButton #####
        // Mouse events will be handled by ButtonHandler class
        newBtn.addMouseListener(new ButtonHandler());
        openBtn.addMouseListener(new ButtonHandler());
        saveBtn.addMouseListener(new ButtonHandler());
        copyBtn.addMouseListener(new ButtonHandler());
        clearBtn.addMouseListener(new ButtonHandler());
        statisticsBtn.addMouseListener(new ButtonHandler());
        exitBtn.addMouseListener(new ButtonHandler());

        // ##### Adding ActionListener to each JButton ######
        // We use lambda expressions to handle each button functionality
        newBtn.addActionListener(event -> newOperation());
        openBtn.addActionListener(event -> openOperation());
        saveBtn.addActionListener(event -> saveOperation(0));
        copyBtn.addActionListener(event -> saveOperation(1));
        clearBtn.addActionListener(event -> clearOperation());
        statisticsBtn.addActionListener(event -> statistics());
        exitBtn.addActionListener(event -> exitOperation());

        // ##### Creating JTextArea and adding border ######
        contentsTA = new JTextArea();
        contentsTA.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        contentsTA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!hasModified) {
                    modification();
                }

            }
        });

        // ##### Creating scroll pane for TextArea #####
        // We ensure that user can navigate to the entire length
        // of the content
        JScrollPane scrollPane = new JScrollPane(contentsTA);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // ##### Adding components to the frame #####
        // First we add the panel with the buttons at the top of the frame
        // and then the text area at the center of the frame. Because we don't have
        // anything else at the left, right all bottom of frame (BorderLayout)
        // text area will fill the remaining space
        this.setJMenuBar(menuBar);
        this.add(btnPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setIconImage(frameImage); // Changes the frame's icon


        // ##### Adding WindowListener to handle the termination process #####
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitOperation();
            }
        });

        // Attributes that will help with various operations
        opendFile = null;
        hasModified = false;
    }

    /**
     * Functionality of the "New" button and menuItem
     * Creates a new File based on the path and file name
     * user provided via JFileChooser. Also replaces the
     * frame's title with the path of the file.
     * In case the file cannot be created, a message informs the user.
     *
     * @since 1.0
     */
    private void newOperation() {
        int result;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Normal text file", "txt"));

        result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get path of file that user created and also adding the extension
            opendFile = new File(fileChooser.getSelectedFile().getPath() + ".txt");

            try {
                opendFile.createNewFile();
                this.setTitle(opendFile.getAbsolutePath());
                contentsTA.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error at creating file.\nPlease try again",
                        "File error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }

    }

    /**
     * Functionality of the "Clear" button and menuItem
     * Set the contains of the textArea to an empty sting,
     * essentially "clearing" the area.
     * Used as a separated method for code maintenance.
     *
     * @since 1.0
     */
    private void clearOperation() {
        contentsTA.setText("");
    }

    /**
     * Functionality of the textArea's keyListener
     * Make's program aware that modification indeed made
     * and also appends an asterisk at the end of the
     * opened file's name to inform user that there's
     * unsaved work.
     */
    private void modification() {
        hasModified = true;
        this.setTitle(this.getTitle() + '*');
    }


    /**
     * Functionality of the "Open" button and menuItem
     * Opens an existing file and fill the contents of textArea
     * with the contents of the file. Also replaces the
     * frame's title with the path of the file.
     *
     * In case the file cannot be found or read the user will be informed
     *
     * @since 1.0
     */
    private void openOperation() {
        int result;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Normal text file", "txt"));

        result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            opendFile = new File(fileChooser.getSelectedFile().getPath());
            String tmpStr;
            StringBuilder fileText = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(opendFile));
                while((tmpStr = reader.readLine()) != null) {
                    fileText.append(tmpStr);
                    fileText.append(System.lineSeparator());
                }
                reader.close();
                this.setTitle(opendFile.getAbsolutePath());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "File not found!",
                        "File error",
                        JOptionPane.INFORMATION_MESSAGE
                );

                opendFile = null;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error at opening file.\nPlease try again",
                        "File error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
            contentsTA.setText(fileText.toString());
        }
    }

    /**
     * Functionality of the "Save" and "Copy" button and menuItem
     * Based on the button or menuItem clicked, function performs
     * two similar operations. Also replaces the
     * frame's title with the path of the file.
     * <p>
     * <b><i>option = 0</i></b> indicates that user wants to save the contents of textArea
     * to the current opened file.<br>
     * <b><i>option = 1</i></b> indicates that user want to create a copy of the current file,
     * so this method will function as a "Save As" operation.
     * <p>
     * In both cases, we check if there is an opened file at the moment of
     * saving the contents. If a file is not opened (opendFile has <code>null</code>),
     * then both "Save" and "Save As" operations functions as a "Save"
     * action which also performs for the first time, so user must specify a new file
     * for the contents to be written.
     * <p>
     * If file pointed by user cannot be opened then we are using
     * <code>return</code> to terminate the function as it cannot
     * operate without an opened file.
     * In case a new file is opened the program is informed so
     * any changes by the user will affect this file.
     * <p>
     * In case the file cannot be found or read the user will be informed
     * @param option specifies if the method will function as
     *               an "Save" or "Save As" operation
     *
     * @since 1.0
     */
    private void saveOperation(int option) {
        // Will operate as "Save As" operation
        // or as a first time "Save" operation
        // TODO see if user has aldeady gave an extension
        // TODO set null if file does not open
        // TODO see if there is unsaved work during opening
        if (option == 1 || opendFile == null) {
            int result;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Normal text file", "txt"));

            result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                opendFile = new File(fileChooser.getSelectedFile().getPath() + ".txt");
            }
            else {
                JOptionPane.showMessageDialog(
                        null,
                        "No file was selected. Save canceled!",
                        "File error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(opendFile));
            String[] lines = contentsTA.getText().trim().split("\\n");
            StringBuilder contents = new StringBuilder();

            for (int i = 0; i < lines.length; i++) {
                contents.append(lines[i]).append(System.lineSeparator());
            }

            writer.write(contents.toString());


            writer.close();
            this.setTitle(opendFile.getAbsolutePath());
            hasModified = false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error at writing to file.\nPlease try again",
                    "File error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Functionality of the "Statistics" button and menuItem
     * Performs various statistics calculations based on the exercise
     * <p>
     * To calculate the number of <b><i>words</i></b> we get
     * the contents of the textArea as a String and we split it
     * with space as delimiter (continues spaces included).
     * We store the result in an Array of strings,
     *  where every element is a word. If the first element
     * is empty, then no elements was inserted inside the Array, thus
     * the textArea was empty.
     * <p>
     * To calculate the number of <b><i>characters</i></b> we get the
     * length of the Array that holds the contents of the textArea.
     * <p>
     * To calculate the number of characters <b><i>without spaces</i></b>
     * we remove all spaces from textAreas contents, get the length of it
     * and subtract it from the length of the original textArea content. The
     * resulted number will be the number of spaces, which we again subtract\
     * from the length of the original content of textArea.
     * <p>
     * To calculate the number of <b><i>paragraphs</i></b> we follow
     * the same approach as with words and we define as paragraph a string
     * that ends with the character '\n' followed by an unknown number
     * of '\n', which indicates the empty lines after this string.
     * <p>
     *  We use a StringBuilder to build a string with all these information
     *  and display them via a MessageDialog to user.
     *
     * @since 1.0
     */
    private void statistics() {
        String tmpStr = contentsTA.getText().trim();
        String[] words = tmpStr.split("\\s+");
        String[] paragraphs = tmpStr.split("\\n\\n+");
        StringBuilder info = new StringBuilder();
        int numOfSpaces;


        info.append("Words: ").append((words[0].isEmpty() ? 0 : words.length)).append(System.lineSeparator());
        info.append("Characters: ").append(contentsTA.getText().length()).append(System.lineSeparator());
        numOfSpaces = contentsTA.getText().length() - contentsTA.getText().replaceAll(" ", "").length();
        info.append("Characters (no spaces): ").append(Math.abs(contentsTA.getText().length() - numOfSpaces)).append(System.lineSeparator());
        info.append("Paragraphs: ").append((paragraphs[0].isEmpty()) ? 0 : paragraphs.length).append(System.lineSeparator());

        if (opendFile != null) {
            info.append("File size: ").append(opendFile.length()).append(" bytes").append(System.lineSeparator());
        }

        JOptionPane.showMessageDialog(
                null,
                info.toString(),
                "Statistics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Functionality of the "exit" button, menuItem and WindowListener
     * Determines the message that will display to user and also the
     * operations that will executed during exiting.
     * <p>
     * Method uses the "hasModified" attribute to determine
     * the message that will be displayed. In case the "hasModified"
     * attribute contains the value true means that there is
     * unsaved contents, so we warn the user and ask him if he want
     * to save them.
     *
     * @since 1.0
     */
    private void exitOperation() {
        int result;
        if (hasModified && !contentsTA.getText().equals("")) {
            result = JOptionPane.showConfirmDialog(
                    null,
                    "There is unsaved changes. Do you want to save them?",
                    "Exit confirmation",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                saveOperation(1);
                if (!hasModified) {
                    System.exit(0);
                }
                // If the saveOperation is success then the value of
                // "hasModified" will change to false. But in case the
                // method fails, the value will be true, so the program
                // will not terminate
            }
            else if (result == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
        else {
            result = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit program?",
                    "Exit confirmation",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }


}
