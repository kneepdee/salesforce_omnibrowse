public class OmniBrowseControllerExtension {

  public SObject record { get; private set; }
  private String externalId = 'Email';
  private String modelName = 'Contact';
  private String externalIdFieldName = 'Email';
  private User currentuser {get; set;}

  public OmniBrowseControllerExtension(ApexPages.standardController std)
  {
    currentuser=new User();
    currentuser=[Select ExternalIDFieldName__c,OmniBrowse_Model__c from User where Id=:userinfo.getuserId()];
    externalIdFieldName = string.valueof(currentUser.get('ExternalIDFieldName__c'));
    modelName = string.valueof(currentUser.get('OmniBrowse_Model__c'));
    if (std.getId() != null) {
      String id = std.getId();
      String query = 'SELECT '+ externalIdFieldName + ' FROM '+ modelName +' WHERE Id = :id';
      List<SObject> objs = Database.query(query);
      if (objs.size() > 0){
        record = objs[0];
        externalId = string.valueof(record.get(externalIdFieldName));
      }
    }
  }

  public String getExternalIdValue(){
    return externalId;
  }

  public String getOmniBrowseModelName(){
    return modelName;
  }
}
