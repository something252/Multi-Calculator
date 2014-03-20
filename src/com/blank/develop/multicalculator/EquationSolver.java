package com.blank.develop.multicalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.ArrayList;

public final class EquationSolver{

	private EquationSolver() {}

	private static final DecimalFormat DF = new DecimalFormat();
	private static final StringBuffer SB = new StringBuffer();
	private static final MathContext MC = new MathContext(20);
	private enum Direction {
		LEFT_TO_RIGHT, RIGHT_TO_LEFT
	};

	/** Performs an operation on the two numbers specified */
	private static String doOperation(String lhs, char operator, String rhs) {
		BigDecimal bdLhs = new BigDecimal(lhs);
		BigDecimal bdRhs = new BigDecimal(rhs);
		switch(operator) {
			case '^':
				return "" + Math.pow( bdLhs.doubleValue(), bdRhs.doubleValue() );
			case '*':
				return "" + bdLhs.multiply(bdRhs).toString();
			case '/':
				return "" + bdLhs.divide(bdRhs, MC).toString();
			case '+':
				return "" + bdLhs.add(bdRhs).toString();
			case '%':
				return "" + bdLhs.remainder(bdRhs).toString();
		}
		return "";
	}

	/** Returns a string with it's first and last parentheses removed */
	private static String correctedString(String arg) {

		StringBuilder sb = new StringBuilder();
		boolean foundFirst = false;
		for(int i = 0; i < arg.length(); i++)
			if(foundFirst == false && arg.charAt(i) == '(')
				foundFirst = true;
			else sb.append(arg.charAt(i));

		arg = new StringBuilder(sb.reverse()).toString();
		sb.delete(0, sb.length());

		foundFirst = false;
		for(int i = 0; i < arg.length(); i++)
			if(foundFirst == false && arg.charAt(i) == ')')
				foundFirst = true;
			else sb.append(arg.charAt(i));

		return new StringBuilder(sb.reverse()).toString();
	}

	/** Returns a string without spaces */
	private static String removeSpaces(String a) {
		String tmp = "";
		for(int i = 0; i < a.length(); i++)
			if(a.charAt(i) != ' ')
				tmp += "" + a.charAt(i);

		return tmp;
	}

	
	private static String parse(String arg) {

		String expression = removeSpaces(correctedString(arg));
		String finalExpression = "";
		boolean operatorEncountered = true;
		boolean initialValue = true;
		for(int i = 0; i < expression.length(); i++) {
			if(expression.charAt(i) == '(') {
				String multiply = "";
				if(operatorEncountered == false && initialValue == false) {
					multiply += "*";
				}

				String placeHolder = "(";
				int valuesCounted = 1;
				operatorEncountered = false;
				for(int j = i + 1; valuesCounted != 0; j++) {
					if(expression.charAt(j) == '(')
						valuesCounted++;
					else if(expression.charAt(j) == ')')
						valuesCounted--;
					placeHolder += "" + expression.charAt(j);
				}

				String evaluatedString = parse(placeHolder);
				finalExpression += multiply + evaluatedString;
				i+= (placeHolder.length() - 1);
			}else{
				if(expression.charAt(i) == '-' && operatorEncountered == false) {
					finalExpression += ((!initialValue) ? "+": "") + expression.charAt(i);
				}else if(expression.charAt(i) == '-' && operatorEncountered == true) {
					finalExpression += "-1*";
				}else finalExpression += expression.charAt(i);

				if((expression.charAt(i) == '+' || expression.charAt(i) == '/' || expression.charAt(i) == '^'
				|| expression.charAt(i) == '*' || expression.charAt(i) == '%' || expression.charAt(i) == '-'))
					operatorEncountered = true;
				else if(expression.charAt(i) != ' ')
					operatorEncountered = false;
			}
			initialValue = false;
		}

		finalExpression = removeSpaces(finalExpression);
		String perfectExpression = "";

		for(int i = 0; i < finalExpression.length(); i++) {
			if((i + 1) < finalExpression.length())
				if(finalExpression.charAt(i) == '-' && finalExpression.charAt(i + 1) == '-')
					i+=2;
			perfectExpression += "" + finalExpression.charAt(i);
		}
		finalExpression = perfectExpression;

		ArrayList<String> totalNumbers = new ArrayList<String>(0);
		ArrayList<Character> totalOperations = new ArrayList<Character>(0);

		for(int i = 0; i < finalExpression.length(); i++) {
			if(finalExpression.charAt(i) >= '0' && finalExpression.charAt(i) <= '9'
			|| finalExpression.charAt(i) == '-' || finalExpression.charAt(i) == '.'
			|| finalExpression.charAt(i) == ',') {
				String temp = "";
				for(int j = i; j < finalExpression.length(); j++) {
					if(finalExpression.charAt(j) >= '0' && finalExpression.charAt(j) <= '9'
					|| finalExpression.charAt(j) == '-' || finalExpression.charAt(j) == '.'
					|| finalExpression.charAt(j) == ',') {
							temp += "" + finalExpression.charAt(j);
					}else break;
				}
				totalNumbers.add(temp);
				i += temp.length() == 0 ? 0 : (temp.length() - 1);
			}
			else if(finalExpression.charAt(i) == '*' || finalExpression.charAt(i) == '/' || finalExpression.charAt(i) == '^'
				 || finalExpression.charAt(i) == '+' || finalExpression.charAt(i) == '%') {
				totalOperations.add(Character.valueOf(finalExpression.charAt(i)));
			}
		}
		
		Character[] firstSet = {'^'}, secondSet = {'*', '/', '%'}, thirdSet = {'+'};
		calculate(totalNumbers, totalOperations, firstSet, Direction.RIGHT_TO_LEFT);
		calculate(totalNumbers, totalOperations, secondSet, Direction.LEFT_TO_RIGHT);
		calculate(totalNumbers, totalOperations, thirdSet, Direction.LEFT_TO_RIGHT);

		return totalNumbers.get(0);
	}

	/** Returns true if the target character exists in the set of Character operands, returns false otherwise. */
	private static boolean containsCharacter(Character anOperation, Character operands[]) {
		for(Character item : operands) {
			if(anOperation.equals(item)) {
				return true;
			}
		}
		return false;
	}

	/** Attempts to solve an equation that is separated into a set of numbers and operands. (More to add) */
	private static void calculate(ArrayList<String> totalNumbers, ArrayList<Character> totalOperations, Character operands[], Direction dir) {
		String result = "";
		if(dir == Direction.LEFT_TO_RIGHT) {
			for(int i = 0; i < totalOperations.size(); i++) {
				if(containsCharacter(totalOperations.get(i), operands)) {
					result = doOperation(totalNumbers.get(i), (char)totalOperations.get(i), totalNumbers.get(i + 1));
					totalNumbers.set(i, result);
					totalOperations.remove(i);
					totalNumbers.remove(i + 1);
					i--;
				}
			}
		}
		else if(dir == Direction.RIGHT_TO_LEFT) {
			for(int i = totalOperations.size() - 1; i >= 0; i--) {
				if(containsCharacter(totalOperations.get(i), operands)) {
					result = doOperation(totalNumbers.get(i), (char)totalOperations.get(i), totalNumbers.get(i + 1));
					totalNumbers.set(i, result);
					totalOperations.remove(i);
					totalNumbers.remove(i + 1);
				}
			}
		}
	}

	/** Checks to see if the expression is solvable or not */
	private static boolean isSolvable(String equation) {
		int parenthesisCount = 0;	// assuming 0 parenthesis to begin with
		for(char element : equation.toCharArray()) { // for every char in the String equation
			if(element == '(')	// if the element is a left parenthesis
				parenthesisCount++;	// increment the parenthesisCount
			else if(element == ')')	// else if the element is a right parenthesis
				parenthesisCount--;	// decrement the parenthesisCount

			if(parenthesisCount < 0) // if brackets aren't in correct order, return false
				return false;
		}
		return parenthesisCount == 0; // return true if parenthesisCount is zero, otherwise return false
	}

	/** Attempts to solve a given equation */
	public static String solver(String equation) {
		if(isSolvable(equation)) {
			String value = ("(" + equation + ")" ); // Appending parenthesis to the equation for accuracy
			return parse(value); //	returning the final value of the expression
		}else return "";
	}

	/** Attempts to solve a given equation with a given precision */
	public static String solver(String equation, int precision) {
		SB.delete(0, SB.length());
		return DF.format( (double)Double.parseDouble(solver(equation)), SB, new FieldPosition(precision) ).toString();
	}
}