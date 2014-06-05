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
    
    int maxTries = 9999;
    int step = 0;
    Square current = origin;
    current.markAsOrigin();
    while ( soln.getStep() < soln.getMaxMoves() && step++ < maxTries )
    { 
      Square prev = null;
      int i = random( current.getUnvisitedNeighborsSize() );
      if ( -1 == i )
      {
        prev = soln.undo(); 
      }
      else
      {
        Square[] li = current.getUnvisitedNeighbors();
        Square next = li[i];
        
        // Don't short-circuit.
        if ( next.isOrigin() && soln.getStep() + 1 < soln.getMaxMoves() )
        {
          continue;
        }

        soln.move( current, next );
        if ( !next.isValidLastMove() && next.hasOrphanedNeighbors() )
        {
          prev = soln.undo();
        }
        else
        {
          current = next;
        }
      }
      if ( null != prev )
      {
        current = prev;
      }
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
  protected ChessBoard makeChessBoardGraph( int n )
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
