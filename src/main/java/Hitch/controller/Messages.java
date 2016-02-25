package Hitch.controller;

import Hitch.persistance.SQLHelper;
import Hitch.util.Constants;
import Hitch.util.HitchError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by olafurma on 22.2.2016.
 */
@Controller
@RequestMapping("/messages")
public class Messages {
  private SQLHelper db = new SQLHelper();

  @RequestMapping(method= RequestMethod.GET)
  public List<String> fetchMessagesForUser()
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    return db.fetchMessages(sessionId);
  }

  @RequestMapping(value= "/createMessage", method= RequestMethod.POST)
  public @ResponseBody void createMessage(@RequestParam(value="message") String message, @RequestParam(value="id") String id) throws HitchError
  {
    int passengerId;

    try {
      passengerId = Integer.parseInt(id);
    } catch (Exception e) {
      throw new HitchError(Constants.NOT_AN_INTEGER, Level.WARNING, e);
    }
    db.insertMessage(message, passengerId);
  }
}
