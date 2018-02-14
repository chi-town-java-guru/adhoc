package test;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import adhoc.RunThis;

class RunThisTest {
	// Test that all CSV data loads work fine

	@Test
	void testLoad_plan_csv() throws FileNotFoundException, IOException {
		assert (RunThis.load_plan_csv().size() > 0);
	}

	@Test
	void testLoad_zips_csv() throws FileNotFoundException, IOException {
		assert (RunThis.load_zips_csv().size() > 0);
	}

	@Test
	void testLoad_slcsp_csv() throws FileNotFoundException, IOException {
		assert (RunThis.load_slcsp_csv().size() > 0);
	}

}
