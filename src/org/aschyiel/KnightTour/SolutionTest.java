package org.aschyiel.KnightTour;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SolutionTest
{
  private int dimensions = 3;
  private Solution subject;
  private Square a;
  private Square b;
  private Square c;

  @Before
  public void setUp() throws Exception
  {
    subject = new Solution( dimensions, dimensions );
    a = new Square( "a" );
    b = new Square( "b" );
    c = new Square( "c" );
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public void testMove()
  {
    subject.move( a, b );
    subject.move( b, c );
    assertTrue( 3 == subject.getStep() );
  }
  
  @Test
  public void testUndo()
  {
    subject.move( a, b );
    subject.move( b, c );
    assertTrue( b == subject.undo().getFrom() );
    assertTrue( a == subject.undo().getFrom() ); 
    assertTrue( 1 == subject.getStep() );
  }
  
  @Test
  public void testPrint()
  {
    subject.move( a, b );
    subject.print();
    assertTrue( true );
  }

}
