package com.meti.lib.log;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
class LogBuilder {
    String log(String message, Exception exception) {
        StringBuilder builder = new StringBuilder();
        appendMessage(message, builder)
                .appendNewLine(message, exception, builder)
                .appendException(exception, builder);
        return builder.toString();
    }

    private void appendException(Exception exception, StringBuilder builder) {
        if (exception != null) {
            builder.append(Exceptions.stackTraceString(exception));
        }
    }

    private LogBuilder appendNewLine(String message, Exception exception, StringBuilder builder) {
        if (message != null && exception != null) {
            builder.append("\n");
        }
        return this;
    }

    private LogBuilder appendMessage(String message, StringBuilder builder) {
        if (message != null) {
            builder.append(message);
        }
        return this;
    }
}
