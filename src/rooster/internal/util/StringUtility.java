 package rooster.internal.util;
 
 import java.util.StringTokenizer;
 
 public class StringUtility
 {
   public static boolean stringHasValue(String s)
   {
     return (s != null) && (s.length() > 0);
   }
   public static String composeFullyQualifiedTableName(String catalog, String schema, String tableName, char separator)
   {
     StringBuffer sb = new StringBuffer();
     if (stringHasValue(catalog))
     {
       sb.append(catalog);
       sb.append(separator);
     }
     if (stringHasValue(schema))
     {
       sb.append(schema);
       sb.append(separator);
     }
     else if (sb.length() > 0)
     {
       sb.append(separator);
     }
     sb.append(tableName);
     
     return sb.toString();
   }
   /**
    * 
   * @method name: stringContainsSpace
   * @Description: TODO(判断�?个String对象是否含有空格)
   * @param s 
   * @return boolean   如果含有空格,返回true.没有则返回false�?
   * @throws
    */
   public static boolean stringContainsSpace(String s)
   {
     return (s != null) && (s.indexOf(' ') != -1);
   }
   /**
    * 
   * @method name: escapeStringForJava
   * @Description: TODO(替换java当中不能正常识别的字符串)
   * @param s
   * @return String    返回类型替换后的字符�?
    */
   public static String escapeStringForJava(String s)
   {
     StringTokenizer st = new StringTokenizer(s, "\"", true);
     StringBuffer sb = new StringBuffer();
     while (st.hasMoreTokens())
     {
       String token = st.nextToken();
       if ("\"".equals(token)) {
         sb.append("\\\"");
       } else {
         sb.append(token);
       }
     }
     return sb.toString();
   }
   /**
    * 
   * @method name: escapeStringForXml
   * @Description: TODO(替换xml不能识别的反斜杠)
   * @param s
   * @return    
   * @return String    返回类型
   * @throws
    */
   public static String escapeStringForXml(String s)
   {
     StringTokenizer st = new StringTokenizer(s, "\"", true);
     StringBuffer sb = new StringBuffer();
     while (st.hasMoreTokens())
     {
       String token = st.nextToken();
       if ("\"".equals(token)) {
         sb.append("&quot;");
       } else {
         sb.append(token);
       }
     }
     return sb.toString();
   }
 }
