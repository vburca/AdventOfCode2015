import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class NoMath {

  public static void main( String[] args ) {
    String fileName = args[0];

    try {
      FileReader reader = new FileReader( fileName );
      BufferedReader bufferedReader = new BufferedReader( reader );

      String line;
      int totalPaper = 0;
      int totalRibbon = 0;

      while ( ( line = bufferedReader.readLine() ) != null ) {
        String[] dimensions = line.split( "x" );
        int l = Integer.parseInt( dimensions[0] );
        int w = Integer.parseInt( dimensions[1] );
        int h = Integer.parseInt( dimensions[2] );

        totalPaper += calculatePaper( l, w, h );
        totalRibbon += calculateRibbon( l, w, h );
        // System.out.format( "Dimensions: %d x %d x %d%n", l, w, h );
      }
      
      System.out.format( "Total square feet of wrapping paper: %d%n", totalPaper );
      System.out.format( "Total feet of ribbon: %d%n", totalRibbon ); 

      reader.close();
    } catch ( IOException e ) {
      e.printStackTrace();
    }

  }

  private static int calculatePaper( int l, int w, int h ) {
    int side = w * h;
    int top = w * l;
    int front = l * h;
    int totalSurface = 2 * side + 2 * front + 2 * top;

    int extra = 0;
    if ( side <= top && side <= front ) {
      extra = side;
    } else if ( top <= side && top <= front ) {
      extra = top;
    } else {
      extra = front;
    }

    return totalSurface + extra;
  }

  private static int calculateRibbon( int l, int w, int h ) {
    int side = 2 * ( w + h );
    int top = 2 * ( w + l );
    int front = 2 * ( l + h );

    int wrapLength = 0;
    if ( side <= top && side <= front ) {
      wrapLength = side;
    } else if ( top <= side && top <= front ) {
      wrapLength = top;
    } else {
      wrapLength = front;
    }

    return wrapLength + l * w * h;
  }

}
