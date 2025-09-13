public enum Rarity {
  COMMON(new Setup("Common",
      new int[] { 10, 19 }, new int[] { 2, 2 }, 10)),
  RARE(new Setup("Rare",
      new int[] { 20, 39 }, new int[] { 2, 4 }, 15)),
  UNIQUE(new Setup("Unique",
      new int[] { 40, 69 }, new int[] { 3, 5 }, 20)),
  LEGENDARY(new Setup("Legendary",
      new int[] { 70, 99 }, new int[] { 4, 5 }, 20));

  private Setup setup;

  private Rarity(Setup setup) {
    this.setup = setup;
  }

  public Setup getSetup() {
    return setup;
  }

} // End of enum