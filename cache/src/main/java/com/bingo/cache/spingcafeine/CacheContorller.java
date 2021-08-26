package com.bingo.cache.spingcafeine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheContorller {
    @Autowired
    CacheManager manager;

    @GetMapping("/showCacheManagers")
    public Collection showCacheManagers(){
        Collection<String> cacheNames = manager.getCacheNames();
        return cacheNames;
    }

    @GetMapping("showCacheManagers/{cacheName}/{key}")
    public Object showCache(@PathVariable("cacheName") String cacheName,@PathVariable("key")String key){
        CaffeineCache cache = (CaffeineCache)manager.getCache(cacheName);
        SimpleValueWrapper obj = (SimpleValueWrapper)cache.get(Integer.parseInt(key));
        return obj.get();
    }
}
