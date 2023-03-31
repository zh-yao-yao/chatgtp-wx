package com.zyy.wxpush.util;


import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 空字符串处理
 */
public final class OptionalString {

    private static final String EMPTY_STR = "";


    private static final OptionalString EMPTY = new OptionalString();


    private final String value;


    private OptionalString() {
        this.value = EMPTY_STR;
    }


    public static OptionalString empty() {
        return EMPTY;
    }

    private OptionalString(String value) {
        this.value = isBlank(value) ? EMPTY_STR : value;
    }


    public static OptionalString of(String value) {
        return new OptionalString(value);
    }


    public static OptionalString ofNullable(String value) {
        return isBlank(value) ? empty() : of(value);
    }

    public String get() {
        if (isBlank(value)) {
            return EMPTY_STR;
        }
        return value;
    }

    public boolean isPresent() {
        return isNotBlank(value);
    }


    public void ifPresent(Consumer<String> consumer) {
        if (isNotBlank(value)) {
            consumer.accept(value);
        }
    }


    public OptionalString map(Function<? super String, ? extends String> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return OptionalString.ofNullable(mapper.apply(value));
        }
    }


    public String orElse(String other) {
        return isNotBlank(value) ? value : other;
    }

    public OptionalString orElseChain(String other) {
        return isNotBlank(value) ? this : of(other);
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static boolean isBlank(CharSequence str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i = 0; i < length; ++i) {
                if (!isBlankChar(str.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    private static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == 65279 || c == 8234 || c == 0;
    }


}
