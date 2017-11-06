package ru.cwl.otus.hw04;

import com.example.Client;
import com.example.HelloMBean;
import javafx.beans.binding.FloatExpression;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;
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

        JMXServiceURL url =
                new JMXServiceURL("service:jmx:rmi:///jndi/rmi://:9999/jmxrmi");
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);




        // Create a dedicated proxy for the MBean instead of
        // going directly through the MBean server connection
        //
        /*HelloMBean mbeanProxy =
                JMX.newMBeanProxy(mbsc, mbeanName, HelloMBean.class, true);*/



        Client.ClientListener listener = new Client.ClientListener();

        // Get an MBeanServerConnection
        //
        echo("\nGet an MBeanServerConnection");
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        echo("\nQuery MBeanServer MBeans:");

        ObjectName qName = null; //new ObjectName("java.lang");

        Set<ObjectName> names =
                new TreeSet<ObjectName>(mbsc.queryNames(qName, null));
        for (ObjectName name : names) {
            echo("\tObjectName = " + name);
        }

        List<ObjectName> l = names.stream().filter(e -> e.toString().contains("GarbageCollector")).collect(toList());
        //ObjectName mbeanName = new ObjectName("java.lang:type=GarbageCollector");


        // Add notification listener on Hello MBean
        //
        echo("\nAdd notification listener...");

        List<GarbageCollectorMXBean> gcList=new ArrayList<>();

        for (ObjectName objectName : l) {

            GarbageCollectorMXBean mbeanProxy = JMX.newMBeanProxy(mbsc, objectName, GarbageCollectorMXBean.class, true);

            gcList.add(mbeanProxy);

            //mbsc.addNotificationListener(objectName, listener, null, null);
        }
        // waitForEnterPressed();


        ArrayList<Object> list=new ArrayList<>();
        final int SIZE = 1000_00;
        for(int i=0;i<1000;i++){
            add(list, () -> new long[SIZE],1000);
            del(list,997);
            System.out.println(i);
            dumpGC(gcList);
        }

    }

    private static void dumpGC(List<GarbageCollectorMXBean> gcList) {
        for (GarbageCollectorMXBean gcb : gcList) {
            System.out.printf(" %s %s %s %s\n", gcb.getObjectName(), gcb.getName(),gcb.getCollectionCount(),gcb.getCollectionTime());
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
