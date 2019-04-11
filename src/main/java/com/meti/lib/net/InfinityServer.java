package com.meti.lib.net;

import com.meti.chat.Chat;
import com.meti.chat.ChatRequest;
import com.meti.chat.ChatUpdate;
import com.meti.chat.Message;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class InfinityServer extends Server {
    private final Chat chat = new Chat();

    public InfinityServer(ServerSocket serverSocket, ExecutorService service) {
        super(serverSocket, service);
        this.handlerFactory = new InfinityHandlerFactory();
    }

    private class InfinityHandlerFactory implements Function<Client, TokenHandler> {
        @Override
        public TokenHandler apply(Client client) {
            TokenHandler handler = new TokenHandler(client);
            handler.put(new TypePredicate<>(Message.class),
                    ((Function<Message, OKResponse>) message -> {
                        chat.add(message);
                        return new OKResponse("Message received successfully.");
                    }).compose(new TypeFunction<>(Message.class))
            );

            handler.put(new TypePredicate<>(ChatRequest.class),
                    ((Function<ChatRequest, ChatUpdate>) chatRequest -> new ChatUpdate(chat.poll()))
                            .compose(new TypeFunction<>(ChatRequest.class))
            );
            return handler;
        }
    }
}
