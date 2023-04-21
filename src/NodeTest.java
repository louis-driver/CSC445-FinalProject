package src;

public class NodeTest
{
    public static void main(String[] args)
    {
        Node node1 = new Node(1, 1, false);
        Node node2 = new Node(0, 2, false);

        node1.setSibling(node2, 1);
        node2.setSibling(node1, 7);

        System.out.println(node1.getColor());
        System.out.println(node1.getSibling(1).getColor());
    }
}

