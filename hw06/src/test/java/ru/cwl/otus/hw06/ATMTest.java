package ru.cwl.otus.hw06;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import ru.cwl.otus.hw06.exception.NotEnoughMoneyException;
import ru.cwl.otus.hw06.exception.NotEnoughSpaceException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by vadim.tishenko
 * on 28.11.2017 21:55.
 */
public class ATMTest {
    ATM atm;
    private CashBox box20x100;
    private CashBox box15x50;
    private CashBox box10x10;

    private CashBox box1x100;
    private CashBox box1x50;
    private CashBox box1x10;

    @Before
    public void setUp() {
        atm = new ATM();
        box20x100 = new CashBox(100, 20, 100);
        box15x50 = new CashBox(100, 15, 50);
        box10x10 = new CashBox(100, 10, 10);

        box1x100 = new CashBox(100, 1, 100);
        box1x50 = new CashBox(100, 1, 50);
        box1x10 = new CashBox(100, 1, 10);
    }


    @Test
    public void getBalance()  {
        assertThat(atm.getBalance(), is(0));
        atm.insert(box20x100);
        assertThat(atm.getBalance(), is(2000));
        atm.insert(box15x50);
        atm.insert(box10x10);
        assertThat(atm.getBalance(), is(2850));
        atm.remove(box10x10);
        assertThat(atm.getBalance(), is(2750));
    }

    @Test
    public void putMoney550() {
        atm.insert(box20x100);
        atm.insert(box15x50);
        atm.putMoney("100,100,100,100,50,100");

        assertThat(box20x100.getCount(), is(25));
        assertThat(box15x50.getCount(), is(16));

        assertThat(atm.getBalance(), is(3300));
    }


    @Test(expected = NotEnoughSpaceException.class)
    public void putMoney200Overflow() {
        // недостаточно места при приеме денег
        atm.insert(new CashBox(5, 4, 100));

        atm.putMoney("100,100");

    }

    @Test(expected = NotEnoughSpaceException.class)
    public void putMoney200Overflow2() {
        // недостаточно места при приеме денег под конкретный номинал банкнот.
        atm.insert(new CashBox(5, 5, 100));
        atm.insert(box15x50);

        atm.putMoney("100,100");

    }

    @Test
    public void getMoney500() {
        atm.insert(box20x100);
        atm.insert(box15x50);

        String result = atm.getMoney(500);

        assertThat(box20x100.getCount(), is(15));
        assertThat(box15x50.getCount(), is(15));
        assertThat(result, is("100,100,100,100,100"));
        assertThat(atm.getBalance(), is(2250));
    }

    @Test
    public void getMoney250() {
        atm.insert(box20x100);
        atm.insert(box15x50);

        String result = atm.getMoney(250);

        assertThat(box20x100.getCount(), is(18));
        assertThat(box15x50.getCount(), is(14));
        assertThat(result, is("100,100,50"));
        assertThat(atm.getBalance(), is(2500));
    }

    @Test
    public void getMoney160() {
        atm.insert(box20x100);
        atm.insert(box15x50);
        atm.insert(box10x10);

        String result = atm.getMoney(160);

        assertThat(box20x100.getCount(), is(19));
        assertThat(box15x50.getCount(), is(14));
        assertThat(box10x10.getCount(), is(9));
        assertThat(result, is("100,50,10"));
        assertThat(atm.getBalance(), is(2850 - 160));
    }


    @Test(expected = NotEnoughMoneyException.class)
    public void getMoney170fail() {
        // недостаточно денег при выдаче
        atm.insert(box1x100);
        atm.insert(box1x50);
        atm.insert(box1x10);

        String result = atm.getMoney(170);

    }

    @Test(expected = NotEnoughMoneyException.class)
    public void getMoney150fail() {
        // деньги есть(суммой) но нельзя подобрать под выдачу. есть 2х100 а нужно выдать 150
        atm.insert(new CashBox(100, 2, 100));

        String result = atm.getMoney(150);
        assertThat(atm.getBalance(), is(200));
    }

    @Test
    public void testMemento(){
        atm.insert(box20x100);
        atm.insert(box15x50);
        atm.insert(box10x10);

        assertThat(2850,is(atm.getBalance()));
        AtmMemento memento = atm.saveToMemento();

        atm.getMoney(1060);
        assertThat(2850-1060,is(atm.getBalance()));

        atm.restoreFromMemento(memento);
        assertThat(2850,is(atm.getBalance()));
    }

}