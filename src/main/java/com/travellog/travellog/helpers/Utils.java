package com.travellog.travellog.helpers;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    private Utils(){};

    public static String sanitizedString(String s){
        try{
            PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
            String safeHTML = policy.sanitize(s);
            System.out.println("safe html"+safeHTML);
            return safeHTML.trim();
        } catch(Exception e){
            System.out.println(e);
        }
        return s;
    }
}
