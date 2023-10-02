package LCK.snowTaxi2.controller;

import LCK.snowTaxi2.domain.chat.MessageType;
import LCK.snowTaxi2.dto.ResultResponse;
import LCK.snowTaxi2.dto.chat.MessageRequestDto;
import LCK.snowTaxi2.dto.chat.MessageResponseDto;
import LCK.snowTaxi2.service.chat.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void send(MessageRequestDto messageRequestDto) {
        messageService.send(messageRequestDto);
    }

    @GetMapping("/chatroom/{roomId}")
    public ResultResponse getChats(@PathVariable long roomId) {
        List<MessageResponseDto> responseDto = messageService.getChats(roomId);

        return ResultResponse.builder()
                .code(HttpStatus.OK.value())
                .message("채팅 메시지 조회")
                .data(responseDto)
                .build();

    }
}
