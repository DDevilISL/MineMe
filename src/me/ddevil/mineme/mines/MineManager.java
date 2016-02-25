/* 
 * Copyright (C) 2016 Selma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.ddevil.mineme.mines;

import java.util.ArrayList;
import me.ddevil.mineme.MineMe;
import org.bukkit.entity.Player;

/**
 *
 * @author Selma
 */
public class MineManager {

    private static final ArrayList<Mine> mines = new ArrayList();

    public static ArrayList<Mine> getMines() {
        return mines;
    }

    public static void registerMine(Mine m) {
        mines.add(m);
        MineMe.getInstance().debug("Mine " + m.getName() + " registered to manager!");

    }

    public static void unregisterMines() {
        mines.clear();
        MineMe.getInstance().debug("Unloaded all mines!");
    }

    public static Mine getMine(String name) {
        for (Mine mine : mines) {
            if (mine.getName().equalsIgnoreCase(name)) {
                return mine;
            }
        }
        return null;
    }

    public static boolean isPlayerInAMine(Player p) {
        return getMineWith(p) != null;
    }

    public static Mine getMineWith(Player p) {
        for (Mine m : mines) {
            if (m.contains(p)) {
                return m;
            }
        }
        return null;
    }
}
