package com.beyondbell.bugisoft.old.Utilities.TextFormatters;

public final class IdFormatter {
    public static String trimId(final String mention) {
        return mention.substring(2,mention.length() - 1);
    }
}