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
    
    public List<String> parse (String str) {
        
        List<String> result = new ArrayList<String>();
        
        String[] lines = str.split(LINE_SEPERATOR);
        
        for (String line : lines) {
            
            List<String> chunks = Arrays.asList(line.split(CHUNK_SEPERATOR));
            
            result.addAll(chunks);
            
        }
        
        return result;
        
    }
    
}
