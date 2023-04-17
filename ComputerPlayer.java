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
    private ArrayList<Node> nonEdgePieces;
    private ArrayList<Node> opponentNodes;

    public ComputerPlayer(AbaloneGraph g)
    {
        computerNodes = new ArrayList<Node>(14);
        edgePieces = new ArrayList<Node>(computerNodes.size());
        nonEdgePieces = new ArrayList<>(computerNodes.size());
        opponentNodes = new ArrayList<>(computerNodes.size());
        graph = g;
        for(int i=0; i<91; i++)
        {
            if(g.getNode(i).getColor()==2)
            {
                computerNodes.add(g.getNode(i));
                if(g.getNode(i).bordersEdge() && !g.getNode(i).isEdge())
                    edgePieces.add(g.getNode(i));
                
                else if(!g.getNode(i).isEdge())
                    nonEdgePieces.add(g.getNode(i));
            }  
            else if(g.getNode(i).getColor()==1)
                opponentNodes.add(g.getNode(i));    
        }
        edgePieces.trimToSize();
        nonEdgePieces.trimToSize();
        computerNodes.trimToSize();
        // System.out.println("edge piece size 1: " + edgePieces.size());
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
                    edgePieces.add(g.getNode(i));
                else if(!g.getNode(i).isEdge())
                    nonEdgePieces.add(g.getNode(i));
            }   
        }
        computerNodes.trimToSize();
        edgePieces.trimToSize();
        nonEdgePieces.trimToSize();
        // System.out.println("comp piece size 2: " + computerNodes.size());
        // System.out.println("edge piece size 2: " + edgePieces.size());
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
        // System.out.println("edge piece size 3: " + edgePieces.size());
        ArrayList<Node> priority = new ArrayList<Node>(edgePieces.size());
        ArrayList<Node> secondary = new ArrayList<Node>(edgePieces.size());
        //Adds edge pieces that border a white piece or can inline move into priority
        for(int i=0; i<edgePieces.size(); i++)
        {
            boolean priorityNode = false;
            for(int j=1; j<12; j+=2)
            {
                Node piece1 = edgePieces.get(i);
                Node piece2 = edgePieces.get(i).getSibling(j);
                int direction = graph.getDirection(piece1, piece2);
                if(piece1 != null && piece2.getColor()==1)
                    priorityNode = true;
                else if(graph.destination(piece1, piece2, direction)!=null)
                    priorityNode = true;
            }
            if(priorityNode)
                priority.add(edgePieces.get(i));
            else 
                secondary.add(edgePieces.get(i));
        }
        priority.trimToSize();
        secondary.trimToSize();
        System.out.println("prio size 1: " + priority.size());
        System.out.println("sec size 1: " + secondary.size());
        

        for(int i=0; i<priority.size(); i++)
        {
            System.out.print(priority.get(i) + " ");
        }
        System.out.println("prio size 2: " + priority.size());
        System.out.println("sec size 2: " + secondary.size());

        //Goes through the priority nodes and returns a move based on the ranking system
        if(priority.size()!=0)
        {
            //Checks to see if any of the priority nodes can push a white node or inline move away from edge and returns possible move
            for(int i=0; i<priority.size(); i++)
            {
                for(int j=1; j<12; j+=2)
                {   
                    Node piece1 = priority.get(i);
                    Node piece2 = piece1.getSibling(j);
                    int direction = j;
                    Node destination = graph.destination(piece1, piece2, direction);
                    System.out.println("p1: " + piece1.getID());
                    System.out.println("p2: " + piece2.getID());
                    if((piece2 != null) && (piece2.getColor()==1))
                    {
                        if(destination!=null)
                        {
                            System.out.println("return 1");
                            int[] move = {piece1.getID(), destination.getID(), direction};
                            return move;

                        }
                        else if(piece2.getColor()==0)
                        {
                            int[] move = {piece1.getID(), piece2.getID(), direction};
                            return move;
                        }
                    }
                }    
                
            }   
            for(int i=0; i<priority.size(); i++)
            {
                for(int j=1; j<12; j+=2)
                {
                    Node piece1 = priority.get(i);
                    Node piece2 = piece1.getSibling(j);
                    int direction = j;
                    Node destination = graph.destination(piece1, piece2, direction);
                    System.out.println("return 1");
                    if(!piece2.isEdge() && destination!=null && !destination.isEdge())
                    {
                        System.out.println("in else if statement");
                        System.out.println("j: " + j + "piece1: " + piece1.getID() + "piece2: " + piece2.getID() + "destination: " + graph.destination(piece1, piece2, direction).getID());
                        int[] move = {piece1.getID(), destination.getID(), direction};
                        return move;
                    }
                }
            }
            //Finds which node can move and moves it 
            for(int i=0; i<priority.size(); i++)
            {
                for(int j=1; j<12; j+=2)
                {
                    if(priority.get(i).getSibling(j)!=null && priority.get(i).getSibling(j).getColor()==0 && !priority.get(i).getSibling(j).isEdge())
                    {
                        int direction = graph.getDirection(priority.get(i), priority.get(i).getSibling(j));
                        Node dest = graph.destination(priority.get(i), priority.get(i).getSibling(j), direction);
                        int[] move = {priority.get(i).getID(), dest.getID(), direction};
                        System.out.println("return 2");
                        return move;
                    }
                }
            }  
            
            System.out.println("return 3");
        }
        //If there are no priority nodes, select the first node from the secondary nodes and move it in a direction it can go
        else if(secondary.size()!=0)
        {
            System.out.println("in second statement");
            for(int i=0; i<secondary.size(); i++)
            {
                for(int j=1; j<12; j+=2)
                {
                    if(secondary.get(i).getSibling(j)!=null && secondary.get(i).getSibling(j).getColor()==0 && !secondary.get(i).getSibling(j).isEdge())
                    {
                        int direction = j;
                        System.out.println("direction: " + direction);
                        Node dest = graph.destination(secondary.get(i), secondary.get(i).getSibling(j), direction);
                        int[] move = {secondary.get(i).getID(), dest.getID(), direction};
                        return move;
                    }
                }
            }

        }
        //If there are no priority nodes, the first available single move for a non edge piece 
        else 
        {
            System.out.println("in else");
            for(int i=0; i<computerNodes.size(); i++)
            {
                for(int j=1; j<12; j+=2)
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
    //This the real one. Ignore above
    public int[] getMove2()
    {
        Node toMove;
        int direction = -1;
        int level = 8;
        for(int i=0; i<edgePieces.size(); i++)
        {
            Node piece1 = edgePieces.get(i);
            boolean inDanger = false; //call method to check
            int pushDirection = 0; //call method to return direction it can push
            //Checks all directions of edge nodes that are in danger and checks if they can escape the edge or move out of danger 
            for(int j=2; j<12 && inDanger; j+=2)
            {
                Node piece2 = piece1.getSibling(j);
                Node destination= graph.destination(piece1, piece2, j);

                if(destination!= null && !destination.bordersEdge() && !destination.isEdge())
                {
                    toMove = piece1;
                    direction = j; 
                    level = 1;
                }
                else if(destination!=null && !destination.isEdge() && level > 2)
                {
                    toMove = piece2;
                    direction =j;
                    level = 2;
                }
            }

            //If this node can push a white node and a better node hasnt been found, update to move
            if(pushDirection!=-1 && level>4)
            {
                toMove = piece1;
                level = 3;
            }
            
            //Checks all directions of a node to see if it can move off edge if a better move has not been found
            for(int j=2; j<12 && level>5; j+=2)
            {
                Node piece2 = piece1.getSibling(j);
                Node destination= graph.destination(piece1, piece2, j);
                if(destination!= null && !destination.bordersEdge())
                {
                    //do something 
                }
            }
              
        }

        for(int i=0; i<nonEdgePieces.size(); i++)
        {

        }
        
        //Checks all directions of a node to see if it can push a white node if a better move has not been found
        for(int i=0; i<nonEdgePieces.size() && level>6; i++)
        {
            Node piece1 = nonEdgePieces.get(i);
            int pushDirection = 0; //call can push method 
            if(pushDirection!= -1)
            {
                //do something 
            }
        }

        //Checks all directions of a node and finds the first available move if a better move has not been found
        for(int i=0; i<nonEdgePieces.size() && level>7; i++)
        {
            Node piece1 = nonEdgePieces.get(i);
            for(int j=2; j<12; j+=2)
            {
                Node piece2 = piece1.getSibling(j);
                Node destination = graph.destination(piece1, piece2, i);
                if(destination!= null && !destination.isEdge())
                {
                    // do something 
                }
            }
        }
        int[] r = {1,2};
        return r;

    }

    private int[] dangerEscapeBoth()
    {
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<edgePieces.size(); i++)
        {
            Node piece1 = edgePieces.get(i);
            boolean inDanger = false; //call method to check
            for(int j=2; j<12 && inDanger; j+=2)
            {
                Node piece2 = piece1.getSibling(j);
                Node dest= graph.destination(piece1, piece2, j);
                if(destination!= null && !destination.bordersEdge() && !destination.isEdge())
                {
                    toMove = piece1;
                    direction = j; 
                    destination = dest;
                }
            }
        }
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getColor(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }

    }
    private int[] dangerEscapeDanger()
    {
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<edgePieces.size(); i++)
        {
            Node piece1 = edgePieces.get(i);
            boolean inDanger = false; //call method to check
            for(int j=2; j<12 && inDanger; j+=2)
            {
                Node piece2 = piece1.getSibling(j);
                Node dest= graph.destination(piece1, piece2, j);
                if(destination!=null && !destination.isEdge())
                {
                    toMove = piece2;
                    direction =j;
                    destination = dest;
                }
            }
        } 
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getColor(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }  
    }
    private int[] captureOpponent()
    {
        int toMove = -1;
        int direction = -1;
        for(int i=0; i<opponentNodes.size(); i++)
        {
            int[] danger = {0,0,0}; //call method for node that pushes and direction
            if(danger[0]!=-1)
            {
                toMove = danger[0];
                direction = danger[1];
            }
        }
        if(toMove!=-1 && direction!=-1)
        {
            Node piece1 = graph.getNode(toMove);
            Node piece2 = piece1.getSibling(direction);
            Node destination = graph.destination(piece1, piece2, direction);
            int[] move = {piece1.getID(), destination.getID(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }
    private int[] edgePush()
    {
        Node toMove = null;
        int direction = -1;
        for(int i=0; i<edgePieces.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                boolean canPush = false; //call method to check if it pushes white
                if(canPush)
                {
                    toMove = edgePieces.get(i);
                    direction = j;
                }
            }
        }
        if(toMove!=null && direction!=-1)
        {
            Node piece2 = toMove.getSibling(direction);
            Node destination = graph.destination(toMove, piece2, direction);
            int[] move = {toMove.getID(), destination.getID(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }
    private int[] edgeEscape()
    {
        Node toMove = null;
        int direction = -1;
        Node destination = null;
        for(int i=0; i<edgePieces.size(); i++)
        {
            for(int j=2; j<12; j+=2)
            {
                Node piece1 = edgePieces.get(i);
                Node piece2 = piece1.getSibling(j);
                Node dest= graph.destination(piece1, piece2, j);
                if(destination!= null && !destination.bordersEdge())
                {
                    toMove = piece1;
                    direction = j;
                    destination = dest;
                }
            }

        }
        if(toMove!=null && direction!=-1 && destination!=null)
        {
            int[] move = {toMove.getID(), destination.getColor(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        } 
    }
    private int[] pushWhite()
    {
        Node toMove = null;
        int direction = -1;
        for(int i=0; i<nonEdgePieces.size(); i++)
        {
            for(int j=1; j<12; j+=2)
            {
                boolean canPush = false; //call method to check if it pushes white
                if(canPush)
                {
                    toMove = edgePieces.get(i);
                    direction = j;
                }
            }
        }
        if(toMove!=null && direction!=-1)
        {
            Node piece2 = toMove.getSibling(direction);
            Node destination = graph.destination(toMove, piece2, direction);
            int[] move = {toMove.getID(), destination.getID(), direction};
            return move;
        }
        else 
        {
            int[] move = {-1, -1, -1};
            return move;
        }
    }
    private int[] otherMove()
    {
        for(int i=0; i<nonEdgePieces.size(); i++)
        {
            for(int j=1; j<12; j++)
            {
                Node piece1 = nonEdgePieces.get(i);
                Node peice2 = piece1.getSibling(j);
                Node dest = graph.destination(piece1, peice2, j);
                if(dest!=null && !dest.isEdge())
                {
                    int[] move = {piece1.getID(), dest.getID(), j};
                    return move;

                }

            }
        }

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
