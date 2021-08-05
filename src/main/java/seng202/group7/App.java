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
}
