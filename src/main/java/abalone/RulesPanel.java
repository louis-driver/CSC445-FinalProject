package abalone;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

//A class to display the abalone rules in a JPanel
//Don't make any components here private, the ComponentListener for this is still on AbaloneMenu

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
        try{
            secondClick = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/SecondClickInline.png")));
            inlineMove = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/InlineMoveMadeSmall.png")));
            selectMultiple = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/SelectMultiple2Small.png")));
            broadsideRight = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/BroadsideRight2Small.png")));
            beforePush = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/BeforePushSmall.png")));
            afterPush = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("images/AfterPushSmall.png")));
        }
        catch(IOException e){

        }
		ruleLabels = new JLabel[42];
		 ruleLabels[0] = new JLabel("   General ");
         ruleLabels[1] = new JLabel("       - Your objective is to push 6 of your opponent's pieces off the board.");
		 ruleLabels[2] = new JLabel("       - You may move up to three adjacent pieces, positioned in a STRAIGHT line, one space in one direction.");
		 ruleLabels[3] = new JLabel("       - This direction can be either 'in-line' (parallel to your line of selected pieces),");
		 ruleLabels[4] = new JLabel("           or 'broadside' (perpendicular to the line).");
		 ruleLabels[5] = new JLabel("       - One common strategy involves having more pieces near the center of the board, then");
		 ruleLabels[6] = new JLabel("           isolating a few of your opponent's pieces on the edge to push them off without resistance.");
		 ruleLabels[7] = new JLabel("   How to Move");
		 ruleLabels[8] = new JLabel("       - For an in-line move, find a line of two or three pieces you want to move all at once.");
		 ruleLabels[9] = new JLabel("       - You will only need to click TWO pieces:");
		ruleLabels[10] = new JLabel("          ~ First, left-click the piece at the beginning of the line.");
		ruleLabels[11] = new JLabel("          ~ Then, right-click another piece that is adjacent to the piece you selected.");
        ruleLabels[12] = new JLabel("      - Think of it like using the first piece in the line to push the other pieces. However, you can only");
        ruleLabels[13] = new JLabel("          move a total of up to three pieces: The piece you selected, and the other two pieces you're pushing.");
		ruleLabels[14] = new JLabel("      - The image below shows what an in-line move should look like on the board before it's been made.");
        ruleLabels[15] = new JLabel(secondClick);
        ruleLabels[16] = new JLabel("      - The next step for an in-line move would be to right-click another piece that is diagonally adjacent to the selected piece.");
        ruleLabels[17] = new JLabel("      - NOTE: You should not have more than one piece highlighted when making an in-line move.");
		ruleLabels[18] = new JLabel("      - The image below shows how the line of 3 pieces will move northwest after having right-clicked.");
        ruleLabels[19] = new JLabel(inlineMove);
        ruleLabels[20] = new JLabel("      - For a broadside move, you will select two or three pieces in a straight, unbroken line by left-clicking.");
        ruleLabels[21] = new JLabel("      - The image below shows three pieces having been selected in a continuous and straight line.");
        ruleLabels[22] = new JLabel(selectMultiple);
        ruleLabels[23] = new JLabel("      - Next, you will right-click on an EMPTY space that is diagonal to either end of the line of pieces.");
        ruleLabels[24] = new JLabel("          In the above image, these would be the spaces northwest and southwest to the leftmost piece, and");
        ruleLabels[25] = new JLabel("          the spaces northeast and southeast of the rightmost piece.");
        ruleLabels[26] = new JLabel("      - NOTE: Do not right-click spaces that border MORE THAN ONE PIECE in the selected line of pieces for a broadside move.");
        ruleLabels[27] = new JLabel("          This is because these spaces do not provide a definitive direction and you won't always move in the direction you intended.");
        ruleLabels[28] = new JLabel("      - The image below shows the three pieces moving southeast after having right-clicked a valid corresponding space.");
        ruleLabels[29] = new JLabel(broadsideRight);
        ruleLabels[30] = new JLabel("      - NOTE: Do not try to move your line of selected pieces directly east or west. This would be the equivalent");
        ruleLabels[31] = new JLabel("          of an in-line move. Directions on how to make in-line moves are above.");
        ruleLabels[32] = new JLabel("   Pushing Your Opponent");
        ruleLabels[33] = new JLabel("      - To push an opponent's piece(s), you must make an IN-LINE move in which your line of pieces outnumbers your opponent's.");
        ruleLabels[34] = new JLabel("      - The images below show black first left-clicking to select the start of the row they wish to move (highlighted in red),");
		ruleLabels[35] = new JLabel("          then right-clicking the black piece that borders the red selected piece to the southwest. This results in ");
		ruleLabels[36] = new JLabel("          the white piece at the end of the line being pushed off the edge of the board.");
        ruleLabels[37] = new JLabel(beforePush);
        ruleLabels[38] = new JLabel(afterPush);
        ruleLabels[39] = new JLabel("      - Just remember: 3 of your pieces push 2 of your opponent's pieces, and 2 of your pieces push 1 of your opponent's pieces.");
        ruleLabels[40] = new JLabel("      - 1 single piece can't push another (since 1 is not greater than 1), which is why broadside moves can't push opponents.");
        ruleLabels[41] = new JLabel("   Good luck!");

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
