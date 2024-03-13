package chess.piece;

@PieceInfo(name = Category.SPRINGER,value = 3,blackImagePath = "/pieceImages/SpringerBLACK.png",whiteImagePath = "/pieceImages/SpringerWHITE.png")
public class Springer extends Piece {

	
	public Springer(char color, int y, int x) {
		super(color, y, x);
		
	}

	@Override
	public boolean canMove(int y, int x) {
		
		if(((Math.abs(this.y-y) == 2 && Math.abs(this.x-x)== 1)
				
				|| (Math.abs(this.y-y) == 1 && Math.abs(this.x-x)== 2))
				
				
				
				
				) {
			
			return true;
			
		}
		
		return false;
	}

}
