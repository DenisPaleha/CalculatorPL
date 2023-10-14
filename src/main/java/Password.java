import java.util.Scanner;

public class Password {

    private String rawPass;

    /**
     * function asks the user to enter a password when the program is first launched.
     */
    public int newPassword(Scanner scanner) {
        System.out.println(ConstantLibrary.PASSWORD_TEXT_HALLO_ENG);
        System.out.println(ConstantLibrary.PASSWORD_TEXT_RULES_ENG);

        passwordScanner(scanner);

        int hashWord = rawPass.hashCode();

        System.out.println("Your password " + rawPass + " has been saved...");
        return hashWord;
    }

    private boolean containsNonEnglishChars(String str) { // Возвращает false, если строка содержит только английские буквы и цифры
        return !str.matches(".*[^a-zA-Z0-9].*");
    }

    private boolean checkCharacterCount(String str) { // Возвращает true если пароль короче 5 символов
        return str.length() < 5;
    }

    private void passwordScanner(Scanner passwordScanner) {
//        Scanner passwordScanner = new Scanner(System.in).useLocale(Locale.ENGLISH);
        while (passwordScanner.hasNextLine()) { // scan input line
            String str = passwordScanner.nextLine(); // Save user input to the variable line
            if (!checkCharacterCount(str) && containsNonEnglishChars(str)) {
                this.rawPass = str;
                break;

            } else {
                System.out.println(ConstantLibrary.PASSWORD_TEXT_RULES_ENG);
            }
        }
//        passwordScanner.close();                                                            //  +++???+++
    }

    public void checkPassword(State state, Scanner passwordScanner) {
        System.out.println("Insert your password");
//        Scanner passwordScanner = new Scanner(System.in).useLocale(Locale.ENGLISH);
        while (passwordScanner.hasNextLine()) { // scan input line
            String password = passwordScanner.nextLine();
            if (state.isPasswordCorrect(password)) {
                System.out.println("Password accepted");
                break;
            } else {
                System.out.println("Wrong password entered!");
            }
        }
//        passwordScanner.close();                                                            //  +++???+++
    }

}
