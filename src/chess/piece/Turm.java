package chess.piece;

@PieceInfo(name = Category.TURM,value = 5,blackImagePath = "/pieceImages/TurmBLACK.png",whiteImagePath = "/pieceImages/TurmWHITE.png")
public class Turm extends Piece {

	public Turm(char color, int y, int x) {
		super(color, y, x);
		
	}

	@Override
	public boolean canMove(int y, int x) {
		
		if((Math.abs(this.y-y) > 0 && Math.abs(this.x-x)== 0)
				
				|| Math.abs(this.y-y) == 0 && Math.abs(this.x-x)> 0
				
				) {
			
			return true;
			
		}
		
		return false;
	}

}
