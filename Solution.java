import java.util.*;
class Solution {
  public static void main(String[] args) {
    String s = "100-(32-(42/2)*(2+3))";
    System.out.println(parseFormular(s));
  }
  
  public static void calc(Stack<Character> operators, Stack<Integer> operands)
  {

    if(operators.empty() || operands.size() < 2) return;
    Character op = operators.pop();
    int op2 = operands.pop();
    int op1 = operands.pop();
    if(op.equals('+')) {
      operands.push(op2+op1);
    } 
    else if (op.equals('-')) {
      
      operands.push(op1-op2);
    }
    else if (op.equals('*')) {
      operands.push(op1*op2);
    }
    else if (op.equals('/')) {
      operands.push(op1/op2);
    }
    else {
      operands.push(op1);
      operands.push(op2);
    }
  }
  public static int parseFormular(String s) {
    int start = 0, end = 0, size = s.length();
    int res = 0;
    Stack<Character> operators = new Stack<>();
    Stack<Integer> operands = new Stack<>();
    while(end < size) {
      while(end < size && s.charAt(end) >= 48  && s.charAt(end) <= 57) end++;
      if(end > start) {
        int num = Integer.parseInt(s.substring(start,end));
        operands.push(num);
      }
      System.out.println(operands);

      if(end >= size) break;
      else {
        if(operators.empty())
          operators.push(s.charAt(end));
        Character prevOp = operators.peek();
        // For operators priority, * = / > + = - > (
        switch (s.charAt(end)) {
          case '+': 
          case '-':
            if(!prevOp.equals('('))
              calc(operators,operands);
            operators.push(s.charAt(end));
            break;
          case '*':
          case '/':
          if(prevOp.equals('*') || prevOp.equals('/'))
            calc(operators,operands);
            operators.push(s.charAt(end));
            break;
          case '(':
            operators.push(s.charAt(end));
            break;
          case ')':
            while(!operators.empty() && !operators.peek().equals('(')) 
              calc(operators,operands);
            if(operators.empty())
              return 0;
            else 
              operators.pop();
            break;          
        }
       
      }
      end++;     
      start = end;           
    }
    while(!operators.empty())
      calc(operators,operands);
    return operands.pop();
  } 
}