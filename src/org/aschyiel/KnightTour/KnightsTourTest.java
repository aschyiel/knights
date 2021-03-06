package org.aschyiel.KnightTour;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KnightsTourTest
{
  private KnightsTour game;

  @Before
  public void setUp() throws Exception
  {
    game = new KnightsTour();
  }

  @After
  public void tearDown() throws Exception
  {
  }

  /**
   * @see http://homepage.eircom.net/~reidr1/Knights-Tour_files/image006.jpg
   */
//  @Test
  public void testSolve8x8()
  { 
    Solution soln = solveSection( 8, 8, 0, 5, 2, 4, "A6", "C5" );
    assertTrue( soln != null );
soln.printAsAsciiArt();
  }


  /**
   * for example:
   * 
   *      1     2     3     4     5     6
   * A [ 34 ][  3 ][ 12 ][ 15 ][ 28 ][  1 ]
   * B [ 13 ][ 22 ][ 35 ][  2 ][ 11 ][ 16 ]
   * C [  4 ][ 33 ][ 14 ][ 29 ][ 36 ][ 27 ]
   * D [ 21 ][ 30 ][ 23 ][  8 ][ 17 ][ 10 ]
   * E [ 24 ][  5 ][ 32 ][ 19 ][ 26 ][  7 ]
   * F [ 31 ][ 20 ][ 25 ][  6 ][  9 ][ 18 ]
   * 
   * alternative example:
   *      1     2     3     4     5     6  
   * A [ 26 ][ 31 ][ 28 ][ 19 ][ 24 ][  1 ]
   * B [ 29 ][ 18 ][ 25 ][  2 ][ 11 ][ 20 ]
   * C [ 32 ][ 27 ][ 30 ][ 21 ][ 36 ][ 23 ]
   * D [ 17 ][  6 ][ 15 ][ 12 ][  3 ][ 10 ]
   * E [ 14 ][ 33 ][  8 ][  5 ][ 22 ][ 35 ]
   * F [  7 ][ 16 ][ 13 ][ 34 ][  9 ][  4 ]
   * 
   * ie3.
   *      1     2     3     4     5     6  
   * A [ 18 ][ 31 ][ 28 ][  9 ][ 20 ][  1 ]
   * B [ 27 ][ 10 ][ 19 ][  2 ][ 29 ][  8 ]
   * C [ 32 ][ 17 ][ 30 ][  7 ][ 36 ][ 21 ]
   * D [ 11 ][ 26 ][ 13 ][ 22 ][  3 ][  6 ]
   * E [ 16 ][ 33 ][ 24 ][  5 ][ 14 ][ 35 ]
   * F [ 25 ][ 12 ][ 15 ][ 34 ][ 23 ][  4 ]
   * 
   * @see http://delphiforfun.org/Programs/images/KnightsTour6x6.gif
   */
  @Test
  public void testSolve6x6()
  { 
    // GOTCHA: This test can take up to a minute or so to run if unlucky.
    Solution soln = solveSection( 6, 6, 0, 5, 2, 4, "A6", "C5" );
    assertTrue( soln != null );
    soln.printAsAsciiArt();
  }

  /**
   * ie. 5x4 solution
   * 
   *      1     2     3     4     5
   * A [  1 ][ 20 ][  5 ][ 14 ][  9 ]
   * B [  6 ][ 15 ][ 10 ][ 19 ][  4 ]
   * C [ 11 ][  2 ][ 17 ][  8 ][ 13 ]
   * D [ 16 ][  7 ][ 12 ][  3 ][ 18 ]
   * 
   * ie2.
   *      1     2     3     4     5
   * A [  1 ][ 20 ][  7 ][ 16 ][  3 ]
   * B [  6 ][ 15 ][  2 ][ 11 ][  8 ]
   * C [ 19 ][ 10 ][ 13 ][  4 ][ 17 ]
   * D [ 14 ][  5 ][ 18 ][  9 ][ 12 ]
   */
  @Test
  public void testSolve4x5()
  { 
    Solution soln = solveSection( 4, 5, 0, 0, 0, 1, "A1", "A2" );
    assertTrue( soln != null );
  } 

  /**
   * Here's a quick (only) solution for a 3x4 section from A1 to A2.
   * 
   *      1     2     3
   * A [  1 ][ 12 ][  3 ]
   * B [  4 ][  9 ][  6 ]
   * C [  7 ][  2 ][ 11 ]
   * D [ 10 ][  5 ][  8 ]
   */
  @Test
  public void testSolveSimple3x4Section()
  {
    Solution soln = solveSection( 4, 3, 0, 0, 0, 1, "A1", "A2" );
    assertTrue( soln != null );
  }

  @Test
  public void testSolveImpossible3x4Section()
  {
    // GOTCHA: There is NO closed path in a 4x3. 
    Solution soln = solveSection( 4, 3, 0, 0, 0, 0, "A1", "A1" );
    assertTrue( null == soln );    // An impossible answer for an impossible solution.
  }
  
  @Test
  public void integrationTestOfBasicGameConcepts()
  {
    int n = 5;
    ChessBoardSection mini = new ChessBoardSection(
        new ChessBoard( n, n ),
        0, 0, n, n );
    Solution soln = new Solution( n, n );
    Square a1 = mini.getSquare( 0, 0 );
    assertEquals( "A1", a1.toString() );
    a1.markAsOrigin();
    assertTrue( 2 == a1.getUnvisitedNeighborsSize() );    // C2, B3.
    
    // Let's say we move to C2 from A1.
    Square c2 = mini.getSquare( 2, 1 );
    soln.move( a1, c2 );

    // A3, B4, D4, E1, and E3.
    // NOTE: A1 is excluded because we've already marked it as the origin.
    assertEquals( "C2", c2.toString() );
    assertTrue( 5 ==  c2.getUnvisitedNeighborsSize() );

    Square[] li = c2.getUnvisitedNeighbors();
    assertTrue( li.length == 5 );
    
    // Now let's say we want to move to B4 from C2.
    Square b4 = mini.getSquare( 1, 3 );
    soln.move( c2, b4 );
    assertEquals( "B4", b4.toString() );
    
    // A2, C2, D3, D5, however C2 has already been visited.
    assertTrue( 3 == b4.getUnvisitedNeighborsSize() );
    assertTrue( 3 == b4.getUnvisitedNeighbors().length );
    assertTrue( null != b4.getUnvisitedNeighbors()[0] );
    assertTrue( null != b4.getUnvisitedNeighbors()[1] );
    assertTrue( null != b4.getUnvisitedNeighbors()[2] );
  } 
  
  @Test
  public void testRandom()
  {
    Map<Integer, Boolean> xi = new HashMap<Integer, Boolean>();
    Integer it;
    int choices = 3;
    int maxTries = 99;
    int tries = 0;
    // There has GOT to be a better way of mocking this up...
    while ( tries < maxTries )
    {
      tries++;
      it = game.random( choices );
      if ( 0 == it )
      {
        xi.put( 0, true );
      }
      else if ( 1 == it )
      {
        xi.put( 1, true );
      }
      else if ( 2 == it )
      {
        xi.put( 2, true );
      }
    }
    
    assertTrue( xi.get(  0 ) );
    assertTrue( xi.get(  1 ) );
    assertTrue( xi.get(  2 ) );
    assertTrue( null == xi.get(  3 ) );
    assertTrue( null == xi.get( -1 ) );
  } 

  @Test
  public void testChessBoardVertices()
  {
    assertEquals( "A1", game.board.getSquare( 0, 0 ).toString() );
    assertEquals( "A8", game.board.getSquare( 0, 7 ).toString() );
    assertEquals( "H1", game.board.getSquare( 7, 0 ).toString() );
    assertEquals( "H8", game.board.getSquare( 7, 7 ).toString() );
  }

  /**
   * We should have varying numbers of edges between vertices.
   * ie. 2, 3, 4, 6, and 8.
   * 
   * @see http://en.wikipedia.org/wiki/File:Knight%27s_graph_showing_number_of_possible_moves.svg
   */
  @Test
  public void testChessBoardEdges()
  {
    ChessBoardSection section = new ChessBoardSection( game.board, 0, 0,
        game.board.getRowCount(), game.board.getColumnCount() );
    Square corner      = section.getSquare( 0, 0 );
    Square nearCorner  = section.getSquare( 1, 0 );
    Square border      = section.getSquare( 2, 0 );
    Square innerBorder = section.getSquare( 2, 1 );
    Square center      = section.getSquare( 2, 2 );
    assertEquals( 2, corner     .edgeCount() );
    assertEquals( 3, nearCorner .edgeCount() );
    assertEquals( 4, border     .edgeCount() );
    assertEquals( 6, innerBorder.edgeCount() );
    assertEquals( 8, center     .edgeCount() );
  }

  /**
   * A sub-routine for solving an arbitrary board (section) size from a to b.
   * @return
   */
  private Solution solveSection( int m,           int n,
                                        int srcX,        int srcY,
                                        int dstX,        int dstY,
                                        String srcLabel, String dstLabel )
  {
    KnightsTour caty = new KnightsTour( m, n );
    ChessBoardSection section = new ChessBoardSection(
        caty.board, 0, 0, m, n ); 
    Square src = section.getSquare( srcX, srcY );
    Square dst = section.getSquare( dstX, dstY );
    assertEquals( srcLabel, src.toString() );
    assertEquals( dstLabel, dst.toString() );
    return caty.solveSection( src, dst, section );
  }
 
}
