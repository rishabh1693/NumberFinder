package demo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NumberFinderImplTest {
	
	private NumberFinderImpl numberFinderImpl;
	private String FILE_PATH = "/src/test/java/demo/";
	private String CURRENT_DIR = "user.dir";
	private String CORRECT_INPUT = "ValidInput.json";
	private String INVALID_INPUT = "InvalidInput.json";
	private String FILE_DOES_NOT_EXIST = "Dummy.json";
	
	@Before
	public void setUp() {
		numberFinderImpl = new NumberFinderImpl();
		System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","12");
	}
	
	@Test
	public void readFromFile_success() {
		List<CustomNumberEntity> customNumberEntityList = numberFinderImpl.readFromFile(System.getProperty(CURRENT_DIR) + FILE_PATH + CORRECT_INPUT);
		assertEquals("[CustomNumberEntity [number=67], CustomNumberEntity [number=45], CustomNumberEntity [number=45], CustomNumberEntity [number=s], CustomNumberEntity [number=-3], CustomNumberEntity [number=12], CustomNumberEntity [number=100], CustomNumberEntity [number=3]]",
		        customNumberEntityList.toString());
	}
	
	@Test
	public void readFromFile_fileDoesNotExist() {
		List<CustomNumberEntity> customNumberEntityList = numberFinderImpl.readFromFile(System.getProperty(CURRENT_DIR) + FILE_PATH + INVALID_INPUT);
		assertEquals(0,customNumberEntityList.size());
	}
	
	@Test
	public void readFromFile_invalidFileData() {
		List<CustomNumberEntity> customNumberEntityList = numberFinderImpl.readFromFile(System.getProperty(CURRENT_DIR) + FILE_PATH + FILE_DOES_NOT_EXIST);
		assertEquals(0,customNumberEntityList.size());
	}
	
	@Test
	public void contains_numberFound() {
		List<CustomNumberEntity> customNumberEntityList = numberFinderImpl.readFromFile(System.getProperty(CURRENT_DIR) + FILE_PATH + CORRECT_INPUT);
		assertEquals(true,numberFinderImpl.contains(12, customNumberEntityList));
	}
	
	@Test
	public void contains_numberFound1() {
		List<CustomNumberEntity> customNumberEntityList = numberFinderImpl.readFromFile(System.getProperty(CURRENT_DIR) + FILE_PATH + CORRECT_INPUT);
		assertEquals(true,numberFinderImpl.contains(45, customNumberEntityList));
	}
	
	@Test
	public void contains_numberNotFound() {
		List<CustomNumberEntity> customNumberEntityList = numberFinderImpl.readFromFile(System.getProperty(CURRENT_DIR) + FILE_PATH + CORRECT_INPUT);
		assertEquals(false,numberFinderImpl.contains(1, customNumberEntityList));
	}
	
	@Test
	public void contains_numberNotFound1() {
		List<CustomNumberEntity> customNumberEntityList = numberFinderImpl.readFromFile(System.getProperty(CURRENT_DIR) + FILE_PATH + CORRECT_INPUT);
		assertEquals(false,numberFinderImpl.contains(1000, customNumberEntityList));
	}
}
