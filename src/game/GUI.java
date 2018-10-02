package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;

/**
 * GUI Class
 *
 * @author asus
 *
 */
public class GUI extends JFrame {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8101732434076202546L;
    private JPanel panelBoard;
    private final JPanel panelBackground = new JPanel();
    private JPanel panelBackgroundBoard;
    private final JPanel[][] SQUARE = new JPanel[8][8];
    private final JPanel panelHistory = new JPanel();
    private JPanel panelBellow;
    private final JLabel labelInfo = new JLabel();
    private final JMenuBar menuBar = new JMenuBar();
    private JMenu menuGame, menuOptionss, menuAbout;
    private JMenuItem itemNew, itemExit;
    private JMenuItem itemUndo, itemHint;
    private JMenuItem itemAbout, itemRules;
    private final JTextArea textArea = new JTextArea(30, 20);
    private JLabel lbPieces;
    private final String colString = "abcdefgh";
    private JTable tabelLangkah;

    /**
     * Constructor GUI class
     */
    public GUI() {
        super();
        createGui();

        DragMouseAdapter dragMouse = new DragMouseAdapter();
        // addMouseListener(dragMouse);
        addMouseMotionListener(dragMouse);

        this.setIconImage(PiecesIcon.iconGame.getImage());
        setTitle("Java Checkers");// set title game
        setSize(750, 600); // setWindow size
        setResizable(false); // set resizable window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // this.addWindowListener(new WindowAdapter() {
        // public void windowClosing(WindowEvent e) {
        // onExitClicked();
        // }
        // });
        setLocation(new Point(250, 20)); // location to show this window
        setVisible(true); //
    } // end of constructor GUI class

    /**
     * Create game board
     */
    public void createSquare() {

        panelBackgroundBoard = new JPanel();
        panelBackgroundBoard.setLayout(new BorderLayout());
        panelBackgroundBoard.setBorder(new CompoundBorder(new EmptyBorder(8, 8, 8, 8), new LineBorder(Color.BLACK)));
        panelBackgroundBoard.setBackground(new Color(130, 100, 80));

        panelBoard = new JPanel();
        panelBoard.setPreferredSize(new Dimension(500, 500));
        panelBoard.setLayout(new GridLayout(8, 8));
        panelBoard.setBackground(Color.DARK_GRAY);
        for (int row = 0; row < SQUARE.length; row++) {
            for (int col = 0; col < SQUARE[row].length; col++) {

                JPanel cell = new JPanel();
                if ((row + col) % 2 == 1) {
                    cell.setBackground(new Color(99, 164, 54));
                    // cell.setBackground(Color.BLACK);
                }

                if ((row + col) % 2 == 0) {
                    cell.setBackground(new Color(247, 235, 164));
                    // cell.setBackground(Color.WHITE);
                }
                SQUARE[row][col] = cell;

                panelBoard.add(SQUARE[row][col]);
            } // end of for loop col
        } // end of for loop row

        JLabel lbHurufSelatan;
        JLabel lbAngkaBarat;
        JLabel lbAngkaTimur;
        JLabel lbHurufUtara;
        panelBackgroundBoard.add(new JLabel(""));

        // label kolom atas
        for (int i = 0; i < 8; i++) {
            lbHurufSelatan = new JLabel(colString.substring(i, i + 1));
            
            lbHurufSelatan.setForeground(Color.WHITE);
            lbHurufSelatan.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbHurufSelatan.setPreferredSize(new Dimension(500, 30));
            panelBackgroundBoard.add(lbHurufSelatan, BorderLayout.NORTH);
        } // end of for loop i

        // label baris kanan
        for (int j = 0; j < 8; j++) {
            lbAngkaBarat = new JLabel("" + (j + 1), SwingConstants.CENTER);
            lbAngkaBarat.setForeground(Color.WHITE);
            lbAngkaBarat.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbAngkaBarat.setPreferredSize(new Dimension(30, 500));
            panelBackgroundBoard.add(lbAngkaBarat, BorderLayout.WEST);
        } // end of for loop j

        // label kolom bawah
        for (int ii = 0; ii < 8; ii++) {
            lbHurufUtara = new JLabel(
                    "a           b           c           d           e            f           g           h",
                    SwingConstants.CENTER);
            lbHurufUtara.setForeground(Color.WHITE);
            lbHurufUtara.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbHurufUtara.setPreferredSize(new Dimension(500, 30));
            panelBackgroundBoard.add(lbHurufUtara, BorderLayout.SOUTH);

        } // end of for loop k

        // lebel baris timur
        for (int jj = 0; jj < 8; jj++) {
            lbAngkaTimur = new JLabel("" + (jj + 1), SwingConstants.CENTER);
            lbAngkaTimur.setForeground(Color.WHITE);
            lbAngkaTimur.setFont(new Font("Tahoma", Font.BOLD, 12));
            lbAngkaTimur.setPreferredSize(new Dimension(30, 500));
            panelBackgroundBoard.add(lbAngkaTimur, BorderLayout.EAST);
        } // end of for loop L

        panelBackgroundBoard.add(panelBoard, BorderLayout.CENTER);

        panelBackground.setLayout(new BorderLayout());
        panelBackground.add(panelBackgroundBoard, BorderLayout.CENTER);
    } // end of createSquare mathod

    // withWhiteText
    public final JLabel withWhiteText(JLabel label) {
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        return label;
    } // end of withWhiteText

    /**
     * Side panel to show move
     */
    public void addSidePanel() {
        textArea.setLineWrap(true);
        textArea.setCursor(new java.awt.Cursor(Cursor.TEXT_CURSOR));
        textArea.setBackground(new Color(204, 204, 204));
        textArea.setEditable(false);

        JScrollPane jScroll = new JScrollPane(textArea);
        int horizontalPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        jScroll.setHorizontalScrollBarPolicy(horizontalPolicy);

        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panelHistory.add(jScroll);
        panelHistory.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(153, 0, 0), 1, true),
                "Hostory ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 14),
                new Color(104, 105, 150)));
        panelHistory.setPreferredSize(new Dimension(240, 500));
        panelBackground.add(panelHistory, BorderLayout.EAST);
    } // end of addSidePanel method

    /**
     * Add pieces to the board
     */
    private void addPieces() {

        for (int row = 0; row < SQUARE.length; row++) {
            for (int col = 0; col < SQUARE[row].length; col++) {

                lbPieces = new JLabel();
                lbPieces.setHorizontalAlignment(SwingConstants.CENTER);
                //lb.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

                if ((row + col) % 2 == 1) {
                    if (row < 3) {
                        lbPieces.setIcon(PiecesIcon.redPiece);
                    } else if (row > 4) {
                        lbPieces.setIcon(PiecesIcon.whitePiece);
                    }
                }

                SQUARE[row][col].setLayout(new BorderLayout());
                SQUARE[row][col].add(lbPieces, BorderLayout.CENTER);
            } // end of for col loop
        } // end of for row loop
    } // end of addPieces method

    // Create South Panel to show message
    private void southPanel() {
        labelInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        labelInfo.setBackground(Color.GREEN);
        labelInfo.setAlignmentX(CENTER_ALIGNMENT);

        panelBellow = new JPanel();
        panelBellow.setBackground(new Color(204, 204, 204));
        panelBellow.setLayout(new BorderLayout());
        panelBellow.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelBellow.setPreferredSize(new Dimension(13, 30));
        panelBellow.add(labelInfo, BorderLayout.CENTER);
        panelBackground.add(panelBellow, BorderLayout.SOUTH);
    } // end of southPanel method

    private void createGui() {
        createSquare();
        addPieces();
        addSidePanel();
        southPanel();
        setJMenuBar(createMenuBar());
        add(panelBackground);
    }

    /**
     * Create JMenuBar
     *
     * @return menuBar value
     */
    private JMenuBar createMenuBar() {

        JSeparator separator1 = new JSeparator();

        itemNew = new JMenuItem("New Game");
        itemNew.setMnemonic(KeyEvent.VK_N);
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        itemNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                new PermainanBaru(GUI.this);
            }

        });

        itemExit = new JMenuItem("Exit");
        itemExit.setMnemonic('E');
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        itemExit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                onExitClicked();
            }

        });

        menuGame = new JMenu("Game");
        menuGame.add(itemNew);
        menuGame.add(separator1);
        menuGame.add(itemExit);

        itemUndo = new JMenuItem("Undo");
        itemUndo.setMnemonic('U');
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        itemUndo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub

            }

        });

        itemHint = new JMenuItem("Hint");
        itemHint.setMnemonic('H');
        itemHint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        itemHint.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub

            }

        });

        menuOptionss = new JMenu("Options");
        menuOptionss.add(itemUndo);
        menuOptionss.add(itemHint);

        itemRules = new JMenuItem("Game Rules");
        itemRules.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                onRulesClicked();
            }

        });

        itemAbout = new JMenuItem("About");
        itemAbout.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                onAboutClicked();
            }

        });
        menuAbout = new JMenu("About");
        menuAbout.add(itemRules);
        menuAbout.add(itemAbout);

        menuBar.add(menuGame);
        menuBar.add(menuOptionss);
        menuBar.add(menuAbout);

        return menuBar;
    } // end of createMenuBar method

    /**
     * exit action method
     */
    private void onExitClicked() {
        Object[] obj = {"Yes", "No"};
        int exit = JOptionPane.showOptionDialog(this, "Are you sure want to leave this game?", "Exit",
                JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, obj, obj[1]);
        if (exit == 0) {
            this.dispose();
            System.exit(0);
        } // end if
    } // end of onExitClicked method

    // to show rules of the game
    public void onRulesClicked() {
        JOptionPane.showMessageDialog(GUI.this, IfaceAbout.GAME_RULES, "RULES", JOptionPane.INFORMATION_MESSAGE);
    } // end of onRulesClicked method

    // to show ABOUT game
    public void onAboutClicked() {
        JOptionPane.showMessageDialog(GUI.this, IfaceAbout.ABOUT, "About", JOptionPane.INFORMATION_MESSAGE);
    } // // end of onAboutClicked method

    /**
     * Mouse adapter class
     */
    class DragMouseAdapter implements MouseMotionListener, MouseListener {

        int xAdjustment;
        int yAdjustment;
        int sx, sy;
        int dx, dy;

        int x, y;

        @Override
        public void mouseDragged(MouseEvent e) {
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }

    } // end of DragMouseAdapter class

    /**
     * new game class
     *
     * @author asus
     *
     */
    @SuppressWarnings("serial")
    static class PermainanBaru extends JDialog {

        private final ImageIcon img1 = new ImageIcon("src/images/iconGame.png");
        final GUI gui;

        private JRadioButton rbOnePlayer, rbTwoPlayer;
        private JLabel lb1, lb2, plSetting;
        private JPanel pn1, pn2;
        private JButton btPlay;
        private ButtonGroup bg;
        private JTextField txt1, txt2;

        // konstruktor inner class PermainanBaru
        PermainanBaru(final GUI gui) {
            super(gui);
            this.setTitle("New Game");
            this.gui = gui;
            setIconImage(img1.getImage());

            Container b = getContentPane();
            b.setLayout(null);

            // pemberian nama pada DialogBox pada permainan baru
            plSetting = new JLabel("Player Settings");
            plSetting.setFont(new Font("Vardana", Font.BOLD, 20));
            plSetting.setBounds(170, 40, 300, 25);
            b.add(plSetting);

            // TextField untuk input nama pemain satu
            txt1 = new JTextField(15);
            txt1.setEditable(false);
            txt1.setToolTipText("Enter the name of the player one");
            txt1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
            txt1.setBounds(180, 170, 200, 25);
            b.add(txt1);

            // TextField untuk input nama pemain dua
            txt2 = new JTextField(15);
            txt2.setEditable(false);
            txt2.setToolTipText("Enter the name of the player two");
            txt2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
            txt2.setBounds(180, 210, 200, 25);
            b.add(txt2);

            // radioButton untuk satu pemain
            rbOnePlayer = new JRadioButton("One Player");
            rbOnePlayer.setSelected(true);
            rbOnePlayer.setToolTipText("Choose this if you want to play against computer");
            rbOnePlayer.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    txt1.setEditable(false);
                    txt2.setEditable(false);
                }

            });

            // radioButton untuk dua pemain
            rbTwoPlayer = new JRadioButton("Two Player");
            rbTwoPlayer.setToolTipText("Choose this if you want to play against your friend");
            rbTwoPlayer.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    txt1.setEditable(true);
                    txt2.setEditable(true);
                }

            });

            bg = new ButtonGroup();
            bg.add(rbOnePlayer);
            bg.add(rbTwoPlayer);

            // panel untuk menampung radioButton pemain
            pn1 = new JPanel();
            // pn1.setBackground(Color.red);
            pn1.add(rbOnePlayer);
            pn1.add(rbTwoPlayer);
            pn1.setBounds(100, 100, 300, 40);
            b.add(pn1);

            // label nama pemain satu
            lb1 = new JLabel("Player1 : ");
            lb1.setBounds(120, 170, 200, 25);
            b.add(lb1);

            // label nama pemain dua
            lb2 = new JLabel("Player2 : ");
            lb2.setBounds(120, 210, 200, 25);
            b.add(lb2);

            pn2 = new JPanel();
            pn2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pn2.setBounds(100, 150, 300, 110);
            b.add(pn2);

            // Penggunaan Text Area
            btPlay = new JButton("Play");
            btPlay.setToolTipText("Press the play the play button to start the game");
            btPlay.setBounds(320, 280, 80, 40);
            btPlay.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                }

            });

            b.add(btPlay);

            this.setLocationByPlatform(true);
            this.pack();
            this.setLocationRelativeTo(null);
            this.setResizable(false);
            this.setSize(490, 400);
            this.setVisible(true);
        } // end of constructor PermainanBaru
    } // end of PermainanBaru Class
} // end of main Class
