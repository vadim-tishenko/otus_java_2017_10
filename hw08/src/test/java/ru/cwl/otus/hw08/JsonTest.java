package ru.cwl.otus.hw08;

import org.junit.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by vadim.tishenko
 * on 11.12.2017 22:52.
 */
public class JsonTest {
    @Test
    public void test(){
        // Create Jsonb and serialize
        Jsonb jsonb = JsonbBuilder.create();
        Primitives primitives=new Primitives();
        String result = jsonb.toJson(primitives);

        Primitives p0 = jsonb.fromJson(result, Primitives.class);
        assertThat(p0, samePropertyValuesAs(primitives));

    }



}
