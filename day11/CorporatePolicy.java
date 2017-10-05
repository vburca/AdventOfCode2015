import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class SingleNight {

  private static HashMap<String, Integer> distances = new HashMap<String, Integer>();
  private static HashSet<String> cities = new HashSet<String>(); 

  private static void part1( BufferedReader bf ) {
    String line;

    try {
      while ( ( line = bf.readLine() ) != null ) {
        parseLine( line );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    //System.out.format( "Distances: %s\n", distances.toString() );
    //System.out.format( "Cities: %s\n", cities.toString() );
  
    getPermutations( new ArrayList<String>( cities ) );
    //System.out.format( "Permutations: %s\n", permutations.toString() );
  
    int minDistance = -1;
    ArrayList<String> minRoute = new ArrayList<String>();
    for ( int i = 0; i < permutations.size(); i += 1 ) {
      int routeTotal = 0;
      ArrayList<String> currPerm = permutations.get( i );
      for ( int j = 0; j < currPerm.size() - 1; j += 1 ) {
        String city1 = currPerm.get( j );
        String city2 = currPerm.get( j + 1 );
        
        routeTotal += distances.get( String.format( "%s%s", city1, city2 ) );
      }

      if ( minDistance < 0 || minDistance > routeTotal ) {
        minDistance = routeTotal;
        minRoute = currPerm;
      }
    }

    System.out.format( "Minimum route for Santa is: %s with total distance of: %d\n", minRoute.toString(), minDistance );
  }

  private static void part2( BufferedReader bf ) {
    String line;
    
    try {
      while ( ( line = bf.readLine() ) != null ) {
        parseLine( line );
      }
    } catch ( IOException e ) {
      e.printStackTrace();
    }

    getPermutations( new ArrayList<String>( cities ) );
    //System.out.format( "Permutations: %s\n", permutations.toString() );
  
    int maxDistance = -1;
    ArrayList<String> maxRoute = new ArrayList<String>();
    for ( int i = 0; i < permutations.size(); i += 1 ) {
      int routeTotal = 0;
      ArrayList<String> currPerm = permutations.get( i );
      for ( int j = 0; j < currPerm.size() - 1; j += 1 ) {
        String city1 = currPerm.get( j );
        String city2 = currPerm.get( j + 1 );
        
        routeTotal += distances.get( String.format( "%s%s", city1, city2 ) );
      }

      if ( maxDistance < routeTotal ) {
        maxDistance = routeTotal;
        maxRoute = currPerm;
      }
    }

    System.out.format( "Maximum route for Santa is: %s with total distance of: %d\n", maxRoute.toString(), maxDistance );
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

  private static ArrayList<ArrayList<String>> permutations = new ArrayList<ArrayList<String>>();
  
  private static void getPermutations( ArrayList<String> currentCities ) {
    getPermutations( currentCities, new ArrayList<String>() );
  }
  private static void getPermutations( ArrayList<String> currentCities, ArrayList<String> partial ) {  
    if ( currentCities.size() == 0 ) {
      permutations.add( partial );
    } else {
      for ( int i = 0; i < currentCities.size(); i += 1 ) {
        ArrayList<String> remainingCities = (ArrayList<String>)currentCities.clone();
        String pickedCity = remainingCities.remove( i );
        ArrayList<String> newPartial = (ArrayList<String>)partial.clone();
        newPartial.add( pickedCity );
        getPermutations( remainingCities, newPartial );
      }
    }
  }
  
  private static final String LINE = "(\\w+) to (\\w+) = ([0-9]*)";
  private static final Pattern LINE_PATTERN = Pattern.compile( LINE );
  private static void parseLine( String line ) {
    Matcher lineMatcher = LINE_PATTERN.matcher( line );
    
    if ( lineMatcher.find() ) {
      String city1 = lineMatcher.group( 1 );
      String city2 = lineMatcher.group( 2 );
      int distance = Integer.parseInt( lineMatcher.group( 3 ) );

      distances.put( String.format( "%s%s", city1, city2 ), distance );
      distances.put( String.format( "%s%s", city2, city1 ), distance );
      cities.add( city1 );
      cities.add( city2 );
    } else {
      System.out.format( "No match found for input line: %s\n", line );
    }
  }
} 
