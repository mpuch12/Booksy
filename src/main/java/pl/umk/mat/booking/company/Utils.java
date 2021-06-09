package pl.umk.mat.booking.company;

import java.lang.reflect.Field;

public class Utils {
    public static <T> T updateObject(T updating, T toUpdate) throws Exception{
        var fields = toUpdate.getClass().getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);
            var newField = field.get(updating);
            var oldField = field.get(toUpdate);
            if (newField != null && !oldField.equals(newField)) {
                field.set(toUpdate, newField);
            }
        }
        return toUpdate;
    }
}
