import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Matchsticks {

  private static void part1( BufferedReader bf ) {
    String line;
    int total = 0;
    int size = 0;

    try {
      while ( ( line = bf.readLine() ) != null ) {
        size = parseLine( line );
        total += size;
        System.out.format( "Memory length for string %s is %d\n", line, size );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    System.out.format( "Total size needed: %d\n", total );
  }

  private static void part2( BufferedReader bf ) {
    String line;
    int total = 0;
    int size = 0;
    
    try {
      while ( ( line = bf.readLine() ) != null ) {
        size = encodeLine( line );
        total += size;
        System.out.format( "Memory length for string %s is %d\n", line, size );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    System.out.format( "Total size: %d\n", total );
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

  private static final String REPLACE_STRING = "a";

  private static final String ESCAPED = "\\\\x[a-z0-9]{2}|\\\\\\\\|\\\\\"";
  private static final Pattern ESCAPED_PATTERN = Pattern.compile( ESCAPED );

  private static int parseLine( String line ) {
    Matcher escapedMatcher = ESCAPED_PATTERN.matcher( line );
    
    return line.length() - ( escapedMatcher.replaceAll( REPLACE_STRING ).length() - 2 );

  }
 
  private static final String TO_ESCAPE = "(\"|\\\\)";
  private static final Pattern TO_ESCAPE_PATTERN = Pattern.compile( TO_ESCAPE ); 
  private static int encodeLine( String line ) {
    String result = line;
    Matcher escapedMatcher = TO_ESCAPE_PATTERN.matcher( result );
    StringBuffer buffer = new StringBuffer();

    while ( escapedMatcher.find() ) {
      String matched = escapedMatcher.group( 1 );
      if ( "\\".equals( matched ) ) {
        escapedMatcher.appendReplacement( buffer, "\\\\\\\\" );
      } else {
        escapedMatcher.appendReplacement( buffer, String.format( "\\\\%s", matched ) );
      }
    }
    String finalResult = String.format( "\"%s\"", buffer.toString() );
    System.out.format( "Encoded line: %s with character length: %d\n", finalResult, finalResult.length() ); 
    return finalResult.length() - line.length();
  }
} 
