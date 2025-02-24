package com.aps.echo.controller.echoController;

import com.aps.echo.folders.Folder;
import com.aps.echo.folders.FolderRepository;
import com.aps.echo.folders.FolderService;
import com.aps.echo.message.Message;
import com.aps.echo.message.MessageRepository;
import com.aps.echo.messagelist.MessageListItem;
import com.aps.echo.messagelist.MessageListItemRepository;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EchoController {

  @Autowired private FolderRepository folderRepository;

  @Autowired private FolderService folderService;

  @Autowired private MessageListItemRepository messageListItemRepository;


  @GetMapping(value = "/")
  public String homePage(
      @RequestParam(required = false) String folder,
      @AuthenticationPrincipal OAuth2User principal,
      Model model) {

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

    // fetch messages
    if (!StringUtils.hasText(folder)) {
      folder = "Inbox";
    }
    model.addAttribute("folderName", folder);
    List<MessageListItem> messageList =
        messageListItemRepository.findAllByKey_IdAndKey_Label(userId, folder);
    model.addAttribute("messageList", messageList);
    // Fetch Received ago
    PrettyTime prettyTime = new PrettyTime();
    messageList.stream()
        .forEach(
            messageItem -> {
              UUID timeUUID = messageItem.getKey().getTimeUUID();
              Date messageDateTime = new Date(Uuids.unixTimestamp(timeUUID));
              messageItem.setReceivedAgo(prettyTime.format(messageDateTime));
            });

    return "echo-page";
  }
}
