package com.example.footstep.controller;

import com.example.footstep.domain.dto.chat.ChatRoomDto;
import com.example.footstep.domain.repository.ChatRepository;
import com.example.footstep.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatRoomController {
    private final ChatRepository chatRepository;
    private final ChatService chatService;

    @GetMapping("/")
    public String joinChatRoom(Model model){
        model.addAttribute("list", chatRepository.findAll());
        log.info("SHOW ALL ChatList {}", chatRepository.findAll());
        return "roomlist";
    }
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam String name, RedirectAttributes rttr) {
        ChatRoomDto room = chatService.createChatRoom(name);
        log.info("CREATE Chat Room {}", room);
        rttr.addFlashAttribute("roomName", room);
        return "redirect:/";
    }
    @GetMapping("/chat/room")
    public String roomDetail(Model model, Long roomId){

        log.info("roomId {}", roomId);
        model.addAttribute("room", chatRepository.findChatRoomDtoByRoomId(roomId));
        return "chatroom";
    }

}
