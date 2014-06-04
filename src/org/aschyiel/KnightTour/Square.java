package org.aschyiel.KnightTour;

import java.util.HashMap;
import java.util.Map;

/**
 * An individual chess-board square.
 * AKA vertex.
 */
public class Square
{
  private String name;
  
  private Map<String, Square> neighbors;
  
  public Square( String name )
  {
    this.name = name;
    neighbors = new HashMap<String, Square>();
  }
  
  @Override
  public String toString()
  {
    return name;
  }

  /**
   * Create an edge between us and a neighbor.
   */
  public void addNeighbor( Square them )
  {
    neighbors.put( them.toString(), them );
  }

  /**
   * Remove an edge.
   */
  public void removeNeighbor( Square them )
  {
    neighbors.remove( them.toString() );
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
}
