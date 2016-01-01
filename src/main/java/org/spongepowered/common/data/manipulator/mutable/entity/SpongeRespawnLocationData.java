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
package org.spongepowered.common.data.manipulator.mutable.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Maps;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableRespawnLocation;
import org.spongepowered.api.data.manipulator.mutable.entity.RespawnLocationData;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.util.Tuple;
import org.spongepowered.api.world.World;
import org.spongepowered.common.data.manipulator.immutable.entity.ImmutableSpongeRespawnLocation;
import org.spongepowered.common.data.manipulator.mutable.common.collection.AbstractSingleMapData;
import org.spongepowered.common.data.value.mutable.SpongeMapValue;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SpongeRespawnLocationData extends AbstractSingleMapData<UUID, Tuple<Vector3d, Boolean>, RespawnLocationData, ImmutableRespawnLocation>
        implements RespawnLocationData {

    public SpongeRespawnLocationData() {
        this(Maps.newHashMap());
    }

    public SpongeRespawnLocationData(Map<UUID, Tuple<Vector3d, Boolean>> locations) {
        super(RespawnLocationData.class, locations, Keys.RESPAWN_LOCATIONS, ImmutableSpongeRespawnLocation.class);
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer()
            .set(Keys.RESPAWN_LOCATIONS, getValue());
    }

    @Override
    public MapValue<UUID, Tuple<Vector3d, Boolean>> respawnLocation() {
        return new SpongeMapValue<>(Keys.RESPAWN_LOCATIONS, getValue());
    }

    @Override
    public Optional<Tuple<Vector3d, Boolean>> getForWorld(World world) {
        return Optional.ofNullable(getValue().get(checkNotNull(world, "world").getUniqueId()));
    }

    @Override
    public int compareTo(RespawnLocationData o) {
        return 0;
    }

    @Override
    public Optional<Vector3d> getLocation(World world) {
        Optional<Tuple<Vector3d, Boolean>> tuple = getForWorld(world);
        return tuple.isPresent() ? Optional.of(tuple.get().getFirst()) : Optional.empty();
    }

    @Override
    public Optional<Boolean> isSpawnForced(World world) {
        Optional<Tuple<Vector3d, Boolean>> tuple = getForWorld(world);
        return tuple.isPresent() ? Optional.of(tuple.get().getSecond()) : Optional.empty();
    }

}
