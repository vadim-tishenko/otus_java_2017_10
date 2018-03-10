package ru.cwl.otus.hw15.front;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.cwl.otus.hw15.app.AbstractAddressee;
import ru.cwl.otus.hw15.app.FrontendService;
import ru.cwl.otus.hw15.messageSystem.Address;
import ru.cwl.otus.hw15.messageSystem.MessageSystem;
import ru.cwl.otus.hw15.messages.CacheInfo;
import ru.cwl.otus.hw15.messages.CacheInfoRequestMessage;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vadim.tishenko
 * on 10.03.2018 15:34.
 */
public class FrontendServiceImpl extends AbstractAddressee implements FrontendService {
    private final AtomicInteger ID_GENERATOR;
    private final Map<Integer, AsyncContext> ctxMap;
    private final TemplateEngine templateEngine;

    public FrontendServiceImpl(Address address, MessageSystem messageSystem, TemplateEngine templateEngine) {
        super(address, messageSystem);
        this.templateEngine = templateEngine;
        this.ctxMap = new ConcurrentHashMap<>();
        ID_GENERATOR = new AtomicInteger();
    }

    @Override
    public void handleCacheRequest(AsyncContext asyncContext) {
        Integer ctxId = ID_GENERATOR.incrementAndGet();
        ctxMap.put(ctxId, asyncContext);
        CacheInfoRequestMessage requestMessage = new CacheInfoRequestMessage(getAddress(), new Address("cache"), ctxId);
        getMS().sendMessage(requestMessage);
    }

    @Override
    public void handleCacheResponse(int ctxId, CacheInfo cacheInfo) {

        AsyncContext asyncContext = ctxMap.get(ctxId);

        final PrintWriter writer;
        try {
            writer = asyncContext.getResponse().getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Context ctx = new Context();
        ctx.setVariable("cacheInfo", cacheInfo);

        templateEngine.process("cache", ctx, writer);
        asyncContext.complete();
        ctxMap.remove(ctxId);
    }

}
