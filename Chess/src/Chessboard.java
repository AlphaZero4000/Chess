import java.util.ArrayList;
public class Chessboard 
{
	private Piece[][] board;
	
	public Chessboard()
	{
		board = new Piece[8][8];
		ArrayList<Piece> whitePieces = new ArrayList<Piece>();
		ArrayList<Piece> blackPieces = new ArrayList<Piece>();
		whitePieces.add(new Piece(true, 'R'));
		whitePieces.add(new Piece(true, 'N'));
		whitePieces.add(new Piece(true, 'B'));
		whitePieces.add(new Piece(true, 'Q'));
		whitePieces.add(new Piece(true, 'K'));
		whitePieces.add(new Piece(true, 'B'));
		whitePieces.add(new Piece(true, 'N'));
		whitePieces.add(new Piece(true, 'R'));
		blackPieces.add(new Piece(false, 'R'));
		blackPieces.add(new Piece(false, 'N'));
		blackPieces.add(new Piece(false, 'B'));
		blackPieces.add(new Piece(false, 'Q'));
		blackPieces.add(new Piece(false, 'K'));
		blackPieces.add(new Piece(false, 'B'));
		blackPieces.add(new Piece(false, 'N'));
		blackPieces.add(new Piece(false, 'R'));

		for (int c = 0; c < 8; c++)
		{
			int random = (int)(Math.random() * whitePieces.size());			
			board[0][c] = whitePieces.remove(random);
			board[1][c] = new Piece(true, 'P');
			board[6][c] = new Piece(false, 'P');
			board[7][c] = blackPieces.remove(random);
		}
		
	}
	
	public boolean move(int fromRow, int fromCol, int toRow, int toCol)
	{
		if (fromRow < 0 || fromRow > 7 || fromCol < 0 || toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7 || fromRow == toRow && fromCol == toCol || board[fromRow][fromCol] == null || board[fromRow][fromCol].isWhite() != ChessGame.isWhiteTurn() || board[toRow][toCol] != null && board[toRow][toCol].isWhite() == board[fromRow][fromCol].isWhite())
		{
			return false;
		}
		if (board[fromRow][fromCol].getPiece() == 'R' && !rookCanMove(fromRow, fromCol, toRow, toCol))
		{
			return false;
		}
		if (board[fromRow][fromCol].getPiece() == 'N' && !knightCanMove(fromRow, fromCol, toRow, toCol))
		{
			return false;
		}
		if (board[fromRow][fromCol].getPiece() == 'B' && !bishopCanMove(fromRow, fromCol, toRow, toCol))
		{
			return false;
		}
		if (board[fromRow][fromCol].getPiece() == 'Q' && !queenCanMove(fromRow, fromCol, toRow, toCol))
		{
			return false;
		}
		if (board[fromRow][fromCol].getPiece() == 'K' && !kingCanMove(fromRow, fromCol, toRow, toCol))
		{
			return false;
		}
		if (board[fromRow][fromCol].getPiece() == 'P' && board[toRow][toCol] == null && !pawnCanMove(fromRow, fromCol, toRow, toCol))
		{
			return false;
		}
		if (board[fromRow][fromCol].getPiece() == 'P' && board[toRow][toCol] != null && board[toRow][toCol].isWhite() != board[fromRow][fromCol].isWhite() && !pawnCanCapture(fromRow, fromCol, toRow, toCol))
		{
			return false;
		}
		
		Piece[][] temp = new Piece[8][8];
		for (int r = 0; r < temp.length; r++)
			for (int c = 0; c < temp[r].length; c++)
				temp[r][c] = board[r][c];
		
		board[toRow][toCol] = board[fromRow][fromCol];
		board[fromRow][fromCol] = null;
		if (board[toRow][toCol].getPiece() == 'P' && (toRow == 7 || toRow == 0))
		{
			board[toRow][toCol] = new Piece(board[toRow][toCol].isWhite(), ChessGame.promote());
		}
		if (ChessGame.isCheck())
		{
			board = temp;
			return false;
		}
		return true;
	}
	
	public boolean rookCanMove(int fromRow, int fromCol, int toRow, int toCol)
	{
		if (fromRow != toRow && fromCol != toCol)
		{
			return false;
		}
		if (fromRow == toRow)
		{
			int c = fromCol;
			while (Math.abs(c - toCol) != 1)
			{
				if (fromCol < toCol)
				{
					c++;
				}
				else
				{
					c--;
				}
				if (board[fromRow][c] != null)
				{
					return false;
				}				
			}
		}
		if (fromCol == toCol)
		{
			int r = fromRow;
			while (Math.abs(r - toRow) != 1)
			{
				if (fromRow < toRow)
				{
					r++;
				}
				else
				{
					r--;
				}
				if (board[r][fromCol] != null)
				{
					return false;
				}				
			}
		}
		return true;
	}
	
	public boolean bishopCanMove(int fromRow, int fromCol, int toRow, int toCol)
	{
		if (!(Math.abs(fromRow - toRow) == Math.abs(fromCol - toCol)))
		{
			return false;
		}
		int r = fromRow;
		int c = fromCol;
		while (Math.abs(r - toRow) != 1)
		{
			if (fromRow < toRow)
			{
				r++;
			}
			else
			{
				r--;
			}
			if (fromCol < toCol)
			{
				c++;
			}
			else
			{
				c--;
			}
			if (board[r][c] != null)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean knightCanMove(int fromRow, int fromCol, int toRow, int toCol)
	{
		return Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 1 || Math.abs(fromRow - toRow) == 1 && Math.abs(fromCol - toCol) == 2;
	}
	
	public boolean queenCanMove(int fromRow, int fromCol, int toRow, int toCol)
	{
		return bishopCanMove(fromRow, fromCol, toRow, toCol) || rookCanMove(fromRow, fromCol, toRow, toCol);
	}
	
	public boolean kingCanMove(int fromRow, int fromCol, int toRow, int toCol)
	{
		return (fromRow == toRow || Math.abs(fromRow - toRow) == 1 ) && (fromCol == toCol || Math.abs(fromCol - toCol) == 1);
	}
	
	public boolean pawnCanMove(int fromRow, int fromCol, int toRow, int toCol)
	{
		if (board[fromRow][fromCol].isWhite() && !((fromRow == 1 && toRow - fromRow == 2 || toRow - fromRow == 1) && fromCol == toCol))
		{
			return false;
		}
		if (!board[fromRow][fromCol].isWhite() && !((fromRow == 6 && toRow - fromRow == -2 || toRow - fromRow == -1) && fromCol == toCol))
		{
			return false;
		}
		return true;
	}
	
	public boolean pawnCanCapture(int fromRow, int fromCol, int toRow, int toCol)
	{
		if (board[fromRow][fromCol].isWhite() && !(toRow - fromRow == 1 && Math.abs(fromCol - toCol) == 1))
		{
			return false;
		}
		if (!board[fromRow][fromCol].isWhite() && !(toRow - fromRow == -1 && Math.abs(fromCol - toCol) == 1))
		{
			return false;
		}
		return true;
	}
	
	public Piece[][] getBoard()
	{
		return board;
	}
}
