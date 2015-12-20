
public class Tuple<X, Y> {
  private X x;
  private Y y;

  public Tuple( X x, Y y ) {
    this.x = x;
    this.y = y;
  }

  public X getX() {
    return this.x;
  }

  public Y getY() {
    return this.y;
  }

  public void setX( X x ) {
    this.x = x;
  }

  public void setY( Y y ) {
    this.y = y;
  }

  @Override
  public int hashCode() {
    int result = 11; // You need large primes here!
    result = 571 * result + this.x.hashCode();
    result = 601 * result + this.y.hashCode();
    return result;
  }

  @Override
  public boolean equals( Object obj ) {
    if ( this == obj ) {
      return true;
    }
    
    if ( obj == null ) {
      return false;
    }

    if ( !this.getClass().equals( obj.getClass() ) ) {
      return false;
    }

    Tuple other = (Tuple) obj;
    if ( !this.x.equals( other.getX() ) ) {
      return false;
    }
    if ( !this.y.equals( other.getY() ) ) {
      return false;
    }

    return true;
  }
}
