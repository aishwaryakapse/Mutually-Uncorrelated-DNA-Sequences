
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;

public class UncorrelatedDNA {

	// Method that checks for self-uncorrelated or not. Returns true if
	// self-uncorrelated string
	private static boolean findSelfUncorrelated(String s) {
		BitSet b = new BitSet();
		int n = s.length();
		for (int j = 0; j < n; j++) {
			if (s.charAt(j) == s.charAt(0)) {
				b.set(j);
			}
		}
		if (b.get(0) == true && b.cardinality() == 1) {
			return true;
		} else {
			return false;
		}
	}

	// Method to find whether mutually uncorrelated pair or not. Adds the
	// strings if both are mutually uncorrelated.
	// Checks for self-uncorrelation for each string
	// Adds the result to an Arraylist of type string.
	// Returns this Arraylist
	private static ArrayList<String> findUncorrelated(String[] str, int n) {
		ArrayList<String> result = new ArrayList<String>();

		// Check pairs for uncorrelated pairs
		for (int i = 0; i < str.length - 1; i++) {
			String s1 = str[i];
			if (findSelfUncorrelated(s1)) {
				for (int j = i + 1; j < str.length; j++) {
					String s2 = str[j];
					if (findSelfUncorrelated(s2)) {
						BitSet b1 = new BitSet();
						for (int k = 0; k < s1.length(); k++) {
							if (s1.charAt(k) == s2.charAt(0)) {
								b1.set(k);
							}
						}

						BitSet b2 = new BitSet();
						for (int k = 0; k < s2.length(); k++) {
							if (s2.charAt(k) == s1.charAt(0)) {
								b2.set(k);
							}
						}

						// Add result to arraylist
						if (b1.cardinality() == 0 && b2.cardinality() == 0) {
							if (!result.contains(s1)) {
								result.add(s1);
							}
							if (!result.contains(s2)) {
								result.add(s2);
							}
						}
					}
				}
			}
		}

		return result;
	}

	//Method to print the DNA sequences that we found to be mutually uncorrelated
	private static void printResult(ArrayList<String> result) {
		for (String s : result) {
			System.out.println(s);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		
		//HashMap to set 2 binary bits for each protein
		HashMap<String, Character> hm = new HashMap<String, Character>();
		hm.put("00", 'A');
		hm.put("01", 'C');
		hm.put("10", 'G');
		hm.put("11", 'T');
		
		//Generate the DNA sequences. Maximum possible are 4^6 = 4096.
		ArrayList<String> str = new ArrayList<String>();
		for(int i=0; i<4096; i++) {
			String binString = Integer.toBinaryString(i);
			//12 binary bits. so pad zeros at the start
			int length = 12 - Integer.toBinaryString(i).length();
			char[] padArray = new char[length];
			Arrays.fill(padArray, '0');
			String padString = new String(padArray);
			binString = padString + binString;
			
			String sequence = new String("");
			for(int j = 0;j < binString.length()-1; j = j+2) {
				String sub = binString.substring(j, j+2);
				//Check for protein type {A, C, G, T} in HashMap
				if(hm.containsKey(sub)) {
					sequence = sequence + hm.get(sub);
				}
			}
			str.add(sequence);
		}
		
		//convert Arraylist to Array for simplicity of code
		String[] s = new String[str.size()];
		for(int i=0; i<str.size(); i++) {
			s[i] = str.get(i);
		}
		
		//Get the output
		ArrayList<String> result = findUncorrelated(s, 6);
		System.out.println("Make sure all the strings are of size n. In our case n is 6");
		System.out.println();
		System.out.println("********************************");
		System.out.println();
		System.out.println("The mutually uncorrelated DNA sequences are: ");

		printResult(result);
		System.out.println("Size of Result: " + result.size());
	}
}
