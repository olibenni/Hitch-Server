package Hitch.persistance.dao;

/**
 * Created by olafurma on 20.2.2016.
 */
public class RidesDAO {
  private int pickup;
  private int dropOff;
  private int id;

  public RidesDAO(){};

  public RidesDAO(int pickup, int dropOff, int id)
  {
    this.pickup = pickup;
    this.dropOff = dropOff;
    this.id = id;
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

  public int getId() {
    return this.id;
  }

  public void getId(int id) {
    this.id = id;
  }
}
