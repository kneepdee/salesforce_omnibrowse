@RestResource(urlMapping='/Tasks/*')
global with sharing class TaskCreator {

    @HttpGet
    global static Task doGet() {
        RestRequest req = RestContext.request;
        RestResponse res = RestContext.response;
        String taskID = req.requestURI.substring(req.requestURI.lastIndexOf('/')+1);
        Task result = [SELECT Id, Description FROM Task WHERE Id = :taskId];
        return result;
    }

    private static String lookUpOperatorId(String operatorId) {
        List<User> operators = [SELECT Id FROM User WHERE OmniBrowse_Operator_ID__c = :operatorId];
        return (operators.size() > 0) ? operators[0].Id : null;
    }

    @HttpPost
    global static String doPost(String visitorId, String operatorId, String eventType){
        String ownerId = lookUpOperatorId(operatorId);
        ownerId = (ownerId != null) ? ownerId : UserInfo.getUserId();
        Task newTask = new Task(Description = visitorId,
        Priority = 'Normal',
        Status = 'Completed',
        OwnerId = ownerId,
        Subject = eventType,
        IsReminderSet = false);
        insert newTask;
        return newTask.Id;
    }
}