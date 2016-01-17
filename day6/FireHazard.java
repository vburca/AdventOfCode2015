import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class FireHazard {

  private enum Action {
    TURN_ON, TURN_OFF, TOGGLE
  }

  public static class Input {
    private int startX, startY, endX, endY;
    private Action action;

    public Input( int sX, int sY, int eX, int eY, Action a ) {
      this.startX = sX;
      this.startY = sY;
      this.endX = eX;
      this.endY = eY;
      this.action = a;
    }

    public int getStartX() {
      return this.startX;
    }
    public int getStartY() {
      return this.startY;
    }
    public int getEndX() {
      return this.endX;
    }
    public int getEndY() {
      return this.endY;
    }
    public Action getAction() {
      return this.action;
    }

    public String[] getKeys() {
      int i, j, k = 0;
      String[] keys = new String[ (endX - startX + 1) * (endY - startY + 1) ];
      
      for ( i = startX; i <= endX; i++ ) {
        for ( j = startY; j <= endY; j++ ) {
          keys[ k ] = String.format( "%d,%d", i, j ); 
          k += 1;
        }
      }

      return keys;
    }

    public void print() {
      System.out.format( "[ (%d, %d), (%d, %d), %s ]\n", 
          this.startX, this.startY, this.endX, this.endY, action );
    }
  }

  private static void part1( BufferedReader bf ) {
    String line;
    HashSet<String> lights = new HashSet<String>();

    try {
      while ( ( line = bf.readLine() ) != null ) {
        Input in = parseInput( line );
        in.print(); 
       
        if ( Action.TOGGLE.equals( in.getAction() ) ) {
          toggleLights( in.getKeys(), lights );
        } else if ( Action.TURN_ON.equals( in.getAction() ) ) {
          turnOnLights( in.getKeys(), lights );
        } else if ( Action.TURN_OFF.equals( in.getAction() ) ) {
          turnOffLights( in.getKeys(), lights );
        }
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }
    
    System.out.format( "Number of lights that are on: %d\n", lights.size() );
  }

  private static void part2( BufferedReader bf ) {
    String line;
    HashMap<String, Integer> lights = new HashMap<String, Integer>();
    
    try {
      while ( ( line = bf.readLine() ) != null ) {
        Input in = parseInput( line );
        in.print(); 
       
        if ( Action.TOGGLE.equals( in.getAction() ) ) {
          toggleLights2( in.getKeys(), lights );
        } else if ( Action.TURN_ON.equals( in.getAction() ) ) {
          turnOnLights2( in.getKeys(), lights );
        } else if ( Action.TURN_OFF.equals( in.getAction() ) ) {
          turnOffLights2( in.getKeys(), lights );
        }
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    System.out.format( "Total brightness: %d\n", lights.values().stream().reduce( 0, (a, b) -> a + b ) );
  }


  public static void main( String[] args ) throws Exception {
    String fileName = args[0];

    try {
      FileReader reader = new FileReader( fileName );
      
      // part1( new BufferedReader( reader ) );
      part2( new BufferedReader( reader ) );

      reader.close();
    } catch ( IOException e ) {
      e.printStackTrace();
    }
  }

  
  private static void toggleLights( String[] keys, HashSet<String> hashSet ) {
    for ( String key : keys ) {
      if ( hashSet.contains( key ) ) {
        hashSet.remove( key );
      } else {
        hashSet.add( key );
      }
    }
  }

  private static void turnOnLights( String[] keys, HashSet<String> hashSet ) {
    for ( String key : keys ) {
      hashSet.add( key );
    }
  }

  private static void turnOffLights( String[] keys, HashSet<String> hashSet ) {
    for ( String key : keys ) {
      hashSet.remove( key );
    }
  }

  private static void toggleLights2( String[] keys, HashMap<String, Integer> hashMap ) {
    for ( String key : keys ) {
      int v = 2;
      if ( hashMap.containsKey( key ) ) {
        v = hashMap.get( key ) + 2;
      } 
      hashMap.put( key, v );
    }
  }

  private static void turnOnLights2( String[] keys, HashMap<String, Integer> hashMap ) {
    for ( String key : keys ) {
      int v = 1;
      if ( hashMap.containsKey( key ) ) {
        v = hashMap.get( key ) + 1;
      }
      hashMap.put( key, v );
    }
  }

  private static void turnOffLights2( String[] keys, HashMap<String, Integer> hashMap ) {
    for ( String key : keys ) {
      if ( hashMap.containsKey( key ) ) {
        int v = hashMap.get( key );
        if ( v > 1 ) {
          hashMap.put( key, v - 1 );
        } else {
          hashMap.remove( key );
        }
      }
    }
  }
    
  private static final String INPUT_LINE = new String(
      "(turn on|turn off|toggle) (\\d{1,3},\\d{1,3}) through (\\d{1,3},\\d{1,3})" );
  private static final Pattern inputPattern = Pattern.compile( INPUT_LINE );
  private static Input parseInput( String line ) {
    Matcher matcher = inputPattern.matcher( line );
    
    if ( !matcher.find() ) {
      System.out.format( "No matches found for line %s\n", line );
      return null;
    } 
    
    String actionStr = matcher.group( 1 );
    String[] startStrs = matcher.group( 2 ).split( "," );
    String[] endStrs = matcher.group( 3 ).split( "," );
    
    Action a = null;
    switch( actionStr ) {
      case "turn on": a = Action.TURN_ON; break;
      case "turn off": a = Action.TURN_OFF; break;
      case "toggle": a = Action.TOGGLE; break;
    }

    return new Input( 
        Integer.parseInt( startStrs[0] ),
        Integer.parseInt( startStrs[1] ),
        Integer.parseInt( endStrs[0] ),
        Integer.parseInt( endStrs[1] ),
        a ); 
  }
 
} 
