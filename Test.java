import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;


/**
 * A driver class to test the Cache class through
 * given console commands
 * 
 * @author Dominik Huffield
 *
 */
public class Test {

	public static void main(String[] args) {
		System.out.println("Launching Application!");
		if(args.length < 3 || args.length < 4) {
			printUsage();
			System.exit(1);
		} 
		if (args[0].equals("1")) {
			try {
				Integer.parseInt(args[1]);
				
			} catch (NumberFormatException n) {
				printUsage();
				System.exit(1);
			}
		} else if (args[0].equals("2")) {
			try {
				int a1 = Integer.parseInt(args[1]);
				int a2 = Integer.parseInt(args[2]);
				if (a2 < a1) {
                    printUsage();
                    System.exit(1);
                }
			} catch (NumberFormatException n) {
				printUsage();
				System.exit(1);
			}
		} else {
			printUsage();
			System.exit(1);
		}
		
		
		test(args);
	}
	
	/**
	 * Presents the user with dialogue explaining 
	 * the specifications for a valid input
	 */
	public static void printUsage() {
		System.out.println("There was an issue with the argument you entered, \n \tTo use one cache: \n" +
				"\t  $ java Test 1 [cache size] [file name]\n" +
				"\tTo use two caches: \n" + "\t  $ java Test 2 [first cache size] [second cache size] [filename] \n" +
				"\tFile must be valid and accessible. The second cache must but larger than the first."
				);
	}
	
	/**
	 * Tests the cache class by accepting a file and then 
	 * using the input cache sizes to build objects. The
	 * method then uses the cache for memory as it parses 
	 * through the file word by word
	 * 
	 * @param testFile taken from the file given by args
	 */
	public static void test(String[] testFile) {
		
		File file = null;
		Cache cache1 = null;
		Cache cache2 = null;
		Scanner scan = null;
		StringTokenizer line = null;
		String currentLine, token = null;
		
		if(testFile[0].equals("1")) {
			cache1 = new Cache(Integer.parseInt(testFile[1]));
			file = new File(testFile[2]);
		} else if(testFile[0].equals("2")) {
			cache1 = new Cache(Integer.parseInt(testFile[1]));
			cache2 = new Cache(Integer.parseInt(testFile[2]));
			file = new File(testFile[3]);
			
		} else {
			printUsage();
		}
		int processing = 0;
		try {
			
			scan = new Scanner(new FileReader(file));
			while(scan.hasNextLine()) {
				currentLine = scan.nextLine();
				line = new StringTokenizer(currentLine);
				
				while(line.hasMoreTokens()) {
					token = line.nextToken();
					if (cache1.getObject(token) != null) {
						cache1.write(token);
						if (cache2 != null) {
							cache2.write(token);
						}
					} else {
						
						cache1.addObject(token);
						if(cache2 != null && cache2.getObject(token) == null) {
							cache2.addObject(token);
						}
					}
					if (processing % 100000 == 0) {
						System.out.println("*** ***");
					}
					processing++;
				}
				 
			}
			scan.close();
			
			printTestResults(cache1, cache2, file);
			
		} catch(FileNotFoundException f) {
			printUsage();
			System.exit(1);
		}
	}
	
	/**
	 * Prints the resulting data after the file has been read and will display cache hits
	 * along with words in text file and hit ratings
	 * 
	 * @param cache1 The initial cache bank used for more ease of access
	 * @param cache2 The large cache, contains duplicate data of cache1
	 * @param file The file thats being parsed
	 */
	private static void printTestResults(Cache cache1, Cache cache2, File file) {
		
        DecimalFormat percentFormat = new DecimalFormat("#0.00%");
        DecimalFormat intFormat = new DecimalFormat("0.##");
        
        System.out.println("Text file read: " + file);
        System.out.println("Created cache with the size: " + cache1.capacity());
        if(cache2 != null) {
        	System.out.println("Created second cache with the size: " + cache2.capacity());
        }
        
        System.out.println();
        
        System.out.println("Number of hits for the first cache: " + intFormat.format(cache1.getHits()));
        System.out.println("Hit percentage for the first cache: " + percentFormat.format(cache1.getHitRate()));
        if(cache2 != null) {
        	System.out.println();
        	System.out.println("Number of hits for the second cache: " + intFormat.format(cache2.getHits()));
            System.out.println("Hit percentage for the second cache: " + percentFormat.format(cache2.getHitRate()));
        }
        
        System.out.println();
        System.out.println("Total number of references: " + intFormat.format(cache1.getRefers()));
        
        double totalHits = 0; 
        if(cache2 != null) {
        	totalHits = (cache1.getHits() + cache2.getHits());
        } else {
        	totalHits = cache1.getHits();
        }
        System.out.println("Total number of hits: " + intFormat.format(totalHits));
        System.out.println("Overall hit rate: " + percentFormat.format(totalHits/cache1.getRefers()));
	}    

}
