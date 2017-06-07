public class OmniBrowseControllerExtension {

  public Contact record { get; private set; }
  private String externalId = '';
  private String externalIdFieldName = 'Email';
  private User currentuser {get; set;}

  public OmniBrowseControllerExtension(ApexPages.standardController std)
  {
    currentuser=new User();
    currentuser=[Select ExternalIDFieldName__c from User where Id=:userinfo.getuserId()];
    externalIdFieldName = string.valueof(currentUser.get('ExternalIDFieldName__c'));
    if (std.getId() != null) {
      String id = std.getId();
      String query = 'SELECT '+ externalIdFieldName + ' FROM Contact WHERE Id = :id';
      List<Contact> objs = Database.query(query);
      if (objs.size() > 0){
        record = objs[0];
        externalId = string.valueof(record.get(externalIdFieldName));
      }
    }
  }

  public String getExternalIdValue(){
    return externalId;
  }
}