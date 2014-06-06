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
  public static final boolean DEBUG = true;
  
  public KnightsTour()
  {
    this( DEFAULT_DIMENSIONS, DEFAULT_DIMENSIONS );
  }
  public KnightsTour( int m, int n )
  { 
    attempted = new HashMap<Integer, Map<String, Map<String, Boolean>>>();
    board = new ChessBoard( m, n );
  }

  /**
   * Depth, by parent square, to attempted child square.
   */
  protected Map<Integer, Map<String, Map<String, Boolean>>> attempted;

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
    Square current = origin;
    current.markAsOrigin();
    while ( true )
    { 
      int step = soln.getStep(); 
      Square prev = null; 
      Square[] unvisited = current.getUnvisitedNeighbors(); 
      boolean exausted = hasExausted( step, current, unvisited );
      
      if ( exausted && current == origin )
      {
        System.out.println( "WARNING:Failed to find a solution from "+ origin + " to " + destination );
        return null;
      }
      
      Square next = ( exausted )? null : getRandomUnvisitedNeighbor( step, current, unvisited ); 

      boolean isLastMove = step + 2 == soln.getMaxMoves();
      if ( null != next )
      {
        if ( !isLastMove && next == destination )
        {
          tag( step, current, next );
          continue;
        } 
        if ( next.isDeadEnd() )
        {
          tag( step, current, next );
          continue;
        }
      } 
      
      if ( exausted )
      {
        // We ran out of stuff to try at this level.
        debug( "Exausted:"+ current.toString() ); 
        prev = soln.undo(); 
      }
      else if ( null != next && !hasTried( step, current, next ) )
      { 
        soln.move( current, next ); 
        if ( isLastMove && next.neighborsWith( destination ) )
        {
          soln.move( next, destination );
          break;    // We found a solution.
        } 
        tag( step, current, next ); 
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
        tag( step, prev, current );
        current = prev;
      }
    } 
    
    soln.print();
    return soln;
  }
  
  /**
   * Returns true if we've tried everything from the given square already.
   */
  private boolean hasExausted( int depth, Square sq, Square[] unvisited )
  {
    for ( int i = 0; i < unvisited.length; i++ )
    {
      if ( !hasTried( depth, sq, unvisited[i] ) )
      {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Returns a random unvisited neighbor. 
   */
  private Square getRandomUnvisitedNeighbor( int depth, Square src, Square[] unvisited )
  {
    if ( unvisited.length < 1 )
    {
      return null;
    }
    Square them = unvisited[ random( unvisited.length ) ]; 
    while ( hasTried( depth, src, them ) )
    {
      them = unvisited[ random( unvisited.length ) ];
    } 
    return them;
  } 
  
  /**
   * Keep track of bad inter-square attempts.
   */
  private void tag( int depth, Square a, Square b )
  {
    String k1 = a.toString();
    String k2 = b.toString();
    if ( null == attempted.get( depth ) )
    {
      attempted.put( depth, new HashMap<String, Map<String, Boolean>>() );
    }
    if ( null == attempted.get( depth ).get( k1 ) )
    { 
      attempted.get( depth ).put( k1, new HashMap<String, Boolean>() );
    }
    attempted.get( depth ).get( k1 ).put( k2, true );
  }
  
  /**
   * Returns true if we're already attempted something previously.
   */
  private boolean hasTried( int depth, Square a, Square b )
  {
    Map<String, Map<String, Boolean>> xi = attempted.get( depth );
    String k1 = a.toString();
    String k2 = b.toString();
    if ( null == xi )
    {
      return false;
    }
    Map<String, Boolean> zi = xi.get( k1 );
    Boolean yesh = ( null == zi || !zi.containsKey( k2 ) )?
        false : zi.get( k2 );
    return ( null == yesh )?
        false : yesh;
  } 
  
  private Random r = new Random();
  
  /**
   * Return a random (zero-based) index from the available choices.
   */
  protected int random( int n )
  { 
    return r.nextInt( n  );
  }
  
  public static void debug( String msg )
  {
    if ( DEBUG )
    {
      System.out.println( msg );
    }
  }
}
