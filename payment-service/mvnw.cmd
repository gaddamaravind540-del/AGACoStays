@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
set "WRAPPER_PROPS=%SCRIPT_DIR%.mvn\wrapper\maven-wrapper.properties"

if not exist "%WRAPPER_PROPS%" (
	echo Missing Maven wrapper properties: "%WRAPPER_PROPS%" 1>&2
	exit /b 1
)

for /f "tokens=1,* delims==" %%A in ('findstr /b "distributionUrl=" "%WRAPPER_PROPS%"') do set "DISTRIBUTION_URL=%%B"

if "%DISTRIBUTION_URL%"=="" (
	echo Missing distributionUrl in "%WRAPPER_PROPS%" 1>&2
	exit /b 1
)

for /f %%F in ('powershell -NoProfile -Command "[IO.Path]::GetFileName('%DISTRIBUTION_URL%')"') do set "DIST_FILE=%%F"
set "MAVEN_DIR=%DIST_FILE:-bin.zip=%"
set "MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\%MAVEN_DIR%"
set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"

if not exist "%MAVEN_CMD%" (
	powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $url='%DISTRIBUTION_URL%'; $mavenHome='%MAVEN_HOME%'; $zip=Join-Path ([IO.Path]::GetTempPath()) '%DIST_FILE%'; $parent=Split-Path $mavenHome -Parent; New-Item -ItemType Directory -Force -Path $parent | Out-Null; Invoke-WebRequest -Uri $url -OutFile $zip; $extract=Join-Path ([IO.Path]::GetTempPath()) ([Guid]::NewGuid().ToString()); Expand-Archive -Path $zip -DestinationPath $extract -Force; $dir=Get-ChildItem -Path $extract -Directory | Select-Object -First 1; if (Test-Path $mavenHome) { Remove-Item $mavenHome -Recurse -Force }; Move-Item $dir.FullName $mavenHome; Remove-Item $zip -Force; Remove-Item $extract -Recurse -Force"
	if errorlevel 1 exit /b 1
)

call "%MAVEN_CMD%" %*
