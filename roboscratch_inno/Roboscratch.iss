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
#define   ExeName    "roboscratch-tool"
; ���� �� ����������
#define		Sourse	 "D:\GitHub\roboardu\"


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
DefaultDirName={code:find}

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
Name: "libs\NewPing";                          Description: "NewPing"; Types: full
Name: "libs\OneWire";                          Description: "OneWire-2.3.5"; Types: full

;------------------------------------------------------------------------------
;   �����, ������� ���� �������� � ����� �����������
;------------------------------------------------------------------------------
[Files]

; ������������� �������
Source: "{#Sourse}\ArduinoLibraries\Adafruit_SSD1306-master\*"; Components: libs\Adafruit_SSD1306; DestDir:  "{app}\libraries\Adafruit_SSD1306-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\Adafruit-GFX-Library-master\*"; Components: libs\AdafruitGFX; DestDir: "{app}\libraries\Adafruit-GFX-Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\I2Cdev\*"; Components: libs\I2Cdev; DestDir: "{app}\libraries\I2Cdev"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\MPU9250-master\*"; Components: libs\MPU9250; DestDir: "{app}\libraries\MPU9250-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master\*"; Components: libs\SparkFun_ToF_Range_FinderVL6180; DestDir: "{app}\libraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\NewPing\*"; Components: libs\NewPing; DestDir: "{app}\libraries\NewPing"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\OneWire-2.3.5\*"; Components: libs\OneWire; DestDir: "{app}\libraries\OneWire-2.3.5"; Flags: recursesubdirs createallsubdirs ignoreversion


Source: "{#Sourse}\target\{#ExeName}.jar"; Components: programm; DestDir: "{app}\tools\{#Name}\tool"; Flags: ignoreversion

[Code]
function find(Value:string): string;

var 
    reg_key: string; // ��������������� ��������� ���������� �������
    success: boolean; // ���� ������� ������������� ������ .NET
    key_value: string; // ����������� �� ������� �������� �����

begin

    success := false;
    reg_key := 'SOFTWARE\WOW6432Node\Arduino';
    success := RegQueryStringValue(HKEY_LOCAL_MACHINE, reg_key, 'Install_Dir', key_value);
    if success then
    begin
      result := key_value;
      Exit;
    end;
    if MsgBox('{#Name} �������� ����������� � Arduino IDE.'#13#13
             '���������� �� ��������� Arduino IDE, ���������� ���������?',
             mbConfirmation,
             MB_YESNO or MB_DEFBUTTON2) = IDNO then
    begin
      result := '';
      Abort;
    end;
end;
