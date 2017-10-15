package com.github.rahmathan.google.commute.provider;

import com.github.rahmnathan.google.commute.provider.GoogleCommuteProvider;
import org.junit.Test;

public class GoogleCommuteProviderTest {
    private final GoogleCommuteProvider commuteProvider = new GoogleCommuteProvider("AIzaSyCI8h8-IBhbARw6gl5DhqXOAWsZGCVmI7o");

    @Test
    public void getCommuteContentTest(){
        String commuteTime = commuteProvider.getCommuteTime("44.93540369999999,-93.3341026", "44.80669119999999,-93.35580879999998");
        System.out.println(commuteTime);
    }
}
