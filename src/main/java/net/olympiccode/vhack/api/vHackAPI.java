package net.olympiccode.vhack.api;

import net.olympiccode.vhack.api.entities.console.Console;
import net.olympiccode.vhack.api.entities.User;
import net.olympiccode.vhack.api.entities.tasks.UpgradeManager;
import net.olympiccode.vhack.api.events.EventListener;

import java.util.List;

public interface vHackAPI {

    enum Status
    {
        INITIALIZING(true),
        INITIALIZED(true),
        LOGGING_IN(true),
        AWAITING_LOGIN_CONFIRMATION(true),
        LOADING_SUBSYSTEMS(true),
        CONNECTED(true),
        SHUTDOWN,
        FAILED_TO_LOGIN;

        private final boolean isInit;

        Status(boolean isInit)
        {
            this.isInit = isInit;
        }

        Status()
        {
            this.isInit = false;
        }

        public boolean isInit()
        {
            return isInit;
        }
    }

    Status getStatus();


    void addEventListener(Object... listeners);

    void removeEventListener(Object... listeners);

    List<EventListener> getRegisteredListeners();

    Console getConsole();

    User getUser();

    UpgradeManager getUpgradeManager();
}
