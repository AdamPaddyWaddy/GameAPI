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
import org.bukkit.Location;

/**
 *
 * @author Goblom
 */
public class Team {

    private String teamName;
    private boolean friendlyFire = false;

    private List<String> players = new ArrayList<String>();

    private List<Arena> arenasForTeam = new ArrayList<Arena>();
    private Map<Arena, Location> arenaSpawn = new HashMap();

    public Team(String teamName) {
        this(teamName, null, null);
    }
    
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
