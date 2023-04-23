import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

// A class to display the abalone rules in a JPanel

public class RulesPanel extends JPanel
{
    JScrollPane scrollPane;
    JFrame frame;
    JPanel rules = new JPanel();
    JButton menuButton;
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
        
        int frameWidth = 12000;
        int frameHeight = 800;
        JFrame frame = new JFrame();
		JButton menuBtn = new JButton("Back to Menu");
        RulesPanel panel = new RulesPanel(frame, menuBtn);

        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Rules");
        frame.add(panel);
        
		panel.setSize(frame.getSize());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    public RulesPanel(JFrame frame, JButton toMenuBtn)
    {
        this.frame = frame;
        menuButton = toMenuBtn;
		rules.setLayout(new BoxLayout(rules, BoxLayout.Y_AXIS));
		rules.setBackground(BoardColorLight);
		
		JLabel rulesTitle = new JLabel(" RULES OF ABALONE");
		rulesTitle.setFont(titleFont);
		rulesTitle.setForeground(Color.BLACK);

		toMenuBtn.setForeground(Color.white);
		toMenuBtn.setBackground(Color.RED);

        rules.add(Box.createRigidArea(new Dimension(0,20)));
		rules.add(rulesTitle);

        setRuleStrings();
        createLabels(ruleStrings);

		rules.add(Box.createRigidArea(new Dimension(0,7)));
		rules.add(toMenuBtn);
        //scrollPane = new JScrollPane(this);
        //scrollPane.setEnabled(true);
        //scrollPane.createHorizontalScrollBar();
        this.add(rules);
    }


    private void createLabels(String[] strings)
    {
        ruleLabels = new JLabel[strings.length];
        for (int i = 0; i < strings.length; ++i)
        {
            //Image Path found
            if (strings[i].charAt(0) == '.')
            {
                ImageIcon rulesImage = new ImageIcon(strings[i]);
                ruleLabels[i] = new JLabel(rulesImage);
                ruleLabels[i].setMaximumSize(new Dimension(350, 350));

            }
            //Text Labels
            else
            {
                rules.add(Box.createRigidArea(new Dimension(0,10)));
                ruleLabels[i] = new JLabel(strings[i]);
                ruleLabels[i].setForeground(Color.BLACK);
                if (strings[i].length() < 30)
                    ruleLabels[i].setFont(headerFont);
                else    
                    ruleLabels[i].setFont(textFont);
            }
            rules.add(ruleLabels[i]);
        }
    }

    private void setRuleStrings()
    {
        ruleStrings = new String[22];
		ruleStrings[0] = "   General ";
		ruleStrings[1] = "      - Your objective is to push 6 of your opponent's marbles off the board.";
		ruleStrings[2] = "      - You may move up to three adjacent pieces, positioned in a straight line, in one direction.";
		ruleStrings[3] = "      - This direction can be either 'broadside' (parallel to your line of selected pieces).";
		ruleStrings[4] = "          or 'inline' (perpendicular to the line).";
		ruleStrings[5] = "   How to Move";
		ruleStrings[6] = "       - For an inline move, left-click the piece at the beginning of the line you wish to move.";
        ruleStrings[7] = "./Images/SecondClickInline.png";
		ruleStrings[8] = "       - Then right-click the board space that neighbors the selected piece in the direction you wish to move";
        ruleStrings[9] = "./Images/InlineMoveMadeSmall.png";
        ruleStrings[10] = "       - For a broadside move, select two or three pieces that are in a continuous line.";
        ruleStrings[11] = "./Images/SelectMultiple2Small.png";
        ruleStrings[12] = "       - Then right-click on a space neighboring either end of the selected line of pieces.";
        ruleStrings[13] = "./Images/BroadsideRight2Small.png";
        ruleStrings[14] = "       - NOTE: You should not right-click spaces that border the middle of the selected line of pieces.";
        ruleStrings[15] = "           because the middle spaces provide an ambiguous direction to move in, will be choosen for you.";
        ruleStrings[16] = "   Pushing Your Opponent";
        ruleStrings[17] = "       - To push an opponent's piece(s) simply make an inline move in which your pieces outnumber your opponent's.";
        ruleStrings[18] = "./Images/BeforePushSmall.png";
        ruleStrings[19] = "./Images/AfterPushSmall.png";
        ruleStrings[20] = "       - Simply remember: 3 pushes 2 & 2 pushes 1";
        ruleStrings[21] = "       - Given that 1 single piece can't push another (as 1 is not greater than 1), broadside moves can't push opponents.  ";
    }
}
