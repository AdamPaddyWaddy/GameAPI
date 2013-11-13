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
package org.goblom.arenaapi;

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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import org.goblom.arenaapi.ArenaManager.*; //Import Myself ?
import org.goblom.arenaapi.ArenaManager.ArenaData.*; //Import Myself?

/**
 * Arena Manager v0.1
 *
 * Easily Create, Manager, Edit & Delete Arenas for any game that you have in
 * mind. Arena Manager comes with Teams, Inventory saving/deleting/restoring and
 * custom equipment for teams.
 *
 * @TODO - Implement Min/Max Players
 * @TODO - Location Types
 * 
 * @author Goblom
 */
public class ArenaManager {

    private Plugin plugin;
    
    private Map<String, Arena> arenas = new HashMap();
    private Map<String, Team> teams = new HashMap();
    
    public ArenaManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public Map<String, Arena> getArenas() {
        return arenas;
    }

    public Arena getArena(String arenaName) {
        return arenas.get(arenaName);
    }
    
    public Arena getArena(Arena arena) {
        return arenas.get(arena.getName());
    }

    public Arena createArena(String arenaName) {
        return arenas.put(arenaName, new Arena(arenaName));
    }
    
    public Arena createArena(String arenaName, int maxPlayers) {
        return arenas.put(arenaName, new Arena(arenaName, maxPlayers));
    }
    
    public Arena createArena(String arenaName, int minPlayers, int maxPlayers) {
        return arenas.put(arenaName, new Arena(arenaName, minPlayers, maxPlayers));
    }
    
    public Arena createArena(String arenaName, ArenaHandler handler) {
        return arenas.put(arenaName, new Arena(arenaName, handler));
    }

    public Arena createArena(String arenaName, int minPlayers, int maxPlayers, ArenaHandler handler) {
        return arenas.put(arenaName, new Arena(arenaName, minPlayers, maxPlayers, handler));
    }

    public Map<String, Team> getTeams() {
        return teams;
    }
    
    public Team createTeam(String teamName, Arena arena, Location spawnLocation) {
        return teams.put(teamName, new Team(teamName, arena, spawnLocation));
    }
    
    public class Team {

        private String teamName;
        private boolean friendlyFire = false;

        private List<String> players = new ArrayList<String>();

        private List<Arena> arenasForTeam = new ArrayList<Arena>();
        private Map<Arena, Location> arenaSpawn = new HashMap();

        public Team(String teamName, Arena arena, Location arenaSpawn) {
            this.arenasForTeam.add(arena);
            this.teamName = teamName;
            this.arenaSpawn.put(arena, arenaSpawn);
        }

        public void remove() {
            this.arenaSpawn = null;
            this.teamName = null;
            this.arenasForTeam = null;
            this.players = null;
        }

        public String getName() {
            return teamName;
        }

        public boolean getFriendlyFire() {
            return friendlyFire;
        }

        public List<String> getPlayers() {
            return players;
        }

        public List<Arena> getArenasForTeam() {
            return arenasForTeam;
        }

        public Map<Arena, Location> getArenaSpawnsForTeam() {
            return arenaSpawn;
        }

        public Location getSpawnForArena(Arena arena) {
            return arenaSpawn.get(arena);
        }

        public void setFriendlyFire(boolean friendlyFire) {
            this.friendlyFire = friendlyFire;
        }

        public boolean setSpawnForArena(Arena arena, Location loc) {
            if (!arenaSpawn.containsKey(arena)) {
                arenaSpawn.put(arena, loc);
                return true;
            }
            return false;
        }

        public boolean remSpawnForArena(Arena arena) {
            if (arenaSpawn.containsKey(arena)) {
                arenaSpawn.remove(arena);
                return true;
            }
            return false;
        }

        public void addPlayers(List<String> players) {
            this.players = players;
        }

        public boolean addPlayer(String playerName) {
            if (!players.contains(playerName)) {
                players.add(playerName);
                return true;
            }
            return false;
        }

        public boolean hasPlayer(String playerName) {
            return players.contains(playerName);
        }

        public boolean remPlayer(String playerName) {
            if (players.contains(playerName)) {
                players.remove(playerName);
                return true;
            }
            return false;
        }

        public boolean addArena(Arena arena) {
            if (!arenasForTeam.contains(arena)) {
                arenasForTeam.add(arena);
                return true;
            } else {
                return false;
            }
        }

        public boolean remArena(Arena arena) {
            if (arenasForTeam.contains(arena)) {
                arenasForTeam.remove(arena);
                return true;
            }
            return false;
        }
    }
}
