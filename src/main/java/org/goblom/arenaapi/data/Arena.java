/*
 * The MIT License
 *
 * Copyright 2013 Goblom.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.goblom.arenaapi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;
import org.goblom.arenaapi.ArenaAPI;
import org.goblom.arenaapi.ArenaHandler;
import org.goblom.arenaapi.data.enums.ArenaPhase;
import org.goblom.arenaapi.data.enums.LocationType;
import org.goblom.arenaapi.events.EventCrafter;

/**
 * 
 * @TODO - Implement Min/Max Players
 * @TODO - Location Types
 * 
 * @author Goblom
 */
public class Arena implements Listener {

    private String arenaName;
    private int minPlayers, maxPlayers;
    private ArenaPhase currentPhase;

    private ArenaHandler handler;
    private List<String> players = new ArrayList<String>();
    private List<Team> teams = new ArrayList<Team>();

    private Map<String, PlayerInventory> playerInv = new HashMap();

    private Map<LocationType, String> locationNames = new HashMap();
    private Map<String, Location> locations = new HashMap();
    private List<String> locationsUsed = new ArrayList<String>();

    private int timer;
    private BukkitTask timerTask;


    public Arena(String arenaName, int maxPlayers) {
        this(arenaName, 1, maxPlayers, null);
    }

    public Arena(String arenaName, int minPlayers, int maxPlayers) {
        this(arenaName, minPlayers, maxPlayers, null);
    }

    public Arena(String arenaName, ArenaHandler handler) {
        this(arenaName, 1, 1, handler);
    }

    public Arena(String arenaName, int minPlayers, int maxPlayers, ArenaHandler handler) {
        preventItemLoss();

        this.arenaName = arenaName;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;

        this.handler = handler;

        create();
        ArenaAPI.getPlugin().getServer().getPluginManager().registerEvents(this, ArenaAPI.getPlugin());
    }

    public void delete() {
        EventCrafter.craftArenaDeleteEvent(this);
        HandlerList.unregisterAll(this);
        this.currentPhase = null;
        this.arenaName = null;
        this.playerInv = null;
        this.players = null;
        this.teams = null;
        this.locationNames = null;
        this.locations = null;
        this.locationsUsed = null;
        this.handler = null;
        this.timerTask = null;
    }

    public void create() {
        EventCrafter.craftArenaCreateEvent(this);
        this.currentPhase = ArenaPhase.CREATE;
        handler.onCreate();
    }
    
    public void load() {
       EventCrafter.craftArenaChangePhaseEvent(this, ArenaPhase.LOAD, currentPhase);
       this.currentPhase = ArenaPhase.LOAD;
       handler.onLoad();
    }
    
    public void start() {
        EventCrafter.craftArenaChangePhaseEvent(this, ArenaPhase.GAME_START, currentPhase);
        this.currentPhase = ArenaPhase.GAME_START;
        handler.start();
    }

    public void end() {
        EventCrafter.craftArenaChangePhaseEvent(this, ArenaPhase.GAME_END, currentPhase);
        this.currentPhase = ArenaPhase.GAME_START;
        handler.end();
    }

    public String getName() {
        load();
        return arenaName;
    }

    public List<String> getPlayerList() {
        load();
        return players;
    }

    public List<Player> getPlayersAsPlayers() {
        List<Player> stringAsPlayers = new ArrayList<Player>();
        for (String playerString : getPlayerList()) {
            Player player = Bukkit.getPlayer(playerString);
            if (player != null) {
                stringAsPlayers.add(player);
            }
        }
        return stringAsPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Map<LocationType, String> getLocationNames() {
        return locationNames;
    }

    public Map<String, Location> getLocations() {
        return locations;
    }

    public List<String> getLocationsUsed() {
        return locationsUsed;
    }

    public Map<String, PlayerInventory> getPlayerInventory() {
        return playerInv;
    }

    public void addPlayer(String playerName) {
        players.add(playerName);
    }

    public void remPlayer(String playerName) {
        if (players.contains(playerName)) {
            players.remove(playerName);
        }
    }

    public void addLocation(LocationType locType, String locName, Location loc) {
        locationNames.put(locType, locName);
        locations.put(locName, loc);
    }

    public boolean remLocation(LocationType locType, String locName) {
        if (locationNames.containsKey(locType)) {
            for (LocationType locTypes : locationNames.keySet()) {
                if (locTypes.equals(locType)) {
                    String keyName = locationNames.get(locType);
                    if (keyName.equals(locName) && locations.containsKey(locName)) {
                        locationNames.remove(locType);
                        locations.remove(locName);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Arena addTeam(Team team) {
        if (!teams.contains(team)) {
            EventCrafter.craftTeamAddedToArenaEvent(this, team);
            teams.add(team);
        }
        return this;
    }

    public Arena remTeam(Team team) {
        if (teams.contains(team)) {
            EventCrafter.craftTeamRemovedFromArenaEvent(this, team);
            teams.remove(team);
        }
        return this;
    }

    public ArenaHandler getHandler() {
        return handler;
    }

    public void setHandler(ArenaHandler handler) {
        this.handler = handler;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int time) {
        this.timer = time;
    }

    public boolean startTimer() {
        if (timer == 0) {
            return false;
        }
        if (timerTask != null) {
            return false;
        } else if (timerTask == null) {
            BukkitTask task = Bukkit.getScheduler().runTaskLater(ArenaAPI.getPlugin(), new Runnable() {
                public void run() {
                    handler.end();
                }
            }, timer);

            if (task != null) {
                ArenaAPI.getPlugin().getArenaTimers().put(this, task);
                return true;
            }
        }
        return false;
    }

    public boolean endTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            return true;
        }
        return false;
    }

    @EventHandler
    void onFriendlyFire(EntityDamageByEntityEvent event) {
        if (currentPhase.equals(ArenaPhase.GAME_START)) {
            Entity r = event.getDamager();
            Entity d = event.getEntity();

            if ((d instanceof Player) && (r instanceof Player)) {
                Player damager = (Player) r;
                Player damaged = (Player) d;

                for (Team team : teams) {
                    if ((team.hasPlayer(damager.getName())) && team.hasPlayer(damaged.getName())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private final void preventItemLoss() {
        Bukkit.getScheduler().runTaskTimer(ArenaAPI.getPlugin(), new Runnable() {
            public void run() {
                for (Team team : teams) {
                    for (String playerName : team.getPlayers()) {
                        Player player = Bukkit.getPlayer(playerName);
                        if (player != null) {
                            PlayerInventory pi = player.getInventory();
                            if (playerInv.containsKey(playerName)) {
                                playerInv.remove(playerName);
                            }
                            playerInv.put(playerName, pi);
                        }
                    }
                }
            }
        }, 0L, 5);
    }
}
