public class Setup {

  // * - Fields - *

  String rarity;
  int minQual, maxQual;
  int minPropAmt, maxPropAmt;
  final int minPropVal = 5;
  int maxPropVal;

  // * - Constructors - *

  public Setup(String rarity,
      int[] qualRange, int[] propAmtRange, int maxPropVal) {
    setRarity(rarity);
    setQualRange(qualRange);
    setPropAmtRange(propAmtRange);
    setMaxPropVal(maxPropVal);
  } // End of constructor

  // * - toString Methods - *

  @Override
  public String toString() {
    return "Rarity: " + rarity + ", Quality: " + minQual + "%-" + maxQual + "%\n" +
        "Property Amount: " + minPropAmt + "-" + maxPropAmt +
        ", Property Value: " + minPropVal + "%-" + maxPropVal + "%\n";
  } // End of String method

  /*
   * - Getters and Setters -
   */

  public int[] getQualRange() {
    return new int[] { minQual, maxQual };
  } // End of int[] method

  public void setQualRange(int[] qualRange) {
    setMinQual(qualRange[0]);
    setMaxQual(qualRange[1]);
  } // End of void method

  public int[] getPropAmtRange() {
    return new int[] { minPropAmt, maxPropAmt };
  } // End of int[] method

  public void setPropAmtRange(int[] propAmtRange) {
    setMinPropAmt(propAmtRange[0]);
    setMaxPropAmt(propAmtRange[1]);
  } // End of void method

  public int[] getPropValRange() {
    return new int[] { minPropVal, maxPropVal };
  } // End of int[] method

  // * - Auto-generated - *

  public String getRarity() {
    return rarity;
  }

  public void setRarity(String rarity) {
    this.rarity = rarity;
  }

  public int getMinQual() {
    return minQual;
  }

  public void setMinQual(int minQual) {
    this.minQual = minQual;
  }

  public int getMaxQual() {
    return maxQual;
  }

  public void setMaxQual(int maxQual) {
    this.maxQual = maxQual;
  }

  public int getMinPropAmt() {
    return minPropAmt;
  }

  public void setMinPropAmt(int minPropAmt) {
    this.minPropAmt = minPropAmt;
  }

  public int getMaxPropAmt() {
    return maxPropAmt;
  }

  public void setMaxPropAmt(int maxPropAmt) {
    this.maxPropAmt = maxPropAmt;
  }

  public int getMinPropVal() {
    return minPropVal;
  }

  public int getMaxPropVal() {
    return maxPropVal;
  }

  public void setMaxPropVal(int maxPropVal) {
    this.maxPropVal = maxPropVal;
  }

} // End of class