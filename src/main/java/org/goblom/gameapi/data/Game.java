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

package org.goblom.gameapi.data;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.goblom.gameapi.GameHandler;
import org.goblom.gameapi.Main;
import org.goblom.gameapi.data.enums.SignData;

/**
 *
 * @author Goblom
 */
public class Game {
    
    private final String gameName;
    private final GameHandler handler;
    private final String[] teams;
    private final boolean autoStart;
    private final int autoCountdown;
    
    private final List<String> players = new ArrayList<String>();
    private boolean hasStarted = false;
    
    public Game(String gameName, GameHandler handler, boolean autoStart) {
        this.gameName = gameName;
        this.handler = handler;
        this.teams = handler.setTeams();
        
        this.autoStart = autoStart;
        this.autoCountdown = (handler.autoStartCountdown() * 20);
    }
    
    public String getName() {
        return gameName;
    }
    
    public GameHandler getHandler() {
        return handler;
    }
    
    public int getCountdown(boolean inTicks) {
        if (inTicks) return autoCountdown;
        return (autoCountdown / 20);
    }
    
    public void start() {
        if (!hasStarted) {
            //CraftEvent
            handler.start(this);
            this.hasStarted = true;
        }
    }
    
    public void autoStart() {
        String message = handler.autoStartMessage().replaceAll("%time%", "" + handler.autoStartCountdown());
        for (String playerName : players) {
            Player player = Bukkit.getPlayer(playerName);
            if (player != null) {
                player.sendMessage(message);
            }
        }
        Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
            public void run() {
                if (!hasStarted) start();
            }
        }, autoCountdown);
    }
    
    public void end() {
        if (hasStarted) {
            //CraftEvent
            handler.end(this);
        }
    }
    
    public String[] getTeams() {
//        this.teams = handler.setTeams(); //Old Method of getting teams. Might return in future.
        return teams;
    }
    
    public List<String> getPlayers() {
        return players;
    }
    
    public void addPlayer(String playerName) {
        if (players.size() < handler.maxPlayers()) {
            players.add(playerName);
            if (players.size() == handler.minPlayers()) {
                if (autoStart) autoStart();
            }
        } else if (players.size() == handler.maxPlayers()) {
            start();
        }
    }
    
    public void remPlayer(String playerName) {
        if (players.contains(playerName)) {
            players.remove(playerName);
        }
    }
    
    public String[] getSign() {
        String line1 = "";
        String line2 = "";
        String line3 = "";
        String line4 = "";
        
        if (handler.sign.containsKey(SignData.Line.LINE_1)) line1 = handler.sign.get(SignData.Line.LINE_1);
        if (handler.sign.containsKey(SignData.Line.LINE_2)) line2 = handler.sign.get(SignData.Line.LINE_2);
        if (handler.sign.containsKey(SignData.Line.LINE_3)) line3 = handler.sign.get(SignData.Line.LINE_3);
        if (handler.sign.containsKey(SignData.Line.LINE_4)) line4 = handler.sign.get(SignData.Line.LINE_4);
        
        return new String[] { line1, line2, line3, line4 };
    }
}
