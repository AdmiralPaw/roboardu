; Имя приложения
#define   Name       "OmegaBot_IDE"
; Путь установки по стандарту
#define   Path 			 "Arduino\tools"
; Версия приложения
#define   Version    "1.0.1.5"
; Фирма-разработчик
#define   Publisher  "Omega-lab"
; Сафт фирмы разработчика
#define   URL        "https://omegabot.ru/"
; Имя исполняемого модуля
#define   ExeName    "roboscratch-tool"
; Путь до приложения
#define		Sourse	 "C:\Users\User\Работа\OmegaBot_IDE\"


;------------------------------------------------------------------------------
;   Параметры установки
;------------------------------------------------------------------------------
[Setup]
; Уникальный идентификатор приложения, 
;сгенерированный через Tools -> Generate GUID
AppId= {{8B598797-275D-4E06-83DA-CF5DAEE9DB23}

; Прочая информация, отображаемая при установке
AppName={#Name}
AppVersion={#Version}
AppPublisher={#Publisher}
AppPublisherURL={#URL}
AppSupportURL={#URL}
AppUpdatesURL={#URL}

; Путь установки по-умолчанию 
DefaultDirName={code:findMSG}

; Имя группы в меню "Пуск"
DefaultGroupName={#Name}

; Каталог, куда будет записан собранный setup и имя исполняемого файла
OutputDir={#Sourse}\roboscratch_inno
OutputBaseFileName=OmegaBot_IDE-setup

; Файл иконки
SetupIconFile={#Sourse}\roboscratch_inno\icon.ico

; Параметры сжатия
Compression=lzma
SolidCompression=yes


AlwaysShowDirOnReadyPage=yes 
DirExistsWarning=no
UninstallFilesDir={app}\tools\{#Name}

[Icons]
Name: "{commondesktop}\OmegaBot"; Filename: "{app}\tools\{#Name}\tool\{#Name}.bat"; IconFilename: "{app}\tools\{#Name}\unins000.exe"

;[Registry]
;Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\/Omega/Bot_/I/D/E"; ValueType: string; ValueName: "cmd_run"; ValueData: "true"

;------------------------------------------------------------------------------
;   Устанавливаем языки для процесса установки
;------------------------------------------------------------------------------
[Languages] 
; Если нужно, необходимо указывать файлы лицензии
Name: "english"; MessagesFile: "compiler:Default.isl";
; LicenseFile: "License_ENG.txt"
Name: "russian"; MessagesFile: "compiler:Languages\Russian.isl";
; LicenseFile: "License_RUS.txt"

;------------------------------------------------------------------------------
;   Опциональность - меню выбора типа установки
;------------------------------------------------------------------------------
[Types]
Name: "full"; 																 Description: "Полная установка"
Name: "custom"; 															 Description: "Выборочная установка"; Flags: iscustom 

[Components]
Name: "programm"; 														 Description: "Установить {#Name}"; Types: full custom ;Flags: fixed

Name: "libs"; 																             Description: "Установить библиотеки"; Types: full 
Name: "libs\Adafruit_SSD1306"; 								             Description: "Adafruit_SSD1306"; Types: full
Name: "libs\AdafruitGFX"; 										             Description: "Adafruit-GFX"; Types: full
Name: "libs\I2Cdev"; 													             Description: "I2Cdev"; Types: full
Name: "libs\MPU9250";													             Description: "MPU9250"; Types: full
Name: "libs\SparkFun_ToF_Range_FinderVL6180";              Description: "SparkFun_ToF_Range_Finder-VL6180"; Types: full
Name: "libs\NewPing";                                      Description: "NewPing"; Types: full
Name: "libs\OneWire";                                      Description: "OneWire-2.3.5"; Types: full
Name: "libs\Arduino_Temperature_Control_Library";          Description: "Arduino-Temperature-Control-Library-master"; Types: full
Name: "libs\SparkFun_BH1749NUC";                           Description: "SparkFun_BH1749NUC"; Types: full

;------------------------------------------------------------------------------
;   Файлы, которые надо включить в пакет установщика
;------------------------------------------------------------------------------
[Files]

; Прилагающиеся ресурсы
Source: "{#Sourse}\ArduinoLibraries\Adafruit_SSD1306-master\*"; Components: libs\Adafruit_SSD1306; DestDir:  "{app}\libraries\Adafruit_SSD1306-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\Adafruit-GFX-Library-master\*"; Components: libs\AdafruitGFX; DestDir: "{app}\libraries\Adafruit-GFX-Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\I2Cdev\*"; Components: libs\I2Cdev; DestDir: "{app}\libraries\I2Cdev"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\MPU9250-master\*"; Components: libs\MPU9250; DestDir: "{app}\libraries\MPU9250-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master\*"; Components: libs\SparkFun_ToF_Range_FinderVL6180; DestDir: "{app}\libraries\SparkFun_ToF_Range_Finder-VL6180_Arduino_Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\NewPing\*"; Components: libs\NewPing; DestDir: "{app}\libraries\NewPing"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\OneWire-2.3.5\*"; Components: libs\OneWire; DestDir: "{app}\libraries\OneWire-2.3.5"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\Arduino-Temperature-Control-Library-master\*"; Components: libs\Arduino_Temperature_Control_Library; DestDir: "{app}\libraries\Arduino-Temperature-Control-Library-master"; Flags: recursesubdirs createallsubdirs ignoreversion
Source: "{#Sourse}\ArduinoLibraries\SparkFun_BH1749NUC\*"; Components: libs\SparkFun_BH1749NUC; DestDir: "{app}\libraries\SparkFun_BH1749NUC"; Flags: recursesubdirs createallsubdirs ignoreversion

Source: "{#Sourse}\roboscratch_inno\{#Name}.bat"; DestDir: "{app}\tools\{#Name}\tool"; Flags: ignoreversion
Source: "{#Sourse}\roboscratch_inno\prefs_fix.bat"; DestDir: "{app}\tools\{#Name}\tool"; Flags: ignoreversion
Source: "{#Sourse}\target\{#ExeName}.jar"; Components: programm; DestDir: "{app}\tools\{#Name}\tool"; Flags: ignoreversion

[Run]
Filename: "{app}\tools\{#Name}\tool\prefs_fix.bat"; StatusMsg: "Fix Arduino IDE"; Flags: runhidden

[Code]
function findMSG(Value:string): string;

var 
    reg_key: string; // Просматриваемый подраздел системного реестра
    success: boolean; // Флаг наличия запрашиваемой версии
    key_value: string; // Прочитанное из реестра значение ключа

begin

    success := false;
    reg_key := 'SOFTWARE\WOW6432Node\Arduino';
    success := RegQueryStringValue(HKEY_LOCAL_MACHINE, reg_key, 'Install_Dir', key_value);
    if success then
    begin
      result := key_value;
      Exit;
    end;
    MsgBox('{#Name} является дополнением к Arduino IDE.'#13#13
             'Пожалуйста установите Arduino IDE.',
             mbInformation,
             MB_OK);
    Abort;
end;

function find(): boolean;

var 
    reg_key: string; // Просматриваемый подраздел системного реестра
    success: boolean; // Флаг наличия запрашиваемой версии
    key_value: string; // Прочитанное из реестра значение ключа

begin

    success := false;
    reg_key := 'SOFTWARE\WOW6432Node\Arduino';
    success := RegQueryStringValue(HKEY_LOCAL_MACHINE, reg_key, 'Install_Dir', key_value);
    result := success;
end;

function InitializeSetup(): boolean;
var
  ResultCode: integer;
begin
  if not find() then
  begin
  // Launch Notepad and wait for it to terminate
    //if Exec(ExpandConstant('{tmp}\arduino-1.8.13-windows.exe'), '', '', SW_SHOW,
    //   ewWaitUntilTerminated, ResultCode) then
    if MsgBox('{#Name} является дополнением к Arduino IDE.'#13#13
             'Установщик не обнаружил Arduino IDE, установить?',
              mbConfirmation, MB_YESNO or MB_DEFBUTTON2) = IDYES then
    begin
      if Exec('.\arduino\arduino-1.8.13-windows.exe', '', '', SW_SHOW,
         ewWaitUntilTerminated, ResultCode) then
      begin
        // handle success if necessary; ResultCode contains the exit code
      end
      else begin
        // handle failure if necessary; ResultCode contains the error code
      end;
    end;
  end;

  // Proceed Setup
  Result := True;
end;

procedure DeinitializeUninstall();

begin
  RegDeleteKeyIncludingSubkeys(HKEY_CURRENT_USER, 'SOFTWARE\JavaSoft\Prefs\/Omega/Bot_/I/D/E');
end;
