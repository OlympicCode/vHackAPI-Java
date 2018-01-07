package net.olympiccode.vhack.api.entities;

public interface User {
   long getMoney();
   int getPackages();
   int getGoldPackages();
   int getUnreadMail();
   String getIP();
   int getScore();
   int getRank();
   int getActiveSpyware();
   int getId();
   boolean isActive();
   int getReputation();
   int getNetcoins();
   int getBoosters();
}
