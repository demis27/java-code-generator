package $defaultPackageName$;

import java.util.UUID;

public class PersistenceTestUtil {
   public static String generateRandomString(int size) {
       StringBuilder builder = new StringBuilder(UUID.randomUUID().toString().replaceAll("-", ""));
       while (builder.length() < size) {
           builder.append(UUID.randomUUID().toString().replaceAll("-", ""));
       }
       if (builder.length() > size) {
           return builder.toString().substring(0, size);
       }
       else {
           return builder.toString();
       }
   }
}