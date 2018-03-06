public Class SaleMoveEndpoints {

    static String US_API_ENDPOINT = 'https://api.salemove.com';
    static String EU_API_ENDPOINT = 'https://api.salemove.eu';


    static String US_OMNIBROWSE_ENDPOINT = 'https://omnibrowse.salemove.com';
    static String EU_OMNIBROWSE_ENDPOINT = 'https://omnibrowse.salemove.eu';

    public static String apiEndpointForRegion(String region){
        String apiEndpoint;
        if (region.toUpperCase() == 'US') {
            apiEndpoint = US_API_ENDPOINT;
        }
        else if (region.toUpperCase() == 'EU') {
            apiEndpoint = EU_API_ENDPOINT;
        }
        else{
            apiEndpoint = US_API_ENDPOINT;
        }
        return apiEndpoint;
    }

    public static String omniBrowseEndpointForRegion(String region){
        String omnibrowseEndpoint;
        if (region.toUpperCase() == 'US') {
            omnibrowseEndpoint = US_OMNIBROWSE_ENDPOINT;
        }
        else if (region.toUpperCase() == 'EU') {
            omnibrowseEndpoint = EU_OMNIBROWSE_ENDPOINT;
        }
        else{
            omnibrowseEndpoint = US_OMNIBROWSE_ENDPOINT;
        }
        return omnibrowseEndpoint;
    }
}