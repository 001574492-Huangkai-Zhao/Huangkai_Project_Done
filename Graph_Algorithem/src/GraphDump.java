import java.util.HashMap;

/**
 * File Name: GraphDump.java
 * 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphDump{
	private Graph g ;
	private String name;
	//You can have any number of private variables
	
	GraphDump(Graph g, String n) {
		this.g = g ;
		this.name = n ;
		dump() ;
	}
	
	/******************************************************************
  									WRITE YOUR CODE BELOW
	******************************************************************/
	private void dump() {
		System.out.println("------------ " + name + " ------------");
		System.out.println("GraphType."+g.type);
		System.out.println("Num Vertices = "+g.numV());
		System.out.println("Num Edges  = "+g.numEdges);
		for(int i=0;i<g.nodes.size();i++)
		{

			System.out.print(g.io.getRealName(i)+" ");
			System.out.print("FanOuts: ");
			int Size=g.getNode(i).fanout.size();
			if(Size==0)
			{
				System.out.print("NONE");
			}
			else
			{
				for(Integer Key:g.getNode(i).fanout.keySet())
				{
					if(Size==1)
					{
						System.out.print(g.io.getRealName(Key));
					}
					else
					{
						System.out.print(g.io.getRealName(Key)+",");
					}
					Size--;
				}
			}
			System.out.println();
			System.out.print(g.io.getRealName(i)+" ");
			System.out.print("FanIns: ");
			Size=g.getNode(i).fanin.size();
			if (Size==0)
			{
				System.out.print("NONE");
			}
			else
			{
				for(Integer Key:g.getNode(i).fanin.keySet())
				{
					if(Size==1)
					{
						System.out.print(g.io.getRealName(Key));
					}
					else
					{
						System.out.print(g.io.getRealName(Key)+",");
					}
					Size--;
				}
			}
			System.out.println();
		}
	}
	

}