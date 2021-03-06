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

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Selma
 */
public class MineUtils {

    public static ItemStack getItemStackInComposition(Mine m, ItemStack i) {
        for (ItemStack a
                : m.getMaterials()) {
            if (a.getType() == i.getType()) {
                if (a.getData().getData() == i.getData().getData()) {
                    return a;
                }
            }
        }
        return null;
    }

    public static boolean containsRelativeItemStackInComposition(Mine m, ItemStack i) {
        return getItemStackInComposition(m, i) != null;
    }
}
