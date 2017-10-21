
public class Node {
	
	private Node parent;
	
	private Field state;
	private int cost;
	private Action action;
	private int depth;
	private int value;
	
	public Node(Node parent, Field state, int cost, Action action, int depth, int value) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
		this.action = action;
		this.depth = depth;
		this.value = value;
	}
	

	public Node(Field state, int cost, Action action, int depth, int value) {
		this.state = state;
		this.cost = cost;
		this.action = action;
		this.depth = depth;
		this.value = value;
	}



	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Field getState() {
		return state;
	}

	public void setState(Field state) {
		this.state = state;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	
	
}
