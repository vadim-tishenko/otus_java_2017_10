package ru.cwl.otus.hw08;

import org.junit.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by tischenko on 13.12.2017 13:51.
 */
public class JsonSerializerTest {

    private final JsonSerializer jsonSer = new JsonSerializer();
    private final Jsonb jsonb = JsonbBuilder.create();

    @Test
    public void testSerializePrimitives() {

        Primitives p = new Primitives();
        p.d = 10.0;
        p.f = 100.0f;
        p.i = 10;
        p.l = 100;

        String result = jsonSer.toJson(p);


        Primitives p0 = jsonb.fromJson(result, Primitives.class);
        assertThat(p0.d, equalTo(10.0));
        assertThat(p0.f, equalTo(100.0f));
        assertThat(p0.i, equalTo(10));
        assertThat(p0.l, equalTo(100L));
        assertThat(p0.boolFalse, equalTo(false));
        assertThat(p0.boolTrue, equalTo(true));
        assertThat(p0.nullValue, equalTo(null));

        //assertThat(p0, samePropertyValuesAs(p));

    }

    @Test
    public void testPrimitiveWrappers() {
        WrPrimitives p = new WrPrimitives();
        p.d = 10.0;
        p.f = 100.0f;
        p.i = 10;
        p.l = 100L;


        String result = jsonSer.toJson(p);


        WrPrimitives p0 = jsonb.fromJson(result, WrPrimitives.class);
        assertThat(p0.d, equalTo(10.0));
        assertThat(p0.f, equalTo(100.0f));
        assertThat(p0.i, equalTo(10));
        assertThat(p0.l, equalTo(100L));
        assertThat(p0.boolFalse, equalTo(false));
        assertThat(p0.boolTrue, equalTo(true));
        assertThat(p0.nullValue, equalTo(null));
        assertThat(p0.str, equalTo("The String"));
    }

    @Test
    public void testArrayOfPrimitives() {
        Primitives p = new Primitives();
        String result = jsonSer.toJson(p);
        WrPrimitives p0 = jsonb.fromJson(result, WrPrimitives.class);
        assertThat(p0.intArray, not(equalTo(null)));
        assertThat(p0.intArray, arrayWithSize(6));
        assertThat(p0.intArray, arrayContaining(0, 1, 2, 3, 4, 5));
    }

    @Test
    public void testArrayOfPrimitiveWrappers() {
        WrPrimitives p = new WrPrimitives();
        String result = jsonSer.toJson(p);
        WrPrimitives p0 = jsonb.fromJson(result, WrPrimitives.class);
        assertThat(p0.intArray, not(equalTo(null)));
        assertThat(p0.intArray, arrayWithSize(6));
        assertThat(p0.intArray, arrayContaining(1000, 2000, 3000, 4000, 5000, 6000));
    }

    public void testArrayOfObjects() {

    }


    @Test
    public void testCollection(){
        Pojo p=new Pojo();
        p.list.add("aaaaa");
        p.list.add("bbbbb");
        p.list.add("ccccc");
        String result = jsonSer.toJson(p);
        Pojo p0 = jsonb.fromJson(result, Pojo.class);
        assertThat(p0.list.size(),is(3));
        assertThat(p0.list,contains("aaaaa","bbbbb","ccccc"));
    }

}
