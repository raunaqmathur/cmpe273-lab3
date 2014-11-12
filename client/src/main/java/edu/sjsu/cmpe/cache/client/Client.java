package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.Hashing;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");

        String[] values = new String[] {"a","b","c","d","e","f","g","h","i","j"};

        ArrayList<CacheServiceInterface> servers = new ArrayList<CacheServiceInterface>();

        servers.add(cache1);
        servers.add(cache2);
        servers.add(cache3);

        int bucket=0;
        //code for putting values
        for(int k = 1; k <= 10; k++) {
            bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(k)), servers.size());
            System.out.println("Routing value "+ values[k-1]  + " to server number: " + (bucket+1));
            servers.get(bucket).put(k,values[k-1]);
        }

        //code for getting values
        for(int k = 1; k <= 10; k++) {
            bucket = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(k)), servers.size());
            System.out.println("fetching value "+ values[k-1]  + " from server number: " + (bucket+1));
            System.out.println(servers.get(bucket).get(k));
        }



        System.out.println("Existing Cache Client...");
    }

}
