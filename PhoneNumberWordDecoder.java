import java.util.*;

public class PhoneNumberWordDecoder {

    // Mapping of letters to digits based on a phone keypad
    private static final Map<Character, Character> KEYPAD_MAP = new HashMap<>();
    static {
        KEYPAD_MAP.put('A', '2'); KEYPAD_MAP.put('B', '2'); KEYPAD_MAP.put('C', '2');
        KEYPAD_MAP.put('D', '3'); KEYPAD_MAP.put('E', '3'); KEYPAD_MAP.put('F', '3');
        KEYPAD_MAP.put('G', '4'); KEYPAD_MAP.put('H', '4'); KEYPAD_MAP.put('I', '4');
        KEYPAD_MAP.put('J', '5'); KEYPAD_MAP.put('K', '5'); KEYPAD_MAP.put('L', '5');
        KEYPAD_MAP.put('M', '6'); KEYPAD_MAP.put('N', '6'); KEYPAD_MAP.put('O', '6');
        KEYPAD_MAP.put('P', '7'); KEYPAD_MAP.put('Q', '7'); KEYPAD_MAP.put('R', '7'); KEYPAD_MAP.put('S', '7');
        KEYPAD_MAP.put('T', '8'); KEYPAD_MAP.put('U', '8'); KEYPAD_MAP.put('V', '8');
        KEYPAD_MAP.put('W', '9'); KEYPAD_MAP.put('X', '9'); KEYPAD_MAP.put('Y', '9'); KEYPAD_MAP.put('Z', '9');
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a 10-digit phone number (e.g., 800888TEST): ");
        String input = sc.nextLine();
        try {
            String result = decodePhoneNumber(input);
            System.out.println("Decoded phone number: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String decodePhoneNumber(String input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }

        // Remove spaces and special characters except letters and digits
        String cleanInput = input.toUpperCase().replaceAll("[^A-Z0-9]", "");

        StringBuilder digits = new StringBuilder();

        for (char c : cleanInput.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.append(c);
            } else if (Character.isLetter(c)) {
                digits.append(KEYPAD_MAP.get(c));
            } else {
                throw new IllegalArgumentException("Invalid character detected: " + c);
            }
        }

        // Validate final length (must be 10 digits)
        if (digits.length() != 10) {
            throw new IllegalArgumentException("Invalid phone number length. Must be 10 digits.");
        }

        // Format: (XXX) XXX-XXXX
        return String.format("(%s) %s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6));
    }
}
