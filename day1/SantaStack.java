public class SantaStack {
  private Stack<Character> stack = new Stack<Character>();

  public void followInstruction( Character instruction ) {
    if ( instruction != null &&
          !stack.isEmpty() && !stack.peek().equals( instruction ) ) {
      stack.pop();
    } else if ( instruction != null ) {
      stack.push( instruction );
    }
  }

  public int getFloor() {
    int multiplier = !stack.isEmpty() && stack.peek().equals(')') ? -1 : 1;
    return multiplier * stack.size();
  }
}
