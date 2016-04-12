@echo off

SET dart-temp="dart-build"

echo(
	
rem Check for correct directory
if not exist pubspec.yaml (
	echo ERROR: Wrong directory
	echo(
	exit	
) 

rem Pull changes from repository
echo ### Pull changes from repository
call git pull
echo OK

rem Build project
echo ### Building dart project
call pub.bat get
call pub.bat build --mode=release
echo OK

rem Copy to temporary directory
echo(
echo ### Copy to temporary directory

if not exist %TEMP%\%dart-temp% (
	mkdir %TEMP%\%dart-temp%
)

call xcopy /s/e/y .\build\* %TEMP%\%dart-temp%
echo OK

rem Checkout gh-pages
echo(
echo ### Checkout gh-pages
call git checkout gh-pages
echo OK

rem Copy files from temp folder
echo(
echo ### Copy files from temp folder
call xcopy /s/e/y %TEMP%\%dart-temp%\web\* .

rem Commit to repository
echo(
echo ### Commit to repository
call git add .
call git commit -m "Auto commit %date% - %time%"
call git push origin gh-pages
echo OK

rem Change back to master branch
echo(
echo ### Change back to master branch
call git checkout master
echo OK

rem Remove temporary directory again
echo(
echo ### Remove temporary directory
call rmdir /s /q %TEMP%\%dart-temp%
echo OK