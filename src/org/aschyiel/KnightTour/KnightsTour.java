package org.aschyiel.KnightTour;

import java.util.Random;

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
    return solve( board.getSquare( 0, 0 ) );    // Default to something.
  }
  public Solution solve( Square origin )
  {
    Solution soln = new Solution( board.getDimensions() );
    
    Square current = origin;
    while ( soln.getStep() < soln.getMaxMoves() )
    { 
      int i = random( current.getUnvisitedNeighborsSize() );
      boolean isUndo = -1 == i;
//      Square next = 
    } 
    
    soln.print();
    return soln;
  }
  
  private Random r = new Random();
  
  /**
   * Return a random (zero-based) index from the available choices.
   * Always has a chance of returning -1, meaning,
   * it selected none of the choices.
   * 
   * @param choices Is the number of available of choices.  Must be 1+.
   * 
   * For Example:
   * random( 3 )
   * =>  2, meaning we choose the 3rd choice.
   * =>  0, meaning we choose the 1st choice.
   * => -1, meaning we choose none of the available choices.
   */
  protected int random( int choices )
  { 
    return r.nextInt( choices + 1 ) - 1;
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
