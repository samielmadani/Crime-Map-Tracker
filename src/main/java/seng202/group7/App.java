package seng202.group7;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }

    public static int power(int number1, int number2) {
        int result = 1;
        for (int i = 0; i < number2; i++) {
            result *= number1;
        }

        return result;
    }

    /**
     * Returns True if the first number is the largest. Otherwise, it returns False.
     * @param number1       The first number value.
     * @param number2       The second number value.
     * @return The result of if number1 is greater than number2.
     */
    public static boolean numbers(int number1, int number2) {
        return (number1 > number2);
    }

    public static int add(int number1, int number2) {
        return number1 + number2;
    }
}
