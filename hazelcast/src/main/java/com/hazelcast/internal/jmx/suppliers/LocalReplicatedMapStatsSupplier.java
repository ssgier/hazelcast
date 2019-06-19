/*
 * Copyright (c) 2008-2019, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.internal.jmx.suppliers;

import com.hazelcast.replicatedmap.ReplicatedMap;
import com.hazelcast.monitor.LocalReplicatedMapStats;
import com.hazelcast.monitor.impl.EmptyLocalReplicatedMapStats;

/**
 * Implementation of {@link StatsSupplier} for {@link LocalReplicatedMapStats}
 */
public class LocalReplicatedMapStatsSupplier
        implements StatsSupplier<LocalReplicatedMapStats> {

    private final ReplicatedMap<?, ?> map;

    public LocalReplicatedMapStatsSupplier(ReplicatedMap<?, ?> map) {
        this.map = map;
    }

    @Override
    public LocalReplicatedMapStats getEmpty() {
        return new EmptyLocalReplicatedMapStats();
    }

    @Override
    public LocalReplicatedMapStats get() {
        return map.getReplicatedMapStats();
    }
}
