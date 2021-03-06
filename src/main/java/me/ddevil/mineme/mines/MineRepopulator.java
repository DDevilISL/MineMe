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

import me.ddevil.mineme.MineMe;
import me.ddevil.mineme.utils.RandomCollection;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class MineRepopulator {
    
    public void repopulate(Mine m) {
        try {
            RepopulateMap map = new RepopulateMap(m);
            if (map.isEmpty()) {
                return;
            }
            for (Block b : m) {
                ItemStack bb = map.getRandomBlock();
                b.setType(bb.getType());
                b.setData(bb.getData().getData());
            }
        } catch (Exception e) {
            MineMe.instance.printException("There was an error repopulating mine " + m.getName() + ", is the composition correct?", e);
        }
    }
    
    private static class RepopulateMap {
        
        private final RandomCollection<ItemStack> randomCollection;
        
        private RepopulateMap(Mine mine) {
            randomCollection = new RandomCollection<>();
            for (ItemStack m : mine.getComposition().keySet()) {
                randomCollection.add(
                        mine.getPercentage(m),
                        m);
            }
            
        }
        
        private boolean isEmpty() {
            return randomCollection.isEmpty();
        }
        
        private ItemStack getRandomBlock() {
            return randomCollection.next();
        }
    }
}
