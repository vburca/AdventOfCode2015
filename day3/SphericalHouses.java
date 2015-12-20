import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashSet;

public class SphericalHouses {
 
  public static void main( String[] args ) {
    String fileName = args[0];

    try {
      FileReader reader = new FileReader( fileName );
      BufferedReader bufferedReader = new BufferedReader( reader );

      int santaCode, roboCode;
      Tuple<Integer, Integer> santaPosition = new Tuple<Integer, Integer>(0, 0);
      Tuple<Integer, Integer> roboPosition = new Tuple<Integer, Integer>(0, 0);
      HashSet<Tuple<Integer, Integer>> visited = new HashSet<Tuple<Integer, Integer>>();

      if ( visited.add( santaPosition ) ) {
        System.out.format( "Inserted ( %d, %d )%n", santaPosition.getX(), santaPosition.getY() );
      }

      while ( ( santaCode = bufferedReader.read() ) != -1 && 
              ( roboCode = bufferedReader.read() ) != -1 ) {
        // Santa
        char santaCh = (char) santaCode;
        insertNewPosition( "Santa", santaCh, santaPosition, visited );
        // Robo
        char roboCh = (char) roboCode;
        insertNewPosition( "Robo", roboCh, roboPosition, visited );
      }
      reader.close();
      
      System.out.format( "Houses that received at least one present: %d%n", visited.size() );
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  private static void insertNewPosition( String who, char ch, Tuple<Integer, Integer> position, HashSet<Tuple<Integer, Integer>> visited ) {
    boolean insert = true;
    switch ( ch ) {
      case '^': position.setY( position.getY() + 1 ); break;
      case '>': position.setX( position.getX() + 1 ); break;
      case 'v': position.setY( position.getY() - 1 ); break;
      case '<': position.setX( position.getX() - 1 ); break;
      default: insert = false; break;
    }
    
    Tuple<Integer, Integer> newPosition = new Tuple<Integer, Integer>( position.getX(), position.getY() );
    
    if ( insert && visited.add( newPosition ) ) {
      System.out.format( "[ %s ] Inserted ( %d, %d )%n", who, newPosition.getX(), newPosition.getY() );
    }
  }
}
