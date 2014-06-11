package org.aschyiel.KnightTour;

import java.util.ArrayDeque; 
import java.util.Deque;
import java.util.Iterator; 

public class Solution
{
  private Integer _columns;
  private Integer _rows;
  
  public Solution( int columns, int rows )
  {
    maxMoves = columns * rows;
    _columns = columns;
    _rows    = rows;
    moves = new ArrayDeque<Move>( maxMoves );
  }

  /**
   * The exact number of moves needed to complete the knight's tour.
   * 
   * By default for a 8x8 chess-board, that's gonna
   * be 64 moves (1 move per square).
   */
  private Integer maxMoves; 
  
  public int getMaxMoves()
  {
    return maxMoves;
  }
  
  public int getStep()
  {
    return moves.size() + 1;
  }

  /**
   * The "stack" of moves we're keeping track within our solution space.
   */
  private Deque<Move> moves; 
  
  public void move( Square a, Square b )
  {
    moves.push( new Move( getStep(), a, b ) );
    KnightsTour.debug( "move: "+ moves.peek().toString() );
    b.markAsVisited();
  }
  
  /**
   * Undoes the last move, and returns the previous Square.
   * 
   * Safely ignores invalid undos.
   */
  public Move undo()
  {
    if ( moves.size() < 1 )
    {
      return null;
    }
    Move prev = moves.pop();
    KnightsTour.debug( "undo: "+ prev.toString() );
    prev.getTo().remarkAsUnvisited();
    return prev;
  }
  
  /**
   * Print out our solution steps.
   */
  public void print()
  {
    Iterator<Move> it = moves.descendingIterator();
    while ( it.hasNext() )
    {
      System.out.println( it.next() );
    }
  }
  
  /**
  * Print out our solution as a crude ascii-art matrix.
  *
  * ie
  *      1     2     3     4     5     6
  * A [ 34 ][  3 ][ 12 ][ 15 ][ 28 ][  1 ]
  * B [ 13 ][ 22 ][ 35 ][  2 ][ 11 ][ 16 ]
  * C [  4 ][ 33 ][ 14 ][ 29 ][ 36 ][ 27 ]
  * D [ 21 ][ 30 ][ 23 ][  8 ][ 17 ][ 10 ]
  * E [ 24 ][  5 ][ 32 ][ 19 ][ 26 ][  7 ]
  * F [ 31 ][ 20 ][ 25 ][  6 ][  9 ][ 18 ] 
  */
  public void printAsAsciiArt()
  {
    Integer m = _columns;
    Integer n = _rows;
    if ( null == m || null == n ) {
      return;
    } 
    
    //
    // Collect our moves-list as a matrix.
    //
    
    int[][] tmp = new int[n][m]; 
    Iterator<Move> it = moves.descendingIterator();
    Move move = null;
    Square sq = null;
    while ( it.hasNext() )
    {
      move = it.next();
      sq = move.getFrom();
      tmp[ sq.getRowIndex() ][ sq.getColumnIndex() ] = move.getStep();
    }
    sq = move.getTo();    //..finishing-move..
    tmp[ sq.getRowIndex() ][ sq.getColumnIndex() ] = move.getStep() + 1;

    //
    // Print column-labels.
    //
    
    StringBuilder sb = new StringBuilder();
    sb.append( "   " );
    for ( int i = 1; i < m + 1; i++ )
    {
      sb.append( "   " );
      sb.append( i );
      sb.append( "  " );
    }
    System.out.println( sb.toString() );
    sb.delete( 0, sb.length() );
    
    //
    // Print each row.
    //
    
    for ( int i = 0; i < n; i++ )
    {
      sb.append( " " );
      sb.append( Character.toChars( 65 + i ) );
      sb.append( " " );
      for ( int j = 0; j < m; j++ )
      {
        sb.append( "[ " );
        if ( 10 > tmp[i][j] )
        {
          sb.append( " " );
        }
        sb.append( tmp[i][j] );
        sb.append( " ]" );
      }
      System.out.println( sb.toString() );
      sb.delete( 0, sb.length() );
    } 
  }
  
}
