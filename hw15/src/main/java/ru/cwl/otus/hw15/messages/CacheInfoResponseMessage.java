package ru.cwl.otus.hw15.messages;

import ru.cwl.otus.hw15.app.FrontendService;
import ru.cwl.otus.hw15.app.MsgToFrontend;
import ru.cwl.otus.hw15.messageSystem.Address;

/**
 * Created by vadim.tishenko
 * on 09.03.2018 22:12.
 */
public class CacheInfoResponseMessage extends MsgToFrontend {
    private int ctxId;
    private CacheInfo cacheInfo;

    public CacheInfoResponseMessage(Address from, Address to, int ctxId, CacheInfo cacheInfo) {
        super(from, to);
        this.ctxId = ctxId;
        this.cacheInfo = cacheInfo;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.handleCacheResponse(ctxId, cacheInfo);
    }
}
