import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * File Name: GraphDFSUsingTimeStamp.java
 * 
 * 
 * @author Jagadeesh Vasudevamurthy
 * @year 2021
 */

class GraphDFSUsingTimeStamp{
	private Graph g ;
	private int [] work ;
	private boolean [] cycle;
	private ArrayList<Integer> topologicalOrderArray;
	private String f;
	private int In=1;
	//You can have any number of private classes, variables and functions
	
	GraphDFSUsingTimeStamp(Graph g, int [] work, boolean [] cycle,ArrayList<Integer> topologicalOrderArray,String f) throws IOException {
		this.g = g ;
		this.work = work ;
		this.cycle = cycle ;
		this.topologicalOrderArray = topologicalOrderArray ;
		this.f = f ;
		//You MUST WRITE 2 routines
		HashMap<Integer,InAndOut> IOState= new HashMap();
		dfs(IOState) ;
		bfs();
		writeDFSDot(IOState) ;

	}

	private void writeDFSDot(HashMap<Integer,InAndOut> IOState) throws IOException {

		String s1="digraph g{",s2="edge [color=red]",s3="}",s4="edge [dir=none,color=red]",s5="graph[concentrate=true]";
		String sl1="label = \"[",sl2a="] LOOP\"",sl2b="] NOLOOP\"",sl3="[label = <",sl4="<BR /><FONT POINT-SIZE=\"10\">",sl5="</FONT>>]";
		FileWriter Fw = new FileWriter(f);
		Fw.write(s1);
		Fw.write("\n");
		Fw.write(sl1);
		for (int i : topologicalOrderArray) {
			String RealName=g.io.getRealName(i);
			Fw.write(RealName+" ");
		}
		if(cycle[0])
		{
			Fw.write(sl2a);
		}
		else
		{
			Fw.write(sl2b);
		}
		Fw.write("\n");
		for (int i=0;i<g.numV();i++) {
			String RealName=g.io.getRealName(i);
			String IO= String.valueOf(IOState.get(i).RIn())+"/"+String.valueOf(IOState.get(i).ROut());
			Fw.write(RealName+sl3+RealName+sl4+IO+sl5);
			Fw.write("\n");
		}

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

	/*
	 * WRITE CODE BELOW 
	 * //YOU CAN HAVE ANY NUMBER OF PRIVATE VARIABLES, DATA STRUCTURES AND FUNCTIONS
	 */
	public void dfs(HashMap<Integer,InAndOut> IOState) {

		for(int i=0;i<g.numV();i++)
		{
			InAndOut A=new InAndOut();
			IOState.put(i,A);
		}
		for(int i=0;i<g.numV();i++)
		{
			InAndOut Node=IOState.get(i);
			if(!Node.RTraversed())
			{
				DFSRecursion(i,IOState);
			}
			work[0]++;
		}
		int i=0;
		int j=topologicalOrderArray.size()-1;
		int temp;
		while(j>i)
		{
			temp = topologicalOrderArray.get(i);
			topologicalOrderArray.set(i,topologicalOrderArray.get(j));
			topologicalOrderArray.set(j,temp);
			i++;
			j--;
		}

	}
	private void DFSRecursion(int name,HashMap<Integer,InAndOut> IOState,int Parent)
	{
		IOState.get(name).SetIn(In);
		In++;
		for(Integer Key:g.getNode(name).fanout.keySet())
		{
				if(Key!=Parent) {
					if(!IOState.get(Key).RTraversed()&&IOState.get(Key).RIn()!=0)
					{
						cycle[0]=true;
					}
					else if(IOState.get(Key).RTraversed())
					{
						//do nothing
					}
					else if(!IOState.get(Key).RTraversed()&&IOState.get(Key).RIn()==0)
					{
						DFSRecursion(Key,IOState,name);
					}
				}
			work[0]++;
		}
		IOState.get(name).SetOut(In);
		In++;
		IOState.get(name).SetTraversed();
		topologicalOrderArray.add(name);
	}
	private void DFSRecursion(int name,HashMap<Integer,InAndOut> IOState)
	{
		IOState.get(name).SetIn(In);
		In++;
		for(Integer Key:g.getNode(name).fanout.keySet())
		{
				if(!IOState.get(Key).RTraversed()&&IOState.get(Key).RIn()!=0)
				{
					cycle[0]=true;
				}
				else if(IOState.get(Key).RTraversed())
				{
					//do nothing
				}
				else if(!IOState.get(Key).RTraversed()&&IOState.get(Key).RIn()==0)
				{
					DFSRecursion(Key,IOState,name);
				}
			work[0]++;
		}
		IOState.get(name).SetOut(In);
		In++;
		IOState.get(name).SetTraversed();
		topologicalOrderArray.add(name);
	}
	public void bfs(){
		LevelAndParent [] v =new LevelAndParent[g.numV()];
		Queue<Integer> q = new LinkedList<>();
		ArrayList<Integer> bfsOrder = new ArrayList<>();
		ArrayList<Integer> bfsPath = new ArrayList<>();
		int level;
		int parent;
		int work=0;
		for (int i=0;i<g.numV();i++)
		{
			v[i]=new LevelAndParent();
		}
		for (int i=0;i<g.numV();i++)
		{
			if(v[i].reLevel()==-1)
			{
				v[i].setParent(i);
				v[i].setLevel(0);
				q.add(i);
				v[i].setVisited();

				while(!q.isEmpty()){
					parent=q.remove();
					bfsOrder.add(parent);
					bfsPath.add(v[parent].reParent());
					level=v[parent].reLevel()+1;
					work++;
					for (Integer key : g.getNode(parent).fanout.keySet())
					{
						if(!v[key].reVisited())
						{
							q.add(key);
							v[key].setParent(parent);
							v[key].setLevel(level);
							v[key].setVisited();
						}
						work++;
					}

				}
			}
		}
		for (int i=0;i< g.numV();i++)
		{
			System.out.print(g.io.getRealName(bfsOrder.get(i))+" ");
		}
		System.out.println();
		for (int i=0;i< g.numV();i++)
		{
			System.out.print(g.io.getRealName(bfsPath.get(i))+" ");
		}
		System.out.println();
		System.out.println("work = "+work);
	}

}
class LevelAndParent{
	private int level;
	private int parent;
	private boolean visited;
	LevelAndParent()
	{
		level=-1;
		parent=-1;
		visited=false;
	}
	public void setLevel(int level){
		this.level=level;
	}
	public void setParent(int parent){
		this.parent=parent;
	}
	public void setVisited(){
		visited=true;
	}
	public int reLevel(){
		return level;
	}
	public int reParent(){
		return parent;
	}
	public boolean reVisited(){
		return visited;
	}
}




class InAndOut{
	private int In;
	private int Out;
	private boolean IsTraversed;
	InAndOut(){
		In=0;
		Out=0;
		IsTraversed=false;

	}
	public int RIn() {return In;}
	public int ROut() {return Out;}
	public void SetIn(int In){this.In=In;}
	public void SetOut(int Out){this.Out=Out;}
	public boolean RTraversed(){return IsTraversed;}
	public void SetTraversed(){IsTraversed=true;}
}

