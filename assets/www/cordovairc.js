var cordovairc = function () {};

cordovairc.prototype.connect = function (callback, args) {
	cordova.exec(callback, function(err){alert(err);},'CordovaIrc', 'connect', args);
};

cordovairc.prototype.send = function (args) {
	cordova.exec(function(res){}, function(err){alert(err);},'CordovaIrc', 'send', args);
};

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.cordovairc) {
    window.plugins.cordovairc = new cordovairc();
}