import java.io.FileWriter;
import java.io.IOException;

/**
 * File Name: GraphDot.java
 * 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphDot{
	private Graph g ;
	private String fname;
	//You can have any number of private variables
	
	GraphDot(Graph g, String n) throws IOException {
		this.g = g ;
		this.fname = n ;
		dump() ;
	}
	
	/******************************************************************
									WRITE YOUR CODE BELOW
	 ******************************************************************/
	private void dump() throws IOException {
		String s1="digraph g{",s2="edge [color=red]",s3="}",s4="edge [dir=none,color=red]",s5="graph[concentrate=true]";
		System.out.println("See dot file at " + fname) ;
		FileWriter Fw = new FileWriter(fname);
		Fw.write(s1);
		Fw.write("\n");
		switch (g.type) {
			case UNDIRECTED -> {
				Fw.write(s4);
				Fw.write("\n");
				for (int i = 0; i < g.nodes.size(); i++) {
					for (Integer Key : g.getNode(i).fanout.keySet()) {
						if (i < Key) {
							Fw.write(g.io.getRealName(i) + " -> " + g.io.getRealName(Key) + ";");
							Fw.write("\n");
						}
					}
				}
			}
			case WEIGHTED_UNDIRECTED -> {
				Fw.write(s4);
				Fw.write("\n");
				for (int i = 0; i < g.nodes.size(); i++) {
					for (Integer Key : g.getNode(i).fanout.keySet()) {
						if (i < Key) {
							Fw.write(g.io.getRealName(i) + " -> " + g.io.getRealName(Key) + " [label = " + g.getNode(i).fanout.get(Key).cost + "]" + ";");
							Fw.write("\n");
						}
					}
				}
			}
			case DIRECTED -> {
				Fw.write(s2);
				Fw.write("\n");
				for (int i = 0; i < g.nodes.size(); i++) {
					for (Integer Key : g.getNode(i).fanout.keySet()) {
						Fw.write(g.io.getRealName(i) + " -> " + g.io.getRealName(Key) + ";");
						Fw.write("\n");
					}
				}
			}
			case WEIGHTED_DIRECTED -> {
				Fw.write(s2);
				Fw.write("\n");
				for (int i = 0; i < g.nodes.size(); i++) {
					for (Integer Key : g.getNode(i).fanout.keySet()) {
						Fw.write(g.io.getRealName(i) + " -> " + g.io.getRealName(Key) + " [label = " + g.getNode(i).fanout.get(Key).cost + "]" + ";");
						Fw.write("\n");
					}
				}
			}
		}
		Fw.write(s3);
		Fw.close();
	}
}