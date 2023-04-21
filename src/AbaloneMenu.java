package src;
//Bryan Floyd
//Abalone Menu

//TODO Add example images to rules panel, finish rules
//TODO Possibly add options panel

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JFrame
{
	static JFrame MenuFrame;
	static JPanel MenuPanel;
	JPanel LogPanel;
	JPanel RulesPanel;
	JPanel ButtonSectionPanel;
	AbalonePanel AbalonePanelS;
	AbalonePanel AbalonePanelM;
	AbaloneGraph AbaloneGraphS;
	AbaloneGraph AbaloneGraphM;
	JLabel Title;
	JLabel ImageLabel;
	JLabel rulesTitle;
	JLabel[] ruleLabels;
	JButton SPButton;
	JButton MPButton;
	JButton RulesButton;
	JButton QuitButton;
	JMenuBar GameOptionsBar = new JMenuBar();
	JMenu GameOptionsMenu = new JMenu("Options");
	JMenuItem QuitGameItem = new JMenuItem("Quit Game");
	JMenuItem ShowRulesItem = new JMenuItem("Rules");
	
	
	static Color BoardColorLight = new Color(160, 130, 105);
	static Color BoardColorDark = new Color(75, 45, 30);
	static Dimension screenSize = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40);
	static int screenWidth = (int) screenSize.getWidth();
	static int screenHeight = (int) screenSize.getHeight();
	boolean SPGameInProgress = false;
	boolean MPGameInProgress = false;

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
		
		ImageIcon BoardImage = new ImageIcon("Resources/AbaloneBoard.png");
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
		
		//Add picture of board, add spacer
		MenuPanel.add(ImageLabel, BorderLayout.CENTER);
		
		//Add panels containing buttons, add spacers, add size bounds
		Dimension buttDimension = new Dimension(250, 40);
		
		ButtonSectionPanel = new JPanel();
		ButtonSectionPanel.setLayout(new BoxLayout(ButtonSectionPanel, BoxLayout.PAGE_AXIS));
		ButtonSectionPanel.setOpaque(false);

		SPBPanel.add(SPButton);
		ButtonSectionPanel.add(SPBPanel);
		SPBPanel.setMinimumSize(buttDimension);
		SPBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createRigidArea(new Dimension(50,10)));
		MPBPanel.add(MPButton);
		ButtonSectionPanel.add(MPBPanel);
		MPBPanel.setMinimumSize(buttDimension);
		MPBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createRigidArea(new Dimension(50,10)));
		RulesBPanel.add(RulesButton);
		ButtonSectionPanel.add(RulesBPanel);
		RulesBPanel.setMinimumSize(buttDimension);
		RulesBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createRigidArea(new Dimension(50,10)));
		QuitBPanel.add(QuitButton);
		ButtonSectionPanel.add(QuitBPanel);
		QuitBPanel.setMinimumSize(buttDimension);
		QuitBPanel.setMaximumSize(buttDimension);
		ButtonSectionPanel.add(Box.createVerticalGlue());
		ButtonSectionPanel.add(Box.createHorizontalGlue());

		MenuPanel.add(ButtonSectionPanel, BorderLayout.SOUTH);
		
		SPButton.addActionListener(MainListener);
		MPButton.addActionListener(MainListener);
		RulesButton.addActionListener(MainListener);
		QuitButton.addActionListener(MainListener);
		
		add(Box.createVerticalGlue());
		add(MenuPanel);
		add(Box.createVerticalStrut(50));
		RulesPanelSetup();
		//GamePanelSetup();

		GameOptionsBar.setBackground(BoardColorDark);
		GameOptionsBar.add(GameOptionsMenu);
		GameOptionsMenu.setForeground(Color.WHITE);
		GameOptionsMenu.setBackground(BoardColorDark);
		GameOptionsMenu.add(ShowRulesItem);
		GameOptionsMenu.add(QuitGameItem);
		QuitGameItem.setForeground(Color.WHITE);
		QuitGameItem.setBackground(BoardColorDark);
		QuitGameItem.setForeground(Color.RED);
		QuitGameItem.setBackground(BoardColorDark);
		ShowRulesItem.addActionListener(MainListener);
		QuitGameItem.addActionListener(MainListener);
		ShowRulesItem.addActionListener(MainListener);

		addComponentListener(ComponentListener);

		setName("MenuFrame");
		add(Box.createVerticalGlue());
		add(MenuPanel);
		setTitle("Abalone");
		setMinimumSize(new Dimension(screenWidth/3, (int) (screenHeight * 0.85)));
		setBackground(BoardColorLight);
		setSize(screenSize);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args)
	{
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
	}
	
	public void RulesPanelSetup()
	{
		RulesPanel = new JPanel();
		RulesPanel.setLayout(new BoxLayout(RulesPanel, BoxLayout.PAGE_AXIS));
		RulesPanel.setBackground(BoardColorLight);
		RulesPanel.setPreferredSize(getSize());
		
		rulesTitle = new JLabel(" RULES OF ABALONE");
		rulesTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, getHeight()/15));
		rulesTitle.setForeground(Color.BLACK);
		
		ruleLabels = new JLabel[5];
		ruleLabels[0] = new JLabel(" - Objective: Push your opponent's marbles off the board.");
		ruleLabels[1] = new JLabel(" - Black moves first.");
		ruleLabels[2] = new JLabel(" - You may move up to three adjacent pieces, positioned in a straight line, in one direction.");
		ruleLabels[3] = new JLabel(" - This direction can be either 'broadside' (parallel to the line the pieces create):");
		ruleLabels[4] = new JLabel(" - or 'in-line' (perpendicular to the line):");

		for(int i=0; i<ruleLabels.length; i++)
		{
			ruleLabels[i].setForeground(Color.BLACK);
			ruleLabels[i].setFont(new Font("Times New Roman", Font.ITALIC, MenuPanel.getHeight()/20));
		}
		
		JButton BackToMenuButton = new JButton("Back");
		BackToMenuButton.setForeground(Color.RED);
		BackToMenuButton.setBackground(BoardColorDark);

		JPanel RulesPicPanel1 = new JPanel();
		RulesPicPanel1.setBackground(BoardColorLight);
		ImageIcon RulesImage1 = new ImageIcon("Resources/AbaloneBoardSmall.png");
		JLabel RulesImg1 = new JLabel(RulesImage1);
		RulesPicPanel1.add(RulesImg1);
		
		RulesPanel.add(Box.createRigidArea(new Dimension(0,20)));
		RulesPanel.add(rulesTitle);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,10)));
		RulesPanel.add(RulesImg1);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabels[0]);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabels[1]);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabels[2]);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabels[3]);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabels[4]);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(BackToMenuButton);
		
		BackToMenuButton.addActionListener(MainListener);
	}
	
	//Leaving in case game progress should be saved
	public void GamePanelSetup()
	{
		//AbaloneGraphS = new AbaloneGraph();
		//AbalonePanelS = new AbalonePanel(AbaloneGraphS, true);
		//AbaloneGraphM = new AbaloneGraph();
		//AbalonePanelM = new AbalonePanel(AbaloneGraphM, false);
	}
	
	ActionListener MainListener = new ActionListener()
	{ 
		public void actionPerformed(ActionEvent actionEvent) 
	    {
			System.out.println("Player says " + actionEvent.getActionCommand());
			if (actionEvent.getActionCommand().equals("Singleplayer"))
			{
				AbaloneGraphS = new AbaloneGraph();
				AbalonePanelS = new AbalonePanel(AbaloneGraphS, true);
				remove(MenuPanel);
				remove(RulesPanel);
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
				AbaloneGraphM = new AbaloneGraph();
				AbalonePanelM = new AbalonePanel(AbaloneGraphM, false);
				remove(MenuPanel);
				remove(RulesPanel);
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
				add(RulesPanel);
				setTitle("Abalone: Rules");
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Quit"))
			{
				System.exit(0);
			}
			else if (actionEvent.getActionCommand().equals("Back"))
			{
				remove(RulesPanel);
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
				repaint();
				setSize(getWidth()-1, getHeight()-1);
				setSize(getWidth()+1, getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Quit Game"))
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
				remove(RulesPanel);
				setJMenuBar(null);
				add(MenuPanel);
				setTitle("Abalone");
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
			System.out.println("Player resized Frame to:" + getSize());
			Title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, MenuPanel.getHeight()/10));
			MenuPanel.setPreferredSize(new Dimension(getWidth(), getHeight()-90));
			//ImageLabel.setPreferredSize(new Dimension(getWidth()-50, getHeight()-50));
			rulesTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, getHeight()/15));

			for(int i=0; i<ruleLabels.length; i++)
			{
				ruleLabels[i].setFont(new Font("Times New Roman", Font.ITALIC, getHeight()/25));
			}
		}
	};
}
