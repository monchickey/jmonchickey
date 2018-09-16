package com.monchickey.tests;

import com.monchickey.dataprocess.PBKDF2Hash;

/**
 * monchickey 测试类
 * @author monchickey
 *
 */

public class PDKDF2HashTest {
    public static void main(String[] args)
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
                String password = ""+i;
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
            // String hash2 = createHash("hello_admin");
            // System.out.println(hash2);
            if(PBKDF2Hash.validatePassword("hello_admin", "1000:92061d8f17a45c7001c47b0d9c19d6bd05f6d103b37edac5:b5900b928ef39c218f0259269d2b64e79ee788b5f976fec3")) {
                System.out.println("ok!");
            } else {
                System.out.println("error!");
            }
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: " + ex);
        }
    }
}
