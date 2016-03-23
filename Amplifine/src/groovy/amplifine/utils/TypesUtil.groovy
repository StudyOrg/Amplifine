package amplifine.utils;

public class TypesUtil {
    public static Integer parseInt(String str) {
        Integer retVal;

        try {
            retVal = Integer.parseInt(str);
        } catch (NumberFormatException ignored) {
            retVal = null;
        }

        return retVal;
    }
}
