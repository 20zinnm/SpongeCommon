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
package org.spongepowered.common.data.manipulator.immutable.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.immutable.entity.ImmutableRespawnLocation;
import org.spongepowered.api.data.manipulator.mutable.entity.RespawnLocationData;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.util.Tuple;
import org.spongepowered.api.world.World;
import org.spongepowered.common.data.manipulator.immutable.common.collection.AbstractImmutableSingleMapData;
import org.spongepowered.common.data.manipulator.mutable.entity.SpongeRespawnLocationData;
import org.spongepowered.common.data.value.immutable.ImmutableSpongeMapValue;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ImmutableSpongeRespawnLocation extends AbstractImmutableSingleMapData<UUID, Tuple<Vector3d, Boolean>, ImmutableRespawnLocation, RespawnLocationData>
        implements ImmutableRespawnLocation {

    private final ImmutableMapValue<UUID, Tuple<Vector3d, Boolean>> locations;

    public ImmutableSpongeRespawnLocation(Map<UUID, Tuple<Vector3d, Boolean>> locations) {
        super(ImmutableRespawnLocation.class, locations, Keys.RESPAWN_LOCATIONS, SpongeRespawnLocationData.class);
        this.locations = new ImmutableSpongeMapValue<>(Keys.RESPAWN_LOCATIONS, locations);
    }

    @Override
    public ImmutableMapValue<UUID, Tuple<Vector3d, Boolean>> respawnLocation() {
        return this.locations;
    }

    @Override
    public Optional<Tuple<Vector3d, Boolean>> getForWorld(World world) {
        return Optional.ofNullable(this.getValue().get(checkNotNull(world, "world").getUniqueId()));
    }

}
