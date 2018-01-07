package net.olympiccode.vhack.api.requests;

import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.vHackAPI;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class Requester {
    public static final Logger LOG = LoggerFactory.getLogger(Requester.class);
    protected final vHackAPIImpl api;
    private long lastRequest = 0;
    private final OkHttpClient httpClient;

    private volatile boolean retryOnTimeout = false;


    public Requester(vHackAPI api) {

        this.api = (vHackAPIImpl) api;
        this.httpClient = this.api.getHttpClientBuilder().build();
    }

    public static InputStream getBody(okhttp3.Response response) throws IOException {
        String encoding = response.header("content-encoding", "");
        if (Objects.equals(encoding, "gzip"))
            return new GZIPInputStream(Objects.requireNonNull(response.body()).byteStream());
        return Objects.requireNonNull(response.body()).byteStream();
    }

    public vHackAPIImpl getAPI() {
        return api;
    }

    public OkHttpClient getHttpClient() {
        return this.httpClient;
    }

    public Response getResponse(Route.CompiledRoute route) {
        if (lastRequest >= System.currentTimeMillis() - 1000) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lastRequest = System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(route.getCompiledRoute())
                .addHeader("user-agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.4; SM-N935F Build/KTU84P)")
                .addHeader("Accept-Encoding", "gzip").build();
        final Response[] response = new Response[1];

        okhttp3.Response r;
        try {
            r = httpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (r.isSuccessful()) {
            response[0] = new Response(r);
        }
        try {
            switch (response[0].getString()) {
                case "8":
                    throw new LoginException("Invalid username or password");
                case "10":
                    throw new RuntimeException("Invalid data sent");
                case "1":
                    throw new RuntimeException("Failed to process request");
            }
        } catch (final Exception ee) {
            throw new IllegalStateException("An error occurred while processing rest request", ee);
        }
        return response[0];
    }
}
