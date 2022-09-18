package com.ssfw.auth.util;

/**
 * UserAgent分析程序
 * @author beets
 */
public class UserAgentAnalyzer {

    public final static nl.basjes.parse.useragent.UserAgentAnalyzer UUA = nl.basjes.parse.useragent.UserAgentAnalyzer
            .newBuilder().useJava8CompatibleCaching()
            .withCache(1000)
            .build();

}
