package net.olympiccode.vhack.api;

import net.olympiccode.vhack.api.entities.impl.vHackAPIImpl;
import net.olympiccode.vhack.api.utils.Checks;
import okhttp3.OkHttpClient;

import javax.security.auth.login.LoginException;
import java.util.*;

public class vHackAPIBuilder {
    private final List<Object> listeners;

    private OkHttpClient.Builder httpClientBuilder = null;
    private int maxReconnectDelay = 900;
    private boolean autoReconnect = true;
    private String username;
    private String password;
    private String uhash;

    public vHackAPIBuilder()
    {
        listeners = new LinkedList<>();
    }


    public vHackAPIBuilder setUsername(String username)
    {
        this.username = username;
        return this;
    }


    public vHackAPIBuilder setPassword(String password)
    {
        this.password = password;
        return this;
    }

    public vHackAPIBuilder setUhash(String uhash) {
        this.uhash = uhash;
        return this;
    }

    public vHackAPIBuilder setHttpClientBuilder(OkHttpClient.Builder builder)
    {
        this.httpClientBuilder = builder;
        return this;
    }


    public vHackAPIBuilder addEventListener(Object... listeners)
    {
        Collections.addAll(this.listeners, listeners);
        return this;
    }


    public vHackAPIBuilder removeEventListener(Object... listeners)
    {
        this.listeners.removeAll(Arrays.asList(listeners));
        return this;
    }


    public vHackAPI buildAsync() throws LoginException, IllegalArgumentException
    {
        OkHttpClient.Builder httpClientBuilder = this.httpClientBuilder == null ? new OkHttpClient.Builder() : this.httpClientBuilder;
        vHackAPIImpl api = new vHackAPIImpl(httpClientBuilder, autoReconnect, maxReconnectDelay, 5);

        listeners.forEach(api::addEventListener);
        api.setStatus(vHackAPI.Status.INITIALIZED);
        api.setUhash(uhash);
        api.login(username, password);
        return api;
    }


    private vHackAPI buildBlocking(vHackAPI.Status status) throws LoginException, IllegalArgumentException, InterruptedException
    {
        Checks.notNull(status, "Status");
        Checks.check(status.isInit(), "Cannot await the status %s as it is not part of the login cycle!", status);
        vHackAPI api = buildAsync();
        while (!api.getStatus().isInit()
                || api.getStatus().ordinal() < status.ordinal())
        {
            if (api.getStatus() == vHackAPI.Status.SHUTDOWN)
                throw new IllegalStateException("vHackAPI was unable to finish starting up!");
            Thread.sleep(50);
        }

        return api;
    }


    public vHackAPI buildBlocking() throws LoginException, IllegalArgumentException, InterruptedException
    {
        return buildBlocking(vHackAPI.Status.CONNECTED);
    }
}
