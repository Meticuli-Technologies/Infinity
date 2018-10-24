package com.meti.app.server;

import java.io.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public class ServerInput {
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public ServerInput(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;

        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.writer = new PrintWriter(outputStream);
    }

    public String readLine() throws IOException {
        String returnString = reader.readLine();
        return returnString == null ? "" : returnString;
    }

    public void write(String line){
        writer.println(line);
        writer.flush();
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        reader.close();
        writer.close();
    }
}
