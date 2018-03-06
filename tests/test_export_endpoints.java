@isTest
private class SaleMoveEndpointsTest {
     @isTest static void testApiEndpoint() {
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
            String USApiEndpoint = SaleMoveEndpoints.apiEndpointForRegion('US');
            String EUApiEndpoint = SaleMoveEndpoints.apiEndpointForRegion('EU');
            System.assertEquals(USApiEndpoint, 'https://api.salemove.com');
            System.assertEquals(EUApiEndpoint, 'https://api.salemove.eu');
        }
        System.Test.stopTest();
    }

    @isTest static void testOmniBrowseEndpoint() {
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
            String USApiEndpoint = SaleMoveEndpoints.omniBrowseEndpointForRegion('US');
            String EUApiEndpoint = SaleMoveEndpoints.omniBrowseEndpointForRegion('EU');
            System.assertEquals(USApiEndpoint, 'https://omnibrowse.salemove.com');
            System.assertEquals(EUApiEndpoint, 'https://omnibrowse.salemove.eu');
        }
        System.Test.stopTest();
    }

}
