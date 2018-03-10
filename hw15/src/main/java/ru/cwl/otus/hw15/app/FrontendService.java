package ru.cwl.otus.hw15.app;


import ru.cwl.otus.hw15.messageSystem.Addressee;
import ru.cwl.otus.hw15.messages.CacheInfo;

import javax.servlet.AsyncContext;

/**
 * Created by tully.
 */
public interface FrontendService extends Addressee {

    void handleCacheRequest(AsyncContext asyncContext);

    void handleCacheResponse(int ctxId, CacheInfo ci);

}

