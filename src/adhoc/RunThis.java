package adhoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class RunThis {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		/**
		 * Load CSV files: 1. slcsp.csv 2. zips.csv 3. plans.csv
		 */
		List<SLCSP> slcsp = load_slcsp_csv();
		List<Zip> zips = load_zips_csv();
		List<Plan> plans = load_plan_csv();
		/**
		 * Load the 1:M associations: zip -> rate_area -> rate
		 * 
		 * NOTE: ignore county info - rate_area is sufficient to get rate
		 */
		Map<String, Set<String>> zip_to_rateArea = computeZip_to_rateArea(zips);
		Map<String, Set<String>> rateArea_to_rate = computeRateArea_to_Rate(plans);

		/**
		 * Calculate second lowest cost silver plan (SLCSP)
		 * 
		 */
		String zipCode, rate;
		for (SLCSP s : slcsp) {
			zipCode = s.getZipcode();
			Set<String> rateAreas = zip_to_rateArea.get(zipCode);
			/**
			 * Per REQ: A ZIP Code can also be in more than one rate area. In that case, the
			 * answer is ambiguous and should be left blank.
			 */
			if (null == rateAreas || rateAreas.size() > 1) {
				rate = "";
			} else {
				String rateArea = rateAreas.iterator().next();
				Set<String> rates = rateArea_to_rate.get(rateArea);
				/**
				 * Per REQ: To find the SECOND lowest rate, we need at least TWO rates
				 */
				if (null == rates || !(rates.size() >= 2)) {
					rate = "";
				} else {
					TreeSet<String> rateSet = new TreeSet<>();
					rateSet.addAll(rates);
					Iterator<String> it = rateSet.iterator();
					rate = it.next();// ignore the lowest rate
					rate = it.next();// BINGO :-)
				}
			}
			s.setRate(rate);
		}
		/**
		 * Write rates back to slcsp.csv
		 * 
		 */
		writeToCSV(slcsp);
	}

	private static void writeToCSV(List<SLCSP> slcsp) throws IOException {
		FileWriter fw = null;
		try {
			fw = new FileWriter("resources/slcsp.csv", false);
			fw.write("zipcode rate");
			fw.write(System.lineSeparator());
			for (SLCSP s : slcsp) {
				fw.write(s.getZipcode());
				fw.write("   ");
				fw.write(s.getRate());
				fw.write(System.lineSeparator());
			}
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<String, Set<String>> computeRateArea_to_Rate(List<Plan> plans) {
		Map<String, Set<String>> rates_per_rateArea = new HashMap<>();
		Set<String> rates;
		for (Plan p : plans) {
			rates = rates_per_rateArea.get(p.getState() + p.getRateArea()) == null ? new HashSet<>()
					: rates_per_rateArea.get(p.getState() + p.getRateArea());
			rates.add(p.getRate());
			rates_per_rateArea.put(p.getState() + p.getRateArea(), rates);
		}
		return rates_per_rateArea;

	}

	private static Map<String, Set<String>> computeZip_to_rateArea(List<Zip> zips) {
		Map<String, Set<String>> zip_to_rateArea = new HashMap<>();
		Set<String> rateAreas;
		for (Zip z : zips) {
			rateAreas = zip_to_rateArea.get(z.getZipcode()) == null ? new HashSet<>()
					: zip_to_rateArea.get(z.getZipcode());
			rateAreas.add(z.getState() + z.getRateArea());
			zip_to_rateArea.put(z.getZipcode(), rateAreas);
		}
		return zip_to_rateArea;
	}

	public static List<Plan> load_plan_csv() throws FileNotFoundException, IOException {
		List<Plan> plans = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("resources/plans.csv"));
		String line = br.readLine(); // Ignore this line it is the header
		String state, rateArea, rate;
		while (null != (line = br.readLine())) {
			String[] split = line.split(",");
			state = split[1];
			rate = split[3];
			rateArea = split[4];
			if (split[2].trim().equalsIgnoreCase("Silver")) {
				plans.add(new Plan(state, rateArea, rate));
			}
		}
		br.close();
		return plans;
	}

	public static List<Zip> load_zips_csv() throws FileNotFoundException, IOException {
		List<Zip> zips = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("resources/zips.csv"));
		String line = br.readLine(); // Ignore this line it is the header
		String zipcode, state, rateArea;
		while (null != (line = br.readLine())) {
			String[] split = line.split(",");
			zipcode = split[0];
			state = split[1];
			rateArea = split[4];
			zips.add(new Zip(zipcode, state, rateArea));
		}
		br.close();
		return zips;
	}

	public static List<SLCSP> load_slcsp_csv() throws FileNotFoundException, IOException {
		List<SLCSP> slcsp = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader("resources/slcsp.csv"));
		String line = br.readLine(); // Ignore this line it is the header
		while (null != (line = br.readLine())) {
			slcsp.add(new SLCSP(line.trim(), ""));
		}
		br.close();
		return slcsp;
	}
}
