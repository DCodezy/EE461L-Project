/*
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class NicotineHabitTest {

	NicotineHabit h;

	@Before
	public void setUp() throws Exception {
		h = new NicotineHabit(true);
	}

	//test getPercent with several values

	@Test
	public void testGetPercentA(){
		int days = 3;
		assertEquals(h.getPercent(days), 23);
	}

	@Test
	public void testGetPercentB(){
		int days = 28;
		assertEquals(h.getPercent(days), 50);
	}

	@Test
	public void testGetPercentC(){
		int days = 101;
		assertEquals(h.getPercent(days), 73);
	}

	//test getExpectedStress for several values

	@Test
	public void testGetExpectedStressA(){
		int days = 12;
		assertEquals(h.getExpectedStress(days), 7);
	}

	@Test
	public void testGetExpectedStressB(){
		int days = 122;
		assertEquals(h.getExpectedStress(days), 1);
	}

	@Test
	public void testGetExpectedStressC(){
		int days = 289;
		assertEquals(h.getExpectedStress(days), 3);
	}

	//test recalculateState for all possible states

	@Test
	public void testRecalculateStateNormal() {
		int stress = 3;
		int days = 245;
		h.recalculateState(days, stress);

		assertEquals(h.getState().toString(), "NORMAL");
	}

	@Test
	public void testRecalculateStateHighStress() {
		int stress = 9;
		int days = 5;
		h.recalculateState(days, stress);

		assertEquals(h.getState().toString(), "HIGH_RISK");
	}

	@Test
	public void testRecalculateStateMedRisk() {
		int stress = 7;
		int days = 65;
		h.recalculateState(days, stress);

		assertEquals(h.getState().toString(), "MED_RISK");
	}
}

 */