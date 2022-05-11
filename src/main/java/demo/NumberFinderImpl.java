package demo;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class NumberFinderImpl implements NumberFinder {
	
	private static Logger logger = LoggerFactory.getLogger(NumberFinderImpl.class);
	@Override
	public boolean contains(int valueToFind, List<CustomNumberEntity> list) {
		logger.info("Started searching for the number at: "+System.currentTimeMillis());
		FastestComparator comparator = new FastestComparator();
		Optional<Integer> result = list.stream()
				.parallel()
				.map(customNumber -> {
					try {
						return comparator.compare(valueToFind, customNumber);
					}
					catch(NumberFormatException e) {
						logger.error(String.format("Failure to parse!!") + e);
						return -1;
					}})
				.filter(p -> p==0).findAny();
		logger.info("Finished searching for the number at: "+System.currentTimeMillis());
		return result.isPresent();
	}

	@Override
	public List<CustomNumberEntity> readFromFile(String filePath) {
		List<CustomNumberEntity> output = new ArrayList<CustomNumberEntity>();
		Gson gs = new Gson();
		try (Reader reader = new FileReader(filePath)) {
			CustomNumberEntity[] out = gs.fromJson(reader, CustomNumberEntity[].class);
			output = (ArrayList<CustomNumberEntity>) Arrays.stream(out).filter(p -> null != p.getNumber())
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error(String.format("Failure while reading File!!! ") + e);
		}
		System.out.println(output);
		return output;
	}
	
	public static void main(String[] args) {
		NumberFinderImpl numberFinderImpl = new NumberFinderImpl();
		List<CustomNumberEntity> output = numberFinderImpl.readFromFile("C:/Java Myself/ascential-new/input.json");
		logger.info(output.toString());
	}
}
