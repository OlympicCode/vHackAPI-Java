package net.olympiccode.vhack.api.events;

import net.olympiccode.vhack.api.vHackAPI;

public class Event {
    protected final vHackAPI api;

    public Event(vHackAPI api, long responseNumber)
    {
        this.api = api;
    }

    public Event(vHackAPI api)
    {
        this.api = api;
    }

    public vHackAPI getAPI()
    {
        return api;
    }
}
