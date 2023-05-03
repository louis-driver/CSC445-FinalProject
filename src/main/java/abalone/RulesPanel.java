package abalone;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

//A class to display the abalone rules in a JPanel

public class RulesPanel extends JPanel
{
    private JLabel rulesTitle;
    private JLabel[] ruleLabels;

    public RulesPanel()
    {
        Color boardColorLight = new Color(160, 130, 105);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(boardColorLight);
		
		rulesTitle = new JLabel(" RULES OF ABALONE");
		rulesTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/20)));
		rulesTitle.setForeground(Color.BLACK);
		add(Box.createRigidArea(new Dimension(0,20)));
		add(rulesTitle);
        ImageIcon secondClick = null;
		ImageIcon inlineMove = null;
        ImageIcon selectMultiple = null;
        ImageIcon broadsideRight = null;
        ImageIcon beforePush = null;
        ImageIcon afterPush = null;
        try
        {
            secondClick = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/SecondClickInline.png")));
            inlineMove = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/InlineMoveMadeSmall.png")));
            selectMultiple = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/SelectMultiple2Small.png")));
            broadsideRight = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/BroadsideRight2Small.png")));
            beforePush = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/BeforePushSmall.png")));
            afterPush = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/AfterPushSmall.png")));
        }
        catch(IOException e)
        {
            System.out.println("Images unable to be loaded");
        }

		ruleLabels = new JLabel[47];
		 ruleLabels[0] = new JLabel("   General");
         ruleLabels[1] = new JLabel("      - Your objective is to push six of your opponent's pieces off the board.");
		 ruleLabels[2] = new JLabel("      - You may move up to three adjacent pieces, positioned in a straight line, one space in any direction.");
		 ruleLabels[3] = new JLabel("      - A move can be either 'in-line' (parallel to your line of selected pieces)");
		 ruleLabels[4] = new JLabel("           or 'broadside' (perpendicular to the line).");
		 ruleLabels[5] = new JLabel("      ");
		 ruleLabels[6] = new JLabel("   How to Move");
		 ruleLabels[7] = new JLabel("      - For an in-line move, find a line of two or three pieces you want to move all at once.");
		 ruleLabels[8] = new JLabel("          ~ First, left-click the piece at the beginning of the line. Then, right-click");
		 ruleLabels[9] = new JLabel("               another piece that is adjacent to the piece you selected.");
		ruleLabels[10] = new JLabel("      - Think of in-line moves like using the first piece in the line to push the other pieces. You can only move");
        ruleLabels[11] = new JLabel("          up to three pieces total, though: The piece you selected, and the other two pieces you're pushing.");
        ruleLabels[12] = new JLabel("      - The images below show the board before and after an in-line move.");
		ruleLabels[13] = new JLabel("          ~ Green arrows indicate directions in which the selected piece(s) can move, i.e. which spaces can be right-clicked,");
        ruleLabels[14] = new JLabel("               and blue arrows indicate the direction in which a piece has moved, i.e. which space was right-clicked.");
        ruleLabels[15] = new JLabel(secondClick);
        ruleLabels[16] = new JLabel(inlineMove);
        ruleLabels[17] = new JLabel("      - Since moving directly left or right would push other white pieces off the board,");
        ruleLabels[18] = new JLabel("          the selected piece in the above example can only move southwest or southeast.");
        ruleLabels[19] = new JLabel("      - NOTE: You should not have more than one piece highlighted when making an in-line move.");
        ruleLabels[20] = new JLabel("      ");
        ruleLabels[21] = new JLabel("      - For a broadside move, select two or three pieces in a straight, unbroken line by left-clicking. Then,");
        ruleLabels[22] = new JLabel("          right-click an empty space diagonal to either end of the line of pieces.");
        ruleLabels[23] = new JLabel("      - The images below show the board before and after a broadside move.");
        ruleLabels[24] = new JLabel(selectMultiple);
        ruleLabels[25] = new JLabel(broadsideRight);
        ruleLabels[26] = new JLabel("      - NOTE: The spaces BETWEEN those indicated by the green arrows cannot be selected when making a broadside move.");
        ruleLabels[27] = new JLabel("          ~ The travel direction to these spaces is ambiguous, so selecting them when making a broadside move");
        ruleLabels[28] = new JLabel("               would cause your line of pieces to move in ways you might not have intended.");
        ruleLabels[29] = new JLabel("      - Do not try to move in directions parallel the your line of pieces when attempting a broadside move.");
        ruleLabels[30] = new JLabel("          ~ This would be equivalent to an in-line move. Find directions on how to make in-line moves above.");
        ruleLabels[31] = new JLabel("      ");
        ruleLabels[32] = new JLabel("   Pushing Your Opponent");
        ruleLabels[33] = new JLabel("      - To push an opponent's piece(s), you must make an IN-LINE move in which your line of pieces outnumbers your opponent's.");
        ruleLabels[34] = new JLabel("      - The images below show the board before and after black pushes one of white's pieces off the board.");
        ruleLabels[35] = new JLabel(beforePush);
        ruleLabels[36] = new JLabel(afterPush);
        ruleLabels[37] = new JLabel("      - Black has a line of 3 pieces, which outnumbers white's line of 2 pieces. This allows black to push,");
		ruleLabels[38] = new JLabel("          which knocks one of white's pieces off the board.");
        ruleLabels[39] = new JLabel("      - NOTE: 1 single piece can't push another single piece, because pushing requires having more pieces than your opponent.");
        ruleLabels[40] = new JLabel("          ~ This is why broadside moves can't push opponents.");
        ruleLabels[41] = new JLabel("   Tips");
        ruleLabels[42] = new JLabel("      - One common strategy involves having more pieces near the center of the board, then isolating");
        ruleLabels[43] = new JLabel("          a few of your opponent's pieces on the edge to push them off without resistance.");
        ruleLabels[44] = new JLabel("      ");
        ruleLabels[45] = new JLabel("   Good luck!");
        ruleLabels[46] = new JLabel("      ");

		for (int i = 0; i < ruleLabels.length; ++i)
        {
            //Image JLabel
            if (ruleLabels[i].getIcon() != null)
            {
                ruleLabels[i].setMaximumSize(new Dimension(350, 350));
            }
            //Text Labels
            //Detect headers by their format
            else if (ruleLabels[i].getText().charAt(3) != ' ')
            {
                add(Box.createRigidArea(new Dimension(0,25)));
                ruleLabels[i] = new JLabel(ruleLabels[i].getText());
                ruleLabels[i].setForeground(Color.BLACK);
				ruleLabels[i].setFont(new Font("Times New Roman", Font.BOLD, (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/60)));
            }
            //Detect normal text by its format
            else
            {
                add(Box.createRigidArea(new Dimension(0,10)));
                ruleLabels[i] = new JLabel(ruleLabels[i].getText());
                ruleLabels[i].setForeground(Color.BLACK);
				ruleLabels[i].setFont(new Font("Times New Roman", Font.PLAIN, (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/60)));
            }
            add(ruleLabels[i]);
		}
        add(Box.createRigidArea(new Dimension(0,25)));
    }
}
