package org.aschyiel.KnightTour;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KnightsTourTest
{
  private KnightsTour game = new KnightsTour();

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }
  
  @Test
  public void testSolve()
  {
    Solution soln = (new KnightsTour()).solve();
    assertTrue( soln != null );
  }
  
  @Test
  public void integrationTestOfBasicGameConcepts()
  {
    int n = 5;
    ChessBoard mini = game.makeChessBoardGraph( n );
    Solution soln = new Solution( n );
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
      if ( -1 == it )
      {
        xi.put( -1, true );
      }
      else if ( 0 == it )
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
    
    assertTrue( xi.get( -1 ) );
    assertTrue( xi.get(  0 ) );
    assertTrue( xi.get(  1 ) );
    assertTrue( xi.get(  2 ) );
    assertTrue( null == xi.get(  3 ) );
    assertTrue( null == xi.get( -2 ) );
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
    Square corner      = game.board.getSquare( 0, 0 );
    Square nearCorner  = game.board.getSquare( 1, 0 );
    Square border      = game.board.getSquare( 2, 0 );
    Square innerBorder = game.board.getSquare( 2, 1 );
    Square center      = game.board.getSquare( 2, 2 );
    assertEquals( 2, corner     .edgeCount() );
    assertEquals( 3, nearCorner .edgeCount() );
    assertEquals( 4, border     .edgeCount() );
    assertEquals( 6, innerBorder.edgeCount() );
    assertEquals( 8, center     .edgeCount() );
  }
  
}
