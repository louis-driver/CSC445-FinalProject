//Bryan Floyd
//Abalone Menu

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JPanel
{
	static JFrame GUIFrame;
	JFrame RulesFrame;
	JFrame GameFrame;
	JPanel MenuPanel;
	JPanel PicPanel;
	JPanel LogPanel;
	JButton SPButton;
	JButton MPButton;
	JButton RulesButton;
	JButton QuitButton;
	
	Color BoardColorLight = new Color(160, 130, 105);
	Color BoardColorDark = new Color(75, 45, 30);

	boolean gameInProgress = false;
	boolean rulesFrameOpen = false;
	
	public AbaloneMenu()
	{			
		setPreferredSize(GUIFrame.getSize());
		setBackground(new Color(160, 130, 105));
		
		MenuPanel = new JPanel();
		MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.PAGE_AXIS));
		//MenuPanel.setPreferredSize(new Dimension(GUIFrame.getWidth(), GUIFrame.getHeight()+100));
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
		
		PicPanel = new JPanel();
		PicPanel.setBackground(BoardColorLight);
		ImageIcon BoardImage = new ImageIcon("AbaloneBoard.png");
		JLabel ImageLabel = new JLabel(BoardImage);
		PicPanel.add(ImageLabel);
		
		JLabel Title = new JLabel("ABALONE", SwingConstants.CENTER);
		Title.setAlignmentX(CENTER_ALIGNMENT);
		Title.setFont(Title.getFont().deriveFont(32f).deriveFont(1));
		MenuPanel.add(Box.createVerticalGlue());
		MenuPanel.add(Box.createHorizontalGlue());
		
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
		MenuPanel.add(TitlePanel);
		
		//Add picture of board, add spacer
		MenuPanel.add(PicPanel, BorderLayout.CENTER);
		MenuPanel.add(Box.createRigidArea(new Dimension(50,10)));
		
		//Add panels containing buttons, add spacers
		SPBPanel.add(SPButton);
		MenuPanel.add(SPBPanel);
		MenuPanel.add(Box.createRigidArea(new Dimension(50,10)));
		MPBPanel.add(MPButton);
		MenuPanel.add(MPBPanel);
		MenuPanel.add(Box.createRigidArea(new Dimension(50,10)));
		RulesBPanel.add(RulesButton);
		MenuPanel.add(RulesBPanel);
		MenuPanel.add(Box.createRigidArea(new Dimension(50,10)));
		QuitBPanel.add(QuitButton);
		MenuPanel.add(QuitBPanel);
		
		SPButton.addActionListener(MainListener);
		MPButton.addActionListener(MainListener);
		RulesButton.addActionListener(MainListener);
		QuitButton.addActionListener(MainListener);
		
		add(MenuPanel);
		RulesFrameSetup();
		GameFrameSetup();
	}
	
	public static void main(String[] args)
	{
		GUIFrame = new JFrame();
		GUIFrame.setSize(400, 500);
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
		GUIFrame.add(AbaloneMenu);
		GUIFrame.setTitle("Abalone");
		GUIFrame.setVisible(true);
		GUIFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void RulesFrameSetup()
	{
		RulesFrame = new JFrame();
		RulesFrame.setSize(675,GUIFrame.getHeight());
		RulesFrame.setTitle("Abalone: Rules");
		
		JPanel RulesPanel = new JPanel();
		RulesPanel.setLayout(new BoxLayout(RulesPanel, BoxLayout.PAGE_AXIS));
		RulesPanel.setBackground(BoardColorLight);
		RulesPanel.setPreferredSize(GUIFrame.getSize());
		
		JLabel rulesTitle = new JLabel("   RULES OF ABALONE:");
		rulesTitle.setForeground(Color.BLACK);
		JLabel ruleLabel1 = new JLabel(" - Objective: Push your opponent's marbles off the board.");
		ruleLabel1.setForeground(Color.BLACK);
		JLabel ruleLabel2 = new JLabel(" - Black moves first.");
		ruleLabel2.setForeground(Color.BLACK);
		JLabel ruleLabel3 = new JLabel(" - You may move up to three adjacent pieces, positioned in a straight line, in one direction.");
		ruleLabel3.setForeground(Color.BLACK);
		JLabel ruleLabel4 = new JLabel(" - This direction can be either 'broadside' (parallel to the line the pieces create) or 'in-line' (perpendicular to the line).");
		ruleLabel4.setForeground(Color.BLACK);
		JLabel ruleLabel5 = new JLabel(" - TODO");
		ruleLabel5.setForeground(Color.BLACK);
		
		JButton BackToMenuButton = new JButton("Back to Menu");
		BackToMenuButton.setForeground(Color.RED);
		BackToMenuButton.setBackground(BoardColorDark);

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
		RulesPanel.add(Box.createRigidArea(new Dimension(0,150)));
		RulesPanel.add(BackToMenuButton);
		
		BackToMenuButton.addActionListener(MainListener);
		
		RulesFrame.add(RulesPanel);
		RulesFrame.setUndecorated(true);
	}
	
	public void GameFrameSetup()
	{
		GameFrame = new JFrame();
		AbaloneGraph AbaloneGraph = new AbaloneGraph();
		AbalonePanel AbalonePanel = new AbalonePanel(AbaloneGraph);
		LogPanel = new JPanel();

		JTextArea TextLog = new JTextArea();
		TextLog.setEditable(false);
		LogPanel.add(TextLog);
		TextLog.append("Test");

		//GameFrame.setLayout(new BorderLayout());
		GameFrame.setSize(AbalonePanel.getWidth() + LogPanel.getWidth(), AbalonePanel.getHeight());
		GameFrame.setSize(GUIFrame.getSize());
		GameFrame.add(AbalonePanel);
		//GameFrame.add(LogPanel, BorderLayout.EAST);
		GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameFrame.setTitle("Abalone");
	}
	
	ActionListener MainListener = new ActionListener()
	{ 
		public void actionPerformed(ActionEvent actionEvent) 
	    {
			System.out.println("Player says " + actionEvent.getActionCommand());
			if (actionEvent.getActionCommand().equals("Singleplayer"))
			{
				if(gameInProgress)
				{
					System.out.println("Game already in progress");
				}
				else
				{
					SPButton.setForeground(Color.GRAY);
					SPButton.setBackground(Color.WHITE);
					
					GUIFrame.setVisible(false);
					GameFrame.setVisible(true);
					gameInProgress = true;
				}
			}
			else if (actionEvent.getActionCommand().equals("Multiplayer"))
			{
				
			}
			else if (actionEvent.getActionCommand().equals("Rules"))
			{
				if(rulesFrameOpen)
				{
					System.out.println("Rules window is already open");
				}
				else
				{
					RulesButton.setForeground(Color.GRAY);
					RulesButton.setBackground(Color.WHITE);

					RulesFrame.setVisible(true);
					rulesFrameOpen = true;
				}
			}
			else if (actionEvent.getActionCommand().equals("Quit"))
			{
				System.exit(0);
			}
			else if (actionEvent.getActionCommand().equals("Back to Menu"))
			{
				RulesFrame.setVisible(false);
				RulesButton.setForeground(Color.WHITE);
				RulesButton.setBackground(BoardColorDark);
				rulesFrameOpen = false;
			}
	    }
	};
}
