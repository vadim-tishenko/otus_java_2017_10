package ru.cwl.otus.hw06;

import org.junit.Test;
import ru.cwl.otus.hw06.adapter.List2BanknotesAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * Created by vadim.tishenko
 * on 03.12.2017 17:17.
 */
public class List2BanknotesAdapterTest {

    List2BanknotesAdapter adapter=new List2BanknotesAdapter();

    @Test
    public void from() {
        Integer [] a={100,50,100,50,10,50};
        List<Integer> src= new ArrayList<>(Arrays.asList(a));
        MoneysPack res = adapter.from(src);
        assertThat(res.getSum(),is(360));
    }

    @Test
    public void to() {
        MoneysPack mp=new MoneysPack();
        mp.put(100,3);
        mp.put(20,2);
        List<Integer> res = adapter.to(mp);
        assertThat(res, containsInAnyOrder(20,100,100,20,100));

    }
}