<<<<<<< HEAD:src/AbaloneMenu.java
=======
package abalone;
>>>>>>> main:src/main/java/abalone/AbaloneMenu.java
//Bryan Floyd
//Abalone Menu

package src;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JFrame
{
<<<<<<< HEAD:src/AbaloneMenu.java
	private JPanel menuPanel = new JPanel();

	private JLabel titleLabel = new JLabel("ABALONE", SwingConstants.CENTER);
	private JButton[] menuButtons = new JButton[4];
	private JPanel[] buttonPanels = new JPanel[4];
	private JPanel buttonSectionPanel = new JPanel();

	private AbalonePanel abalonePanelS = new AbalonePanel(new AbaloneGraph(), true, false);
	private AbalonePanel abalonePanelM = new AbalonePanel(new AbaloneGraph(), false, false);
=======
	private static JPanel menuPanel;
	private JPanel LogPanel;
	private JPanel buttonSectionPanel;
	private AbalonePanel displayPanel = new AbalonePanel(new AbaloneGraph(), false, true);
	//private AbalonePanel abalonePanelD = new AbalonePanel(AbaloneGraphD, true);
	private AbalonePanel abalonePanelS;
	private AbalonePanel abalonePanelM;
	//private AbaloneGraph abaloneGraphD = new AbaloneGraoh();
	private AbaloneGraph abaloneGraphS;
	private AbaloneGraph abaloneGraphM;
	private RulesPanel rulesPanel;
	private JScrollPane rulesScrollPane;
	private JLabel title;
	private JButton spButton;
	private JButton mpButton;
	private JButton rulesButton;
	private JButton quitButton;
	private JButton backToMenuButton;
>>>>>>> main:src/main/java/abalone/AbaloneMenu.java
	private JMenuBar gameOptionsBar = new JMenuBar();
	private boolean spGameInProgress = false;
	private boolean mpGameInProgress = false;

	private RulesPanel rulesPanel = new RulesPanel();
	private JScrollPane rulesScrollPane = new JScrollPane(rulesPanel);

	private JPanel goodByePanel = new JPanel();
	private JLabel goodByeLabel = new JLabel("Thanks for playing!", SwingConstants.CENTER);
	
	private Color boardColorLight = new Color(160, 130, 105);
	private Color boardColorDark = new Color(75, 45, 30);

	//Constructor
	public AbaloneMenu()
	{			
		//Setting up panels and in-game menu bar
		menuPanelSetup();
		rulesPanelSetup();
		gameMenuSetup();
		goodByePanelSetup();

		//Setting up JFrame
		setContentPane(menuPanel);
		setTitle("Abalone");
		setSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-30));
		setMinimumSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/3), (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.85)));
		setBackground(boardColorLight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentListener(componentListener);
		setVisible(true);
	}
	//Main method
	public static void main(String[] args)
	{
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
	}
	
	//Set up main menu
	private void menuPanelSetup()
	{
		//Set menuPanel appearance
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBackground(boardColorLight);
		
		//Set titleLabel Appearance
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, getHeight()/10));
		titleLabel.setForeground(boardColorDark);

		//Instance of the game panel for display on the main menu
		AbalonePanel abalonePanelD = new AbalonePanel(new AbaloneGraph(), false, true);

		//Initialize main menu buttons
		menuButtons[0] = new JButton("Singleplayer");
		menuButtons[1] = new JButton("Multiplayer");
		menuButtons[2] = new JButton("Rules");
		menuButtons[3] = new JButton("Quit");
		for(int i=0; i<buttonPanels.length; i++)
		{
			menuButtons[i].addActionListener(actionListener);
			menuButtons[i].setForeground(Color.WHITE);
			menuButtons[i].setBackground(boardColorDark);
		}
		menuButtons[3].setForeground(Color.RED);
		
<<<<<<< HEAD:src/AbaloneMenu.java
		//Initialize panel the main menu buttons will go on
=======
		title = new JLabel("ABALONE", SwingConstants.CENTER);
		title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, getHeight()/10));
		title.setAlignmentX(CENTER_ALIGNMENT);
		
		spButton = new JButton("Singleplayer");
		mpButton = new JButton("Multiplayer");
		rulesButton = new JButton("Rules");
		quitButton = new JButton("Quit");
		
		title.setForeground(new Color(75, 45, 30));
		spButton.setForeground(Color.WHITE);
		mpButton.setForeground(Color.WHITE);
		rulesButton.setForeground(Color.WHITE);
		quitButton.setForeground(Color.RED);
		
		spButton.setBackground(new Color(75, 45, 30));
		mpButton.setBackground(new Color(75, 45, 30));
		rulesButton.setBackground(new Color(75, 45, 30));
		quitButton.setBackground(new Color(75, 45, 30));
		
		//Add title
		titlePanel.add(title);
		menuPanel.add(titlePanel, BorderLayout.NORTH);
		
		//Add picture of board
		menuPanel.add(displayPanel, BorderLayout.CENTER);
		
		//Add panels containing buttons, add spacers, add size bounds
		Dimension buttDimension = new Dimension(250, 40);
		
		buttonSectionPanel = new JPanel();
>>>>>>> main:src/main/java/abalone/AbaloneMenu.java
		buttonSectionPanel.setLayout(new BoxLayout(buttonSectionPanel, BoxLayout.PAGE_AXIS));
		buttonSectionPanel.setOpaque(false);

		//Add each main menu button to its own panel to make it center properly in BoxLayout
		//Set constant size for button panels
		//Add button panels to a panel to be added to the menu
		for(int i=0; i<buttonPanels.length; i++)
		{
			buttonPanels[i] = new JPanel();
			buttonPanels[i].setLayout(new BorderLayout());
			buttonPanels[i].setOpaque(false);
			buttonPanels[i].add(menuButtons[i]);
			buttonPanels[i].setMinimumSize(new Dimension(250, 40));
			buttonPanels[i].setMaximumSize(new Dimension(250, 40));

			buttonSectionPanel.add(buttonPanels[i]);
			buttonSectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
		}

		//Build main menu panel
		menuPanel.add(titleLabel, BorderLayout.NORTH);
		menuPanel.add(abalonePanelD, BorderLayout.CENTER);
		menuPanel.add(buttonSectionPanel, BorderLayout.SOUTH);
	}

	//Add additional button to return to previous panel
	private void rulesPanelSetup()
	{
		//Back button takes player from RulesPanel to their previous panel
		JButton rulesBackButton = new JButton("Back");
		rulesBackButton.setForeground(Color.RED);
		rulesBackButton.setBackground(boardColorDark);
		rulesBackButton.addActionListener(actionListener);
		rulesPanel.add(rulesBackButton);
	}
	
	//Set up JMenuBar to be displayed during game
	//ActionListener will add and remove JMenuBar as needed
	private void gameMenuSetup()
	{
		//JMenuBar components
		JMenu gameOptionsMenu = new JMenu("Options");
		JMenu gameQuitMenu = new JMenu("Quit to...");
		JMenuItem showRulesItem = new JMenuItem("Rules");
		JMenuItem gameResetItem = new JMenuItem("Reset Board");
		JMenuItem quitGameItem = new JMenuItem("Menu");
		JMenuItem quitProgramItem = new JMenuItem("Desktop");
		
		//Set JMenuBar appearance
		gameOptionsBar.setBackground(boardColorDark);
		gameOptionsMenu.setForeground(Color.WHITE);
		gameOptionsMenu.setBackground(boardColorDark);
		showRulesItem.setForeground(Color.WHITE);
		showRulesItem.setBackground(boardColorDark);
		gameResetItem.setForeground(Color.WHITE);
		gameResetItem.setBackground(boardColorDark);
		gameQuitMenu.setForeground(Color.RED);
		gameQuitMenu.setBackground(boardColorDark);
		gameQuitMenu.setOpaque(true);
		quitGameItem.setForeground(Color.RED);
		quitGameItem.setBackground(boardColorDark);
		quitProgramItem.setForeground(Color.RED);
		quitProgramItem.setBackground(boardColorDark);
		
		//Add Action Listeners to JMenuBar components
		quitGameItem.addActionListener(actionListener);
		quitProgramItem.addActionListener(actionListener);
		showRulesItem.addActionListener(actionListener);
		gameResetItem.addActionListener(actionListener);
		
		//Build JMenuBar
		gameOptionsBar.add(gameOptionsMenu);
		gameOptionsMenu.add(showRulesItem);
		gameOptionsMenu.add(gameResetItem);
		gameOptionsMenu.add(gameQuitMenu);
		gameQuitMenu.add(quitGameItem);
		gameQuitMenu.add(quitProgramItem);
	}

<<<<<<< HEAD:src/AbaloneMenu.java
	private void goodByePanelSetup()
=======
		RulesPanelSetup();
		GamePanelSetup();

		setName("MenuFrame");
		setContentPane(menuPanel);
		setTitle("Abalone");
		//setSize(screenSize);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension((int)(screenWidth/3), (int)(screenHeight*0.85)));
		setBackground(boardColorLight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args)
>>>>>>> main:src/main/java/abalone/AbaloneMenu.java
	{
		//Set goodByePanel appearance
		goodByePanel.setLayout(new BorderLayout());
		goodByePanel.setBackground(boardColorLight);
		
<<<<<<< HEAD:src/AbaloneMenu.java
		//Set goodByeLabel Appearance
		goodByeLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, getHeight()/10));
		goodByeLabel.setForeground(boardColorDark);

		//Instance of the game panel for display on goodByePanel
		AbalonePanel abalonePanelD = new AbalonePanel(new AbaloneGraph(), false, true);

		//Build goodByePanel
		goodByePanel.add(goodByeLabel, BorderLayout.NORTH);
		goodByePanel.add(abalonePanelD, BorderLayout.CENTER);
=======
		backToMenuButton = new JButton("Back");
		backToMenuButton.setForeground(Color.RED);
		backToMenuButton.setBackground(boardColorDark);
		backToMenuButton.addActionListener(MainListener);
		rulesScrollPane = new JScrollPane(rulesPanel);
		rulesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rulesScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		rulesPanel.add(backToMenuButton);
>>>>>>> main:src/main/java/abalone/AbaloneMenu.java
	}

	//Image wouldn't update until resizing the window, so I have it resize to the same size
	private void updateImage()
	{
		repaint();
		setSize(getWidth()-1, getHeight()-1);
		setSize(getWidth()+1, getHeight()+1);
	}
	
	//Action listener for buttons
	private ActionListener actionListener = new ActionListener()
	{ 
		Sound sound = new Sound();
		public void actionPerformed(ActionEvent actionEvent) 
	    {
<<<<<<< HEAD:src/AbaloneMenu.java
			//Sound plays whenever a button is pressed
=======
>>>>>>> main:src/main/java/abalone/AbaloneMenu.java
			sound.setFile(0);
        	sound.play();
			
			//"Singleplayer" button in main menu
			if (actionEvent.getActionCommand().equals("Singleplayer"))
			{
				setContentPane(abalonePanelS);
				setJMenuBar(gameOptionsBar);
				setTitle("Abalone: Singleplayer");
				spGameInProgress = true;
				updateImage();
			}
			//"Multiplayer" button in main menu
			else if (actionEvent.getActionCommand().equals("Multiplayer"))
			{
				setContentPane(abalonePanelM);
				setJMenuBar(gameOptionsBar);
				setTitle("Abalone: Multiplayer");
				mpGameInProgress = true;
				updateImage();
			}
			//"Rules" button found in main menu or in game panel JMenuBar
			else if (actionEvent.getActionCommand().equals("Rules"))
			{
				setContentPane(rulesScrollPane);
				setJMenuBar(null);
				setTitle("Abalone: Rules");
				updateImage();
			}
			//Main menu "quit" button or game panel JMenuBar "Quit to... Desktop" button
			//Displays goodByePanel for 2 seconds before closing program
			else if (actionEvent.getActionCommand().equals("Quit") || actionEvent.getActionCommand().equals("Desktop"))
			{
				setContentPane(goodByePanel);
				setJMenuBar(null);
				updateImage();

				Timer timer = new Timer(2000, new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
			//RulesPanel "Back" button
			else if (actionEvent.getActionCommand().equals("Back"))
			{
				if (spGameInProgress)
				{
					setContentPane(abalonePanelS);
					setJMenuBar(gameOptionsBar);
					setTitle("Abalone: Singleplayer");
				}
				else if (mpGameInProgress)
				{
					setContentPane(abalonePanelM);
					setJMenuBar(gameOptionsBar);
					setTitle("Abalone: Multiplayer");
				}
				else
				{
					setJMenuBar(null);
					setContentPane(menuPanel);
					setTitle("Abalone");
				}
				updateImage();
			}
			//"Quit to... Menu" button found in game panel JMenuBar
			else if (actionEvent.getActionCommand().equals("Menu"))
			{
				spGameInProgress = false;
				mpGameInProgress = false;
				setContentPane(menuPanel);
				setJMenuBar(null);
				setTitle("Abalone");
				updateImage();
			}
			//"Reset Board" button found in game panel JMenuBar
			//Resets whichever board is currently being displayed
			else if (actionEvent.getActionCommand().equals("Reset Board"))
			{
				if (spGameInProgress)
				{
					abalonePanelS = new AbalonePanel(new AbaloneGraph(), true, false);
					setContentPane(abalonePanelS);
				}
				if (mpGameInProgress)
				{
					abalonePanelM = new AbalonePanel(new AbaloneGraph(), false, false);
					setContentPane(abalonePanelM);
				}
				updateImage();
			}
	    }
	};

	//Component listener for JFrame
	private ComponentAdapter componentListener = new ComponentAdapter()
	{	
		//Resizes titleLabel and goodByeLabel as JFrame is resized
		public void componentResized(ComponentEvent ce)
		{
			titleLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, menuPanel.getHeight()/10));
			goodByeLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, getHeight()/10));
		}
	};
}