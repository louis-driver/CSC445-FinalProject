import javax.swing.JPanel;

//Louis Driver
// Source for hexagon: https://stackoverflow.com/questions/35853902/drawing-hexagon-using-java-error

//This is a JPanel that represents an Abalone board during play

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import java.util.*;

public class AbalonePanel extends JPanel
{
    AbaloneGraph graph; 
    Polygon hexExterior;
    Polygon hexInterior;
    int[] startHeights = new int[11];
    int[] lowerHeights = new int[11];
    int[] startXCoords = new int[11];
    Ellipse2D.Double[] boardSpaces = new Ellipse2D.Double[91];

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
    }

    //Iterates through the graph to assign board spaces proportional to the size
    // of the panel.
    private void assignBoardSpaces()
    {
        //get height and width of the interior hexagon
        Rectangle rect = hexInterior.getBounds();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight();
        // Find an upper and lower x coordinates
        int upperY = hexInterior.ypoints[0];
        int lowerY = hexInterior.ypoints[0];
        for (int i = 1; i < hexInterior.npoints; ++i)
        {
            //Use opposite value as swing creates coordinates from the upper left corner
            if (hexInterior.ypoints[i] < upperY)
                upperY = hexInterior.ypoints[i];
            if (hexInterior.ypoints[i] > lowerY)
                lowerY = hexInterior.ypoints[i];
        }

        //Create array of ints that represent the starting height for each row
        int heightGap = height / 9;
        int rowGap = (int) (heightGap / 15.0);
        int pieceHeight = heightGap - 2*rowGap;
        startHeights[1] = upperY + rowGap;
        lowerHeights[1] = startHeights[0] + pieceHeight;
        for (int i = 2; i < startHeights.length; ++i)
        {
            startHeights[i] = startHeights[i-1] + heightGap;
            lowerHeights[i] = lowerHeights[i-1] + heightGap; 
        }

        //Find starting x coordinates for each row
        int upperX = hexInterior.xpoints[2];
        int middleX = hexInterior.xpoints[3];

        //TODO do math to find more accurate hexagon locations
        int xGap = (int) ((upperX - middleX) / 11.0);
        startXCoords[1] = upperX;
        for (int i = 2; i < startXCoords.length; ++i)
        {
            startXCoords[i] = startXCoords[i-1] - (int) (xGap*2.5);
        }
        System.out.println(Arrays.toString(startXCoords));


        //TODO use startHeights and pieceHeights to set each pieces' height
        int numNodes = 91;
        boolean incrementing = true;
        int rowSize = 6;
        int numRows = 11;
        int currPosition = 0;

         
        /* for (int i=0; i<numNodes; ++i)
        {
            Ellipse2D.Double boardSpace = new Ellipse2D.Double(0,0, pieceHeight, pieceHeight);
            graph.getNode(i).setPiece(boardSpace);
        } */

        for (int i = 0; i < numRows; ++i)
        {
            int currX = startXCoords[i];
            for (int j = 0; j < rowSize; ++j)
            {
                currX = startXCoords[i] + (j-1)* (xGap + pieceHeight);
                if (rowSize == 11)
                    incrementing = false;

                if (incrementing && !graph.getNode(currPosition).isEdge())
                {
                    
                    //System.out.print("node assigned ");
                    //System.out.println("CurrX: " + currX + " CurrPosition:" + currPosition);
                    boardSpaces[currPosition] = new Ellipse2D.Double(currX, (double) startHeights[i], pieceHeight, pieceHeight);
                   
                    //System.out.println(boardSpaces[currPosition].getBounds());
                }
                else //I.e. Player1/Bottom side of the board
                {
                    boardSpaces[currPosition] = new Ellipse2D.Double(0,0, pieceHeight, pieceHeight);
                    //boardSpaces[i] = boardSpace;
                    //System.out.println(boardSpaces[currPosition].getBounds());
                }
                ++currPosition;
                //prevX = currX;
            }
            if (incrementing)
                ++rowSize;
            else
                --rowSize;
        }
        
        for (int i = 0; i < 91; ++i)
        {
            //System.out.println("i" + i + ": " + graph.getPiece(i));
            graph.setPiece(i, boardSpaces[i]);
            //System.out.println(i + ": " + boardSpaces[i].getBounds());
            if (graph.getPiece(i) != null)
            {
                Shape Space = graph.getPiece(i);
                //System.out.println("i" + i + ": " +Space.getBounds());
                //g2.fill(boardSpace);
            }
        }
        //Create array to represent starting x coordinate of each row
        //Calculate the gap between board spaces

    }

    private void createHexagons()
    {
        // Create large hexagon
        Point center1 = new Point(this.getWidth()/2, this.getHeight()/2);
        int radius1 = this.getHeight()/2;
        if (this.getWidth()/2 < radius1)
            radius1 = this.getWidth()/2;
        hexExterior = createHexagon(center1, radius1);
        // Create interior hexagon
        Point center2 = new Point(this.getWidth()/2, this.getHeight()/2);
        int radius2 = (int) (this.getHeight()/2.4);
        if (this.getWidth()/2.5 < radius2)
            radius2 = (int) (this.getWidth()/2.4);
        hexInterior = createHexagon(center2, radius2);
        // Create interior spaces for pieces
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        createHexagons();
        assignBoardSpaces();

        g.setColor(Color.BLACK);
        g.fillPolygon(hexExterior);
        g.setColor(Color.GRAY);
        g.fillPolygon(hexInterior);
        g.setColor(Color.red);
        for (int he : startHeights)
        {
            g.drawLine(0, he, this.getWidth(), he);
        }
        g.setColor(Color.blue);
        for (int he : lowerHeights)
        {
            g.drawLine(0, he, this.getWidth(), he);
        }


        g.setColor(Color.green);
        for (int i = 0; i < 91; ++i)
        {
           //System.out.println("i" + i + ": " + graph.getPiece(i));
            if (graph.getPiece(i) != null)
            {
                Shape boardSpace = graph.getPiece(i);
                //System.out.println("i" + i + ": " +boardSpace.getBounds());
                g2.fill(boardSpace);
            }
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
}
