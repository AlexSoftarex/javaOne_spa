

function Application() {
	this.name = 'World!!!';		
}

Application.annotations = [
                           new angular.Component({
                        	   selector: "application"
                           }),
                           new angular.View({
                        	   template: "<div><h1>Hello {{name}}</h1></div>"
                           })
                          ];

document.addEventListener("DOMContentLoaded", function(){
	angular.bootstrap(Application)
})
