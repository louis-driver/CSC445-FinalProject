package src;
//Bryan Floyd
//Abalone Menu

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JFrame
{
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
	private JMenuBar gameOptionsBar = new JMenuBar();
	private JMenu gameOptionsMenu = new JMenu("Options");
	private JMenu gameQuitMenu = new JMenu("Quit to...");
	private JMenuItem showRulesItem = new JMenuItem("Rules");
	private JMenuItem gameResetItem = new JMenuItem("Reset Game");
	private JMenuItem quitGameItem = new JMenuItem("Menu");
	private JMenuItem quitProgramItem = new JMenuItem("Desktop");
	
	
	private static Color boardColorLight = new Color(160, 130, 105);
	private static Color boardColorDark = new Color(75, 45, 30);
	private static Dimension screenSize = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40);
	private static int screenWidth = (int) screenSize.getWidth();
	private static int screenHeight = (int) screenSize.getHeight();
	private boolean spGameInProgress = false;
	private boolean mpGameInProgress = false;
	private Sound sound = new Sound();

	public AbaloneMenu()
	{			
		setPreferredSize(getSize());
		setBackground(new Color(160, 130, 105));
		
		menuPanel = new JPanel();
		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBackground(boardColorLight);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(boardColorLight);
		JPanel spBPanel = new JPanel();
		spBPanel.setBackground(new Color(75, 45, 30));
		spBPanel.setLayout(new BorderLayout());
		JPanel mpBPanel = new JPanel();
		mpBPanel.setBackground(new Color(75, 45, 30));
		mpBPanel.setLayout(new BorderLayout());
		JPanel rulesBPanel = new JPanel();
		rulesBPanel.setBackground(new Color(75, 45, 30));
		rulesBPanel.setLayout(new BorderLayout());
		JPanel quitBPanel = new JPanel();
		quitBPanel.setBackground(new Color(75, 45, 30));
		quitBPanel.setLayout(new BorderLayout());
		
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
		buttonSectionPanel.setLayout(new BoxLayout(buttonSectionPanel, BoxLayout.PAGE_AXIS));
		buttonSectionPanel.setOpaque(false);

		spBPanel.add(spButton);
		buttonSectionPanel.add(spBPanel);
		spBPanel.setMinimumSize(buttDimension);
		spBPanel.setMaximumSize(buttDimension);
		buttonSectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
		mpBPanel.add(mpButton);
		buttonSectionPanel.add(mpBPanel);
		mpBPanel.setMinimumSize(buttDimension);
		mpBPanel.setMaximumSize(buttDimension);
		buttonSectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
		rulesBPanel.add(rulesButton);
		buttonSectionPanel.add(rulesBPanel);
		rulesBPanel.setMinimumSize(buttDimension);
		rulesBPanel.setMaximumSize(buttDimension);
		buttonSectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
		quitBPanel.add(quitButton);
		buttonSectionPanel.add(quitBPanel);
		quitBPanel.setMinimumSize(buttDimension);
		quitBPanel.setMaximumSize(buttDimension);
		buttonSectionPanel.add(Box.createRigidArea(new Dimension(0,7)));
		buttonSectionPanel.add(Box.createVerticalGlue());
		buttonSectionPanel.add(Box.createHorizontalGlue());

		menuPanel.add(buttonSectionPanel, BorderLayout.SOUTH);
		
		spButton.addActionListener(MainListener);
		mpButton.addActionListener(MainListener);
		rulesButton.addActionListener(MainListener);
		quitButton.addActionListener(MainListener);
		
		add(menuPanel);

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
		
		gameOptionsBar.add(gameOptionsMenu);
		gameOptionsMenu.add(showRulesItem);
		gameOptionsMenu.add(gameResetItem);
		gameOptionsMenu.add(gameQuitMenu);
		gameQuitMenu.add(quitGameItem);
		gameQuitMenu.add(quitProgramItem);
		
		quitGameItem.addActionListener(MainListener);
		quitProgramItem.addActionListener(MainListener);
		showRulesItem.addActionListener(MainListener);
		gameResetItem.addActionListener(MainListener);
		addComponentListener(ComponentListener);

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
	{
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
	}
	
	public void RulesPanelSetup()
	{
		rulesPanel = new RulesPanel();
		
		backToMenuButton = new JButton("Back");
		backToMenuButton.setForeground(Color.RED);
		backToMenuButton.setBackground(boardColorDark);
		backToMenuButton.addActionListener(MainListener);
		rulesScrollPane = new JScrollPane(rulesPanel);
		rulesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rulesScrollPane.getVerticalScrollBar().setUnitIncrement(15);
		rulesPanel.add(backToMenuButton);
	}
	
	public void GamePanelSetup()
	{
		abaloneGraphS = new AbaloneGraph();
		abalonePanelS = new AbalonePanel(abaloneGraphS, true, false);
		abaloneGraphM = new AbaloneGraph();
		abalonePanelM = new AbalonePanel(abaloneGraphM, false, false);
	}
	
	ActionListener MainListener = new ActionListener()
	{ 
		public void actionPerformed(ActionEvent actionEvent) 
	    {
			//System.out.println("Player says " + actionEvent.getActionCommand());
			sound.setFile(0);
        	sound.play();
			if (actionEvent.getActionCommand().equals("Singleplayer"))
			{
				setContentPane(abalonePanelS);
				setJMenuBar(gameOptionsBar);
				setTitle("Abalone: Singleplayer");
				repaint();
				spGameInProgress = true;
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Multiplayer"))
			{
				setContentPane(abalonePanelM);
				setJMenuBar(gameOptionsBar);
				setTitle("Abalone: Multiplayer");
				repaint();
				mpGameInProgress = true;
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Rules"))
			{
				setJMenuBar(null);
				setContentPane(rulesScrollPane);
				setTitle("Abalone: Rules");
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Quit") || actionEvent.getActionCommand().equals("Desktop"))
			{
				setContentPane(menuPanel);
				setJMenuBar(null);
				menuPanel.remove(buttonSectionPanel);
				title.setText("Thanks for playing!");

				
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);

				Timer timer = new Timer(3000, new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				timer.setRepeats(false);
				timer.start();
			}
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
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Menu"))
			{
				if (spGameInProgress)
				{
					spGameInProgress = false;
				}
				if (mpGameInProgress)
				{
					mpGameInProgress = false;
				}
				
				setContentPane(menuPanel);
				setJMenuBar(null);
				setTitle("Abalone");
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Reset Game"))
			{
				if (spGameInProgress)
				{
					abaloneGraphS = new AbaloneGraph();
					abalonePanelS = new AbalonePanel(abaloneGraphS, true, false);
					setContentPane(abalonePanelS);
				}
				if (mpGameInProgress)
				{
					abaloneGraphM = new AbaloneGraph();
					abalonePanelM = new AbalonePanel(abaloneGraphM, false, false);
					setContentPane(abalonePanelM);
				}
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
	    }
	};

	ComponentAdapter ComponentListener = new ComponentAdapter()
	{	
		public void componentShown(ComponentEvent ce)
		{

		}

		public void componentHidden(ComponentEvent ce)
		{

		}

		public void componentResized(ComponentEvent ce)
		{
			title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, menuPanel.getHeight()/10));
			menuPanel.setPreferredSize(new Dimension(getWidth(), getHeight()-90));
		}
	};
}
