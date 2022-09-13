package kopo.poly.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.ChatDTO;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequestMapping(value="/chat")
@ServerEndpoint(value = "/ws/{roomName}/{userName}")
public class ChatController {

    private static Set<Session> clients = Collections.synchronizedSet(new LinkedHashSet<>());

    private static Map<String, String> roomInfo = Collections.synchronizedMap(new LinkedHashMap<>());



    @RequestMapping(value = "intro")
    public String intro() throws Exception {
        return "/chat/intro";
    }

    @RequestMapping(value = "room")
    public String room() throws Exception {
        return "/chat/chatroom";
    }

    @RequestMapping(value = "roomList")
    @ResponseBody
    public Set<String> roomList() throws Exception {
        log.info(this.getClass().getName() + ".roomList Start!");

        log.info(this.getClass().getName() + ".roomList End!");

        return roomInfo.keySet();
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("roomName") String roomName,
                       @PathParam("userName") String userName) throws Exception {

        log.info(this.getClass().getName() + ".roomList Start!");

        log.info("session : " + session);
        log.info("session id : " + session.getId());
        log.info("roomName : " + roomName);
        log.info("userName : " + userName);

        String roomNameHash = EncryptUtil.encHashSHA256(roomName);

        session.getUserProperties().putIfAbsent("roomName", roomNameHash);
        session.getUserProperties().putIfAbsent("userName", userName);

        log.info("session roomName : " + session.getUserProperties().get("roomName"));

        clients.stream().forEach(s -> {
            log.info("s.room : " + s.getUserProperties().get("roomName"));
            log.info("s.userName : " + s.getUserProperties().get("userName"));

            if(roomNameHash.equals(s.getUserProperties().get("roomName"))) {

                try {

                    ChatDTO cDTO = new ChatDTO();
                    cDTO.setName("관리자");
                    cDTO.setMsg(userName + "님이" + roomName + "채팅방에 입장하셨습니다.");
                    cDTO.setDate(DateUtil.getDateTime("yyyyMMdd hh:mm:ss"));

                    String json = new ObjectMapper().writeValueAsString(cDTO);
                    log.info("json : " + json);

                    s.getBasicRemote().sendText(json);

                    cDTO = null;

                } catch (IOException e) {
                    log.info("Error :" + e);
                }
            }

            if (!clients.contains(session)) {
                clients.add(session);
                roomInfo.put(roomName, roomNameHash);
                log.info("session open : " + session);
            }

            log.info(this.getClass().getName() + ".onOpen End!");

        });
    }




}

