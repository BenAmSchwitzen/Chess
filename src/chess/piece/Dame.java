package chess.piece;


@PieceInfo(name = Category.DAME,value = 9,blackImagePath = "/pieceImages/DameBLACK.png",whiteImagePath = "/pieceImages/DameWHITE.png")
public class Dame extends Piece {

	public Dame(char color, int y, int x) {
		super(color, y, x);
		
	}

	@Override
	public boolean canMove(int y, int x) {
		
		if((Math.abs(this.y-y)== Math.abs(this.x-x) && Math.abs(this.y-y)!=0)
				
				|| (Math.abs(this.y-y)> 0 && Math.abs(this.x-x) == 0)
				
				|| (Math.abs(this.y-y)== 0 && Math.abs(this.x-x) > 0)
				
				) {
			
			return true;
			
		}
		
		
		return false;
	}

}
