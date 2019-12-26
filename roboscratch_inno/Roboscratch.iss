; ��� ����������
#define   Name       "OmegaBot_IDE"
; ���� ��������� �� ���������
#define   Path 			 "Arduino\tools"
; ������ ����������
#define   Version    "1.0.0"
; �����-�����������
#define   Publisher  "Omega-lab"
; ���� ����� ������������
#define   URL        "https://omegabot.ru/"
; ��� ������������ ������
#define   ExeName    "OmegaBot_IDE"
; ���� �� ����������
#define		Sourse	 "C:\Users\Jesus\roboardu"



;------------------------------------------------------------------------------
;   ��������� ���������
;------------------------------------------------------------------------------
[Setup]

; ���������� ������������� ����������, 
;��������������� ����� Tools -> Generate GUID
AppId= {{8B598797-275D-4E06-83DA-CF5DAEE9DB23}

; ������ ����������, ������������ ��� ���������
AppName={#Name}
AppVersion={#Version}
AppPublisher={#Publisher}
AppPublisherURL={#URL}
AppSupportURL={#URL}
AppUpdatesURL={#URL}

; ���� ��������� ��-��������� 
DefaultDirName={userdocs}\Arduino

; ��� ������ � ���� "����"
DefaultGroupName={#Name}

; �������, ���� ����� ������� ��������� setup � ��� ������������ �����
OutputDir={#Sourse}\roboscratch_inno
OutputBaseFileName=OmegaBot_IDE-setup

; ���� ������
SetupIconFile={#Sourse}\roboscratch_inno\icon.ico

; ��������� ������
Compression=lzma
SolidCompression=yes


AlwaysShowDirOnReadyPage=yes 
DirExistsWarning=no
UninstallFilesDir={app}\tools\{#Name}

;------------------------------------------------------------------------------
;   ������������� ����� ��� �������� ���������
;------------------------------------------------------------------------------
[Languages] 
; ���� �����, ���������� ��������� ����� ��������
Name: "english"; MessagesFile: "compiler:Default.isl";
; LicenseFile: "License_ENG.txt"
Name: "russian"; MessagesFile: "compiler:Languages\Russian.isl";
; LicenseFile: "License_RUS.txt"

;------------------------------------------------------------------------------
;   �������������� - ���� ������ ���� ���������
;------------------------------------------------------------------------------
[Types]
Name: "full"; 																 Description: "������ ���������"
Name: "custom"; 															 Description: "���������� ���������"; Flags: iscustom 

[Components]
Name: "programm"; 														 Description: "���������� {#Name}"; Types: full custom ;Flags: fixed

Name: "libs"; 																 Description: "���������� ����������"; Types: full 
Name: "libs\Adafruit_SSD1306"; 								 Description: "Adafruit_SSD1306"; Types: full
Name: "libs\AdafruitGFX"; 										 Description: "Adafruit-GFX"; Types: full
Name: "libs\I2Cdev"; 													 Description: "I2Cdev"; Types: full
Name: "libs\MPU9250";													 Description: "MPU9250"; Types: full
Name: "libs\SparkFun_ToF_Range_FinderVL6180";  Description: "SparkFun_ToF_Range_Finder-VL6180"; Types: full

;------------------------------------------------------------------------------
;   �����, ������� ���� �������� � ����� �����������
;------------------------------------------------------------------------------
[Files]

; ������������� �������
Source: "{#Sourse}\ArduinoLibraries\Adafruit_SSD1306-master\*"; Components: libs\Adafruit_SSD1306; DestDir: "{userdocs}\Arduino\libraries\Adafruit_SSD1306-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\Adafruit-GFX-Library-master\*"; Components: libs\AdafruitGFX; DestDir: "{userdocs}\Arduino\libraries\Adafruit-GFX-Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\I2Cdev\*"; Components: libs\I2Cdev; DestDir: "{userdocs}\Arduino\libraries\I2Cdev"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\MPU9250-master\*"; Components: libs\MPU9250; DestDir: "{userdocs}\Arduino\libraries\MPU9250-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master\*"; Components: libs\SparkFun_ToF_Range_FinderVL6180; DestDir: "{userdocs}\Arduino\libraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion

Source: "{#Sourse}\target\OmegaBot_IDE-tool.jar"; Components: programm; DestDir: "{app}\tools\{#Name}\tool"; Flags: ignoreversion


