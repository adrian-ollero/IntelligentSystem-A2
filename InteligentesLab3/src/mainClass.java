import java.util.*;
import java.io.*;

public class mainClass {
	
	static boolean [] positions= new boolean[4];
	
	public static void main(String[] args) throws IOException {
		// TO-DO 
		// - File writter
		// - Comprobar que la cantidad a la posicion a la que movemos es menor que el max
		// - Errores de numeros negativos
		// - Try catch for the standard input
		
		ArrayList<Action> actions;
		Field field = new Field();
		field.readField();
		System.out.println("-----ATTRIBUTES----- \nXt: " + field.getXt());
		System.out.println("Yt: " + field.getYt());
		System.out.println("K: " + field.getK());
		System.out.println("Max: " + field.getMax());
		System.out.println("Size column: " + field.getSizeC());
		System.out.println("Size Row: " + field.getSizeR() + "\n-----MATRIX-----");
		field.printMatrix();
		actions=successor(field);
		
		////////////////////////////////////
		System.out.println("--------PERMFORM AN ACTION------");
		actions.get(8).perform(field);
		String nextMov= " Performing the action: "+actions.get(8) +"\n Xt: " + field.getXt() + "\n Yt: " + field.getYt() + "\n K: " + field.getK() + "\n Max: " + field.getMax() + "\n Size column: " + field.getSizeC() + "\n Size Row: " + field.getSizeR() + "\n -----MATRIX-----";
		System.out.println(nextMov);
		field.printMatrix();
		String matrix=field.saveMatrix();
		///////////////////////////////////
		field.generateOutput(nextMov, matrix);
	}
	
	/********************************************************************************
	 * Method name: successor
	 * Description: is in charge of all tasks related to the successors creation
	 * obtaining the adjacent positions where the tractor can move and all the 
	 * possible sand distributions and combine them all
	 * @param field -> current state of the field and the tractor position
	 ********************************************************************************/
	private static ArrayList<Action> successor(Field field) {
		ArrayList <int[]> adjacentPositions, sandMovements;
		ArrayList <Action> actions =  new ArrayList<Action>();
		sandMovements= new ArrayList<int[]>();
		checkPositions(field);
		adjacentPositions=moveTractor(field);
		printAdjacent(adjacentPositions, field);
		moveSand(field, sandMovements);
		///////////////////////////
		removeMax(sandMovements, field);
		///////////////////////////
		createActions(adjacentPositions, sandMovements, actions);
		printActions(actions);
		return actions;
	}
	
	private static void removeMax(ArrayList<int[]> sandMovements, Field field) {
		ArrayList<int[]> aux = new ArrayList<int[]>();
		Iterator<int[]> it = sandMovements.iterator();
		for(int i = 0; i< sandMovements.size(); i++) {
			int s [] = sandMovements.get(i);
			if( (s[1]!=0 && s[1]+field.getNumber(field.getXt()-1, field.getYt()) > field.getMax()) || //North
				(s[2]!=0 && s[2]+field.getNumber(field.getXt(), field.getYt()-1) > field.getMax()) || //West
				(s[3]!=0 && s[3]+field.getNumber(field.getXt(), field.getYt()+1) > field.getMax()) || //East
				(s[4]!=0 && s[4]+field.getNumber(field.getXt()+1, field.getYt()) > field.getMax())	  //South V
			) 
				aux.add(s);
		}
		sandMovements.removeAll(aux);
	}
	
	/********************************************************************************
	 * Method name: moveTractor
	 * Description: checks whether the tractor can move to each of the four directions
	 * of the field, for that change the values of a boolean array format as 
	 * [N, W, E, S], and that saves a true if it is able to move and false otherwise
	 * @param field -> current state
	 * @return an ArrayList with all the possible movements of the tractor, which
	 * are, the adjacent positions of the current one
	 *******************************************************************************/
	private static ArrayList<int[]> moveTractor(Field field) {
	ArrayList <int[]> adjacent= new ArrayList<int[]>();
	//NORTH		
		if (positions[0]==true) {
			int [] vectorn= new int[2];
			vectorn[0]= field.getXt()-1;
			vectorn[1]= field.getYt();
			adjacent.add(vectorn);
		}
	//WEST
		if (positions[1]==true) {
			int [] vectorw= new int[2];
			vectorw[0]= field.getXt();
			vectorw[1]= field.getYt()-1;
			adjacent.add(vectorw);
		}
	//EAST
		if (positions[2]==true) {
			int [] vectore= new int[2];
			vectore[0]= field.getXt();
			vectore[1]= field.getYt()+1;
			adjacent.add(vectore);
		}
	//SOUTH
		if (positions[3]==true) {
			int [] vectors= new int[2];
			vectors[0]= field.getXt()+1;
			vectors[1]= field.getYt();
			adjacent.add(vectors);
		}
		return adjacent;
	}

	/*******************************************************************************
	 * Method name: printAdjacent
	 * @param adjacent -> list of available adjacent positions
	 * @param field -> current state of the field
	 *******************************************************************************/
	public static void printAdjacent (ArrayList<int[]> adjacent, Field field) {
		Iterator <int[]> it;
		it=adjacent.iterator();
		System.out.println("-----ADJACENT POSITIONS-----");
		while(it.hasNext()) {
			int [] vector= it.next();
			System.out.println(vector[0] +" "+ vector[1] + "----->" +field.getNumber(vector[0], vector[1]));
		}
	}
	
	/******************************************************************************
	 * Method name: moveSand
	 * Description: begins the recursive algorithm that gets all possible sand
	 * distributions in the field
	 * @param field -> current state
	 * @param sandMovements -> set of all possible distributions of sand 
	 * from the current position
	 ******************************************************************************/
	public static void moveSand(Field field, ArrayList<int[]> sandMovements) {
		int [] distribution =  new int[5]; //[pos actual, norte, oeste, este sur]
		distribution[0]=field.getNumber(field.getXt(), field.getYt());
		loop(0, distribution, sandMovements, field);
	}
	
	/*******************************************************************************
	 * Method name: loop
	 * Description: recursive algorithm that combines the quantity of sand available in
	 * the current position of the tractor so we obtain the different possible distributions
	 * @param position -> pointer to the element that is losing sand
	 * @param distribution -> array where the quantity of sand moved is format as [D, N, W, E, S]
	 * where D is the sand not moved
	 * @param solution -> list where a distribution is saved when it is computed
	 * @param field -> current state
	 *********************************************************************************/
	private static void loop(int position, int [] distribution, ArrayList<int[]> solution, Field field) {
		int[] auxDistribution;
		int nextPos;
		auxDistribution = distribution.clone();
		solution.add(distribution.clone());
		if(position < distribution.length) {
			nextPos = nextPosAvailable(position, distribution, field);
			for(int j=distribution[position]; j>0; j--) {	
				//nextPos = nextPosAvailable(position, distribution, field);
				if(nextPos < distribution.length) {// && roomForSand(nextPos, distribution, field)) {
					auxDistribution[position]--;
					auxDistribution[nextPos]++;
					//if(!roomForSand(nextPos, auxDistribution, field)) continue;
					//else 
					loop(nextPos, auxDistribution, solution, field);
				}
			}
		}else return;
	}
	
	/*************************************************************************************
	 * Method name: nextPositions
	 * Description: computes which is the next positions that can store values checking if
	 * there is adjacent position in each of the four directions
	 * @param position -> current pointer in distribution
	 * @param distribution -> array with format [D, N, W, E, S] where the amount of sand
	 * moved is stored
	 * @param field -> current state
	 * @return the index of the next available position or 5 if there is no more positions
	 *************************************************************************************/
	private static int nextPosAvailable(int position, int [] distribution, Field field) {
		for(int i = position+1; i<distribution.length; i++) {
			if(positions[i-1]) { //Checks if current position is a valid position
				if(i-1 == 0 && field.getNumber(field.getXt()-1, field.getYt())+distribution[1]<field.getMax()) //North
					return i;
				if(i-1 == 1 && field.getNumber(field.getXt(), field.getYt()-1)+distribution[2]<field.getMax()) //West
					return i;
				if(i-1 == 2 && field.getNumber(field.getXt(), field.getYt()+1)+distribution[3]<field.getMax()) //East
					return i;
				if(i-1 == 3 && field.getNumber(field.getXt()+1, field.getYt())+distribution[4]<field.getMax()) //South
					return i;
			}
		}
		/*for(int i = position+1; i<distribution.length; i++) {
			if(positions[i-1]) { //Checks if current position is a valid position
				return i;
			}
		}*/
		return 5; //Key to say there's no more places to place sand
	}
	
	private static boolean roomForSand(int nextPos, int[] distribution, Field field) {
		if(
				(nextPos == 1 && field.getNumber(field.getXt()-1, field.getYt())+distribution[1]<field.getMax()) || //North
				(nextPos == 2 && field.getNumber(field.getXt(), field.getYt()-1)+distribution[2]<field.getMax()) || //West
				(nextPos == 3 && field.getNumber(field.getXt(), field.getYt()+1)+distribution[3]<field.getMax()) || //East
				(nextPos == 4 && field.getNumber(field.getXt()+1, field.getYt())+distribution[4]<field.getMax())	//South
			)
			return true;
		else
			return false;
	}
	
	/************************************************************************************
	 * Method name: createActions
	 * Description: combines all adjacent positions where the tractor can move with all
	 * sand distributions
	 * @param adjacentPositions -> set of coordinates where the tractor can move
	 * @param sandMovements -> set of all possible distributions of sand
	 * @param actions -> list where all actions are being stored
	 ************************************************************************************/
	private static void createActions(ArrayList<int[]> adjacentPositions, ArrayList<int[]> sandMovements,
									  ArrayList<Action> actions) {
		for(int i=0; i<adjacentPositions.size(); i++) {
			for(int j=0; j<sandMovements.size(); j++) {
				actions.add(new Action(adjacentPositions.get(i), sandMovements.get(j)));
			}
		}
	}
	
	/*************************************************************************************
	 * Method name: printAction
	 * Description: print the set of actions that has been computed
	 * @param actions -> set of actions
	 *************************************************************************************/
	private static void printActions(ArrayList<Action> actions) {
		Iterator<Action> it = actions.iterator();
		System.out.println("-----ACTIONS-----");
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}
	
	/**************************************************************************************
	 * Method name: checkPositions
	 * Description: checks if the tractor can move to each of the four directions
	 * and set value in positions to true if there is available position in that direction
	 * and set value to false if there is no position. positions is format as [N, W, E, S]
	 * @param field -> current state
	 *************************************************************************************/
	public static void checkPositions(Field field) {
		//NORTH		
			if (field.getXt() > 0 && field.getXt() < field.getSizeR()) {
				positions[0]= true;
			}else {
				positions[0]=false;
			}
		//WEST
			if (field.getYt() > 0 && field.getYt() < field.getSizeC()) {
				positions[1]=true;
			}else {
				positions[1]=false;
			}
		//EAST
			if (field.getYt() < field.getSizeC()-1) {
				positions[2]=true;
			}else {
				positions[2]=false;
			}
		//SOUTH
			if (field.getXt() < field.getSizeR()-1) {
				positions[3]=true;
			}else {
				positions[3]=false;
			}
	}
	
}//End mainClass
