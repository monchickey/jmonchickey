import com.monchickey.security.PBKDF2Hash;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * monchickey 测试类
 * @author monchickey
 *
 */

public class SecurityTest {
    @Test
    public void testPDKDF2Hash()
    {
        try
        {
            // Print out 10 hashes
            for(int i = 0; i < 10; i++)
                System.out.println(PBKDF2Hash.createHash("p\r\nassw0Rd!"));
 
            // Test password validation
            boolean failure = false;
            System.out.println("Running tests...");
            for(int i = 0; i < 100; i++)
            {
                String password = System.currentTimeMillis() + "-" + i;
                String hash = PBKDF2Hash.createHash(password);
                String secondHash = PBKDF2Hash.createHash(password);
                if(hash.equals(secondHash)) {
                    System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
                    failure = true;
                }
                String wrongPassword = ""+(i+1);
                if(PBKDF2Hash.validatePassword(wrongPassword, hash)) {
                    System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
                    failure = true;
                }
                if(!PBKDF2Hash.validatePassword(password, hash)) {
                    System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
                    failure = true;
                }
            }
            if(failure)
                System.out.println("TESTS FAILED!");
            else
                System.out.println("TESTS PASSED!");

            Assertions.assertTrue(PBKDF2Hash.validatePassword("hello_admin", "1000:92061d8f17a45c7001c47b0d9c19d6bd05f6d103b37edac5:b5900b928ef39c218f0259269d2b64e79ee788b5f976fec3"));
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex);
        }
    }
}
