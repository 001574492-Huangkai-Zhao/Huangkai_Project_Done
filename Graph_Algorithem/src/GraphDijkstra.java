import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;


/**
 * File Name: GraphDijkstra.java 
 * Implements Dijkstra's algorithms
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2020
 */

/*
 * To compile you require: IntUtil.java RandomInt.java Graph.java GraphTest.javs GraphDijkstra.java
 */

class GraphDijkstra{
	//Note all fields are public
	public Graph g ;
	public String title;
	public String startCity;
	public String dotFile ; //Tree traversal picture as dot file
	public int [] work ; //Note public
	public double [] cost; //Note Public. The space is already allocated. Fill -1 if path does not exist
	//YOU CANNOT ADD ANTHING HERE
	
	GraphDijkstra(Graph g, String title,String startCity,String f, int[] work,double [] cost) throws IOException {
		//NOTHING CAN BE CHANGED BELOW
		this.g = g ;
		this.title = title ;
		this.startCity = startCity;
		this.dotFile = f ;
		this.work = work;
		this.cost = cost ;
		//You must implement DijkstraAlg as an object
		DijkstraAlg a = new DijkstraAlg(this);
	}
}
	
	/*--------------------------------------------------------------------------
	 *                        WRITE CODE BELOW
	 *          YOU CAN HAVE ANY NUMBER OF classes and private functions
	 ----------------------------------------------------------------------------*/
	
	
	/*--------------------------------------------------------------------------
	 *   Algorithm implementation as a class
	 ----------------------------------------------------------------------------*/
	class DijkstraAlg{
		private GraphDijkstra d ;
		private int numberofNodeAddedToHeap ;
		public final double INFINITY = Double.MAX_VALUE;
		//You can have any number of private classes and data structures
		
		
		DijkstraAlg(GraphDijkstra d) throws IOException {
			this.d = d ;
			numberofNodeAddedToHeap = 0 ;
			this.d.g.u.openDotFile(d.dotFile);
			alg();	//in alg use: d.g.u.appendDotFile(d.dotFile,t); //t is the string you are appending
			this.d.g.u.closeDotFile(d.dotFile) ;
		}
		
		private void updateWorkDone(int n) {
			d.work[0] = d.work[0] + n ;
		}
	
		private void Statistics() {
			System.out.println(GraphType.gtype(d.g.type));
			System.out.println("Num Vertices = " + d.g.numV()) ;
			System.out.println("Num Edges    = " + d.g.numE()) ;
			System.out.println("Work done    = " + d.work[0]) ;
			System.out.println("numberofNodeAddedToHeap = " + numberofNodeAddedToHeap) ;
		}
		
		/*
		 * Dijkstra algorithm
		 * Time: O(V + E)
		 * Space: O(V)
		 */
		private void alg() throws IOException {
			//WRITE CODE
			FileWriter Fw = new FileWriter(d.dotFile);
			String s1="digraph g{",s2="[label = ",s3="[xlabel = ";
			PriorityQueue<Vertices> pQueue = new PriorityQueue<>();
			Vertices vTop;
			Vertices vTemp;
			double vTopCost;
			double currentCost;
			int vNum=d.g.numV();
			int xLabel=1;
			int startCityIndex;
			boolean isRelaxed=false;
			String realName;
			Vertices[] vArray= new Vertices[vNum];
			for (int i=0;i<vNum;i++)
			{
				vArray[i]= new Vertices(i,INFINITY);
			}
			Fw.write(s1);
			Fw.write("\n");
			startCityIndex=d.g.io.insertOrFind(d.startCity,true);
			vArray[startCityIndex].setCost(0);
			pQueue.add(vArray[startCityIndex]);
			numberofNodeAddedToHeap++;
			while(!pQueue.isEmpty())
			{
				vTop=pQueue.remove();
				if(!vTop.isVisited())
				{
					vTopCost=vTop.getCost();
					d.work[0]++;
					for(Integer key : d.g.getNode(vTop.getName()).fanout.keySet())
					{
						currentCost=d.g.getNode(vTop.getName()).fanout.get(key).cost+vTopCost;
						if(currentCost<vArray[key].getCost())
						{
							realName=d.g.io.getRealName(vTop.getName());
							vArray[key].setCost(currentCost);
							pQueue.add(vArray[key]);
							numberofNodeAddedToHeap++;
							isRelaxed=true;
							Fw.write(realName+"->"+d.g.io.getRealName(key));
							Fw.write(s2+currentCost+"]");
							Fw.write(s3+xLabel+"]");
							Fw.write("\n");
						}
						d.work[0]++;
					}
					vArray[vTop.getName()].setVisited();
					if (isRelaxed)
						xLabel++;
					isRelaxed=false;
				}
			}
			Fw.write("}");
			Fw.close();
			for (int i=0;i<vNum;i++)
			{
				if(vArray[i].getCost()!=INFINITY)
					d.cost[i]=vArray[i].getCost();
				else
					d.cost[i]=-1;
			}
			Statistics();
		}
		public static void main(String[] args) {
			System.out.println("GraphDijkstra.java starts");
			System.out.println("Use GraphTest.java to test");
			System.out.println("GraphDijkstra.java Ends");
		}
}
class Vertices implements Comparable<Vertices>{
		private int name;
		private double cost;
		private boolean visited;
		
		Vertices(int name,double cost)
		{
			this.cost=cost;
			visited=false;
			this.name=name;
		}

		@Override
		public int compareTo(Vertices v)
		{
			if(this.cost > v.getCost()) {
				return 1;
			} else if (this.cost < v.getCost()) {
				return -1;
			} else {
				return 0;
			}
		}


	public void setVisited() {
		visited=true;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getCost() {
		return cost;
	}

	public boolean isVisited() {
		return visited;
	}

	public int getName() {
		return name;
	}
}