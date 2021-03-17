({
  getOmniBrowseConfiguration: function(cmp) {
    // Load all contact data
    var action = cmp.get("c.getOmniBrowseConfiguration");
    action.setParams({ id : cmp.get("v.recordId") });

    action.setCallback(this, function(response) {
      var state = response.getState();
      if (state === "SUCCESS") {
        var omniBrowseConfiguration = response.getReturnValue();
        cmp.set("v.ModelName", omniBrowseConfiguration.modelName);
        cmp.set("v.MobileiOSOpeninNewTab", omniBrowseConfiguration.iOSOpenInNewTab);
        cmp.set("v.ExternalId", omniBrowseConfiguration.externalId);
        cmp.set("v.SiteId", omniBrowseConfiguration.siteId);
        cmp.set("v.ApiToken", omniBrowseConfiguration.apiToken);
        cmp.set("v.AppToken", omniBrowseConfiguration.siteToken);
        cmp.set("v.OperatorId", omniBrowseConfiguration.operatorId);
        cmp.set("v.ApiEndpoint", omniBrowseConfiguration.apiEndpoint);
        cmp.set("v.OmniBrowseEndpoint", omniBrowseConfiguration.omnibrowseEndpoint);

        this.backoff.bind(this)(4, this.retryFun.bind(this)(cmp), 1000);
      }
    });
    $A.enqueueAction(action);
  },
  
  retryFun: function(cmp) {
      return function () {  
	return this.getLaunchUrl(cmp).then(function(res){        
      if (res.isVisitorOnline > 0){
        return Promise.resolve();
      }
      else{
        return Promise.reject();
      }
    })
      }.bind(this);
  },

  readCredentials: function(cmp){
    return {
     apiToken: cmp.get('v.ApiToken'),
     appToken: cmp.get('v.AppToken'),
     siteId: cmp.get('v.SiteId'),
     operatorId: cmp.get('v.OperatorId'),
     omnibrowseEndpoint: cmp.get('v.OmniBrowseEndpoint'),
     mobileiOSOpeninNewTab: cmp.get('v.MobileiOSOpeninNewTab'),
     externalId: cmp.get('v.ExternalId'),
     modeName: cmp.get('v.ModelName')
    }
  },

  status: function (response) {
    if (response.status >= 200 && response.status < 300) {
      return Promise.resolve(response)
    } else {
      return Promise.reject(new Error(response.statusText))
    }
  },

  json: function(response) {
    return response.json()
  },

  acquireLaunchToken: function(credentials) {
    const omnibrowseEndpoint = credentials.omnibrowseEndpoint;
    const appToken = credentials.appToken;
    const operatorId = credentials.operatorId;
    const url = `${omnibrowseEndpoint}/auth/token`;

    const data = new FormData();
    data.append('app_token', appToken);
    data.append('operator_id', operatorId);
    const options = {
      headers: {
        'Accept': 'application/vnd.salemove.v1+json'
      },
      mode: 'cors',
      body: data,
      method: 'POST'
    }
    return fetch(url, options)
    .then(this.status)
    .then(this.json)
    .then(function(res){
      const response = {launchToken: res.launch_token};
      return Promise.resolve(Object.assign({}, credentials, response));
    });
  },

  isVisitorOnlineByExternalId: function(credentials){
    const externalId = credentials.externalId;
    const omnibrowseEndpoint = credentials.omnibrowseEndpoint;
    const url = `${omnibrowseEndpoint}/sites/${credentials.siteId}/visitors?external_id=${externalId}`;
    const options = {
      headers: {
        'Authorization': `Token ${credentials.apiToken}`,
        'Accept': 'application/vnd.salemove.v1+json',
        'Content-type': 'application/x-www-form-urlencoded'
      },
      mode: 'cors',
      method: 'GET'
    };
	
    return fetch(url, options)
    .then(this.status)
    .then(this.json)
    .then(function(res){
      const response = {isVisitorOnline: res.length};
	  return Promise.resolve(Object.assign({}, credentials, response));    
    })
  },

  isIOS: function () {
    var iOS = parseFloat(
        ('' + (/CPU.*OS ([0-9_]{1,5})|(CPU like).*AppleWebKit.*Mobile/i.exec(navigator.userAgent) || [0,''])[1])
        .replace('undefined', '3_2').replace('_', '.').replace('_', '')
    ) || false;
    return (iOS == false) ? false : true;
  },

  prepareOmniBrowseLaunchUrl: function(config){
    const omnibrowseEndpoint = config.omnibrowseEndpoint;
    const launchToken = config.launchToken;
    const siteId = config.siteId;
    const externalId = config.externalId;
    const iframePermissionsParameter = '?iframe_allow=' + encodeURIComponent("camera *; microphone *");

    const url = (config.isVisitorOnline == true) ? `${omnibrowseEndpoint}/auth/${launchToken}/${siteId}/external_id/${externalId}${iframePermissionsParameter}` :
            `${omnibrowseEndpoint}/auth/${launchToken}/${siteId}/${iframePermissionsParameter}`;
    const response = { launchUrl:  url};
    return Promise.resolve(Object.assign({}, config, response));
  },
      
   pause: function pause(duration) {
     return new Promise(function (res) {
       return setTimeout(res, duration);
     });
   },

   backoff: function backoff(retries, fn) {
     var delay = arguments.length <= 2 || arguments[2] === undefined ? 500 : arguments[2];
     return fn().catch((function (err) {
       return retries > 1 ? this.pause(delay).then((function () {
         return this.backoff(retries - 1, fn, delay * 2);
       }).bind(this)) : Promise.reject(err);
     }).bind(this));
   },
       
   log: function log(message) {
     return function () {
       return console.log(message);
     };
   },

   iframeExists: function iframeExists(attempts, onReject) {
      return function () {
        var doesNotexists = ((document.getElementById("obIframe") == null) || (document.getElementById("obIframe") == undefined));
        if (doesNotexists || (--attempts > 0)) {
          onReject();
          return Promise.reject();
        }
        return Promise.resolve();
      };
    },
        
   getLaunchUrl: function(cmp){
    const credentials = this.readCredentials(cmp);
    return this.isVisitorOnlineByExternalId(credentials)
    .then(this.acquireLaunchToken.bind(this))
    .then(this.prepareOmniBrowseLaunchUrl.bind(this))
    .then((function(res){
        this.backoff(4, this.iframeExists(1, this.log('CoBrowsing Iframe does not exist'))).then(function(){
          document.getElementById("obIframe").src = res.launchUrl;
		  cmp.set('v.LaunchUrl', res.launchUrl);
        })
        return Promise.resolve(res)
    }).bind(this))
  }
})