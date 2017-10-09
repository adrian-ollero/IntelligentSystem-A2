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
	
	public void perform(Field field) {
		//Comprobar q la cantidad en sandMovement en una posicion es mayor que 0 y si lo es entonces sumar la arena en field en la direccion
		//que sea y restarselo a la posicion actual
		if(sandMovement[1]!=0) {//Al norte
			field.setNumber(field.getXt()-1, field.getYt(), field.getNumber(field.getXt()-1, field.getYt())+sandMovement[1]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[1]);
		}
		if(sandMovement[2]!=0) { //Al oeste
			field.setNumber(field.getXt(), field.getYt()-1, field.getNumber(field.getXt(), field.getYt()-1)+sandMovement[2]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[2]);
		}
		if(sandMovement[3]!=0) { //Al este
			field.setNumber(field.getXt(), field.getYt()+1,field.getNumber(field.getXt(), field.getYt()+1)+ sandMovement[3]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[3]);
		}
		if(sandMovement[4]!=0) { //Al sur
			field.setNumber(field.getXt()+1, field.getYt(), field.getNumber(field.getXt()+1, field.getYt())+sandMovement[4]);
			field.setNumber(field.getXt(), field.getYt(), field.getNumber(field.getXt(), field.getYt())-sandMovement[4]);
		}
		
		field.setXt(xt);
		field.setYt(yt);
	}
	
}
