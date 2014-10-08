/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.help;

import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author maxmeffert
 */
public class RequestParameters {
    
    private static Map<String, String> parameterMap () {
        
       return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
    }
    
    public static String get (String key) {
        
        return parameterMap().get(key);
        
    }
    
    public static boolean has (String key) {
        
        return parameterMap().containsKey(key);
        
    }
    
}
