import javax.swing.JPanel;

//Louis Driver
// Source for hexagon: https://stackoverflow.com/questions/35853902/drawing-hexagon-using-java-error

//This is a JPanel that represents an Abalone board during play

import java.awt.*;
import javax.swing.*;

public class AbalonePanel extends JPanel
{
    AbaloneGraph graph; 
    Polygon hexExterior;
    Polygon hexInterior;
    int[] startHeights = new int[9];
    int[] lowerHeights = new int[10];

    //Test Main class
    public static void main(String[] args)
    {
        
        int frameWidth = 1000;
        int frameHeight = 1000;
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

    public AbalonePanel(AbaloneGraph graph)
    {
        this.graph = graph;
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
        startHeights[0] = upperY + rowGap;
        lowerHeights[0] = startHeights[0] + pieceHeight;
        for (int i = 1; i < startHeights.length; ++i)
        {
            startHeights[i] = startHeights[i-1] + heightGap;
            lowerHeights[i] = lowerHeights[i-1] + heightGap; 
        }
        //TODO use startHeights and pieceHeights to set each pieces' height
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
