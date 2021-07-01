@echo off
if "%~1"=="" (set "x=%~f0"& start "" /min "%comspec%" /v/c "!x!" any_word & exit /b)

set process=javaw.exe
reg add "hkcu\SOFTWARE\JavaSoft\Prefs\/Omega/Bot_/I/D/E" /v cmd_run                /t REG_SZ /d true /f >nul
reg add "hkcu\SOFTWARE\JavaSoft\Prefs\/Omega/Bot_/I/D/E" /v ardublock.ui.autohide  /t REG_SZ /d true /f >nul
reg add "hkcu\SOFTWARE\JavaSoft\Prefs\/Omega/Bot_/I/D/E" /v ardublock.ui.autostart /t REG_SZ /d true /f >nul

start /wait ..\..\..\arduino.exe