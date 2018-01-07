package net.olympiccode.vhack.api.entities.impl;

import net.olympiccode.vhack.api.entities.console.Console;
import net.olympiccode.vhack.api.entities.User;
import net.olympiccode.vhack.api.entities.console.impl.ConsoleImpl;
import net.olympiccode.vhack.api.events.Event;
import net.olympiccode.vhack.api.events.EventListener;
import net.olympiccode.vhack.api.requests.Requester;
import net.olympiccode.vhack.api.requests.Response;
import net.olympiccode.vhack.api.requests.Route;
import net.olympiccode.vhack.api.vHackAPI;
import okhttp3.OkHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public class vHackAPIImpl implements vHackAPI {


    private static final Logger LOG = LoggerFactory.getLogger(vHackAPI.class);

    private final OkHttpClient.Builder httpClientBuilder;
    private final int maxReconnectDelay;
    private List<EventListener> listeners = new ArrayList<>();

    private Status status = Status.INITIALIZING;
    private Requester requester;
    private boolean autoReconnect;
    private String username = null;
    private String password = null;
    private String uhash = "";

    private UserImpl user = new UserImpl(this);
    private ConsoleImpl console = new ConsoleImpl(this);

    public vHackAPIImpl(OkHttpClient.Builder httpClientBuilder, boolean autoReconnect, int maxReconnectDelay, int corePoolSize) {
        this.httpClientBuilder = httpClientBuilder;
        this.autoReconnect = autoReconnect;
        this.maxReconnectDelay = maxReconnectDelay;
        this.requester = new Requester(this);
    }


    public void login(String username, String password) throws LoginException {
        setStatus(Status.LOGGING_IN);
        if (username == null || username.isEmpty())
            throw new LoginException("Provided username was null or empty!");
        if (password == null || password.isEmpty())
            throw new LoginException("Provided password was null or empty!");
        setUsername(username);
        setPassword(password);
        setStatus(Status.AWAITING_LOGIN_CONFIRMATION);
        verifyDetails();
        setStatus(Status.CONNECTED);
        LOG.info("Login Successful!");
    }

    private void verifyDetails() throws LoginException {
        Route.CompiledRoute r = Route.Misc.UPDATE.compile(this);
        try {
            Response resp = getRequester().getResponse(r);
            JSONObject userResponse = new JSONObject(resp.getString());
            this.uhash = userResponse.getString("uhash");
            user.setMoney(Long.valueOf(userResponse.getString("money")));
            user.setIp(userResponse.getString("ip"));
            user.setPackages(Integer.parseInt(userResponse.getString("bonus")));
            user.setGoldPackages(Integer.parseInt(userResponse.getString("allbox")) - Integer.parseInt(userResponse.getString("bonus")));
            user.setUnreadMail(Integer.parseInt(userResponse.getString("urmail")));
            user.setScore(Integer.parseInt(userResponse.getString("score")));
            user.setRank(Integer.parseInt(userResponse.getString("rank")));
            user.setActiveSpyware(Integer.parseInt(userResponse.getString("actspyware")));
            user.setId(Integer.parseInt(userResponse.getString("id")));
            user.setActive(Integer.parseInt(userResponse.getString("active")) > 0);
            user.setReputation(Integer.parseInt(userResponse.getString("elo")));
            user.setNetcoins(Integer.parseInt(userResponse.getString("netcoins")));
            user.setBoosters(Integer.parseInt(userResponse.getString("boost")));
        } catch (RuntimeException e) {
            Throwable ex = e.getCause() != null ? e.getCause().getCause() : null;
            if (ex instanceof LoginException)
                throw (LoginException) ex;
            else
                throw e;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Status getStatus() {
        return null;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addEventListener(Object... listener) {
        for (Object o : listener) if (o instanceof EventListener) listeners.add((EventListener) o);
    }

    public void fireEvent(Event e) {
        listeners.forEach(eventListener -> eventListener.onEvent(e));
    }

    public OkHttpClient.Builder getHttpClientBuilder() {
        return httpClientBuilder;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public void removeEventListener(Object... listener) {
        for (Object o : listener) if (o instanceof EventListener) listeners.remove(o);
    }

    public List<EventListener> getRegisteredListeners() {
        return listeners;
    }

    @Override
    public Console getConsole() {
        return console;
    }

    public String getUhash() {
        return uhash;
    }

    public void setUhash(String uhash) {
        this.uhash = uhash;
    }

    public Requester getRequester() {
        return requester;
    }

    @Override
    public User getUser() {
        return user;
    }

    public UserImpl getUserI() {
        return user;
    }
}
