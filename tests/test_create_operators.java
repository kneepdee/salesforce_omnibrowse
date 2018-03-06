@isTest
private class CreateOperatorTest {
     @isTest static void createOperator() {
        // Set mock callout class
        System.Test.setMock(HttpCalloutMock.class, new OmniBrowseMockHttpResponseGenerator());
        OmniBrowseConfiguration__c omnibrowseConfiguration = new OmniBrowseConfiguration__c(
            Active__c = true,
            ApiToken__c = 'API Token',
            AppToken__c = 'APP Token',
            ComponentHeight__c ='1200px',
            ExternalIDFieldName__c = 'Email',
            ModelName__c ='Contact',
            SiteId__c  = 'Site Id'
        );
        insert omnibrowseConfiguration;
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
        insert user;

        System.runas(user)
        {
            System.Test.startTest();
            CreateOperators.Operator operator = CreateOperators.createOperator(user);
            System.assertEquals(operator.operatorId, 'ID');
        }
        System.Test.stopTest();
    }

     @isTest static void assertGetConfiguration() {
        // Set mock callout class
        System.Test.setMock(HttpCalloutMock.class, new OmniBrowseMockHttpResponseGenerator());
        OmniBrowseConfiguration__c omnibrowseConfiguration = new OmniBrowseConfiguration__c(
            Active__c = true,
            ApiToken__c = 'API Token',
            AppToken__c = 'APP Token',
            ComponentHeight__c ='1200px',
            ExternalIDFieldName__c = 'Email',
            ModelName__c ='Contact',
            SiteId__c  = 'Site Id'
        );
        insert omnibrowseConfiguration;
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
        insert user;

        System.runas(user)
        {
            System.Test.startTest();
            OmniBrowseConfiguration__c activeConfiguration = CreateOperators.getConfiguration();

            System.assertEquals(activeConfiguration.SiteId__c, 'Site Id');
            System.assertEquals(activeConfiguration.ApiToken__c, 'API Token');
        }
        System.Test.stopTest();
    }

    @isTest static void createOperators() {
        // Set mock callout class
        System.Test.setMock(HttpCalloutMock.class, new OmniBrowseMockHttpResponseGenerator());
        OmniBrowseConfiguration__c omnibrowseConfiguration = new OmniBrowseConfiguration__c(
            Active__c = true,
            ApiToken__c = 'API Token',
            AppToken__c = 'APP Token',
            ComponentHeight__c ='1200px',
            ExternalIDFieldName__c = 'Email',
            ModelName__c ='Contact',
            SiteId__c  = 'Site Id'
        );
        insert omnibrowseConfiguration;
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
        insert user;

        System.runas(user)
        {
            System.Test.startTest();
            List<User> users = new List<User>();
            users.add(user);

            List<CreateOperators.Operator> operators = CreateOperators.createOperators(users);
            System.assertEquals(operators[0].operatorId, 'ID');
        }
        System.Test.stopTest();
    }
}