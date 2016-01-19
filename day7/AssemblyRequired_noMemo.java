import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.lang.Math;
import java.lang.Integer;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AssemblyRequired_noMemo {

  private static final int MAX_INT = 65536;

  private static HashMap<String, String> wires = new HashMap<String, String>();

  private static int and( int op1, int op2 ) {
    return Math.floorMod( op1 & op2, MAX_INT );
  }

  private static int or( int op1, int op2 ) {
    return Math.floorMod( op1 | op2 , MAX_INT );
  }

  private static int lshift( int op1, int op2 ) {
    return Math.floorMod( op1 << op2, MAX_INT );
  }

  private static int rshift( int op1, int op2 ) {
    return Math.floorMod( op1 >> op2, MAX_INT );
  }

  private static int not( int op ) {
    return Math.floorMod( ~op, MAX_INT );
  }

  private static Integer getInteger( String s ) {
    Integer n = null;
    try { 
      n = Integer.parseInt( s );
    } catch ( NumberFormatException nfe ) {
      return null;
    }
    
    return n;
  }

  private static final String AND_DEF     = "([a-z0-9]*) AND ([a-z0-9]*)";
  private static final String OR_DEF      = "([a-z0-9]*) OR ([a-z0-9]*)";
  private static final String LSHIFT_DEF  = "([a-z0-9]*) LSHIFT ([a-z0-9]*)";
  private static final String RSHIFT_DEF  = "([a-z0-9]*) RSHIFT ([a-z0-9]*)";
  private static final String NOT_DEF     = "NOT ([a-z0-9]*)";
  private static final String WIRE        = "^([a-z0-9]*)$";
  
  private static final Pattern AND_PATTERN      = Pattern.compile( AND_DEF );
  private static final Pattern OR_PATTERN       = Pattern.compile( OR_DEF );
  private static final Pattern LSHIFT_PATTERN   = Pattern.compile( LSHIFT_DEF );
  private static final Pattern RSHIFT_PATTERN   = Pattern.compile( RSHIFT_DEF );
  private static final Pattern NOT_PATTERN      = Pattern.compile( NOT_DEF );
  private static final Pattern WIRE_PATTERN     = Pattern.compile( WIRE );

  private static int evaluate( String wire ) {
    Integer wireValue = getInteger( wire );
    if ( wireValue != null ) {
       return wireValue;
    } else {
      String wireDefinition = wires.get( wire );
      
      if ( wireDefinition == null ) {
        System.out.format( "No definition found for wire %s\n", wire );
        return 0;
      }
       
      Matcher andMatcher    = AND_PATTERN.matcher( wireDefinition );
      Matcher orMatcher     = OR_PATTERN.matcher( wireDefinition );
      Matcher lshiftMatcher = LSHIFT_PATTERN.matcher( wireDefinition );
      Matcher rshiftMatcher = RSHIFT_PATTERN.matcher( wireDefinition );
      Matcher notMatcher    = NOT_PATTERN.matcher( wireDefinition );
      Matcher wireMatcher   = WIRE_PATTERN.matcher( wireDefinition );

      if ( andMatcher.find() ) {
        return and( 
            evaluate( andMatcher.group( 1 ) ),
            evaluate( andMatcher.group( 2 ) ) );
      } else if ( orMatcher.find() ) {
        return or( 
            evaluate( orMatcher.group( 1 ) ),
            evaluate( orMatcher.group( 2 ) ) );
      } else if ( lshiftMatcher.find() ) {
        return lshift( 
            evaluate( lshiftMatcher.group( 1 ) ),
            evaluate( lshiftMatcher.group( 2 ) ) );
      } else if ( rshiftMatcher.find() ) {
        return rshift( 
            evaluate( rshiftMatcher.group( 1 ) ),
            evaluate( rshiftMatcher.group( 2 ) ) );
      } else if ( notMatcher.find() ) {
        return not( evaluate( notMatcher.group( 1 ) ) );
      } else if ( wireMatcher.find() ) {
        return evaluate( wireMatcher.group( 1 ) );
      } else {
        System.out.format( "Definition %s did not match any known operation!\n",
            wireDefinition );
        return 0;
      }
    }
  }

  private static void part1( BufferedReader bf ) {
    String line;

    try {
      while ( ( line = bf.readLine() ) != null ) {
        saveWireDefinition( line );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }
    
    System.out.format( "Definitions: \n%s\n", wires.toString() );

    long startTime = System.currentTimeMillis();
    System.out.format( "a: %d\n", evaluate( "a" ) );
    long endTime = System.currentTimeMillis();

    double durationMinutes = ( endTime - startTime ) / 60000.0;
    System.out.format( "Evaluation time (minutes): %ld\n", durationMinutes );
  }

  private static void part2( BufferedReader bf ) {
    String line;
    
    try {
      while ( ( line = bf.readLine() ) != null ) {
        System.out.format( "IMPLEMENT THIS PART!" );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  private static final String PART1 = "part1";
  private static final String PART2 = "part2";
  public static void main( String[] args ) throws Exception {
    String fileName = args[0];
    String part = args[1];

    try {
      FileReader reader = new FileReader( fileName );
      
      if ( PART1.equals( part ) ) {
        part1( new BufferedReader( reader ) );
      } else if ( PART2.equals( part ) ) {
        part2( new BufferedReader( reader ) );
      } else { 
        System.out.format( "Unknown argument for running part. Received: %s\n", part );
      }
      
      reader.close();
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  private static final String INPUT_LINE = "(.*) -> ([a-z]*)";
  private static final Pattern inputPattern = Pattern.compile( INPUT_LINE );
  private static void saveWireDefinition( String line ) {
    Matcher matcher = inputPattern.matcher( line );
    
    if ( !matcher.find() ) {
      System.out.format( "No matches found for line %s\n", line );
    } else { 
    
      String definition = matcher.group( 1 );
      String wire = matcher.group( 2 );
      
      if ( wire.equals( "a" ) ) {
        System.out.format( "Inserting wires[ %s ] = %s\n", wire, definition );
      }
       
      wires.put( wire, definition );  
    }
  }
 
} 
