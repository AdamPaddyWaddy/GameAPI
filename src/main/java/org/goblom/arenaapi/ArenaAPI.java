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

import org.goblom.arenaapi.data.Team;
import org.goblom.arenaapi.data.Arena;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.goblom.arenaapi.data.ArenaAPIInterface;

/**
 * Arena Manager
 *
 * Easily Create, Manager, Edit & Delete Arenas for any game that you have in
 * mind. Arena Manager comes with Teams, Inventory saving/deleting/restoring and
 * custom equipment for teams.
 * 
 * @TODO - JavaDocs
 * @TODO - Implement Events (I think they are all done)
 * 
 * @version 1.0
 * @author Goblom
 */
public class ArenaAPI extends JavaPlugin implements ArenaAPIInterface {

    private static ArenaAPI plugin;

    private static Map<String, Arena> arenas = new HashMap();
    private static Map<String, Team> teams = new HashMap();

    private static Map<Arena, BukkitTask> arenaTimers = new HashMap();

    public void onEnable() {
        this.plugin = this;
    }

    public static ArenaAPI getPlugin() {
        return plugin;
    }

    @Override
    public Map<Arena, BukkitTask> getArenaTimers() {
        return arenaTimers;
    }

    @Override
    public Map<String, Arena> getArenas() {
        return arenas;
    }
    
    @Override
    public Map<String, Team> getTeams() {
        return teams;
    }
    
    @Override
    public Arena getArena(String arenaName) {
        return arenas.get(arenaName);
    }

    @Override
    public Team getTeam(String teamName) {
        return teams.get(teamName);
    }

    @Override
    public Arena createArena(String arenaName, int maxPlayers) {
        return arenas.put(arenaName, new Arena(arenaName, maxPlayers));
    }

    @Override
    public Arena createArena(String arenaName, int minPlayers, int maxPlayers) {
        return arenas.put(arenaName, new Arena(arenaName, minPlayers, maxPlayers));
    }

    @Override
    public Arena createArena(String arenaName, ArenaHandler handler) {
        return arenas.put(arenaName, new Arena(arenaName, handler));
    }

    @Override
    public Arena createArena(String arenaName, int minPlayers, int maxPlayers, ArenaHandler handler) {
        return arenas.put(arenaName, new Arena(arenaName, minPlayers, maxPlayers, handler));
    }

    @Override
    public Team createTeam(String teamName, TeamHandler handler) {
        return teams.put(teamName, new Team(teamName, handler));
    }
}
