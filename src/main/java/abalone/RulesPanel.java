package abalone;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
		setMinimumSize(new Dimension(getWidth(), getHeight()+500));
		
		rulesTitle = new JLabel(" RULES OF ABALONE");
		rulesTitle.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 50));
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
		ruleLabels = new JLabel[44];
		ruleLabels[0] = new JLabel("   General ");
        ruleLabels[1] = new JLabel("       - Your objective is to push 6 of your opponent's pieces off the board.");
		ruleLabels[2] = new JLabel("       - You may move up to three adjacent pieces, positioned in a STRAIGHT line, in one direction, one space away from where each piece ");
		ruleLabels[3] = new JLabel("          was before the move was made (i.e. pieces can't hop mutliple spaces away from where they were to begin with).");
		ruleLabels[4] = new JLabel("       - This direction can be either 'in-line' (parallel to your line of selected pieces)");
		ruleLabels[5] = new JLabel("          or 'broadside' (perpendicular to the line).");
		ruleLabels[6] = new JLabel("       - Common strategies include having more pieces near the center of the board and isolating");
		ruleLabels[7] = new JLabel("          a few of your opponent's pieces on the edge to push them off without resistance.");
		ruleLabels[8] = new JLabel("   How to Move");
		ruleLabels[9] = new JLabel("       - For an in-line move, you start by left-clicking the piece at the beginning of the line you wish to move.");
		ruleLabels[10] = new JLabel("       - You will only need to click TWO pieces: ");
		ruleLabels[11] = new JLabel("         ~ the piece at start of the line you wish to move (using a left-click).");
		ruleLabels[12] = new JLabel("         ~ the piece DIRECTLY AFTER the already selected piece in the direction you wish to move (using a right-click).");
		ruleLabels[13] = new JLabel("       - The image below shows the highlighted start of the line to be moved after having been left-clicked.");
        ruleLabels[14] = new JLabel(secondClick);
		ruleLabels[15] = new JLabel("       - The next step for an in-line move is to right-click the board space that neighbors the selected piece in the direction you wish to move.");
		ruleLabels[16] = new JLabel("       - NOTE: You should NEVER have more than one piece highlighted when making an in-line move. As well you will NOT click the ");
		ruleLabels[17] = new JLabel("           final destination that the line will move to.");
		ruleLabels[18] = new JLabel("       - The image below shows how the line of 3 pieces moved towards 11 o' clock (like on a clock face) after having right-clicked the piece");
		ruleLabels[19] = new JLabel("          DIRECTLY after the highlighted piece (i.e. they were 'touching') that was in the 11 o' clock direction relative to the highlighted piece.");
        ruleLabels[20] = new JLabel(inlineMove);
        ruleLabels[21] = new JLabel("       - For a broadside move, you will select two or three pieces that are in a CONTINUOUS and STRAIGHT line by left-clicking.");
        ruleLabels[22] = new JLabel("       - The image below shows three pieces having been selected with left-clicks in a continuous and straight line.");
        ruleLabels[23] = new JLabel(selectMultiple);
        ruleLabels[24] = new JLabel("       - Next, you will right-click on an EMPTY space that is PERPENDICULAR to the selected line and neighbors EITHER END of the selected line.");
        ruleLabels[25] = new JLabel("       - You will NOT click on an empty spaces that would 'lengthen' the line of selected pieces had an additional piece been there");
        ruleLabels[26] = new JLabel("          because that would be making an in-line move, which can be done more easily by only selecting one piece, then clicking its neighbor.");
        ruleLabels[27] = new JLabel("       - The image below shows the three pieces moving in the 5 o' clock direction after having right-clicked the EMPTY space bordering the ");
		ruleLabels[28] = new JLabel("          end of the selected pieces (i.e. the red piece furthest right) in the 5 o' clock direction. To move that line broadside in the");
		ruleLabels[29] = new JLabel("          7 o' clock direction, you would simply right-click the empty space that borders the furthest left piece in the 7 o' clock direction.");
        ruleLabels[30] = new JLabel(broadsideRight);
        ruleLabels[31] = new JLabel("       - NOTE: You should NOT right-click spaces that border MORE THAN ONE PIECE in the selected line of pieces for a broadside move.");
        ruleLabels[32] = new JLabel("          This is because these spaces do not provide a definitive direction and you won't always move in the direction you intended.");
        ruleLabels[33] = new JLabel("   Pushing Your Opponent");
        ruleLabels[34] = new JLabel("       - To push an opponent's piece(s), you must make an IN-LINE move in which your pieces outnumber your opponent's pieces in that line.");
        ruleLabels[35] = new JLabel("       - The images below show black first left-clicking to select the start of the row they wish to move (highlighted in red).");
		ruleLabels[36] = new JLabel("          Then right-clicking the black piece that borders the red selected piece in the 7 o' clock direction. This results in ");
		ruleLabels[37] = new JLabel("          the white piece at the end of the line being pushed off the edge of the board. ");
        ruleLabels[38] = new JLabel(beforePush);
        ruleLabels[39] = new JLabel(afterPush);
        ruleLabels[40] = new JLabel("       - NOTE: You CANNOT ever push a line of more than FIVE pieces, nor can you push your own pieces that are not in a continuous line.");
        ruleLabels[41] = new JLabel("       - Just remember: 3 pieces push 2 of your opponent's pieces, and 2 pieces push 1 of your opponent's pieces.");
        ruleLabels[42] = new JLabel("       - 1 single piece can't push another (since 1 is not greater than 1), which means broadside moves can't push opponents.");
        ruleLabels[43] = new JLabel("   Have fun!");

		for (int i = 0; i < ruleLabels.length; ++i)
        {
            //Image Path found
            if (ruleLabels[i].getIcon() !=null)
            {
//                ImageIcon rulesImage = new ImageIcon(ruleLabels[i].getText());
//                ruleLabels[i] = new JLabel(rulesImage);
                ruleLabels[i].setMaximumSize(new Dimension(350, 350));
            }
            //Text Labels
            //Headers
            else if (ruleLabels[i].getText().charAt(3) != ' ')
            {
                add(Box.createRigidArea(new Dimension(0,25)));
                ruleLabels[i] = new JLabel(ruleLabels[i].getText());
                ruleLabels[i].setForeground(Color.BLACK);
				ruleLabels[i].setFont(new Font("Times New Roman", Font.BOLD, 30));
            }
            //Normal text
            else
            {
                add(Box.createRigidArea(new Dimension(0,10)));
                ruleLabels[i] = new JLabel(ruleLabels[i].getText());
                ruleLabels[i].setForeground(Color.BLACK);
				ruleLabels[i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
            }
            add(ruleLabels[i]);
		}
        add(Box.createRigidArea(new Dimension(0,25)));
    }
}
