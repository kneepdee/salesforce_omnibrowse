@isTest
private class TestOmniBrowseControllerForLeads {


    static testMethod void testGetExternalIdValueWhenEmail(){
        final String contactEmail='test@omnibrowse.com';
        final String contactLastName = 'LastName';
        final String modelName = 'Lead';

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
             LocaleSidKey = 'en_US'
        );
        OmniBrowseConfiguration__c omnibrowseConfiguration = new OmniBrowseConfiguration__c(
            Active__c = true,
            ApiToken__c = 'API Token',
            AppToken__c = 'APP Token',
            ComponentHeight__c ='1200px',
            ExternalIDFieldName__c = 'Email',
            ModelName__c = modelName,
            SiteId__c  = 'Site Id'
        );

        insert omnibrowseConfiguration;
        insert user;

        System.runas(user)
        {
            Lead lead = new Lead(Email=contactEmail, LastName=contactLastName, Company='SaleMove');
            insert lead;
            System.Test.startTest();
            ApexPages.StandardController contactController = new ApexPages.StandardController(lead);
            OmniBrowseControllerExtension obExtension = new OmniBrowseControllerExtension(contactController);
            ApexPages.currentPage().getParameters().put('id', lead.id);
            String externalIdValue = obExtension.getExternalIdValue();
            String omnibrowseModelName = obExtension.getOmniBrowseModelName();
            System.assertEquals(contactEmail, externalIdValue);
            System.assertEquals(omnibrowseModelName, modelName);
        }
        System.Test.stopTest();
    }

     static testMethod void testGetExternalIdValueWhenNotEmail(){
        final String contactEmail='test@omnibrowse.com';
        final String contactLastName = 'LastName';
        final String modelName = 'Lead';

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
             LocaleSidKey = 'en_US'
        );

        OmniBrowseConfiguration__c omnibrowseConfiguration = new OmniBrowseConfiguration__c(
            Active__c = true,
            ApiToken__c = 'API Token',
            AppToken__c = 'APP Token',
            ComponentHeight__c ='1200px',
            ExternalIDFieldName__c = 'LastName',
            ModelName__c = modelName,
            SiteId__c  = 'Site Id'
        );

        insert omnibrowseConfiguration;
        insert user;
        System.runas(user)
        {
            Lead lead = new Lead(Email=contactEmail, LastName=contactLastName, Company='SaleMove');
            insert lead;
            System.Test.startTest();

            ApexPages.StandardController contactController = new ApexPages.StandardController(lead);
            OmniBrowseControllerExtension obExtension = new OmniBrowseControllerExtension(contactController);
            ApexPages.currentPage().getParameters().put('id', lead.id);
            String externalIdValue = obExtension.getExternalIdValue();
            String omnibrowseModelName = obExtension.getOmniBrowseModelName();
            System.assertEquals(contactLastName, externalIdValue);
            System.assertEquals(omnibrowseModelName, modelName);
        }
        System.Test.stopTest();
    }

    static testMethod void testOmniBrowseConfiguration(){
        final String contactEmail='test@omnibrowse.com';
        final String contactLastName = 'LastName';
        final String modelName = 'Lead';
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
             LocaleSidKey = 'en_US'
        );

        OmniBrowseConfiguration__c omnibrowseConfiguration = new OmniBrowseConfiguration__c(
            Active__c = true,
            ApiToken__c = 'API Token',
            AppToken__c = 'APP Token',
            ComponentHeight__c ='1200px',
            ExternalIDFieldName__c = 'Email',
            ModelName__c = modelName,
            SiteId__c  = 'Site Id'
        );
        insert omnibrowseConfiguration;
        insert user;
        System.runas(user)
        {
            Lead lead = new Lead(Email=contactEmail, LastName=contactLastName, Company='SaleMove');
            insert lead;
            System.Test.startTest();

            ApexPages.StandardController contactController = new ApexPages.StandardController(lead);
            OmniBrowseControllerExtension obExtension = new OmniBrowseControllerExtension(contactController);
            ApexPages.currentPage().getParameters().put('id', lead.id);

            String omnibrowseModelName = obExtension.getOmniBrowseModelName();
            String siteId = obExtension.getSiteId();
            String appToken = obExtension.getAppToken();
            String apiToken = obExtension.getApiToken();

            System.assertEquals(siteId, omnibrowseConfiguration.SiteId__c);
            System.assertEquals(omnibrowseModelName, omnibrowseConfiguration.ModelName__c);
            System.assertEquals(appToken, omnibrowseConfiguration.AppToken__c);
            System.assertEquals(apiToken, omnibrowseConfiguration.ApiToken__c);
        }
        System.Test.stopTest();
    }

}