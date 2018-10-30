import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RomanNumerals {
	
	private String inputString;
	private ArrayList<Integer> valuesArray;
	private Map<Character, Integer> rnConversionMap;
	
	private static final String ERROR_INVALID_INPUT_MSG = "Invalid roman numeral!!!";
	private static final String RESULT_MSG = "Arabic number: ";
	
	public RomanNumerals() {
		valuesArray = new ArrayList<>();
		rnConversionMap = RomanNumeralConversionMap.rnConversionMap;
	}

	public String getInputString() {
		return inputString;
	}

	public void setInputString(String inputString) {
		valuesArray.clear();
		this.inputString = inputString.toUpperCase();
	}

	private boolean sumRules(int val, int sum) {
		boolean ret = true;
		switch (val) {
			case 1:
				ret = sum < 3;
				break;
			case 5:
				ret = sum < 4;
				break;
			case 10:
				ret = sum < 30;
				break;
			case 50:
				ret = sum < 40;
				break;
			case 100:
				ret = sum < 300;
				break;
			case 500:
				ret = sum < 400;
				break;
			default:
				break;
		}
		
		return ret;
	}
	
	private boolean diffRules(int val, int sum) {
		boolean ret = false;
		
		switch (val) {
			case 1:
				ret = (sum == 5 || sum == 10);
				break;
			case 10:
				ret = ((sum > 49 && sum < 60) || (sum > 99 && sum < 110));
				break;
			case 100:
				ret = ((sum > 499 && sum < 600) || (sum > 999 && sum < 1100));
				break;
			default:
				break;
		}
		
		return ret;
	}
	
	private void convertInputToDecimalArray() {
		Integer val = null;
		
		if (valuesArray.isEmpty()) {
			for (int i = 0; i < inputString.length(); ++i) {
				val = rnConversionMap.get(inputString.charAt(i));
				
				if (val == null) {
					valuesArray.clear();
					break;
				}
				
				valuesArray.add(val);
			}
		}
		
		return;
	}
	
	public int calculateArabicNumberAlg() {
		int retNum = 0;
		int size = 0;
		
		convertInputToDecimalArray();
		
		if (!valuesArray.isEmpty()) {
			size = valuesArray.size();
			retNum = valuesArray.get(size - 1);
			
			if (size > 1) {
				for (int i = size - 2 ; i >= 0 ; --i) {
					if (valuesArray.get(i) >= valuesArray.get(i+1)) {
						if (!sumRules(valuesArray.get(i), retNum)) {
							valuesArray.clear();
							break;
						}
						retNum += valuesArray.get(i);
					} else {
						if (!diffRules(valuesArray.get(i), retNum)) {
							valuesArray.clear();
							break;
						}
						retNum -= valuesArray.get(i);
					}
				}
			}
		}
		
		if (valuesArray.isEmpty()){
			System.out.println(ERROR_INVALID_INPUT_MSG);
			retNum = 0;
		}
			
		return retNum;
	}
	
	public static void main(String[] args) {
		int result = 0;
		
		System.out.println("Poke the roman numeral and hit Enter\nPoke again for an new numeral, or hammer Enter to exit\nExample: LXXXVIII\n");
		
		try (Scanner sc = new Scanner(System.in)) {
			String inputRomanNumeral = sc.nextLine();
			RomanNumerals rn = new RomanNumerals();
			
			
			while (!inputRomanNumeral.isEmpty()) {
				rn.setInputString(inputRomanNumeral);
				result = rn.calculateArabicNumberAlg();
				if (result > 0) {
					System.out.println(RESULT_MSG + result);
				}
		        inputRomanNumeral = sc.nextLine();
			}
		}
		
		System.out.println("Thank You!");
	}
	
	public static class RomanNumeralConversionMap {
		public static Map<Character, Integer> rnConversionMap; 
		
		static {
			rnConversionMap = new HashMap<>();
			rnConversionMap.put('I', 1);
			rnConversionMap.put('V', 5);
			rnConversionMap.put('X', 10);
			rnConversionMap.put('L', 50);
			rnConversionMap.put('C', 100);
			rnConversionMap.put('D', 500);
			rnConversionMap.put('M', 1000);
		}
	}
}
