package ru.cwl.otus.hw15.cache;

import ru.cwl.otus.hw11.CacheEngine;
import ru.cwl.otus.hw15.messageSystem.Address;
import ru.cwl.otus.hw15.messageSystem.MessageSystem;
import ru.cwl.otus.hw15.system.AbstractAddressee;
import ru.cwl.otus.hw15.system.CacheService;

/**
 * Created by vadim.tishenko
 * on 10.03.2018 18:41.
 */
public class CacheServiceImpl extends AbstractAddressee implements CacheService {
    private final CacheEngine cacheService;

    public CacheServiceImpl(Address address, MessageSystem messageSystem, CacheEngine cacheService) {
        super(address, messageSystem);
        this.cacheService = cacheService;
    }

    @Override
    public CacheEngine getCacheEngine() {
        return cacheService;
    }


}
