@isTest
global class OmniBrowseMockHttpResponseGenerator implements HttpCalloutMock {
    // Implement this interface method
    global HTTPResponse respond(HTTPRequest req) {
        // Optionally, only send a mock response for a specific method
        System.assertEquals('POST', req.getMethod());

        // Create a fake response
        String body = '{' +
          '"name": "Jared Grey",' +
          '"email": "jared@institution.com",' +
          '"phone": "+19174743334",' +
          '"password": "password",' +
          '"password_confirmation": "password",' +
          '"picture_data": "data:image/png;base64,[Base64 encoded image]",' +
          '"assignments": [' +
            '{' +
              '"site_id": "$site_id"' +
            '}' +
          ']' +
        '}';

        HttpResponse res = new HttpResponse();
        res.setHeader('Content-Type', 'application/json');
        res.setBody(body);
        res.setStatusCode(200);
        res.setBody('{"id": "ID"}');
        return res;
    }
}
