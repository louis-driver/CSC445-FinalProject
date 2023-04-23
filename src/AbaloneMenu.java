package src;
//Bryan Floyd
//Abalone Menu

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JFrame
{
	static JFrame MenuFrame;
	static JPanel MenuPanel;
	JPanel LogPanel;
	JPanel ButtonSectionPanel;
	private AbalonePanel displayPanel = new AbalonePanel(new AbaloneGraph(), false, true);
	AbalonePanel AbalonePanelS;
	AbalonePanel AbalonePanelM;
	AbaloneGraph AbaloneGraphS;
	AbaloneGraph AbaloneGraphM;
	RulesPanel RulesPanel;
	JScrollPane RulesScrollPane;
	JLabel Title;
	JLabel ImageLabel;
	JButton SPButton;
	JButton MPButton;
	JButton RulesButton;
	JButton QuitButton;
	JButton BackToMenuButton;
	JMenuBar GameOptionsBar = new JMenuBar();
	JMenu GameOptionsMenu = new JMenu("Options");
	JMenu GameQuitMenu = new JMenu("Quit to...");
	JMenuItem ShowRulesItem = new JMenuItem("Rules");
	JMenuItem GameResetItem = new JMenuItem("Reset Game");
	JMenuItem QuitGameItem = new JMenuItem("Menu");
	JMenuItem QuitProgramItem = new JMenuItem("Desktop");
	
	
	static Color BoardColorLight = new Color(160, 130, 105);
	static Color BoardColorDark = new Color(75, 45, 30);
	static Dimension screenSize = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40);
	static int screenWidth = (int) screenSize.getWidth();
	static int screenHeight = (int) screenSize.getHeight();
	boolean SPGameInProgress = false;
	boolean MPGameInProgress = false;
	Sound sound = new Sound();

	public AbaloneMenu()
	{			
		setPreferredSize(getSize());
		setBackground(new Color(160, 130, 105));
		
		MenuPanel = new JPanel();
		MenuPanel.setLayout(new BorderLayout());
		MenuPanel.setBackground(BoardColorLight);
		
		JPanel TitlePanel = new JPanel();
		TitlePanel.setBackground(BoardColorLight);
		JPanel SPBPanel = new JPanel();
		SPBPanel.setBackground(new Color(75, 45, 30));
		SPBPanel.setLayout(new BorderLayout());
		JPanel MPBPanel = new JPanel();
		MPBPanel.setBackground(new Color(75, 45, 30));
		MPBPanel.setLayout(new BorderLayout());
		JPanel RulesBPanel = new JPanel();
		RulesBPanel.setBackground(new Color(75, 45, 30));
		RulesBPanel.setLayout(new BorderLayout());
		JPanel QuitBPanel = new JPanel();
		QuitBPanel.setBackground(new Color(75, 45, 30));
		QuitBPanel.setLayout(new BorderLayout());
		
		ImageIcon BoardImage = new ImageIcon("./Resources/Images/MainMenuImage.png");
		ImageLabel = new JLabel(BoardImage);
		
		Title = new JLabel("ABALONE", SwingConstants.CENTER);
		Title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, getHeight()/10));
		Title.setAlignmentX(CENTER_ALIGNMENT);
		
		SPButton = new JButton("Singleplayer");
		MPButton = new JButton("Multiplayer");
		RulesButton = new JButton("Rules");
		QuitButton = new JButton("Quit");
		
		Title.setForeground(new Color(75, 45, 30));
		SPButton.setForeground(Color.WHITE);
		MPButton.setForeground(Color.WHITE);
		RulesButton.setForeground(Color.WHITE);
		QuitButton.setForeground(Color.RED);
		
		SPButton.setBackground(new Color(75, 45, 30));
		MPButton.setBackground(new Color(75, 45, 30));
		RulesButton.setBackground(new Color(75, 45, 30));
		QuitButton.setBackground(new Color(75, 45, 30));
		
		//Add title
		TitlePanel.add(Title);
		add(Box.createVerticalGlue());
		MenuPanel.add(TitlePanel, BorderLayout.NORTH);
		add(Box.createVerticalStrut(50));
		
		//Add picture of board
		MenuPanel.add(displayPanel, BorderLayout.CENTER);
		
		//Add panels containing buttons, add spacers, add size bounds
		Dimension buttDimension = new Dimension(250, 40);
		
		ButtonSectionPanel = new JPanel();
		ButtonSectionPanel.setLayout(new BoxLayout(ButtonSectionPanel, BoxLayout.PAGE_AXIS));
		ButtonSectionPanel.setOpaque(false);

		SPBPanel.add(SPButton);
		ButtonSectionPanel.add(SPBPanel);
		SPBPanel.setMinimumSize(buttDimension);
		SPBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
		MPBPanel.add(MPButton);
		ButtonSectionPanel.add(MPBPanel);
		MPBPanel.setMinimumSize(buttDimension);
		MPBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
		RulesBPanel.add(RulesButton);
		ButtonSectionPanel.add(RulesBPanel);
		RulesBPanel.setMinimumSize(buttDimension);
		RulesBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createRigidArea(new Dimension(0,10)));
		QuitBPanel.add(QuitButton);
		ButtonSectionPanel.add(QuitBPanel);
		QuitBPanel.setMinimumSize(buttDimension);
		QuitBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createRigidArea(new Dimension(0,7)));
		ButtonSectionPanel.add(Box.createVerticalGlue());
		ButtonSectionPanel.add(Box.createHorizontalGlue());

		MenuPanel.add(ButtonSectionPanel, BorderLayout.SOUTH);
		
		SPButton.addActionListener(MainListener);
		MPButton.addActionListener(MainListener);
		RulesButton.addActionListener(MainListener);
		QuitButton.addActionListener(MainListener);
		
		//add(Box.createVerticalGlue());
		add(MenuPanel);
		//add(Box.createVerticalStrut(50));

		GameOptionsBar.setBackground(BoardColorDark);
		GameOptionsMenu.setForeground(Color.WHITE);
		GameOptionsMenu.setBackground(BoardColorDark);
		ShowRulesItem.setForeground(Color.WHITE);
		ShowRulesItem.setBackground(BoardColorDark);
		GameResetItem.setForeground(Color.WHITE);
		GameResetItem.setBackground(BoardColorDark);
		GameQuitMenu.setForeground(Color.RED);
		GameQuitMenu.setBackground(BoardColorDark);
		GameQuitMenu.setOpaque(true);
		QuitGameItem.setForeground(Color.RED);
		QuitGameItem.setBackground(BoardColorDark);
		QuitProgramItem.setForeground(Color.RED);
		QuitProgramItem.setBackground(BoardColorDark);
		
		GameOptionsBar.add(GameOptionsMenu);
		GameOptionsMenu.add(ShowRulesItem);
		GameOptionsMenu.add(GameResetItem);
		GameOptionsMenu.add(GameQuitMenu);
		GameQuitMenu.add(QuitGameItem);
		GameQuitMenu.add(QuitProgramItem);
		
		QuitGameItem.addActionListener(MainListener);
		QuitProgramItem.addActionListener(MainListener);
		ShowRulesItem.addActionListener(MainListener);
		GameResetItem.addActionListener(MainListener);
		addComponentListener(ComponentListener);

		RulesPanelSetup();
		GamePanelSetup();

		setName("MenuFrame");
		add(MenuPanel);
		setTitle("Abalone");
		setSize(screenSize);
		setMinimumSize(new Dimension((int)(screenWidth/3), (int)(screenHeight*0.85)));
		setBackground(BoardColorLight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
	}
	
	public void RulesPanelSetup()
	{
		RulesPanel = new RulesPanel();
		
		BackToMenuButton = new JButton("Back");
		BackToMenuButton.setForeground(Color.RED);
		BackToMenuButton.setBackground(BoardColorDark);
		BackToMenuButton.addActionListener(MainListener);
		RulesScrollPane = new JScrollPane(RulesPanel);
		RulesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		RulesPanel.add(BackToMenuButton);
	}
	
	public void GamePanelSetup()
	{
		AbaloneGraphS = new AbaloneGraph();
		AbalonePanelS = new AbalonePanel(AbaloneGraphS, true, false);
		AbaloneGraphM = new AbaloneGraph();
		AbalonePanelM = new AbalonePanel(AbaloneGraphM, false, false);
	}
	
	ActionListener MainListener = new ActionListener()
	{ 
		public void actionPerformed(ActionEvent actionEvent) 
	    {
			System.out.println("Player says " + actionEvent.getActionCommand());
			sound.setFile(0);
        	sound.play();
			if (actionEvent.getActionCommand().equals("Singleplayer"))
			{
				remove(MenuPanel);
				remove(RulesScrollPane);
				add(AbalonePanelS);
				setJMenuBar(GameOptionsBar);
				setTitle("Abalone: Singleplayer");
				repaint();
				SPGameInProgress = true;
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Multiplayer"))
			{
				remove(MenuPanel);
				remove(RulesScrollPane);
				add(AbalonePanelM);
				setJMenuBar(GameOptionsBar);
				setTitle("Abalone: Multiplayer");
				repaint();
				MPGameInProgress = true;
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Rules"))
			{
				if (SPGameInProgress)
				{
					remove(AbalonePanelS);
				}
				else if (MPGameInProgress)
				{
					remove(AbalonePanelM);
				}
				remove(MenuPanel);
				setJMenuBar(null);
				add(RulesScrollPane);
				setTitle("Abalone: Rules");
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Quit") || actionEvent.getActionCommand().equals("Desktop"))
			{
				if (SPGameInProgress)
				{
					remove(AbalonePanelS);
					setJMenuBar(null);
					SPGameInProgress = false;
				}
				if (MPGameInProgress)
				{
					remove(AbalonePanelM);
					setJMenuBar(null);
					MPGameInProgress = false;
				}
				remove(RulesScrollPane);
				add(MenuPanel);
				MenuPanel.remove(ButtonSectionPanel);
				Title.setText("Thanks for playing!");

				
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
				if (SPGameInProgress)
				{
					setJMenuBar(GameOptionsBar);
					add(AbalonePanelS);
					setTitle("Abalone: Singleplayer");
				}
				else if (MPGameInProgress)
				{
					setJMenuBar(GameOptionsBar);
					add(AbalonePanelM);
					setTitle("Abalone: Multiplayer");
				}
				else
				{
					setJMenuBar(null);
					add(MenuPanel);
					setTitle("Abalone");
				}
				remove(RulesScrollPane);
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Menu"))
			{
				if (SPGameInProgress)
				{
					remove(AbalonePanelS);
					SPGameInProgress = false;
				}
				if (MPGameInProgress)
				{
					remove(AbalonePanelM);
					MPGameInProgress = false;
				}
				
				remove(RulesScrollPane);
				setJMenuBar(null);
				add(MenuPanel);
				setTitle("Abalone");
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Reset Game"))
			{
				if (SPGameInProgress)
				{
					remove(AbalonePanelS);
					AbaloneGraphS = new AbaloneGraph();
					AbalonePanelS = new AbalonePanel(AbaloneGraphS, true, false);
					add(AbalonePanelS);
				}
				if (MPGameInProgress)
				{
					remove(AbalonePanelM);
					AbaloneGraphM = new AbaloneGraph();
					AbalonePanelM = new AbalonePanel(AbaloneGraphM, false, false);
					add(AbalonePanelM);
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
			Title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, MenuPanel.getHeight()/10));
			MenuPanel.setPreferredSize(new Dimension(getWidth(), getHeight()-90));
		}
	};
}
