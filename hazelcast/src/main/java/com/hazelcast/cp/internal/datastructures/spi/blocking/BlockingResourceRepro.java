package com.hazelcast.cp.internal.datastructures.spi.blocking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockingResourceRepro {

    public static void main(String[] args) throws InterruptedException {
        BlockingResource<WaitKey> blockingResource = new BlockingResource<WaitKey>() {
            @Override
            protected void onSessionClose(long sessionId, Map<Long, Object> responses) {

            }

            @Override
            protected Collection<Long> getActivelyAttachedSessions() {
                return Collections.emptyList();
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        AtomicBoolean abort = new AtomicBoolean(false);

        executorService.submit(() -> {
            while (!abort.get()) {
                for (int i = 0; i < 1000; ++i) {
                    blockingResource.addWaitKey(i, new WaitKey() {
                        @Override
                        public long sessionId() {
                            return 0;
                        }

                        @Override
                        public int getFactoryId() {
                            return 0;
                        }

                        @Override
                        public int getClassId() {
                            return 0;
                        }
                    });
                }
                for (int i = 0; i < 1000; ++i) {
                    blockingResource.removeWaitKey(i);
                }
            }
        });

        executorService.submit(() -> {
            while (!abort.get()) {
                try {
                    blockingResource.collectAttachedSessions(new ArrayList<>());
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });

        Thread.sleep(1000);
        abort.set(true);
        executorService.shutdown();
    }
}
