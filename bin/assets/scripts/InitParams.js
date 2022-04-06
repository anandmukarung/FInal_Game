// just a test
//importer helps with vector 3f
var JavaPackages = new JavaImporter(
	Packages.tage.Light,
	Packages.org.joml.Vector3f
);
var rumble = .001;
var objectScale = 0.2;
var dolphinScale = 3.0;
var groundScale = 100.0;
var streetlightScale = 4.0;
var buildingScale = 10.0;

with (JavaPackages){
	var dolphinTranslate = new Vector3f(0.0, 7.0, 0.0);
	var garbageScale = new Vector3f(10.0, 10.0, 10.0);
	var streetlightTranslate = new Vector3f(5.0, 0.0, 0.0);
	var garbageTranslate = new Vector3f(30.0, 0.0, 20.0);
	var buidling1 = new Vector3f(40.0, 0.0, 40.0);
	var buidling2 = new Vector3f(40.0, 0.0, 70.0);
	var buidling3 = new Vector3f(40.0, 0.0, 100.0);
}
