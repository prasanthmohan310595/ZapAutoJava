FOR /F "tokens=5" %%P IN ('
    netstat -noa 
    ^| findstr /l /c:"LISTENING" 
    ^| findstr /l /c:%1
') DO taskkill /F /PID %%P