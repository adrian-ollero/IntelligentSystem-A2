public class Action {

	private int xt, yt; //Position where the tractor is moved
	private int[] sandMovement;
	
	public Action(int[] coor, int[] sand) {
		xt = coor[0];
		yt = coor[1];
		sandMovement = sand.clone();
	}
	
	public String toString() {
		return "Tractor: ("+xt+", "+yt+") Sand: ["+sandMovement[0]+", "+sandMovement[1]+", "+sandMovement[2]+
				", "+sandMovement[3]+", "+sandMovement[4]+"]";
	}
	
	/*************************************************************************************
	 * Method name: perform
	 * Description: modifies the state of the field passed as argument with the information
	 * set on the attributes, changing the position of the tractor and modifying the 
	 * quantity on the field
	 * @param field -> current state of the field
	 ************************************************************************************/
	public void perform(Field field) {
		if(sandMovement[1]!=0) {//To North
			field.setNumber(field.getXt()-1, field.getYt(), field.getNumber(field.getXt()-1, field.getYt())+sandMovement[1]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[1]);
		}
		if(sandMovement[2]!=0) { //To West
			field.setNumber(field.getXt(), field.getYt()-1, field.getNumber(field.getXt(), field.getYt()-1)+sandMovement[2]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[2]);
		}
		if(sandMovement[3]!=0) { //To East
			field.setNumber(field.getXt(), field.getYt()+1,field.getNumber(field.getXt(), field.getYt()+1)+ sandMovement[3]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[3]);
		}
		if(sandMovement[4]!=0) { //To South
			field.setNumber(field.getXt()+1, field.getYt(), field.getNumber(field.getXt()+1, field.getYt())+sandMovement[4]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[4]);
		}
		
		field.setXt(xt);
		field.setYt(yt);
	}
}//End class
