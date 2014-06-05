package org.aschyiel.KnightTour;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SquareTest
{
  private Square subject;
  private Square a;
  private Square b;
  private Square c;

  @Before
  public void setUp() throws Exception
  {
    subject = new Square( "subject" );
    a = new Square( "a" );
    b = new Square( "b" );
    c = new Square( "c" );
    subject.addNeighbor( a );
    subject.addNeighbor( b );

    a.addNeighbor( subject );
    a.addNeighbor( b );
    a.addNeighbor( c );

    b.addNeighbor( a );
    b.addNeighbor( subject );

    c.addNeighbor( a );
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testMarkAsVisited()
  {
    // Before Square#markAsVisited.
    assertTrue( subject.isUnvisited() );
    assertTrue( !subject.isVisited() );

    assertTrue( a.isUnvisited() );
    assertTrue( b.isUnvisited() );
    assertTrue( c.isUnvisited() );

    int n = subject.getUnvisitedNeighborsSize();
    int na = a.getUnvisitedNeighborsSize();
    int nb = b.getUnvisitedNeighborsSize();
    int nc = c.getUnvisitedNeighborsSize();

    subject.markAsVisited();

    // After Square#markAsVisited.
    assertTrue( subject.isVisited() );
    assertTrue( !subject.isUnvisited() );

    assertTrue( a.isUnvisited() );
    assertTrue( b.isUnvisited() );
    assertTrue( c.isUnvisited() );

    assertTrue( n  - 1 == subject.getUnvisitedNeighborsSize() );
    assertTrue( na - 1 == a      .getUnvisitedNeighborsSize() );
    assertTrue( nb - 1 == b      .getUnvisitedNeighborsSize() );
    assertTrue( nc     == c      .getUnvisitedNeighborsSize() ); 
  }
  
  @Test
  public void testGetUnvistedNeighborsSize()
  {
    assertTrue( 2 == subject.getUnvisitedNeighborsSize() );
    assertTrue( 3 == a      .getUnvisitedNeighborsSize() );
    assertTrue( 1 == c      .getUnvisitedNeighborsSize() );
    a.markAsVisited();
    assertTrue( 1 == subject.getUnvisitedNeighborsSize() );
    assertTrue( 0 == c      .getUnvisitedNeighborsSize() );
  }
  
  @Test
  public void testGetUnvistedNeighbors()
  {
    Square[] li = subject.getUnvisitedNeighbors();
    assertTrue( 2 == li.length );
    
    a.markAsVisited();
    li = subject.getUnvisitedNeighbors();
    assertTrue( 1 == li.length );
    
    b.markAsVisited();
    li = subject.getUnvisitedNeighbors();
    assertTrue( 0 == li.length ); 
  }
  
  @Test
  public void testHasOrphanedNeighbors()
  {
    assertEquals( false, a.hasOrphanedNeighbors() );
    a.markAsVisited();
    assertTrue( a.hasOrphanedNeighbors() );    // Aka "C" is now unreachable. 
  }

}
