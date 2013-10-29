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

    public static Integer generateRandomInteger(int size) {
        int result = (int)(Math.random()*10);
        for (int i = 0; i < size; i++) {
            result = result * 10 + (int)(Math.random()*10);
        }
        return new Integer(result);
    }
}