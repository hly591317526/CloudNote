package org.hly.cloudnote.controller.user;

import javax.annotation.Resource;

import org.hly.cloudnote.service.UserService;
import org.hly.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/user")
@Controller
public class UserFriendController {
  @Resource
  private UserService userService;

  @RequestMapping("/loadfriends.do")
  @ResponseBody
  public NoteResult execute(String userId) {
    NoteResult result = userService.loadFriends(userId);
    return result;
  }

  @RequestMapping("/addfriend.do")
  @ResponseBody
  public NoteResult execute1(String userId, String name) {
    NoteResult result = userService.addFriend(userId, name);
    return result;
  }

  @RequestMapping("/deletefriend.do")
  @ResponseBody
  public NoteResult execute2(String userId, String friendId) {
    NoteResult result = userService.deleteFriend(userId, friendId);
    return result;
  }

  @RequestMapping("/loadfriendfile.do")
  @ResponseBody
  public NoteResult execute3(String friendId) {
    NoteResult result = userService.loadFriendFile(friendId);
    return result;
  }

}
