/*

Assignment number: 8

File Name: RecursionHW8.java

Name: Ben Terem

Student ID: 309981512

Email: ben.terem@post.idc.ac.il

*/
import std.StdDraw;

/**
 * Created by Ben on 02-Jan-18.
 */
public class RecursionHW8 {

    public static void main(String args[]){
        //test1(); // add()
        //test2(); // int2Bin()
        //test3(); // parseInt()
        //test4(); // isPalindrome()
        // Sierpinski()
        //test5();
        //test6();
    }

    /** Returns the sum of two positive integers
     *
     * @param a
     * @param b
     * @return
     */
    public static int add(int a, int b){
        if (b == 0)  return a;
        return add(a + 1, b - 1);
    }

    /** Takes in an integer in base 10 and returns its binary form
     *
     * @param n
     * @return
     */
    public static String int2Bin (int n){
        if (n < 2) return  "" + n % 2;
        String last =  ((n % 2) == 0) ? "0" : "1";
        return int2Bin( n / 2) + last;
    }

    /** Converts a string representation of a number and returns its int form. In case of an illegal input, i.e. a char, throws an exception
     *
     * @param str
     * @return
     */
    public static int parseInt(String str){
        if(str.length() == 0) {
            return 0;
            }
        if (str.charAt(str.length() - 1) < 48 || str.charAt(str.length() - 1) > 57) {
            throw new IllegalArgumentException();
        }
        return str.charAt(str.length() - 1) - 48 + parseInt(str.substring(0, str.length() - 1)) * 10;
    }

    /** Recieves a word and checks if its a palindrome.
     *
     * @param str
     * @return
     */
    public static boolean isPalindrome(String str){
        int N = str.length();
        if (N < 2) return true;

        if (str.charAt(0) == str.charAt(N - 1)) {
            return isPalindrome(str.substring(1, N - 1));
        }
        return false;
    }

    /** Given a depth of size n, creates a Sierpinski Triangle of that depth.
     *
     * @param n
     */
    public static void sierpinski (int n){
        // Stores the x and y values of the vertices in an array
        double [] xVertex = {0.000, 1.000, 0.500};
        double [] yVertex = {0.000, 0.000, 0.866};

        // Draws the sides of the triangle
        StdDraw.line(xVertex[0], yVertex[0], xVertex[1], yVertex[1]);
        StdDraw.line(xVertex[1], yVertex[1], xVertex[2], yVertex[2]);
        StdDraw.line(xVertex[2], yVertex[2], xVertex[0], yVertex[0]);
        // Calls on the drawing method
        sierpinskiDraw(xVertex[0], xVertex[1], xVertex[2], yVertex[0], yVertex[1], yVertex[2], n);
    }

    /** Auxielery Drawing function for sierpinsky()
     *
     * @param x0
     * @param x1
     * @param x2
     * @param y0
     * @param y1
     * @param y2
     * @param n
     */
    public static void sierpinskiDraw(double x0, double x1, double x2, double y0, double y1, double y2, int n){
        if (n > 1) {
            // Draws The first inner triangle
            StdDraw.line(0.5 * (x0 + x1), 0.5 * (y0 + y1), 0.5  *(x1 + x2), 0.5 * (y1 + y2));
            StdDraw.line(0.5 * (x1 + x2), 0.5 * (y1 + y2), 0.5  *(x2 + x0), 0.5 * (y2 + y0));
            StdDraw.line(0.5 * (x2 + x0), 0.5 * (y2 + y0), 0.5 * (x0 + x1), 0.5 * (y0 + y1));
            // Draws the remaining n - 1 triangles
            sierpinskiDraw(x0, x2, 0.5 *(x2 + x0), y0, y0, 0.5 * (y2 + y0), n - 1);
            sierpinskiDraw(x2, x1, 0.5 * (x1 + x2), y0, y0, 0.5 * (y1 + y2), n -1);
            sierpinskiDraw(0.5 * (x0 + x2), 0.5 * (x1 + x2), 0.5 * (0.5 * (x0 + x2) + 0.5 * (x1 + x2)), 0.5 * (y0 + y2), 0.5 * (y0 + y2), y2, n - 1);
        }
    }

    //Tests the add method
    public static void test1(){
        System.out.println(add(3,5));
        System.out.println(add(500 ,12556));
        System.out.println(add(0, 0));
    }
    // Tests the int2Bin method
    public static void test2(){
        System.out.println(int2Bin(5));
        System.out.println(int2Bin(35));
        System.out.println(int2Bin(2));
        System.out.println(int2Bin(0));
    }

    // Tests parseInt method
    public static void test3(){
        System.out.println(parseInt("2356")); // True
        System.out.println(parseInt("7509038")); // True
        System.out.println(parseInt("")); // True
        // Uncomment to test exception
        //System.out.println(parseInt("23r6")); // False
    }

    // Tests Palindrome method
    public static void test4() {
        System.out.println(isPalindrome("madam"));
        System.out.println(isPalindrome("avid diva"));
        System.out.println(isPalindrome("john"));
        System.out.println(isPalindrome(""));
    }

    // Tests  5 and 6 are for sierpinski
    public static void test5(){
        sierpinski(3);
    }

    public static void test6(){
        sierpinski(9);
    }
}
