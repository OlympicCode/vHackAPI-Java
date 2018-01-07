package net.olympiccode.vhack.api.entities.console;

import net.olympiccode.vhack.api.entities.console.impl.ScannedTargetImpl;
import net.olympiccode.vhack.api.exceptions.ScanFailException;

public interface Target {
    boolean isWatched();
    String getHostName();
    ScannedTargetImpl scan() throws ScanFailException;
}
