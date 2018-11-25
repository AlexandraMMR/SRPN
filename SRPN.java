import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.lang.Integer;




public class SRPN {

    public static void main(String[] args) throws IOException {

        long n1;
        long n2;
        SRPNRandom random = new SRPNRandom();
        Stack<String> stack = new Stack<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "ascii"));

	while(true){

            String command = reader.readLine();
          
            //tries all the characters with functions,otherwise prints error message
            try{
                //Close on EOF 
                if(command == null){
                    //Exit code 0
                    System.exit(0);
                }

                /**
                 * If the character is a number, 0 to 9, check if it has to be treated as an octal if there is a 0 in front or a decimal,
                 * for saturation and stack overflow
                 */
                for (String input: command.split("\\n+|\\s+")){    //splits the string if there is a return \\n or a white space \\s
                    if (!input.equals("+") && !input.equals("-") && !input.equals("*") && !input.equals("/") && !input.equals("%") && !input.equals("^") && !input.contains("#") && !input.equals("=") && !input.equals("d") && !input.equals("r") && !input.equals("rachid")){
                        boolean stackOverflow = stackOverflow(stack);
                        if (!stackOverflow){  
                            long octal_n;
                            if (input.startsWith("0") || input.startsWith("-0")){   //checks if it is an octal 
                                if (input.contains("8") || input.contains("9")){
                                    break;  
                                }
                                else if (Long.parseLong(input, 8) > Integer.MAX_VALUE){
                                    stack.push((String.valueOf(Integer.MAX_VALUE))); //MAX_VALUE = 2147483647
                                }
                                else if (Long.parseLong(input, 8) < Integer.MIN_VALUE){
                                    stack.push(String.valueOf(Integer.MIN_VALUE));  //MIN_VALUE = -2147483648
                                }
                                else{
                                    octal_n = Long.parseLong(input, 8);
                                    stack.push(String.valueOf(octal_n));
                                }
                            }
                            else{
                                if (Long.parseLong(input, 10) > Integer.MAX_VALUE){
                                    stack.push((String.valueOf(Integer.MAX_VALUE)));
                                }
                                else if (Long.parseLong(input, 10) < Integer.MIN_VALUE){
                                    stack.push(String.valueOf(Integer.MIN_VALUE));
                                }
                                else{
                                    stack.push(input);  
                                }
                            }
                        }
                        else{
                            System.err.println("Stack overflow."); 
                        }
                    }

                   
                //else if statements for calculations
                    //sum
                    else if(input.equals("+")){ 
                        boolean stackUnderflow = stackUnderflow(stack);
                        if (!stackUnderflow){
                            input = stack.pop();
                            n1 = Long.parseLong(input);
                            input = stack.pop();
                            n2 = Long.parseLong(input);
                            Long result = n2 + n1;
                            betweenRange(result, stack); // checks if the result of the operation is within range MIN MAX for Integer
                        }
                        else{
                            System.err.println("Stack underflow.");
                        }
                    }
                    //subtraction
                    else if(input.equals("-")){ 
                        boolean stackUnderflow = stackUnderflow(stack);
                        if (!stackUnderflow){
                            input = stack.pop();
                            n1 = Long.parseLong(input);
                            input = stack.pop();
                            n2 = Long.parseLong(input);
                            Long result = n2 - n1;
                            betweenRange(result, stack);
                        }
                        else{
                            System.err.println("Stack underflow.");
                        }
                    }
                    //multiplication
                    else if(input.equals("*")){ 
                        boolean stackUnderflow = stackUnderflow(stack);
                        if (!stackUnderflow){
                            input = stack.pop();
                            n1 = Long.parseLong(input);
                            input = stack.pop();
                            n2 = Long.parseLong(input);
                            Long result = n2 * n1;
                            betweenRange(result, stack);
                        }
                        else{
                            System.err.println("Stack underflow.");
                        }
                    }
                    //division
                    else if(input.equals("/")){ 
                        boolean stackUnderflow = stackUnderflow(stack);
                        if (!stackUnderflow){
                            input = stack.pop();
                            n1 = Long.parseLong(input);
                            input = stack.pop();
                            n2 = Long.parseLong(input);
                            if (n1 == 0){
                                System.err.println("Divide by 0.");
                            }
                            else{
                                Long result = n2 / n1;
                                betweenRange(result, stack);
                            }
                        }
                        else{
                            System.err.println("Stack underflow.");
                        }
                    }
                    //modulus
                    else if(input.equals("%")){ 
                        boolean stackUnderflow = stackUnderflow(stack);
                        if (!stackUnderflow){
                            input = stack.pop();
                            n1 = Long.parseLong(input);
                            input = stack.pop();
                            n2 = Long.parseLong(input);
                            Long result = n2 % n1;
                            betweenRange(result, stack);
                        }
                        else{
                            System.err.println("Stack underflow.");
                        }
                    }
                    //powers
                    else if(input.equals("^")){ 
                        boolean stackUnderflow = stackUnderflow(stack);
                        if (!stackUnderflow){
                            input = stack.pop();
                            n1 = Long.parseLong(input);
                            input = stack.pop();
                            n2 = Long.parseLong(input);
                            if (n1 < 0){  //checks if the power is less than 0
                                System.err.println("Negative power.");  
                                stack.push(String.valueOf(n2));
                                stack.push(String.valueOf(n1));
                            }
                            else{
                                if (n2 / n1 > Integer.MAX_VALUE){
                                    stack.push((String.valueOf(Integer.MAX_VALUE)));
                                }
                                else if (n2 / n1 < Integer.MIN_VALUE){
                                    stack.push(String.valueOf(Integer.MIN_VALUE));
                                }
                                else{
                                    stack.push(String.valueOf((int)Math.pow(n2, n1)));  // returns an int from Math.pow
                                }
                            }
                        }
                        else{
                            System.err.println("Stack underflow.");
                        }
                    }
                    //equals
                    else if(input.equals("=")){
                        String result = stack.pop();
                        boolean stackOverflow = stackOverflow(stack);
                        if(!stackOverflow){
                            System.out.println(result);
                            stack.push(result); 
                        }
                        else{
                            System.err.println("Stack overflow.");
                        }
                    }
                    // stack
                    else if(input.equals("d")){
                        for (String value : stack){
                            System.out.println(value);
                        }
                    }
                    //comment
                    else if(input.contains("#")){
                        break;
                    }
                    //easter egg
                    else if(input.equals("rachid")){
                        System.out.println("Rachid is the best unit lecturer.");
                    }
                    //"random" number
                    else if(input.equals("r")){
                        String current_random = Integer.toString(random.listSRPNRandom());
                        stack.push(current_random);
                        boolean stackOverflow = stackOverflow(stack);
                        if (!stackOverflow){
                            stack.pop();
                            if (Integer.parseInt(current_random) > Integer.MAX_VALUE){
                                stack.push((String.valueOf(Integer.MAX_VALUE)));
                            }
                            else if (Integer.parseInt(current_random) < Integer.MIN_VALUE){
                                stack.push(String.valueOf(Integer.MIN_VALUE));
                            }
                            else{
                                stack.push(current_random);
                            }
                        }
                        else{
                            System.err.println("Stack overflow.");
                        }
                    }
                }
            }
            catch(Exception e){
                char quote = '"';
                System.err.println("Unrecognised operator or operand " + quote + command + quote + ".");
            }
        }
    }

    
    //Check for stack underflow
    public static boolean stackUnderflow(Stack stack){
        return (stack.size() <= 1);
    }


      //Check for stack overflow
    public static boolean stackOverflow(Stack stack){
        return (stack.size() >= 23);
    }

    //check number is within MIN and MAX range
    public static void betweenRange(Long numberToCheck, Stack<String> stack)
    {
        if (numberToCheck > Integer.MAX_VALUE){
            stack.push((String.valueOf(Integer.MAX_VALUE)));
        }
        else if (numberToCheck < Integer.MIN_VALUE){
            stack.push(String.valueOf(Integer.MIN_VALUE));
        }
        else{
            stack.push(String.valueOf(numberToCheck));
        }
    }



}