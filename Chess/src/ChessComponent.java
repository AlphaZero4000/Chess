import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.ImageIcon;
public class ChessComponent extends JComponent
{  
	public final static int size = 62;
	public Point highlight;
	public boolean insufficient = false;
	public boolean checkmate = false;
	public boolean promotion = false;
	public void draw(Graphics g)
	{
		ImageIcon wPawn = new ImageIcon(getClass().getResource("/Images/WhitePawn.png"));
		ImageIcon wRook = new ImageIcon(getClass().getResource("/Images/WhiteRook.png"));
		ImageIcon wKnight = new ImageIcon(getClass().getResource("/Images/WhiteKnight.png"));
		ImageIcon wBishop = new ImageIcon(getClass().getResource("/Images/WhiteBishop.png"));
		ImageIcon wQueen = new ImageIcon(getClass().getResource("/Images/WhiteQueen.png"));
		ImageIcon wKing = new ImageIcon(getClass().getResource("/Images/WhiteKing.png"));
		ImageIcon bPawn = new ImageIcon(getClass().getResource("/Images/BlackPawn.png"));
		ImageIcon bRook = new ImageIcon(getClass().getResource("/Images/BlackRook.png"));
		ImageIcon bKnight = new ImageIcon(getClass().getResource("/Images/BlackKnight.png"));
		ImageIcon bBishop = new ImageIcon(getClass().getResource("/Images/BlackBishop.png"));
		ImageIcon bQueen = new ImageIcon(getClass().getResource("/Images/BlackQueen.png"));
		ImageIcon bKing = new ImageIcon(getClass().getResource("/Images/BlackKing.png"));
		ImageIcon reset = new ImageIcon(getClass().getResource("/Images/Reset.png"));
		ImageIcon help = new ImageIcon(getClass().getResource("/Images/Help.png"));
		reset.paintIcon(this, g, 200, 550);reset.paintIcon(this, g, 200, 550);
		help.paintIcon(this, g, 10, 880);
	    Chessboard game = ChessGame.getGame();
		
		g.setColor(new Color(118,150,86));
	    for (int r = 0; r < 8; r++)
	    {
	    	for (int c = 0; c < 8; c++)
	    	{
	    		if (highlight != null)
	    		System.out.print("");
	    		if (highlight != null && 7 - r == (int) (highlight.getY()) / size && c == (int) highlight.getX() / size)
	    		{
	    			g.setColor(Color.YELLOW);
	    			g.fillRect(c * size, (7 - r) * size, size, size);
	    			g.setColor(new Color(118,150,86));
	    		}
	    		else if ((c + r) % 2 == 1 )
	    		{
	    			g.setColor(new Color (200, 200, 200));
	    			g.drawRect(c * size, (7 - r) * size, size, size);
	    			g.setColor(new Color(118,150,86));
	    		}
	    		
	    		else 
	    		{
	    			g.fillRect(c * size, (7 - r) * size, size, size);
	    		}
	    		
	    		
	    		Piece current = ChessGame.getGame().getBoard()[r][c];
    			if (current != null && current.getPiece() == 'P' && current.isWhite())
    			{
    				wPawn.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'R' && current.isWhite())
    			{
    				wRook.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'N' && current.isWhite())
    			{
    				wKnight.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'B' && current.isWhite())
    			{
    				wBishop.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'Q' && current.isWhite())
    			{
    				wQueen.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'K' && current.isWhite())
    			{
    				wKing.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'P' && !current.isWhite())
    			{
    				bPawn.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'R' && !current.isWhite())
    			{
    				bRook.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'N' && !current.isWhite())
    			{
    				bKnight.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'B' && !current.isWhite())
    			{
    				bBishop.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'Q' && !current.isWhite())
    			{
    				bQueen.paintIcon(this, g, c * size, (7 - r) * size);
    			}
    			if (current != null && current.getPiece() == 'K' && !current.isWhite())
    			{
    				bKing.paintIcon(this, g, c * size, (7 - r) * size);
    			}
	    	}
	    }
	    
	    g.setColor(Color.BLACK);	    
	    g.setFont(new Font("Arial", Font.PLAIN, 20));
	    for (int i = 1; i <= 8; i++)
	    {
	    	g.drawString(Integer.toString(i), 507, (7 - (i - 1)) * size + 37);
	    	g.drawString(Character.toString((char) (i + 96)), (i - 1) * size + 27, 525);
	    }
	    if (ChessGame.isWhiteTurn())
	    {
	    	g.drawString("White to Move", 190, 700);
	    }
	    else
	    {
	    	g.drawString("Black to Move", 190, 700);
	    }
	    g.drawRect(180, 670, 145, 40);
	    g.drawString("Move: " + (ChessGame.getPly() + 1) / 2, 400, 920);
	    if (ChessGame.isCheck() && highlight == null)
	    {
		    g.setColor(Color.RED);
		    g.setFont(new Font("Arial", Font.PLAIN, 40));
	    	g.drawString("Check!", 200, 800);
	    	for (int r = 0; r < game.getBoard().length; r++)
			{
				for (int c = 0; c < game.getBoard()[r].length; c++)
				{
					if (game.getBoard()[r][c] != null && game.getBoard()[r][c].getPiece() == 'K' && game.getBoard()[r][c].isWhite() == ChessGame.isWhiteTurn())
					{
						g.fillRect(c * size, (7 - r) * size, size, size);
						if (game.getBoard()[r][c].isWhite())
						{
							wKing.paintIcon(this, g, c * size, (7 - r) * size);
						}
						else
						{
							bKing.paintIcon(this, g, c * size, (7 - r) * size);
						}
					}
				}
			}
	    }
	    g.setColor(Color.RED);
	    g.setFont(new Font("Arial", Font.PLAIN, 200));
	    if (insufficient)
	    {
	    	g.drawString("Draw", 200, 100);
	    }
	    g.setFont(new Font("Arial", Font.PLAIN, 80));
	    if (checkmate)
	    {
	    	if (ChessGame.isWhiteTurn())
	    	{
	    		g.drawString("Black Wins", 40, 280);
	    	}
	    	else
	    	{
	    		g.drawString("White Wins", 40, 280);
	    	}
	    	
	    }
	}
}


