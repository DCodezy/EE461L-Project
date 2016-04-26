/*package com.goodfriend.goodfriend;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class NicotineHabitTest {

    NicotineHabit h;

    @Before
    public void setUp() throws Exception {
        h = new NicotineHabit(true);
    }


    @Test
    public void testRecalculateStateNormal() {
        int stress = 3;
        int days = 245;
        h.recalculateState(days, stress);
        System.out.println(h.getState().toString());
        assertEquals(h.getState().toString(), "NORMAL");
    }

    @Test
    public void testRecalculateStateHighStress() {
        int stress = 9;
        int days = 5;
        h.recalculateState(days, stress);
        System.out.println(h.getState().toString());
        assertEquals(h.getState().toString(), "HIGH_RISK");
    }

    @Test
    public void testRecalculateStateMedRisk() {
        int stress = 7;
        int days = 65;
        h.recalculateState(days, stress);
        System.out.println(h.getState().toString());
        assertEquals(h.getState().toString(), "MED_RISK");
    }

}
*/