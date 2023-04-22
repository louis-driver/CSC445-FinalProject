import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentEvent;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// A class to display the abalone rules in a JPanel

public class RulesPanel extends JPanel
{
    JScrollPane scrollPane;
    JFrame frame;
    JPanel rules = new JPanel();
    String[] ruleStrings;
    JLabel[] ruleLabels;
	private Font titleFont = new Font("Times New Roman", Font.ITALIC, 50);
	private Font headerFont = new Font("Times New Roman", Font.BOLD, 30);
	private Font textFont = new Font("Times New Roman", Font.PLAIN, 20);
	
	private Color BoardColorLight = new Color(160, 130, 105);
	private Color boardColorDark = new Color(75, 45, 30);
    
    //Test Main class
    public static void main(String[] args)
    {
        
        int frameWidth = 1000;
        int frameHeight = 800;
        JFrame frame = new JFrame();
        RulesPanel panel = new RulesPanel(frame);

        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Rules");
        frame.add(panel);
        
		panel.setSize(frame.getSize());
        panel.updateGraphics();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    public RulesPanel(JFrame frame)
    {
        this.frame = frame;
		rules.setLayout(new BoxLayout(rules, BoxLayout.Y_AXIS));
		rules.setBackground(BoardColorLight);
		
		JLabel rulesTitle = new JLabel(" RULES OF ABALONE");
		Font rulesTitleFont = new Font("Times New Roman", Font.ITALIC, 30);
		rulesTitle.setFont(titleFont);
		rulesTitle.setForeground(Color.BLACK);
        /*
		JLabel ruleLabel1 = new JLabel(" - Objective: Push 6 of your opponent's marbles off the board.");
		ruleLabel1.setForeground(Color.BLACK);
		JLabel ruleLabel2 = new JLabel(" - Black moves first.");
		ruleLabel2.setForeground(Color.BLACK);
		JLabel ruleLabel3 = new JLabel(" - You may move up to three adjacent pieces, positioned in a straight line, in one direction.");
		ruleLabel3.setForeground(Color.BLACK);
		JLabel ruleLabel4 = new JLabel(" - This direction can be either 'broadside' (parallel to your line of selected pieces):");
		ruleLabel4.setForeground(Color.BLACK);
		JLabel ruleLabel5 = new JLabel(" - or 'in-line' (perpendicular to the line):");
		ruleLabel5.setForeground(Color.BLACK);
		JLabel ruleLabel6 = new JLabel("How to Move");
        ruleLabel6.setFont(headerFont);
		ruleLabel6.setForeground(Color.BLACK);
		
		rules.add(Box.createRigidArea(new Dimension(0,20)));
		rules.add(rulesTitle);
		rules.add(Box.createRigidArea(new Dimension(0,10)));
		rules.add(RulesImg1);
		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(ruleLabel1);
		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(ruleLabel2);
		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(ruleLabel3);
		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(ruleLabel4);
		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(ruleLabel5);
		rules.add(Box.createRigidArea(new Dimension(0,7)));
        rules.add(ruleLabel6);
		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(BackToMenuButton);
        */

        
		
		JButton BackToMenuButton = new JButton("Back to Menu");
		BackToMenuButton.setForeground(Color.RED);
		BackToMenuButton.setBackground(boardColorDark);

		JPanel RulesPicPanel1 = new JPanel();
		RulesPicPanel1.setBackground(BoardColorLight);
		ImageIcon RulesImage1 = new ImageIcon("AbaloneBoardSmall.png");
		JLabel RulesImg1 = new JLabel(RulesImage1);
		RulesPicPanel1.add(RulesImg1);

        rules.add(Box.createRigidArea(new Dimension(0,20)));
		rules.add(rulesTitle);

        setRuleStrings();
        createRuleLabels(ruleStrings);

		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(BackToMenuButton);
        //scrollPane = new JScrollPane(rules);
        this.add(rules);
        this.addComponentListener(componentListener);
    }

    private void createRuleLabels(String[] strings)
    {
        ruleLabels = new JLabel[strings.length];
        for (int i = 0; i < strings.length; ++i)
        {
            rules.add(Box.createRigidArea(new Dimension(0,10)));
		    ruleLabels[i] = new JLabel(strings[i]);
		    ruleLabels[i].setForeground(Color.BLACK);
            if (strings[i].length() < 30)
                ruleLabels[i].setFont(headerFont);
            else    
                ruleLabels[i].setFont(textFont);
            rules.add(ruleLabels[i]);
        }
    }

    private void updateFonts()
    {
        titleFont = new Font("Times New Roman", Font.ITALIC, frame.getHeight()/15);
        headerFont = new Font("Times New Roman", Font.BOLD, frame.getHeight()/25);
        textFont = new Font("Times New Roman", Font.PLAIN, frame.getHeight()/40);
    }

    private void updateLabels()
    {
        updateFonts();
        for (int i = 0; i < ruleLabels.length; ++i)
        {
            if (ruleStrings[i].length() < 30)
                ruleLabels[i].setFont(headerFont);
            else    
                ruleLabels[i].setFont(textFont);
        }
    }

    private void setRuleStrings()
    {
        ruleStrings = new String[16];
		ruleStrings[0] = "   General ";
		ruleStrings[1] = "      - Your objective is to push 6 of your opponent's marbles off the board.";
		ruleStrings[2] = "      - You may move up to three adjacent pieces, positioned in a straight line, in one direction.";
		ruleStrings[3] = "      - This direction can be either 'broadside' (parallel to your line of selected pieces).";
		ruleStrings[4] = "      - or 'inline' (perpendicular to the line).";
		ruleStrings[5] = "   How to Move";
		ruleStrings[6] = "       - For an inline move, left-click the piece at the beginning of the line you wish to move.";
		ruleStrings[7] = "       - Then right-click the board space that neighbors the selected piece in the direction you wish to move";
        ruleStrings[8] = "       - For a broadside move, select two or three pieces that are in a continuous line.";
        ruleStrings[9] = "       - Then right-click on a space neighboring either end of the selected line of pieces.";
        ruleStrings[10] = "       - NOTE: You should not right-click spaces that border the middle of the selected line of pieces.";
        ruleStrings[11] = "           because the selected space provides an ambiguous direction to move in, will be choosen for you";
        ruleStrings[12] = "   Pushing Your Opponent";
        ruleStrings[13] = "       - To push an opponent's piece(s) simply make an inline move in which your pieces outnumber your opponent's.";
        ruleStrings[14] = "       - Simply remember: 3 pushes 2 & 2 pushes 1";
        ruleStrings[15] = "       - Given that 1 single piece can't push another (as 1 is not greater than 1), broadside moves can't push opponents";
    }

    public void updateGraphics()
    {
        System.out.println(rules.getParent().getSize());
        rules.setPreferredSize(rules.getParent().getSize());
        this.revalidate();
        this.repaint();
    }

    ComponentAdapter componentListener = new ComponentAdapter()
    {	
        public void componentResized(ComponentEvent ce)
        {
            {
                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();
                //ruleTitle.setFont(new Font("Times New Roman", Font.ITALIC, MenuPanel.getHeight()/10));
                setPreferredSize(frame.getSize());
                rules.setPreferredSize(new Dimension(frameWidth, frameHeight));
                updateLabels();
                revalidate();
                //ImageLabel.setMinimumSize(new Dimension(MenuFrame.getWidth()-50, MenuFrame.getHeight()-50));
            }
        }
    };
}
