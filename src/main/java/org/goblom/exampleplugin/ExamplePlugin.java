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

package org.goblom.exampleplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.goblom.arenaapi.CoreAPI;

/**
 *
 * @author Goblom
 */
public class ExamplePlugin {
    
    private CoreAPI arenaAPI;
    
    public void onEnable() {
        arenaAPI = (CoreAPI) Bukkit.getServer().getPluginManager().getPlugin("Arena API");
        initTeam();
        initArena();
        initTeamSpawn();
    }
    
    public CoreAPI getArenaAPI() {
        return arenaAPI;
    }
    
    public void initTeam() {
        getArenaAPI().createTeam("Example Team", new ExampleTeamHandler());
        getArenaAPI().getTeam("Example Team").addPlayer("Goblom");
    }
    public void initArena() {
        getArenaAPI().createArena("Example Arena", new ExampleArenaHandler());
        getArenaAPI().getArena("Example Arena").setMinPlayers(1);
        getArenaAPI().getArena("Example Arena").setMaxPlayers(10);
    }
    public void initTeamSpawn() {
        getArenaAPI().getTeam("Example Team").setSpawnForArena("Example Arena", new Location(Bukkit.getWorld("world"), 0,0,0));
    }
}
