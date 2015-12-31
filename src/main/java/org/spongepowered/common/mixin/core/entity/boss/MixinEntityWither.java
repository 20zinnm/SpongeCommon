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
package org.spongepowered.common.mixin.core.entity.boss;

import static com.google.common.base.Preconditions.checkNotNull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.monster.Wither;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.common.mixin.core.entity.monster.MixinEntityMob;

import java.util.ArrayList;
import java.util.List;

/*
 * Disabled due to mixin bug: https://github.com/SpongePowered/Mixin/issues/87
 */
@Mixin(EntityWither.class)
public abstract class MixinEntityWither extends MixinEntityMob implements Wither {

    @Shadow public abstract int getWatchedTargetId(int p_82203_1_);
    @Shadow public abstract void updateWatchedTargetId(int targetOffset, int newId);

    @Override
    public List<Living> getTargets() {
        List<Living> values = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int id = getWatchedTargetId(i);
            if (id > 0) {
                values.add((Living) this.worldObj.getEntityByID(id));
            }
        }
        return values;
    }

    @Override
    public void setTargets(List<Living> targets) {
        checkNotNull(targets, "Targets is null!");
        for (int i = 0; i < 2; i++) {
            updateWatchedTargetId(i, targets.size() > i ? ((EntityLivingBase) targets.get(i)).getEntityId() : 0);
        }
    }
}
