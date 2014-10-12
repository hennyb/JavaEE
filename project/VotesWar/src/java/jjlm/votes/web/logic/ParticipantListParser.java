/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author maxmeffert
 */
public class ParticipantListParser {

    private final static String LINE_SEPERATOR = "\\r?\\n";
    private final static String CHUNK_SEPERATOR = ",";
    private final static String EMAIL_REGEXP = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public List<String> parse(String str) {

        List<String> result = new ArrayList<>();

        String[] lines = str.split(LINE_SEPERATOR);

        for (String line : lines) {

            List<String> chunks = Arrays.asList(line.split(CHUNK_SEPERATOR));

            for (String chunk : chunks) {
                chunk = chunk.trim();
                if (chunk.matches(EMAIL_REGEXP)) {
                    result.add(chunk);
                }
            }
        }

        return result;

    }
    
    public static boolean isValidEmailAddress(String email){
        return email.matches(EMAIL_REGEXP);
    }

}
