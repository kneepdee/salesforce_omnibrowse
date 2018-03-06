global class CreateOperators {
  @InvocableMethod(label='Create Operators')
  global static List<Operator> createOperators(List<User> users) {
    List<Operator> results = new List<Operator>();
    for (User user : users) {
      User u = [SELECT Name, Email FROM User WHERE Id = :user.Id];
      Operator operator = createOperator(u);
      results.add(operator);
    }
    return results;
  }

  public static OmniBrowseConfiguration__c getConfiguration() {
    List<OmniBrowseConfiguration__c> omnibrowseConfigurations = [SELECT ApiToken__c, SiteId__c, Region__c FROM OmniBrowseConfiguration__c WHERE Active__c = true];
    OmniBrowseConfiguration__c activeConfiguration = omnibrowseConfigurations[0];
    return activeConfiguration;
  }

  public static Operator createOperator(User user) {
    Operator operator = new Operator();
    OmniBrowseConfiguration__c activeConfiguration = getConfiguration();

    HttpRequest req = new HttpRequest();
    String endpoint = SaleMoveEndpoints.apiEndpointForRegion(activeConfiguration.Region__c) + '/operators';
    req.setEndpoint(endpoint);
    req.setMethod('POST');

    String body =
      '{"name": "'+ user.Name +'",' +
      '"email": "'+ user.Email +'",' +
      '"password": "password",' +
      '"password_confirmation": "password",' +
      '"phone": "+19174743334",' +
      '"assignments": [{"site_id": "'+ activeConfiguration.SiteId__c +'"}]}';

    String token = 'Token '+activeConfiguration.ApiToken__c;
    req.setHeader('Authorization', token);
    req.setHeader('Accept', 'application/vnd.salemove.v1+json');
    req.setHeader('Content-Type', 'application/json');
    req.setBody(body);
    System.debug(Logginglevel.INFO,body);

    Http http = new Http();
    HTTPResponse res = http.send(req);
    String responseBody = res.getBody();
    JSONParser parser = JSON.createParser(responseBody);
    String operatorId='';
    while (parser.nexttoken() != null) {
      if ((parser.getCurrentToken()  == JSONToken.FIELD_NAME) && (parser.getText() == 'id')){
        parser.nextToken();
        operatorId = parser.getText();
        System.debug(Logginglevel.INFO, operatorId);
      }
    }
    operator.operatorId = operatorId;
    return operator;
  }

  global class Operator {
    @InvocableVariable
    global String operatorId;
  }
}