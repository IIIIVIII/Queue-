import java.util.*;
import java.io.*;

public class Part4Main {

	public static void main(String[] args) {
		if (args.length == 0) {
			args = generateInputFileNames(995); // Generate all input file names from 0 to 995
		}
		InputOutput.readWriteInputOutput(args, "part4");
	}

	private static String[] generateInputFileNames(int maxIndex) {
		String[] fileNames = new String[(maxIndex / 5) + 1];
		for (int i = 0; i <= maxIndex; i += 5) {
			fileNames[i / 5] = "input" + i;
		}
		return fileNames;
	}

	public static String solve(String input) {
		String[] parts = input.split("\n\n");

		// 检查输入是否包含足够的部分
		if (parts.length < 18) {
			return "Invalid input format";
		}

		// 提取外星字符和操作符
		String alienCharacters = parts[0].trim().replaceAll(" ", "");
		String alienOperations = parts[1].trim().replaceAll(" ", "");
		int charCount = alienCharacters.length();
		int opCount = alienOperations.length();

		// 初始化操作表
		char[][][] opTables = new char[opCount][charCount][charCount];

		// 读取每个操作表
		for (int tableIndex = 0; tableIndex < opCount; tableIndex++) {
			String[] rows = parts[tableIndex + 2].trim().split("\n");
			if (rows.length != charCount) {
				return "Invalid operation table row format: insufficient rows for table " + tableIndex;
			}
			for (int row = 0; row < charCount; row++) {
				String rowString = rows[row].trim().replaceAll(" ", "");
				if (rowString.length() != charCount) {
					return "Invalid operation table row format at table " + tableIndex + ", row " + row;
				}
				opTables[tableIndex][row] = rowString.toCharArray();
			}
		}

		// 获取表达式
		String expression = parts[parts.length - 1].trim();

		// 检查表达式中是否有无效字符
		for (char c : expression.toCharArray()) {
			if (alienCharacters.indexOf(c) == -1 && alienOperations.indexOf(c) == -1 && c != '(' && c != ')') {
				return "Invalid expression format: invalid character " + c;
			}
		}

		// 尝试求值表达式
		try {
			return String.valueOf(evaluateExpression(expression, alienCharacters, alienOperations, opTables));
		} catch (Exception e) {
			return "Invalid expression format: " + e.getMessage();
		}
	}

	private static char evaluateExpression(String expression, String alienCharacters, String alienOperations, char[][][] opTables) {
		while (expression.contains("(") || expression.contains(")")) {
			int closeIndex = expression.indexOf(")");
			int openIndex = expression.lastIndexOf("(", closeIndex);
			if (openIndex == -1 || closeIndex == -1) {
				throw new IllegalArgumentException("Mismatched parentheses in expression");
			}
			String subExpr = expression.substring(openIndex + 1, closeIndex);
			char result = evaluateSubExpression(subExpr, alienCharacters, alienOperations, opTables);
			expression = expression.substring(0, openIndex) + result + expression.substring(closeIndex + 1);
		}
		return evaluateSubExpression(expression, alienCharacters, alienOperations, opTables);
	}

	private static char evaluateSubExpression(String expression, String alienCharacters, String alienOperations, char[][][] opTables) {
		if (expression.length() == 1) {
			return expression.charAt(0);
		}

		List<ExpressionPart> parts = new ArrayList<>();
		int i = 0;

		while (i < expression.length()) {
			char operand1 = expression.charAt(i);
			i++;
			if (i < expression.length()) {
				char operation = expression.charAt(i);
				i++;
				if (i < expression.length()) {
					char operand2 = expression.charAt(i);
					i++;
					int precedence = alienOperations.indexOf(operation);
					parts.add(new ExpressionPart(operand1, operand2, operation, precedence, alienCharacters, alienOperations, opTables));
				}
			}
		}

		parts.sort(Comparator.comparingInt(part -> part.precedence));

		while (parts.size() > 1) {
			ExpressionPart part1 = parts.remove(0);
			ExpressionPart part2 = parts.remove(0);
			char mergedOperation = part1.precedence < part2.precedence ? part1.operation : part2.operation;
			char newOperand = evaluateOperation(part1.result, part2.result, mergedOperation, alienCharacters, alienOperations, opTables);
			parts.add(0, new ExpressionPart(newOperand, '\0', mergedOperation, Math.max(part1.precedence, part2.precedence), alienCharacters, alienOperations, opTables));
		}

		return parts.get(0).result;
	}

	private static char evaluateOperation(char operand1, char operand2, char operation, String alienCharacters, String alienOperations, char[][][] opTables) {
		int opIndex = alienOperations.indexOf(operation);
		int row = alienCharacters.indexOf(operand1);
		int col = alienCharacters.indexOf(operand2);
		if (opIndex == -1 || row == -1 || col == -1) {
			throw new IllegalArgumentException("Invalid operands or operation");
		}
		return opTables[opIndex][row][col];
	}

	private static class ExpressionPart {
		char operand1;
		char operand2;
		char operation;
		int precedence;
		char result;
		String alienCharacters;
		String alienOperations;
		char[][][] opTables;

		ExpressionPart(char operand1, char operand2, char operation, int precedence, String alienCharacters, String alienOperations, char[][][] opTables) {
			this.operand1 = operand1;
			this.operand2 = operand2;
			this.operation = operation;
			this.precedence = precedence;
			this.alienCharacters = alienCharacters;
			this.alienOperations = alienOperations;
			this.opTables = opTables;
			this.result = operand2 == '\0' ? operand1 : evaluateOperation(operand1, operand2, operation, alienCharacters, alienOperations, opTables);
		}
	}
}
