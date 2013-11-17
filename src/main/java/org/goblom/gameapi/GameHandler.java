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

package org.goblom.gameapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.goblom.gameapi.data.Game;
import org.goblom.gameapi.data.GameHandlerInterface;
import org.goblom.gameapi.data.enums.SignData;

/**
 *
 * @author Goblom
 */
public abstract class GameHandler implements GameHandlerInterface, Listener {
    
    public List<String> players = new ArrayList<String>(); //Need a way to also account for what team they will be
    public Map<SignData.Line, String> sign = new HashMap();
    
    public GameHandler() {
        Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
    }
    
    public abstract int minPlayers();
    public abstract int maxPlayers();
    
    public abstract String[] setTeams();
    
    public boolean addPlayer(String playerName) {
        return players.add(playerName);
    }
    
    public boolean remPlayer(String playerName) {
        return players.remove(playerName);
    }
    
    public void setSign(SignData.Line line, String data) {
        sign.put(line, data);
    }
    
    public abstract void start(Game game);
    public abstract void end(Game game);
}
