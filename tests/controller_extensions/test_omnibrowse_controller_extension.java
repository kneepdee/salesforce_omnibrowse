@isTest
private class TestOmniBrowseControllerExtension {

  static testMethod void testGetExternalIdValueWhenEmail(){
    final String contactEmail='test@omnibrowse.com';
    final String contactLastName = 'LastName';
    final String modelName = 'Contact';

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
      ExternalIDFieldName__c = 'Email',
      OmniBrowse_Model__c = modelName
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
      String omnibrowseModelName = obExtension.getOmniBrowseModelName();
      System.assertEquals(contactEmail, externalIdValue);
      System.assertEquals(omnibrowseModelName, modelName);
    }
    Test.stopTest();
  }

  static testMethod void testGetExternalIdValueWhenNotEmail(){
    final String contactEmail='test@omnibrowse.com';
    final String contactLastName = 'LastName';
    final String modelName = 'Contact';
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
      ExternalIDFieldName__c = 'LastName',
      OmniBrowse_Model__c = modelName
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
      String omnibrowseModelName = obExtension.getOmniBrowseModelName();
      System.assertEquals(contactLastName, externalIdValue);
      System.assertEquals(omnibrowseModelName, modelName);
    }
    Test.stopTest();
  }
}