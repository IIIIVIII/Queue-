public class Part2Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			args = generateInputFileNames(995);
		}
		System.out.println("Starting Part2Main with arguments:");
		for (String arg : args) {
			System.out.println(arg);
		}
		InputOutput.readWriteInputOutput(args, "part2");
	}

	public static String solve(String input) {
		if (isValidParentheses(input.trim())) {
			return "VALID";
		} else {
			return "INVALID";
		}
	}

	public static boolean isValidParentheses(String expression) {
		MyStack<Character> stack = new MyStack<>();
		for (char c : expression.toCharArray()) {
			if (c == '(' || c == '[') {
				stack.push(c);
			} else if (c == ')' || c == ']') {
				if (stack.isEmpty()) {
					return false;
				}
				char open = stack.pop();
				if ((c == ')' && open != '(') || (c == ']' && open != '[')) {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}

	private static String[] generateInputFileNames(int maxNum) {
		String[] inputFiles = new String[maxNum / 5 + 1];
		for (int i = 0; i <= maxNum; i += 5) {
			inputFiles[i / 5] = "input" + i;
		}
		return inputFiles;
	}
}
