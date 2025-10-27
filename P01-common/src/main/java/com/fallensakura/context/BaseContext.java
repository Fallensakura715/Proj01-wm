package com.fallensakura.context;

public class BaseContext {
    private static final ThreadLocal<Long> userIdHoler = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        userIdHoler.set(id);
    }

    public static Long getCurrentId() {
        return userIdHoler.get();
    }

    public static void clear() {
        userIdHoler.remove();
    }
}
