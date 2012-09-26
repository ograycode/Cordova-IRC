var cordovairc = function () {};

cordovairc.prototype.connect = function (callback, args) {
	cordova.exec(callback, function(err){alert(err);},'CordovaIrc', 'connect', args);
};

if(!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.cordovairc) {
    window.plugins.cordovairc = new cordovairc();
}