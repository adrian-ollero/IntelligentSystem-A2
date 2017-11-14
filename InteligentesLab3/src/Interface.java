import java.io.*;
import java.util.*;

public class Interface {

	static Scanner sc= new Scanner(System.in);
	static UninformedSearch uSearch= new UninformedSearch();
	/***********************************************************************************************************
	 * Class name: Main
	 * Description: in this class all computations of the executions of the problem are performed, including
	 * the main method with an example of a performed action on the field
	 * @author Adrian Ollero Jimenez, Enrique Garrido Pozo, Pablo Mora Herreros 
	 * Subject: Intelligent Systems
	 * Group: A2
	 **********************************************************************************************************/
	public static void main(String[] args) throws IOException {
		String path= "Output.txt";
		File file=new File(path);
		BufferedWriter br;
		br= new BufferedWriter(new FileWriter(file));
		String inputFile;
		System.out.println("Input File: ");
		inputFile= sc.next();
		Problem prob = new Problem(inputFile);
		Strategy str=menu();
		int max= obtainValue("Max depth: ");
		int incr;
		if (str==Strategy.IDS) {
			incr= obtainValue("Increment: ");
		}else {
			incr=1;
		}
		ArrayList<Node> list=uSearch.search(prob, str, max, incr);
		for(int i = 0; i<list.size(); i++) {
			System.out.println(i+1 + "- "+list.get(list.size()-i-1).getAction());
			br.write(i+1 + "- "+list.get(list.size()-i-1).getAction());
			System.out.print("\n");
			br.write("\n");
			System.out.print(list.get(list.size()-i-1).getState().saveMatrix());
			br.write(list.get(list.size()-i-1).getState().saveMatrix());
			br.newLine();
			br.newLine();
		}
		br.close();
	}
	/**************************************************************************************
	 * Class name: menu
	 * Description: This is a menu that we use to choose the strategy that we will use.
	 * @return str -> It returns the strategy that we will use to aplicate later
	 **************************************************************************************/
	public static Strategy menu() {
		boolean select=true;
		int opt;
		Strategy str=null;
		while(select==true) {
			System.out.println("Choose a strategy: \n1-BFS \n2-DFS \n3-DLS \n4-IDS \n5-UCS");
			opt=sc.nextInt();
			switch(opt) {
			case 1:
				str=Strategy.BFS;
				select=false;
				break;
			case 2:
				str=Strategy.DFS;
				select=false;
				break;
			case 3: 
				str= Strategy.DLS;
				select=false;
				break;
			case 4:
				str= Strategy.IDS;
				select=false;
				break;
			case 5:
				str= Strategy.UCS;
				select=false;
				break;
			default: 
				System.out.println("Error. Write a number between 1-5");
			}
		}
		return str;
	}
	/*********************************************************************************
	 * Method: obtainValue
	 * Description: This is method to check that the number that we are going to 
	 * introduce is not a negative number
	 * @param cadena -> The program we will ask to us about what the program wants
	 * @return value -> The value that we want
	 ********************************************************************************/
	public static int obtainValue(String cadena) {
		System.out.println(cadena);
		int value=sc.nextInt();
		while (value <= 0){					
			System.out.println("Error. Choose another number");	
			System.out.print(cadena);
			value=sc.nextInt();
		}
		return value;
	}
}