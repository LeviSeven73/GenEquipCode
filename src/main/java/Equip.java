public class Equip implements Comparable<Equip> {

  // * - Fields - *

  int experience, minecoins, gems, tickets, exclusive;

  // * - Constructors - *

  public Equip(int[] properties) {
    setProperties(properties);
  } // End of constructor

  // * - Override Methods - *

  // Converts property values into an equipment code
  @Override
  public String toString() {
    int[] properties = getProperties();
    String code = "";

    for (int prop : properties) {
      switch (prop) {
        // Values of 10 and 20 has specific chars
        case 10:
          code += "X";
          break;
        case 20:
          code += "P";
          break;
        // Converts one-digit values to num itself, rest to A-I
        default:
          code += (prop < 10 ? prop : (char) (prop + 54) + "");
      } // End of switch case
    } // End of for loop

    return code;
  } // End of String method

  // Compares the equipment codes
  @Override
  public int compareTo(Equip other) {
    int[] thisProp = getProperties(), otherProp = other.getProperties();

    for (int i = 0; i < thisProp.length; i++) {
      if (thisProp[i] != otherProp[i])
        return Integer.compare(thisProp[i], otherProp[i]);
    } // End of for loop
    return 0;
  } // End of int method

  /*
   * - Getters and Setters -
   */

  public int[] getProperties() {
    return new int[] {
        experience, minecoins, gems, tickets, exclusive };
  } // End of int[] method

  public void setProperties(int[] properties) {
    setExperience(properties[0]);
    setMinecoins(properties[1]);
    setGems(properties[2]);
    setTickets(properties[3]);
    setExclusive(properties[4]);
  } // End of void method

  // * - Auto-generated - *

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public int getMinecoins() {
    return minecoins;
  }

  public void setMinecoins(int minecoins) {
    this.minecoins = minecoins;
  }

  public int getGems() {
    return gems;
  }

  public void setGems(int gems) {
    this.gems = gems;
  }

  public int getTickets() {
    return tickets;
  }

  public void setTickets(int tickets) {
    this.tickets = tickets;
  }

  public int getExclusive() {
    return exclusive;
  }

  public void setExclusive(int exclusive) {
    this.exclusive = exclusive;
  }

} // End of class