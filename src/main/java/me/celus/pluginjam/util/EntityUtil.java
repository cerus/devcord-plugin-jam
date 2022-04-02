package me.celus.pluginjam.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.entity.FallingBlock;

/**
 * Collection of utility methods for entities
 */
public class EntityUtil {

    private EntityUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * Set the FallHurtAmount and FallHurtMax values
     * <p>
     * Unfortunately there is not api for this. :(
     *
     * @param fallingBlock   The falling block update
     * @param fallHurtAmount The amount of damage to set
     */
    public static void setFallHurtAmount(final FallingBlock fallingBlock, final float fallHurtAmount) {
        try {
            final Method getHandleMethod = fallingBlock.getClass().getDeclaredMethod("getHandle");
            getHandleMethod.setAccessible(true);
            final Object handle = getHandleMethod.invoke(fallingBlock);

            final Class<?> nmsClass = handle.getClass();
            final Method bMethod = nmsClass.getDeclaredMethod("b", float.class, int.class);
            bMethod.setAccessible(true);
            bMethod.invoke(handle, fallHurtAmount, (int) fallHurtAmount);
        } catch (final InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
