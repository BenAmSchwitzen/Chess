package chess.piece;

@PieceInfo(name = Category.KING,value = 0,blackImagePath = "/pieceImages/KönigBLACK.png",whiteImagePath = "/pieceImages/KönigWHITE.png")
public class König extends Piece {

	public König(char color, int y, int x) {
		super(color, y, x);
		
	}

	@Override
	public boolean canMove(int y, int x) {
		
		if((Math.abs(this.y-y)== 1 && Math.abs(this.x-x) == 1)
				
				|| (Math.abs(this.y-y)== 1 && Math.abs(this.x-x) == 0)
				
				|| (Math.abs(this.y-y)== 0 && Math.abs(this.x-x) == 1)
				
				) {
			
			return true;
			
		}
		
		
		
		return false;
	}

}
