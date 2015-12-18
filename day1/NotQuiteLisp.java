import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class NotQuiteLisp {
  
  public static void main( String[] args ) {
    String fileName = args[0];

    try {
      FileReader reader = new FileReader( fileName );
      BufferedReader bufferedReader = new BufferedReader( reader );

      String line;

      while ( ( line = bufferedReader.readLine() ) != null ) {
        SantaStack santaStack = new SantaStack();

        for ( int i = 0; i < line.length(); i++ ) {
          if ( santaStack.getFloor() == 0 && line.charAt( i ) == ')'  ) {
            System.out.format( "Santa enters underground at instruction #%d%n", i + 1 );
          }
          santaStack.followInstruction( line.charAt( i ) );
        }
      
        System.out.format( "Floor %d%n", santaStack.getFloor() );
      }
      
      reader.close();
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }
}
