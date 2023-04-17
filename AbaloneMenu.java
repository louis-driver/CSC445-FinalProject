//Bryan Floyd
//Abalone Menu

//TODO Rescale components based on window size change
//TODO Add example images to rules panel
//TODO Add detection for rules window exit with x button, reformat longer strings, reduce default window size
//TODO Add multiplayer button function
//TODO Window size consistency between JFrames
//TODO fix fullscreen button component resize

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JPanel
{
	static JFrame MenuFrame;
	JFrame RulesFrame;
	JFrame GameFrame;
	JPanel MenuPanel;
	JPanel PicPanel;
	JPanel LogPanel;
	JPanel RulesPanel;
	JPanel ButtonSectionPanel;
	JLabel Title;
	JButton SPButton;
	JButton MPButton;
	JButton RulesButton;
	JButton QuitButton;
	Font TitleFont = new Font("Times New Roman", Font.ITALIC, 50);
	Font ButtonFont = new Font("Times New Roman", Font.ITALIC, this.getHeight()/20);
	
	Color BoardColorLight = new Color(160, 130, 105);
	Color BoardColorDark = new Color(75, 45, 30);

	boolean gameInProgress = false;
	boolean rulesFrameOpen = false;
	
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
		
		Title = new JLabel("ABALONE", SwingConstants.CENTER);
		Title.setFont(TitleFont);
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
		MenuPanel.add(PicPanel, BorderLayout.CENTER);
		
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
		MenuFrame.setSize(400, 500);
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
		MenuFrame.add(Box.createVerticalGlue());
		MenuFrame.add(AbaloneMenu);
		//MenuFrame.add(Box.createVerticalStrut(50));
		MenuFrame.setTitle("Abalone");
		MenuFrame.setMinimumSize(new Dimension(460,535));
		MenuFrame.setVisible(true);
		MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void RulesFrameSetup()
	{
		RulesFrame = new JFrame();
		RulesFrame.setName("RulesFrame");
		RulesFrame.setSize(675,MenuFrame.getHeight());
		RulesFrame.setTitle("Abalone: Rules");
		
		RulesPanel = new JPanel();
		RulesPanel.setLayout(new BoxLayout(RulesPanel, BoxLayout.PAGE_AXIS));
		RulesPanel.setBackground(BoardColorLight);
		RulesPanel.setPreferredSize(MenuFrame.getSize());
		
		JLabel rulesTitle = new JLabel("   RULES OF ABALONE");
		rulesTitle.setForeground(Color.BLACK);
		JLabel ruleLabel1 = new JLabel(" - Objective: Push your opponent's marbles off the board.\n" +
		" - Black moves first.\n" +
		" - You may move up to three adjacent pieces, positioned in a straight line, in one direction.");
		ruleLabel1.setForeground(Color.BLACK);
		JLabel ruleLabel2 = new JLabel(" - This direction can be either 'broadside' (parallel to the line the pieces create) or 'in-line' (perpendicular to the line).");
		ruleLabel2.setForeground(Color.BLACK);
		JLabel ruleLabel3 = new JLabel(" - TODO");
		ruleLabel3.setForeground(Color.BLACK);
		
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
		RulesPanel.add(BackToMenuButton);
		
		BackToMenuButton.addActionListener(MainListener);
		RulesFrame.addComponentListener(ComponentListener);
		
		RulesFrame.add(RulesPanel);
	}
	
	public void GameFrameSetup()
	{
		GameFrame = new JFrame();
		GameFrame.setName("GameFrame");
		AbaloneGraph AbaloneGraph = new AbaloneGraph();
		AbalonePanel AbalonePanel = new AbalonePanel(AbaloneGraph);

		//GameFrame.setLayout(new BorderLayout());
		GameFrame.setSize(AbalonePanel.getWidth(), AbalonePanel.getHeight());
		GameFrame.setSize(MenuFrame.getSize());
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
					
					MenuFrame.setVisible(false);
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

					MenuFrame.setVisible(false);
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
				//ComponentListener resets menu
			}
	    }
	};

		ComponentAdapter ComponentListener = new ComponentAdapter(){	
		public void componentShown(ComponentEvent ce)
		{

		}

		public void componentHidden(ComponentEvent ce)
		{
			if (ce.getComponent().getName() == "RulesFrame")
			{
				MenuFrame.setVisible(true);
				RulesButton.setForeground(Color.WHITE);
				RulesButton.setBackground(BoardColorDark);
				rulesFrameOpen = false;
			}
		}

		public void componentResized(ComponentEvent ce)
		{
			if (ce.getComponent().getName() == "MenuFrame")
			{
				System.out.println("Player resized MenuFrame to:" + MenuFrame.getSize());
				Title.setFont(new Font("Times New Roman", Font.ITALIC, MenuPanel.getHeight()/10));
				setPreferredSize(MenuFrame.getSize());
				MenuPanel.setPreferredSize(new Dimension(MenuFrame.getWidth(), MenuFrame.getHeight()-100));
			}
			
		}
	};
}
