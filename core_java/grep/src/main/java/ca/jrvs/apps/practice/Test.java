package ca.jrvs.apps.practice;

public class Test{
  public static void main(String[] args) {
    RegexExc regex = new RegexExcImp();

    System.out.println(regex.matchJpeg("photo.jpg"));    // true
    System.out.println(regex.matchJpeg("document.pdf")); // false

    System.out.println(regex.matchIP("192.168.0.1"));    // true
    System.out.println(regex.matchIP("999.999.999.999"));// true (regex not strict)

    System.out.println(regex.isEmptyLine("   "));       // true
    System.out.println(regex.isEmptyLine("text"));      // false
  }
}
