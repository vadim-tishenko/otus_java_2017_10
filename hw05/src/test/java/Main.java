import ru.cwl.otus.hw05.TestClass;
import ru.cwl.otus.hw05.annotation.After;
import ru.cwl.otus.hw05.annotation.Before;
import ru.cwl.otus.hw05.annotation.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 11.11.2017 18:46.
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<TestClass> c = TestClass.class;
        runTest(c);

    }

    static void runTest(Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        System.out.println("test class: "+c.getCanonicalName());
        Method beforeTest=null;
        Method afterTest=null;
        List<Method> tests=new ArrayList<>();
        for (Method method : c.getDeclaredMethods()) {
            if(method.getAnnotation(Test.class)!=null){
                tests.add(method);
                continue;
            }
            if(method.getAnnotation(Before.class)!=null){
                beforeTest=method;
                continue;
            }
            if(method.getAnnotation(After.class)!=null){
                afterTest=method;
                continue;
            }
        }

        Object obj = c.newInstance();
        for (Method test : tests) {
            if(beforeTest!=null){
                beforeTest.invoke(obj);
            }
            test.invoke(obj);
            if(afterTest!=null){
                afterTest.invoke(obj);
            }
        }
    }
}
