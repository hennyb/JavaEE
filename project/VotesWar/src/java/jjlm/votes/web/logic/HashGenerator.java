package jjlm.votes.web.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Generator for salted SHA-256 hashes
 * 
 */
public class HashGenerator {

    /**
     * Hash salt, DO NOT CHANGE unless you want to drop all users;
     */
    private static final String salt = "0acaadc6-6ed5-43f3-b4fb-1e2ff915097a";
    
    /**
     * Hash algorithm, DO NOT CHANGE unless you want to drop all users;
     */
    private static final String msgDigestAlgorithm = "SHA-256";
    
    
    /**
     * Internal hash generator instance
     */
    private final MessageDigest msgDigestInstance;
    
    /**
     * Constructs new HashGenerator
     * @throws NoSuchAlgorithmException 
     */
    public HashGenerator () 
            throws NoSuchAlgorithmException {
        
        msgDigestInstance = MessageDigest.getInstance(msgDigestAlgorithm);
        
    }
    
    /**
     * Generates salted SHA-256 hash in hex encoding for input sting
     * @param str
     * @return 
     */
    public String generateHash (String str) {
        
        String hash;
        byte[] bytes;
        
        //DO NOT CHANGE this line unless you want to drop all users
        bytes = (salt + str + salt).getBytes();
        
        msgDigestInstance.update(bytes);
        
        bytes = msgDigestInstance.digest();
        
        hash = "";
        
        for (byte b : bytes) {
        
            hash += String.format("%02X", b) ;
        
        }
        
        return hash;
        
    }
    
}
