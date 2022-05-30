public class Password {
    private static int workload = 12;
    /**
     * @param password_plaintext
     * @return Hashed password String
     * Fill in a password and this will make it a hashed password.
     * You need this to login to the application
     */
    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return (hashed_password);
    }

}
