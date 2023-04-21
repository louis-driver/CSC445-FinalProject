//Bryan Floyd
//Abalone Menu

//TODO Add example images to rules panel, finish rules
//TODO Possibly add options panel

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AbaloneMenu extends JPanel
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
	
	
	static Color BoardColorLight = new Color(160, 130, 105);
	static Color BoardColorDark = new Color(75, 45, 30);
	static Dimension screenSize = new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()-40);
	static int screenWidth = (int) screenSize.getWidth();
	static int screenHeight = (int) screenSize.getHeight();

	public AbaloneMenu()
	{			
		setPreferredSize(MenuFrame.getSize());
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
		
		ImageIcon BoardImage = new ImageIcon("AbaloneBoard.png");
		ImageLabel = new JLabel(BoardImage);
		
		Title = new JLabel("ABALONE", SwingConstants.CENTER);
		Title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, MenuFrame.getHeight()/10));
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
		GamePanelSetup();

		GameOptionsBar.setBackground(BoardColorDark);
		GameOptionsMenu.setForeground(Color.WHITE);
		GameOptionsMenu.setBackground(BoardColorDark);
		GameOptionsBar.add(GameOptionsMenu);
		GameOptionsMenu.add(QuitGameItem);
		QuitGameItem.setForeground(Color.RED);
		QuitGameItem.setBackground(BoardColorDark);
		GameOptionsMenu.addActionListener(MainListener);
		QuitGameItem.addActionListener(MainListener);

		MenuFrame.addComponentListener(ComponentListener);
	}
	
	public static void main(String[] args)
	{
		MenuFrame = new JFrame();
		MenuFrame.setName("MenuFrame");
		AbaloneMenu AbaloneMenu = new AbaloneMenu();
		MenuFrame.add(Box.createVerticalGlue());
		MenuFrame.add(MenuPanel);
		MenuFrame.setTitle("Abalone");
		MenuFrame.setMinimumSize(new Dimension(460, 600));
		MenuFrame.setBackground(BoardColorLight);
		MenuFrame.setSize(screenSize);
		MenuFrame.setVisible(true);
		MenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void RulesPanelSetup()
	{
		RulesPanel = new JPanel();
		RulesPanel.setLayout(new BoxLayout(RulesPanel, BoxLayout.PAGE_AXIS));
		RulesPanel.setBackground(BoardColorLight);
		RulesPanel.setPreferredSize(MenuFrame.getSize());
		
		rulesTitle = new JLabel(" RULES OF ABALONE");
		rulesTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, MenuFrame.getHeight()/15));
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
				MenuFrame.remove(MenuPanel);
				MenuFrame.remove(RulesPanel);
				MenuFrame.add(AbalonePanelS);
				MenuFrame.setJMenuBar(GameOptionsBar);
				MenuFrame.setTitle("Abalone: Singleplayer");
				MenuFrame.repaint();
				MenuFrame.setSize(MenuFrame.getWidth()-1, MenuFrame.getHeight()-1);
				MenuFrame.setSize(MenuFrame.getWidth()+1, MenuFrame.getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Multiplayer"))
			{
				AbaloneGraphM = new AbaloneGraph();
				AbalonePanelM = new AbalonePanel(AbaloneGraphM, false);
				MenuFrame.remove(MenuPanel);
				MenuFrame.remove(RulesPanel);
				MenuFrame.add(AbalonePanelM);
				MenuFrame.setJMenuBar(GameOptionsBar);
				MenuFrame.setTitle("Abalone: Multiplayer");
				MenuFrame.repaint();
				MenuFrame.setSize(MenuFrame.getWidth()-1, MenuFrame.getHeight()-1);
				MenuFrame.setSize(MenuFrame.getWidth()+1, MenuFrame.getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Rules"))
			{
				MenuFrame.remove(MenuPanel);
				MenuFrame.setJMenuBar(null);
				MenuFrame.add(RulesPanel);
				MenuFrame.setTitle("Abalone: Rules");
				MenuFrame.repaint();
				MenuFrame.setSize(MenuFrame.getWidth()-1, MenuFrame.getHeight()-1);
				MenuFrame.setSize(MenuFrame.getWidth()+1, MenuFrame.getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Quit"))
			{
				System.exit(0);
			}
			else if (actionEvent.getActionCommand().equals("Back to Menu"))
			{
				MenuFrame.remove(RulesPanel);
				MenuFrame.setJMenuBar(null);
				MenuFrame.add(MenuPanel);
				MenuFrame.setTitle("Abalone");
				MenuFrame.repaint();
				MenuFrame.setSize(MenuFrame.getWidth()-1, MenuFrame.getHeight()-1);
				MenuFrame.setSize(MenuFrame.getWidth()+1, MenuFrame.getHeight()+1);
			}
			else if (actionEvent.getActionCommand().equals("Quit Game"))
			{
				Component currentComponent = MenuFrame.getContentPane().getComponents()[1];
				if(currentComponent == AbalonePanelS)
				{
					MenuFrame.remove(AbalonePanelS);
				}
				if(currentComponent == AbalonePanelM)
				{
					MenuFrame.remove(AbalonePanelM);
				}
				MenuFrame.remove(RulesPanel);
				MenuFrame.setJMenuBar(null);
				MenuFrame.add(MenuPanel);
				MenuFrame.setTitle("Abalone");
				MenuFrame.repaint();
				MenuFrame.setSize(MenuFrame.getWidth()-1, MenuFrame.getHeight()-1);
				MenuFrame.setSize(MenuFrame.getWidth()+1, MenuFrame.getHeight()+1);
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
			System.out.println("Player resized Frame to:" + MenuFrame.getSize());
			Title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, MenuPanel.getHeight()/10));
			MenuPanel.setPreferredSize(new Dimension(MenuFrame.getWidth(), MenuFrame.getHeight()-90));
			ImageLabel.setPreferredSize(new Dimension(MenuFrame.getWidth()-50, MenuFrame.getHeight()-50));
			rulesTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, MenuFrame.getHeight()/15));

			for(int i=0; i<ruleLabels.length; i++)
			{
				ruleLabels[i].setFont(new Font("Times New Roman", Font.ITALIC, MenuFrame.getHeight()/25));
			}
		}
	};
}
