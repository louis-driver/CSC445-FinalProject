//Bryan Floyd
//Abalone Menu

//TODO Add example images to rules panel, finish rules
//TODO Add RulesFrame scroll bar(s)
//TODO Possibly add options frame
//TODO Fix main menu ImageLabel scaling if possible

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JPanel
{
	private static JFrame MenuFrame;
	private JFrame RulesFrame;
	private JFrame GameFrame;
	private JPanel MenuPanel;
	private JPanel LogPanel;
	private JPanel RulesPanel;
	private JPanel ButtonSectionPanel;
	private AbalonePanel AbalonePanelS;
	private AbalonePanel AbalonePanelM;
	private AbaloneGraph AbaloneGraphS;
	private AbaloneGraph AbaloneGraphM;
	private JLabel Title;
	private JLabel ImageLabel;
	private JButton SPButton;
	private JButton MPButton;
	private JButton RulesButton;
	private JButton QuitButton;
	private Font TitleFont = new Font("Times New Roman", Font.ITALIC, 50);
	private Font ButtonFont = new Font("Times New Roman", Font.ITALIC, this.getHeight()/20);
	
	private Color BoardColorLight = new Color(160, 130, 105);
	private Color boardColorDark = new Color(75, 45, 30);
	private static int commonWindowHeight;
	private static int commonWindowWidth;
	
	public AbaloneMenu()
	{			
		setPreferredSize(MenuFrame.getSize());
		setBackground(new Color(160, 130, 105));
		
		MenuPanel = new JPanel();
		//MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.PAGE_AXIS));
		MenuPanel.setLayout(new BorderLayout());
		MenuPanel.setBackground(BoardColorLight);
		
		JPanel TitlePanel = new JPanel();
		TitlePanel.setBackground(BoardColorLight);
		JPanel SPBPanel = new JPanel();
		SPBPanel.setBackground(boardColorDark);
		SPBPanel.setLayout(new BorderLayout());
		JPanel MPBPanel = new JPanel();
		MPBPanel.setBackground(boardColorDark);
		MPBPanel.setLayout(new BorderLayout());
		JPanel RulesBPanel = new JPanel();
		RulesBPanel.setBackground(boardColorDark);
		RulesBPanel.setLayout(new BorderLayout());
		JPanel QuitBPanel = new JPanel();
		QuitBPanel.setBackground(boardColorDark);
		QuitBPanel.setLayout(new BorderLayout());
		
		ImageIcon BoardImage = new ImageIcon("Images/AbaloneBoardSmall.jpg");
		ImageLabel = new JLabel(BoardImage);
		
		Title = new JLabel("ABALONE", SwingConstants.CENTER);
		Title.setFont(TitleFont);
		Title.setAlignmentX(CENTER_ALIGNMENT);
		
		SPButton = new JButton("Singleplayer");
		MPButton = new JButton("Multiplayer");
		RulesButton = new JButton("Rules");
		QuitButton = new JButton("Quit");
		
		Title.setForeground(boardColorDark);
		SPButton.setForeground(Color.WHITE);
		MPButton.setForeground(Color.WHITE);
		RulesButton.setForeground(Color.WHITE);
		QuitButton.setForeground(Color.RED);
		
		SPButton.setBackground(boardColorDark);
		MPButton.setBackground(boardColorDark);
		RulesButton.setBackground(boardColorDark);
		QuitButton.setBackground(boardColorDark);
		
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
		RulesFrameSetup();
		GameFrameSetup();

		MenuFrame.addComponentListener(ComponentListener);
	}
	
	public static void main(String[] args)
	{
		MenuFrame = new JFrame();
		MenuFrame.setName("MenuFrame");
		commonWindowHeight = MenuFrame.getHeight();
		commonWindowWidth = MenuFrame.getWidth();
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
		MenuFrame.add(Box.createVerticalGlue());
		MenuFrame.add(AbaloneMenu);
		MenuFrame.setTitle("Abalone");
		MenuFrame.setMinimumSize(new Dimension(460,535));
		MenuFrame.setVisible(true);
		MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void RulesFrameSetup()
	{
		RulesFrame = new JFrame();
		RulesFrame.setName("RulesFrame");
		RulesFrame.setMinimumSize(new Dimension(535,535));
		RulesFrame.setTitle("Abalone: Rules");
		
		RulesPanel = new JPanel();
		RulesPanel.setLayout(new BoxLayout(RulesPanel, BoxLayout.PAGE_AXIS));
		RulesPanel.setBackground(BoardColorLight);
		RulesPanel.setPreferredSize(MenuFrame.getSize());
		
		JLabel rulesTitle = new JLabel(" RULES OF ABALONE");
		Font rulesTitleFont = new Font("Times New Roman", Font.ITALIC, 30);
		rulesTitle.setFont(rulesTitleFont);
		rulesTitle.setForeground(Color.BLACK);
		JLabel ruleLabel1 = new JLabel(" - Objective: Push your opponent's marbles off the board.");
		ruleLabel1.setForeground(Color.BLACK);
		JLabel ruleLabel2 = new JLabel(" - Black moves first.");
		ruleLabel2.setForeground(Color.BLACK);
		JLabel ruleLabel3 = new JLabel(" - You may move up to three adjacent pieces, positioned in a straight line, in one direction.");
		ruleLabel3.setForeground(Color.BLACK);
		JLabel ruleLabel4 = new JLabel(" - This direction can be either 'broadside' (parallel to the line the pieces create):");
		ruleLabel4.setForeground(Color.BLACK);
		JLabel ruleLabel5 = new JLabel(" - or 'in-line' (perpendicular to the line):");
		ruleLabel5.setForeground(Color.BLACK);
		
		JButton BackToMenuButton = new JButton("Back to Menu");
		BackToMenuButton.setForeground(Color.RED);
		BackToMenuButton.setBackground(boardColorDark);

		JPanel RulesPicPanel1 = new JPanel();
		RulesPicPanel1.setBackground(BoardColorLight);
		ImageIcon RulesImage1 = new ImageIcon("AbaloneBoardSmall.png");
		JLabel RulesImg1 = new JLabel(RulesImage1);
		RulesPicPanel1.add(RulesImg1);
		
		RulesPanel.add(Box.createRigidArea(new Dimension(0,20)));
		RulesPanel.add(rulesTitle);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,10)));
		RulesPanel.add(RulesImg1);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabel1);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabel2);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabel3);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabel4);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(ruleLabel5);
		RulesPanel.add(Box.createRigidArea(new Dimension(0,7)));
		RulesPanel.add(BackToMenuButton);
		
		BackToMenuButton.addActionListener(MainListener);
		RulesFrame.addComponentListener(ComponentListener);
		
		RulesFrame.add(RulesPanel);
	}
	
	public void GameFrameSetup()
	{
		GameFrame = new JFrame();
		GameFrame.setName("GameFrame");
		GameFrame.setMinimumSize(new Dimension(550,550));
		
		AbaloneGraph graphS = new AbaloneGraph();
		AbalonePanelS = new AbalonePanel(graphS, true);
		AbaloneGraph graphM = new AbaloneGraph();
		AbalonePanelM = new AbalonePanel(graphM, false);

		GameFrame.addComponentListener(ComponentListener);
	}
	
	ActionListener MainListener = new ActionListener()
	{ 
		public void actionPerformed(ActionEvent actionEvent) 
	    {
			System.out.println("Player says " + actionEvent.getActionCommand());
			if (actionEvent.getActionCommand().equals("Singleplayer"))
			{
				GameFrame.remove(AbalonePanelM);
				GameFrame.add(AbalonePanelS);
				GameFrame.setTitle("Abalone: Singleplayer");
				MenuFrame.setVisible(false);
				if (GameFrame.getMinimumSize().getWidth() <= commonWindowWidth && GameFrame.getMinimumSize().getHeight() <= commonWindowHeight)
				{
					GameFrame.setSize(commonWindowWidth, commonWindowHeight);
				}
				GameFrame.setVisible(true);
			}
			else if (actionEvent.getActionCommand().equals("Multiplayer"))
			{
				GameFrame.remove(AbalonePanelS);
				GameFrame.add(AbalonePanelM);
				GameFrame.setTitle("Abalone: Multiplayer");
				MenuFrame.setVisible(false);
				if (GameFrame.getMinimumSize().getWidth() <= commonWindowWidth && GameFrame.getMinimumSize().getHeight() <= commonWindowHeight)
				{
					GameFrame.setSize(commonWindowWidth, commonWindowHeight);
				}
				GameFrame.setVisible(true);
			}
			else if (actionEvent.getActionCommand().equals("Rules"))
			{
				MenuFrame.setVisible(false);
				if (RulesFrame.getMinimumSize().getWidth() <= commonWindowWidth && RulesFrame.getMinimumSize().getHeight() <= commonWindowHeight)
				{
					RulesFrame.setSize(new Dimension(commonWindowWidth, commonWindowHeight));
				}
				RulesFrame.setVisible(true);
			}
			else if (actionEvent.getActionCommand().equals("Quit"))
			{
				System.exit(0);
			}
			else if (actionEvent.getActionCommand().equals("Back to Menu"))
			{
				RulesFrame.setVisible(false);
				//ComponentListener resets menu
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
			if (ce.getComponent().getName() == "RulesFrame")
			{
				if (MenuFrame.getMinimumSize().getWidth() <= commonWindowWidth && MenuFrame.getMinimumSize().getHeight() <= commonWindowHeight)
				{
					MenuFrame.setSize(new Dimension(commonWindowWidth, commonWindowHeight));
				}
				MenuFrame.setVisible(true);

			}
			if (ce.getComponent().getName() == "GameFrame")
			{
				if (MenuFrame.getMinimumSize().getWidth() <= commonWindowWidth && MenuFrame.getMinimumSize().getHeight() <= commonWindowHeight)
				{
					MenuFrame.setSize(new Dimension(commonWindowWidth, commonWindowHeight));
				}
				MenuFrame.setVisible(true);

			}
		}

		public void componentResized(ComponentEvent ce)
		{
			if (ce.getComponent().getName() == "MenuFrame")
			{
				Title.setFont(new Font("Times New Roman", Font.ITALIC, MenuPanel.getHeight()/10));
				setPreferredSize(MenuFrame.getSize());
				commonWindowHeight = MenuFrame.getHeight();
				commonWindowWidth = MenuFrame.getWidth();
				MenuPanel.setPreferredSize(new Dimension(MenuFrame.getWidth(), MenuFrame.getHeight()-100));
				ImageLabel.setMinimumSize(new Dimension(MenuFrame.getWidth()-50, MenuFrame.getHeight()-50));
			}
			if (ce.getComponent().getName() == "GameFrame")
			{
				commonWindowHeight = GameFrame.getHeight();
				commonWindowWidth = GameFrame.getWidth();
			}
			if (ce.getComponent().getName() == "RulesFrame")
			{
				commonWindowHeight = RulesFrame.getHeight();
				commonWindowWidth = RulesFrame.getWidth();
			}
		}
	};
}
