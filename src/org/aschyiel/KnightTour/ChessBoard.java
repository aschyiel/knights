package org.aschyiel.KnightTour;

public class ChessBoard
{
  private int dimensions = -1;

  public ChessBoard( int n )
  {
    dimensions = n;
    board = new Square[n][n];
    fillBoard();
  }
  
  public int getDimensions()
  {
    return dimensions;
  }

  private Square[][] board;
  
  /**
   * Create all of our default chess-board squares.
   * AKA create the vertices but NOT the edges.
   * 
   * To be called only once at init time.
   * 
   * @see http://stackoverflow.com/questions/833709/converting-int-to-char-in-java
   */
  private void fillBoard()
  {
    for ( int i = 0; i < dimensions; i++ )
    for ( int j = 0; j < dimensions; j++ )
    {
      String row    = String.valueOf( Character.toChars( 65 + i ) );
      String column = String.valueOf( j + 1 );
      board[i][j] = new Square( row + column );
    }
  }
  
  /**
   * Attempt to make a connection between a square
   * and a relative neighboring square, if any.
   * 
   * Will NOT panic if given sucky indices;  It'll just ignore the directive.
   * Makes writing KnightsTour#makeChessBoardGraph a little simpler.
   */
  public void makeEdge( Square sq, int i, int j )
  {
    if ( !isInvalidIndices( i, j ) )
    {
      Square neighbor = board[i][j];
      sq.addNeighbor( neighbor );
    }
  }

  /**
   * Returns true if the given board indices suck.
   */
  private boolean isInvalidIndices( int i, int j )
  {
    int max = dimensions - 1;
    return i < 0   ||
           j < 0   ||
           i > max ||
           j > max;
  }
  
  /**
   * Pick out an individual chess-board square by coordinates. 
   */
  public Square getSquare( int i, int j )
  {
    return ( isInvalidIndices( i, j ) )?
        null : board[i][j];
  }
}
