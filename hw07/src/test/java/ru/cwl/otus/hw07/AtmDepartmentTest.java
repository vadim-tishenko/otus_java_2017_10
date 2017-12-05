package ru.cwl.otus.hw07;

import org.junit.Before;
import org.junit.Test;
import ru.cwl.otus.hw06.ATM;
import ru.cwl.otus.hw06.CashBox;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by vadim.tishenko
 * on 05.12.2017 21:49.
 */
public class AtmDepartmentTest {
    public static final int ATM_START_SUM = 630 + 6300 + 160;
    AtmDepartment atmDepartment=new AtmDepartment();
    ATM atm1,atm2,atm3;
    @Before
    public void setUp() throws Exception {
        atm1=new ATM(); // 300+250+80=630
        atm1.insert(new CashBox(100,3,100));
        atm1.insert(new CashBox(100,5,50));
        atm1.insert(new CashBox(100,8,10));

        atm2=new ATM(); // 3000+2500+800=6300
        atm2.insert(new CashBox(100,30,100));
        atm2.insert(new CashBox(100,50,50));
        atm2.insert(new CashBox(100,80,10));

        atm3=new ATM(); // 160
        atm3.insert(new CashBox(100,1,100));
        atm3.insert(new CashBox(100,1,50));
        atm3.insert(new CashBox(100,1,10));
    }

    @Test
    public void getBalanceDeptWithOutAtm() {
        assertThat(atmDepartment.getBalance(),is(0));
    }

    @Test
    public void testStartBalanceDept3Atm() {
        atmDepartment.atmList.add(atm1);
        atmDepartment.atmList.add(atm2);
        atmDepartment.atmList.add(atm3);
        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM));
    }

    @Test
    public void testMoneyOperations() {
        atmDepartment.atmList.add(atm1);
        atmDepartment.atmList.add(atm2);
        atmDepartment.atmList.add(atm3);

        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM));

        atm1.getMoney(500);
        atm2.getMoney(5000);

        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM -5500));

        atm2.putMoney("100,100,100");
        atm3.putMoney("100,50,10,10");

        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM -5500+470));

    }

    @Test // проверка сохранения состояния департамента;
    public void testSaveAndRestoreAtmDepartState() {
        atmDepartment.atmList.add(atm1);
        atmDepartment.atmList.add(atm2);
        atmDepartment.atmList.add(atm3);
        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM));

        AtmState state=atmDepartment.getState();


        atm1.getMoney(500);
        atm2.getMoney(5000);

        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM -5500));

        atm2.putMoney("100,100,100");
        atm3.putMoney("100,50,10,10");

        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM -5500+470));

        atmDepartment.restoreState(state);
        assertThat(atmDepartment.getBalance(),is(ATM_START_SUM));

    }

}