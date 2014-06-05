package org.aschyiel.KnightTour;

/**
 * An immutable object recording an individual move within our knights tour.
 * 
 * @see http://www.javapractices.com/topic/TopicAction.do?Id=29
 */
public final class Move
{
  private final Integer step;
  private final Square from;
  private final Square to;
  
  public Move( int step, Square from, Square to )
  {
    this.step = step;
    this.from = from;
    this.to   = to;
  }
  
  @Override
  public String toString()
  {
    return String.format( "Step%2$d: Move from %s to %s.", step, from, to );
  }
  
  /**
   * Readonly properties.
   */
  public Square getFrom()
  {
    return from;
  } 
  public Square getTo()
  {
    return to;
  }
}
