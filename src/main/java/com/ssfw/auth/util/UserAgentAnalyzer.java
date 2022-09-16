package com.ssfw.auth.util;

public class UserAgentAnalyzer {

    public final static nl.basjes.parse.useragent.UserAgentAnalyzer UUA = nl.basjes.parse.useragent.UserAgentAnalyzer
            .newBuilder().useJava8CompatibleCaching()
            .withCache(1000)
            .build();

}
