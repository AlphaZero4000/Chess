import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;
public class ChessGame extends JFrame
{
	private static boolean isWhiteTurn = true;
	private static int plyNumber;
	private static Chessboard game = new Chessboard();
	private static Point mouseClick;
	
	public static void main(String[] args) 
	{
				
		JFrame frame = new JFrame();
		frame.setSize(550, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(new MouseAdapter() 
	    {				   
			public void mouseClicked(MouseEvent e)
			{				    
				mouseClick = new Point(e.getX(), e.getY());
				if (Math.sqrt(Math.pow(e.getX() - 48, 2) + Math.pow(e.getY() - 941, 2)) <= 30)
				{
					JFrame frame = new JFrame();
					frame.setSize(530, 450);
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setTitle("Help Menu");
					JComponent component = new JComponent() 
					{
				         public void paintComponent(Graphics graph)
				         {
				            
				            HelpComponent hc = new HelpComponent();
				            hc.draw(graph);
				            
				         }
				    };
				    frame.add(component);
				    frame.setVisible(true);
				}
			}				   
		});
		
		while (true)
		{
			
			JComponent component = new JComponent() 
			{
		         public void paintComponent(Graphics graph)
		         {
		            
		            ChessComponent cb = new ChessComponent();
		            if (isInsufficientMaterial())
					{
						cb.insufficient = true;
					}
		            cb.highlight = null;
		            cb.checkmate = false;
		            cb.draw(graph);
		            
		         }
		    };
		    frame.add(component);
		    frame.setVisible(true);
		    Point mouseClick1 = null;
		    Point mouseClick2 = null;
		    mouseClick = null;
		    while (mouseClick1 == null)
		    {
		    	if (mouseClick != null)
		    	{
		    		mouseClick1 = new Point((int)(mouseClick.getX()) - 7, (int)(mouseClick.getY()) - 30); 
		    	}
		    	System.out.print("");
		    }
		    if (mouseClick.getY() < 606+30 && mouseClick.getY() > 550+30 && mouseClick.getX() > 200+7 && mouseClick.getX() < 300+7)
		    {
		    	game = new Chessboard();
		    	frame.remove(component);
		    	isWhiteTurn = true;
		    	plyNumber = 0;
		    	continue;
		    }
		    if (mouseClick.getY() < 556 && mouseClick.getY() > 530 && mouseClick.getX() > 505 && mouseClick.getX() < 538)
		    {
		    	frame.remove(component);
		    	JComponent c = new JComponent() 
				{
		    		public void paintComponent(Graphics graph)
		    		{
		            
		    			ChessComponent cb = new ChessComponent();
		    			cb.checkmate = true;
		    			cb.highlight = null;
		    			cb.draw(graph);
		    		}
		        };
		        frame.add(c);
			    frame.setVisible(true);
		    }
		    frame.remove(component);
		    final Point p = mouseClick1;
		    JComponent b = new JComponent() 
			{
		         public void paintComponent(Graphics graph)
		         {
		            
		            ChessComponent cb = new ChessComponent();
		            cb.highlight = new Point((int) p.getX(), (int) p.getY());
		            cb.draw(graph);
		            
		         }
		    };
		    frame.add(b);
		    frame.setVisible(true);
		    mouseClick = null;
		    while (mouseClick2 == null || mouseClick1.equals(mouseClick2))
	    	{
		    	if (mouseClick != null)
		    	{
		    		mouseClick2 = new Point((int)(mouseClick.getX()) - 7, (int)(mouseClick.getY()) - 30);
		    	}
		    	System.out.print("");
	    	}
		    if(game.move(7 - (int)(mouseClick1.getY() / ChessComponent.size), (int)(mouseClick1.getX() / ChessComponent.size), 7 - (int)(mouseClick2.getY() / ChessComponent.size), (int)(mouseClick2.getX() / ChessComponent.size)))
		    {
		    	isWhiteTurn = !isWhiteTurn;
		    	plyNumber++;
		    }		    
		    frame.remove(b);
		}
	}
	
	public static boolean isInsufficientMaterial()
	{
		Piece[][] board = game.getBoard();
		int numMinorPieces = 0;
		for (Piece[] row : board)
		{
			for (Piece p : row)
			{
				if (p == null)
				{
					continue;
				}
				if (p.getPiece() == 'R' || p.getPiece() == 'Q' || p.getPiece() == 'P')
				{
					return false;
				}
				if (p.getPiece() == 'N' || p.getPiece() == 'B')
				{
					numMinorPieces++;
				}
			}
		}
		return numMinorPieces <= 1;
	}
	
	public static boolean isCheck()
	{
		Piece[][] board = game.getBoard();
		int kingRow = 0; int kingCol = 0;
		for (int r = 0; r < board.length; r++)
		{
			for (int c = 0; c < board[r].length; c++)
			{
				if (board[r][c] != null && board[r][c].getPiece() == 'K' && board[r][c].isWhite() == isWhiteTurn)
				{
					kingRow = r;
					kingCol = c;
				}
			}
		}
		
		for (int r = 0; r < board.length; r++)
		{
			for (int c = 0; c < board[r].length; c++)
			{
				if (board[r][c] == null || board[r][c].isWhite() == isWhiteTurn)
				{
					continue;
				}
				if (board[r][c].getPiece() == 'R' && game.rookCanMove(r, c, kingRow, kingCol))
				{
					return true;
				}
				else if (board[r][c].getPiece() == 'B' && game.bishopCanMove(r, c, kingRow, kingCol))
				{
					return true;
				}
				else if (board[r][c].getPiece() == 'N' && game.knightCanMove(r, c, kingRow, kingCol))
				{
					return true;
				}
				else if (board[r][c].getPiece() == 'Q' && game.queenCanMove(r, c, kingRow, kingCol))
				{
					return true;
				}
				else if (board[r][c].getPiece() == 'K' && game.kingCanMove(r, c, kingRow, kingCol))
				{
					return true;
				}
				else if (board[r][c].getPiece() == 'P' && game.pawnCanCapture(r, c, kingRow, kingCol))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	static Point click;
	public static char promote()
	{
		
		JFrame frame = new JFrame();
		frame.setSize(250, 150);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.addMouseListener(new MouseAdapter() 
	    {				   
			public void mouseClicked(MouseEvent e)
			{
				click = new Point(e.getX(), e.getY());

			}
	    });
		JComponent component = new JComponent() 
		{
	         public void paintComponent(Graphics graph)
	         {
	            
	            PromoteComponent pc = new PromoteComponent();
	            pc.draw(graph);
	            
	         }
	    };
	    frame.add(component);
	    frame.setVisible(true);
	    while (true)
	    {
	    	System.out.print("");
	    	if (click == null)
	    	{
	    		continue;
	    	}
	    	int x = (int)(click.getX() - 7) / 62;
	    	int y = (int)(click.getY() - 30) / 62;
	    	if (x < 0 || x > 3 || y != 0) 
	    	{
	    		click = null;
	    		continue;
	    	}
	    	if (x == 0)
	    	{
	    		click = null;
	    		return 'Q';	    		
	    	}
	    	if (x == 1)
	    	{
	    		click = null;
	    		return 'R';
	    	}
	    	if (x == 2)
	    	{
	    		click = null;
	    		return 'B';
	    	}
	    	if (x == 3)
	    	{
	    		click = null;
	    		return 'N';
	    	}
	    	else return 'Q';
	    }
	}
	
	public static Chessboard getGame()
	{
		return game;
	}
	
	public static Point getMouseClick()
	{
		return mouseClick;
	}
	
	public static boolean isWhiteTurn()
	{
		return isWhiteTurn;
	}
	
	public static int getPly()
	{
		return plyNumber;
	}
}
class HelpComponent extends JComponent
{
	ImageIcon helpMenu = new ImageIcon(getClass().getResource("/Images/Help Menu.png"));
	public void draw(Graphics g)
	{
		helpMenu.paintIcon(this, g, 0, 0);
	}
}
class PromoteComponent extends JComponent
{
	ImageIcon wRook = new ImageIcon(getClass().getResource("/Images/WhiteRook.png"));
	ImageIcon wKnight = new ImageIcon(getClass().getResource("/Images/WhiteKnight.png"));
	ImageIcon wBishop = new ImageIcon(getClass().getResource("/Images/WhiteBishop.png"));
	ImageIcon wQueen = new ImageIcon(getClass().getResource("/Images/WhiteQueen.png"));
	ImageIcon bRook = new ImageIcon(getClass().getResource("/Images/BlackRook.png"));
	ImageIcon bKnight = new ImageIcon(getClass().getResource("/Images/BlackKnight.png"));
	ImageIcon bBishop = new ImageIcon(getClass().getResource("/Images/BlackBishop.png"));
	ImageIcon bQueen = new ImageIcon(getClass().getResource("/Images/BlackQueen.png"));
	public void draw(Graphics g)
	{
		g.drawString("Select the piece you want to promote to", 0, 100);
		for (int i = 0; i < 4; i++)
		{
			g.drawRect(i * 62, 0, 62, 62);
		}
		if (ChessGame.isWhiteTurn())
		{		
			wQueen.paintIcon(this, g, 0, 0);
			wRook.paintIcon(this, g, 62, 0);
			wBishop.paintIcon(this, g, 124, 0);
			wKnight.paintIcon(this, g, 186, 0);
		}
		else
		{
			bQueen.paintIcon(this, g, 0, 0);
			bRook.paintIcon(this, g, 62, 0);
			bBishop.paintIcon(this, g, 124, 0);
			bKnight.paintIcon(this, g, 186, 0);
		}
	}
}
