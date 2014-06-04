package org.aschyiel.KnightTour;

import static org.junit.Assert.*;

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
