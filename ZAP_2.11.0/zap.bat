if exist "%USERPROFILE%\OWASP ZAP\.ZAP_JVM.properties" (
	set /p jvmopts=< "%USERPROFILE%\OWASP ZAP\.ZAP_JVM.properties"
) else (
	set jvmopts=-Xmx512m
)
set widget=%3
set widget
set widget=%widget:"=%
set widget
java %jvmopts% -jar %1zap-2.11.0.jar %2 %widget%
