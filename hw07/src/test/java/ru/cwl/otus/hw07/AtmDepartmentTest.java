package ru.cwl.otus.hw07;

import org.junit.Before;
import org.junit.Test;
import ru.cwl.otus.hw06.ATM;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by vadim.tishenko
 * on 05.12.2017 21:49.
 */
public class AtmDepartmentTest {
    private static final int ATM_START_SUM = 630 + 6300 + 160;
    private AtmBuilder atmBuilder=new AtmBuilder();
    private AtmDepartment atmDepartment = new AtmDepartment();
    private ATM atm1, atm2, atm3;

    @Before
    public void setUp() {

        // 300+250+80=630
        atm1 = atmBuilder.cb(3, 100).cb(5, 50).
                cb(8, 10).build();
        // 3000+2500+800=6300
        atm2 = atmBuilder.clear().cb(30, 100).cb(50, 50).
                cb(80, 10).build();
        // 160
        atm3 = atmBuilder.clear().cb(1, 100).cb(1, 50)
                .cb(1, 10).build();
    }

    @Test
    public void testDeptBalanceWithOutAtm() {
        assertThat(atmDepartment.getBalance(), is(0));
    }

    @Test
    public void testDeptStartBalance3Atm() {
        atmDepartment.add(atm1, atm2, atm3);
        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM));
    }

    @Test
    public void testMoneyOperations() {
        atmDepartment.add(atm1, atm2, atm3);

        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM));

        atm1.getMoney(500);
        atm2.getMoney(5000);

        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM - 5500));

        atm2.putMoney("100,100,100");
        atm3.putMoney("100,50,10,10");

        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM - 5500 + 470));

    }

    @Test // проверка сохранения состояния департамента;
    public void testSaveAndRestoreDepartState() {
        atmDepartment.add(atm1, atm2, atm3);
        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM));

        AtmDepartmentMemento memento = atmDepartment.saveToMemento();


        atm1.getMoney(500);
        atm2.getMoney(5000);

        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM - 5500));

        atm2.putMoney("100,100,100");
        atm3.putMoney("100,50,10,10");

        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM - 5500 + 470));

        atmDepartment.restoreFromMemento(memento);
        assertThat(atmDepartment.getBalance(), is(ATM_START_SUM));

    }

}