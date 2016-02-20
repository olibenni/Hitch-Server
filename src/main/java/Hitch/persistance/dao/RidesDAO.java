package Hitch.persistance.dao;

import Hitch.controller.Ride;

/**
 * Created by olafurma on 20.2.2016.
 */
public class RidesDAO {
  private int pickup;
  private int dropOff;
  private String sessionId;

  public RidesDAO(){};

  public RidesDAO(int pickup, int dropOff, String sessionId)
  {
    this.pickup = pickup;
    this.dropOff = dropOff;
    this.sessionId = sessionId;
  }

  public int getPickup() {
    return pickup;
  }

  public void setPickup(int pickup) {
    this.pickup = pickup;
  }

  public int getDropOff() {
    return this.dropOff;
  }

  public void setDropOff(int dropOff) {
    this.dropOff = dropOff;
  }

  public String getSessionId() {
    return this.sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
}
