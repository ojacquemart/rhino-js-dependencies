Proto = function() {};
Proto.static1 = function() {};
Proto.static2 = function() {};

Proto.prototype.classic1 = function() {};
Proto.prototype.classic2 = function() {};

Proto.static1();

var p = new Proto();
p.classic1();


