package chess.piece;


@PieceInfo(name = Category.BAUER,value = 1,blackImagePath = "/pieceImages/BauerBLACK.png",whiteImagePath = "/pieceImages/BauerWHITE.png")
public class Bauer extends Piece {

	
	
	
	
	public Bauer(char color,int y,int x) {
		super(color, y , x);
		
		
		
		
	}

	@Override
	public boolean canMove(int y, int x) {
		
		int diffY = this.y-y;
		int diffX = this.x-x;
		
		// One step && two steps(without enemy)
		
		if(diffY == 1 && diffX == 0 && this.color == 'w') {
			
			return true;
			
		}
		
         if(diffY == -1 && diffX == 0 && this.color == 'b') {
			
			return true;
			
		}
         
         
        
	
	return false;
	
}
}