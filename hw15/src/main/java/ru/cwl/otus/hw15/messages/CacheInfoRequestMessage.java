package ru.cwl.otus.hw15.messages;

import ru.cwl.otus.hw15.app.CacheService;
import ru.cwl.otus.hw15.app.MsgToCache;
import ru.cwl.otus.hw15.messageSystem.Address;

/**
 * Created by vadim.tishenko
 * on 09.03.2018 22:11.
 */
public class CacheInfoRequestMessage extends MsgToCache {
    private int ctxId;

    public CacheInfoRequestMessage(Address from, Address to, int ctxId) {
        super(from, to);
        this.ctxId = ctxId;
    }

    @Override
    public void exec(CacheService cacheService) {
        CacheInfo cacheInfo = new CacheInfo(cacheService.getCacheEngine());
        cacheService.getMS().sendMessage(new CacheInfoResponseMessage(getTo(), getFrom(), ctxId, cacheInfo));
    }
}
