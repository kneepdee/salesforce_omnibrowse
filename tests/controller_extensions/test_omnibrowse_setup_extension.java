@isTest
private class TestOmniBrowseSetupExtension {

  static testMethod void testSetPageSizeBasedOnNumberOfOperators(){
    final String contactEmail='test@omnibrowse.com';
    final String contactLastName = 'LastName';

    User user = new User(
      ProfileId = [SELECT Id FROM Profile WHERE Name = 'Standard User' LIMIT 1].Id,
      LastName = 'Last',
      Email = 'user@omnibrowse.com',
      Username = 'user@omnibrowse.com' + System.currentTimeMillis(),
      CompanyName = 'SaleMove',
      Title = 'title',
      Alias = 'alias',
      TimeZoneSidKey = 'America/New_York',
      EmailEncodingKey = 'UTF-8',
      LanguageLocaleKey = 'en_US',
      LocaleSidKey = 'en_US',
      ExternalIDFieldName__c = 'Email'
    );

    insert user;

    System.runas(user)
    {
      Test.startTest();
      List<user> operatorList = [SELECT Name FROM User];
      ApexPages.StandardSetController contactController = new ApexPages.StandardSetController(operatorList);
      OmniBrowseSetupExtension obExtension = new OmniBrowseSetupExtension(contactController);
      Integer numberOfOperators = obExtension.getNumerOfOperators();
      System.assertEquals(operatorList.size(), numberOfOperators);
    }
    Test.stopTest();
  }
}