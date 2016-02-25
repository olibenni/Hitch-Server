package Hitch.controller;

import Hitch.persistance.SQLHelper;
import Hitch.persistance.dao.RidesDAO;
import Hitch.util.Constants;
import Hitch.util.HitchError;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by olafurma on 6.2.2016.
 */
@Controller
@RequestMapping("/rides")
public class Ride {
  private SQLHelper db = new SQLHelper();

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody List<RidesDAO> fetchAllRides() throws IOException
  {
    return db.fetchRides();
  }

  @RequestMapping(value= "/newRide", method= RequestMethod.PUT)
  public @ResponseBody void handleNewRide(@RequestParam(value="from") String from, @RequestParam(value="to") String to) throws IOException, HitchError
  {
    int pickup;
    int dropOff;
    try {
      pickup = Integer.parseInt(from);
      dropOff = Integer.parseInt(to);
    } catch (Exception e) {
      throw new HitchError(Constants.NOT_AN_INTEGER, Level.WARNING, e);
    }

    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    System.out.println(sessionId);

    db.insertRide(pickup, dropOff, sessionId);
  }

  @RequestMapping(value= "/cancelRide", method= RequestMethod.DELETE)
  public @ResponseBody void handleCancelRide()
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    db.deleteRide(sessionId);
  }

  @RequestMapping(value= "/messages", method= RequestMethod.GET)
  public @ResponseBody List<String> fetchMessagesForUser()
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    return db.fetchMessages(sessionId);
  }

  @RequestMapping(value= "/createMessage", method= RequestMethod.POST)
  public @ResponseBody void createMessage(@RequestParam(value="message") String message)
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
//    db.insertMessage(message, sessionId);
  }
}
