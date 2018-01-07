package net.olympiccode.vhack.api.requests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.Objects;
import java.util.stream.Collectors;

public class Response implements Closeable
{
    public static final int ERROR_CODE = 0;
    public static final String ERROR_MESSAGE = "ERROR";

    private final String response;
    private final okhttp3.Response rawResponse;



    protected Response(final okhttp3.Response response)
    {
        this.rawResponse = response;
        if (response == null || Objects.requireNonNull(response.body()).contentLength() == 0)
        {
            this.response = null;
            return;
        }

        InputStream body = null;
        BufferedReader reader = null;
        try
        {
            body = Requester.getBody(response);
            reader = new BufferedReader(new InputStreamReader(body));
            char begin;
            int mark = 1;
            do
            {
                reader.mark(mark++);
                begin = (char) reader.read();
            }
            while (Character.isWhitespace(begin));

            reader.reset();

            switch (begin) {
                case '{':
                    this.response = new JSONObject(new JSONTokener(reader)).toString();
                    break;
                case '[':
                    this.response = new JSONArray(new JSONTokener(reader)).toString();
                    break;
                default:
                    this.response = reader.lines().collect(Collectors.joining());
                    break;
            }
        }
        catch (final Exception ee)
        {
            throw new IllegalStateException("An error occurred while parsing the response for a RestAction", ee);
        }
        finally
        {
            try
            {
                Objects.requireNonNull(body).close();
                Objects.requireNonNull(reader).close();
            } catch (NullPointerException | IOException ignored) {}
        }
    }



    public String getString()
    {
        return Objects.toString(response);
    }

    public okhttp3.Response getRawResponse()
    {
        return this.rawResponse;
    }

    @Override
    public String toString()
    {
        return "HTTPResponse[" + response + ']';
    }

    @Override
    public void close()
    {
        if (rawResponse != null)
            rawResponse.close();
    }
}
