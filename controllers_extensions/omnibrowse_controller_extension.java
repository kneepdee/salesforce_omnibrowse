public class OmniBrowseControllerExtension {

  public SObject record { get; private set; }
  private String externalId = 'Email';
  private User currentuser {get; set;}
  private OmniBrowseConfiguration__c omniBrowseConfiguration;

  public OmniBrowseControllerExtension(ApexPages.standardController std)
  {
    currentuser=new User();
    List<OmniBrowseConfiguration__c> omnibrowseConfigurations = [SELECT ModelName__c, ExternalIDFieldName__c, AppToken__c, ComponentHeight__c, ApiToken__c, SiteId__c, Region__C FROM OmniBrowseConfiguration__c WHERE Active__c = true];
    if (omnibrowseConfigurations.size() > 0){
      omniBrowseConfiguration = omnibrowseConfigurations[0];
      if (std.getId() != null) {
        String id = std.getId();
        String query = 'SELECT '+ omnibrowseConfiguration.ExternalIDFieldName__c + ' FROM '+ omnibrowseConfiguration.ModelName__c +' WHERE Id = :id';
        List<SObject> objs = Database.query(query);
        if (objs.size() > 0){
          record = objs[0];
          externalId = string.valueof(record.get(omnibrowseConfiguration.ExternalIDFieldName__c));
        }
      }
    }
  }

  public String getApiEndpoint() {
    return (omniBrowseConfiguration != null) ? SaleMoveEndpoints.apiEndpointForRegion(omniBrowseConfiguration.Region__c) : '';
  }

  public String getOmniBrowseEndpoint() {
    return (omniBrowseConfiguration != null) ? SaleMoveEndpoints.omniBrowseEndpointForRegion(omniBrowseConfiguration.Region__c) : '';
  }

  public String getApiToken() {
    return (omniBrowseConfiguration != null) ? omniBrowseConfiguration.ApiToken__c : '';
  }

  public String getAppToken() {
    return (omniBrowseConfiguration != null) ? omniBrowseConfiguration.AppToken__c : '';
  }

  public String getSiteId() {
    return (omniBrowseConfiguration != null) ? omniBrowseConfiguration.SiteId__c : '';
  }

  public String getExternalIdValue(){
    return externalId;
  }

  public String getOmniBrowseModelName(){
      return (omniBrowseConfiguration != null) ? omniBrowseConfiguration.ModelName__c : '';
  }
}