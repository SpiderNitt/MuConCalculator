package edu.nitt.spider.calculator;

public class CalculatorParser {

    // constants

    private enum State {
        ENTERING_NO_1,
        ENTERING_NO_2,
        ENTERING_OP
    }

    private static final char[] ALLOWED_DIGITS
            = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final char[] ALLOWED_OPS
            = {'+', '-', '*', '/'};

    // variables

    private int firstValue;
    private int secondValue;
    private char latestOperator;

    private State currentState;

    // constructor

    public CalculatorParser() {
        firstValue = 0;
        secondValue = 0;
        currentState = State.ENTERING_NO_1;
    }

    // major functions

    public String parse(String inputString) {

        reset();

        char currentChar;

        for (int i = 0; i < inputString.length(); i++) {
            currentChar = inputString.charAt(i);
            try {
                updateState(currentChar);
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        if (currentState == State.ENTERING_NO_1) {
            return Integer.toString(firstValue);
        }
        else if (currentState == State.ENTERING_OP) {
            return "Error: Last character operator";
        }
        else {
            try {
                int result = calculateValue(firstValue, latestOperator, secondValue);
                return Integer.toString(result);
            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }

    private void updateState(char c) throws Exception {
        if (isContainedIn(c, ALLOWED_DIGITS)) {
            // do something

            int currentDigit = Integer.parseInt(Character.toString(c));

            if (currentState == State.ENTERING_NO_1) {
                firstValue = 10*firstValue + currentDigit;
            }
            else if (currentState == State.ENTERING_NO_2) {
                secondValue = 10*secondValue + currentDigit;
            }
            else if (currentState == State.ENTERING_OP) {
                currentState = State.ENTERING_NO_2;
                secondValue = currentDigit;
            }
        }
        else if (isContainedIn(c, ALLOWED_OPS)) {
            // do something else

            if (currentState == State.ENTERING_NO_1) {
                currentState = State.ENTERING_OP;
                latestOperator = c;
            }
            else if (currentState == State.ENTERING_NO_2) {
                currentState = State.ENTERING_OP;
                firstValue = calculateValue(firstValue, latestOperator, secondValue);
                latestOperator = c;
            }
            else if (currentState == State.ENTERING_OP) {
                throw new Exception("Error: Two consecutive operators");
            }
        }
    }

    // utility function

    private boolean isContainedIn(char c, char[] container) {
        boolean result = false;
        for (int i = 0; i < container.length; i++) {
            if (c == container[i]) {
                result = true;
                break;
            }
        }

        return result;
    }

    private int calculateValue (int first, char op, int second) throws Exception {
        if (op == '+') return first + second;
        else if (op == '-') return first - second;
        else if (op == '*') return first * second;
        else if (op == '/') {
            if (second == 0) throw new Exception("Divide by zero error");
            else return first / second;
        }
        else return 0;
    }

    private void reset() {
        firstValue = 0;
        secondValue = 0;
        currentState = State.ENTERING_NO_1;
    }
}
