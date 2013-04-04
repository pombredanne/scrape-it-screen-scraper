import java.util.regex.*;

class testergex
{
  public static void main(String[] args)
  {
    String txt="\"dick\",\"lover\"";

    String re1=".*?";	// Non-greedy match on filler
    String re2="\"";	// Uninteresting: c
    String re3=".*?";	// Non-greedy match on filler
    String re4="(\")";	// Any Single Character 1
    String re5="(,)";	// Any Single Character 2
    String re6="(\")";	// Any Single Character 3

    Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    Matcher m = p.matcher(txt);
    if (m.find())
    {
        String c1=m.group(1);
        String c2=m.group(2);
        String c3=m.group(3);
        System.out.print("("+c1.toString()+")"+"("+c2.toString()+")"+"("+c3.toString()+")"+"\n");
    }
  }
}