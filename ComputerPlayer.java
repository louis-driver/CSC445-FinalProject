//A class to play against a human 
//It will probably play like the biggest idiot known to man kind, so be nice. 


//Player takes a AbaloneGraph Object for construction.
//For each turn, the updated graph should be passed to the Player to keep track of its nodes and positions
//Calling the get move method provides the nodes for the next computer move
import java.util.ArrayList;

 
public class ComputerPlayer {
    private ArrayList<Node> computerNodes;
    private ArrayList<Node> edgePieces;
    private AbaloneGraph graph;

    public ComputerPlayer(AbaloneGraph g)
    {
        computerNodes = new ArrayList<Node>(14);
        edgePieces = new ArrayList<Node>(computerNodes.size());
        graph = g;
        for(int i=0; i<91; i++)
        {
            if(g.getNode(i).getColor()==2)
            {
                computerNodes.add(g.getNode(i));
                if(g.getNode(i).bordersEdge() && !g.getNode(i).isEdge())
                {
                    edgePieces.add(g.getNode(i));
                }
            }      
        }
        edgePieces.trimToSize();
        System.out.println("edge piece size 1: " + edgePieces.size());
    }

    //Takes the current state of graph and iterates throguh adding its players to an ArrayList and its edgePieces to and Array List
    public void updatePlayers(AbaloneGraph g)
    {
        computerNodes.clear();
        edgePieces.clear();
        for(int i=0; i<91; i++)
        {
            if(g.getNode(i).getColor()==2)
            {
                computerNodes.add(g.getNode(i));
                if(g.getNode(i).bordersEdge() && !g.getNode(i).isEdge())
                {
                    edgePieces.add(g.getNode(i));
                }
            }   
        }
        computerNodes.trimToSize();
        edgePieces.trimToSize();
        System.out.println("comp piece size 2: " + computerNodes.size());
        System.out.println("edge piece size 2: " + edgePieces.size());
    }

    //Returns the nodes and direction for the next move
    //The system to determine moves is to prioritise edge pieces and move them from the edge
    //The moves are ranked as follows:
    // 1. If its an edge piece, borders a white piece, and can push the white piece
    // 2. If its an edge piece, borders a white piece, and can move away from the white piece
    // 3. If its an edge piece and can move 
    // 4. Any other single piece that can move 
    //The int[] returned is ordered as follows: [first node ID from graph, destination piece ID from graph, direction]
    public int[] getMove()
    {
        System.out.println("comp piece size 3: " + computerNodes.size());
        System.out.println("edge piece size 3: " + edgePieces.size());
        ArrayList<Node> priority = new ArrayList<Node>(edgePieces.size());
        ArrayList<Node> secondary = new ArrayList<Node>(edgePieces.size());
        for(int i=0; i<edgePieces.size(); i++)
        {
            for(int j=0; j<6; j++)
            {
                if(edgePieces.get(i).getSibling(j) != null && edgePieces.get(i).getSibling(j).getColor()==1)
                    priority.add(edgePieces.get(i));
                else 
                    secondary.add(edgePieces.get(i));
            }
        }
        priority.trimToSize();
        secondary.trimToSize();
        //Removes nodes from the priority list unless it can push a white piece or can move
        for(int i=0; i<priority.size(); i++)
        {
            boolean remove = true;
            for(int j=0; j<6; j++)
            {
                if((priority.get(i).getSibling(j) != null) && (priority.get(i).getSibling(j).getColor()==0))
                    remove = false;
                else if((priority.get(i).getSibling(j) != null) && (priority.get(i).getSibling(j).getColor()==1))
                {
                    int direction = graph.getDirection(priority.get(i), priority.get(i).getSibling(j));
                    Node dest = graph.destination(priority.get(i), priority.get(i).getSibling(j), direction);
                    if(dest!=null)
                        remove=false;
                }
            }

            if(remove)
                priority.remove(i);
        }

        //Goes through the priority nodes and returns a move based on the ranking system
        if(priority.size()!=0)
        {
            for(int i=0; i<priority.size(); i++)
            {
                for(int j=0; j<priority.size(); j++)
                {
                    if((priority.get(i).getSibling(j) != null) && (priority.get(i).getSibling(j).getColor()==1))
                    {
                        int direction = graph.getDirection(priority.get(i), priority.get(i).getSibling(j));
                        Node dest = graph.destination(priority.get(i), priority.get(i).getSibling(j), direction);
                        if(dest!=null)
                        {
                            int[] move = {priority.get(i).getID(), dest.getID(), direction};
                            return move;

                        }
                    
                        
                    }
                }
            }
            for(int i=0; i<6; i++)
            {
                if(priority.get(0).getSibling(i)!=null && priority.get(0).getSibling(i).getColor()==0)
                {
                    int direction = graph.getDirection(priority.get(0), priority.get(0).getSibling(i));
                    Node dest = graph.destination(priority.get(0), priority.get(0).getSibling(i), direction);
                    int[] move = {priority.get(i).getID(), dest.getID(), direction};
                    return move;
                }
            }


        }
        //If there are no priority nodes, select the first node from the secondary nodes and move it in a direction it can go
        else if(secondary.size()!=0)
        {
            for(int i=0; i<secondary.size(); i++)
            {
                for(int j=0; j<6; j++)
                {
                    if(priority.get(i).getSibling(j)!=null && priority.get(i).getSibling(j).getColor()==0)
                    {
                        int direction = graph.getDirection(priority.get(i), priority.get(i).getSibling(j));
                        Node dest = graph.destination(priority.get(i), priority.get(i).getSibling(j), direction);
                        int[] move = {priority.get(i).getID(), dest.getID(), direction};
                        return move;
                    }
                }
            }

        }
        //If there are no priority nodes, the first available single move for a non egd piece 
        else 
        {
            for(int i=0; i<computerNodes.size(); i++)
            {
                for(int j=0; j<6; j++)
                {
                    if(computerNodes.get(i).getSibling(j)!=null && computerNodes.get(i).getSibling(j).getColor()==0)
                    {
                        int direction = graph.getDirection(computerNodes.get(i), computerNodes.get(i).getSibling(j));
                        Node dest = graph.destination(computerNodes.get(i), computerNodes.get(i).getSibling(j), direction);
                        int[] move = {computerNodes.get(i).getID(), dest.getID(), direction};
                        return move;
                    }
                }
            }
        }

        //If no move was found, return all -1
        int[] move = {-1, -1, -1};
        return move;

    }



    public String toString()
    {
        String s = "";
        for(int i=0; i<computerNodes.size(); i++)
        {
            if(computerNodes.get(i)!=null)
                s += computerNodes.get(i).getID() + " ";
        }

        return s;
    }

}
