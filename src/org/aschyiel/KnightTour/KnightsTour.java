package org.aschyiel.KnightTour;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KnightsTour
{
  protected ChessBoard board;
  
  /**
   * Usually, chess-boards are gonna be 8x8 = 64 squares.
   */
  public final static int DEFAULT_DIMENSIONS = 8;

  /**
   * Flag to enable annoying debugging messages.
   */
  public static final boolean DEBUG = false;
  
  public KnightsTour()
  {
    this( DEFAULT_DIMENSIONS, DEFAULT_DIMENSIONS );
  }
  public KnightsTour( int m, int n )
  { 
    attempted = new HashMap<String, Map<String, Boolean>>();
    board = makeChessBoardGraph( m, n );
  }

  protected Map<String, Map<String, Boolean>> attempted; 

  /**
   * Attempt to tour from the origin to the destination within a section.
   * 
   * If the origin and destination squares are different,
   * it is considered an "open" tour, otherwise it's "closed".
   */
  public Solution solveSection( Square origin,
                                Square destination,
                                ChessBoardSection section )
  { 
    int m = section.getRowCount();
    int n = section.getColumnCount();
    Solution soln = new Solution( m, n );
    
//    int maxTries = m * n * 3;
    int maxTries = 199;
    int attempts = 0;
    Square current = origin;
    current.markAsOrigin();
    while ( soln.getStep() < soln.getMaxMoves() )
    { 
      if ( attempts++ > maxTries )
      {
        System.out.println( "WARNING:Too many attempts, "+
            "we're probably doing it wrong." );
        break;
      }
      
      Square prev = null; 
      Square[] unvisited = current.getUnvisitedNeighbors(); 
      boolean exausted = hasExausted( current, unvisited );
      Square next = ( exausted )? null : getRandomUnvisitedNeighbor( current, unvisited ); 

      boolean isLastMove = soln.getStep() + 2 == soln.getMaxMoves();
      if ( null != next )
      {
        if ( !isLastMove && next == destination )
        {
          attempt( current, next );
          continue;
        } 
        if ( next.isDeadEnd() )
        {
          attempt( current, next );
          continue;
        }
      } 
      
      if ( exausted )
      {
        // We ran out of stuff to try at this level.
        debug( "Exausted:"+ current.toString() ); 
//        resetAttempts( current );
        prev = soln.undo(); 
      }
      else if ( null != next && !hasTried( current, next ) )
      { 
        soln.move( current, next ); 
        if ( isLastMove && next.neighborsWith( destination ) )
        {
          soln.move( next, destination );
          break;    // We found a solution.
        } 
        attempt( current, next ); 
        if ( next.hasOrphanedNeighbors() )
        {
          prev = soln.undo();
        }
        else
        {
          current = next;
        }
      }
      
      if ( !isLastMove && destination.isDeadEnd() )
      {
        debug( "Accidentally blocked off destination." );
        prev = soln.undo();
      }
      
      if ( null != prev )
      {
        attempt( prev, current );
        current = prev;
      }
    } 
    
    soln.print();
    return soln;
  }
  
  /**
   * Returns true if we've tried everything from the given square already.
   */
  private boolean hasExausted( Square sq, Square[] unvisited )
  {
    for ( int i = 0; i < unvisited.length; i++ )
    {
      if ( !hasTried( sq, unvisited[i] ) )
      {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Returns a random unvisited neighbor. 
   */
  private Square getRandomUnvisitedNeighbor( Square src, Square[] unvisited )
  {
    if ( unvisited.length < 1 )
    {
      return null;
    }
    Square them = unvisited[ random( unvisited.length ) ]; 
    while ( hasTried( src, them ) )
    {
      them = unvisited[ random( unvisited.length ) ];
    } 
    return them;
  } 
  
  /**
   * Keep track of bad inter-square attempts.
   */
  private void attempt( Square a, Square b )
  {
    attempted.get( a.toString() ).put( b.toString(), true );
  }
  
  /**
   * Returns true if we're already attempted something previously.
   */
  private boolean hasTried( Square a, Square b )
  {
    Boolean yesh = attempted.get( a.toString() ).get( b.toString() );
    return ( null == yesh )? false : yesh;
  }
  
  /**
   * Clear-out the attempted failures from the given square.
   */
  private void resetAttempts( Square sq )
  {
    attempted.put( sq.toString(), new HashMap<String, Boolean>() );
  }
  
  private Random r = new Random();
  
  /**
   * Return a random (zero-based) index from the available choices.
   */
  protected int random( int n )
  { 
    return r.nextInt( n  );
  }
  
  /**
   * Builds up a chess-board (vertices, but NOT edges) for the knight's tour.
   * 
   * @see ChessBoardSection#setupEdgesWithinSection
   */
  protected ChessBoard makeChessBoardGraph( int m, int n )
  {
    ChessBoard board = new ChessBoard( m, n ); 
    for ( int i = 0; i < m; i++ )
    for ( int j = 0; j < n; j++ )
    {
      Square sq = board.getSquare( i, j );
      resetAttempts( sq );
    } 
    return board;
  } 
  
  public static void debug( String msg )
  {
    if ( DEBUG )
    {
      System.out.println( msg );
    }
  }
}
