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
package me.ddevil.mineme.mines.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.ddevil.mineme.MineMeMessageManager;
import me.ddevil.mineme.MineMe;
import me.ddevil.mineme.mines.Mine;
import me.ddevil.mineme.mines.configs.MineConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public abstract class BasicMine implements Mine {

    protected final ArrayList<Block> brokenBlocks = new ArrayList();
    protected boolean broadcastOnReset;
    protected String broadcastMessage;
    protected double broadcastRadius;
    protected boolean broadcastNearby;
    protected final String name;
    protected final String alias;

    protected int currentResetDelay;
    protected int totalResetDelay;

    public BasicMine(MineConfig config) {
        this.broadcastMessage = MineMe.forceDefaultBroadcastMessage
                ? MineMeMessageManager.globalResetMessage
                : config.isUseCustomBroadcast() ? config.getBroadcastMessage() : MineMeMessageManager.globalResetMessage;
        this.broadcastOnReset = config.isBroadcastOnReset();
        this.broadcastRadius = config.getBroadcastRadius();
        this.broadcastNearby = config.isNearbyBroadcast();
        this.currentResetDelay = totalResetDelay;
        this.totalResetDelay = config.getResetDelay();
        this.name = config.getName();
        this.alias = config.getAlias();
    }

    public BasicMine(String name, boolean broadcastOnReset, boolean nearbyBroadcast, String broadcastMessage, double broadcastRadius, int resetMinutesDelay) {
        this.broadcastOnReset = broadcastOnReset;
        this.broadcastNearby = nearbyBroadcast;
        this.broadcastRadius = broadcastRadius;
        this.totalResetDelay = resetMinutesDelay;
        this.currentResetDelay = totalResetDelay;
        this.broadcastMessage = broadcastMessage;
        this.name = name;
        this.alias = name;
    }

    public BasicMine(String name, boolean broadcastOnReset, boolean nearbyBroadcast, double broadcastRadius, int resetMinutesDelay) {
        this.broadcastOnReset = broadcastOnReset;
        this.broadcastNearby = nearbyBroadcast;
        this.broadcastRadius = broadcastRadius;
        this.totalResetDelay = resetMinutesDelay;
        this.currentResetDelay = totalResetDelay;
        broadcastMessage = MineMe.messagesConfig.getString("messages.resetMessage");
        this.name = name;
        this.alias = name;
    }

    public BasicMine(String name, String alias, boolean broadcastOnReset, boolean nearbyBroadcast, String broadcastMessage, double broadcastRadius, int resetMinutesDelay) {
        this.broadcastOnReset = broadcastOnReset;
        this.broadcastNearby = nearbyBroadcast;
        this.broadcastRadius = broadcastRadius;
        this.totalResetDelay = resetMinutesDelay;
        this.currentResetDelay = totalResetDelay;
        this.broadcastMessage = broadcastMessage;
        this.name = name;
        this.alias = alias;
    }

    public BasicMine(String name, String alias, boolean broadcastOnReset, boolean nearbyBroadcast, double broadcastRadius, int resetMinutesDelay) {
        this.broadcastOnReset = broadcastOnReset;
        this.broadcastNearby = nearbyBroadcast;
        this.broadcastRadius = broadcastRadius;
        this.totalResetDelay = resetMinutesDelay;
        this.currentResetDelay = totalResetDelay;
        broadcastMessage = MineMe.messagesConfig.getString("messages.resetMessage");
        this.name = name;
        this.alias = alias;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getResetMinutesDelay() {
        return totalResetDelay;
    }

    public void setResetMinutesDelay(int resetMinutesDelay) {
        this.totalResetDelay = resetMinutesDelay;
    }

    @Override
    public boolean broadcastOnReset() {
        return broadcastOnReset;
    }

    @Override
    public boolean broadcastToNearbyOnly() {
        return broadcastNearby;
    }

    @Override
    public double broadcastRadius() {
        return broadcastRadius;
    }

    @Override
    public boolean isBroadcastOnReset() {
        return broadcastOnReset;
    }

    @Override
    public void setBroadcastOnReset(boolean broadcastOnReset) {
        this.broadcastOnReset = broadcastOnReset;
    }

    public void setResetDelay(int resetDelay) {
        this.currentResetDelay = resetDelay;
    }

    public void setNearbyBroadcast(boolean nearbyBroadcast) {
        this.broadcastNearby = nearbyBroadcast;
    }

    public void setBroadcastRadius(double broadcastRadius) {
        this.broadcastRadius = broadcastRadius;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public double getBroadcastRadius() {
        return broadcastRadius;
    }

    public String getBroadcastMessage() {
        return broadcastMessage;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void tictoc() {
        currentResetDelay--;
        if (currentResetDelay <= 0) {
            reset();
        }
    }

    @Override
    public List<String> getInfo() {
        String[] basic = new String[]{
            "$1Mine: $2" + getName(),
            "$1World: $2" + getLocation().getWorld().getName(),
            "$1Location: $2" + getLocation().getBlockX() + ", " + getLocation().getBlockY() + ", " + getLocation().getBlockZ() + ", ",
            "$1Broadcast on reset: $2" + broadcastOnReset(),
            "$1Nearby broadcast: $2" + broadcastToNearbyOnly(),
            "$1Broadcast radius: $2" + broadcastRadius(),
            "$1Composition: $2"
        };
        ArrayList<String> comp = new ArrayList();

        comp.addAll(Arrays.asList(basic));
        for (Material ma
                : getMaterials()) {
            comp.add("$1" + ma.name() + " $2= $1" + getComposition().get(ma));
        }
        return comp;
    }

    @Override
    public boolean wasAlreadyBroken(Block b) {
        return brokenBlocks.contains(b);
    }

    @Override
    public double getPercentageMined() {
        return Double.valueOf(
                new DecimalFormat("###.#").format(
                        (getVolume() * 100) / brokenBlocks.size()
                ));
    }

    @Override
    public int getMinedBlocks() {
        return getVolume() - getRemainingBlocks();
    }

    @Override
    public int getRemainingBlocks() {
        return getVolume() - brokenBlocks.size();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Block b = e.getBlock();
        if (contains(b)) {
            if (!wasAlreadyBroken(b)) {
                brokenBlocks.add(b);
            }
        }
    }
}
