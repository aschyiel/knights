package org.aschyiel.KnightTour;

import java.util.ArrayList;
import java.util.List;

/**
 * An individual chess-board square.
 * AKA vertex.
 */
public class Square
{
  private String name;
  
  private List<Square> neighbors;
  
  public Square( String name )
  {
    this.name = name;
    neighbors = new ArrayList<Square>();
  }
  
  @Override
  public String toString()
  {
    return name;
  }
  
  /**
   * A cheap way of keeping track of our surroundings.
   */
  private int unvisitedNeighborsSize = 0;
  
  /**
   * This flag gets flipped to true when this square gets visited by the knight.
   */
  private boolean visited = false;

  /**
   * Create an edge between us and a neighbor.
   */
  public void addNeighbor( Square them )
  {
    neighbors.add( them );
    unvisitedNeighborsSize++;
  } 
  
  /**
   * Returns the number of edges connecting us to other squares.
   * 
   * ie. 8 for center squares, while 2 for corner squares,
   *     and 4 for outside squares, etc.
   */
  public int edgeCount()
  {
    return neighbors.size();
  }
  
  public int getUnvisitedNeighborsSize()
  {
    return unvisitedNeighborsSize;
  } 
  
  public boolean isUnvisited()
  {
    return !visited;
  } 
  public boolean isVisited()
  {
    return visited;
  }
  
  public void markAsVisited()
  {
    visited = true;
    unvisitedNeighborsSize--;
    for ( Square neighbor : neighbors )
    {
      neighbor.unvisitedNeighborsSize--;
    } 
  }
  
  public void remarkAsUnvisited()
  {
    visited = false;
    unvisitedNeighborsSize++;
    for ( Square neighbor : neighbors )
    {
      neighbor.unvisitedNeighborsSize++;
    }
  }
}
