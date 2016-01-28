import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ElvesSay {

  private static void part1( BufferedReader bf ) {
    String line;

    try {
      while ( ( line = bf.readLine() ) != null ) {
        System.out.format( "%s after 40 times, length: %d\n", line, getResult( line, 40 ).length() );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  private static void part2( BufferedReader bf ) {
    String line;
    
    try {
      while ( ( line = bf.readLine() ) != null ) {
        System.out.format( "%s after 50 times, length: %d\n", line, getResult( line, 50 ).length() );
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

  private static String getResult( String input, int iterations ) {
    String result = input;
    for ( int i = 0; i < iterations; i += 1 ) {
      result = lookAndSay( result );
    }

    return result;
  }

  private static String lookAndSay( String input ) {
    String result = new String( "" );
    Character ch = null;
    int count = 0;

    for ( int i = 0; i < input.length(); i += 1 ) {
      if ( ch == null ) {
        ch = input.charAt( i );
        count += 1;
      } else if ( ch == input.charAt( i ) ) {
        count += 1;
      } else if ( ch != input.charAt( i ) ) {
        result = result + count + ch;
        ch = input.charAt( i );
        count = 1;
      }
    }
    result = result + count + ch;

    return result;
  }

  
  private static final String LINE = "(\\w+) to (\\w+) = ([0-9]*)";
  private static final Pattern LINE_PATTERN = Pattern.compile( LINE );
  private static void parseLine( String line ) {
    Matcher lineMatcher = LINE_PATTERN.matcher( line );
  }
} 
