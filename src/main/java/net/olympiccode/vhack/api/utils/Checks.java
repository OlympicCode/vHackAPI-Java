package net.olympiccode.vhack.api.utils;

public class Checks {

    public static void notNull(final Object argument, final String name)
    {
        if (argument == null)
            throw new IllegalArgumentException(name + " may not be null");
    }

    public static void check(final boolean expression, final String message, final Object arg)
    {
        if (!expression)
            throw new IllegalArgumentException(String.format(message, arg));
    }

}
