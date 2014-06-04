package org.aschyiel.KnightTour;

public class KnightsTour
{
  protected ChessBoard board;
  
  public KnightsTour()
  { 
    board = makeChessBoardGraph( 8 );
  }
  
  /**
  * Solves the default knight's tour randomly.
  */
  public Solution solve()
  {
    return null;
  }
  
  /**
   * Builds up a chess-board for the knight's tour.
   */
  private ChessBoard makeChessBoardGraph( int n )
  {
    ChessBoard board = new ChessBoard( n );
    
    //
    // Setup edges between vertices as a knight.
    //
    
    for ( int i = 0; i < n; i++ )
    for ( int j = 0; j < n; j++ )
    {
      Square sq = board.getSquare( i, j );
      board.makeEdge( sq, i - 2, j - 1 );
      board.makeEdge( sq, i - 1, j - 2 );
      board.makeEdge( sq, i + 1, j - 2 );
      board.makeEdge( sq, i + 2, j - 1 );
      board.makeEdge( sq, i + 2, j + 1 );
      board.makeEdge( sq, i + 1, j + 2 );
      board.makeEdge( sq, i - 1, j + 2 );
      board.makeEdge( sq, i - 2, j + 1 );
    }
    
    return board;
  }
}
