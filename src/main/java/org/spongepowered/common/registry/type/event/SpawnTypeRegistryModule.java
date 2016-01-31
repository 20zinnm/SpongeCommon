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
package org.spongepowered.common.registry.type.event;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.collect.ImmutableSet;
import org.spongepowered.api.event.cause.entity.spawn.SpawnType;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.common.data.type.SpongeSpawnType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SpawnTypeRegistryModule implements AdditionalCatalogRegistryModule<SpawnType> {

    @RegisterCatalog(SpawnTypes.class)
    private final Map<String, SpawnType> spawnTypeMap = new HashMap<>();

    @Override
    public void registerAdditionalCatalog(SpawnType extraCatalog) {
        checkArgument(!this.spawnTypeMap.containsKey(extraCatalog.getId().toLowerCase()),
                "SpawnType with the same id is already registered: {}", extraCatalog.getId());
        this.spawnTypeMap.put(extraCatalog.getId().toLowerCase(), extraCatalog);
    }

    @Override
    public Optional<SpawnType> getById(String id) {
        return Optional.ofNullable(this.spawnTypeMap.get(checkNotNull(id, "Id cannot be null!").toLowerCase()));
    }

    @Override
    public Collection<SpawnType> getAll() {
        return ImmutableSet.copyOf(this.spawnTypeMap.values());
    }

    @Override
    public void registerDefaults() {
        this.spawnTypeMap.put("block_spawning", new SpongeSpawnType("BlockSpawning"));
        this.spawnTypeMap.put("breeding", new SpongeSpawnType("Breeding"));
        this.spawnTypeMap.put("dispense", new SpongeSpawnType("Dispense"));
        this.spawnTypeMap.put("dropped_item",new SpongeSpawnType("DroppedItem"));
        this.spawnTypeMap.put("experience", new SpongeSpawnType("Experience"));
        this.spawnTypeMap.put("falling_block", new SpongeSpawnType("FallingBlock"));
        this.spawnTypeMap.put("mob_spawner", new SpongeSpawnType("MobSpawner"));
        this.spawnTypeMap.put("passive", new SpongeSpawnType("Passive"));
        this.spawnTypeMap.put("placement", new SpongeSpawnType("Placement"));
        this.spawnTypeMap.put("projectile", new SpongeSpawnType("Projectile"));
        this.spawnTypeMap.put("spawn_egg", new SpongeSpawnType("SpawnEgg"));
        this.spawnTypeMap.put("structure", new SpongeSpawnType("Structure"));
        this.spawnTypeMap.put("tnt_ignite", new SpongeSpawnType("TNT"));
        this.spawnTypeMap.put("weather", new SpongeSpawnType("Weather"));
        this.spawnTypeMap.put("custom", new SpongeSpawnType("Custom"));
        this.spawnTypeMap.put("chunk_load", new SpongeSpawnType("ChunkLoad"));
        this.spawnTypeMap.put("world_spawner", new SpongeSpawnType("WorldSpawner"));
        this.spawnTypeMap.put("plugin", new SpongeSpawnType("Plugin"));
    }
}
