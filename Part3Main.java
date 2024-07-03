import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Part3Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			args = generateInputFileNames(995);
		}
		InputOutput.readWriteInputOutput(args, "part3");
	}

	public static String solve(String input) {
		if (isValidExpression(input.trim())) {
			return "VALID";
		} else {
			return "INVALID";
		}
	}

	public static boolean isValidExpression(String expression) {
		String operators = "+-*/";
		boolean lastWasOperator = true;
		int parenthesesBalance = 0;
		int squareBracketsBalance = 0;

		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);

			if (Character.isDigit(c)) {
				lastWasOperator = false;
			} else if (operators.indexOf(c) != -1) {
				if (lastWasOperator) {
					return false;
				}
				lastWasOperator = true;
			} else if (c == '(' || c == '[') {
				if (!lastWasOperator) {
					return false;
				}
				if (c == '(') parenthesesBalance++;
				else squareBracketsBalance++;
				lastWasOperator = true;
			} else if (c == ')' || c == ']') {
				if (lastWasOperator) {
					return false;
				}
				if (c == ')') {
					parenthesesBalance--;
					if (parenthesesBalance < 0) return false;
				} else {
					squareBracketsBalance--;
					if (squareBracketsBalance < 0) return false;
				}
				lastWasOperator = false;
			} else {
				return false;
			}
		}

		return !lastWasOperator && parenthesesBalance == 0 && squareBracketsBalance == 0;
	}

	private static String[] generateInputFileNames(int maxIndex) {
		String[] fileNames = new String[(maxIndex / 5) + 1];
		for (int i = 0; i <= maxIndex; i += 5) {
			fileNames[i / 5] = "input" + i;
		}
		return fileNames;
	}
}
