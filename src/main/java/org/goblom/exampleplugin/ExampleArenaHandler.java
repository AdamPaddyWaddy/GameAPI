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
import org.bukkit.entity.Player;
import org.goblom.gameapi.ArenaHandler;
import org.goblom.gameapi.data.Arena;
import org.goblom.gameapi.data.Team;
import org.goblom.gameapi.data.enums.LocationType;
import org.goblom.gameapi.events.arena.ArenaChangePhase;
import org.goblom.gameapi.events.arena.ArenaCreate;
import org.goblom.gameapi.events.arena.ArenaDelete;
import org.goblom.gameapi.events.arena.TeamAddedToArena;
import org.goblom.gameapi.events.arena.TeamRemovedFromArena;

/**
 *
 * @author Goblom
 */
public class ExampleArenaHandler extends ArenaHandler {

    @Override
    public void start(Arena arena) { 
        for (Team team : arena.getTeams()) {
            for (String playerName : team.getPlayers()) {
                Player player = Bukkit.getPlayer(playerName);
                if (player != null) {
                    player.teleport(team.getSpawnForArena(arena.getName()));
                }
            }
        }
    }

    @Override
    public void end(Arena arena) { 
        for (Team team : arena.getTeams()) {
            for (String playerName : team.getPlayers()) {
                Player player = Bukkit.getPlayer(playerName);
                for (LocationType locType : arena.getLocationNames().keySet()) {
                    if (locType.equals(LocationType.END)) {
                        String locName = arena.getLocationNames().get(locType);
                        if (arena.getLocations().containsKey(locName)) {
                            if (player != null) {
                                player.teleport(arena.getLocations().get(locName));
                            }
                        }
                    }
                }
            }
        }
    }

    public void onArenaPhaseChange(ArenaChangePhase event) { }
    public void onArenaDelete(ArenaDelete event) { }
    public void onArenaCreate(ArenaCreate event) { }
    public void onTeamAddedToArena(TeamAddedToArena event) { }
    public void onTeamRemovedFromArena(TeamRemovedFromArena event) { }
    public void onCreate(Arena arena) { }
    public void onLoad(Arena arena) { }
}
