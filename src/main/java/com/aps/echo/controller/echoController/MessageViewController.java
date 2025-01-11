package com.aps.echo.controller.echoController;

import com.aps.echo.folders.Folder;
import com.aps.echo.folders.FolderRepository;
import com.aps.echo.folders.FolderService;
import com.aps.echo.message.Message;
import com.aps.echo.message.MessageRepository;
import com.aps.echo.messagelist.MessageListItemRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MessageViewController {

  @Autowired private FolderRepository folderRepository;

  @Autowired private FolderService folderService;

  @Autowired private MessageRepository messageRepository;

  @GetMapping(value = "/messages/{message_id}")
  public String messageView(@PathVariable UUID message_id, @AuthenticationPrincipal OAuth2User principal, Model model) {
    if (principal == null || !StringUtils.hasText(principal.getAttribute("name"))) {
      return "index";
    }
    // Fetch Folders
    String userId = principal.getAttribute("login");
    List<Folder> userFolders = folderRepository.findAllById(userId);
    model.addAttribute("userFolders", userFolders);
    // Fetch Default Folders
    List<Folder> defaultFolders = folderService.fetchDefaultFolders(userId);
    model.addAttribute("defaultFolders", defaultFolders);
    //fetch a particular message
    Optional<Message> optionalMessage =  messageRepository.findById(message_id);
    if(optionalMessage.isEmpty()){
      return "echo-page";
    }
    String toIds = String.join(", ", optionalMessage.get().getTo());
    model.addAttribute("message", optionalMessage.get());
    model.addAttribute("toIds", toIds);
    System.out.println(model);

    return "message-page";
  }
}
