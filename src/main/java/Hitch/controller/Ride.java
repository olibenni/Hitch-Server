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
import java.util.logging.Logger;

/**
 * Created by olafurma on 6.2.2016.
 */
@Controller
@RequestMapping("/rides")
public class Ride {
  private Logger logger = Logger.getLogger(Ride.class.getName());
  private SQLHelper db = new SQLHelper();

  @RequestMapping(method= RequestMethod.GET)
  public @ResponseBody List<RidesDAO> fetchAllRides() throws IOException
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    logger.info("Incoming request for path /rides from: " + sessionId);
    return db.fetchRides();
  }

  @RequestMapping(value= "/newRide", method= RequestMethod.PUT)
  public @ResponseBody void handleNewRide(@RequestParam(value="from") String from, @RequestParam(value="to") String to) throws IOException, HitchError
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    logger.info("Incoming request for path /rides/newRide from: " + sessionId + ". With params from = " + from + " and to = " + to);
    int pickup;
    int dropOff;
    try {
      pickup = Integer.parseInt(from);
      dropOff = Integer.parseInt(to);
    } catch (Exception e) {
      throw new HitchError(Constants.NOT_AN_INTEGER, Level.WARNING, e);
    }

    db.insertRide(pickup, dropOff, sessionId);
  }

  @RequestMapping(value= "/cancelRide", method= RequestMethod.DELETE)
  public @ResponseBody void handleCancelRide()
  {
    String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    logger.info("Incoming request for path /rides/cancelRide from: " + sessionId);
    db.deleteRide(sessionId);
  }
}
