package net.olympiccode.vhack.api.entities;


import net.olympiccode.vhack.api.entities.tasks.TaskType;

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
   int getStat(TaskType t);

   int getFirewall();
}
