package org.aschyiel.KnightTour;

import java.util.ArrayDeque;
import java.util.Deque;

public class Solution
{
  public Solution( int dimensions )
  {
    maxMoves = dimensions * dimensions;
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
    b.markAsVisited();
  }
  
  /**
   * Undoes the last move, and returns the previous Square.
   */
  public Square undo()
  {
    Move prev = moves.pop();
    if ( null == prev )
    {
      return null;
    }
    prev.getTo().remarkAsUnvisited();
    return prev.getFrom();
  }
  
  /**
   * Print out our solution steps.
   */
  public void print()
  {
    for ( Move it : moves )
    {
      System.out.println( it );
    }
  }
  
}
