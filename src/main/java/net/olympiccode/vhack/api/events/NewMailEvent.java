package net.olympiccode.vhack.api.events;

import net.olympiccode.vhack.api.vHackAPI;

public class NewMailEvent extends Event {
    protected final int quantity;
    public NewMailEvent(vHackAPI api, int quantity) {
        super(api);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
