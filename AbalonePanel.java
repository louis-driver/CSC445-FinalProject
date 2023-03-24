import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

//Louis Driver
// Source for hexagon: https://stackoverflow.com/questions/35853902/drawing-hexagon-using-java-error

//This is a JPanel that represents an Abalone board during play

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import java.util.*;

public class AbalonePanel extends JPanel
{
    AbaloneGraph graph; 
    int graphSize = 91;
    Polygon hexExterior;
    Polygon hexInterior;
    Polygon exteriorShadow;
    Polygon interiorHighlight;
    int[] startHeights = new int[11];
    int[] startXCoords = new int[11];
    Node firstClicked;
    Node secondClicked;

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
        //int direction = graph.getDirection(graph.getNode(7), graph.getNode(14));
        //graph.makeInlineMove(graph.getNode(7), graph.getNode(22), direction);
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
        int pieceHeight = heightGap - 2*rowGap;
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
                currX = startXCoords[i] + (j-1)* (xGap + pieceHeight);
                if (rowSize == 11)
                    incrementing = false;

                if (!graph.getNode(currPosition).isEdge())
                {
                    //System.out.println("CurrX: " + currX + " CurrPosition:" + currPosition);
                    Ellipse2D.Double boardSpace = new Ellipse2D.Double(currX, (double) startHeights[i], pieceHeight, pieceHeight);
                    graph.setPiece(currPosition, boardSpace);
                }
                else //I.e. an edge of the board
                {
                    Ellipse2D.Double boardSpace = new Ellipse2D.Double(0,0, pieceHeight, pieceHeight);
                    graph.setPiece(currPosition, boardSpace);
                }
                ++currPosition;
            }
            if (incrementing)
                ++rowSize;
            else
                --rowSize;
        }
    }

    private void createHexagons()
    {
        // Create large hexagon
        Point center = new Point(this.getWidth()/2, this.getHeight()/2);
        Point offCenterLow = new Point(center.x+4, center.y+2);
        Point offCenterHigh = new Point(center.x-4, center.y-2);
        int radius1 = (int) (this.getHeight()/1.9);
        if (this.getWidth()/2 < radius1)
            radius1 = this.getWidth()/2;
        hexExterior = createHexagon(center, radius1);
        // Create interior hexagon
        int radius2 = (int) (this.getHeight()/2.4);
        if (this.getWidth()/2.5 < radius2)
            radius2 = (int) (this.getWidth()/2.4);
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
        for (int i = 0; i < graphSize; ++i)
        {
            if (graph.getNode(i).getColor() == 0)
                g2.setColor(boardDark);
            else if (graph.getNode(i).getColor() == 1)
                g2.setColor(Color.white);
            else if (graph.getNode(i).getColor() == 2)
                g2.setColor(Color.black);
            g2.fill(graph.getPiece(i));
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
            // Finds the point that was clicked if one was clicked
            while (!nodeFound && i < graphSize)
            {
                if(graph.getPiece(i).contains(clickedX, clickedY))
                {
                    nodePosition = i;
                    nodeFound = true;
                }
                ++i;
            }

            // Prints the node that was clicked if one is found
            if (nodePosition != -1)
                System.out.println("(" + graph.getNode(nodePosition) +  ")");

            if (SwingUtilities.isLeftMouseButton(e) && nodePosition != -1)
            {
                // do stuff for left click
                firstClicked = graph.getNode(nodePosition);
            }
            if (SwingUtilities.isRightMouseButton(e) && nodePosition != -1)
            {
                // do stuff for rigt click
                secondClicked = graph.getNode(nodePosition);
            }
            if (firstClicked != null && secondClicked != null)
            {
                try
                {
                    
                    int direction = graph.getDirection(firstClicked, secondClicked);
                    if (direction != -1)
                    {
                        System.out.println("Direction:" + direction);
                        Node last = graph.destination(firstClicked, secondClicked, direction);
                        System.out.println("First: " + firstClicked);
                        System.out.println("Last:" + last);
                        graph.makeInlineMove(firstClicked, last, direction);
                        repaint();
                        System.out.println("Move Made");
                    }
                }
                catch (RuntimeException ex)
                {
                    //System.out.println(ex);
                    System.out.println("Invalid Move");
                }
                finally
                {
                    firstClicked = null;
                    secondClicked = null;
                }
            }
        }
    }
}
