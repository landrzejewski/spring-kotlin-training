package pl.training

import org.springframework.stereotype.Service
import java.util.Collections.synchronizedMap

@Service
class ChatGlobalState {

    private val chatUsers: MutableMap<String, ChatUser> = synchronizedMap(HashMap())

    fun put(key: String, chatUser: ChatUser) {
        chatUsers[key] = chatUser
    }

    fun get(key: String): ChatUser? {
        return chatUsers[key]
    }

    fun remove(key: String) {
        chatUsers.remove(key)
    }

    fun getAll(): Collection<ChatUser> {
        return chatUsers.values
    }

}
