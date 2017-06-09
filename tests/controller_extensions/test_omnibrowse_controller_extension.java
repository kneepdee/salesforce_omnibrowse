@isTest
private class TestOmniBrowseControllerExtension {

  static testMethod void testGetExternalIdValueWhenEmail(){
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
      Contact contact = new Contact(Email=contactEmail, LastName=contactLastName);
      insert contact;
      Test.startTest();
      ApexPages.StandardController contactController = new ApexPages.StandardController(contact);
      OmniBrowseControllerExtension obExtension = new OmniBrowseControllerExtension(contactController);
      ApexPages.currentPage().getParameters().put('id', contact.id);
      String externalIdValue = obExtension.getExternalIdValue();
      System.assertEquals(contactEmail, externalIdValue);

    }
    Test.stopTest();
  }

  static testMethod void testGetExternalIdValueWhenNotEmail(){
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
      ExternalIDFieldName__c = 'LastName'
    );

    insert user;
    System.runas(user)
    {
      Contact contact = new Contact(Email=contactEmail, LastName=contactLastName);
      insert contact;
      Test.startTest();

      ApexPages.StandardController contactController = new ApexPages.StandardController(contact);
      OmniBrowseControllerExtension obExtension = new OmniBrowseControllerExtension(contactController);
      ApexPages.currentPage().getParameters().put('id', contact.id);
      String externalIdValue = obExtension.getExternalIdValue();
      System.assertEquals(contactLastName, externalIdValue);
    }
    Test.stopTest();
  }

}