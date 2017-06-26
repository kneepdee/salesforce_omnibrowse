public class OmniBrowseSetupExtension {
  public Integer size;
  ApexPages.StandardSetController setCon;

  public OmniBrowseSetupExtension(ApexPages.StandardSetController controller) {
    setCon = controller;
    size = [SELECT COUNT() FROM User];
    if (size > 1000) {
        size = 1000;
    }
    setCon.setPageSize(size);
  }

  public Integer getNumerOfOperators() {
    return size;
  }
}