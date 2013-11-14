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

import java.util.Map;
import org.bukkit.scheduler.BukkitTask;
import org.goblom.arenaapi.ArenaHandler;
import org.goblom.arenaapi.TeamHandler;
import org.goblom.arenaapi.data.Arena;
import org.goblom.arenaapi.data.Team;

/**
 *
 * @author Goblom
 */
public interface CoreAPI {
    public Map<Arena, BukkitTask> getArenaTimers();
    
    public Map<String, Arena> getArenas();
    public Arena getArena(String arenaName);
    public Arena createArena(String arenaName, int maxPlayers);
    public Arena createArena(String arenaName, ArenaHandler handler);
    public Arena createArena(String arenaName, int minPlayers, int maxPlayers);
    public Arena createArena(String arenaName, int minPlayers, int maxPlayers, ArenaHandler handler);
    
    
    public Map<String, Team> getTeams();
    public Team getTeam(String teamName);
    public Team createTeam(String teamName, TeamHandler handler);
}
