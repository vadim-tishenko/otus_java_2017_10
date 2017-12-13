package ru.cwl.otus.hw08;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;

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
        assertThat(p0, samePropertyValuesAs(p));

    }

    public void testPrimitiveWrappers(){

    }

    public void testArrayOfPrimitives(){

    }

    public void testArrayOfPrimitiveWrappers(){

    }

    public void testArrayOfObjects(){

    }

}
