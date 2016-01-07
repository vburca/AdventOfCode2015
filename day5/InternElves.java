import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.Arrays;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class InternElves {

  public static void main( String[] args ) throws Exception {
    String fileName = args[0];

    try {
      FileReader reader = new FileReader( fileName );
      BufferedReader bufferedReader = new BufferedReader( reader );

      String line;
      
      int count1 = 0;
      int count2 = 0;

      while ( ( line = bufferedReader.readLine() ) != null ) {
        if ( line.length() > 1 ) {
          int i = 0;
          boolean naughtyPair = false;
          int minVowels = 3;
          boolean doubleLetter = false;

          if ( isNicerString( line ) ) {
            count2 += 1;
          }

          while ( i < line.length() - 1 && !naughtyPair ) {
            char first = line.charAt( i );
            char second = line.charAt( i + 1 );
            String pair = String.format( "%s%s", first, second );
              
            doubleLetter = doubleLetter ? doubleLetter : first == second;
            naughtyPair = isNaughtyPair( pair );
            minVowels = countVowels( first, minVowels );
             
            i += 1;
          }
          minVowels = countVowels( line.charAt( i ), minVowels ); // count last char.

          if ( !naughtyPair && minVowels <= 0 && doubleLetter ) {
            // System.out.format( "String %s is nice ( %b, %d, %b )%n", line, naughtyPair, minVowels, doubleLetter );
            
            count1 += 1;
          } else {
            // System.out.format( "String %s is naughty ( %b, %d, %b )%n", line, naughtyPair, minVowels, doubleLetter );
          }
        }
      }

      System.out.format( "Number of nice strings (part 1): %d%n", count1 );
      System.out.format( "Number of nice strings (part 2): %d%n", count2 );

      reader.close();
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }
  
  private static final String VOWELS = "aeiou";
  private static boolean isVowel( char ch ) {
    return VOWELS.indexOf( ch ) >= 0;
  }

  private static int countVowels( char ch, int minVowels ) {
    if ( minVowels > 0 && isVowel( ch ) ) {
      return minVowels - 1; // count one more vowel.
    } else { // no need to substract anymore, got enough.
      return minVowels;
    }
  }

  private static final String[] SET_VALUES = new String[] { 
    "ab", "cd", "pq", "xy" 
  };
  private static final HashSet<String> NAUGHTY_PAIRS = new HashSet<String>( 
                                  Arrays.asList( SET_VALUES ) );
  private static boolean isNaughtyPair( String pair ) {
    return NAUGHTY_PAIRS.contains( pair );
  }

  private static final String DUPLICATE_PAIRS = new String( "(..).*\\1" );
  private static final String ONE_LETTER = new String( "(.).\\1" );
  private static final Pattern rule1 = Pattern.compile( DUPLICATE_PAIRS );
  private static final Pattern rule2 = Pattern.compile( ONE_LETTER );
  private static boolean isNicerString( String line ) {
    Matcher matcher1 = rule1.matcher( line );
    Matcher matcher2 = rule2.matcher( line );

    return matcher1.find() && matcher2.find();
  }
}
