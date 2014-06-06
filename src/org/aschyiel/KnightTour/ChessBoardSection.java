package org.aschyiel.KnightTour;

/**
 * Declare a sub-section of a chess-board.
 * 
 * For example: A 4x4 quadrant within the default 8x8 board.
 *
 */
public class ChessBoardSection
{
  /**
   * Dimensions for our section.
   */
  private final Integer x;
  private final Integer y;
  private final Integer w;
  private final Integer h;
  
  /**
   * The parent chess-board we're delegating on behalf of.
   */
  private final ChessBoard board;
  
  /**
   * @param board The parent chess-board we're abstracting.
   * @param x The x-offset (column offset).
   * @param y The y-offset (row offset).
   * @param w The width of the section (in columns).
   * @param h The height of the section (in rows).
   * 
   * For example:
   * new ChessBoardSection( board, 0, 0, 4, 4 )
   * => Declares a 4x4 section in the top-left corner of the board.
   */
  public ChessBoardSection( ChessBoard board, int x, int y, int w, int h )
  {
    this.board = board;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    
    setupEdgesWithinSection();
  }
  
  /**
  * Setup edges between vertices as a knight;
  * Limited to within our section.
  */
  private void setupEdgesWithinSection()
  { 
    int n = y + h;
    int m = x + w;
    for ( int i = y; i < m; i++ )
    for ( int j = x; j < n; j++ )
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
  }

  public int getColumnCount()
  {
    return w;
  }

  public int getRowCount()
  {
    return h;
  }

  public Square getSquare( int i, int j )
  {
    return board.getSquare( i, j );
  } 
  
}
