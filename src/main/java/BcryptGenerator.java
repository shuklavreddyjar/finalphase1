import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The type Bcrypt generator.
 */
public class BcryptGenerator {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "admin123";
        String hash = encoder.encode(password);

        System.out.println(hash);
    }
}
