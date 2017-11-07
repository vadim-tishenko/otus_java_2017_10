package ru.cwl.otus.hw04;

import javax.management.AttributeChangeNotification;
import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;


import static java.util.stream.Collectors.toList;

/**
 * Created by vadim.tishenko
 * on 05.11.2017 20:07.
 * по мотивам ... https://docs.oracle.com/javase/tutorial/jmx/remote/custom.html
 * -Dcom.sun.management.jmxremote.port=9999
 * -Dcom.sun.management.jmxremote.authenticate=false
 * -Dcom.sun.management.jmxremote.ssl=false
 */
public class Main {
    public static void main(String[] args) throws IOException, MalformedObjectNameException, InstanceNotFoundException {

        List<GarbageCollectorMXBean> gcMXBeansList = ManagementFactory.getGarbageCollectorMXBeans();


        ArrayList<Object> list=new ArrayList<>();
        final int SIZE = 1000_00;
        for(int i=0;i<1000;i++){
            add(list, () -> new long[SIZE],1000);
            del(list,997);
            System.out.println(i);
            dumpGC(gcMXBeansList);
        }

    }

    private static void dumpGC(List<GarbageCollectorMXBean> gcList) {
        Map<String, String> map = new HashMap<>();
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

        for (GarbageCollectorMXBean gcb : gcList) {
            String gcType = map.get(gcb.getName());
            System.out.printf(" %s %s %s %s\n", gcType, gcb.getName(), gcb.getCollectionCount(), gcb.getCollectionTime());
        }
    }

    private static void add(ArrayList<Object> list, Supplier<Object> supplier, int i) {
        for (int addCount=0;addCount<i;addCount++) {
            list.add(supplier.get());
        }
    }

    private static void del(ArrayList<Object> list, int i) {
        for (int delCount=0;delCount<i;delCount++) {
            list.remove(0);
        }
    }

    public static class ClientListener implements NotificationListener {
        public void handleNotification(Notification notification,
                                       Object handback) {
            echo("\nReceived notification:");
            echo("\tClassName: " + notification.getClass().getName());
            echo("\tSource: " + notification.getSource());
            echo("\tType: " + notification.getType());
            echo("\tMessage: " + notification.getMessage());
            if (notification instanceof AttributeChangeNotification) {
                AttributeChangeNotification acn =
                        (AttributeChangeNotification) notification;
                echo("\tAttributeName: " + acn.getAttributeName());
                echo("\tAttributeType: " + acn.getAttributeType());
                echo("\tNewValue: " + acn.getNewValue());
                echo("\tOldValue: " + acn.getOldValue());
            }
        }
    }
    private static void echo(String msg) {
        System.out.println(msg);
    }

}
