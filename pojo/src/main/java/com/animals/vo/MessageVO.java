package com.animals.vo;

import lombok.Data;
import org.springframework.ai.chat.messages.Message;

@Data
public class MessageVO {
    private String content;
    private String role;

    public MessageVO(Message message)
    {
        switch (message.getMessageType())
        {
            case USER:
                this.role = "user";
                break;
            case ASSISTANT:
                this.role = "assistant";
                break;
            default:
                this.role = "unknown";
        }
        this.content = message.getText();

    }

}
