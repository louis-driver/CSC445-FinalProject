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
		ruleLabels = new JLabel[23];
		ruleLabels[0] = new JLabel("   General ");
		ruleLabels[1] = new JLabel("       - Your objective is to push 6 of your opponent's pieces off the board.");
		ruleLabels[2] = new JLabel("       - You may move up to three adjacent pieces, positioned in a straight line, in one direction.");
		ruleLabels[3] = new JLabel("       - This direction can be either 'in-line' (parallel to your line of selected pieces).");
		ruleLabels[4] = new JLabel("          or 'broadside' (perpendicular to the line).");
		ruleLabels[5] = new JLabel("   How to Move");
		ruleLabels[6] = new JLabel("       - For an in-line move, left-click the piece at the beginning of the line you wish to move.");
        ruleLabels[7] = new JLabel(secondClick);
		ruleLabels[8] = new JLabel("       - Then, right-click the board space that neighbors the selected piece in the direction you wish to move");
        ruleLabels[9] = new JLabel(inlineMove);
        ruleLabels[10] = new JLabel("       - For a broadside move, select two or three pieces that are in a continuous line.");
        ruleLabels[11] = new JLabel(selectMultiple);
        ruleLabels[12] = new JLabel("       - Then, right-click on a space neighboring either end of the selected line of pieces.");
        ruleLabels[13] = new JLabel(broadsideRight);
        ruleLabels[14] = new JLabel("       - NOTE: You should not right-click spaces that border the middle piece in the selected line of pieces;");
        ruleLabels[15] = new JLabel("           Because the middle spaces make which direction to move in ambiguous, you may not always move the way you intended.");
        ruleLabels[16] = new JLabel("   Pushing Your Opponent");
        ruleLabels[17] = new JLabel("       - To push an opponent's piece(s), simply make an in-line move in which your pieces outnumber your opponent's.");
        ruleLabels[18] = new JLabel(beforePush);
        ruleLabels[19] = new JLabel(afterPush);
        ruleLabels[20] = new JLabel("       - Just remember: 3 pieces push 2, and 2 pieces push 1.");
        ruleLabels[21] = new JLabel("       - 1 single piece can't push another (since 1 is not greater than 1), which means broadside moves can't push opponents.");
        ruleLabels[22] = new JLabel("   Have fun!");

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