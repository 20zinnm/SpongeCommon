/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
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
package org.spongepowered.common.mixin.core.util;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.common.interfaces.IMixinWorld;
import org.spongepowered.common.util.StaticMixinHelper;
import org.spongepowered.common.world.CauseTracker;

@Mixin(targets = "net/minecraft/network/PacketThreadUtil$1")
public class MixinPacketThreadUtil {

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V") )
    public void onProcessPacket(Packet packetIn, INetHandler netHandler) {
        if (netHandler instanceof NetHandlerPlayServer) {
            StaticMixinHelper.processingPacket = packetIn;
            StaticMixinHelper.packetPlayer = ((NetHandlerPlayServer) netHandler).playerEntity;

            // This is another horrible hack required since the client sends a C10 packet for every slot
            // containing an itemstack after a C16 packet in the following scenarios :
            // 1. Opening creative inventory after initial server join.
            // 2. Opening creative inventory again after making a change in previous inventory open.
            //
            // This is done in order to sync client inventory to server and would be fine if the C10 packet
            // included an Enum of some sort that defined what type of sync was happening.
            if (StaticMixinHelper.packetPlayer.theItemInWorldManager.isCreative() && (packetIn instanceof C16PacketClientStatus
                    && ((C16PacketClientStatus) packetIn).getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT)) {
                StaticMixinHelper.lastInventoryOpenPacketTimeStamp = System.currentTimeMillis();
            } else if (packetIn instanceof C10PacketCreativeInventoryAction) {
                long packetDiff = System.currentTimeMillis() - StaticMixinHelper.lastInventoryOpenPacketTimeStamp;
                // If the time between packets is small enough, mark the current packet to be ignored for our event handler.
                if (packetDiff < 100) {
                    StaticMixinHelper.ignoreCreativeInventoryPacket = true;
                }
            }

            StaticMixinHelper.lastOpenContainer = StaticMixinHelper.packetPlayer.openContainer;
            ItemStackSnapshot cursor = StaticMixinHelper.packetPlayer.inventory.getItemStack() == null ? ItemStackSnapshot.NONE
                    : ((org.spongepowered.api.item.inventory.ItemStack) StaticMixinHelper.packetPlayer.inventory.getItemStack()).createSnapshot();
            StaticMixinHelper.lastCursor = cursor;

            CauseTracker tracker = ((IMixinWorld) StaticMixinHelper.packetPlayer.worldObj).getCauseTracker();
            if (StaticMixinHelper.packetPlayer.getHeldItem() != null
                    && (packetIn instanceof C07PacketPlayerDigging || packetIn instanceof C08PacketPlayerBlockPlacement)) {
                StaticMixinHelper.lastPlayerItem = ItemStack.copyItemStack(StaticMixinHelper.packetPlayer.getHeldItem());
            }

            tracker.setProcessingCaptureCause(true);
            packetIn.processPacket(netHandler);
            tracker.handlePostTickCaptures(Cause.of(NamedCause.source(StaticMixinHelper.packetPlayer)));
            tracker.setProcessingCaptureCause(false);
            StaticMixinHelper.packetPlayer = null;
            StaticMixinHelper.processingPacket = null;
            StaticMixinHelper.lastCursor = null;
            StaticMixinHelper.lastOpenContainer = null;
            StaticMixinHelper.ignoreCreativeInventoryPacket = false;
        } else { // client
            packetIn.processPacket(netHandler);
        }
    }

}
