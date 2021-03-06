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
package org.spongepowered.common.mixin.core.entity.ai;

import net.minecraft.entity.ai.EntityAITarget;
import org.spongepowered.api.entity.ai.task.builtin.creature.target.TargetAITask;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityAITarget.class)
public abstract class MixinEntityAITarget<A extends TargetAITask<A>> extends MixinEntityAIBase implements TargetAITask<A> {

    @Shadow protected boolean shouldCheckSight;
    @Shadow private boolean nearbyOnly;
    @Shadow private int targetSearchStatus;
    @Shadow private int targetSearchDelay;
    @Shadow private int targetUnseenTicks;

    @Override
    public boolean shouldCheckSight() {
        return shouldCheckSight;
    }

    @Override
    public A setCheckSight(boolean checkSight) {
        this.shouldCheckSight = checkSight;
        return (A) this;
    }

    @Override
    public boolean onlyNearby() {
        return nearbyOnly;
    }

    @Override
    public A setOnlyNearby(boolean nearby) {
        this.nearbyOnly = nearby;
        return (A) this;
    }

    @Override
    public int getSearchStatus() {
        return targetSearchStatus;
    }

    @Override
    public A setSearchStatus(int status) {
        this.targetSearchStatus = status;
        return (A) this;
    }

    @Override
    public int getSearchDelay() {
        return targetSearchDelay;
    }

    @Override
    public A setSearchDelay(int delay) {
        this.targetSearchDelay = delay;
        return (A) this;
    }

    @Override
    public int getInterruptIfTargetUnseenTicks() {
        return targetUnseenTicks;
    }

    @Override
    public A setInterruptIfTargetUnseenTicks(int ticks) {
        this.targetUnseenTicks = ticks;
        return (A) this;
    }
}
