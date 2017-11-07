package ru.cwl.otus.hw04;

import javax.management.*;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

/**
 * Created by vadim.tishenko
 * on 05.11.2017 20:07.
 */
public class Main {
    public static void main(String[] args) throws IOException, MalformedObjectNameException, InstanceNotFoundException {
        List<GarbageCollectorMXBean> gcMXBeansList = ManagementFactory.getGarbageCollectorMXBeans();

        GCProbe gcProbe = new GCProbe(gcMXBeansList);
        AtomicLong atomicLong = new AtomicLong();

        Thread daemon = new Thread(() -> {
            long oldCount = 0;
            long youngCount = 0;
            long oldTime = 0;
            long youngTime = 0;
            final int periodSeconds = 10;

            System.out.println("Garbage Collection test.");

            System.out.println("young: "+gcProbe.young.getName() );
            System.out.println("  old: "+gcProbe.old.getName() );
            System.out.println("probe period "+periodSeconds+" sec.");
            System.out.println("__iter_  __________yang_________ __________old__________  gc util");
            System.out.println("_count_  ___count___ __time_ms__ ___count___ __time_ms__  __%%__");
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(periodSeconds);
                    long oc = gcProbe.getOldCount();
                    long yc = gcProbe.getYuongCount();
                    long ot = gcProbe.getOldTime();
                    long yt = gcProbe.getYuongTime();
                    //echo(Thread.currentThread().getName());
                    double t = ((yt - youngTime) + (ot - oldTime))/1000.0*100.0 / periodSeconds;
                    System.out.printf("%7s  %11s %11s %11s %11s  %.3f\n",
                            atomicLong.get(), yc - youngCount, yt - youngTime,
                            oc - oldCount, ot - oldTime,t);
                    youngCount = yc;
                    youngTime = yt;
                    oldCount = oc;
                    oldTime = ot;
                }
            } catch (InterruptedException e) {
                System.out.println("sleep() interrupted");
            }
        });
        daemon.setDaemon(true);
        daemon.start();

        ArrayList<Object> list = new ArrayList<>();
        final int SIZE = 1000_00;
        for (int i = 0; i < 1000; i++) {
            add(list, () -> new long[SIZE], 1000);
            del(list, 997);
            atomicLong.incrementAndGet();
        }

    }

    private static void add(ArrayList<Object> list, Supplier<Object> supplier, int i) {
        for (int addCount = 0; addCount < i; addCount++) {
            list.add(supplier.get());
        }
    }

    private static void del(ArrayList<Object> list, int i) {
        for (int delCount = 0; delCount < i; delCount++) {
            list.remove(0);
        }
    }

    private static class GCProbe {
        List<GarbageCollectorMXBean> gcMXBeansList;
        Map<String, String> map = new HashMap<>();
        GarbageCollectorMXBean old;
        GarbageCollectorMXBean young;

        public GCProbe(List<GarbageCollectorMXBean> gcMXBeansList) {
            this.gcMXBeansList = gcMXBeansList;
            //• Young generation GC
            map.put("Copy", "Young");
            map.put("PS Scavenge", "Young");
            map.put("ParNew", "Young");
            map.put("G1 Young Generation", "Young");
            //• Old generation GC
            map.put("MarkSweepCompact", "Old");
            map.put("PS MarkSweep", "Old");
            map.put("ConcurrentMarkSweep", "Old");
            map.put("G1 Mixed Generation", "Old");
            map.put("G1 Old Generation", "Old");


            for (GarbageCollectorMXBean gcb : gcMXBeansList) {
                String name = gcb.getName();
                String type = map.get(name);
                if("Young".equals(type)){
                    young=gcb;
                }
                if("Old".equals(type)){
                    old=gcb;
                }
            }
        }

        long getOldCount() {
            return old.getCollectionCount();
        }

        long getYuongCount() {
            return young.getCollectionCount();
        }

        long getOldTime() {
            return old.getCollectionTime();
        }

        long getYuongTime() {
            return young.getCollectionTime();
        }
        String getOldName() {
            return old.getName();
        }

        String  getYuongName() {
            return young.getName();
        }


    }
}
