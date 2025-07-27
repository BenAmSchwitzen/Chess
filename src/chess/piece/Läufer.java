package chess.piece;

@PieceInfo(name = Category.LÄUFER,value = 5,blackImagePath = "/pieceImages/bb.png",whiteImagePath = "/pieceImages/wbi.png")

public class Läufer extends Piece {

	public Läufer(char color, int y, int x) {
		super(color, y, x);
		
		
	}

	@Override
	public boolean canMove(int y, int x) {
	
		if((Math.abs(this.y-y)  == Math.abs(this.x-x)) && (Math.abs(this.y-y) !=0)) {
			
			return true;
			
		}
		
		return false;
	}

	@Override
	public Piece pCopy() {
		
		
	   return new Läufer(color, drawY, drawX);
		
	}

}
