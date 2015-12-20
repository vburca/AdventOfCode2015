import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.security.MessageDigest;
import java.math.BigInteger;

public class StockingStuffer {

  public static void main( String[] args ) throws Exception {
    String fileName = args[0];

    try {
      FileReader reader = new FileReader( fileName );
      BufferedReader bufferedReader = new BufferedReader( reader );

      String line;
      int number = -1;
      String md5;

      MessageDigest md = MessageDigest.getInstance( "MD5" );

      while ( ( line = bufferedReader.readLine() ) != null ) {
        
        do {
          number += 1;
          String seed = String.format( "%s%s", line, Integer.toString( number ) );
          md.update( seed.getBytes() );
          StringBuffer sb = new StringBuffer();
          for ( byte b : md.digest() ) {
            sb.append( String.format( "%02x", b & 0xff ) );
          }
          md5 = sb.toString();
          System.out.format( "md5( %s ) = %s%n", seed, md5 );
//        } while ( !checkMD5_Part1( md5 ) );
        } while ( !checkMD5_Part2( md5 ) );
      }

      System.out.format( "Minimum number is: %d%n", number );

      reader.close();
    } catch ( IOException e ) {
      e.printStackTrace();
    }

  }

  private static boolean checkMD5_Part1( String md5 ) {
    String check = "00000";
    return check.length() <= md5.length() && check.equals( md5.substring( 0, 5 ) );
  }

  private static boolean checkMD5_Part2( String md5 ) {
    String check = "000000";
    return check.length() <= md5.length() && check.equals( md5.substring( 0, 6 ) );
  }
}
