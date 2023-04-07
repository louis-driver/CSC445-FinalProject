import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

//Louis Driver
// Source for hexagon: https://stackoverflow.com/questions/35853902/drawing-hexagon-using-java-error
// Source for text display: https://docs.oracle.com/javase/tutorial/2d/text/measuringtext.html 
//This is a JPanel that represents an Abalone board during play

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class AbalonePanel extends JPanel
{
    //example commit
    private AbaloneGraph graph; 
    private int graphSize = 91;
    private Polygon hexExterior;
    private Polygon hexInterior;
    private Polygon exteriorShadow;
    private Polygon interiorHighlight;
    private int[] startHeights = new int[11];
    private int[] startXCoords = new int[11];
    private int[] yCapturedCoords = new int[6];
    private int[] xCapturedCoords = new int[2];
    private int pieceSize;
    private Node secondClicked;
    private ArrayBlockingQueue<Node> selected = new ArrayBlockingQueue<>(3);
    private int player1Score;
    private int player2Score;

    //Test Main class
    public static void main(String[] args)
    {
        
        int frameWidth = 600;
        int frameHeight = 600;
        JFrame frame = new JFrame();
        AbaloneGraph graph = new AbaloneGraph();
        AbalonePanel panel = new AbalonePanel(graph);

        frame.setSize(frameWidth, frameHeight);
        frame.setTitle("Graph");
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public AbalonePanel(AbaloneGraph g)
    {
        this.graph = g;
        this.addMouseListener(new MoveAdapter());
    }

    //Iterates through the graph to assign board spaces proportional to the size
    // of the panel.
    private void assignBoardSpaces()
    {
        Rectangle rect = hexInterior.getBounds();
        int height = (int) rect.getHeight();
        int upperY = hexInterior.ypoints[4];
        //Create array of ints that represent the starting height for each row
        int heightGap = (int) (height / 9.0);
        int rowGap = (int) (heightGap / 14.0);
        pieceSize = heightGap - 2*rowGap;
        startHeights[1] = upperY + rowGap;
        for (int i = 2; i < startHeights.length; ++i)
        {
            startHeights[i] = startHeights[i-1] + heightGap;
        }

        //Find starting x coordinates for each row
        int upperX = hexInterior.xpoints[2];
        int middleX = hexInterior.xpoints[3];
        //TODO do math to find more accurate hexagon locations
        int xGap = (int) ((upperX - middleX) / 11.0);
        startXCoords[1] = upperX;
        //Set upper half of hexagon
        for (int i = 2; i < 6; ++i)
        {
            startXCoords[i] = startXCoords[i-1] - (int) (xGap*2.5);
        }
        //Lower half of hexagon
        for (int i = 6; i < startXCoords.length; ++i)
        {
            startXCoords[i] = startXCoords[i-1] + (int) (xGap*2.5);
        }

        boolean incrementing = true;
        int rowSize = 6;
        int numRows = 11;
        int currPosition = 0;
        for (int i = 0; i < numRows; ++i)
        {
            int currX = startXCoords[i];
            for (int j = 0; j < rowSize; ++j)
            {
                currX = startXCoords[i] + (j-1)* (xGap + pieceSize);
                if (rowSize == 11)
                    incrementing = false;

                if (!graph.getNode(currPosition).isEdge())
                {
                    //System.out.println("CurrX: " + currX + " CurrPosition:" + currPosition);
                    Ellipse2D.Double boardSpace = new Ellipse2D.Double(currX, (double) startHeights[i], pieceSize, pieceSize);
                    graph.setPiece(currPosition, boardSpace);
                }
                ++currPosition;
            }
            if (incrementing)
                ++rowSize;
            else
                --rowSize;
        }

        //Calculate the positions for captured pieces to be displayed
        int panelWidth = this.getWidth();
        int capturedMargin = (int) (pieceSize/10.0);
        yCapturedCoords[0] = capturedMargin;
        for (int i = 1; i < yCapturedCoords.length; ++i)
        {
            yCapturedCoords[i] = yCapturedCoords[i-1] + pieceSize + capturedMargin;
        }
        //Positon 0 will display on the left side, position 1 on the right
        xCapturedCoords[0] = capturedMargin;
        xCapturedCoords[1] = panelWidth - pieceSize - capturedMargin;
    }

    private void createHexagons()
    {
        int heightGap = (int) (this.getHeight() / 9.0);
        int rowGap = (int) (heightGap / 14.0);
        int pieceSize = heightGap - 2*rowGap;

        // Create large hexagon
        Point center = new Point(this.getWidth()/2, this.getHeight()/2);
        Point offCenterLow = new Point(center.x+4, center.y+2);
        Point offCenterHigh = new Point(center.x-4, center.y-2);
        int radius1 = (int) (this.getHeight()/1.9);
        if (this.getWidth()  < this.getHeight() + pieceSize*2)
            radius1 = (int) (this.getWidth()/2.3);
        hexExterior = createHexagon(center, radius1);

        // Create interior hexagon
        int radius2 = (int) (this.getHeight()/2.4);
        if (this.getWidth() < this.getHeight() + pieceSize*2)
            radius2 = (int) (this.getWidth()/2.9);
        hexInterior = createHexagon(center, radius2);

        // Create offset hexagon as a shadow
        exteriorShadow = createHexagon(offCenterLow, radius1);
        // Create offset hexagon as a highlight
        interiorHighlight = createHexagon(offCenterHigh, radius2);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(new Color(160, 130, 105));

        Color boardColor = new Color(140, 100, 75);
        Color boardDark = new Color(75, 45, 30);

        createHexagons();
        assignBoardSpaces();

        //Draw hexagons of the board
        g.setColor(Color.black);
        g.fillPolygon(exteriorShadow);
        g.setColor(boardDark);
        g.fillPolygon(hexExterior);
        g.setColor(Color.white);
        g.fillPolygon(interiorHighlight);
        g.setColor(boardColor);
        g.fillPolygon(hexInterior);

        //Draw spaces where pieces are/can be
        Node currNode;
        for (int i = 0; i < graphSize; ++i)
        {
            currNode = graph.getNode(i);
            if (currNode.getPiece() != null)
            {
                if (currNode.getColor()==1 && selected.contains(currNode))
                    g2.setColor(new Color(150, 150, 220));
                else if (currNode.getColor() == 2 && selected.contains(currNode))
                    g2.setColor(new Color(150, 10, 10));
                else if (currNode.getColor() == 0)
                    g2.setColor(boardDark);
                else if (currNode.getColor() == 1)
                    g2.setColor(Color.white);
                else if (currNode.getColor() == 2)
                    g2.setColor(Color.black);
                g2.fill(graph.getPiece(i));
            }
        }

        //Draw any captured pieces
        g2.setColor(Color.black);
        for (int i = 0; i<player1Score && player1Score<=6; ++i)
        {
            g2.fillOval(xCapturedCoords[0], yCapturedCoords[i], pieceSize, pieceSize);
        }
        g2.setColor(Color.white);
        for (int i = 0; i<player2Score && player2Score<=6; ++i)
        {
            g2.fillOval(xCapturedCoords[1], yCapturedCoords[i], pieceSize, pieceSize);
        }
        
        //Display winner if applicable
        //TODO center display in the panel
        if (player1Score >= 6)
        {
            Font font = new Font("Times New Roman", Font.ITALIC, this.getHeight()/10);
            g2.setFont(font);
            // get metrics from the graphics
            FontMetrics metrics = g.getFontMetrics(font);
            // get the height of a line of text in this font and render context
            int hgt = metrics.getHeight();
            // get the advance of my text in this font and render context
            int adv = metrics.stringWidth("Player1 WINS!");

            g.setColor(Color.white);
            g.fillRect(this.getWidth()/10 - adv/20, this.getHeight()/2 - (int) (hgt/1.3), adv+adv/10, hgt);
            g.setColor(Color.black);
            g.drawString("Player1 WINS!", this.getWidth()/10, this.getHeight()/2);
        }
        else if (player2Score >= 6)
        {
            Font font = new Font("Times New Roman", Font.ITALIC, this.getHeight()/10);
            g2.setFont(font);
            // get metrics from the graphics
            FontMetrics metrics = g.getFontMetrics(font);
            // get the height of a line of text in this font and render context
            int hgt = metrics.getHeight();
            // get the advance of my text in this font and render context
            int adv = metrics.stringWidth("Player2 WINS!");

            g.setColor(Color.black);
            g.fillRect(this.getWidth()/10 - adv/20, this.getHeight()/2 - (int) (hgt/1.3), adv+adv/10, hgt);
            g.setColor(Color.white);
            g.drawString("Player2 WINS!", this.getWidth()/10, this.getHeight()/2);
        }
    }

    private Polygon createHexagon(Point center, int radius)
    {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; ++i)
        {
            int x = (int) (center.x + radius * Math.cos(i*2*Math.PI/6));
            int y = (int) (center.y + radius * Math.sin(i*2*Math.PI/6));
            hexagon.addPoint(x, y);
        }
        return hexagon;
    }

    private class MoveAdapter extends MouseInputAdapter
    {
        public MoveAdapter()
        {
            super();
        }

        public void mouseClicked(MouseEvent e)
        {
            double clickedX = e.getX();
            double clickedY = e.getY();
            
            int i = 0;
            boolean nodeFound = false;
            int nodePosition = -1;
            Node currNode = null;
            // Finds the point that was clicked if one was clicked
            while (!nodeFound && i < graphSize)
            {
                Ellipse2D currPiece = graph.getPiece(i);
                if(currPiece != null && currPiece.contains(clickedX, clickedY))
                {
                    nodePosition = i;
                    nodeFound = true;
                    currNode = graph.getNode(nodePosition);
                }
                ++i;
            }

            // Prints the node that was clicked if one is found
            if (nodePosition != -1)
            {
                System.out.println("(" + currNode +  ")");
            }

            //Assign most recent three left clicks to the selected queue
            //If a left click exceeds the three, pop the head, then add
            if (SwingUtilities.isLeftMouseButton(e) && currNode != null)
            {
                //Removes a selected node if pressed again
                if (selected.contains(currNode))
                {
                    selected.remove(currNode);
                }
                else if (selected.size() == 3)
                {
                    selected.poll();
                    selected.add(currNode);
                }
                else if (currNode.getColor() != 0)
                {
                    selected.add(currNode);
                }
                System.out.println(Arrays.toString(selected.toArray()));
                repaint();
            }
            if (SwingUtilities.isRightMouseButton(e) && nodePosition != -1)
            {
                // do stuff for right click
                secondClicked = graph.getNode(nodePosition);
            }
            if (selected.size() == 1 && secondClicked != null) // if canMoveInline
            {
                Node firstClicked = selected.poll();
                try
                {
                    int direction = graph.getDirection(firstClicked, secondClicked);
                    if (direction != -1)
                    {
                        Node last = graph.destination(firstClicked, secondClicked, direction);
                        graph.makeInlineMove(firstClicked, last, direction);
                        repaint();
                        System.out.println("Move Made");
                    }
                }
                catch (RuntimeException ex)
                {
                    System.out.println("Invalid Move");
                }
                finally
                {
                    firstClicked = null;
                    secondClicked = null;
                    player1Score = graph.getPlayer1Score();
                    player2Score = graph.getPlayer2Score();
                }
            }
            else if (selected.size() >= 2 && secondClicked != null) //see if broadside move can be made
            {
                try 
                {   
                    System.out.println("Making broadside move");
                    Node[] nodes = new Node[selected.size()];
                    int size = selected.size();
                    for (int j = 0; j < size; ++j)
                    {
                        nodes[j] = selected.poll();
                    }
                    System.out.println("Completed Array: +" + Arrays.toString(nodes));
                    // TODO need to test canMoveBroadside()
                    int direction = graph.getBroadsideDirection(nodes, secondClicked);
                    System.out.println("Direction: "+ direction);
                    graph.makeBroadsideMove(nodes, direction);
                    repaint();
                }
                catch (RuntimeException ex)
                {
                    System.out.println("Could not move broadside.");
                }
                finally
                {
                    secondClicked = null;
                    player1Score = graph.getPlayer1Score();
                    player2Score = graph.getPlayer2Score();
                }
            }
        }
    }
}
