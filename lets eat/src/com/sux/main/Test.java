package com.sux.main;

import com.sux.utility.Encoder;

/**
 * Created by Shorty on 4/29/2014.
 */
public class Test {
    public static void main(String args[]){
        String str1 = "nogrow315@aol.com";
        String str2 = "noman516";
        String str7 = "a@b.c";
        String str8 = "abc@dom.com";

        String str3 = "qwerty";
        String str4 = "short";
        String str5 = "7This5%";
        String str6 = "This5%";

        System.out.println("Reg Ex Test ==========");
        System.out.println("Is " + str1 + " a valid email? " + Encoder.regex(str1, Encoder.EMAIL_PATTERN));
        System.out.println("Is " + str2 + " a valid email? " + Encoder.regex(str2, Encoder.EMAIL_PATTERN));
        System.out.println("Is " + str7 + " a valid email? " + Encoder.regex(str7, Encoder.EMAIL_PATTERN));
        System.out.println("Is " + str8 + " a valid email? " + Encoder.regex(str8, Encoder.EMAIL_PATTERN));

        System.out.println("Is " + str3 + " a valid password? " + Encoder.regex(str3, Encoder.PASSWORD_PATTERN));
        System.out.println("Is " + str4 + " a valid password? " + Encoder.regex(str4, Encoder.PASSWORD_PATTERN));
        System.out.println("Is " + str5 + " a valid password? " + Encoder.regex(str5, Encoder.PASSWORD_PATTERN));
        System.out.println("Is " + str6 + " a valid password? " + Encoder.regex(str6, Encoder.PASSWORD_PATTERN));
    }
}
