
public class Piece 
{
	private boolean isWhite;
	private char piece;

	public Piece(boolean b, char p)
	{
		isWhite = b;
		piece = p;

	}
	
	public boolean isWhite()
	{
		return isWhite;
	}
	
	public char getPiece()
	{
		return piece;
	}
}
