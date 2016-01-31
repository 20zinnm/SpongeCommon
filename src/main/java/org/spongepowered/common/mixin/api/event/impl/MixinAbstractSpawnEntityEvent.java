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
package org.spongepowered.common.mixin.api.event.impl;

import static com.google.common.base.Preconditions.checkArgument;

import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.event.impl.AbstractSpawnEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * This needs to be overwritten because the classloaders don't like
 * sharing the same class and instance of checks don't work between
 * the two classloaders. So, this will do it within the Launch
 * classloader where it should be (as all static accessors of Sponge
 * need to be classloaded in the Launch classloader regardless).
 */
@Mixin(AbstractSpawnEntityEvent.class)
public abstract class MixinAbstractSpawnEntityEvent extends AbstractEvent{

    @Overwrite
    @Override
    public void init() {
        Cause cause = getCause();
        Object root = cause.root();
        checkArgument(root instanceof SpawnCause, "SpawnEntityEvent MUST have a SpawnCause as the root!");
    }

}
