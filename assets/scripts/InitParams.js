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
with (JavaPackages){
	var dolphinTranslate = new Vector3f(0.0, 0.7, 0.0);
	var garbageScale = new Vector3f(1.0, 1.0, 1.0);
}
